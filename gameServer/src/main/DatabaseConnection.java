package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import data.DataStorage;

import object.Gamer;
import object.GamerResearch;;


//import org.h2.jdbcx.JdbcDataSource;

/** Klasa odpowiedzialna za polaczenie z baza danych
 */
public class DatabaseConnection 
{
	Connection conn;
	
	/** Metoda odpowiedzialna za laczenie sie z baza
	 * @param login login do bazy danych
	 * @param password haslo do bazy dancyh
	 * */
	public DatabaseConnection(String login, String password) 
	{
		try 
		{
			String localization = System.getProperty("user.dir")+System.getProperty("file.separator")+"test";
			DriverManager.registerDriver(new org.h2.Driver());
			conn = DriverManager.getConnection("jdbc:h2:"+localization, login, password);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		/*
		 * http://127.0.1.1:8082/login.jsp?jsessionid=92c8a8b56d51dba5f50439873f912a84#
		 * */
	}
	/** Metoda odpowiedzialna za zamkniecie polaczenia z baza */
	public void close()
	{
		try 
		{
			conn.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	/** Metoda odpowiedzialna za pobranie z bazy danych gid danego gracza
	 * @param gamer dane gracze, ktorego gid ma byc pobrany
	 * @return zwraca dane gracza + gid
	 * */
	public Gamer setGamerGID(Gamer gamer)
	{
		String s;
		try 
		{
			Statement sql_stmt = conn.createStatement();
			s="SELECT gid FROM gamers WHERE LOGIN='" + gamer.getLogin() + "' and PASS='" + gamer.getPassword() + "'";
			ResultSet rset = sql_stmt.executeQuery(s);
			rset.next();
			//obsluga bledu
			gamer.setgID(rset.getInt("gid"));
			rset.close();
			sql_stmt.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return gamer;
	}
	
	/** Metoda odpowiedzialna za pobranie danych z bazy danych na temat wersji pliku
	 * @return Zwraca String z informacja o stanie plikow
	 */
	public String getUpdateInfo()
	{
		String s;
		try 
		{
			s="";
			Statement sql_stmt = conn.createStatement();
			ResultSet rset = sql_stmt.executeQuery("SELECT STATE FROM UPDATE_info ORDER BY ID_NUMBER ASC");
			while (rset.next())
			{
				s=s+rset.getString("state");
			}
			rset.close();
			sql_stmt.close();
			//System.out.println(s);
			return s;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return "";
		}
	}
	
	/** Metoda odpowiedzialna za pobranie sciezki i nazwy pliku
	 * @param idNumber identyfikator pliku
	 * @param system jakiego systemu dotyczy zapytanie
	 * @return Zwraca String w postaci sciezka/plik
	 */
	public String getFileUpdatePath(int idNumber, String system)
	{
		String path;
		String file_name;
		String ret;
		try 
		{
			path="";
			file_name="";
			ret="";
			Statement sql_stmt = conn.createStatement();
			ResultSet rset = sql_stmt.executeQuery("SELECT PATH, FILE_NAME FROM FILE_UPDATE_"+system+" WHERE ID_NUMBER='"+ idNumber +"'");
			rset.next();
			path=rset.getString("path");
			file_name=rset.getString("file_name");
			if(path!=null)
			{
				ret=path+"/"+file_name;
			}
			else
			{
				ret=file_name;
			}
			rset.close();
			sql_stmt.close();
			
			return ret;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return "";
		}
	}
	public void updateResearchColumnInDB(Gamer gamer, DataStorage dataStorage)
	{
		if(gamer.getCurrentResearchActionType().contentEquals("S"))
		{
			try 
			{
				PreparedStatement stmt;
				stmt = conn.prepareStatement(
				           "UPDATE GAMERS SET CR_CAT = ?, CR_ID = ?, CR_ENDTIME = ? WHERE GID  =?");
				stmt.setString(1, gamer.getCurrentResearchCategory());
				stmt.setInt(2, gamer.getCurrentResearchID());
				Timestamp timestamp = new Timestamp(gamer.getCurrentResearchTimestamp());
				stmt.setTimestamp(3, timestamp);
				stmt.setInt(4, gamer.getgID());

				stmt.executeUpdate();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		else if(gamer.getCurrentResearchActionType().contentEquals("E"))
		{
			try 
			{
				PreparedStatement stmt;
				stmt = getPreparedStatementToUpdateResearchColumn(gamer.getCurrentResearchCategory(), gamer.getCurrentResearchID());
				
				stmt.setString(1, dataStorage.getResearchName(gamer.finishCurrentResearch()));
				stmt.setInt(2, gamer.getgID());
				stmt.executeUpdate();
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}
	private PreparedStatement getPreparedStatementToUpdateResearchColumn(String category, int researchID)
	{
		String preparedStatement = "";
		
		if(category.contentEquals("A"))
		{
			if(researchID<8)
			{
				preparedStatement="UPDATE GAMERS SET MISSILE = ?, CR_CAT = null, CR_ID = -1, CR_ENDTIME = null  WHERE GID = ?";
			}
			else if(researchID<16)
			{
				preparedStatement="UPDATE GAMERS SET ION_CANNON = ?, CR_CAT = null, CR_ID = -1, CR_ENDTIME = null  WHERE GID = ?";
			}
			else if(researchID<24)
			{
				preparedStatement="UPDATE GAMERS SET PLASMA_GUN = ?, CR_CAT = null, CR_ID = -1, CR_ENDTIME = null  WHERE GID = ?";
			}
			else if(researchID<30)
			{
				preparedStatement="UPDATE GAMERS SET ARMOR = ?, CR_CAT = null, CR_ID = -1, CR_ENDTIME = null  WHERE GID = ?";
			}
			else if(researchID<36)
			{
				preparedStatement="UPDATE GAMERS SET SHIELD = ?, CR_CAT = null, CR_ID = -1, CR_ENDTIME = null  WHERE GID = ?";
			}
		}
		else if(category.contentEquals("B"))
		{
			if(researchID<8)
			{
				preparedStatement="UPDATE GAMERS SET ECM = ?, CR_CAT = null, CR_ID = -1, CR_ENDTIME = null  WHERE GID = ?";
			}
			else if(researchID<16)
			{
				preparedStatement="UPDATE GAMERS SET ECCM = ?, CR_CAT = null, CR_ID = -1, CR_ENDTIME = null  WHERE GID = ?";
			}
		}
		else if(category.contentEquals("C"))
		{
			if(researchID<10)
			{
				preparedStatement="UPDATE GAMERS SET SHIP_SIZE = ?, CR_CAT = null, CR_ID = -1, CR_ENDTIME = null  WHERE GID = ?";
			}
		}	
		else if(category.contentEquals("-"))
		{
			if(researchID<10)
			{
				preparedStatement="UPDATE GAMERS SET RFLEET = ?, CR_CAT = null, CR_ID = -1, CR_ENDTIME = null  WHERE GID = ?";
			}
		}	
		
		
		try 
		{
			return conn.prepareStatement(preparedStatement);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return null;
		}
	}
	public Gamer setResearch(Gamer gamer)
	{
		
		try 
		{
			Statement sql_stmt = conn.createStatement();
			ResultSet rset = sql_stmt.executeQuery("SELECT SHIPS_SIZE, MISSILE, ION_CANNON, PLASMA_GUN, ARMOR, SHIELD, ECM, ECCM, RFLEET, " +
					"CR_CAT, CR_ID, CR_ENDTIME " +
					"FROM GAMERS WHERE GID="+gamer.getgID());
			rset.next();

			GamerResearch research;
			research = new GamerResearch(rset.getString("SHIPS_SIZE"), 
					rset.getString("MISSILE"), rset.getString("ION_CANNON"), rset.getString("PLASMA_GUN"),
					rset.getString("ARMOR"), rset.getString("SHIELD"), 
					rset.getString("ECM"), rset.getString("ECCM"),
					rset.getInt("RFLEET"));
			
			String category = rset.getString("CR_CAT");
			
			if(category==null)
			{
				research.setCurrentResearch("", -1, new Date(0));
			}
			else
			{
				Timestamp o = rset.getTimestamp("CR_ENDTIME");
				research.setCurrentResearch(rset.getString("CR_CAT"), rset.getInt("CR_ID"), new Date(o.getTime()));
				gamer.setResearchState(research);
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return gamer;
	}
}

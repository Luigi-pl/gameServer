package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import clientServer.Gamer;

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
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			//conn =  DriverManager.getConnection("jdbc:oracle:thin:@localhost:9000:ORA2013", login, password);
			conn = DriverManager.getConnection( "jdbc:h2:~/test", login, password);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
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
				ret=path+"\\"+file_name;
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
	
	
}

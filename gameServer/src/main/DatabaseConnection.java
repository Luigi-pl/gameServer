package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import clientServer.Gamer;
//import java.io.*;
//import java.io.*;


public class DatabaseConnection 
{
	Connection conn;
	public DatabaseConnection(String login, String password) 
	{
		try 
		{
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		  
		try 
		{
			conn =  DriverManager.getConnection("jdbc:oracle:thin:@localhost:9000:ORA2013", login, password);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
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
	public String getFileUpdatePath(int idNumber)
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
			ResultSet rset = sql_stmt.executeQuery("SELECT PATH, FILe_name FROM FILE_UPDATE WHERE ID_NUMBER='"+ idNumber +"'");
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
	
}

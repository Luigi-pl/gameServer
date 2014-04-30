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

package main;

import java.util.ArrayList;

public class Update 
{
	DatabaseConnection dbConnection;
	String updateInfo;
	ArrayList <String> filesUpdatePathWin;
	ArrayList <String> filesUpdatePathLin;
	Update(DatabaseConnection databaseConnection)
	{
		this.dbConnection=databaseConnection;
		updateInfo = dbConnection.getUpdateInfo();
		filesUpdatePathWin = new ArrayList<String>(updateInfo.length());
		filesUpdatePathLin = new ArrayList<String>(updateInfo.length());
		
		for(int i=1; i<updateInfo.length(); i++)
		{
			filesUpdatePathWin.add(dbConnection.getFileUpdatePath(i, "WIN"));
			filesUpdatePathLin.add(dbConnection.getFileUpdatePath(i, "LIN"));
		}
	}
	
	public String getUpdateInfo() 
	{
		return updateInfo;
	}
	public String getFileUpdatePathWin(int i, String system) 
	{
		if(system.equals("WIN"))
		{
			return filesUpdatePathWin.get(i);
		}
		else if(system.equals("LIN"))
		{
			return filesUpdatePathLin.get(i);
		}
		else
		{
			return "";
		}
	}	
}

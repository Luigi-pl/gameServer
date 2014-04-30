package main;

import java.util.ArrayList;

public class Update 
{
	DatabaseConnection dbConnection;
	String updateInfo;
	ArrayList <String> filesUpdatePath;
	Update(DatabaseConnection databaseConnection)
	{
		this.dbConnection=databaseConnection;
		updateInfo = dbConnection.getUpdateInfo();
		filesUpdatePath = new ArrayList<String>(updateInfo.length());
		for(int i=1; i<updateInfo.length(); i++)
		{
			filesUpdatePath.add(dbConnection.getFileUpdatePath(i));
		}
	}
	
}

package clientServer;

import java.net.Socket;

import data.DataStorage;

import object.Gamer;

import main.*;

public class Logic implements Runnable
{
	private Gamer gamer;
	private Connection connection;
	private DatabaseConnection dbConnection;
	private String command;
	private Update update;
	private DataStorage dataStorage;
	public Logic(Socket connectionSocket, DatabaseConnection databaseConnection, Update update, DataStorage dataStorage)
	{
		connection = new Connection(connectionSocket);		
		this.dbConnection = databaseConnection;
		gamer = new Gamer();
		this.update = update;
		this.dataStorage=dataStorage;
	}
	public void run()
	{
		do
		{
			command=connection.readCommand();
			if(command == null)
			{
				break;
			}
			else if(command.equals("SLN"))	//obsluga logowania
			{
				gamer = connection.readGamer(gamer);	//pobranie informacji wyslanych przez gracza
				gamer = dbConnection.setGamerGID(gamer);	//odnalezienie gracza w bazie
				connection.writerGamerState(gamer);	//wyslanie graczowi informacji o powodzeniu logowania
				gamer = dbConnection.setResearch(gamer); //
			}
			else if(command.equals("RUI"))	//obsluga informacji o plikach do zupdate'owania
			{
				connection.launcherRequestForUpdate(update);
			}
			else if(command.equals("RUF"))	//obsluga przesylania plikow do update
			{
				String os = connection.readOS();
				connection.launcherRequestForFile(update, os);
			}
			else if(command.equals("RRS"))	//obsluga zapytania o stan badan technologicznych
			{
				connection.sendResearchState(gamer.getResearchState());
			}
			else if(command.equals("CRS"))
			{
				connection.sendCurrentResearchState(gamer.getCurrentResearchState());
			}
			else if(command.equals("SCR"))
			{
				gamer = connection.readCurrentResearch(gamer, dataStorage);
				connection.sendCurrentResearchState(gamer.getCurrentResearchActionType());
				if(gamer.getCurrentResearchActionType().length()==1)
				{
					dbConnection.updateResearchColumnInDB(gamer, dataStorage);
				}
				else
				{
					gamer.resetCurrentActionAfterError();
				}
			}
			else if(command.equals("LGT"))	//wylogowanie
			{
				break;
			}			
		} while(connection.state());
		
		connection.close();
	}
}

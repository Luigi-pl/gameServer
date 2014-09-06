package clientServer;

import java.net.Socket;

import object.Gamer;

import main.*;

public class Logic implements Runnable
{
	private Gamer gamer;
	private Connection connection;
	private DatabaseConnection dbConnection;
	private String command;
	private Update update;
	public Logic(Socket connectionSocket, DatabaseConnection databaseConnection, Update update)
	{
		connection = new Connection(connectionSocket);		
		this.dbConnection = databaseConnection;
		gamer = new Gamer();
		this.update = update;
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
			}
			else if(command.equals("RUI"))	//obsluga informacji o plikach do zupdate'owania
			{
				connection.launcherRequestForUpdate(update);
			}
			else if(command.equals("RUF"))	//obsluga przesylania plikow do update - do przemyslenia
			{
				String os = connection.readOS();
				connection.launcherRequestForFile(update, os);
			}
			else if(command.equals("LGT"))	//wylogowanie
			{
				break;
			}			
		} while(connection.state());
		
		connection.close();
	}
}

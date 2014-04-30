package clientServer;

import java.net.Socket;

import main.*;

public class Logic implements Runnable
{
	private Gamer gamer;
	private Connection connection;
	private DatabaseConnection dbConnection;
	private String command;
	public Logic(Socket connectionSocket, DatabaseConnection databaseConnection)
	{
		connection = new Connection(connectionSocket);		
		this.dbConnection = databaseConnection;
		gamer = new Gamer();
	}
	public void run()
	{
		do
		{
			command=connection.readCommand();
			
			if(command.equals("SLN"))
			{
				gamer = connection.readGamer(gamer);
				gamer=dbConnection.setGamerGID(gamer);
				connection.writerGamerState(gamer.checkGID());
			}
			if(command.equals("LGT"))
			{
				break;
			}
			
		} while(connection.state());
		
		connection.close();
	}
}

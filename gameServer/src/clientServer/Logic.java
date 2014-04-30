package clientServer;

import java.net.Socket;

import main.*;

public class Logic implements Runnable
{
	private Gamer gamer;
	private Connection connection;
	private DatabaseConnection dbConnection;
	private String command;
	@SuppressWarnings("unused")
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
			
			if(command.equals("SLN"))
			{
				gamer = connection.readGamer(gamer);
				gamer=dbConnection.setGamerGID(gamer);
				connection.writerGamerState(gamer.checkGID());
			}
			else if(command.equals("RUF"))
			{
				
			}
			else if(command.equals("LGT"))
			{
				break;
			}			
		} while(connection.state());
		
		connection.close();
	}
}

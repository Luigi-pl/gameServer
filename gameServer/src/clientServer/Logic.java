package clientServer;

import java.net.Socket;

public class Logic implements Runnable
{
	private Gamer gamer;
	private Connection connection;
	public Logic(Socket connectionSocket)
	{
		connection = new Connection(connectionSocket);		
	}
	public void run()
	{
		
		gamer = connection.readGamer();
		System.out.println(gamer.toString() + connection.toString());
		connection.close();
	}
}

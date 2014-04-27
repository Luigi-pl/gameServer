package clientServer;

import java.net.*;
import java.io.*;

public class Connection
{
	private Socket connectionSocket;
	private BufferedReader inFromClient;
	private DataOutputStream outToClient;
	public Connection(Socket connectionSocket) 
	{
		this.connectionSocket=connectionSocket;
		try 
		{
			inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			outToClient = new DataOutputStream(connectionSocket.getOutputStream());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}	
	private String read()
	{
		try 
		{
			return inFromClient.readLine();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return "";
		}
	}
	public Gamer readGamer()
	{
		Gamer gamer = new Gamer();
		gamer.setLogin(read());
		gamer.setPassword(read());
		return gamer;
	}
	public String toString()
	{
		return "Connected with " + connectionSocket.getInetAddress() + ":" + connectionSocket.getLocalPort();
	}
	private	void write(String sended)
	{
		try 
		{
			outToClient.writeChars(sended);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	public void close()
	{
		try {
			connectionSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}

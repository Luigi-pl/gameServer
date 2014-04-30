package clientServer;

import java.net.*;
import java.io.*;

public class Connection
{
	private Socket connectionSocket;
	private BufferedReader inFromClient;
	private DataOutputStream outToClient;
	private boolean state;
	public Connection(Socket connectionSocket) 
	{
		state=true;
		this.connectionSocket=connectionSocket;
		try 
		{
			inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			outToClient = new DataOutputStream(connectionSocket.getOutputStream());
		} 
		catch (IOException e) 
		{
			state=false;
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
			state=false;
			e.printStackTrace();
			return "";
		}
	}
	public String readCommand()
	{
		String command=read();
		return command;
	}
	public Gamer readGamer(Gamer gamer)
	{
		gamer.setLogin(read());
		gamer.setPassword(read());
		return gamer;
	}
	public String toString()
	{
		return " connected with server from " + connectionSocket.getInetAddress() + ":" + connectionSocket.getLocalPort();
	}
	private	void write(String sended)
	{
		try 
		{
			outToClient.writeBytes(sended);
		} 
		catch (IOException e) 
		{
			state=false;
			e.printStackTrace();
		}
	}
	public void writerGamerState(String state)
	{
		write(state);
	}
	public void close()
	{
		try 
		{
			state=false;
			connectionSocket.close();
		} 
		catch (IOException e) 
		{
			state=false;
			e.printStackTrace();
		}	
	}
	public boolean state()
	{
		return state;
	}

}

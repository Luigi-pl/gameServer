package clientServer;

import java.net.*;
import java.io.*;

public class Connection implements Runnable
{
	private Socket connectionSocket;
	private String loginName;
	private String password;
	private BufferedReader inFromClient;
	private DataOutputStream outToClient;
	public Connection(Socket connectionSocket) throws IOException 
	{
		this.connectionSocket=connectionSocket;
		 inFromClient =
				new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
	}
	public void run()
	{
		try 
		{
			loginName = read();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		try 
		{
			password = read();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		System.out.println(loginName + "\n" + password);
	}
	
	
	public String read() throws IOException
	{
		return inFromClient.readLine();
	}
	public void write(String sended) throws IOException
	{
		outToClient.writeChars(sended);
	}
	public void close() throws IOException
	{
		connectionSocket.close();	
	}

}

package main;

import java.io.*; 
import java.net.*;

import clientServer.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//import clientServer.Connection;

public class Main {

	public static void main(String[] args) throws IOException
	{
		String login;
		String password;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		login = br.readLine();
		password = br.readLine();
		
		DatabaseConnection databaseConnection = new DatabaseConnection(login, password);
		login="";
		password="";
		System.out.println("DB - polaczono");
		
		ExecutorService executor = Executors.newFixedThreadPool(10);
		ServerSocket welcomeSocket = new ServerSocket(2400);
		
		for(int i=0; i<12; i++)
		{
			Socket connectionSocket = welcomeSocket.accept();
			Runnable logic = new Logic(connectionSocket, databaseConnection);
			executor.execute(logic);	
			
		}
		welcomeSocket.close();
		executor.shutdown();
		
	}

}

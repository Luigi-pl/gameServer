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
		ExecutorService executor = Executors.newFixedThreadPool(10);
		ServerSocket welcomeSocket = new ServerSocket(2370);
		
		for(int i=0; i<10; i++)
		{
			Socket connectionSocket = welcomeSocket.accept();
			Runnable connection = new Connection(connectionSocket);
			executor.execute(connection);	
			
		}
		executor.shutdown();
		welcomeSocket.close();
	}

}

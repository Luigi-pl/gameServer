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
		ServerSocket welcomeSocket = new ServerSocket(2400);
		
		for(int i=0; i<1; i++)
		{
			Socket connectionSocket = welcomeSocket.accept();
			Runnable logic = new Logic(connectionSocket);
			executor.execute(logic);	
			
		}
		welcomeSocket.close();
		executor.shutdown();
		
	}

}

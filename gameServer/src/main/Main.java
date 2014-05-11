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
		//pobieranie loginu i hasla, zeby stworzyc polaczenie z baza danych
		String login;
		String password;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		login = br.readLine();
		password = br.readLine();
		
		DatabaseConnection databaseConnection = new DatabaseConnection(login, password);
		
		login="";
		password="";
		System.out.println("BAZA DANYCH - POLACZONO");
		//
		
		//Stworzenie klasy sluzacej do updatowania plikow klienta
		Update update = new Update(databaseConnection);
		System.out.println("KLASA UPDATE - STWORZONA");
		//
		
		//Tworzenie Executora do pracy z watkami
		ExecutorService executor = Executors.newFixedThreadPool(10);
		System.out.println("PULA WATKOW - PRZYDZIELONA");
		//
		
		//Tworzenie socketu nasluchujacego
		ServerSocket welcomeSocket = new ServerSocket(2400);
		System.out.println("SOCKET AKCEPTUJACY - STWORZONY");
		//
		
		
		System.out.println("OCZEKIWANIE POLACZEN");
		for(int i=0; i<100; i++)
		{
			Socket connectionSocket = welcomeSocket.accept();
			Runnable logic = new Logic(connectionSocket, databaseConnection, update);
			executor.execute(logic);	
			
		}
		//Zamykanie sokcetu nasluchujacego
		welcomeSocket.close();
		System.out.println("SOCKET AKCEPTUJACY - Zamkniety");
		//
		
		//Wylaczenie executora
		executor.shutdown();
		System.out.println("EXECUTOR - WYLACZONY");
		//
		System.out.println("ZAMYKANIE - ZAKONCZONE");
	}

}

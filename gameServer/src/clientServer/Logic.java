package clientServer;

import java.net.Socket;

import data.DataStorage;

import object.Gamer;

import main.*;

public class Logic implements Runnable
{
	private Gamer gamer;
	private Connection connection;
	private DatabaseConnection dbConnection;
	private String command;
	private Update update;
	private DataStorage dataStorage;
	public Logic(Socket connectionSocket, DatabaseConnection databaseConnection, Update update, DataStorage dataStorage)
	{
		connection = new Connection(connectionSocket);		
		this.dbConnection = databaseConnection;
		gamer = new Gamer();
		this.update = update;
		this.dataStorage=dataStorage;
	}
	public void run()
	{
		do
		{
			command=connection.readCommand();
			if(command == null)
			{
				break;
			}
			else if(command.equals("SLN"))	//obsluga logowania
			{
				gamer = connection.readGamer(gamer);	//pobranie informacji wyslanych przez gracza
				gamer = dbConnection.setGamerGID(gamer);	//odnalezienie gracza w bazie
				connection.writerGamerState(gamer);	//wyslanie graczowi informacji o powodzeniu logowania
				if(gamer.checkGID().contentEquals("T"))
				{
					gamer = dbConnection.setResearch(gamer); //pobranie z bazy danych informacji o badaniach
					gamer = dbConnection.setFleet(gamer, dataStorage); //pobranie z bazy danych informacji o flocie
				}
			}
			else if(command.equals("RUI"))	//obsluga informacji o plikach do zupdate'owania
			{
				connection.launcherRequestForUpdate(update);
			}
			else if(command.equals("RUF"))	//obsluga przesylania plikow do update
			{
				String os = connection.readOS();
				connection.launcherRequestForFile(update, os);
			}
			else if(command.equals("RRS"))	//obsluga zapytania o stan wszystkich badan technologicznych
			{
				connection.sendResearchState(gamer.getResearchState());
			}
			else if(command.equals("CRS"))	//obsluga zapytania o stan aktualnie badanego
			{
				connection.sendCurrentResearchState(gamer.getCurrentResearchState());
			}
			else if(command.equals("SCR"))	//obsluga komendy konczacej/zaczynajace badanie
			{
				gamer = connection.readCurrentResearch(gamer, dataStorage);
				connection.sendCurrentResearchState(gamer.getCurrentResearchActionType());
				if(gamer.getCurrentResearchActionType().length()==1)	//komenda ma sens
				{
					dbConnection.updateResearchColumnInDB(gamer, dataStorage);
				}
				else //komenda zakonczyla sie bledem
				{
					gamer.resetCurrentActionAfterError();
				}
			}
			else if(command.equals("RFI"))	//obsluga komendy pobierajacej informacje ogolne o flocie
			{
				connection.sendFleetInformation(gamer.getFleetInformation());
			}
			else if(command.equals("RSI"))	//obsluga komendy pobierajacej informacje o pojedynczym okrecie
			{
				connection.sendShipInformation(gamer);
			}
			else if(command.equals("LGT"))	//wylogowanie
			{
				break;
			}			
		} while(connection.state());
		
		connection.close();
	}
}

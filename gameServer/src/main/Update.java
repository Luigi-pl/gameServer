package main;

import java.util.ArrayList;

/** Klasa przechowujaca informacje o wersjach plikow, na bazie ktorej klient pobiera pliki, ktore nie sa u niego najnowsze
 */
public class Update 
{
	DatabaseConnection dbConnection;
	String updateInfo;
	ArrayList <String> filesUpdatePathWin;
	ArrayList <String> filesUpdatePathLin;
	
	/** Metoda odpowiedzialna za pobranie danych o wersjach plikow
	 * @param databaseConnection wskaznik na {@link DatabaseConnection}
	 * */
	Update(DatabaseConnection databaseConnection)
	{
		this.dbConnection=databaseConnection;
		updateInfo = dbConnection.getUpdateInfo();
		filesUpdatePathWin = new ArrayList<String>(updateInfo.length());
		filesUpdatePathLin = new ArrayList<String>(updateInfo.length());
		
		for(int i=1; i<updateInfo.length(); i++)
		{
			filesUpdatePathWin.add(dbConnection.getFileUpdatePath(i, "WIN"));
			filesUpdatePathLin.add(dbConnection.getFileUpdatePath(i, "LIN"));
		}
	}
	
	/** Metoda odpowiedzialna za zwrocenie danych o wersji plikow 
	 * */
	public String getUpdateInfo() 
	{
		return updateInfo;
	}
	
	/** Metoda odpowiedzialna za zwrocenie sciezki do kolejnych plikow
	 * @param i identyfikator kolejnych plikow
	 * @param system na jaki system potrzebne sa dane
	 * */
	public String getFileUpdatePathWin(int i, String system) 
	{
		if(system.equals("WIN"))
		{
			return filesUpdatePathWin.get(i);
		}
		else if(system.equals("LIN"))
		{
			return filesUpdatePathLin.get(i);
		}
		else
		{
			return "";
		}
	}	
}

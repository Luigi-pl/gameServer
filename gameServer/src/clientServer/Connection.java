package clientServer;

import java.net.*;
import java.io.*;

import object.Gamer;

import main.*;

/**Klasa odpowiedzialna za lacznosc miedzy klientem a serwerem*/
public class Connection
{
	private Socket connectionSocket;
	private BufferedReader inFromClient;
	private DataOutputStream outToClient;
	private boolean state;
	
	/**Metoda odpowiedzalna za stworzenie polaczenia z klientem*/
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
	
	/**Metoda odpowiedzalna za odebraniem od klienta jednej linii tekstu*/
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
	/**Metoda odpowiedzialna za wyslanie tekstu do klienta
	 * @param send tekst do wyslania*/
	private	void write(String send)
	{
		try 
		{
			outToClient.writeBytes(send);
		} 
		catch (IOException e) 
		{
			state=false;
			e.printStackTrace();
		}
	}
	
	/**Metoda odpowiedzialna za wysylanie liczb*/
	private void writeSize(int size)
	{
		String sendLength;
		if(size<10)
		{
			sendLength = "00000000" + (new Integer(size)).toString();
		}
		else if(size<100)
		{
			sendLength = "0000000" + (new Integer(size)).toString();
		}
		else if(size<1000)
		{
			sendLength = "000000" + (new Integer(size)).toString();
		}
		else if(size<10000)
		{
			sendLength = "00000" + (new Integer(size)).toString();
		}
		else if(size<100000)
		{
			sendLength = "0000" + (new Integer(size)).toString();
		}
		else if(size<1000000)
		{
			sendLength = "000" + (new Integer(size)).toString();
		}
		else if(size<10000000)
		{
			sendLength = "00" + (new Integer(size)).toString();
		}
		else if(size<100000000)
		{
			sendLength = "0" + (new Integer(size)).toString();
		}
		else
		{
			sendLength = (new Integer(size)).toString();
		}
		write(sendLength);
	}
	
	/**Metoda odpowiedzialna za wyslanie tekstu o okreslonej dlugosc do klienta
	 * @param send tekst do wyslania
	 * @param size dlugosc tekstu*/
	private void writeText(String send, int size)
	{
		writeSize(size);
		write(send);
	}
	public String toString()
	{
		return " connected with server from " + connectionSocket.getInetAddress() + ":" + connectionSocket.getLocalPort();
	}
	/**Metoda odpowiedzialna za wczytanie komendy wyslana przez klienta*/
	public String readCommand()
	{
		return read();
	}
	/**Metoda odpowiedzialna za pobranie od klienta informacji o systemie*/
	public String readOS()
	{
		return read();
	}
	/**Metoda odpowiedzialna za zwrocenie stanu polaczenia*/
	public boolean state()
	{
		return state;
	}
	/**Metoda odpowiedzialna za zamkniecie polaczenia*/
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
	
	/**Metoda odpowiedzialna za wczytanie danych o graczu od klienta*/
	public Gamer readGamer(Gamer gamer)
	{
		gamer.setLogin(read());
		gamer.setPassword(read());
		return gamer;
	}
	/**Metoda odpowiedzialna za wyslanie informacji do klienta o tym czy dany gracz poprawnie sie zalogowal*/
	public void writerGamerState(Gamer gamer)
	{
		write(gamer.checkGID());
	}
	
	/**Metoda odpowiedzialna za wyslanie informacji do klienta o tym, ktore pliki musza zostac przez niego pobrane*/
	public void launcherRequestForUpdate(Update update)
	{
		
		String launcherState=read();
		String serverState=update.getUpdateInfo();
		
		String launcher;
		String game;
		String fileToUpdate="";
		int i;
		for(i=0; i<serverState.length(); i++)
		{
			game=Character.toString(serverState.charAt(i));
			try
			{
				launcher=Character.toString(launcherState.charAt(i));
			}
			catch(Exception e)
			{
				launcher=null;
			}
			
			if(launcher!=null && !game.equals(launcher))
			{
				fileToUpdate=fileToUpdate+"1";
			}
			else if(launcher==null)
			{
				fileToUpdate=fileToUpdate+"1";
			}
			else
			{
				fileToUpdate=fileToUpdate+"0";
			}
		}
		writeText(fileToUpdate, fileToUpdate.length());
	}
	/**Metoda odpowiedzialna za przesylanie kolejnych plikow*/
	public void launcherRequestForFile(Update update, String os)
	{
		
		
		int num;
		String number=read();
		while(!number.equals("X"))
		{
			num = Integer.parseInt(number);
			String fileAndPath = update.getFileUpdatePathWin(num, os);
			writeText(fileAndPath,fileAndPath.length());
			/*otwarcie pliku i wyslanie go*/
			sendFile(fileAndPath);
			number=read();			
		}
	}
	private void sendFile(String nameAndPath)
	{
		FileInputStream fis = null;
	    BufferedInputStream bis = null;
	    OutputStream oS = null;
	    
		File file = new File(nameAndPath);	
		
		writeSize((int)file.length());
		
		byte [] mybytearray  = new byte [(int)file.length()];
		
		
		System.out.println(file.length() + " " + file.getAbsolutePath());
		
		try 
		{
			fis = new FileInputStream(file);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
        bis = new BufferedInputStream(fis);
        try 
        {
			bis.read(mybytearray,0,mybytearray.length);
			oS = connectionSocket.getOutputStream();
			oS.write(mybytearray,0,mybytearray.length);
	        oS.flush();
	        
	        bis.close();
		} 
        catch (IOException e) 
        {
			e.printStackTrace();
		}
	}
}

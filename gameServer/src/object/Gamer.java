package object;

public class Gamer 
{
	private int gID;
	private String login;
	private String password;
	
	public Gamer() 
	{
		this.login="";
		this.password="";
		this.gID=0;
	}
	public Gamer(String login, String password)
	{
		this.login=login;
		this.password=password;
		this.gID=0;
	}
	/**Metoda zwracajaca gid*/
	public int getgID() 
	{
		return gID;
	}
	/** Metoda zwracajaca login*/
	public String getLogin() 
	{
		return login;
	}
	/** Metoda zwracajaca haslo zahashowane (sha3-512)*/
	public String getPassword() 
	{
		return password;
	}
	/**Metoda ustawiajaca gid*/
	public void setgID(int gID) 
	{
		this.gID = gID;
	}
	/**Metoda ustawiajaca login*/
	public void setLogin(String login) 
	{
		this.login = login;
	}
	/**Metoda ustawiajaca haslo*/
	public void setPassword(String password) 
	{
		this.password = password;
	}
	@Override
	public String toString() 
	{
		return gID + ": " + login + " " + password;
	}
	/**Metoda sprawdzajaca czy zaszlo poprawne logowanie*/
	public String checkGID()
	{
		if(gID!=0)
		{
			return "T";
		}
		else
		{
			return "N";
		}
	}
}

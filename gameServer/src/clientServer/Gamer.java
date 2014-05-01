package clientServer;

public class Gamer 
{
	private int gID;
	private String login;
	private String password;
	
	Gamer()
	{
		
	}
	Gamer(String login, String password)
	{
		this.login=login;
		this.password=password;
		this.gID=0;
	}
	public int getgID() 
	{
		return gID;
	}
	public String getLogin() 
	{
		return login;
	}
	public String getPassword() 
	{
		return password;
	}
	public void setgID(int gID) 
	{
		this.gID = gID;
	}
	public void setLogin(String login) 
	{
		this.login = login;
	}
	public void setPassword(String password) 
	{
		this.password = password;
	}
	@Override
	public String toString() 
	{
		return gID + ": " + login + " " + password;
	}
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

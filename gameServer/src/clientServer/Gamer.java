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
	public String toString() {
		return "("+login+")";
	}
	
	
	

}

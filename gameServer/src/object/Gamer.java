package object;

public class Gamer 
{
	private int gID;
	private String login;
	private String password;
	private GamerResearch research;
	private Fleet fleet;
	
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
	/** Metoda zwracajaca haslo zahashowane (sha512)*/
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
	/** */
	public void setResearchState(GamerResearch research)
	{
		this.research = research;
	}
	public void setFleet(Fleet fleet)
	{
		this.fleet = fleet;
	}
	public String getResearchState()
	{
		return this.research.getResearchState();
	}
	public String getCurrentResearchState()
	{
		return this.research.getCurrentResearchState();
	}
	public void setResearchState(String type, String category, int researchId, int timeToFinish)
	{
		this.research.setCurrentResearch(type, category, researchId, timeToFinish);
	}
	public String getCurrentResearchActionType()
	{
		return this.research.getCurrentResearchActionType();
	}
	public String getCurrentResearchCategory() 
	{
		return this.research.getCurrentResearchCategory();
	}
	public int getCurrentResearchID()
	{
		return this.research.getCurrentResearchID();
	}
	public long getCurrentResearchTimestamp()
	{
		return this.research.getCurrentResearchTimestamp();
	}
	public int finishCurrentResearch()
	{
		return this.research.finishCurrentResearch();
	}
	public void resetCurrentActionAfterError()
	{
		this.research.resetCurrentActionAfterError();
	}
	public int getFleetSize()
	{
		return fleet.getFleetSize();
	}
	public String getFleetInformation() 
	{
		return fleet.getFleetInformation();
	}
	public String getDBObjectInformation(int i)
	{
		return fleet.getShipDBObjectInformation(i);
	}
}

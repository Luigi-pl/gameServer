package object;

import java.util.Date;

public class GamerResearch 
{
	private String research;
	private String hullSize;
	
	private String missile;
	private String ionCannon;
	private String plasmaGun;
	
	private String armor;
	private String shield;
	
	private String ECM;
	private String ECCM;
	
	private int fleetSize;
	
	
	private String crActionType; /*poprawne: S - do uruchomienia; E - do zakonczenia; W - oczekujace na kolejne badania
	 							   bledne: SS - proba startu przy uruchomionym innym badaniu; ES - poprzednie nie zostalo zakonczone poprawnie;
	 							   		   DE - proba zakonczenia, kiedy niczego nie ma do zakonczenia;  SE - proba zakonczena przed czasem;
	 							   		   EE - proba zakonczenia po raz kolejny, kiedy poprzednie zakonczenie nie zostalo wykonane*/
	private String crCategory;
	private int crID;
	private Date crEndDate;
	
	
	public GamerResearch(String hullSize, String missile, String ionCannon,
			String plasmaGun, String armor, String shield, String ECM,
			String ECCM, int fleetSize) {
		super();
		this.hullSize = hullSize;
		this.missile = missile;
		this.ionCannon = ionCannon;
		this.plasmaGun = plasmaGun;
		this.armor = armor;
		this.shield = shield;
		this.ECM = ECM;
		this.ECCM = ECCM;
		this.fleetSize = fleetSize;
	}
	public void setCurrentResearch(String crCategory, int crID, Date crEndDate) 
	{
		if(crID==-1)
		{
			this.crActionType="D";
			this.crID=-1;
		}
		else
		{
			this.crActionType="S";
			this.crCategory=crCategory;
			this.crID=crID;
			this.crEndDate=crEndDate;	
		}
	}
	public void setCurrentResearch(String crActionType, String crCategory, int crID, int timeToFinish) 
	{
		if(crActionType.contentEquals("S") && 
				this.crActionType.contentEquals("D"))
		{
			this.crCategory=crCategory;
			this.crID=crID;
			this.crEndDate=new Date();
			this.crEndDate.setTime(this.crEndDate.getTime()+timeToFinish*60*1000);
			this.crActionType = crActionType;
		}
		else if(crActionType.contentEquals("S") && 
				this.crActionType.contentEquals("S"))
		{
			this.crActionType = "SS";
		}
		else if(crActionType.contentEquals("S") && 
				this.crActionType.contentEquals("E"))
		{
			this.crActionType = "ES";
		}
		
		if(crActionType.contentEquals("E") && 
				this.crActionType.contentEquals("D"))
		{
			this.crActionType = "DE";
		}
		else if(crActionType.contentEquals("E") && 
				this.crActionType.contentEquals("S"))
		{
			Date crEndDateTmp=new Date();
			
			if(this.crEndDate!=null && crCategory!=null &&
					crCategory.contentEquals(this.crCategory) && 
					crID == this.crID && 
					(crEndDateTmp.after(this.crEndDate) || crEndDateTmp.equals(this.crEndDate)))
			{
				this.crActionType = crActionType;
			}
			else
			{
				this.crActionType = "SE";
			}
		}
		else if(crActionType.contentEquals("E") && 
				this.crActionType.contentEquals("E"))
		{
			this.crActionType = "EE";
		}
	}
	public String getResearchState()
	{
		research = hullSize + missile + ionCannon + plasmaGun +
				armor + shield +
				ECM + ECCM +
				fleetSize;
		return research;
	}
	public String getCurrentResearchState()
	{
		if(crID==-1)
		{
			return "NONE";
		}
		else
		{
			String toReturn=crCategory;
			if(crID<10)
			{
				toReturn=toReturn+"0"+crID;
			}
			else
			{
				toReturn=toReturn+crID;
			}
			
			Date currentDate = new Date();
			long timeLeft = crEndDate.getTime() - currentDate.getTime();
			timeLeft = timeLeft / 60000;
			
			toReturn=toReturn+timeLeft;
			
			return toReturn;
		}
	}
	public String getCurrentResearchActionType()
	{
		return this.crActionType;
	}
	public String getCurrentResearchCategory() 
	{
		return this.crCategory;
	}
	public int getCurrentResearchID() 
	{
		return this.crID;
	}
	public long getCurrentResearchTimestamp() 
	{
		return this.crEndDate.getTime();
	}
	public int finishCurrentResearch()
	{
		
		int toReturn=this.crID;
		this.crActionType="D";
		
		if(crCategory.contentEquals("A"))
		{
			toReturn = this.crID;
		}
		else if(crCategory.contentEquals("B"))
		{
			toReturn = this.crID+36;
		}
		else if(crCategory.contentEquals("C"))
		{
			toReturn = this.crID+36+16;
		}
		else if(crCategory.contentEquals("-"))
		{
			toReturn = this.crID+36+16+10;
		}
		
		this.crCategory=null;
		this.crID=-1;
		this.crEndDate=null;
		
		return toReturn;
	}
	public void resetCurrentActionAfterError()
	{
		this.crActionType = this.crActionType.substring(0,1);
	}
}

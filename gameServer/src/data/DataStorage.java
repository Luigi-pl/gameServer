package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import object.Ship;

public class DataStorage 
{
	private String localization = System.getProperty("user.dir")+System.getProperty("file.separator")+"data"+System.getProperty("file.separator");
	private List<GameObjectData> listOfObjectData;
	
	public DataStorage()
	{
		listOfObjectData = new ArrayList<GameObjectData>();
		
		
		BufferedReader bufferedReaderMainDataFile = null;
		BufferedReader bufferedReaderDataFile = null;
		 
		try 
		{
			String sBufferMainDataFile;
			int type=0;
			bufferedReaderMainDataFile = new BufferedReader(new FileReader(localization+"data"));
			
 
			while ((sBufferMainDataFile = bufferedReaderMainDataFile.readLine()) != null) 
			{
				
				if(sBufferMainDataFile.charAt(0)=='*')
				{
					type=0;
					
				}
				if(type!=0 && sBufferMainDataFile.charAt(0)!='*')
				{
					try
					{
						String sBufferDataFile;
						bufferedReaderDataFile = new BufferedReader(new FileReader(localization+sBufferMainDataFile));
						
						while ((sBufferDataFile = bufferedReaderDataFile.readLine()) != null) 
						{
							if(1<=type && type<=4)
							{
								interpretLineOfObjectData(sBufferDataFile,type);
								
							}
							else if(type==5)
							{
								//Nie trzeba ladowac type 5 research - poniewaz tym zajmuje sie klient
							}
						}
						
					}
					catch (IOException e) 
					{
						e.printStackTrace();
					} 
					finally 
					{
						try 
						{
							if (bufferedReaderDataFile != null)
							{
								bufferedReaderDataFile.close();
							}
						} 
						catch (IOException ex) 
						{
							ex.printStackTrace();
						}
					}
				}
				if(sBufferMainDataFile.charAt(0)=='*')
		        {
		            if(sBufferMainDataFile.charAt(1)=='A')
		            {
		                type=1;
		            }
		            else if(sBufferMainDataFile.charAt(1)=='B')
		            {
		                type=2;
		            }
		            else if(sBufferMainDataFile.charAt(1)=='C')
		            {
		                type=3;
		            }
		            else if(sBufferMainDataFile.charAt(1)=='D')
		            {
		                type=4;
		            }
		            else if(sBufferMainDataFile.charAt(1)=='E')
		            {
		                type=5;
		            }
		            else if(sBufferMainDataFile.charAt(1)=='*')
		            {
		                type=0;
		            }
		        }
				/*
                buffer[readDataSize-1]=0;
                interpratLineOfData(buffer, type);
		        */
			}
 
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				if (bufferedReaderMainDataFile != null)
				{
					bufferedReaderMainDataFile.close();
				}
			} 
			catch (IOException ex) 
			{
				ex.printStackTrace();
			}
		}
	}
	public Ship returnShip(String ship)
	{
		
		String sShipType = ship.substring(0, 2);
		ShipData shipData =(ShipData) (listOfObjectData.get(52 + Integer.parseInt(sShipType)));
		
		String shipName = shipData.getName();
		String sSquadronSize = ship.substring(2, 6);
		String sShipSize = ship.substring(6, 8);
		String shipEquipment = ship.substring(8);
		
		int nSquadronSize = Integer.parseInt(sSquadronSize);
		int nShipSize = Integer.parseInt(sShipSize);
		
		int attackModifier;
		if(nShipSize<14)
	    {
	        attackModifier=1;
	    }
	    else if(nShipSize<44)
	    {
	        attackModifier=2;
	    }
	    else
	    {
	        attackModifier=3;
	    }
		
		int ownAccuracyModifier=0;
		int enemyAccuracyModifier=0;

		int HPTakenPerAttackPerShip=0;
		int APTakenPerAttackPerShip=0;
		int SPTakenPerAttackPerShip=0;

		int maxShipHP=nSquadronSize*nShipSize*10;
		int maxShipAP=0;
		int maxShipSP=0;

		GameObjectData gameObjectData;
		int smallLetter=0;
		
		for(int i=0; i<shipEquipment.length(); i++)
		{
			int c = shipEquipment.charAt(i);
			if(c<=90)
	        {
	            c-=65;
	            smallLetter=0;
	        }
	        else if(c<=122)
	        {
	            c=-97;
	            smallLetter=1;
	        }
			gameObjectData = listOfObjectData.get(c*2 + smallLetter);
			if(gameObjectData.getType()==1)
	        {
	            HPTakenPerAttackPerShip+=((OffensiveData) gameObjectData).getHitPoints();
	            APTakenPerAttackPerShip+=((OffensiveData) gameObjectData).getArmorPoints();
	            SPTakenPerAttackPerShip+=((OffensiveData) gameObjectData).getShieldPoints();
	        }
			else if(gameObjectData.getType()==2)
	        {
	            maxShipAP+=(nSquadronSize*((DefensiveData) gameObjectData).getArmorPoints());
	            maxShipSP+=(nSquadronSize*((DefensiveData) gameObjectData).getShieldPoints());
	        }
			else if(gameObjectData.getType()==3)
	        {
	            int accuracyModifier = ((AccuracyModifierData) gameObjectData).getAccuracyModifier();
	            if(accuracyModifier<0 && accuracyModifier<enemyAccuracyModifier)
	            {
	                enemyAccuracyModifier=accuracyModifier;
	            }
	            else if(accuracyModifier>0 && accuracyModifier>ownAccuracyModifier)
	            {
	                ownAccuracyModifier=accuracyModifier;
	            }
	        }
		}
		
		Ship shipToReturn = new Ship(sShipType, shipName, 
				attackModifier, nSquadronSize, 
				shipData.getMinSize(), nShipSize, shipData.getMaxSize(), 
				ownAccuracyModifier, enemyAccuracyModifier, 
				HPTakenPerAttackPerShip, APTakenPerAttackPerShip, SPTakenPerAttackPerShip, 
				maxShipHP, maxShipAP, maxShipSP);
		return shipToReturn;
	}
	public int getResearchTime(int i)
	{
		return listOfObjectData.get(i).getTime();
	}
	public String getResearchName(int i)
	{
		return listOfObjectData.get(i).getName();
	}
	private void interpretLineOfObjectData(String data, int type)
	{
		String[] parts = data.split("\t");
		
		if(type==1)
		{
			listOfObjectData.add(
					new OffensiveData(
					parts[0], 
					parts[1], 
					Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), 
					Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), 
					Integer.parseInt(parts[6])));
		}
		else if(type==2)
		{
			listOfObjectData.add(new DefensiveData(parts[0], parts[1], 
					Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), 
					Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), 
					Integer.parseInt(parts[6]) ));
		}
		else if(type==3)
		{
			listOfObjectData.add(new AccuracyModifierData(parts[0], parts[1], 
					Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), 
					Integer.parseInt(parts[4])));
		}
		else if(type==4)
		{
			listOfObjectData.add(new ShipData(parts[0], parts[1], 
					Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), 
					Integer.parseInt(parts[4]), Integer.parseInt(parts[5])));
		}
	}
}

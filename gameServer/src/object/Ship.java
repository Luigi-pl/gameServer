package object;

public class Ship 
{
			public Ship(String shipTypeID, String shipType, 
			int attackModifierFromShipType, int maxSquadronSize,
		    int minShipSize, int actShipSize, int maxShipSize,
		    int ownAccuracyModifier, int enemyAccuracyModifier,
		    int HPTakenPerAttackPerShip, int APTakenPerAttackPerShip, int SPTakenPerAttackPerShip,
		    int maxShipHP, int maxShipAP, int maxShipSP)
		    {
				this.shipTypeID=shipTypeID;
				this.shipType=shipType;
			    this.attackModifierFromShipType=attackModifierFromShipType;
			    this.maxSquadronSize=maxSquadronSize;
			    this.minShipSize=minShipSize;
			    this.actShipSize=actShipSize;
			    this.maxShipSize=maxShipSize;
			    this.ownAccuracyModifier=ownAccuracyModifier;
			    this.enemyAccuracyModifier=enemyAccuracyModifier;
			    this.HPTakenPerAttackPerShip=HPTakenPerAttackPerShip;
			    this.APTakenPerAttackPerShip=APTakenPerAttackPerShip;
			    this.SPTakenPerAttackPerShip=SPTakenPerAttackPerShip;
			    this.maxShipHP=maxShipHP;
			    this.maxShipAP=maxShipAP;
			    this.maxShipSP=maxShipSP;
			    this.curShipHP=maxShipHP;
			    this.curShipAP=maxShipAP;
			    this.curShipSP=maxShipSP;
		    }
			public int getSquadronHPAttackPoints()
		    {
		    	return ((int) (Math.floor(curShipHP/actShipSize/100)) * HPTakenPerAttackPerShip * attackModifierFromShipType);
		    }
			public int getSquadronSPAttackPoints()
		    {
		    	return ((int) (Math.floor(curShipHP/actShipSize/100)) * SPTakenPerAttackPerShip * attackModifierFromShipType);
			}
			public int getSquadronAPAttackPoints()
		    {
		    	return ((int) (Math.floor(curShipHP/actShipSize/100)) * APTakenPerAttackPerShip * attackModifierFromShipType);
		    }
			public void resetAfterBattler()
			{
			    curShipHP=maxShipHP;
			    curShipAP=maxShipAP;
			    curShipSP=maxShipSP;
			}
			public void recalculateAfterEnemyAttack(int HPTaken, int APTaken, int SPTaken)
		    {
		        if(curShipSP>=SPTaken)
		        {
		            curShipSP-=SPTaken;
		        }
		        else
		        {
		            curShipSP=0;
		        }
		        if(curShipSP==0)
		        {
		            if(curShipAP>=APTaken)
		            {
		                curShipAP-=APTaken;
		            }
		            else
		            {
		                curShipAP=0;
		            }
		        }
		        if(curShipSP==0 && curShipAP==0)
		        {
		            if(curShipHP>=HPTaken)
		            {
		                curShipHP-=HPTaken;
		            }
		            else
		            {
		                curShipHP=0;
		            }
		        }
		    }
			public int getOwnAccuracyModifier(int enemyECMModifier)
			{
				return 74+ownAccuracyModifier+enemyECMModifier;
			}
			public int getECMModifier()
			{
				return enemyAccuracyModifier;
			}
			public boolean canStillFight()
		    {
		    	if(curShipHP>0)
		    	{
		    		return true;
		    	}
		    	else
		    	{
		    		return false;
		    	}
		    }
			public int isShipWasCreatedCorrect()
			{
				if(actShipSize>maxShipSize)	
				{
					return actShipSize-maxShipSize;
				}
				else if(minShipSize>actShipSize)	
				{
					return actShipSize-minShipSize;
				}
				else
				{
					return 0;
				}
			}
			public String getShipType()
			{
				return shipType;
			}
			/***/
			public String getShipTypeID()
			{
				return shipTypeID;
			}
			public void setAsDBObject(String asDBObject)
			{
				this.asDBObject = asDBObject;
			}
			public String getAsDBObject()
			{
				return asDBObject;
			}
			
			private String asDBObject;
			
			private String shipTypeID;
			/***/
		    private String shipType;
		    private int attackModifierFromShipType;

		    @SuppressWarnings("unused")
			private int maxSquadronSize;

		    private int minShipSize;
		    private int actShipSize;
		    private int maxShipSize;

		    private int ownAccuracyModifier;
		    private int enemyAccuracyModifier;

		    private int HPTakenPerAttackPerShip;
		    private int APTakenPerAttackPerShip;
		    private int SPTakenPerAttackPerShip;

		    private int maxShipHP;
		    private int maxShipAP;
		    private int maxShipSP;
		    private int curShipHP;
		    private int curShipAP;
		    private int curShipSP;
}

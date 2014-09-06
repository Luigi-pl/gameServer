package data;

public class AccuracyModifierData extends GameObjectData {

	protected int accuracy_modifier;

	public AccuracyModifierData(String id, String name, int time, int cost,
			int accuracy_modifier) 
	{
		super(id, name, time, cost);
		this.accuracy_modifier = accuracy_modifier;
		this.type=3;
	}

	public int getAccuracyModifier() 
	{
		return accuracy_modifier;
	}
	
}

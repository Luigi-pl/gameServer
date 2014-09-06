package data;

public class GameObjectData 
{
	protected String id;
	protected String name;
	protected int time;
	protected int cost;
	protected int type;
	
	
	public GameObjectData(String id, String name, int time, int cost) 
	{
		super();
		this.id = id;
		this.name = name;
		this.time = time;
		this.cost = cost;
	}
	public String getID()
	{
		return id;
	}
	public String getName()
	{
		return name;
	}
	public int getTime()
	{
		return time;
	}
	public int getCost()
	{
		return cost;
	}
	public int getType()
	{
		return type;
	}
	
}

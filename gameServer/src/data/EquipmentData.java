package data;

public class EquipmentData extends GameObjectData 
{
	protected int hit_points;
	protected int armor_points;
	protected int shield_points;
	
	
	public EquipmentData(String id, String name, int time, int cost,
			int hit_points, int armor_points, int shield_points) 
	{
		super(id, name, time, cost);
		this.hit_points = hit_points;
		this.armor_points = armor_points;
		this.shield_points = shield_points;
	}
	public int getHitPoints() 
	{
		return hit_points;
	}
	public int getArmorPoints() 
	{
		return armor_points;
	}
	public int getShieldPoints() 
	{
		return shield_points;
	}	
}

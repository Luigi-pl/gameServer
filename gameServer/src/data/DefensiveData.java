package data;

public class DefensiveData extends EquipmentData {

	public DefensiveData(String id, String name, int time, int cost,
			int hit_points, int armor_points, int shield_points) 
	{
		super(id, name, time, cost, hit_points, armor_points, shield_points);
		this.type=2;
	}

}

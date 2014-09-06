package data;

public class ShipData extends GameObjectData 
{
	protected int min_size;
	protected int max_size;
	
	public ShipData(String idExt, String name, int time, int cost, 
			int min_size, int max_size) 
	{
		super(idExt, name, time, cost);
		this.min_size = min_size;
		this.max_size = max_size;
	}
	
	public int getMinSize() {
		return min_size;
	}

	public int getMaxSize() {
		return max_size;
	}
	
}

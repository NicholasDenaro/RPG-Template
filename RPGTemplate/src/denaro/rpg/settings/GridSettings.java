package denaro.rpg.settings;

public class GridSettings
{
	public final int width;
	public final int height;
	
	public static GridSettings instance;
	
	public static GridSettings instance()
	{
		if(instance == null)
		{
			instance = new GridSettings(1,1);
		}
		
		return instance;
	}
	
	public static GridSettings createInstance(int width, int height)
	{
		instance = new GridSettings(width, height);
		
		return instance;
	}
	
	private GridSettings(int width, int height)
	{
		this.width = width;
		this.height = height;
	}
}

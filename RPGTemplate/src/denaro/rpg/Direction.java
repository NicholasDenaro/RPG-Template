package denaro.rpg;

import denaro.nick.core.Vector;

public class Direction
{
	private static final int EAST_ORD = 0;
	private static final int NORTH_ORD = 1;
	private static final int WEST_ORD = 2;
	private static final int SOUTH_ORD = 3;
	
	public static final Direction EAST = new Direction(EAST_ORD);
	public static final Direction NORTH = new Direction(NORTH_ORD);
	public static final Direction WEST = new Direction(WEST_ORD);
	public static final Direction SOUTH = new Direction(SOUTH_ORD);
	
	public Vector getVector()
	{
		switch(ordinal)
		{
			case EAST_ORD:
				return new Vector(1,0);
			case NORTH_ORD:
				return new Vector(0,-1);
			case WEST_ORD:
				return new Vector(-1,0);
			case SOUTH_ORD:
				return new Vector(0,1);
		}
		
		return null;
	}
	
	public String getName()
	{
		switch(ordinal)
		{
			case EAST_ORD:
				return "East";
			case NORTH_ORD:
				return "North";
			case WEST_ORD:
				return "West";
			case SOUTH_ORD:
				return "South";
		}
		
		return "Null";
	}
	
	private final int ordinal;
	
	private Direction(int ord)
	{
		this.ordinal = ord;
	}
}
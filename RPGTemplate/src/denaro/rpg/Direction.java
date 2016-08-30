package denaro.rpg;

import denaro.nick.core.Vector;

public class Direction
{
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	public static Vector getVector(int direction) throws InvalidDirectionException
	{
		switch(direction)
		{
			case NORTH:
				return new Vector(0,-1);
			case EAST:
				return new Vector(1,0);
			case SOUTH:
				return new Vector(0,1);
			case WEST:
				return new Vector(-1,0);
			default:
				throw new InvalidDirectionException(direction);
		}
	}
}

class InvalidDirectionException extends Exception
{
	private static final long serialVersionUID=1L;

	public InvalidDirectionException(int direction)
	{
		super(String.format("The direction '{0}' is invalid.",direction));
	}
}
package denaro.rpg;

import denaro.nick.core.Location;
import denaro.nick.core.Point;
import denaro.nick.core.Vector;
import denaro.nick.core.entity.Entity;
import denaro.nick.core.entity.EntityEvent;
import denaro.rpg.entity.Moving;
import denaro.rpg.settings.GridSettings;

public class Zone extends Location
{
	private Entity[][] tiles;
	
	public Zone(int width, int height)
	{
		tiles = new Entity[width][height];
	}
	
	public int width()
	{
		return tiles.length;
	}
	
	public int height()
	{
		return tiles[0].length;
	}
	
	public void addEntityToGrid(Entity entity)
	{
		GridSettings grid = RPGEngine.instance().settings().grid;
		int x = (int)entity.x() / grid.width;
		int y = (int)entity.y() / grid.height;
		tiles[x][y] = entity;
		RPGEngine.instance().engine().addEntity(entity, this);
		entity.addListener(this);
	}
	
	public void removeEntityFromGrid(Entity entity)
	{
		GridSettings grid = RPGEngine.instance().settings().grid;
		int x = (int)entity.x() / grid.width;
		int y = (int)entity.y() / grid.height;
		RPGEngine.instance().engine().addEntity(entity, this);
		tiles[x][y] = null;
		entity.removeListener(this);
	}
	
	@Override
	public void entityMove(EntityEvent event)
	{
		super.entityMove(event);
	}
	
	public boolean isTileOpen(Point point)
	{
		if(point.isInBounds(0,0,tiles.length - 1, tiles[0].length - 1))
		{
			return tiles[(int) point.x][(int) point.y] == null;
		}
		
		return false;
	}
	
	public boolean isTileOpen(Point start, Direction direction)
	{
		Vector v = direction.getVector();
		return isTileOpen(new Point(start.x + v.x, start.y + v.y));
	}
	
	public void updateTile(Moving mover, Direction direction)
	{
		Point point = mover.gridPoint();
		Vector v = direction.getVector();
		Point gridPoint = new Point(point.x + v.x, point.y + v.y);
		tiles[(int)gridPoint.x][(int)gridPoint.y] = tiles[(int)point.x][(int)point.y];
		tiles[(int)point.x][(int)point.y] = null;
	}
}

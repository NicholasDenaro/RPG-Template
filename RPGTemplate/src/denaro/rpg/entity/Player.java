package denaro.rpg.entity;

import denaro.nick.core.GameEngine;
import denaro.nick.core.Point;
import denaro.nick.core.Sprite;
import denaro.nick.core.controller.ControllerEvent;
import denaro.nick.core.controller.ControllerListener;
import denaro.nick.core.entity.*;
import denaro.rpg.Direction;
import denaro.rpg.RPGEngine;
import denaro.rpg.controller.KeyList;
import denaro.rpg.settings.GridSettings;
import denaro.rpg.text.DialogBox;

public class Player extends Entity implements ControllerListener, Solid, Moving
{
	private static final int MAX_MOVE_HOLD_TIMER = 5;
	private int moveTimer;
	private Direction direction;
	private int moveHoldTimer;
	
	private boolean[] keys;
	
	public Player(Sprite sprite, double x, double y)
	{
		super(sprite,x,y);
		moveTimer = 0;
		direction = Direction.NORTH;
		moveHoldTimer = MAX_MOVE_HOLD_TIMER;
		keys = new boolean[KeyList.NUM_KEYS];
	}

	@Override
	public void tick()
	{
		move();
		
		if(moveTimer > 0)
		{
			moveTimer--;
			this.moveDelta(direction.getVector());
		}
	}
	
	private void move()
	{
		boolean keyHeld = false;
		for(int i = 0; i < keys.length && !keyHeld; i++)
		{
			keyHeld = keys[i];
		}
		
		if(keyHeld)
		{
			if(moveHoldTimer > 0)
			{
				moveHoldTimer--;
			}
		}
		else
		{
			moveHoldTimer = MAX_MOVE_HOLD_TIMER;
		}
		
		if(moveTimer == 0 && moveHoldTimer == 0)
		{
			direction = updateDirection();
			if(isKeyPressed())
			{				
				if(RPGEngine.instance().zone().isTileOpen(gridPoint(), direction))
				{
					moveTimer = direction == Direction.NORTH || direction == Direction.SOUTH ? GridSettings.instance().height : GridSettings.instance().width;
					RPGEngine.instance().zone().updateTile(this, direction);
				}
			}
		}
	}
	
	private boolean isKeyPressed()
	{
		return keys[KeyList.RIGHT] || keys[KeyList.UP] || keys[KeyList.LEFT] || keys[KeyList.DOWN];
	}
	
	private Direction updateDirection()
	{
		Direction dir = direction;
		
		if(keys[KeyList.RIGHT])
		{
			dir = directionFromCode(KeyList.RIGHT);
		}
		else if(keys[KeyList.UP])
		{
			dir = directionFromCode(KeyList.UP);
		}
		else if(keys[KeyList.LEFT])
		{
			dir = directionFromCode(KeyList.LEFT);
		}
		else if(keys[KeyList.DOWN])
		{
			dir = directionFromCode(KeyList.DOWN);
		}
		
		return dir;
	}

	@Override
	public void focusGained()
	{
	}

	@Override
	public void focusLost()
	{
	}

	private Direction directionFromCode(int code)
	{
		switch(code)
		{
			case KeyList.RIGHT:
				return Direction.EAST;
			case KeyList.UP:
				return Direction.NORTH;
			case KeyList.LEFT:
				return Direction.WEST;
			case KeyList.DOWN:
				return Direction.SOUTH;
			default:
				return null;
		}
	}
	
	@Override
	public void actionPerformed(ControllerEvent event)
	{
		if(event.action() == ControllerEvent.PRESSED)
		{
			keys[event.code()] = true;
			if(event.code() == KeyList.A)
			{
				DialogBox box = new DialogBox("Hello World!`\nHow are you doing this fine and glorious day?`", 32, 240, 64, Sprite.sprite("Characters"));
				GameEngine.instance().addEntity(box, GameEngine.instance().location());
				GameEngine.instance().requestFocus(0, box);
			}
		}
		else if(event.action() == ControllerEvent.RELEASED)
		{
			keys[event.code()] = false;
		}
	}
	
	public Point gridPoint()
	{
		return new Point(this.point().x / GridSettings.instance().width, this.point().y / GridSettings.instance().height);
	}
}

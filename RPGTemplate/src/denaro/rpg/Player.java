package denaro.rpg;

import denaro.nick.core.Sprite;
import denaro.nick.core.controller.ControllerEvent;
import denaro.nick.core.controller.ControllerListener;
import denaro.nick.core.entity.*;
import denaro.rpg.controller.KeyList;
import denaro.rpg.settings.GridSettings;
import denaro.rpg.test.Main;

public class Player extends Entity implements ControllerListener
{
	private static final int MAX_MOVE_HOLD_TIMER = 5;
	private int moveTimer;
	private int direction;
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
			try
			{
				this.moveDelta(Direction.getVector(direction));
			}
			catch(InvalidDirectionException e)
			{
				e.printStackTrace();
			}
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
			if(keys[KeyList.KEY_RIGHT])
			{
				direction = directionFromCode(KeyList.KEY_RIGHT);
				moveTimer = GridSettings.instance().width;
			}
			else if(keys[KeyList.KEY_UP])
			{
				direction = directionFromCode(KeyList.KEY_UP);
				moveTimer = GridSettings.instance().height;
			}
			else if(keys[KeyList.KEY_LEFT])
			{
				direction = directionFromCode(KeyList.KEY_LEFT);
				moveTimer = GridSettings.instance().width;
			}
			else if(keys[KeyList.KEY_DOWN])
			{
				direction = directionFromCode(KeyList.KEY_DOWN);
				moveTimer = GridSettings.instance().height;
			}
		}
	}

	@Override
	public void focusGained()
	{
	}

	@Override
	public void focusLost()
	{
	}

	private int directionFromCode(int code)
	{
		switch(code)
		{
			case KeyList.KEY_RIGHT:
				return Direction.EAST;
			case KeyList.KEY_UP:
				return Direction.NORTH;
			case KeyList.KEY_LEFT:
				return Direction.WEST;
			case KeyList.KEY_DOWN:
				return Direction.SOUTH;
			default:
				return -1;
		}
	}
	
	@Override
	public void actionPerformed(ControllerEvent event)
	{
		if(event.action() == ControllerEvent.PRESSED)
		{
			keys[event.code()] = true;
		}
		else if(event.action() == ControllerEvent.RELEASED)
		{
			keys[event.code()] = false;
		}
	}
	
	
}

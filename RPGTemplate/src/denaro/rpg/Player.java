package denaro.rpg;

import denaro.nick.core.GameEngine;
import denaro.nick.core.Sprite;
import denaro.nick.core.controller.ControllerEvent;
import denaro.nick.core.controller.ControllerListener;
import denaro.nick.core.entity.*;
import denaro.rpg.controller.KeyList;
import denaro.rpg.settings.GridSettings;
import denaro.rpg.test.Main;
import denaro.rpg.text.DialogBox;

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
			if(keys[KeyList.RIGHT])
			{
				direction = directionFromCode(KeyList.RIGHT);
				moveTimer = GridSettings.instance().width;
			}
			else if(keys[KeyList.UP])
			{
				direction = directionFromCode(KeyList.UP);
				moveTimer = GridSettings.instance().height;
			}
			else if(keys[KeyList.LEFT])
			{
				direction = directionFromCode(KeyList.LEFT);
				moveTimer = GridSettings.instance().width;
			}
			else if(keys[KeyList.DOWN])
			{
				direction = directionFromCode(KeyList.DOWN);
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
			case KeyList.RIGHT:
				return Direction.EAST;
			case KeyList.UP:
				return Direction.NORTH;
			case KeyList.LEFT:
				return Direction.WEST;
			case KeyList.DOWN:
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
	
	
}

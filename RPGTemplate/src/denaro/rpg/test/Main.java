package denaro.rpg.test;
import java.io.IOException;

import denaro.nick.core.FixedTickType;
import denaro.nick.core.GameEngine;
import denaro.nick.core.GameEngineException;
import denaro.nick.core.GameFrame;
import denaro.nick.core.Location;
import denaro.nick.core.Point;
import denaro.nick.core.Sprite;
import denaro.nick.core.view.GameView2D;
import denaro.rpg.Player;
import denaro.rpg.controller.KeyboardController;
import denaro.rpg.settings.GridSettings;

public class Main
{
	public static void main(String[] args)
	{
		try
		{
			GameEngine instance = GameEngine.instance(new FixedTickType(60));
			
			GameView2D view = new GameView2D(240, 160, 2, 2);
			
			instance.view(view);
			
			Location loc = new Location();
			instance.location(loc);
			
			GridSettings grid = GridSettings.createInstance(16, 16);
			
			sprites();
			
			instance.controller(new KeyboardController());
			
			Player player = new Player(Sprite.sprite("Player"), grid.width, grid.height);
			
			instance.addControllerListener(player);
			
			instance.addEntity(player,loc);
			
			instance.requestFocus(0, player);
			
			new GameFrame("RPG-Template", instance);
			instance.start();
		}
		catch(GameEngineException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void sprites() throws IOException
	{
		new Sprite("Player", "Player.png", 16, 24, new Point(0, 8));
	}
}

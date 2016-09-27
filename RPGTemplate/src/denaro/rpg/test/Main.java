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
import denaro.rpg.RPGEngine;
import denaro.rpg.Zone;
import denaro.rpg.controller.KeyboardController;
import denaro.rpg.entity.NPC;
import denaro.rpg.entity.Player;
import denaro.rpg.settings.GridSettings;
import denaro.rpg.settings.Settings;
import denaro.rpg.text.DialogBox;

public class Main
{
	public static Player player;
	
	public static void main(String[] args)
	{
		try
		{
			Settings settings = new Settings();
			settings.grid = GridSettings.createInstance(16, 16);
			
			RPGEngine engine = RPGEngine.instance(new FixedTickType(60), settings);
			
			GameView2D view = new GameView2D(240, 160, 2, 2);
			
			engine.engine().view(view);
			
			Zone zone = new Zone(10,10);
			engine.zone(zone);
			
			sprites();
			
			engine.engine().controller(new KeyboardController());
			
			player = new Player(Sprite.sprite("Player"), settings.grid.width, settings.grid.height);
			
			engine.engine().addControllerListener(player);
			
			zone.addEntityToGrid(player);
			
			
			NPC npc = new NPC(Sprite.sprite("NPC"), settings.grid.width * 2, settings.grid.height * 2);
			
			zone.addEntityToGrid(npc);
			
			engine.engine().requestFocus(0, player);
			
			new GameFrame("RPG-Template", engine.engine());
			engine.start();
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
		new Sprite("NPC", "Player.png", 16, 24, new Point(0, 8));
		new Sprite("Characters", "3DS - The Legend of Zelda Ocarina of Time 3D - Font.png", 16, 16, new Point(0,0));
	}
}

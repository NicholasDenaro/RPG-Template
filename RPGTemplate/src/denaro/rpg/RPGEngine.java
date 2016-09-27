package denaro.rpg;

import denaro.nick.core.EngineType;
import denaro.nick.core.GameEngine;
import denaro.nick.core.GameEngineException;
import denaro.rpg.settings.Settings;

public class RPGEngine
{
	private static RPGEngine instance;
	private GameEngine engine;
	private Settings settings;
	
	private RPGEngine(EngineType type, Settings settings) throws GameEngineException
	{
		engine = GameEngine.instance(type);
		this.settings = settings;
	}
	
	public static RPGEngine instance()
	{
		return instance;
	}
	
	public static RPGEngine instance(EngineType type, Settings settings) throws GameEngineException
	{
		if(instance != null)
		{
			throw new GameEngineException("Instance may be initialized only once.");
		}
		
		instance = new RPGEngine(type, settings);
		return instance;
	}
	
	public GameEngine engine()
	{
		return engine;
	}
	
	public void start()
	{
		engine.start();
	}
	
	public void stop()
	{
		engine.stop();
	}
	
	public void zone(Zone zone)
	{
		engine.location(zone);
	}
	
	public Zone zone()
	{
		return (Zone) engine.location();
	}
	
	public Settings settings()
	{
		return settings;
	}
}

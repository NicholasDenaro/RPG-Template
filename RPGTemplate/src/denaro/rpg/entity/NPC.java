package denaro.rpg.entity;

import denaro.nick.core.Sprite;
import denaro.nick.core.entity.Entity;

public class NPC extends Entity implements Solid, Interactable
{

	public NPC(Sprite sprite, double x, double y)
	{
		super(sprite,x,y);
	}

	@Override
	public void tick()
	{
	}
}

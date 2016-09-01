package denaro.rpg.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import denaro.nick.core.GameEngine;
import denaro.nick.core.controller.Controller;
import denaro.nick.core.controller.ControllerEvent;
import denaro.rpg.test.Main;

public class KeyboardController extends Controller implements KeyListener
{
	private static final long serialVersionUID=1L;

	@Override
	public boolean init(GameEngine engine)
	{
		addControllerListener(engine);
		engine.view().addKeyListener(this);
		engine.view().setFocusable(true);
		engine.view().requestFocus();
		return true;
	}

	@Override
	protected void createDefaultKeymap()
	{
		defaultKeymap = new HashMap<Integer,Integer>();
		defaultKeymap.put(KeyEvent.VK_RIGHT,KeyList.RIGHT);
		defaultKeymap.put(KeyEvent.VK_UP,KeyList.UP);
		defaultKeymap.put(KeyEvent.VK_LEFT,KeyList.LEFT);
		defaultKeymap.put(KeyEvent.VK_DOWN,KeyList.DOWN);
		defaultKeymap.put(KeyEvent.VK_X,KeyList.A);
		defaultKeymap.put(KeyEvent.VK_Z,KeyList.B);
	}

	@Override
	public void keyPressed(KeyEvent ke)
	{
		actionPerformed(new ControllerEvent(this,ControllerEvent.PRESSED,defaultKeymap.get(ke.getKeyCode())));
	}

	@Override
	public void keyReleased(KeyEvent ke)
	{
		actionPerformed(new ControllerEvent(this,ControllerEvent.RELEASED,defaultKeymap.get(ke.getKeyCode())));
	}

	@Override
	public void keyTyped(KeyEvent ke)
	{
	}

}

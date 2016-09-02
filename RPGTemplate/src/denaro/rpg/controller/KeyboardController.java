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
	private static boolean keys[];

	@Override
	public boolean init(GameEngine engine)
	{
		addControllerListener(engine);
		engine.view().addKeyListener(this);
		engine.view().setFocusable(true);
		engine.view().requestFocus();
		keys = new boolean[defaultKeymap.size()];
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
		int code = defaultKeymap.get(ke.getKeyCode());
		actionPerformed(new ControllerEvent(this,keys[code] ? ControllerEvent.HELD : ControllerEvent.PRESSED,code));
		keys[code] = true;
	}

	@Override
	public void keyReleased(KeyEvent ke)
	{
		int code = defaultKeymap.get(ke.getKeyCode());
		actionPerformed(new ControllerEvent(this,ControllerEvent.RELEASED,code));
		keys[code] = false;
	}

	@Override
	public void keyTyped(KeyEvent ke)
	{
	}

}

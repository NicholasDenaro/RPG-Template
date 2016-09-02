package denaro.rpg.text;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import denaro.nick.core.GameEngine;
import denaro.nick.core.Sprite;
import denaro.nick.core.controller.ControllerEvent;
import denaro.nick.core.controller.ControllerListener;
import denaro.nick.core.entity.Entity;
import denaro.rpg.controller.KeyList;
import denaro.rpg.test.Main;

public class DialogBox extends Entity implements ControllerListener
{
	private String[] text;
	private int cursor;
	private BufferedImage image;
	private int textWidth;
	private int textHeight;
	private Sprite characters;
	private int topLine;
	private int currentLine;
	private int delay;
	private boolean running;
	private boolean finished;
	private boolean speedup;
	
	public DialogBox(String text, int y, int width, int height, Sprite sprite)
	{
		super(null, 0, y);
		
		characters = sprite;
		textWidth = width / (characters.width() + 1) - 1;
		textHeight = height / (characters.height() + 2) - 1;
		
		this.text = formatText(text);
		
		topLine = 0;
		currentLine = 0;
		cursor = -1;
		delay = 8;
		running = false;
		finished = false;
		speedup = false;
		
		System.out.println(textWidth + ", " + textHeight);
		
		for(String str : this.text)
		{
			System.out.println(str);
		}
		
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setColor(Color.black);
		g.fillRect(0,0,width,height);
	}
	
	private String[] formatText(String text)
	{
		ArrayList<String> arr = new ArrayList<String>();
		for (String str : text.split("\n"))
		{
			arr.add(str);
		}
		
		for (int i = 0; i < arr.size(); i++)
		{
			String str = arr.get(i);
			if(str.length() > textWidth)
			{
				if(str.charAt(textWidth) != ' ')
				{
					int lastIndex = str.lastIndexOf(" ", textWidth);
					if(lastIndex > 0)
					{
						String current = str.substring(0, lastIndex);
						String newLine = str.substring(lastIndex + 1);
						if(i == arr.size() - 1)
						{
							arr.add("");
						}
						String nextString = arr.get(i + 1);
						arr.set(i, current);
						arr.set(i + 1, newLine + nextString);
					}
					else
					{
						String current = str.substring(0, textWidth);
						String newLine = str.substring(textWidth);
						if(i == arr.size() - 1)
						{
							arr.add("");
						}
						String nextString = arr.get(i + 1);
						arr.set(i, current);
						arr.set(i + 1, newLine + nextString);
					}
				}
			}
		}
		
		String[] ret = new String[arr.size()];
		arr.toArray(ret);
		
		return ret;
	}

	@Override
	public void tick()
	{
		if(!running)
		{
			return;
		}
		
		if(currentLine < text.length)
		{
			if(cursor == -1 || (cursor < text[currentLine].length() && text[currentLine].charAt(cursor) != '`'))
			{
				delay -= speedup ? 3 : 1;
				if(delay <= 0)
				{
					cursor++;
					delay = 8;
					
					if(cursor < text[currentLine].length())
					{
						Graphics2D g = image.createGraphics();
						g.drawImage(characters.subimage(text[currentLine].charAt(cursor) - 'A' + 33), cursor * characters.width(), (currentLine - topLine) * characters.height(), null);
					}
				}
			}
			else if(cursor == text[currentLine].length())
			{
				cursor = -1;
				currentLine++;
				if(currentLine < text.length && currentLine - topLine >= image.getHeight() / characters.height())
				{
					shiftUp();
				}
				else if(currentLine == text.length)
				{
					finished = true;
				}
			}
		}
	}
	
	private void shiftUp()
	{
		Graphics2D g = image.createGraphics();
		g.drawImage(image.getSubimage(0,characters.height(),image.getWidth(),image.getHeight() - characters.height()), 0, 0, null);
		g.setColor(Color.black);
		g.fillRect(0,image.getHeight() - characters.height(), image.getWidth(), characters.height());
		topLine++;
	}
	
	private void shiftToTop()
	{
		while(topLine < currentLine)
		{
			shiftUp();
		}
	}
	
	public Image image()
	{
		return image;
	}

	@Override
	public void focusGained()
	{
		running = true;
	}

	@Override
	public void focusLost()
	{
		running = false;
	}

	@Override
	public void actionPerformed(ControllerEvent event)
	{
		if(finished && event.code() == KeyList.A)
		{
			GameEngine.instance().removeEntity(this,GameEngine.instance().location());
			GameEngine.instance().requestFocus(0, Main.player);
		}
		else if(event.code() == KeyList.B)
		{
			speedup = event.action() == ControllerEvent.HELD;
		}
		
		if(event.action() == ControllerEvent.PRESSED && (event.code() == KeyList.A || event.code() == KeyList.B))
		{
			if(currentLine < text.length && cursor < text[currentLine].length() && text[currentLine].charAt(cursor) == '`')
			{
				delay = 8;
				cursor = -1;
				currentLine++;
				shiftToTop();
			}
		}
	}
}

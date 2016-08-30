package denaro.rpg.text;

public class DialogBox
{
	private String text;
	private int cursor;
	private int width;
	private int height;
	
	public DialogBox(String text, int width, int height)
	{
		this.text = text;
		this.width = width;
		this.height = height;
		cursor = 0;
	}
}

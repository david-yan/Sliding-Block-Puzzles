
public class Block
{
	public String configuration;
	public int x1, y1, x2, y2;
	
	public Block(String s)
	{
		configuration = s;
		int firstSpace = s.indexOf(" ");
		int secondSpace = s.indexOf(" ", firstSpace + 1);
		int thirdSpace = s.indexOf(" ", secondSpace + 1);
		x1 = Integer.valueOf(s.substring(0, firstSpace));
		y1 = Integer.valueOf(s.substring(firstSpace + 1, secondSpace));
		x2 = Integer.valueOf(s.substring(secondSpace + 1, thirdSpace));
		y2 = Integer.valueOf(s.substring(thirdSpace + 1));
	}
	
	public Block(int x1, int y1, int x2, int y2)
	{
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.configuration = x1 + " " + y1 + " " + x2 + " " + y2;
	}
	
	public int hashCode()
	{
		return configuration.hashCode();
	}
	
	public String toString()
	{
		return configuration;
	}
}

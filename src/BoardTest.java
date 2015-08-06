import static org.junit.Assert.*;

import org.junit.Test;

public class BoardTest
{

	@Test
	public void testConstructor()
	{
		try
		{
			Board b = new Board("0 4");
			fail();
		}
		catch (Exception e)
		{}
		try
		{
			Board b = new Board("2 0");
			fail();
		}
		catch (Exception e)
		{}
		try
		{
			Board b = new Board("4 5");
			
		}
		catch(Exception e)
		{
			fail();
		}
	}

}

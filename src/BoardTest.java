import static org.junit.Assert.*;

import java.util.LinkedList;

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
			assertEquals(b.toString(), "4 5\n");
			b.addBlock("0 0 1 0");
			assertEquals(b.toString(), "4 5\n0 0 1 0\n");
		}
		catch (Exception e)
		{
			fail();
		}
	}

	@Test
	public void testPossibleMoves()
	{
		try
		{
			Board b = new Board("2 2");
			b.addBlock("0 0 0 0");
			b.finishAddingBlocks();
			LinkedList<String> moves = new LinkedList<String>();
			moves.add("0 0 1 0");
			moves.add("0 0 0 1");
			LinkedList<String> possible = b.possibleMoves();
			assertEquals(moves, possible);

			b = new Board("2 2");
			b.addBlock("0 1 0 1");
			b.finishAddingBlocks();
			moves = b.possibleMoves();
			assertTrue(moves.size() == 2);
			assertTrue(moves.contains("0 1 0 0"));
			assertTrue(moves.contains("0 1 1 1"));

			b = new Board("3 3");
			b.addBlock("1 1 1 1");
			b.addBlock("0 1 0 1");
			b.addBlock("1 2 1 2");
			b.finishAddingBlocks();
			moves = b.possibleMoves();
			assertTrue(moves.size() == 6);
			assertTrue(moves.contains("0 1 0 0"));
			assertTrue(moves.contains("0 1 0 2"));
			assertTrue(moves.contains("1 1 1 0"));
			assertTrue(moves.contains("1 1 2 1"));
			assertTrue(moves.contains("1 2 2 2"));
			assertTrue(moves.contains("1 2 0 2"));

			b = new Board("4 4");
			b.addBlock("1 1 2 2");
			b.addBlock("0 2 0 2");
			b.addBlock("1 3 2 3");
			b.finishAddingBlocks();
			moves = b.possibleMoves();
			assertTrue(moves.size() == 6);
			assertTrue(moves.contains("0 2 0 1"));
			assertTrue(moves.contains("0 2 0 3"));
			assertTrue(moves.contains("1 1 1 0"));
			assertTrue(moves.contains("1 1 2 1"));
			assertTrue(moves.contains("1 3 0 3"));
			assertTrue(moves.contains("1 3 2 3"));
		}
		catch (Exception e)
		{}
	}

	@Test
	public void testMove()
	{
		try
		{
			Board b = new Board("2 2");
			b.addBlock("0 0 0 0");
			b.finishAddingBlocks();
			Board b1 = b.move("0 0 1 0");
			assertEquals(b.toString(), "2 2\n0 0 0 0\n");
			assertEquals(b1.toString(), "2 2\n1 0 1 0");
		}
		catch (Exception e)
		{}
	}
}

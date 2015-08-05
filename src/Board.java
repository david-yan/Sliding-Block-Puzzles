import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.sun.media.sound.InvalidFormatException;

public class Board
{
	/**
	 * 2D configuration of board Each position is index + 1 of block occupying it in
	 * blocks 0 represents empty space
	 */
	private int[][]					board;
	/**
	 * Stored to optimize runtime of possibleMoves
	 */
	private LinkedList<Coordinates>	openSpaces;
	/**
	 * List of all blocks
	 */
	private ArrayList<String>		blocks;
	/**
	 * Used to optimize equals method updated slightly with each call to move
	 */
	private int						hashCode;

	/**
	 * Used to store all empty spaces
	 * 
	 * @author David
	 *
	 */
	private class Coordinates
	{
		int	x;
		int	y;
		Coordinates(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
	}

	/**
	 * Construct Board with dimensions specified by s
	 * 
	 * @param s
	 *            configuration specifications
	 */
	public Board(String s)
	{
		board = new int[Integer.valueOf(s.substring(0, s.indexOf(" ")))][Integer.valueOf(s.substring(s.indexOf(" ") + 1))];
		blocks = new ArrayList<String>();
		hashCode = 1;
		openSpaces = new LinkedList<Coordinates>();
	}

	/**
	 * Used in constructor to add blocks to board
	 * 
	 * @param b
	 *            Block to add
	 * @throws Exception 
	 */
	public void addBlock(String b) throws Exception
	{
		int index = blocks.size();
		blocks.add(b);
		int firstSpace = b.indexOf(" ");
		int secondSpace = b.indexOf(" ", firstSpace + 1);
		int thirdSpace = b.indexOf(" ", secondSpace + 1);
		int x1 = Integer.valueOf(b.substring(0, firstSpace));
		int y1 = Integer.valueOf(b.substring(firstSpace + 1, secondSpace));
		int x2 = Integer.valueOf(b.substring(secondSpace + 1, thirdSpace));
		int y2 = Integer.valueOf(b.substring(thirdSpace + 1));
		if (x2 >= board.length || y2 >= board.length || x1 > x2 || y1 > y2)
			throw new Exception();
		for (int i = x1; i < x2; i++)
			for (int j = y1; j < y2; j++)
			{
				if (board[i][j] != 0)
					throw new Exception();
				board[i][j] = index + 1;
			}
	}

	/**
	 * Uses openSpaces to list all possibleMoves
	 * @return move configurations
	 */
	public List<String> possibleMoves()
	{
		return null;
	}

	/**
	 * Returns whether the hashCodes are equal
	 */
	public boolean equals(Object o)
	{
		return false;
	}

	/**
	 * hashCode based on the configuration of the board. Every configuration
	 * will produce a different hash
	 */
	public int hashCode()
	{
		return 0;
	}

	/**
	 * Move block start to end configuration
	 * 
	 * @param move
	 *            Move configuration. First 2 are starting top-left. Last 2 are
	 *            ending top-left
	 * @return resulting Board
	 */
	public Board move(String move)
	{
		return null;
	}

	/**
	 * Prints the board in the correct format
	 */
	public String toString()
	{
		return null;
	}
}

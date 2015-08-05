import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Board
{
	/**
	 * 2D configuration of board Each position is index of block occupying it in
	 * blocks -1 represents empty space
	 */
	private int[][]					board;
	/**
	 * Stored to optimize runtime of possibleMoves
	 */
	private LinkedList<Coordinates>	openSpaces;
	/**
	 * List of all blocks
	 */
	private ArrayList<Block>		blocks;
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

	}

	/**
	 * Used in constructor to add blocks to board
	 * 
	 * @param b
	 *            Block to add
	 */
	public void addBlock(Block b)
	{

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

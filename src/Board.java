import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import com.sun.media.sound.InvalidFormatException;

public class Board
{
	/**
	 * 2D configuration of board Each position is index + 1 of block occupying
	 * it in blocks 0 represents empty space
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
	 * @throws Exception 
	 */
	public Board(String s) throws Exception
	{
		int x = Integer.valueOf(s.substring(0, s.indexOf(" ")));
		int y = Integer.valueOf(s.substring(s.indexOf(" ") + 1));
		if (x <= 0 || y <= 0)
			throw new Exception();
		board = new int[x][y];
		blocks = new ArrayList<Block>();
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
		Block block = new Block(b);
		blocks.add(block);
		if (block.x2 >= board.length || block.y2 >= board.length || block.x1 > block.x2 || block.y1 > block.y2)
			throw new Exception();
		for (int i = block.x1; i < block.x2; i++)
			for (int j = block.y1; j < block.y2; j++)
			{
				if (board[i][j] != 0)
					throw new Exception();
				board[i][j] = index + 1;
				hashCode += i * 7 + j * 13 + block.hashCode();
			}
	}

	public void finishAddingBlocks()
	{
		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board[0].length; j++)
				if (board[i][j] == 0)
				{
					openSpaces.add(new Coordinates(i, j));
					hashCode -= i * 7 + j * 13;
				}
	}

	/**
	 * Uses openSpaces to list all possibleMoves
	 * 
	 * @return move configurations
	 */
	public List<String> possibleMoves()
	{
		HashSet<Block> adjacent = new HashSet<Block>();
		for (Coordinates c : openSpaces)
		{
			if (c.x + 1 < board.length && board[c.x + 1][c.y] != 0)
				adjacent.add(blocks.get(board[c.x + 1][c.y] - 1));
			if (c.x - 1 >= 0 && board[c.x - 1][c.y] != 0)
				adjacent.add(blocks.get(board[c.x - 1][c.y]));
			if (c.y + 1 < board[0].length && board[c.x][c.y + 1] != 0)
				adjacent.add(blocks.get(board[c.x][c.y + 1]));
			if (c.y - 1 >= 0 && board[c.x][c.y - 1] != 0)
				adjacent.add(blocks.get(board[c.x][c.y - 1]));
		}
		LinkedList<String> moves = new LinkedList<String>();
		for (Block b : adjacent)
			moves.addAll(possibleMoves(b));
		return moves;
	}

	/**
	 * Finds all possible moves of a given block
	 * 
	 * @param block
	 *            Block to find possible moves of
	 * @return list of possible moves
	 */
	public List<String> possibleMoves(Block block)
	{
		LinkedList<String> moves = new LinkedList<String>();
		if (block.x1 - 1 >= 0)
		{
			boolean canAdd = true;
			for (int i = block.y1; i <= block.y2; i++)
				if (board[block.x1 - 1][i] != 0)
				{
					canAdd = false;
					break;
				}
			if (canAdd)
				moves.add(block.x1 + " " + block.y1 + " " + (block.x1 - 1) + " " + block.y1);
		}
		if (block.x2 + 1 < board.length)
		{
			boolean canAdd = true;
			for (int i = block.y1; i <= block.y2; i++)
				if (board[block.x2 + 1][i] != 0)
				{
					canAdd = false;
					break;
				}
			if (canAdd)
				moves.add(block.x1 + " " + block.y1 + " " + (block.x1 + 1) + " " + block.y1);
		}
		if (block.y1 - 1 >= 0)
		{
			boolean canAdd = true;
			for (int i = block.x1; i <= block.x2; i++)
				if (board[i][block.y1 - 1] != 0)
				{
					canAdd = false;
					break;
				}
			if (canAdd)
				moves.add(block.x1 + " " + block.y1 + " " + block.x1 + " " + (block.y1 - 1));
		}
		if (block.y2 + 1 < board[0].length)
		{
			boolean canAdd = true;
			for (int i = block.x1; i <= block.x2; i++)
				if (board[i][block.y2 + 1] != 0)
				{
					canAdd = false;
					break;
				}
			if (canAdd)
				moves.add(block.x1 + " " + block.y1 + " " + block.x1 + " " + (block.y2 + 1));
		}
		return moves;
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
		return hashCode;
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

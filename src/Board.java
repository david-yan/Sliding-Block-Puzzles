import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
	private HashSet<Coordinates>	openSpaces;
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
		public int hashCode()
		{
			return x * 7 + y * 13;
		}
		public boolean equals(Object o)
		{
			return this.hashCode() == o.hashCode();
		}
		public String toString()
		{
			return "(" + x + ", " + y + ")";
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
		openSpaces = new HashSet<Coordinates>();
	}
	
	public Board(int[][] board, ArrayList<Block> blocks, int hashCode, HashSet<Coordinates> openSpaces)
	{
		this.board = board;
		this.blocks = blocks;
		this.hashCode = hashCode;
		this.openSpaces = openSpaces;
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
		for (int i = block.x1; i <= block.x2; i++)
			for (int j = block.y1; j <= block.y2; j++)
			{
				if (board[i][j] != 0)
					throw new Exception();
				board[i][j] = index + 1;
				hashCode += (i + 1) * 7 + (j + 1) * 13 + block.hashCode();
			}
	}

	public void finishAddingBlocks()
	{
		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board[0].length; j++)
				if (board[i][j] == 0)
				{
					openSpaces.add(new Coordinates(i, j));
				}
	}

	/**
	 * Uses openSpaces to list all possibleMoves
	 * 
	 * @return move configurations
	 */
	public LinkedList<String> possibleMoves()
	{
		HashSet<Block> adjacent = new HashSet<Block>();
		for (Coordinates c : openSpaces)
		{
			if (c.x + 1 < board.length && board[c.x + 1][c.y] != 0)
				adjacent.add(blocks.get(board[c.x + 1][c.y] - 1));
			if (c.x - 1 >= 0 && board[c.x - 1][c.y] != 0)
				adjacent.add(blocks.get(board[c.x - 1][c.y] - 1));
			if (c.y + 1 < board[0].length && board[c.x][c.y + 1] != 0)
				adjacent.add(blocks.get(board[c.x][c.y + 1] - 1));
			if (c.y - 1 >= 0 && board[c.x][c.y - 1] != 0)
				adjacent.add(blocks.get(board[c.x][c.y - 1] - 1));
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
	public LinkedList<String> possibleMoves(Block block)
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
		int firstSpace = move.indexOf(" ");
		int secondSpace = move.indexOf(" ", firstSpace + 1);
		int thirdSpace = move.indexOf(" ", secondSpace + 1);
		int x1 = Integer.valueOf(move.substring(0, firstSpace));
		int y1 = Integer.valueOf(move.substring(firstSpace + 1, secondSpace));
		int x2 = Integer.valueOf(move.substring(secondSpace + 1, thirdSpace));
		int y2 = Integer.valueOf(move.substring(thirdSpace + 1));
		int[][] newBoard = board.clone();
		ArrayList<Block> newBlocks = new ArrayList<Block>();
		Collections.copy(blocks, newBlocks);
		HashSet<Coordinates> newOpenSpaces = new HashSet<Coordinates>();
		for (Coordinates c : openSpaces)
			newOpenSpaces.add(new Coordinates(c.x, c.y));
		int newHashCode = hashCode;
		Block toMove = newBlocks.get(newBoard[x1][y1] - 1);
		if (x2 - x1 == 1)
		{
			for (int i = y1; i <= toMove.y2; i++)
			{
				newHashCode -= (x1 + 1) * 7;
				newHashCode += (toMove.x2 + 2) * 7;
				newOpenSpaces.add(new Coordinates(x1, i));
				newOpenSpaces.remove(new Coordinates(toMove.x2 + 1, i));
				newBoard[x1][i] = 0;
				newBoard[toMove.x2 + 1][i] = board[x1][y1];
			}
		}
		else if (x1 - x2 == 1)
		{
			for (int i = y1; i <= toMove.y2; i++)
			{
				newHashCode -= (toMove.x2 + 1) * 7;
				newHashCode += (x2 + 1) * 7;
				newOpenSpaces.add(new Coordinates(toMove.x2, i));
				newOpenSpaces.remove(new Coordinates(x2, i));
				newBoard[toMove.x2][i] = 0;
				newBoard[x2][i] = board[x1][y1];
			}
		}
		else if (y2 - y1 == 1)
		{
			for (int i = x1; i <= toMove.x2; i++)
			{
				newHashCode -= (y1 + 1) * 7;
				newHashCode += (toMove.y2 + 2) * 7;
				newOpenSpaces.add(new Coordinates(y1, i));
				newOpenSpaces.remove(new Coordinates(toMove.y2 + 1, i));
				newBoard[y1][i] = 0;
				newBoard[toMove.y2 + 1][i] = board[x1][y1];
			}
		}
		else if (y1 - y2 == 1)
		{
			for (int i = x1; i <= toMove.x2; i++)
			{
				newHashCode -= (toMove.y2 + 1) * 7;
				newHashCode += (y2 + 1) * 7;
				newOpenSpaces.add(new Coordinates(toMove.y2, i));
				newOpenSpaces.remove(new Coordinates(y2, i));
				newBoard[toMove.y2][i] = 0;
				newBoard[y2][i] = board[x1][y1];
			}
		}
		toMove.x2 += (x2 - x1);
		toMove.y2 += (y2 - y1);
		toMove.x1 = x2;
		toMove.y1 = y2;
		toMove.configuration = toMove.x1 + " " + toMove.y1 + " " + toMove.x2 + " " + toMove.y2;
		return new Board(newBoard, newBlocks, newHashCode, newOpenSpaces);
	}

	/**
	 * Prints the board in the correct format
	 */
	public String toString()
	{
		String toReturn = board.length + " " + board[0].length + "\n";
		for (Block b : blocks)
			toReturn += b.toString() + "\n";
		return toReturn;
	}
}

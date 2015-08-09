import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class OldSolver
{
	/**
	 * Reads files and constructs Board accordingly. Then solves the puzzle
	 * 
	 * @param args
	 *            [0] Board configuration [1] Goal configuration
	 */
	public static void main(String[] args)
	{
		Board b;
		LinkedList<String> goals = new LinkedList<String>();
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(args[0]));
			String line = br.readLine();
			b = new Board(line);
			line = br.readLine();
			while (line != null && !line.equals(""))
			{
				b.addBlock(line);
				line = br.readLine();
			}
			b.finishAddingBlocks();
			br = new BufferedReader(new FileReader(args[1]));
			line = br.readLine();
			while(line != null && !line.equals(""))
			{
				goals.add(line);
				line = br.readLine();
			}
			findPathToGoal(b, goals);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Invalid init and/or goal file.");
		}
	}

	/**
	 * Find the moves to the goal, by process each possible moves and finally
	 * prints the solution to the puzzle using graph traversal
	 * 
	 * @param b
	 *            Tray configuration
	 * @param goal
	 *            Goal configuration
	 */
	public static void findPathToGoal(Board b, LinkedList<String> goals)
	{
		// to put each Node of board configuration onto the Stack to process
		// them
		Stack<Node> fringe = new Stack<Node>();

		// to check if Node of board configuration has already been processed
		HashSet<Board> visited = new HashSet<Board>();

		// initial configuration of the board
		Node firstBoard = new Node(b);
		fringe.push(firstBoard);

		// if path is not found, this stays true
		boolean pathNotFound = true;

		// node that indicates the end Node of the goal
		// to go backwards and print the path that got to goal
		Node endResult = null;

		while (!fringe.isEmpty())
		{

			Node boardToLook = fringe.pop();

			Board result = boardToLook.myItem.move(boardToLook.move);
			if (!visited.contains(result))
			{
				LinkedList<String> temp = new LinkedList();
				temp.addAll(goals);
				// found the path to goal
				if (result.checkGoal(temp))
				{
					pathNotFound = false;
					endResult = boardToLook;
					break;
				}

				// create new board with the new possibleMoves
				List<String> possibleMoves = result.possibleMoves();
				for (String s : possibleMoves)
				{
					fringe.push(new Node(result, s, boardToLook));
				}

				visited.add(result);
			}
		}

		// path was not found
		if (pathNotFound)
		{
			return;
		}

		// path was found
		// print out moves that got to goal
		Stack<Node> inorderPath = new Stack<Node>();
		Node cur = endResult;

		while (cur != null)
		{
			inorderPath.push(cur);
			cur = cur.getPrev();
		}

		while (!inorderPath.isEmpty())
		{
			String move = inorderPath.pop().move;
			if (!move.equals(""))
				System.out.println(move);
		}

	}

	/**
	 * Node that contains the current Board and a pointer to its previous board
	 * configurations
	 * 
	 * @author quangnguyen
	 *
	 */
	private static class Node
	{
		private Board	myItem;
		private String	move;
		private Node	prev;

		public Node(Board b, String m, Node p)
		{
			myItem = b;
			move = m;
			prev = p;
		}

		public Node(Board b, String m)
		{
			myItem = b;
			move = m;
		}

		public Node(Board b)
		{
			myItem = b;
			move = "";
		}

		public int hashCode()
		{
			return myItem.hashCode();
		}

		public Node getPrev()
		{
			return prev;
		}
	}
}

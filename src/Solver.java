import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Solver
{
	/**
	 * Reads files and constructs Board accordingly. Then solves the puzzle
	 * 
	 * @param args
	 *            [0] Board configuration [1] Goal configuration
	 */
	public static void main(String[] args)
	{
		ExperimentalBoard b;
		HashMap<String, String> goals = new HashMap<String, String>();
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(args[0]));
			String line = br.readLine();
			b = new ExperimentalBoard(line);
			line = br.readLine();
			while (line != null && !line.equals(""))
			{
				b.addBlock(line);
				line = br.readLine();
			}
			b.finishAddingBlocks();
			br = new BufferedReader(new FileReader(args[1]));
			line = br.readLine();
			while (line != null && !line.equals(""))
			{
				goals.put(ExperimentalBoard.firstTwoNumbers(line), line);
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
	public static void findPathToGoal(ExperimentalBoard b, HashMap<String, String> goals)
	{
		// to put each Node of board configuration onto the Stack to process
		// them
		Stack<Node> fringe = new Stack<Node>();

		// to check if Node of board configuration has already been processed
		HashSet<ExperimentalBoard> visited = new HashSet<ExperimentalBoard>();

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

			ExperimentalBoard result = boardToLook.myItem.move(boardToLook.move);
			if (!visited.contains(result))
			{
				// found the path to goal
				if (goals.containsKey(ExperimentalBoard.lastTwoNumbers(boardToLook.move)) && result.checkGoal(goals))
				{
					pathNotFound = false;
					endResult = boardToLook;
					break;
				}

				// create new board with the new possibleMoves
				List<String> possibleMoves = result.possibleMoves();
				for (String s : possibleMoves)
				{
					if (boardToLook.move.equals("") || !isReverse(boardToLook.move, s))
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
	
	public static Stack<Node> solve(ExperimentalBoard b, HashMap<String, String> goals)
	{
		// to put each Node of board configuration onto the Stack to process
				// them
				Stack<Node> fringe = new Stack<Node>();

				// to check if Node of board configuration has already been processed
				HashSet<ExperimentalBoard> visited = new HashSet<ExperimentalBoard>();

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

					ExperimentalBoard result = boardToLook.myItem.move(boardToLook.move);
					if (!visited.contains(result))
					{
						// found the path to goal
						if (goals.containsKey(ExperimentalBoard.lastTwoNumbers(boardToLook.move)) && result.checkGoal(goals))
						{
							pathNotFound = false;
							endResult = boardToLook;
							break;
						}

						// create new board with the new possibleMoves
						List<String> possibleMoves = result.possibleMoves();
						for (String s : possibleMoves)
						{
							if (boardToLook.move.equals("") || !isReverse(boardToLook.move, s))
								fringe.push(new Node(result, s, boardToLook));
						}

						visited.add(result);
					}
				}

				// path was not found
				if (pathNotFound)
				{
					return null;
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
				return inorderPath;
	}

	public static boolean isReverse(String prev, String curr)
	{
		return ExperimentalBoard.firstTwoNumbers(prev).equals(ExperimentalBoard.lastTwoNumbers(curr)) && ExperimentalBoard.firstTwoNumbers(curr).equals(ExperimentalBoard.lastTwoNumbers(prev));
	}

	/**
	 * Node that contains the current Board and a pointer to its previous board
	 * configurations
	 * 
	 * @author quangnguyen
	 *
	 */
	static class Node
	{
		ExperimentalBoard	myItem;
		private String				move;
		private Node				prev;

		public Node(ExperimentalBoard b, String m, Node p)
		{
			myItem = b;
			move = m;
			prev = p;
		}

		public Node(ExperimentalBoard b, String m)
		{
			myItem = b;
			move = m;
		}

		public Node(ExperimentalBoard b)
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

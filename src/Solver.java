import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class Solver {
	static final Charset ENCODING = StandardCharsets.UTF_8;

	/**
	 * Reads files and constructs Board accordingly. Then solves the puzzle
	 * 
	 * @param args
	 *            [0] Board configuration [1] Goal configuration
	 */
	public static void main(String[] args) {
		Solver s = new Solver();
		Board b;
		String goal;
		try {
			List<String> lines = Files.readAllLines(Paths.get(args[0]),
					ENCODING);
			b = new Board(lines.get(0));
			for (int i = 1; i < lines.size(); i++) {
				b.addBlock(lines.get(i));
			}
			b.finishAddingBlocks();
			lines = Files.readAllLines(Paths.get(args[1]), ENCODING);
			if (lines.size() != 1) {
				System.out.println("Invalid init and/or goal file.");
				return;
			}
			goal = lines.get(0);
			s.findPathToGoal(b, goal);
		} catch (Exception e) {
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
	public void findPathToGoal(Board b, String goal) {
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

		while (!fringe.isEmpty()) {

			Node boardToLook = fringe.pop();
			endResult = boardToLook;

			Board result = boardToLook.myItem.move(boardToLook.move);
			if (!visited.contains(result)) {
				// found the path to goal
				if (result.checkGoal(goal)) {
					pathNotFound = false;
					break;
				}

				// create new board with the new possibleMoves

				for (String s : result.possibleMoves()) {
					fringe.push(new Node(result, s, boardToLook));
				}

				visited.add(result);
			}
		}

		// path was not found
		if (pathNotFound) {
			System.out.println("print nothing");
			return;
		}

		// path was found
		// print out moves that got to goal
		Stack<Node> inorderPath = new Stack<Node>();
		Node cur = endResult;

		while (cur != null) {
			inorderPath.push(cur);
			cur = cur.getPrev();
		}

		while (!inorderPath.isEmpty()) {
			System.out.println(inorderPath.pop().move);
		}

	}

	/**
	 * Node that contains the current Board and a pointer to its previous board
	 * configurations
	 * 
	 * @author quangnguyen
	 *
	 */
	private class Node {
		private Board myItem;
		private String move;
		private Node prev;

		public Node(Board b, String m, Node p) {
			myItem = b;
			move = m;
			prev = p;
		}

		public Node(Board b, String m) {
			myItem = b;
			move = m;
		}

		public Node(Board b) {
			myItem = b;
			move = "";
		}

		public int hashCode() {
			return myItem.hashCode();
		}

		public Node getPrev() {
			return prev;
		}
	}
}

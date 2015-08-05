import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Solver
{
	static final Charset	ENCODING	= StandardCharsets.UTF_8;

	/**
	 * Reads files and constructs Board accordingly. Then solves the puzzle
	 * 
	 * @param args
	 *            [0] Board configuration [1] Goal configuration
	 */
	public static void main(String[] args)
	{
		Board b;
		String goal;
		try
		{
			List<String> lines = Files.readAllLines(Paths.get(args[0]), ENCODING);
			b = new Board(lines.get(0));
			for (int i = 1; i < lines.size(); i++)
			{
				b.addBlock(lines.get(i));
			}
			lines = Files.readAllLines(Paths.get(args[1]), ENCODING);
			if (lines.size() != 1)
			{
				System.out.println("Invalid init and/or goal file.");
				return;
			}
			goal = lines.get(0);
			solve(b, goal);
		}
		catch (Exception e)
		{
			System.out.println("Invalid init and/or goal file.");
		}
	}

	/**
	 * Prints the solution to the puzzle using graph traversal
	 * 
	 * @param b
	 *            Tray configuration
	 * @param goal
	 *            Goal configuration
	 */
	public static void solve(Board b, String goal)
	{

	}
}

import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Stack;

public class Game
{
	private final static String				background			= "resources/wood_texture.jpg";
	private final static String				DARK_WOOD_TEXTURE	= "resources/dark_wood_texture.jpg";
	private final static Font				HEADING_FONT		= new Font("SansSerif", Font.BOLD, 40);
	private final static Font				NORMAL_FONT			= new Font("SansSerif", Font.BOLD, 32);

	private final static String				puzzle_1			= "hard/blockado";
	private final static String				puzzle_1_goal		= "hard/blockado.goal";
	private final static String				puzzle_2			= "hard/quzzle";
	private final static String				puzzle_2_goal		= "hard/quzzle.goal";
	private final static String				puzzle_3			= "hard/quzzle-killer";
	private final static String				puzzle_3_goal		= "hard/quzzle-killer.goal";
	private final static String				puzzle_4			= "hard/super-century";
	private final static String				puzzle_4_goal		= "hard/super-century.goal";
	private final static String				puzzle_5			= "hard/supercompo";
	private final static String				puzzle_5_goal		= "hard/supercompo.goal";
	private final static String				puzzle_6			= "hard/c22";
	private final static String				puzzle_6_goal		= "hard/22.goal";

	private static double					dimR, dimC;
	private static ExperimentalBoard		board;
	private static HashMap<String, String>	goals;
	private static int						window				= 0;
	private static Stack<Solver.Node>		solution;
	private static boolean					solving;

	public static void loadGame(String b, String goal)
	{
		solving = false;
		goals = new HashMap<String, String>();
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(b));
			String line = br.readLine();
			board = new ExperimentalBoard(line);
			dimR = board.x;
			dimC = board.y;
			line = br.readLine();
			while (line != null && !line.equals(""))
			{
				board.addBlock(line);
				line = br.readLine();
			}
			board.finishAddingBlocks();
			br = new BufferedReader(new FileReader(goal));
			line = br.readLine();
			while (line != null && !line.equals(""))
			{
				goals.put(ExperimentalBoard.firstTwoNumbers(line), line);
				line = br.readLine();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Invalid init and/or goal file.");
		}
	}

	private static void drawBoard()
	{
		StdDrawPlus.setPenColor(StdDrawPlus.LIGHT_BROWN);
		StdDrawPlus.filledRectangle(dimC / 2 + 1, dimR / 2, dimC / 2, dimR / 2);
		StdDrawPlus.setPenColor(StdDrawPlus.BLACK);
		for (int i = 0; i <= dimC; i++)
			StdDrawPlus.filledRectangle(i + 1, dimR / 2, 0.02, dimR / 2);
		for (int i = 0; i <= dimR; i++)
			StdDrawPlus.filledRectangle(3, i, 2, 0.02);
		StdDrawPlus.setFont(NORMAL_FONT);
		StdDrawPlus.setPenColor(StdDrawPlus.WHITE);
		StdDrawPlus.text(3, 6.5, "Board");
		StdDrawPlus.setPenColor(StdDrawPlus.DARK_BROWN);
		for (String block : board.blocks())
		{
			int firstSpace = block.indexOf(" ");
			int secondSpace = block.indexOf(" ", firstSpace + 1);
			int thirdSpace = block.indexOf(" ", secondSpace + 1);
			int x1 = Integer.valueOf(block.substring(0, firstSpace));
			int y1 = Integer.valueOf(block.substring(firstSpace + 1, secondSpace));
			int x2 = Integer.valueOf(block.substring(secondSpace + 1, thirdSpace));
			int y2 = Integer.valueOf(block.substring(thirdSpace + 1));
			StdDrawPlus.filledRectangle((y1 + y2 + 3.0) / 2.0, (x1 + x2 + 1.0) / 2, ((y2 - y1 + 1.0) / 2) - .1, ((x2 - x1 + 1.0) / 2) - .1);
		}
		if (solving)
		{
			if (solution.empty())
				solving = false;
			else
				board = solution.pop().myItem;
		}
	}

	private static void drawGoal()
	{
		StdDrawPlus.setPenColor(StdDrawPlus.LIGHT_BROWN);
		StdDrawPlus.filledRectangle(10, dimR / 2, dimC / 2, dimR / 2);
		StdDrawPlus.setPenColor(StdDrawPlus.BLACK);
		for (int i = 0; i <= dimC; i++)
			StdDrawPlus.filledRectangle(i + 8, dimR / 2, 0.02, dimR / 2);
		for (int i = 0; i <= dimR; i++)
			StdDrawPlus.filledRectangle(10, i, 2, 0.02);
		StdDrawPlus.setFont(NORMAL_FONT);
		StdDrawPlus.setPenColor(StdDrawPlus.WHITE);
		StdDrawPlus.text(10, 6.5, "Goal");
		StdDrawPlus.setPenColor(StdDrawPlus.DARK_BROWN);
		for (String block : goals.values())
		{
			int firstSpace = block.indexOf(" ");
			int secondSpace = block.indexOf(" ", firstSpace + 1);
			int thirdSpace = block.indexOf(" ", secondSpace + 1);
			int x1 = Integer.valueOf(block.substring(0, firstSpace));
			int y1 = Integer.valueOf(block.substring(firstSpace + 1, secondSpace));
			int x2 = Integer.valueOf(block.substring(secondSpace + 1, thirdSpace));
			int y2 = Integer.valueOf(block.substring(thirdSpace + 1));
			StdDrawPlus.filledRectangle((y1 + y2 + 17.0) / 2.0, (x1 + x2 + 1.0) / 2, ((y2 - y1 + 1.0) / 2) - .1, ((x2 - x1 + 1.0) / 2) - .1);
		}
	}

	private static void drawMenu()
	{
		StdDrawPlus.setPenColor(StdDrawPlus.WHITE);
		StdDrawPlus.setFont(HEADING_FONT);
		StdDrawPlus.text(6.5, 6.5, "Sliding Block Game");
		StdDrawPlus.setFont(NORMAL_FONT);
		StdDrawPlus.setPenColor(StdDrawPlus.LIGHT_BROWN);
		StdDrawPlus.picture(3, 5.5, DARK_WOOD_TEXTURE, 4, 1);
		StdDrawPlus.text(3, 5.4, "Load Board 1");
		StdDrawPlus.picture(10, 5.5, DARK_WOOD_TEXTURE, 4, 1);
		StdDrawPlus.text(10, 5.4, "Load Board 2");
		StdDrawPlus.picture(3, 3.5, DARK_WOOD_TEXTURE, 4, 1);
		StdDrawPlus.text(3, 3.4, "Load Board 3");
		StdDrawPlus.picture(10, 3.5, DARK_WOOD_TEXTURE, 4, 1);
		StdDrawPlus.text(10, 3.4, "Load Board 4");
		StdDrawPlus.picture(3, 1.5, DARK_WOOD_TEXTURE, 4, 1);
		StdDrawPlus.text(3, 1.4, "Load Board 5");
		StdDrawPlus.picture(10, 1.5, DARK_WOOD_TEXTURE, 4, 1);
		StdDrawPlus.text(10, 1.4, "Load Board 6");
	}

	private static boolean inSolve(double x, double y)
	{
		return x <= 7.5 && x >= 5.5 && y <= 3.5 && y >= 2.5;
	}

	public static void main(String[] args)
	{
		loadGame(puzzle_1, puzzle_1_goal);
		StdDrawPlus.setCanvasSize(1028, 512);
		StdDrawPlus.setXscale(0, 13);
		StdDrawPlus.setYscale(0, 7);
		while (true)
		{
			StdDrawPlus.picture(6.5, 4.5, background, 15, 15);
			StdDrawPlus.picture(0, 7, DARK_WOOD_TEXTURE, 2, 1);
			StdDrawPlus.setPenColor(StdDrawPlus.WHITE);
			StdDrawPlus.setFont(NORMAL_FONT);
			StdDrawPlus.text(.1, 6.9, "Menu");
			switch (window)
			{
				case 0:
					drawMenu();
					StdDrawPlus.show(10);
					break;
				case 1:
					drawBoard();
					drawGoal();
					StdDrawPlus.picture(6.5, 3, DARK_WOOD_TEXTURE, 2, 1);
					StdDrawPlus.setPenColor(StdDrawPlus.WHITE);
					StdDrawPlus.text(6.5, 2.9, "Solve");
					if (solving)
					{
						StdDrawPlus.text(6.5, 1.9, "Solving...");
						StdDrawPlus.show(10);
					}
					StdDrawPlus.show(10);
					break;
			}
			if (StdDrawPlus.mousePressed())
			{
				double x = StdDrawPlus.mouseX();
				double y = StdDrawPlus.mouseY();
//				System.out.println(x + ", " + y);
				if (x >= -1 && x <= 1 && y >= 6.5 && y <= 7.5)
					window = 0;
				if (window == 1)
				{
					if (inSolve(x, y) && !solving)
					{
						StdDrawPlus.text(6.5, 1.9, "Solving...");
						StdDrawPlus.show(10);
						solution = Solver.solve(board, goals);
						solving = true;
					}
				}
				else if (window == 0)
				{
					if (x <= 5 && x >= 1 && y >= 5 && y <= 6)
					{
						loadGame(puzzle_1, puzzle_1_goal);
						window = 1;
					}
					else if(x <= 5 && x >= 1 && y >= 3 && y <= 4)
					{
						loadGame(puzzle_3, puzzle_3_goal);
						window = 1;
					}
					else if(x <= 5 && x >= 1 && y >= 1 && y <= 2)
					{
						loadGame(puzzle_5, puzzle_5_goal);
						window = 1;
					}
					else if(x <= 12 && x >= 8 && y >= 5 && y <= 6)
					{
						loadGame(puzzle_2, puzzle_2_goal);
						window = 1;
					}
					else if(x <= 12 && x >= 8 && y >= 3 && y <= 4)
					{
						loadGame(puzzle_4, puzzle_4_goal);
						window = 1;
					}
					else if(x <= 12 && x >= 8 && y >= 1 && y <= 3)
					{
						loadGame(puzzle_6, puzzle_6_goal);
						window = 1;
					}
				}
			}
		}
	}

}

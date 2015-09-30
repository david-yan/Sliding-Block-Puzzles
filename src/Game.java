import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Stack;

public class Game
{
	private final static String		background			= "resources/wood_texture.jpg";
	private final static String		DARK_WOOD_TEXTURE	= "resources/dark_wood_texture.jpg";
	private final static Font		HEADING_FONT		= new Font("SansSerif", Font.BOLD, 40);
	private final static Font		NORMAL_FONT			= new Font("SansSerif", Font.BOLD, 32);

	private int						dimR, dimC;
	private ExperimentalBoard		board;
	private HashMap<String, String>	goals;
	private static int				window				= 1;
	private Stack<Solver.Node>		solution;
	private boolean					solving;

	public Game()
	{
		dimR = 6;
		dimC = 4;
		solving = false;
	}

	public Game(String board, String goal)
	{
		dimR = 6;
		dimC = 4;
		solving = false;
		goals = new HashMap<String, String>();
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(board));
			String line = br.readLine();
			this.board = new ExperimentalBoard(line);
			line = br.readLine();
			while (line != null && !line.equals(""))
			{
				this.board.addBlock(line);
				line = br.readLine();
			}
			this.board.finishAddingBlocks();
			br = new BufferedReader(new FileReader(goal));
			line = br.readLine();
			while (line != null && !line.equals(""))
			{
				goals.put(ExperimentalBoard.firstTwoNumbers(line), line);
				line = br.readLine();
			}
			solution = Solver.solve(this.board, goals);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Invalid init and/or goal file.");
		}
	}

	private void drawBoard()
	{
		StdDrawPlus.setPenColor(StdDrawPlus.LIGHT_BROWN);
		StdDrawPlus.filledRectangle(3, 3, 2, 3);
		StdDrawPlus.setPenColor(StdDrawPlus.BLACK);
		for (int i = 0; i <= dimC; i++)
			StdDrawPlus.filledRectangle(i + 1, 3, 0.02, 3);
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

	private void drawGoal()
	{
		StdDrawPlus.setPenColor(StdDrawPlus.LIGHT_BROWN);
		StdDrawPlus.filledRectangle(10, 3, 2, 3);
		StdDrawPlus.setPenColor(StdDrawPlus.BLACK);
		for (int i = 0; i <= dimC; i++)
			StdDrawPlus.filledRectangle(i + 8, 3, 0.02, 3);
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
		StdDrawPlus.text(8.5, 8.5, "Sliding Block Game");
		StdDrawPlus.setFont(NORMAL_FONT);
		StdDrawPlus.setPenColor(StdDrawPlus.LIGHT_BROWN);
		StdDrawPlus.picture(4, 7, DARK_WOOD_TEXTURE, 4, 1);
		StdDrawPlus.text(4, 6.9, "Load Board 1");
		StdDrawPlus.picture(13, 7, DARK_WOOD_TEXTURE, 4, 1);
		StdDrawPlus.text(13, 6.9, "Load Board 2");
	}

	private static boolean inSolve(double x, double y)
	{
		return x <= 7.5 && x >= 5.5 && y <= 3.5 && y >= 2.5;
	}

	public static void main(String[] args)
	{

		Game game = new Game("hard/blockado", "hard/blockado.goal");
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
					game.drawBoard();
					game.drawGoal();
					StdDrawPlus.picture(6.5, 3, DARK_WOOD_TEXTURE, 2, 1);
					StdDrawPlus.setPenColor(StdDrawPlus.WHITE);
					StdDrawPlus.text(6.5, 2.9, "Solve");
					if (game.solving)
						StdDrawPlus.show(90);
					StdDrawPlus.show(10);
					break;
			}
			if (StdDrawPlus.mousePressed())
			{
				double x = StdDrawPlus.mouseX();
				double y = StdDrawPlus.mouseY();
				if (x >= -1 && x <= 1 && y >= 6.5 && y <= 7.5)
					window = 0;
				if (window == 1)
				{
					if (inSolve(x, y))
						game.solving = true;
				}
			}
		}
	}

}

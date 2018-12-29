import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Search for shortest paths between start and end points on a circuit board
 * as read from an input file using either a stack or queue as the underlying
 * search state storage structure and displaying output to the console or to
 * a GUI according to options specified via command-line arguments.
 * 
 * @author mvail, nabilr
 */
public class CircuitTracer {
	private CircuitBoard board;
	private Storage<TraceState> stateStore;
	private ArrayList<TraceState> bestPaths;

	/** launch the program
	 * @param args three required arguments:
	 *  first arg: -s for stack or -q for queue
	 *  second arg: -c for console output or -g for GUI output
	 *  third arg: input file name 
	 */
	public static void main(String[] args) {
		//System.out.println(Arrays.toString(args));
		if (args.length != 3) {
			printUsage();
			System.exit(1);
		}
		try {
			new CircuitTracer(args); //create this with args
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/** Print instructions for running CircuitTracer from the command line. */
	private static void printUsage()
	{
		System.err.println("Usage: java CircuitBoard -s|-q -c|-g file");
		System.exit(1);
	}
	
	/** 
	 * Set up the CircuitBoard and all other components based on command
	 * line arguments.
	 * 
	 * @param args command line arguments passed through from main()
	 */
	private CircuitTracer(String[] args) throws FileNotFoundException, InvalidFileFormatException
	{

		if(args[0].equals("-s"))
		{
			stateStore = new Storage<TraceState>(Storage.DataStructure.stack);
		}
		else if(args[0].equals("-q"))
		{
			stateStore = new Storage<TraceState>(Storage.DataStructure.queue);
		}

		stateStore = new Storage<TraceState>(Storage.DataStructure.queue);
		bestPaths = new ArrayList<TraceState>();

		board = new CircuitBoard(args[2]);

		Point currentPoint;

		if(board.isValid())
		{
			for (int i = 0; i < board.numRows(); i++)
			{
				for (int j = 0; j < board.numCols(); j++)

					if (board.isOpen(i, j))
					{
						currentPoint = new Point(j, i);

						if (this.adjacent(currentPoint, board.getStartingPoint()))
						{
							stateStore.store(new TraceState(board, i, j));
						}
					}
			}

			while (!stateStore.isEmpty())
			{
				TraceState currentTraceState = stateStore.retrieve();

				if (currentTraceState.isComplete())
				{
					if (bestPaths.size() == 0)
						bestPaths.add(currentTraceState);

					for (int i = 0; i < bestPaths.size(); i++)
					{
						if (currentTraceState.pathLength() == bestPaths.get(i).pathLength())
						{
							if (currentTraceState != bestPaths.get(i))
								bestPaths.add(currentTraceState);
						} else if (currentTraceState.pathLength() < bestPaths.get(i).pathLength())
						{
							bestPaths.clear();
							bestPaths.add(currentTraceState);
						}
					}
				} else
				{
					Point currentTraceStateLastPoint = currentTraceState.getPath().get(currentTraceState.pathLength() - 1);

					if (currentTraceState.isOpen(currentTraceStateLastPoint.y + 1, currentTraceStateLastPoint.x))
					{
						stateStore.store(new TraceState(currentTraceState, currentTraceStateLastPoint.y + 1, currentTraceStateLastPoint.x));
					}
					if (currentTraceState.isOpen(currentTraceStateLastPoint.y - 1, currentTraceStateLastPoint.x))
					{
						stateStore.store(new TraceState(currentTraceState, currentTraceStateLastPoint.y - 1, currentTraceStateLastPoint.x));
					}
					if (currentTraceState.isOpen(currentTraceStateLastPoint.y, currentTraceStateLastPoint.x + 1))
					{
						stateStore.store(new TraceState(currentTraceState, currentTraceStateLastPoint.y, currentTraceStateLastPoint.x + 1));
					}
					if (currentTraceState.isOpen(currentTraceStateLastPoint.y, currentTraceStateLastPoint.x - 1))
					{
						stateStore.store(new TraceState(currentTraceState, currentTraceStateLastPoint.y, currentTraceStateLastPoint.x - 1));
					}
				}
			}

			// Removes duplicate from the ArrayList<TraceState> bestPaths
			Set<TraceState> bestPathsSet = new HashSet<TraceState>(bestPaths);
			bestPaths.clear();
			bestPaths.addAll(bestPathsSet);

			for (TraceState traceState : bestPathsSet)
				System.out.println(traceState);

			if (args[1].equals("-c"))
			{
				return;
			} else if (args[1].equals("-g"))
			{
				JFrame frame = new JFrame("Circuit Tracer");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().add(new CircuitTracerGUIPanel(getBestPaths()));
				frame.setPreferredSize(new Dimension(600, 600));
				frame.pack();
				frame.setVisible(true);
			}
		}
	}

	/**
	 * Getter method for bestPaths
	 * @return an ArrayList of best paths
	 */
	public ArrayList<TraceState> getBestPaths()
	{
		return bestPaths;
	}

	/**
	 * Checks if two points are adjacent.
	 * @param p1 first Point
	 * @param p2 second Point
	 * @return true if p1 and p2 are adjacent, else false
	 */
	private boolean adjacent(Point p1, Point p2)
	{
		if (p1.x-1 == p2.x && p1.y == p2.y)
		{
			return true;
		}
		if (p1.x+1 == p2.x && p1.y == p2.y)
		{
			return true;
		}
		if (p1.x == p2.x && p1.y-1 == p2.y)
		{
			return true;
		}
		return p1.x == p2.x && p1.y + 1 == p2.y;
	}
} // class CircuitTracer


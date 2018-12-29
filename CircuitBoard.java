import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Represents a 2D circuit board as read from an input file.
 *  
 * @author mvail, nabilr
 */
public class CircuitBoard {
	private char[][] board;
	private Point startingPoint;
	private Point endingPoint;

	//constants you may find useful
	//private final int ROWS; //initialized in constructor
	//private final int COLS; //initialized in constructor
    private int ROWS;
    private int COLS;
	private final char OPEN = 'O'; //capital 'o'
	private final char CLOSED = 'X';
	private final char TRACE = 'T';
	private final char START = '1';
	private final char END = '2';
	private final String ALLOWED_CHARS = "OXT12";

	private boolean validity;

	/** Construct a CircuitBoard from a given board input file, where the first
	 * line contains the number of rows and columns as ints and each subsequent
	 * line is one row of characters representing the contents of that position.
	 * Valid characters are as follows:
	 *  'O' an open position
	 *  'X' an occupied, unavailable position
	 *  '1' first of two components needing to be connected
	 *  '2' second of two components needing to be connected
	 *  'T' is not expected in input files - represents part of the trace
	 *   connecting components 1 and 2 in the solution
	 * 
	 * @param filename
	 * 		file containing a grid of characters
	 * @throws FileNotFoundException if Scanner cannot read the file
	 * @throws InvalidFileFormatException for any other format or content issue that prevents reading a valid input file
	 */
	public CircuitBoard(String filename) throws FileNotFoundException, NumberFormatException, InvalidFileFormatException
	{
		try
        {
        	validity = true;
            Scanner fileScan = new Scanner(new File(filename)).useDelimiter("\\Z"); //Reads file until EOF;

            String[] gridCount = fileScan.nextLine().split(" ");

            ROWS = Integer.parseInt(gridCount[0]);
            COLS = Integer.parseInt(gridCount[1]);

            String content = fileScan.next();
            fileScan.close();

            // Generates char[][] board from string values based on ROWS and COLS
            String[] lines = content.split("\n");
            String[][] valuesInString = new String[ROWS][COLS];

            for (int i = 0; i < lines.length; i++)
            {
                valuesInString[i] = lines[i].split(" ");
            }

            board = new char[valuesInString.length][valuesInString[0].length];

            for (int i = 0; i < valuesInString.length; i++)
            {
                for (int j = 0; j < valuesInString[i].length; j++)
                {
                    board[i][j] = (valuesInString[i][j]).charAt(0);

                    if(valuesInString[i][j].equals("1"))
                    {
                    	if(startingPoint != null)
                    		throw new InvalidFileFormatException("Two or more starting point exist.");

                        startingPoint = new Point(j, i);
                    }
                    else if(valuesInString[i][j].equals("2"))
                    {
						if(endingPoint != null)
							throw new InvalidFileFormatException("Two or more ending point exist.");

						endingPoint = new Point(j, i);
                    }
                    else if(valuesInString[i][j].equals("O"))
					{
						validity = true;
					}
					else if(valuesInString[i][j].equals("X"))
					{
						validity = true;
					}
					else if(valuesInString[i][j].equals("T"))
					{
						validity = true;
					}
					else if(valuesInString[i][j].equals("N"))
					{
						throw new InvalidFileFormatException("Invalid state found " + "\"" + valuesInString[i][j] + "\"");
					}

                }
            }
            if(startingPoint == null && endingPoint == null)
            	throw new InvalidFileFormatException("Missing starting and ending point.");
            else if(startingPoint == null)
            	throw new InvalidFileFormatException("Missing starting point.");
            else if(endingPoint == null)
            	throw new InvalidFileFormatException("Missing ending point.");


        }
        catch(FileNotFoundException e)
        {
            System.out.println(e.toString());
            validity = false;
        }
	catch(NumberFormatException e)
	{
	    System.out.println("NumberFormatException: Found a string instead of an integer: " + e.getMessage().split(" ")[3]);
	    validity = false;
	}
        catch(InvalidFileFormatException e)
        {
            System.out.println(e.toString());
            validity = false;
        }
        catch(ArrayIndexOutOfBoundsException e)
	{
	    System.out.println("InvalidFileFormatException: The rows and/or the column numbers specified on\\nthe first line mismatches with the provided grid for data.");
	    validity = false;
	}

	}
	
	/** Copy constructor - duplicates original board
	 * 
	 * @param original board to copy
	 */
	public CircuitBoard(CircuitBoard original) {
		board = original.getBoard();
		startingPoint = new Point(original.startingPoint);
		endingPoint = new Point(original.endingPoint);
		ROWS = original.numRows();
		COLS = original.numCols();
	}

	/** utility method for copy constructor
	 * @return copy of board array */
	private char[][] getBoard() {
		char[][] copy = new char[board.length][board[0].length];
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				copy[row][col] = board[row][col];
			}
		}
		return copy;
	}
	
	/** Return the char at board position x,y
	 * @param row row coordinate
	 * @param col col coordinate
	 * @return char at row, col
	 */
	public char charAt(int row, int col) {
		return board[row][col];
	}
	
	/** Return whether given board position is open
	 * @param row
	 * @param col
	 * @return true if position at (row, col) is open 
	 */
	public boolean isOpen(int row, int col) {
		if (row < 0 || row >= board.length || col < 0 || col >= board[row].length) {
			return false;
		}
		return board[row][col] == OPEN;
	}
	
	/** Set given position to be a 'T'
	 * @param row
	 * @param col
	 * @throws OccupiedPositionException if given position is not open
	 */
	public void makeTrace(int row, int col) {
		if (isOpen(row, col)) {
			board[row][col] = TRACE;
		} else {
			throw new OccupiedPositionException("row " + row + ", col " + col + "contains '" + board[row][col] + "'");
		}
	}
	
	/** @return starting Point */
	public Point getStartingPoint() {
		return new Point(startingPoint);
	}
	
	/** @return ending Point */
	public Point getEndingPoint() {
		return new Point(endingPoint);
	}
	
	/** @return number of rows in this CircuitBoard */
	public int numRows() {
		return ROWS;
	}
	
	/** @return number of columns in this CircuitBoard */
	public int numCols() {
		return COLS;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				str.append(board[row][col] + " ");
			}
			str.append("\n");
		}
		return str.toString();
	}

	/**
	 * Getter method for validity
	 * @return True if the board is valid. Otherwise, false.
	 */
	public boolean isValid()
	{
		return validity;
	}

}// class CircuitBoard

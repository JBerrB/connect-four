package com.github.jberr.connectfour.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import com.github.jberr.connectfour.items.Board;
import com.github.jberr.connectfour.items.Cells;
import com.github.jberr.connectfour.items.Players;

public class Game {
	
	public final static int INVALID_CHOICE = -1;
	
	/* Game elements */
	private Board board;
	private boolean isGameEnded;
	private int numerOfMovements;
	
	/* IO */
	private PrintStream out;
	private PrintStream err;
	
	// Constructors
	
	public Game() {
		this.board = new Board();
		this.setGameEnded(false);
		this.numerOfMovements = 0;
		out = System.out;
		err = System.err;
	}
	
	// Main methods
	
	public void initGame() {
		out.println("Welcome to Connect Four!");
	}
	
	public boolean inputChoice(Players player) {
		return inputChoice(player, System.in);
	}
	
	/**
	 * Ask player to choose a column
	 * 
	 * @param player who is going to be asked to choose a column
	 */
	public boolean inputChoice(Players player, InputStream in) {
		
		/* Keep looping until a token is inserted in the board */
		boolean tokenInserted = false;
		while (!tokenInserted) {
			int choice = readPlayerChoice(player, in);
			while (choice == Game.INVALID_CHOICE) {
				choice = readPlayerChoice(player, in);
			}
			tokenInserted = insertToken(player, choice);
			
			/* Check if player won the game */
			if (isWinningMove()) {
				out.println("Game is over. Player " + player.getNumber() + " wins!!");
				setGameEnded(true);
			} else if (noMoreMoves()) {
				out.println("Game is over. It's a draw");
				setGameEnded(true);
			}
		}
		return tokenInserted;
	}
		
	/**
	 * 
	 * @param player making the move
	 * @return choice made by the player. It can be an INVALID choice
	 */
	public int readPlayerChoice(Players player, InputStream in) {
		out.print("Player " + player.getNumber() + " [" + player.getColour() + 
				"] - choose column (1-" + Board.NUM_COLUMNS + "): ");
		
		String input = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		try {
			input = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			err.println(e);
		}
		
		int choice;
		boolean inputIsNumber = true;
		/* Verify if choice is a number. If not, return -1 */
		try {
			choice = Integer.parseInt(input);
		} catch (NumberFormatException exc) {
			out.println("That's not a valid option. Please enter a number for a column (1-" + 
					Board.NUM_COLUMNS + ")");
			choice = INVALID_CHOICE;
			inputIsNumber = false;
		}
		
		/* Verify if choice is in a valid interval [1,NUM_COLUMNS] */
		if (inputIsNumber) {
			if (choice < 1 || choice > Board.NUM_COLUMNS) {
				out.println("That's not a valid option. Please enter a number in the given interval (1-" + 
						Board.NUM_COLUMNS + ")");
				choice = INVALID_CHOICE;
			}
		}
		
		return choice;
	}

	public boolean insertToken(Players player, int choice) {
		boolean tokenInserted = false;
		if (player.getColour().equals("RED")) {
			tokenInserted = board.insertToken(Cells.RED, choice);
		} else if (player.getColour().equals("GREEN")) {
			tokenInserted = board.insertToken(Cells.GREEN, choice);
		}
		if (tokenInserted) {
			numerOfMovements++;
		}
		return tokenInserted;
	}
	
	public int getNumberOfMovements() {
		return numerOfMovements;
	}

	public boolean isWinningMove() {
		boolean isWinningMove = false;
		int latestRow = board.getLastRow();
		int latestColumn = board.getLastColumn();
		
		if (this.getNumberOfMovements() > 0) {
			if (checkHorizontalConnection(latestRow, latestColumn)) {
				isWinningMove = true;
			} else if (checkVerticalConnection(latestRow, latestColumn)) {
				isWinningMove = true;
			} else if (checkDiagonalConnections(latestRow, latestColumn)) {
				isWinningMove = true;
			}
		}
		
		return isWinningMove;
	}
	
	public boolean noMoreMoves() {
		return (board.getEmptyCells() == 0);
	}
	
	public boolean isGameEnded() {
		return isGameEnded;
	}

	public void setGameEnded(boolean isGameEnded) {
		this.isGameEnded = isGameEnded;
	}
	
	public void finishGame() {
		out.println("Game over");
	}
	
	public void printBoard() {
		out.println();
		out.println(board.getPrintableBoard());
	}
	
	// Private methods
	
	// Check horizontal connections

	private boolean checkHorizontalConnection(int latestRow, int latestColumn) {
		int numConnections = 1;
		int cellValue = board.getCell(latestRow, latestColumn).getValue();
		numConnections = numConnections + getLeftConnections(latestRow, latestColumn, cellValue) + 
				getRightConnections(latestRow, latestColumn, cellValue);
		return (numConnections >= 4);
	}
	
	private int getLeftConnections(int latestRow, int latestColumn, int cellValue) {
		int numConnections = 0;
		int column = latestColumn-1;
		boolean connectionBroken = false;
		while (column >=0 && !connectionBroken) {
			if (board.getCell(latestRow, column).getValue() == cellValue) {
				numConnections++;
			} else {
				connectionBroken = true;
			}
			column--;
		}
		
		return numConnections;
	}

	private int getRightConnections(int latestRow, int latestColumn, int cellValue) {
		int numConnections = 0;
		int column = latestColumn+1;
		boolean connectionBroken = false;
		while (column < Board.NUM_COLUMNS && !connectionBroken) {
			if (board.getCell(latestRow, column).getValue() == cellValue) {
				numConnections++;
			} else {
				connectionBroken = true;
			}
			column++;
		}
		
		return numConnections;
	}
	
	// Check vertical connections
	
	private boolean checkVerticalConnection(int latestRow, int latestColumn) {
		int numConnections = 1;
		int cellValue = board.getCell(latestRow, latestColumn).getValue();
		numConnections = numConnections + getDownConnections(latestRow, latestColumn, cellValue) + 
				getUpConnections(latestRow, latestColumn, cellValue);
		return (numConnections >= 4);
	}
	
	private int getDownConnections(int latestRow, int latestColumn, int cellValue) {
		int numConnections = 0;
		int row = latestRow+1;
		boolean connectionBroken = false;
		while (row < Board.NUM_ROWS && !connectionBroken) {
			if (board.getCell(row, latestColumn).getValue() == cellValue) {
				numConnections++;
			} else {
				connectionBroken = true;
			}
			row++;
		}
		
		return numConnections;
	}
	
	private int getUpConnections(int latestRow, int latestColumn, int cellValue) {
		int numConnections = 0;
		int row = latestRow-1;
		boolean connectionBroken = false;
		
		while (row >=0 && !connectionBroken) {
			if (board.getCell(row, latestColumn).getValue() == cellValue) {
				numConnections++;
			} else {
				connectionBroken = true;
			}
			row--;
		}
		
		return numConnections;
	}
	
	// Check diagonal connections
	
	private boolean checkDiagonalConnections(int latestRow, int latestColumn) {
		boolean fourConnected = false;
		int numConnections = 1;
		int cellValue = board.getCell(latestRow, latestColumn).getValue();
		numConnections = numConnections + getLeftDownConnections(latestRow, latestColumn, cellValue) + 
				getRightUpConnections(latestRow, latestColumn, cellValue);
		fourConnected = (numConnections >= 4);
		if (!fourConnected) {
			numConnections = 1;
			numConnections = numConnections + getRightDownConnections(latestRow, latestColumn, cellValue) + 
					getLeftUpConnections(latestRow, latestColumn, cellValue);
			fourConnected = (numConnections >= 4);
		}
		return fourConnected;
	}
	
	private int getLeftDownConnections(int latestRow, int latestColumn, int cellValue) {
		int numConnections = 0;
		int column = latestColumn-1;
		int row = latestRow+1;
		boolean connectionBroken = false;
		while (column >=0 && row < Board.NUM_ROWS && !connectionBroken) {
			if (board.getCell(row, column).getValue() == cellValue) {
				numConnections++;
			} else {
				connectionBroken = true;
			}
			column--;
			row++;
		}
		
		return numConnections;
	}
	
	private int getRightUpConnections(int latestRow, int latestColumn, int cellValue) {
		int numConnections = 0;
		int column = latestColumn+1;
		int row = latestRow-1;
		boolean connectionBroken = false;
		while (column < Board.NUM_COLUMNS && row >=0 && !connectionBroken) {
			if (board.getCell(row, column).getValue() == cellValue) {
				numConnections++;
			} else {
				connectionBroken = true;
			}
			column++;
			row--;
		}
		
		return numConnections;
	}

	private int getLeftUpConnections(int latestRow, int latestColumn, int cellValue) {
		int numConnections = 0;
		int column = latestColumn-1;
		int row = latestRow-1;
		boolean connectionBroken = false;
		while (column >=0 && row >= 0 && !connectionBroken) {
			if (board.getCell(row, column).getValue() == cellValue) {
				numConnections++;
			} else {
				connectionBroken = true;
			}
			column--;
			row--;
		}
		
		return numConnections;
	}

	private int getRightDownConnections(int latestRow, int latestColumn, int cellValue) {
		int numConnections = 0;
		int column = latestColumn+1;
		int row = latestRow+1;
		boolean connectionBroken = false;
		while (column < Board.NUM_COLUMNS && row < Board.NUM_ROWS && !connectionBroken) {
			if (board.getCell(row, column).getValue() == cellValue) {
				numConnections++;
			} else {
				connectionBroken = true;
			}
			column++;
			row++;
		}
		
		return numConnections;
	}
}

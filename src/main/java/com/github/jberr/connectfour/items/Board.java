package com.github.jberr.connectfour.items;

import java.util.Arrays;

public class Board {
	
	public static final int NUM_ROWS = 6;
	public static final int NUM_COLUMNS = 7;
	
	private Cells[][] grid;
	private int[] tokensInRows;
	private int lastColumn;
	private int lastRow;

	public Board() {
		
		/* Initialize board cells */
		grid = new Cells[NUM_ROWS][NUM_COLUMNS]; // matrix[rows][columns]
		for (Cells[] completeRow : grid) {
			Arrays.fill(completeRow, Cells.EMPTY);
		}
		   
		/* Initialize token counters */
		this.tokensInRows = new int[NUM_COLUMNS];
		Arrays.fill(tokensInRows, 0);
	}
		
	public int getEmptyCells() {
		int emptySquares = NUM_ROWS * NUM_COLUMNS;
		for (int i=0; i<tokensInRows.length; i++) {
			emptySquares = emptySquares - tokensInRows[i];
		}
		return emptySquares;
	}
		
	public String getPrintableBoard() {
		String boardAsStr = "";
		final String eol = System.getProperty("line.separator");
		
		for (int row=0; row < grid.length; row++) {
		    for (int col=0; col < grid[row].length; col++) {
		    		Cells cell = grid[row][col];
		    		boardAsStr = boardAsStr.concat("| " + cell.getPrintValue() + " ");
		    }
		    boardAsStr = boardAsStr.concat("|");
		    boardAsStr = boardAsStr.concat(eol);
		}
		
		return boardAsStr;
	}

	public boolean insertToken(Cells cell, int choice) {
		boolean tokenInserted = false;
		
		int column = choice-1;
		int row = NUM_ROWS - (tokensInRows[column]+1);
		
		if (row >= 0 && row < NUM_ROWS) {
			grid[row][column] = cell;
			tokensInRows[column]++;
			lastColumn = column;
			lastRow = row;
			tokenInserted = true;
		}
		
		return tokenInserted;
	}
	
	public int getLastColumn() {
		return lastColumn;
	}

	public int getLastRow() {
		return lastRow;
	}
	
	public Cells getCell(int row, int column) {
		Cells cell = null;
		if (row >=0 && row < NUM_ROWS) {
			if (column >=0 && column < NUM_COLUMNS) {
				cell = grid[row][column];
			}
		}
		return cell;
	}
}

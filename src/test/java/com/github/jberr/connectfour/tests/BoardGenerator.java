package com.github.jberr.connectfour.tests;

import com.github.jberr.connectfour.items.Board;

public class BoardGenerator {
	
	private final static String EOL = System.getProperty("line.separator");

	/**
	 * 
	 * @return empty board
	 * 
	 * |   |   |   |   |   |   |   |
	 * |   |   |   |   |   |   |   |
	 * |   |   |   |   |   |   |   |
	 * |   |   |   |   |   |   |   |
	 * |   |   |   |   |   |   |   |
	 * |   |   |   |   |   |   |   |
	 */
	public static String getEmptyBoard() {
		String expectedBoard = "";
		for (int i=0; i<Board.NUM_ROWS; i++) {
			expectedBoard = expectedBoard.concat("|   |   |   |   |   |   |   |");
			expectedBoard = expectedBoard.concat(EOL);
		}
		return expectedBoard;
	}
	
	/**
	 * 
	 * @return board
	 * 
	 * |   |   |   |   |   |   |   |
	 * |   |   |   |   |   |   |   |
	 * |   |   |   |   |   |   |   |
	 * |   |   |   |   |   |   |   |
	 * |   |   |   |   |   |   |   |
	 * | R | G |   |   |   |   |   |
	 */
	public static String getBoardWith2Tokens() {
		String expectedBoard = "";
		for (int i=0; i<Board.NUM_ROWS-1; i++) {
			expectedBoard = expectedBoard.concat("|   |   |   |   |   |   |   |");
			expectedBoard = expectedBoard.concat(EOL);
		}
		expectedBoard = expectedBoard.concat("| R | G |   |   |   |   |   |");
		expectedBoard = expectedBoard.concat(EOL);
		return expectedBoard;
	}

	/**
	 * 
	 * @return board
	 * 
	 * |   |   |   |   |   |   |   |
	 * |   |   |   |   |   |   |   |
	 * |   |   |   |   |   |   |   |
	 * |   |   |   |   |   |   |   |
	 * |   |   |   |   |   |   |   |
	 * | R | G | R | G | R | G | R |
	 */
	public static String getBoardWithCompleteRow() {
		String expectedBoard = "";
		for (int i=0; i<Board.NUM_ROWS-1; i++) {
			expectedBoard = expectedBoard.concat("|   |   |   |   |   |   |   |");
			expectedBoard = expectedBoard.concat(EOL);
		}
		expectedBoard = expectedBoard.concat("| R | G | R | G | R | G | R |");
		expectedBoard = expectedBoard.concat(EOL);
		return expectedBoard;
	}

	/**
	 * 
	 * @return board
	 * 
	 * | G |   |   |   |   |   |   |
	 * | R |   |   |   |   |   |   |
	 * | G |   |   |   |   |   |   |
	 * | R |   |   |   |   |   |   |
	 * | G |   |   |   |   |   |   |
	 * | R |   |   |   |   |   |   |
	 */
	public static String getBoardWithCompleteColumn() {
		String expectedBoard = "";
		for (int i=0; i<Board.NUM_ROWS; i++) {
			if (i % 2 == 0) {
				expectedBoard = expectedBoard.concat("| G |   |   |   |   |   |   |");
			} else {
				expectedBoard = expectedBoard.concat("| R |   |   |   |   |   |   |");
			}
			expectedBoard = expectedBoard.concat(EOL);
		}
		return expectedBoard;
	}
}

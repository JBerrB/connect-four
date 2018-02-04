package com.github.jberr.connectfour.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import com.github.jberr.connectfour.items.Board;
import com.github.jberr.connectfour.items.Cells;

public class BoardTest {
		
	private Board board;
	
	@Before
	public void setupTest() {
		board = new Board();
	}
	
	@Test
	public void test_getEmptyCells_BeginningGame() {
		int expectedEmptyCells = Board.NUM_COLUMNS * Board.NUM_ROWS;
		
		assertEquals(expectedEmptyCells, board.getEmptyCells());
	}
	
	@Test
	public void test_getPrintableBoard_BeginningGame() {
		String expectedBoard = BoardGenerator.getEmptyBoard();
		printBoard("Expected Board:", expectedBoard);
		
		String actualBoard = board.getPrintableBoard();
		printBoard("Actual Board:", actualBoard);
		
		assertEquals(expectedBoard, board.getPrintableBoard());
	}
	
	@Test
	public void test_getEmptyCells_PlayersMake2Movements() {
		int expectedEmptyCells = Board.NUM_COLUMNS * Board.NUM_ROWS - 2;
		board.insertToken(Cells.RED, 1);
		board.insertToken(Cells.GREEN, 2);
		
		assertEquals(expectedEmptyCells, board.getEmptyCells());
	}
	
	@Test
	public void test_getPrintableBoard_PlayersMake2Movements() {
		String expectedBoard = BoardGenerator.getBoardWith2Tokens();
		printBoard("Expected Board:", expectedBoard);
		
		/* Get actual board */
		board.insertToken(Cells.RED, 1);
		board.insertToken(Cells.GREEN, 2);
		String actualBoard = board.getPrintableBoard();
		printBoard("Actual Board:", actualBoard);
		
		assertEquals(expectedBoard, board.getPrintableBoard());
	}
	
	@Test
	public void test_getPrintableBoard_RowComplete() {
		String expectedBoard = BoardGenerator.getBoardWithCompleteRow();
		printBoard("Expected Board:", expectedBoard);
		
		/* Get actual board */
		board.insertToken(Cells.RED, 1);
		board.insertToken(Cells.GREEN, 2);
		board.insertToken(Cells.RED, 3);
		board.insertToken(Cells.GREEN, 4);
		board.insertToken(Cells.RED, 5);
		board.insertToken(Cells.GREEN, 6);
		board.insertToken(Cells.RED, 7);
		
		String actualBoard = board.getPrintableBoard();
		printBoard("Actual Board:", actualBoard);
		
		assertEquals(expectedBoard, board.getPrintableBoard());
	}
	
	@Test
	public void test_getPrintableBoard_ColumnComplete() {
		String expectedBoard = BoardGenerator.getBoardWithCompleteColumn();
		printBoard("Expected Board:", expectedBoard);
		
		board.insertToken(Cells.RED, 1);
		board.insertToken(Cells.GREEN, 1);
		board.insertToken(Cells.RED, 1);
		board.insertToken(Cells.GREEN, 1);
		board.insertToken(Cells.RED, 1);
		board.insertToken(Cells.GREEN, 1);
		
		String actualBoard = board.getPrintableBoard();
		printBoard("Actual Board:", actualBoard);
		
		assertEquals(expectedBoard, board.getPrintableBoard());
	}
	
	@Test
	public void test_trytoInsertInCompleteColumn() {
		board.insertToken(Cells.RED, 1);
		board.insertToken(Cells.GREEN, 1);
		board.insertToken(Cells.RED, 1);
		board.insertToken(Cells.GREEN, 1);
		board.insertToken(Cells.RED, 1);
		board.insertToken(Cells.GREEN, 1);
		
		assertFalse(board.insertToken(Cells.GREEN, 1));
	}
	
	
	// Private methods
	
	private void printBoard(String boardName, String boardStr) {
		System.out.println(boardName + ":");
		System.out.println(boardStr);
	}
}

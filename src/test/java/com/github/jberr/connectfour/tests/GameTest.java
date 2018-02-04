package com.github.jberr.connectfour.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

import com.github.jberr.connectfour.game.Game;
import com.github.jberr.connectfour.items.Board;
import com.github.jberr.connectfour.items.Players;

public class GameTest {
		
	Game game;
	
	@Before
	public void setupTest() {
		game = new Game();
	}
	
	// Tests
	
	@Test
	public void test_BeginningGame() {
		assertEquals(0, game.getNumberOfMovements());
		assertFalse(game.isGameEnded());
		assertFalse(game.isWinningMove());
		assertFalse(game.noMoreMoves());
	}
	
	@Test
	public void test_insertToken_FirstToken() {
		assertTrue(game.insertToken(Players.PLAYER_1, 1));
		assertEquals(1, game.getNumberOfMovements());
		assertFalse(game.isGameEnded());
		assertFalse(game.isWinningMove());
		assertFalse(game.noMoreMoves());
	}
	
	@Test
	/**
	 * Fill first column in the board:
	 * | G |   |   |   |   |   |   |
	 * | R |   |   |   |   |   |   |
	 * | G |   |   |   |   |   |   |
	 * | R |   |   |   |   |   |   |
	 * | G |   |   |   |   |   |   |
	 * | R |   |   |   |   |   |   |
	 */
	public void test_insertToken_FillColumn() {
		/* Fill first column */
		int i=1;
		while (i <= Board.NUM_ROWS) {
			assertTrue(game.insertToken(getPlayerToPlay(i), 1));
			i++;
		}	
		/* Try to insert token in full column returns false */
		assertFalse(game.insertToken(getPlayerToPlay(i), 1));
		game.printBoard();
		assertEquals(Board.NUM_ROWS, game.getNumberOfMovements());
		assertFalse(game.isGameEnded());
		assertFalse(game.isWinningMove());
		assertFalse(game.noMoreMoves());
	}
	
	@Test
	/**
	 * Player 1 wins with horizontal connection. Board:
	 * |   |   |   |   |   |   |   |
	 * |   |   |   |   |   |   |   |
	 * |   |   |   |   |   |   |   |
	 * |   |   |   |   |   |   |   |
	 * |   |   |   |   |   |   |   |
	 * | R | R | R | R | G | G | G |
	 */
	public void test_insertToken_WinWithHorizontalConnection() {
		int[] player1moves = {1,2,3,4};
		int[] player2moves = {5,6,7};
		
		for (int i=0; i<3; i++) {
			assertTrue(game.insertToken(Players.PLAYER_1, player1moves[i]));
			assertTrue(game.insertToken(Players.PLAYER_2, player2moves[i]));
		}
		/* Insert winning token */
		assertTrue(game.insertToken(Players.PLAYER_1, player1moves[player1moves.length-1]));
		game.printBoard();
		assertTrue(game.isWinningMove());
	}
	
	@Test
	/**
	 * Player 1 wins with vertical connection. Board:
	 * |   |   |   |   |   |   |   |
	 * | R |   |   |   |   |   |   |
	 * | R | G |   |   |   |   |   |
	 * | R | G |   |   |   |   |   |
	 * | R | G |   |   |   |   |   |
	 */
	public void test_insertToken_WinWithVerticalConnection() {
		for (int i=0; i<3; i++) {
			assertTrue(game.insertToken(Players.PLAYER_1, 1));
			assertTrue(game.insertToken(Players.PLAYER_2, 2));
		}
		/* Insert winning token */
		assertTrue(game.insertToken(Players.PLAYER_1, 1));
		game.printBoard();
		assertTrue(game.isWinningMove());
	}
	
	@Test
	/**
	 * Player 1 wins with diagonal connection. Board:
	 * |   |   |   |   |   |   |   |
	 * |   |   |   |   |   |   |   |
	 * |   |   |   | R |   |   |   |
	 * |   |   | R | R |   |   |   |
	 * | G | R | R | G |   |   |   |
	 * | R | G | G | G |   |   |   |
	 */
	public void test_insertToken_WinWithDiagonalType1Connection() {
		int[] player1moves = {1,2,3,3,4,4};
		int[] player2moves = {2,3,4,4,1};
		
		for (int i=0; i<5; i++) {
			assertTrue(game.insertToken(Players.PLAYER_1, player1moves[i]));
			assertTrue(game.insertToken(Players.PLAYER_2, player2moves[i]));
		}
		/* Insert winning token */
		assertTrue(game.insertToken(Players.PLAYER_1, player1moves[player1moves.length-1]));
		game.printBoard();
		assertTrue(game.isWinningMove());
	}
	
	@Test
	/**
	 * Player 1 wins with diagonal connection. Board:
	 * |   |   |   |   |   |   |   |
	 * |   |   |   |   |   |   |   |
	 * |   |   |   | R |   |   |   |
	 * |   |   |   | R | R |   |   |
	 * |   |   |   | G | R | R | G |
	 * |   |   |   | G | G | G | R |
	 */
	public void test_insertToken_WinWithDiagonalType2Connection() {
		int[] player1moves = {7,6,5,5,4,4};
		int[] player2moves = {6,5,4,4,7};
		
		for (int i=0; i<5; i++) {
			assertTrue(game.insertToken(Players.PLAYER_1, player1moves[i]));
			assertTrue(game.insertToken(Players.PLAYER_2, player2moves[i]));
		}
		/* Insert winning token */
		assertTrue(game.insertToken(Players.PLAYER_1, player1moves[player1moves.length-1]));
		game.printBoard();
		assertTrue(game.isWinningMove());
	}
	
	@Test
	public void test_readPlayerChoice_validChoice() {
		InputStream in = new ByteArrayInputStream("1".getBytes());
		assertEquals(1, game.readPlayerChoice(Players.PLAYER_1, in));
	}
	
	@Test
	public void test_readPlayerChoice_invalidChoice_NoNumber() {
		InputStream in = new ByteArrayInputStream("invalid".getBytes());
		assertEquals(Game.INVALID_CHOICE, game.readPlayerChoice(Players.PLAYER_1, in));
	}
	
	@Test
	public void test_readPlayerChoice_invalidChoice_OutOfRange() {
		InputStream in = new ByteArrayInputStream("0".getBytes());
		assertEquals(Game.INVALID_CHOICE, game.readPlayerChoice(Players.PLAYER_1, in));
		
		in = new ByteArrayInputStream(Integer.toString(Board.NUM_COLUMNS+1).getBytes());
		assertEquals(Game.INVALID_CHOICE, game.readPlayerChoice(Players.PLAYER_1, in));
	}
	
	@Test
	public void test_inputChoice_validChoice() {
		InputStream in = new ByteArrayInputStream("1".getBytes());
		assertTrue(game.inputChoice(Players.PLAYER_1, in));
	}
	
	@Test
	/**
	 * Player 1 wins with vertical connection. Board:
	 * |   |   |   |   |   |   |   |
	 * |   | G |   |   |   |   |   |
	 * | R | G |   |   |   |   |   |
	 * | R | G |   |   |   |   |   |
	 * | R | G | R |   |   |   |   |
	 */
	public void test_inputChoice_Player2WinWithVerticalConnection() {
		for (int i=0; i<3; i++) {
			assertTrue(game.inputChoice(Players.PLAYER_1, new ByteArrayInputStream("1".getBytes())));
			assertTrue(game.inputChoice(Players.PLAYER_2, new ByteArrayInputStream("2".getBytes())));
		}
		/* Insert winning token */
		assertTrue(game.inputChoice(Players.PLAYER_1, new ByteArrayInputStream("3".getBytes())));
		assertTrue(game.inputChoice(Players.PLAYER_2, new ByteArrayInputStream("2".getBytes())));
		game.printBoard();
		assertTrue(game.isWinningMove());
		assertTrue(game.isGameEnded());
	}
		
	// Private 
	
	private Players getPlayerToPlay(int turn) {
		Players player;
		if (turn % 2 == 1) {
			player = Players.PLAYER_1;
		} else {
			player = Players.PLAYER_2;
		}
		return player;
	}
}

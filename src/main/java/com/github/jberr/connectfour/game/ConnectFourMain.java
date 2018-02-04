package com.github.jberr.connectfour.game;

import java.util.ArrayList;
import java.util.List;

import com.github.jberr.connectfour.items.Players;

public class ConnectFourMain {
		
	private static Game game;
	
	public static void main(String[] args) {

		/* Init Game */
		game = new Game();
		game.initGame();
		List<Players> players = new ArrayList<Players>();
		players.add(Players.PLAYER_1);
		players.add(Players.PLAYER_2);
				
		/* Play game */
		game.printBoard();
		while (!game.isGameEnded()) {
			for (Players player: players) {
				game.inputChoice(player);
				game.printBoard();
				if (game.isGameEnded()) {
					break;
				}
			}
		}
		game.finishGame();
	}
}

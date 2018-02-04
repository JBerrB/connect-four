package com.github.jberr.connectfour.items;

public enum Players {
	PLAYER_1(1, "RED"),
	PLAYER_2(2, "GREEN");
	
	private int number;
	private String colour;
	
	Players(int number, String colour) {
		this.number = number;
		this.colour = colour;
	}
	
	public int getNumber() {
		return number;
	}
	
	public String getColour() {
		return colour;
	}
}

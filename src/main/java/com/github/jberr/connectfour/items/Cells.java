package com.github.jberr.connectfour.items;

public enum Cells {
	
	EMPTY (0, " "),
	RED (1, "R"),
	GREEN (2, "G");
	
	private int value;
	private String printValue;
	
	Cells(int value, String printValue) {
		this.value = value;
		this.printValue = printValue;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getPrintValue() {
		return printValue;
	}
}

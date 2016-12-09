package com.minbo.conference.solver.vo;

public class Request {
	
	private int profit[];
	private int weight[];
	private int numSize;
	private int MaxSize;

	public int[] getProfit() {
		return profit;
	}

	public void setProfit(int[] profit) {
		this.profit = profit;
	}

	public int[] getWeight() {
		return weight;
	}

	public void setWeight(int[] weight) {
		this.weight = weight;
	}

	public int getNumSize() {
		return numSize;
	}

	public void setNumSize(int numSize) {
		this.numSize = numSize;
	}

	public int getMaxSize() {
		return MaxSize;
	}

	public void setMaxSize(int maxSize) {
		MaxSize = maxSize;
	}
}

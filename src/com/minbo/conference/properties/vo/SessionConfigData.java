package com.minbo.conference.properties.vo;

public class SessionConfigData {

	private int id;
	private String name;
	private int startTime;
	private int endTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return getId() + ", " + getName() + ", " + getStartTime() + ", " + getEndTime();
	}

}
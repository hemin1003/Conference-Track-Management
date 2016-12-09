package com.minbo.conference.service.vo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.minbo.conference.service.Session;

public class Track {
	static int count = 0;
	private final int ID;
	private List<Session> sessions;

	public int getID() {
		return ID;
	}

	public Track() {
		this.ID = ++count;
		sessions = new ArrayList<Session>();
	}

	public void addNewSession(Session s) {
		sessions.add(s);
	}

	public void output() {
		Iterator<Session> iter = sessions.iterator();
		int currentSessionEndTime = 0, prevSessionEndTime = 0;
		while (iter.hasNext()) {
			Session s = iter.next();
			currentSessionEndTime = s.calcEndSessionTime();
			s.calculate(prevSessionEndTime);
			prevSessionEndTime = currentSessionEndTime;
		}
	}
}

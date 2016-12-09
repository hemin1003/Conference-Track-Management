package com.minbo.conference.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.minbo.conference.exception.ConferenceException;
import com.minbo.conference.properties.vo.SessionConfigData;
import com.minbo.conference.service.vo.Talk;

public abstract class Session {

	protected int id;
	protected String type;
	protected int startTime;
	protected int endTime;
	protected int currentSessionEndTime;

	public static int timeConverter(int time) {
		Date sdate = null;
		try {
			sdate = new SimpleDateFormat("HHmm").parse(String.format("%04d",
					time));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdate);
		return cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(Calendar.MINUTE);
	}

	public static String formatTime(int currentTime) {
		int hour = currentTime / 60;
		String dTime;
		if (hour < 12)
			dTime = "AM";
		else
			dTime = "PM";
		if (hour > 12)
			hour = hour - 12;
		return String.format("%02d:%02d %s ", hour, (currentTime % 60), dTime);
	}

	public Session(SessionConfigData session) {
		this.id = session.getId();
		this.type = session.getName();
		this.startTime = timeConverter(session.getStartTime());
		this.endTime = timeConverter(session.getEndTime());
	}

	public void calculate(int prevSessionEndTime) {
		
	}

	public abstract int calcEndSessionTime();

	public abstract void analyze(List<Talk> talksList)
			throws ConferenceException;

}
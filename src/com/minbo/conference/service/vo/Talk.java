package com.minbo.conference.service.vo;

import java.util.UUID;

import com.minbo.conference.properties.Configurator;

public class Talk {
	
	private final String ID;
	private String talkTopic;
	private int timeDuration;
	private boolean isIncluded;
	
	public Talk(String talkTopic, int timeDuration) {
		this.ID = this.generateUniqueKey();
		this.talkTopic = talkTopic;
		this.timeDuration = timeDuration;
		this.isIncluded = false;
	}
	
	public Talk(Talk t) {
		this.ID = t.ID;
		this.talkTopic = t.talkTopic;
		this.timeDuration = t.timeDuration;
		this.isIncluded = true;
	}

	public boolean isIncluded() {
		return isIncluded;
	}

	public void setIncluded(boolean isIncluded) {
		this.isIncluded = isIncluded;
	}

	public String generateUniqueKey() {
		String id = UUID.randomUUID().toString();
		return id;
	}

	public int getTimeDuration() {
		return timeDuration;
	}

	public String getTalkTopic() {
		return talkTopic;
	}

	public void print(String startTime) {
		StringBuffer sb = new StringBuffer();
		sb.append(startTime);
		sb.append(this.getTalkTopic());
		sb.append(" ");
		sb.append(this.getTimeDuration());
		sb.append(Configurator.getConferenceConfigData().getMinuteSuffix());
		System.out.println(sb.toString());
	}
}
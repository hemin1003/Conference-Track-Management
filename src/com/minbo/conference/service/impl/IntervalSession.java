package com.minbo.conference.service.impl;

import java.util.List;

import com.minbo.conference.exception.ConferenceException;
import com.minbo.conference.properties.Configurator;
import com.minbo.conference.properties.vo.SessionConfigData;
import com.minbo.conference.service.Session;
import com.minbo.conference.service.vo.Talk;

/**
 * 空闲时间区间
 */
public class IntervalSession extends Session {

	public IntervalSession(SessionConfigData session) {
		super(session);
	}

	@Override
	public void calculate(int prevSessionEndTime) {
		if (prevSessionEndTime == 0) {
			prevSessionEndTime = this.startTime;
		}
		if ("lunch".equals(this.type)) {
			int duration = this.endTime - this.startTime;
			System.out.println(formatTime(this.startTime)
					+ "Lunch "
					+ duration
					+ Configurator.getConferenceConfigData()
							.getMinuteSuffix());
			return;
		}
		if ("networking".equals(this.type)) {
			int minNetworkingStartTime = 0;
			try {
				minNetworkingStartTime = timeConverter(Configurator
						.getConferenceConfigData().getMinNetworkingStartTime());
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			if (prevSessionEndTime > minNetworkingStartTime) {
				System.out.println(formatTime(prevSessionEndTime)
						+ "Networking Event\n");
			} else {
				System.out.println(formatTime(minNetworkingStartTime)
						+ "Networking Event\n");
			}
		}

	}

	public int calcEndSessionTime() {
		return this.endTime;
	}
	
	public void analyze(List<Talk> validTalksList) throws ConferenceException {
		
	}
}

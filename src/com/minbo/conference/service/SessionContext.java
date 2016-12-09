package com.minbo.conference.service;

import com.minbo.conference.exception.ConferenceException;
import com.minbo.conference.properties.vo.SessionConfigData;
import com.minbo.conference.service.impl.IntervalSession;
import com.minbo.conference.service.impl.TalkSession;

/**
 * 处理不同时间段的session
 */
public class SessionContext {

	public static Session getSessionfromType(SessionConfigData session) throws ConferenceException {
		String sessionName = session.getName();

		if ("morning".equals(sessionName) || "afternoon".equals(sessionName)) {
			Session s = new TalkSession(session);
			return s;

		} else if ("lunch".equals(sessionName) || "networking".equals(sessionName)) {
			Session s = new IntervalSession(session);
			return s;

		} else {
			throw new ConferenceException("配置数据异常，请检查.");
		}
	}
}

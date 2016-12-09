package com.minbo.conference.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.minbo.conference.exception.ConferenceException;
import com.minbo.conference.properties.vo.SessionConfigData;
import com.minbo.conference.service.Session;
import com.minbo.conference.service.vo.Talk;
import com.minbo.conference.solver.Solver;
import com.minbo.conference.solver.impl.SolverImpl;
import com.minbo.conference.solver.vo.Request;
import com.minbo.conference.solver.vo.Response;

/**
 * 主时间区间
 */
public class TalkSession extends Session {

	private List<Talk> talks;

	public void addTalk(Talk t) {
		talks.add(new Talk(t));
	}

	public TalkSession(SessionConfigData session) {
		super(session);
		talks = new ArrayList<>();
	}

	/**
	 * 分析处理
	 */
	public void analyze(List<Talk> talksList) throws ConferenceException {
		Request req = setSolverRequest(talksList);
		Response res = new Response();
		useSolver(req, res);
		updateTalkList(talksList, res);
	}

	/**
	 * 设置初始值
	 * @param talksList
	 * @return
	 */
	private Request setSolverRequest(
			List<Talk> talksList) {
		int W = this.getEndTime() - this.getStartTime();
		int N = talksList.size();
		
		int[] profit = new int[N + 1];
		int[] weight = new int[N + 1];
		int i = 1;
		
		//profit和weight值一样
		for (Talk proft : talksList) {
			profit[i] = weight[i] = proft.getTimeDuration();
			i++;
		}
		
		Request req = new Request();
		req.setMaxSize(W);
		req.setNumSize(N);
		req.setProfit(profit);
		req.setWeight(weight);
		
		return req;
	}
	
	/**
	 * 使用算法
	 * @param req
	 * @param res
	 */
	private void useSolver(Request req, Response res) {
		Solver solver = new SolverImpl();
		try {
			solver.process(req, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateTalkList(List<Talk> talksList,
			Response res) {
		boolean[] take;
		//每次处理后最优的值
		take = res.getTake();
		int i = 1;
		for (ListIterator<Talk> iter = talksList.listIterator(); iter.hasNext();) {
			Talk talk = iter.next();
			if (take[i]) {
				talk.setIncluded(true);
				this.addTalk(talk);
				iter.remove();
			}
			i++;
		}
	}

	public int getStartTime() {
		return startTime;
	}

	public int getEndTime() {
		return endTime;
	}
	
	public int calcEndSessionTime() {
		int tsum = 0;
		for (Talk t : talks) {
			tsum += t.getTimeDuration();
		}
		currentSessionEndTime = (tsum + this.startTime);
		return currentSessionEndTime;
	}

	@Override
	public void calculate(int prevEndSessionTime) {
		int currentTime = this.startTime;
		for (Talk talk : talks) {
			String s = Session.formatTime(currentTime);
			talk.print(s);
			currentTime += talk.getTimeDuration();
		}
	}
}
package com.minbo.conference.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.minbo.conference.properties.Configurator;
import com.minbo.conference.properties.vo.ConferenceConfigData;
import com.minbo.conference.properties.vo.SessionConfigData;
import com.minbo.conference.service.Session;
import com.minbo.conference.service.SessionContext;
import com.minbo.conference.service.vo.Talk;
import com.minbo.conference.service.vo.Track;

/**
 * 主操作类
 * @author Minbo
 */
public class Controller {

	private static List<Talk> talksList;
	private static final Logger log = Logger.getLogger(Controller.class);

	/**
	 * 根据背包问题解法，通过计算，得出分组session
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static List<Track> process(List<String> list) throws Exception {
		List<Track> tracks = new ArrayList<Track>();
		try {
			checkValidTalks(list);
			List<SessionConfigData> sessionlist = Configurator.getConferenceConfigData().getSessionlist();
			while (talksList.size() > 0) {
				Iterator<SessionConfigData> iter = sessionlist.iterator();
				Track track = new Track();
				tracks.add(track);
				while (iter.hasNext()) {
					Session session = SessionContext.getSessionfromType((SessionConfigData) iter.next());
					track.addNewSession(session);
					if (talksList.size() > 0) {
						session.analyze(talksList);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tracks;
	}

	/**
	 * 检查并处理每行记录，形成对象后续计算
	 * @throws Exception
	 */
	private static void checkValidTalks(List<String> talkList) throws Exception {
		if (talkList == null || talkList.isEmpty()) {
			log.error("测试数据文件异常，请检查.");
			return;
		}

		talksList = new ArrayList<Talk>();
		ConferenceConfigData config = Configurator.getConferenceConfigData();
		int maxTalkTime = config.getMaxTalkMinutes();
		int minTalkTime = config.getMinTalkMinutes();
		int talktime = 0;

		for (String talk : talkList) {
			Pattern pattern = Pattern.compile("(.*)(\\s){1}([0-2]?[0-9]?[0-9]{1}min|lightning)\\b");
			Matcher matcher = pattern.matcher(talk);
			if (!matcher.matches()) {
				log.error("文件内容格式不对，请检查。Talk=" + talk);
				continue;
			}
			// 分组取值
			talktime = getTalkTime(matcher.group(3));
			if (talktime <= maxTalkTime && talktime >= minTalkTime) {
				talksList.add(new Talk(matcher.group(1), talktime));
			} else {
				log.error("内容时间设置不对，请检查。Talk=" + talk);
			}
		}
	}

	/**
	 * 得到每个Talk的具体时间
	 * @return
	 */
	private static int getTalkTime(String endingStr) {
		ConferenceConfigData config = Configurator.getConferenceConfigData();
		String minuteSuffix = config.getMinuteSuffix();
		String lightningSuffix = config.getLightningSuffix();
		int talktime = 0;
		try {
			if (endingStr.endsWith(minuteSuffix)) {
				talktime = Integer.parseInt(endingStr.substring(0, endingStr.indexOf(minuteSuffix)));

			} else if (endingStr.endsWith(lightningSuffix)) {
				talktime = config.getLightningMinutes();
			}

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return talktime;
	}
}
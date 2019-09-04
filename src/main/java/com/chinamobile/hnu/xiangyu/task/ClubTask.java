/**
 * 
 */
package com.chinamobile.hnu.xiangyu.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinamobile.hnu.xiangyu.ask.service.IAskInfoService;
import com.chinamobile.hnu.xiangyu.club.service.ClubBaseService;
import com.chinamobile.hnu.xiangyu.club.service.ClubVoteService;

/**
 * @author The Old Man and the Sea
 *
 *         2018年6月4日
 */
public class ClubTask {

	private static Logger log = LoggerFactory.getLogger(ClubTask.class);

	@Autowired
	private ClubBaseService baseService;

	@Autowired
	private ClubVoteService voteService;

	@Autowired
	private IAskInfoService askInfoService;

	/***
	 * 团队昨日访问量
	 */
	public void clubVisitTask() {
		log.info("修改团队昨日访问量定时任务开始执行");
		try {
			baseService.updateClubYesterdayTraffic();
		} catch (Exception e) {
			// TODO: handle exception
			log.info("修改团队昨日访问量定时任务执行失败：", e);
		}

		log.info("修改团队昨日访问量定时任务执行完毕");
	}

	/****
	 * 投票过期定时任务
	 */
	public void clubVoteTask() {
		log.info("投票过期定时任务开始执行");
		try {
			voteService.updateVoteStatus();
		} catch (Exception e) {
			// TODO: handle exception
			log.info("投票过期定时任务执行失败：", e);
		}

		log.info("投票过期定时任务执行完毕");
	}

	/***
	 * 问问昨日访问量
	 */
	public void askVisitTask() {
		log.info("修改问问昨日访问量定时任务开始执行");
		try {
			askInfoService.updateAskYesterdayTraffic();
		} catch (Exception e) {
			// TODO: handle exception
			log.info("修改问问昨日访问量定时任务执行失败：", e);
		}
		log.info("修改问问昨日访问量定时任务执行完毕");
	}

}

/**
 * 
 */
package com.chinamobile.hnu.xiangyu.web.club;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chinamobile.hnu.xiangyu.club.pojo.ClubMember;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubVoteInfo;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubVoteOption;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubVoteQuestion;
import com.chinamobile.hnu.xiangyu.club.pojo.ClueVoteRecord;
import com.chinamobile.hnu.xiangyu.club.pojo.ClueVoteRecordKey;
import com.chinamobile.hnu.xiangyu.club.service.ClubBaseService;
import com.chinamobile.hnu.xiangyu.club.service.ClubMemberService;
import com.chinamobile.hnu.xiangyu.club.service.ClubVoteService;
import com.chinamobile.hnu.xiangyu.club.vo.ClubSpeakVo;
import com.chinamobile.hnu.xiangyu.club.vo.ClubVoteVo;
import com.chinamobile.hnu.xiangyu.club.vo.Visibility;
import com.chinamobile.hnu.xiangyu.user.pojo.UserAccount;
import com.chinamobile.hnu.xiangyu.user.service.AuthTokenService;
import com.chinamobile.hnu.xiangyu.user.service.UserService;
import com.chinamobile.hnu.xiangyu.util.ResultUtil;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.util.dto.PageVo;
import com.chinamobile.hnu.xiangyu.util.dto.ResponseDto;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author The Old Man and the Sea
 *
 *         2018年6月4日
 */
@RestController
@RequestMapping("/api/club/vote/")
public class ClubVoteController {

	private static Logger log = LoggerFactory.getLogger(ClubVoteController.class);

	@Autowired
	private AuthTokenService tokenService;

	@Autowired
	private ClubVoteService voteService;
	
	@Autowired
	private ClubMemberService memberService;
	
	@Autowired
	private ClubBaseService clubService;
	
	@Autowired
	private UserService userService;

	/****
	 * 投票分页
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param id
	 * @return
	 */
	@PostMapping("pglist.do")
	public ResponseDto list(@RequestParam(name = "pn", required = true, defaultValue = "0") Integer pageCurrent,
			@RequestParam(name = "ps", required = true, defaultValue = "10") Integer pageSize,
			@RequestParam(required = true) Integer id) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", user.getId());
		if(id != null){
			map.put("clubId", id);
		}else{
			return ResultUtil.result(2, "团队id不能为空");
		}
		Page<ClubVoteVo> page = null;
		try {
			
			Visibility vi = clubService.selectVisibilityId(id);
			if(vi.getSettingVote().intValue() == 1){
				ClubMember clubMember = memberService.selectUserJoin(id, user.getId());
				if(clubMember == null){
					return ResultUtil.result(0, "查询成功",new PageVo(new ArrayList<>(),0L,0));
				}
			}
			page = voteService.selectByPage(pageCurrent, pageSize, map);
			userService.setFriendNickName(page.getData(), user.getId());
			return ResultUtil.result(0, "查询成功", new PageVo(page.getData(), page.getTotalCount(), page.getTotalPage()));
		} catch (Exception e) {
			// TODO: handle exception
			log.info("select ClubVoteInfo page Exception:", e);
			return ResultUtil.serviceException();
		}
	}

	/****
	 * 发布投票
	 * 
	 * @param request
	 * @param clubVoteInfo
	 * @param result
	 * @return
	 */
	@PostMapping("save.do")
	public ResponseDto save(HttpServletRequest request, @Validated ClubVoteInfo clubVoteInfo, BindingResult result) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}

		if (result.hasErrors()) {
			return ResultUtil.result(2, result.getAllErrors().get(0).getDefaultMessage());
		}

		try {
			ClubMember member = memberService.selectUserJoin(clubVoteInfo.getClubId(),user.getId());
			if(member == null){
				return ResultUtil.result(2, "你还没有加入该团队");
			}
			// 解析投票内容
			String jsonData = clubVoteInfo.getContent();
			// 投票题目
			List<ClubVoteQuestion> questions = new ArrayList<>();
			JSONArray jsonArray = JSONArray.fromObject(jsonData);
			int index = 1;
			for (Object obj : jsonArray) {
				ClubVoteQuestion question = (ClubVoteQuestion) JSONObject.toBean((JSONObject) obj, ClubVoteQuestion.class);
				if(question == null){
					return ResultUtil.result(2, "投票" + index + "投票内容不能为空");
				}else if (StringUtils.isBlank(question.getContent())) {
					return ResultUtil.result(2, "投票" + index + "内容不能为空");
				} else if (question.getContent().length() > 32) {
					return ResultUtil.result(2, "投票" + index + "内容字符过长");
				} else if (null == question.getCategory()) {
					return ResultUtil.result(2, "投票" + index + "类型不能为空");
				} else if (null == question.getOptions() || question.getOptions().size() <= 0) {
					return ResultUtil.result(2, "投票" + index + "选项不能为空");
				}
				int temp = 1;

				JSONArray jsonOptions = JSONArray.fromObject(question.getOptions());
				List<ClubVoteOption> options = new ArrayList<>();
				for (Object optionObj : jsonOptions) {
					ClubVoteOption option = (ClubVoteOption) JSONObject.toBean((JSONObject) optionObj, ClubVoteOption.class);
					if(option == null){
						return ResultUtil.result(2, "投票" + index + "选项" + temp + "字符不能为空");
					}else if (option.getContent().length() > 40) {
						return ResultUtil.result(2, "投票" + index + "选项" + temp + "字符过长");
					}
					options.add(option);
					temp++;
				}
				if (options.size() > 0) {
					question.setOptions(options);
				}
				questions.add(question);
				index++;
			}
			if (questions.size() <= 0) {
				return ResultUtil.result(2, "投票内容不能为空");
			}
			clubVoteInfo.setQuestions(questions);
			clubVoteInfo.setPresentor(user.getId());
			voteService.insertClubVoteInfo(clubVoteInfo);
			Map<String, Object> map = new HashMap<>();
			map.put("id", clubVoteInfo.getId());
			return ResultUtil.result(0, "创建投票成功", map);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("save clubInfo Exception:", e);
			return ResultUtil.serviceException();
		}
	}

	
	/****
	 * 投票详情
	 * @param voteid
	 * @return
	 */
	@PostMapping("detail.do")
	public ResponseDto detail(@RequestParam(required = true) Long voteId) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		
		ClubVoteVo vo = null; 
		try {
			vo = voteService.selectClubVoteDetail(voteId,user.getId());
			userService.setFriendNickName(vo, user.getId());
			return ResultUtil.result(0, "查询成功", vo);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("select ClubVoteInfo detail Exception:", e);
			return ResultUtil.serviceException();
		}
	}
	
	
	/***
	 * 用户投票
	 * @param voteId
	 * @param optionId
	 * @return
	 */
	@PostMapping("comment.do")
	public ResponseDto comment(@RequestParam(required = true) Long voteId,@RequestParam(required = true) String optionId) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		
		try {
			ClubVoteInfo vote = voteService.selectClubVoteById(voteId);
			if(vote == null){
				return ResultUtil.result(2, "投票不存在");
			}else{
				ClubMember member = memberService.selectUserJoin(vote.getClubId(),user.getId());
				if(member == null){
					return ResultUtil.result(2, "你还没有加入该团队");
				}
			}
			
			ClueVoteRecordKey key = new ClueVoteRecordKey();
			key.setVoterId(voteId);
			key.setPresentor(user.getId());
			List<ClueVoteRecord> record = voteService.selectByPrimaryKey(key);
			if(record.size() > 0){
				return ResultUtil.result(2, "你已经投过票了");
			}
			if(StringUtils.isBlank(optionId)){
				return ResultUtil.result(2, "投票选项不能为空");
			}
			
			voteService.updateClubVoteOption(voteId,optionId,user.getId());
			
			return ResultUtil.result(0, "投票成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.info("delete ClubVoteInfo Exception:", e);
			return ResultUtil.serviceException();
		}
	}
	
	
	/****
	 * 删除投票
	 * @param voteId
	 * @return
	 */
	@PostMapping("delete.do")
	public ResponseDto delete(@RequestParam(required = true) Long voteId) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		
		
		try {
			ClubVoteInfo vote = voteService.selectClubVoteById(voteId);
			if(vote == null){
				return ResultUtil.result(2, "没有该投票");
			}
			boolean flag = memberService.userIsClubAdmin(vote.getClubId(),user.getId());
			if(!flag && vote.getPresentor().intValue() != user.getId().intValue()){
				return ResultUtil.result(3, "没有权限");	
			}
			voteService.deleteDeleteClubVoteById(voteId);
			return ResultUtil.result(0, "删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.info("delete ClubVoteInfo Exception:", e);
			return ResultUtil.serviceException();
		}
	}
	
	
	
	
	

}

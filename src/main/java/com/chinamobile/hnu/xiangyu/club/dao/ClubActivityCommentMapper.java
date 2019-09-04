package com.chinamobile.hnu.xiangyu.club.dao;

import java.util.List;
import java.util.Map;

import com.chinamobile.hnu.xiangyu.club.pojo.ClubActivityComment;
import com.chinamobile.hnu.xiangyu.club.vo.CommentVo;

public interface ClubActivityCommentMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table club_activity_comment
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table club_activity_comment
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    int insert(ClubActivityComment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table club_activity_comment
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    int insertSelective(ClubActivityComment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table club_activity_comment
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    ClubActivityComment selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table club_activity_comment
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    int updateByPrimaryKeySelective(ClubActivityComment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table club_activity_comment
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    int updateByPrimaryKey(ClubActivityComment record);

	/**
	 * 查询活动评论分页
	 * @param map
	 * @return
	 */
	List<CommentVo> selectByPage(Map<String, Object> map);
}
package com.chinamobile.hnu.xiangyu.club.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.chinamobile.hnu.xiangyu.club.pojo.ClubLabel;

public interface ClubLabelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table club_label
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table club_label
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    int insert(ClubLabel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table club_label
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    int insertSelective(ClubLabel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table club_label
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    ClubLabel selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table club_label
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    int updateByPrimaryKeySelective(ClubLabel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table club_label
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    int updateByPrimaryKey(ClubLabel record);

	/**
	 * 批量增加标签
	 * @param list
	 */
	void insertList(List<ClubLabel> list);

	/**
	 * 查询团队标签
	 * @param id
	 * @return
	 */
	List<ClubLabel> selectByClubId(Integer id);

	/**
	 * 查询团队标签
	 * @param clubIdSet
	 * @return
	 */
	List<ClubLabel> selectByClubIdList(@Param("set")Set<Integer> clubIdSet);

	/**
	 * 删除团队标签
	 * @param id
	 */
	void deleteByClubId(Integer clubId);
}
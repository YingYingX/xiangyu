package com.chinamobile.hnu.xiangyu.club.pojo;

import java.util.Date;

public class ClueVoteRecord extends ClueVoteRecordKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column clue_vote_record.gmt_create
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    private Date gmtCreate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column clue_vote_record.gmt_create
     *
     * @return the value of clue_vote_record.gmt_create
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column clue_vote_record.gmt_create
     *
     * @param gmtCreate the value for clue_vote_record.gmt_create
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}
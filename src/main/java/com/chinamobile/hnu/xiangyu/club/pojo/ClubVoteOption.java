package com.chinamobile.hnu.xiangyu.club.pojo;

import java.util.Date;

public class ClubVoteOption {
    private Long id;

    private Date gmtCreate;

    private Long questionId;

    private String content;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column club_vote_option.vote_amount
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    private Integer voteAmount;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column club_vote_option.id
     *
     * @return the value of club_vote_option.id
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column club_vote_option.id
     *
     * @param id the value for club_vote_option.id
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column club_vote_option.gmt_create
     *
     * @return the value of club_vote_option.gmt_create
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column club_vote_option.gmt_create
     *
     * @param gmtCreate the value for club_vote_option.gmt_create
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column club_vote_option.question_id
     *
     * @return the value of club_vote_option.question_id
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public Long getQuestionId() {
        return questionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column club_vote_option.question_id
     *
     * @param questionId the value for club_vote_option.question_id
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column club_vote_option.content
     *
     * @return the value of club_vote_option.content
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column club_vote_option.content
     *
     * @param content the value for club_vote_option.content
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column club_vote_option.vote_amount
     *
     * @return the value of club_vote_option.vote_amount
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public Integer getVoteAmount() {
        return voteAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column club_vote_option.vote_amount
     *
     * @param voteAmount the value for club_vote_option.vote_amount
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setVoteAmount(Integer voteAmount) {
        this.voteAmount = voteAmount;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ClubVoteOption [content=" + content + "]";
	}
    
    
}
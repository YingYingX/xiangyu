package com.chinamobile.hnu.xiangyu.club.pojo;

import java.util.Date;
import java.util.List;

public class ClubVoteQuestion {
    private Long id;

    private Date gmtCreate;

    private Long voteId;

    private String content;

    private Byte category;

 //   private Integer voteAmount;
    
    private List<ClubVoteOption> options;
    
    
    

    /**
	 * @return the options
	 */
	public List<ClubVoteOption> getOptions() {
		return options;
	}

	/**
	 * @param options the options to set
	 */
	public void setOptions(List<ClubVoteOption> options) {
		this.options = options;
	}

	public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column club_vote_question.id
     *
     * @param id the value for club_vote_question.id
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column club_vote_question.gmt_create
     *
     * @return the value of club_vote_question.gmt_create
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column club_vote_question.gmt_create
     *
     * @param gmtCreate the value for club_vote_question.gmt_create
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column club_vote_question.vote_id
     *
     * @return the value of club_vote_question.vote_id
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public Long getVoteId() {
        return voteId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column club_vote_question.vote_id
     *
     * @param voteId the value for club_vote_question.vote_id
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setVoteId(Long voteId) {
        this.voteId = voteId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column club_vote_question.content
     *
     * @return the value of club_vote_question.content
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column club_vote_question.content
     *
     * @param content the value for club_vote_question.content
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column club_vote_question.category
     *
     * @return the value of club_vote_question.category
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public Byte getCategory() {
        return category;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column club_vote_question.category
     *
     * @param category the value for club_vote_question.category
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setCategory(Byte category) {
        this.category = category;
    }

}
package com.chinamobile.hnu.xiangyu.club.pojo;

public class ClubTeamBuildingPhoto {
	
	private Long id;
	
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column club_team_building_photo.photo_id
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    private String photoId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column club_team_building_photo.tb_id
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    private Long tbId;

    
    
    
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column club_team_building_photo.photo_id
     *
     * @return the value of club_team_building_photo.photo_id
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public String getPhotoId() {
        return photoId;
    }

    
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column club_team_building_photo.photo_id
     *
     * @param photoId the value for club_team_building_photo.photo_id
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setPhotoId(String photoId) {
        this.photoId = photoId == null ? null : photoId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column club_team_building_photo.tb_id
     *
     * @return the value of club_team_building_photo.tb_id
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public Long getTbId() {
        return tbId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column club_team_building_photo.tb_id
     *
     * @param tbId the value for club_team_building_photo.tb_id
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setTbId(Long tbId) {
        this.tbId = tbId;
    }


	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
    
    
    
    
    
}
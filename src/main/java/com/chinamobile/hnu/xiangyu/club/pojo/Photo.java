/**
 * 
 */
package com.chinamobile.hnu.xiangyu.club.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author The Old Man and the Sea
 *
 * 2018年6月4日
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Photo {
	
	private Long id;
    
    private String photoId;

    private Long bzId;

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

	/**
	 * @return the photoId
	 */
	public String getPhotoId() {
		return photoId;
	}

	/**
	 * @param photoId the photoId to set
	 */
	public void setPhotoId(String photoId) {
		 this.photoId = photoId == null ? null : photoId.trim();
	}

	/**
	 * @return the bzId
	 */
	public Long getBzId() {
		return bzId;
	}

	/**
	 * @param bzId the bzId to set
	 */
	public void setBzId(Long bzId) {
		this.bzId = bzId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Photo [id=" + id + ", photoId=" + photoId + ", bzId=" + bzId + "]";
	}
}

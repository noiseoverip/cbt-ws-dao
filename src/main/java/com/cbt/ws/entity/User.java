package com.cbt.ws.entity;

/**
 * Class for storing user variables
 * 
 * @author SauliusAlisauskas
 * 
 */
public class User {

	private String mName;
	private Integer mId;

	public Integer getId() {
		return mId;
	}

	public void setId(Integer id) {
		this.mId = id;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

}

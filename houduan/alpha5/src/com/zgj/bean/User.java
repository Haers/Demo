package com.zgj.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String stuNum;
	private String name;
	private String defaultLocation;
	private Boolean gender;
	private String telephone;
	private String pay;
	private Set<Message> messagesForReceiverId = new HashSet<>(0);
	private Set<Message> messagesForSenderId = new HashSet<>(0);

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String stuNum) {
		this.stuNum = stuNum;
	}

	/** full constructor */
	public User(String stuNum, String name, String defaultLocation,
			Boolean gender, String telephone, String pay,
			Set<Message> messagesForReceiverId, Set<Message> messagesForSenderId) {
		this.stuNum = stuNum;
		this.name = name;
		this.defaultLocation = defaultLocation;
		this.gender = gender;
		this.telephone = telephone;
		this.pay = pay;
		this.messagesForReceiverId = messagesForReceiverId;
		this.messagesForSenderId = messagesForSenderId;
	}

	// Property accessors

	public String getStuNum() {
		return this.stuNum;
	}

	public void setStuNum(String stuNum) {
		this.stuNum = stuNum;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDefaultLocation() {
		return this.defaultLocation;
	}

	public void setDefaultLocation(String defaultLocation) {
		this.defaultLocation = defaultLocation;
	}

	public Boolean getGender() {
		return this.gender;
	}

	public void setGender(Boolean gender) {
		this.gender = gender;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPay() {
		return this.pay;
	}

	public void setPay(String pay) {
		this.pay = pay;
	}

	public Set<Message> getMessagesForReceiverId() {
		return this.messagesForReceiverId;
	}

	public void setMessagesForReceiverId(Set<Message> messagesForReceiverId) {
		this.messagesForReceiverId = messagesForReceiverId;
	}

	public Set<Message> getMessagesForSenderId() {
		return this.messagesForSenderId;
	}

	public void setMessagesForSenderId(Set<Message> messagesForSenderId) {
		this.messagesForSenderId = messagesForSenderId;
	}

}
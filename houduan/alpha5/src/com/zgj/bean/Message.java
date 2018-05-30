package com.zgj.bean;

import java.sql.Time;
import java.sql.Date;

/**
 * Message entity. @author MyEclipse Persistence Tools
 */

public class Message implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private User userBySenderId;
	private User userByReceiverId;
	private Date sendDate;
	private Time sendTime;
	private String msg;
	private String fetchLocation;
	private String sendLocation;
	private Boolean isCaught;
	private Boolean isDone;

	// Constructors

	/** default constructor */
	public Message() {
	}

	/** minimal constructor */
	public Message(User userBySenderId, Date sendDate, Time sendTime,
			String msg, String fetchLocation, String sendLocation,
			Boolean isCaught, Boolean isDone) {
		this.userBySenderId = userBySenderId;
		this.sendDate = sendDate;
		this.sendTime = sendTime;
		this.msg = msg;
		this.fetchLocation = fetchLocation;
		this.sendLocation = sendLocation;
		this.isCaught = isCaught;
		this.isDone = isDone;
	}

	/** full constructor */
	public Message(User userBySenderId, User userByReceiverId, Date sendDate,
			Time sendTime, String msg, String fetchLocation,
			String sendLocation, Boolean isCaught, Boolean isDone) {
		this.userBySenderId = userBySenderId;
		this.userByReceiverId = userByReceiverId;
		this.sendDate = sendDate;
		this.sendTime = sendTime;
		this.msg = msg;
		this.fetchLocation = fetchLocation;
		this.sendLocation = sendLocation;
		this.isCaught = isCaught;
		this.isDone = isDone;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUserBySenderId() {
		return this.userBySenderId;
	}

	public void setUserBySenderId(User userBySenderId) {
		this.userBySenderId = userBySenderId;
	}

	public User getUserByReceiverId() {
		return this.userByReceiverId;
	}

	public void setUserByReceiverId(User userByReceiverId) {
		this.userByReceiverId = userByReceiverId;
	}

	public Date getSendDate() {
		return this.sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Time getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Time sendTime) {
		this.sendTime = sendTime;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getFetchLocation() {
		return this.fetchLocation;
	}

	public void setFetchLocation(String fetchLocation) {
		this.fetchLocation = fetchLocation;
	}

	public String getSendLocation() {
		return this.sendLocation;
	}

	public void setSendLocation(String sendLocation) {
		this.sendLocation = sendLocation;
	}

	public Boolean getIsCaught() {
		return this.isCaught;
	}

	public void setIsCaught(Boolean isCaught) {
		this.isCaught = isCaught;
	}

	public Boolean getIsDone() {
		return this.isDone;
	}

	public void setIsDone(Boolean isDone) {
		this.isDone = isDone;
	}

}
package com.fengjr.util;

public class AccountLog {
	private int id;
	private int userid;
	private int to_userid;
	private String orderid;
	private String status;
	private String type;
	private double total;
	private String addtime;
	private int adduser;
	private String remark;
	private String currency;
	private int source_account;
	private int dest_account;
	private double availableFound;
	private double accountBalance;	
	
	public double getAvailableFound() {
		return availableFound;
	}
	public void setAvailableFound(double availableFound) {
		this.availableFound = availableFound;
	}
	public double getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}
	public int getSource_account() {
		return source_account;
	}
	public void setSource_account(int sourceAccount) {
		source_account = sourceAccount;
	}
	public int getDest_account() {
		return dest_account;
	}
	public void setDest_account(int destAccount) {
		dest_account = destAccount;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getTo_userid() {
		return to_userid;
	}
	public void setTo_userid(int toUserid) {
		to_userid = toUserid;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getUserId() {
		return userid;
	}
	public void setUserId(int userid) {
		this.userid = userid;
	}
	
	public int getToUserid() {
		return to_userid;
	}
	public void setToUserid(int to_userid) {
		this.to_userid = to_userid;
	}
	
	public String getOrderId() {
		return orderid;
	}
	public void setOrderId(String orderid) {
		this.orderid = orderid;
	}
	
	public String getType() {
		return type;
	}
	
	/**
	 * 
	 * @param type yes--转入,no--转出
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	
	public int getAdduser() {
		return adduser;
	}
	public void setAdduser(int adduser) {
		this.adduser = adduser;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	
}

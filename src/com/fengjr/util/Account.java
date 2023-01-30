package com.fengjr.util;

public class Account {
	private int accountId;
	private int userid;
	private String bankCard1;
	private String bankCard2;
	private String status;
	private double acountBalance;
	private double availableFunds;
	private double freezingFunds;
	private double agentfee;
	private String bank1;
	private String bank2;
	private String companyId;
	
	
	public double getAgentfee() {
		return agentfee;
	}
	public void setAgentfee(double agentfee) {
		this.agentfee = agentfee;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public String getBankCard1() {
		return bankCard1;
	}
	public void setBankCard1(String bankCard1) {
		this.bankCard1 = bankCard1;
	}
	public String getBankCard2() {
		return bankCard2;
	}
	public void setBankCard2(String bankCard2) {
		this.bankCard2 = bankCard2;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public double getAcountBalance() {
		return acountBalance;
	}
	public void setAcountBalance(double acountBalance) {
		this.acountBalance = acountBalance;
	}
	public double getAvailableFunds() {
		return availableFunds;
	}
	public void setAvailableFunds(double availableFunds) {
		this.availableFunds = availableFunds;
	}
	public double getFreezingFunds() {
		return freezingFunds;
	}
	public void setFreezingFunds(double freezingFunds) {
		this.freezingFunds = freezingFunds;
	}
	public String getBank1() {
		return bank1;
	}
	public void setBank1(String bank1) {
		this.bank1 = bank1;
	}
	public String getBank2() {
		return bank2;
	}
	public void setBank2(String bank2) {
		this.bank2 = bank2;
	}
	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", acountBalance="
				+ acountBalance + ", availableFunds=" + availableFunds
				+ ", bank1=" + bank1 + ", bank2=" + bank2 + ", bankCard1="
				+ bankCard1 + ", bankCard2=" + bankCard2 + ", companyId="
				+ companyId + ", freezingFunds=" + freezingFunds + ", status="
				+ status + ", userid=" + userid + "]";
	}
	
}

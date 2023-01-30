package com.fengjr.util;

import java.util.List;
import java.util.Set;

import org.apache.xmlbeans.impl.xb.ltgfmt.FileDesc.Role;

public class User {
	private int user_id;
	private String username;
	private String password;
	private int enabled;
	private String email;
	private String create_date;
	private String last_login;
	private String userinfo_number;
	private String phone_number;
	private String sex;
	private String birthday;
	private String educate_degree;
	private String school_year;
	private String school;
	private int married;
	private int have_son;
	private int have_house;
	private int have_house_loan;
	private int have_car;
	private int have_car_loan;
	private String birth_place;
	private String household_place;
	private String adress;
	private String adress_tel;
	private String randomCode;
	private Set<Role> roles;
	private String headPicture;
	private String idCard;
	private String userRole;
	private String userType;
	private String userType_desc;
	private String loanManagerId;
	private String loanCompanyId;
	private String inveManagerId;
	private String inveCompanyId;
	private String realName;
	private String bankCard1;
	private String bankCard2;
	private String bank1;
	private String bank2;
	private int usercredit_id ;
	private String acountBalance;
	private String availableFunds;
	private String user_credit;
	private String work_unit;
	private String  freezingFunds;
	private String  status;
	private String wealthProvinceId;
	private String wealthCityId;
	private String clientManagerId;
	private String finaManagerId;
	private String wealthCenterId;
	private int role_id;
	private String role_group;
	private String user_level;
	private int loan_id;
	private int parent_loan_id;
	private String src;
	private int idcardType;
	private String idcardImageSrc;
	//钱宝网传入ID
	private Integer qianbaouid;
	
	public Integer getQianbaouid() {
		return qianbaouid;
	}

	public void setQianbaouid(Integer qianbaouid) {
		this.qianbaouid = qianbaouid;
	}

	// 一个消息属性，此属性记录所有的例外数据，如：提示信息，过程信息，错误信息，逻辑错误信息 By ZCL
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public int getParent_loan_id() {
		return parent_loan_id;
	}

	public void setParent_loan_id(int parentLoanId) {
		parent_loan_id = parentLoanId;
	}

	public int getLoan_id() {
		return loan_id;
	}

	public void setLoan_id(int loanId) {
		loan_id = loanId;
	}

	public String getUser_level() {
		return user_level;
	}

	public void setUser_level(String userLevel) {
		user_level = userLevel;
	}

	
	
	public String getRole_group() {
		return role_group;
	}

	public void setRole_group(String roleGroup) {
		role_group = roleGroup;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int roleId) {
		role_id = roleId;
	}

	private int invNum;
	
	public int getInvNum() {
		return invNum;
	}

	public void setInvNum(int invNum) {
		this.invNum = invNum;
	}

	private String cashPwd;
	private String idCard_effec;
	

	public String getIdCard_effec() {
		return idCard_effec;
	}

	public void setIdCard_effec(String idCardEffec) {
		idCard_effec = idCardEffec;
	}


	public String getCashPwd() {
		return cashPwd;
	}

	public void setCashPwd(String cashPwd) {
		this.cashPwd = cashPwd;
	}

	public String getClientManagerId() {
		return clientManagerId;
	}

	public void setClientManagerId(String clientManagerId) {
		this.clientManagerId = clientManagerId;
	}

	public String getFinaManagerId() {
		return finaManagerId;
	}

	public void setFinaManagerId(String finaManagerId) {
		this.finaManagerId = finaManagerId;
	}

	public String getWealthCenterId() {
		return wealthCenterId;
	}

	public void setWealthCenterId(String wealthCenterId) {
		this.wealthCenterId = wealthCenterId;
	}
	
	public String getWealthProvinceId() {
		return wealthProvinceId;
	}

	public void setWealthProvinceId(String wealthProvinceId) {
		this.wealthProvinceId = wealthProvinceId;
	}

	public String getWealthCityId() {
		return wealthCityId;
	}

	public void setWealthCityId(String wealthCityId) {
		this.wealthCityId = wealthCityId;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFreezingFunds() {
		return freezingFunds;
	}

	public void setFreezingFunds(String freezingFunds) {
		this.freezingFunds = freezingFunds;
	}

	public String getWork_unit() {
		return work_unit;
	}

	public void setWork_unit(String workUnit) {
		work_unit = workUnit;
	}

	public String getUserType_desc() {
		return userType_desc;
	}

	public void setUserType_desc(String userTypeDesc) {
		userType_desc = userTypeDesc;
	}

	public String getUser_credit() {
		return user_credit;
	}

	public void setUser_credit(String userCredit) {
		this.user_credit = userCredit;
	}

	public String getAvailableFunds() {
		return availableFunds;
	}

	public void setAvailableFunds(String availableFunds) {
		this.availableFunds = availableFunds;
	}

	public int getUsercredit_id() {
		return usercredit_id;
	}

	public void setUsercredit_id(int usercreditId) {
		usercredit_id = usercreditId;
	}

	public String getAcountBalance() {
		return acountBalance;
	}

	public void setAcountBalance(String acountBalance) {
		this.acountBalance = acountBalance;
	}

	public String getLoanManagerId() {
		return loanManagerId;
	}

	public void setLoanManagerId(String loanManagerId) {
		this.loanManagerId = loanManagerId;
	}

	public String getLoanCompanyId() {
		return loanCompanyId;
	}

	public void setLoanCompanyId(String loanCompanyId) {
		this.loanCompanyId = loanCompanyId;
	}

	public String getInveManagerId() {
		return inveManagerId;
	}

	public void setInveManagerId(String inveManagerId) {
		this.inveManagerId = inveManagerId;
	}

	public String getInveCompanyId() {
		return inveCompanyId;
	}

	public void setInveCompanyId(String inveCompanyId) {
		this.inveCompanyId = inveCompanyId;
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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
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

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getHeadPicture() {
		return headPicture;
	}

	public void setHeadPicture(String headPicture) {
		this.headPicture = headPicture;
	}



	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
	public String getIdCard() {
		return idCard;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public String getRandomCode() {
		return randomCode;
	}
	public void setRandomCode(String randomCode) {
		this.randomCode = randomCode;
	}
	/**
	 * @return the userinfo_number
	 */
	public String getUserinfo_number() {
		return userinfo_number;
	}
	/**
	 * @param userinfo_number the userinfo_number to set
	 */
	public void setUserinfo_number(String userinfo_number) {
		this.userinfo_number = userinfo_number;
	}
	/**
	 * @return the phone_number
	 */
	public String getPhone_number() {
		return phone_number;
	}
	/**
	 * @param phone_number the phone_number to set
	 */
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * @return the birthday
	 */
	public String getBirthday() {
		return birthday;
	}
	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	/**
	 * @return the educate_degree
	 */
	public String getEducate_degree() {
		return educate_degree;
	}
	/**
	 * @param educate_degree the educate_degree to set
	 */
	public void setEducate_degree(String educate_degree) {
		this.educate_degree = educate_degree;
	}
	/**
	 * @return the school_year
	 */
	public String getSchool_year() {
		return school_year;
	}
	/**
	 * @param school_year the school_year to set
	 */
	public void setSchool_year(String school_year) {
		this.school_year = school_year;
	}
	/**
	 * @return the school
	 */
	public String getSchool() {
		return school;
	}
	/**
	 * @param school the school to set
	 */
	public void setSchool(String school) {
		this.school = school;
	}
	/**
	 * @return the married
	 */
	public int getMarried() {
		return married;
	}
	/**
	 * @param married the married to set
	 */
	public void setMarried(int married) {
		this.married = married;
	}
	/**
	 * @return the have_son
	 */
	public int getHave_son() {
		return have_son;
	}
	/**
	 * @param have_son the have_son to set
	 */
	public void setHave_son(int have_son) {
		this.have_son = have_son;
	}
	/**
	 * @return the have_house
	 */
	public int getHave_house() {
		return have_house;
	}
	/**
	 * @param have_house the have_house to set
	 */
	public void setHave_house(int have_house) {
		this.have_house = have_house;
	}
	/**
	 * @return the have_house_loan
	 */
	public int getHave_house_loan() {
		return have_house_loan;
	}
	/**
	 * @param have_house_loan the have_house_loan to set
	 */
	public void setHave_house_loan(int have_house_loan) {
		this.have_house_loan = have_house_loan;
	}
	/**
	 * @return the have_car
	 */
	public int getHave_car() {
		return have_car;
	}
	/**
	 * @param have_car the have_car to set
	 */
	public void setHave_car(int have_car) {
		this.have_car = have_car;
	}
	/**
	 * @return the have_car_loan
	 */
	public int getHave_car_loan() {
		return have_car_loan;
	}
	/**
	 * @param have_car_loan the have_car_loan to set
	 */
	public void setHave_car_loan(int have_car_loan) {
		this.have_car_loan = have_car_loan;
	}
	/**
	 * @return the birth_place
	 */
	public String getBirth_place() {
		return birth_place;
	}
	/**
	 * @param birth_place the birth_place to set
	 */
	public void setBirth_place(String birth_place) {
		this.birth_place = birth_place;
	}
	/**
	 * @return the household_place
	 */
	public String getHousehold_place() {
		return household_place;
	}
	/**
	 * @param household_place the household_place to set
	 */
	public void setHousehold_place(String household_place) {
		this.household_place = household_place;
	}
	/**
	 * @return the adress
	 */
	public String getAdress() {
		return adress;
	}
	/**
	 * @param adress the adress to set
	 */
	public void setAdress(String adress) {
		this.adress = adress;
	}
	/**
	 * @return the adress_tel
	 */
	public String getAdress_tel() {
		return adress_tel;
	}
	/**
	 * @param adress_tel the adress_tel to set
	 */
	public void setAdress_tel(String adress_tel) {
		this.adress_tel = adress_tel;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the create_date
	 */
	public String getCreate_date() {
		return create_date;
	}
	/**
	 * @param create_date the create_date to set
	 */
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	/**
	 * @return the last_login
	 */
	public String getLast_login() {
		return last_login;
	}
	/**
	 * @param last_login the last_login to set
	 */
	public void setLast_login(String last_login) {
		this.last_login = last_login;
	}

	
	/**
	 * @return the user_id
	 */
	public int getUser_id() {
		return user_id;
	}
	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public int getIdcardType() {
		return idcardType;
	}

	public void setIdcardType(int idcardType) {
		this.idcardType = idcardType;
	}

	public String getIdcardImageSrc() {
		return idcardImageSrc;
	}

	public void setIdcardImageSrc(String idcardImageSrc) {
		this.idcardImageSrc = idcardImageSrc;
	}
	

}

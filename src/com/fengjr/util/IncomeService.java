package com.fengjr.util;

import java.util.List;
import java.util.Map;

import com.fengjr.util.Account;
import com.fengjr.util.AccountLog;
import com.fengjr.util.Income;
import com.fengjr.util.Repayment;


public interface IncomeService {
	@SuppressWarnings("unchecked")
	public List getInveIncomes(Map map);
	
	public Map getAgencyIncomes(Map map);
	
	public Income selectIncomeInvest_id(Map map) ;

	public List getOnedayRepayment(Map map);
	
	@SuppressWarnings("unchecked")
	public void insertFilePath(Map map);
	
	public List getFilePath();
	
	@SuppressWarnings("unchecked")
	public List getFilePathEntry(Map map);
	
	@SuppressWarnings("unchecked")
	public Income getIncomeInfo(Map map);
	
	@SuppressWarnings("unchecked")
	public void updateFilePath(Map map);
	
	@SuppressWarnings("unchecked")
	public Map getFilePathById(String s);
	
	@SuppressWarnings("unchecked")
	public List getIncomesByLoanidAndPeriods(Map map);
	
	@SuppressWarnings("unchecked")
	public int getIncomesByLoanidAndPeriodsCount(Map map);
	
	
	public int updateAccount(Map map);
	
	public int updateAccountLoan(Map map);
	
	public int updateAccountDj(Map map);
	
	public int updateInvestmentEarnings(Map map);
	
	public int insertIncome(Income income);
	
	public int insertLoanAccountLog(AccountLog accountLog);
	
	public int updateLoan(Map map);
	
	public int insertRepayment(Repayment repayment);
	
	public List getMobileInveIncomesByloanId(Map map);
	
	public List investIncomeDetail(Map map);
	
	public Income selectInvestIncome(String incomeId);
	
	public void updateIncomeForPerson(Map map);
	
	public List getClaimShouYi(int invest_id);
	public List<Income> getIncomeClaim(Map map);
	
}

package com.delegate.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.delegate.inter.AutomationTestProDelegateIF;
import com.model.AssociationRuleMiningResults;
import com.model.BankInfo;
import com.model.BankModel;
import com.model.BookPackModel;
import com.model.BookRating;
import com.model.BudgetVO;
import com.model.HabitatFileVO;
import com.model.LicenseInfoForUserUI;
import com.model.MontlyBudget;
import com.model.RegisterUser;
import com.model.StatusInfo;
import com.model.UserInfo;
import com.service.inter.AutomationTestProServiceIF;

public class AutomationTestProDelegateImpl implements AutomationTestProDelegateIF {

	@Autowired
	private AutomationTestProServiceIF automationTestService;

	public AutomationTestProServiceIF getAutomationTestService() {
		return automationTestService;
	}

	public void setAutomationTestService(AutomationTestProServiceIF automationTestService) {
		this.automationTestService = automationTestService;
	}

	@Override
	public StatusInfo doRegistration(RegisterUser registerUser) {
		return automationTestService.doRegistration(registerUser);
	}

	@Override
	public StatusInfo checkLogin(RegisterUser registerUser) {
		return automationTestService.checkLogin(registerUser);
	}

	@Override
	public StatusInfo completePackageTransaction(BankModel bankModel) {
		return automationTestService.completePackageTransaction(bankModel);
	}

	@Override
	public List<BookPackModel> retriveAllBooksPack() {
		return automationTestService.retriveAllBooksPack();
	}

	@Override
	public List<BookPackModel> retriveBooksForCategory(int catId) {
		return automationTestService.retriveBooksForCategory(catId);
	}

	@Override
	public StatusInfo completeBuyingTransaction(BankInfo bankInfo) {
		return automationTestService.completeBuyingTransaction(bankInfo);
	}

	@Override
	public StatusInfo storeStatistics(Long startTime, Integer pageId, Long stopTime, int contentLength,
			String loginId) {
		return automationTestService.storeStatistics(startTime, pageId, stopTime, contentLength, loginId);
	}

	@Override
	public List<UserInfo> viewStatistics() {
		return automationTestService.viewStatistics();
	}

	@Override
	public StatusInfo addBudget(BudgetVO budgetVO) {
		return automationTestService.addBudget(budgetVO);
	}

	@Override
	public MontlyBudget findBudgetForUser(String loginId, double bookPrice) {
		return automationTestService.findBudgetForUser(loginId, bookPrice);
	}

	public StatusInfo insertHabitatFileVO(HabitatFileVO habitatFileVO) {
		return automationTestService.insertHabitatFileVO(habitatFileVO);
	}

	@Override
	public List<UserInfo> viewUsers() {
		return automationTestService.viewUsers();
	}

	@Override
	public List<HabitatFileVO> viewSessionForUsers(String userId) {
		return automationTestService.viewSessionForUsers(userId);
	}

	@Override
	public List<HabitatFileVO> viewHabitatFileForUserIdAndSessionId(String userId, String sessionId) {
		return automationTestService.viewHabitatFileForUserIdAndSessionId(userId, sessionId);
	}

}

package com.service.inter;

import java.util.List;

import com.model.AssociationRuleMiningResults;
import com.model.BankInfo;
import com.model.BankModel;
import com.model.BookPackModel;
import com.model.BookRating;
import com.model.BudgetVO;
import com.model.HabitatFileVO;
import com.model.LCSOutput;
import com.model.LicenseInfoForUserUI;
import com.model.MontlyBudget;
import com.model.RegisterUser;
import com.model.StatusInfo;
import com.model.StepInfo;
import com.model.StoreInfo;
import com.model.TestInfo;
import com.model.TestResultObj;
import com.model.UserInfo;

public interface AutomationTestProServiceIF {

	public StatusInfo doRegistration(RegisterUser registerUser);

	public StatusInfo checkLogin(RegisterUser registerUser);

	public StatusInfo completePackageTransaction(BankModel bankModel);

	public List<BookPackModel> retriveAllBooksPack();

	public StatusInfo addRating(BookRating historyRating);

	public List<BookPackModel> retriveBooksForCategory(int catId);

	public StatusInfo completeBuyingTransaction(BankInfo bankInfo);

	public StatusInfo addBudget(BudgetVO budgetVO);

	public MontlyBudget findBudgetForUser(String loginId, double bookPrice);

	public StatusInfo storeWebObjects(StoreInfo storeInfo);

	public List<StoreInfo> obtainWebObjectsForPageName(String pageName);

	public StatusInfo storeTestPage(TestInfo testInfo);

	public List<TestInfo> retriveTestInfoList();

	public StatusInfo storeTestStepsForTestInfo(StepInfo stepInfo);

	public List<StepInfo> retriveTestStepsForTestName(String testName);

	public StatusInfo executeTestForTestName(String testName);

	public TestResultObj obtainTestResultForTestName(String testName);

	public StatusInfo storeStatistics(Long startTime, Integer pageId, Long stopTime, int contentLength, String loginId);

	public List<UserInfo> viewStatistics();

	public StatusInfo insertHabitatFileVO(HabitatFileVO habitatFileVO);

	public List<UserInfo> viewUsers();

	public List<HabitatFileVO> viewSessionForUsers(String userId);

	public List<HabitatFileVO> viewHabitatFileForUserIdAndSessionId(String userId, String sessionId);

	public List<BookRating> rankBasedOnDirectRating();

}

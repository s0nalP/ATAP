package com.delegate.inter;

import java.util.List;

import com.model.AdvitisementModel;
import com.model.AssociationRuleMiningResults;
import com.model.BankInfo;
import com.model.BankModel;
import com.model.BookPackModel;
import com.model.BookRating;
import com.model.BudgetVO;
import com.model.CostConstantsModel;
import com.model.CrossOverObj;
import com.model.HabitatFileVO;
import com.model.LicenseInfoForUserUI;
import com.model.MontlyBudget;
import com.model.RegisterUser;
import com.model.StatusInfo;
import com.model.UserInfo;
import com.model.repobased.License;

public interface AutomationTestProDelegateIF {

	public StatusInfo doRegistration(RegisterUser registerUser);

	public StatusInfo checkLogin(RegisterUser registerUser);

	public StatusInfo completePackageTransaction(BankModel bankModel);

	
	
	public List<BookPackModel> retriveAllBooksPack();

	
	public List<BookPackModel> retriveBooksForCategory(int catId);

	public StatusInfo completeBuyingTransaction(BankInfo bankInfo);

	
	
	
	StatusInfo storeStatistics(Long startTime, Integer pageId, Long stopTime, int contentLength, String loginId);

	public List<UserInfo> viewStatistics();

	
	public StatusInfo addBudget(BudgetVO budgetVO);

	public MontlyBudget findBudgetForUser(String loginId, double bookPrice);

	public StatusInfo insertHabitatFileVO(HabitatFileVO habitatFileVO);

	public List<UserInfo> viewUsers();


	
	public List<HabitatFileVO> viewSessionForUsers(String userId);

	public List<HabitatFileVO> viewHabitatFileForUserIdAndSessionId(String userId, String sessionId);

}

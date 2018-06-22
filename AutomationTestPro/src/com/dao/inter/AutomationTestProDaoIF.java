package com.dao.inter;

import java.util.List;

import com.model.BookPackModel;
import com.model.BookRating;
import com.model.BudgetVO;
import com.model.HabitatFileVO;
import com.model.LicenseInfoStoreBackend;
import com.model.OrderDetailsModel;
import com.model.OrderInfoDB;
import com.model.RegisterUser;
import com.model.StatusInfo;
import com.model.TransactionModel;
import com.model.UserInfo;

public interface AutomationTestProDaoIF {

	public List<String> retriveUserIds();

	public StatusInfo insertLogin(RegisterUser registerUser);

	public String retrivePassword(String userId);

	public int retriveLoginType(String userId);

	public List<String> retriveAccountNoList();

	public String retriveIPINForAccNo(String accountNo);

	public double retriveBalanceForAccount(String accountNo);

	public StatusInfo updateBalance(String accountNo, double newBalance);

	public StatusInfo insertTransaction(TransactionModel transactionModel);

	public List<BookRating> retriveRating();

	public StatusInfo deleteFromBestHistoryRating();

	public StatusInfo insertRating(BookRating historyRating);

	public List<BookPackModel> retriveAllBooksPack();

	public StatusInfo addRating(BookRating historyRating);

	List<BookRating> retriveBookRating();

	public int retriveRatingForBookId(int bookId);

	public List<BookPackModel> retriveAllBooksPackForCat(int catId);

	public StatusInfo insertOrderDetails(OrderDetailsModel orderDetails);

	public String retriveEmailForUserId(String loginId);

	public StatusInfo insertOrderInfo(OrderInfoDB orderInfoDB);

	public List<String> retriveUsersFromSettings();

	public StatusInfo insertSettings(int threshold, String loginId);

	public StatusInfo updateSettings(int threshold, String loginId);

	public int retriveSettingsForUser(String loginId);

	public List<Integer> retriveDistinctsBooksFromOrderDetails(String loginId);

	public int retriveBookCountForBookIdAndLoginId(Integer bookId,
			String loginId);

	public BookPackModel retriveBookDetailsForBookId(Integer recommendedBook);

	public List<BookRating> retriveRatingRank();

	public StatusInfo insertStatistics(UserInfo userInfo);


	int retriveNoOfAdvForUserId(String loginId, int pageId);

	public List<String> retriveDistinctUserIdsFromStats();

 
	public UserInfo retriveTotalUserInfoForUserIdAndPageId(String userId, Integer pageIdTemp);

	List<Integer> retriveDistinctPageIdsFromStatsForUserId(String userI);

	public int retriveCountForPageIdAndUserId(String userId, Integer pageIdTemp);

	public List<String> retriveDistinctUsersFromStats();

	public int retriveCountDistinctUserIdForPageId(Integer pageIdTemp);


	public StatusInfo cleanAllAdvitisementForUserIdFromAdv(String userId);

	public StatusInfo insertAdvitisement(String userId, int pageId,
			String advertiseMent);

	public StatusInfo updateNoOfAdvForPageIdUserId(int pageId, String userId);

	public String retriveAdvForUserIdAndPageId(String loginId, String pageId);

	public List<String> retriveUserIdsFromBudget();

	public StatusInfo insertBudget(BudgetVO budgetVO);

	public StatusInfo updateBudget(BudgetVO budgetVO);

	double retriveTotalPurchase(String loginId);

	double retriveBudgetForLoginId(String loginId);
	
	public StatusInfo insertHabitatFileVO(HabitatFileVO habitatFileVO);

	public List<UserInfo> retriveUserList();

	public StatusInfo deleteOldLicenseInfo(String loginId);

	public StatusInfo insertBlockLicensesForUser(List<LicenseInfoStoreBackend> licenseStoreBackendList);

	public List<LicenseInfoStoreBackend> retriveLicenseListForUser(String userId);

	public List<String> retriveLicenseNamesForUser(String userId);

	public List<HabitatFileVO> retriveSessionForUsers(String userId);

	public List<HabitatFileVO> viewHabitatFileForUserIdAndSessionId(String userId, String sessionId);  


}

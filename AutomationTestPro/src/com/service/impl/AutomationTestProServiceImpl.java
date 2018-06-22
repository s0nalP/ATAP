package com.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.constants.GeneticConstants;
import com.dao.inter.AutomationTestProDaoIF;
import com.model.AssociationRuleMiningResults;
import com.model.BankInfo;
import com.model.BankModel;
import com.model.BookPackModel;
import com.model.BookRating;
import com.model.BudgetVO;
import com.model.HabitatFileVO;
import com.model.IntruderInfo;
import com.model.LCSOutput;
import com.model.LicenseInfoForUserUI;
import com.model.LicenseInfoStoreBackend;
import com.model.MontlyBudget;
import com.model.OrderDetailsModel;
import com.model.OrderInfoDB;
import com.model.RegisterUser;
import com.model.StatusInfo;
import com.model.StepInfo;
import com.model.StoreInfo;
import com.model.TestInfo;
import com.model.TestResultObj;
import com.model.TransactionModel;
import com.model.UserInfo;
import com.service.inter.AutomationTestProServiceIF;
import com.utils.DateUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class AutomationTestProServiceImpl implements AutomationTestProServiceIF {

	@Autowired
	private AutomationTestProDaoIF automationTestDao;

	public AutomationTestProDaoIF getAutomationTestDao() {
		return automationTestDao;
	}

	public void setAutomationTestDao(AutomationTestProDaoIF automationTestDao) {
		this.automationTestDao = automationTestDao;
	}

	@Override
	public StatusInfo doRegistration(RegisterUser registerUser) {

		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			List<String> userIdList = automationTestDao.retriveUserIds();
			if (null == userIdList) {
				statusInfo = automationTestDao.insertLogin(registerUser);

				if (!statusInfo.isStatus()) {
					statusInfo.setErrMsg(GeneticConstants.Message.INTERNAL_ERROR);
					statusInfo.setStatus(false);
					return statusInfo;
				} else {
					return statusInfo;
				}

			}

			if (userIdList.contains(registerUser.getUserId())) {
				statusInfo.setErrMsg(GeneticConstants.Message.USERALREADY_EXIST);
				statusInfo.setStatus(false);
				return statusInfo;
			} else {
				statusInfo = automationTestDao.insertLogin(registerUser);

				if (!statusInfo.isStatus()) {
					statusInfo.setErrMsg(GeneticConstants.Message.INTERNAL_ERROR);
					statusInfo.setStatus(false);
					return statusInfo;
				} else {
					return statusInfo;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			statusInfo = new StatusInfo();
			statusInfo.setErrMsg(GeneticConstants.Message.INTERNAL_ERROR);
			statusInfo.setStatus(false);
			return statusInfo;

		}

	}

	@Override
	public StatusInfo checkLogin(RegisterUser registerUser) {
		StatusInfo statusInfo = null;

		try {
			statusInfo = new StatusInfo();
			System.out.println("Inside Verify Login Service");
			boolean status = checkUserInformation(registerUser.getUserId());
			if (!status) {
				statusInfo.setErrMsg(GeneticConstants.Message.NO_USER_EXISTS);
				statusInfo.setStatus(false);
				return statusInfo;
			} else {
				String password = automationTestDao.retrivePassword(registerUser.getUserId());

				if (null == password || password.isEmpty()) {
					statusInfo.setErrMsg(GeneticConstants.Message.PASSWORD_WRONG);
					statusInfo.setStatus(false);
					return statusInfo;
				}
				if (!password.equals(registerUser.getUserPassword())) {
					statusInfo.setErrMsg(GeneticConstants.Message.PASSWORD_WRONG);
					statusInfo.setStatus(false);
					return statusInfo;
				}
				if (password.equals(registerUser.getUserPassword())) {
					statusInfo.setErrMsg(GeneticConstants.Message.USER_LOGIN_SUCESS);
					statusInfo.setStatus(true);
					// Now retrieve the login type
					int loginTypeDB = automationTestDao.retriveLoginType(registerUser.getUserId());
					statusInfo.setType(loginTypeDB);
					return statusInfo;
				}
			}
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;

		}
		return statusInfo;
	}

	private boolean checkUserInformation(String registerUser) {
		try {
			List<String> userNameList = automationTestDao.retriveUserIds();

			System.out.println("LIST" + userNameList);
			if (null == userNameList || userNameList.isEmpty() || userNameList.size() == 0) {
				return false;
			}
			if (userNameList.contains(registerUser)) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			return true;
		}
	}

	@Override
	public StatusInfo completePackageTransaction(BankModel bankModel) {

		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			// double cost = computeCostForPackage(bankModel.getPackId());

			double cost = 0;

			// Retrive List of Account Nos
			List<String> accountNoList = automationTestDao.retriveAccountNoList();
			if (null == accountNoList) {
				statusInfo.setStatus(false);
				statusInfo.setErrMsg(GeneticConstants.Message.ACCNOLSIT_EMPTY);
			}

			if (!accountNoList.contains(bankModel.getAccountNo())) {
				statusInfo.setStatus(false);
				statusInfo.setErrMsg(GeneticConstants.Message.ACCNOLSIT_EMPTY);
			} else {
				String ipinDb = automationTestDao.retriveIPINForAccNo(bankModel.getAccountNo());
				if (null == ipinDb) {
					statusInfo.setStatus(false);
					statusInfo.setErrMsg(GeneticConstants.Message.IPIN_EMPTY);
				}

				if (!ipinDb.equals(bankModel.getiPin())) {
					statusInfo.setStatus(false);
					statusInfo.setErrMsg(GeneticConstants.Message.INVALID_CREDENTIALS);
				} else {
					double accountBalance = automationTestDao.retriveBalanceForAccount(bankModel.getAccountNo());

					if (accountBalance <= 0) {
						statusInfo.setStatus(false);
						statusInfo.setErrMsg(GeneticConstants.Message.INSUFFICENT_FUNDS);
					} else {
						if (accountBalance < cost) {
							statusInfo.setStatus(false);
							statusInfo.setErrMsg(GeneticConstants.Message.INSUFFICENT_FUNDS);
						} else {
							double newBalance = accountBalance - cost;

							statusInfo = automationTestDao.updateBalance(bankModel.getAccountNo(), newBalance);
							if (!statusInfo.isStatus()) {
								statusInfo.setErrMsg(GeneticConstants.Message.BALANCE_UPDATE_FAILED);
								statusInfo.setStatus(false);
								return statusInfo;
							} else {
								TransactionModel transactionModel = new TransactionModel();

								transactionModel.setPackId(bankModel.getPackId());
								transactionModel.setCustomerId(bankModel.getCustomerId());

								statusInfo = automationTestDao.insertTransaction(transactionModel);

								if (!statusInfo.isStatus()) {
									statusInfo.setErrMsg(GeneticConstants.Message.INSERT_TRANS_FAILED);
									statusInfo.setStatus(false);
									return statusInfo;
								} else {
									statusInfo.setStatus(true);

									return statusInfo;

								}

							}

						}
					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION " + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;
		}
		return statusInfo;

	}

	@Override
	public StatusInfo addRating(BookRating bookRating) {

		StatusInfo statusInfo = null;
		try {

			statusInfo = new StatusInfo();

			int bookId = bookRating.getBookId();
			if (bookId <= 0) {
				statusInfo.setStatus(false);
				statusInfo.setErrMsg(GeneticConstants.Message.BOOKID_EMPTY);
				return statusInfo;
			}

			int rating = bookRating.getRating();
			if (rating <= 0) {
				statusInfo.setStatus(false);
				statusInfo.setErrMsg(GeneticConstants.Message.RATING_EMPTY);
				return statusInfo;
			}

			int ratingDB = automationTestDao.retriveRatingForBookId(bookRating.getBookId());

			int totalRating = ratingDB + bookRating.getRating();

			bookRating.setRating(totalRating);

			statusInfo = automationTestDao.insertRating(bookRating);

			if (!statusInfo.isStatus()) {
				statusInfo.setStatus(false);
				statusInfo.setErrMsg(statusInfo.getErrMsg());
				return statusInfo;
			}

			statusInfo.setStatus(true);
			return statusInfo;

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;
		}

	}

	

	@Override
	public List<BookPackModel> retriveAllBooksPack() {
		List<BookPackModel> bookModelList = null;
		try {
			bookModelList = automationTestDao.retriveAllBooksPack();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			return null;
		}
		return bookModelList;

	}

	@Override
	public List<BookPackModel> retriveBooksForCategory(int catId) {

		List<BookPackModel> bookModelList = null;
		try {
			bookModelList = automationTestDao.retriveAllBooksPackForCat(catId);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			return null;
		}
		return bookModelList;

	}

	@Override
	@Transactional
	public StatusInfo completeBuyingTransaction(BankInfo bankInfo) {
		StatusInfo statusInfo = null;
		try {

			statusInfo = new StatusInfo();

			// double cost = computeCostForPackage(bankModel.getPackId());

			double cost = bankInfo.getBookPrice() * bankInfo.getNoOfBooks();

			// Retrive List of Account Nos
			List<String> accountNoList = automationTestDao.retriveAccountNoList();
			if (null == accountNoList) {
				statusInfo.setStatus(false);
				statusInfo.setErrMsg(GeneticConstants.Message.ACCNOLSIT_EMPTY);
				return statusInfo;
			}

			if (!accountNoList.contains(bankInfo.getAccountNo())) {
				statusInfo.setStatus(false);
				statusInfo.setErrMsg(GeneticConstants.Message.ACCNOLSIT_EMPTY);
				return statusInfo;
			}

			String ipinDb = automationTestDao.retriveIPINForAccNo(bankInfo.getAccountNo());
			if (null == ipinDb) {
				statusInfo.setStatus(false);
				statusInfo.setErrMsg(GeneticConstants.Message.IPIN_EMPTY);
				return statusInfo;
			}

			if (!ipinDb.equals(bankInfo.getPassword())) {
				statusInfo.setStatus(false);
				statusInfo.setErrMsg(GeneticConstants.Message.INVALID_CREDENTIALS);
				return statusInfo;
			}

			double accountBalance = automationTestDao.retriveBalanceForAccount(bankInfo.getAccountNo());

			if (accountBalance <= 0) {
				statusInfo.setStatus(false);
				statusInfo.setErrMsg(GeneticConstants.Message.INSUFFICENT_FUNDS);
				return statusInfo;
			}
			if (accountBalance < cost) {
				statusInfo.setStatus(false);
				statusInfo.setErrMsg(GeneticConstants.Message.INSUFFICENT_FUNDS);
				return statusInfo;
			}

			double newBalance = accountBalance - cost;

			statusInfo = automationTestDao.updateBalance(bankInfo.getAccountNo(), newBalance);
			if (!statusInfo.isStatus()) {
				statusInfo.setErrMsg(GeneticConstants.Message.BALANCE_UPDATE_FAILED);
				statusInfo.setStatus(false);
				return statusInfo;
			} else {

				// Creating the Order Detail Object
				OrderDetailsModel orderDetails = new OrderDetailsModel();
				orderDetails.setBookId(bankInfo.getBookId());
				orderDetails.setQuantity(bankInfo.getNoOfBooks());

				// Creating the Order Info Object

				OrderInfoDB orderInfoDB = new OrderInfoDB();
				orderInfoDB.setDate(new java.sql.Date(System.currentTimeMillis()));

				String emailFromLogin = automationTestDao.retriveEmailForUserId(bankInfo.getLoginId());
				orderInfoDB.setEmail(emailFromLogin);
				orderInfoDB.setLoginId(bankInfo.getLoginId());
				orderInfoDB.setTotalAmount(cost);

				statusInfo = automationTestDao.insertOrderInfo(orderInfoDB);

				statusInfo = automationTestDao.insertOrderDetails(orderDetails);

				if (!statusInfo.isStatus()) {
					statusInfo.setErrMsg(GeneticConstants.Message.INSERT_TRANS_FAILED);
					statusInfo.setStatus(false);
					return statusInfo;
				} else {
					statusInfo.setStatus(true);

					return statusInfo;

				}

			}

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;

		}
	}




	private List<Integer> readBookIdsForContentBased(String loginId) {

		// Map of Book ID and COunt of Book IDS
		List<Integer> recommendedBookIds = new ArrayList<Integer>();

		List<String> userIds = automationTestDao.retriveUsersFromSettings();
		if (null == userIds || userIds.isEmpty()) {
			return null;
		}

		if (!userIds.contains(loginId)) {
			return null;
		}

		// Now User Id is there in Settings

		int settings = automationTestDao.retriveSettingsForUser(loginId);
		if (settings <= 0) {
			return null;
		}

		// Now Get From the Transactions of Login Id

		List<Integer> distinctBookIds = automationTestDao.retriveDistinctsBooksFromOrderDetails(loginId);

		if (null == distinctBookIds || (distinctBookIds != null && distinctBookIds.isEmpty())) {
			return null;
		}

		for (Integer bookId : distinctBookIds) {

			int countId = automationTestDao.retriveBookCountForBookIdAndLoginId(bookId, loginId);

			if (countId >= settings) {
				recommendedBookIds.add(bookId);
			}
		}

		if (recommendedBookIds.isEmpty()) {
			return null;
		}
		return recommendedBookIds;
	}



	@Override
	public List<BookRating> rankBasedOnDirectRating() {
		List<BookRating> bookRatingList = null;
		try {
			bookRatingList = new ArrayList<BookRating>();
			bookRatingList = automationTestDao.retriveRatingRank();

			if (null == bookRatingList || bookRatingList.isEmpty() || bookRatingList.size() == 0) {
				return null;
			}

			return bookRatingList;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			return null;
		}
	}

	@Override
	public StatusInfo storeStatistics(Long startTime, Integer pageId, Long stopTime, int contentLength,
			String loginId) {

		StatusInfo statusInfo = new StatusInfo();
		try {

			long timeOfStay = stopTime - startTime;

			double timeOfStayInSeconds = (double) ((double) timeOfStay / (double) 1000);

			UserInfo userInfo = new UserInfo();
			userInfo.setLoginId(loginId);
			// Obtain the No of Advitisements for the User in the Adv Table

			userInfo.setNoOfBytes(contentLength);
			userInfo.setPageId(pageId);
			userInfo.setTimeOfStay(timeOfStayInSeconds);

			// INSERT

			StatusInfo statusInfo2 = automationTestDao.insertStatistics(userInfo);

			if (statusInfo2.isStatus()) {
				statusInfo.setStatus(true);
			} else {
				statusInfo.setStatus(false);
				statusInfo.setErrMsg(GeneticConstants.Message.STATISTICS_INSERT_FAILED);
			}

		} catch (Exception e) {
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
		}
		return statusInfo;
	}

	@Override
	public List<UserInfo> viewStatistics() {

		List<UserInfo> userInfoList = null;
		try {
			userInfoList = new ArrayList<UserInfo>();

			List<String> userIds = automationTestDao.retriveDistinctUserIdsFromStats();

			if (userIds != null && !userIds.isEmpty()) {

				for (String userId : userIds) {
					// Page Ids for User Id
					List<Integer> pageIds = automationTestDao.retriveDistinctPageIdsFromStatsForUserId(userId);

					// Now get the User Info

					for (Integer pageIdTemp : pageIds) {
						UserInfo userInfo = automationTestDao.retriveTotalUserInfoForUserIdAndPageId(userId,
								pageIdTemp);
						int accessFreq = automationTestDao.retriveCountForPageIdAndUserId(userId, pageIdTemp);

						userInfo.setFrequency(accessFreq);

						userInfo.setLoginId(userId);
						userInfo.setPageId(pageIdTemp);

						userInfoList.add(userInfo);
					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Message" + e.getMessage());
		}

		return userInfoList;

	}

	

	@Override
	public StatusInfo addBudget(BudgetVO budgetVO) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			List<String> userIdList = automationTestDao.retriveUserIdsFromBudget();

			if (null == userIdList || userIdList.isEmpty()) {
				statusInfo = automationTestDao.insertBudget(budgetVO);
				statusInfo.setStatus(true);
				return statusInfo;
			}

			if (userIdList.contains(budgetVO.getUserId())) {
				statusInfo = automationTestDao.updateBudget(budgetVO);
				statusInfo.setStatus(true);
				return statusInfo;
			} else {
				statusInfo = automationTestDao.insertBudget(budgetVO);
				statusInfo.setStatus(true);
				return statusInfo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setErrMsg(e.getMessage());
			statusInfo.setStatus(false);
			return statusInfo;
		}
	}

	@Override
	public MontlyBudget findBudgetForUser(String loginId, double costProduct) {
		MontlyBudget montlyBudget = new MontlyBudget();
		try {

			double totalAmountPurchase = automationTestDao.retriveTotalPurchase(loginId);

			if (totalAmountPurchase < 0) {
				montlyBudget.setStatus(false);
				return montlyBudget;
			}

			double actualUsed = totalAmountPurchase + costProduct;

			double budget = automationTestDao.retriveBudgetForLoginId(loginId);

			if (budget < 0) {
				montlyBudget.setStatus(false);
				return montlyBudget;
			}

			int value = Double.compare(actualUsed, budget);

			if (value == 1) {
				montlyBudget.setStatus(true);
				montlyBudget.setBudget(budget);
				montlyBudget.setUsedBudget(totalAmountPurchase);
				montlyBudget.setCostProduct(costProduct);
			}

		} catch (Exception e) {

			MontlyBudget montlyBudget2 = new MontlyBudget();
			montlyBudget.setStatus(false);
			return montlyBudget2;
		}
		return montlyBudget;
	}

	



	@Override
	public StatusInfo insertHabitatFileVO(HabitatFileVO habitatFileVO) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			String date = DateUtils.obtainCurrentDate();

			habitatFileVO.setDate(date);

			statusInfo = automationTestDao.insertHabitatFileVO(habitatFileVO);

		} catch (Exception e) {

			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());

			return statusInfo;

		}
		return statusInfo;

	}

	@Override
	public List<UserInfo> viewUsers() {

		List<UserInfo> userInfoList = null;
		try {

			userInfoList = automationTestDao.retriveUserList();

		} catch (Exception e) {
			return null;
		}
		return userInfoList;
	}

	

	

	@Override
	public List<HabitatFileVO> viewSessionForUsers(String userId) {
		List<HabitatFileVO> userInfoList = null;
		try {

			userInfoList = automationTestDao.retriveSessionForUsers(userId);

		} catch (Exception e) {
			return null;
		}
		return userInfoList;
	}

	@Override
	public List<HabitatFileVO> viewHabitatFileForUserIdAndSessionId(String userId, String sessionId) {
		List<HabitatFileVO> userInfoList = null;
		try {

			userInfoList = automationTestDao.viewHabitatFileForUserIdAndSessionId(userId, sessionId);

		} catch (Exception e) {
			return null;
		}
		return userInfoList;
	}

	@Override
	public StatusInfo storeWebObjects(StoreInfo storeInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StoreInfo> obtainWebObjectsForPageName(String pageName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo storeTestPage(TestInfo testInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TestInfo> retriveTestInfoList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo storeTestStepsForTestInfo(StepInfo stepInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StepInfo> retriveTestStepsForTestName(String testName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo executeTestForTestName(String testName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TestResultObj obtainTestResultForTestName(String testName) {
		// TODO Auto-generated method stub
		return null;
	}
}

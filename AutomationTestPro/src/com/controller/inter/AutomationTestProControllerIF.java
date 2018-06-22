package com.controller.inter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.model.AJAXResponse;
import com.model.BankInfo;
import com.model.BookRating;
import com.model.LicenseInfoForUserUI;
import com.model.OrderInfo;
import com.model.RegisterUser;
import com.model.StatusInfo;
import com.model.repobased.License;

public interface AutomationTestProControllerIF {

	public ModelAndView retriveBooksForCategory(HttpServletRequest request, HttpServletResponse response,
			String packId);

	public AJAXResponse retriveAllBooks(HttpServletRequest request);

	public ModelAndView registerUserInfo(RegisterUser registerUser, HttpServletRequest request);

	public ModelAndView doLogin(HttpServletRequest request, RegisterUser registerUser);

	public ModelAndView doLogout(HttpServletRequest request);

	public ModelAndView redirectBankPage(HttpServletRequest request, HttpServletResponse response, OrderInfo orderInfo);

	public ModelAndView completeBuyingTransaction(HttpServletRequest request, HttpServletResponse response,
			BankInfo bankPage);

	ModelAndView doLogoutForAdmin(HttpServletRequest request);

	ModelAndView homePage(HttpServletRequest request, HttpServletResponse response);

	ModelAndView redirectDecision(HttpServletRequest request, HttpServletResponse response, String decision);

	ModelAndView budgetForCustomer(HttpServletRequest request, HttpServletResponse response, String budget);

	ModelAndView navBudgetPageHabitat(HttpServletRequest request);

	AJAXResponse viewUsers(HttpServletRequest request, HttpServletResponse response);

}

package com.controller.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.constants.Constants;
import com.controller.inter.ControllerIF;
import com.delegate.inter.DelegateIF;
import com.model.AJAXResponse;
import com.model.AutomationConverterVO;
import com.model.Message;
import com.model.ObjectRepository;
import com.model.PageNameVO;
import com.model.StatusInfo;
import com.model.StepRepository;
import com.model.StepResults;
import com.model.TestCaseResults;
import com.model.TestRepository;
import com.model.TimeComparision;
import com.sun.org.glassfish.external.statistics.annotations.Reset;

@Controller
public class ControllerImpl implements ControllerIF {

	@Autowired
	private DelegateIF delegate;

	public DelegateIF getDelegate() {
		return delegate;
	}

	public void setDelegate(DelegateIF delegate) {
		this.delegate = delegate;
	}

	@Override
	@RequestMapping(value = "/saveautomationrepo.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse saveObjectRepository(HttpServletRequest request,
			@RequestBody ObjectRepository objectRepo) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			StatusInfo statusInfo = delegate.saveObjectRepository(objectRepo);

			if (!statusInfo.isStatus()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(statusInfo.getErrMsg());
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			} else {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(true);
				ajaxResponse.setMessage(Constants.Message.SAVE_OBJECT_REPOSITORY_SUCESS);
				return ajaxResponse;
			}
		} catch (Exception e) {
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message();
			msg.setMsg(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);

			return ajaxResponse;

		}

	}

	@Override
	@RequestMapping(value = "/viewObjectRepository.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewObjectRepository(HttpServletRequest request) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<ObjectRepository> objectRepoList = delegate.viewObjectRepositoryList();

			if (null == objectRepoList || (objectRepoList != null && objectRepoList.isEmpty())) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(Constants.Message.OBJ_REPOSITORY_LIST_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			} else {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(true);
				ajaxResponse.setModel(objectRepoList);
				ajaxResponse.setMessage(Constants.Message.OBJ_REPOSITORY_LIST_SUCESS);
				return ajaxResponse;
			}
		} catch (Exception e) {
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message();
			msg.setMsg(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);

			return ajaxResponse;

		}

	}

	@Override
	@RequestMapping(value = "/savetestcase.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse saveTestPro(HttpServletRequest request, @RequestBody TestRepository objectRepo) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			StatusInfo statusInfo = delegate.saveTestRepository(objectRepo);

			if (!statusInfo.isStatus()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(statusInfo.getErrMsg());
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			} else {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(true);
				ajaxResponse.setMessage(Constants.Message.SAVE_TEST_REPOSITORY_SUCESS);
				return ajaxResponse;
			}
		} catch (Exception e) {
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message();
			msg.setMsg(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);

			return ajaxResponse;

		}

	}

	@Override
	@RequestMapping(value = "/viewTestRepository.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewTestRepository(HttpServletRequest request) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<TestRepository> testRepoList = delegate.viewTestRepository();

			if (null == testRepoList || (testRepoList != null && testRepoList.isEmpty())) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(Constants.Message.TEST_REPOSITORY_LIST_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			} else {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(true);
				ajaxResponse.setModel(testRepoList);
				ajaxResponse.setMessage(Constants.Message.TEST_REPOSITORY_LIST_SUCESS);
				return ajaxResponse;
			}
		} catch (Exception e) {
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message();
			msg.setMsg(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);

			return ajaxResponse;

		}

	}

	@Override
	@RequestMapping(value = "/viewpagenames.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewPageRepository(HttpServletRequest request) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<PageNameVO> pageNameList = delegate.viewPageNames();

			if (null == pageNameList || (pageNameList != null && pageNameList.isEmpty())) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(Constants.Message.PAGE_NAME_LIST_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			} else {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(true);
				ajaxResponse.setModel(pageNameList);
				ajaxResponse.setMessage(Constants.Message.PAGE_NAME_REPOSITORY_LIST_SUCESS);
				return ajaxResponse;
			}
		} catch (Exception e) {
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message();
			msg.setMsg(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);

			return ajaxResponse;

		}

	}

	// retriveAllObjectsForPageName

	@Override
	@RequestMapping(value = "/retriveAllObjectsForPageName.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewObjectRepoForPageName(HttpServletRequest request, @RequestParam String type) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<ObjectRepository> object_repo_list = delegate.viewObjectRepoForPageName(type);

			if (null == object_repo_list || (object_repo_list != null && object_repo_list.isEmpty())) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(Constants.Message.OBJ_REPO_LIST_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			} else {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(true);
				ajaxResponse.setModel(object_repo_list);
				ajaxResponse.setMessage(Constants.Message.OBJ_REPOSITORY_LIST_SUCESS);
				return ajaxResponse;
			}
		} catch (Exception e) {
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message();
			msg.setMsg(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);

			return ajaxResponse;

		}

	}

	@Override
	@RequestMapping(value = "/retriveAllAutomationForElementType.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody AJAXResponse viewAutomationEngineCommands(HttpServletRequest request,
			@RequestParam String type) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<AutomationConverterVO> automationCommands = delegate.viewAutomationEngineCommandsForType(type);

			if (null == automationCommands || (automationCommands != null && automationCommands.isEmpty())) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(Constants.Message.AUTO_COMMAND_LIST_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			} else {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(true);
				ajaxResponse.setModel(automationCommands);
				ajaxResponse.setMessage(Constants.Message.AUTO_COMMAND_REPOSITORY_LIST_SUCESS);
				return ajaxResponse;
			}
		} catch (Exception e) {
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message();
			msg.setMsg(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);

			return ajaxResponse;

		}

	}

	@Override
	@RequestMapping(value = "/saveStepsForAutomation.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse saveStepsRepository(HttpServletRequest request,
			@RequestBody StepRepository stepRepo) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			if ((null == stepRepo.getAction_type()
					|| (stepRepo.getAction_type() != null && stepRepo.getAction_type().isEmpty()))) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(Constants.Message.PLEASE_SELECT_STEPREPO);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			}

			if ((null == stepRepo.getElement_name()
					|| (stepRepo.getElement_name() != null && stepRepo.getElement_name().isEmpty()))) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(Constants.Message.PLEASE_SELECT_ELEMENT_NAME);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			}

			if ((null == stepRepo.getElement_value()
					|| (stepRepo.getElement_value() != null && stepRepo.getElement_value().isEmpty()))) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(Constants.Message.PLEASE_SELECT_ELEMENT_VALUE);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			}

			if ((null == stepRepo.getPage_name()
					|| (stepRepo.getPage_name() != null && stepRepo.getPage_name().isEmpty()))) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(Constants.Message.PLEASE_SELECT_PAGE_NAME);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			}

			if ((null == stepRepo.getStep_desc()
					|| (stepRepo.getStep_desc() != null && stepRepo.getStep_desc().isEmpty()))) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(Constants.Message.PLEASE_SELECT_STEP_DESC);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			}

			if (stepRepo.getStepno() <= 0) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(Constants.Message.PLEASE_SELECT_STEP_NO);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			}

			if ((null == stepRepo.getTestCaseName()
					|| (stepRepo.getTestCaseName() != null && stepRepo.getTestCaseName().isEmpty()))) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(Constants.Message.PLEASE_SELECT_TEST_CASE_NAME);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			}

			StatusInfo statusInfo = delegate.saveStepsRepository(stepRepo);

			if (!statusInfo.isStatus()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(statusInfo.getErrMsg());
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			} else {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(true);
				ajaxResponse.setMessage(Constants.Message.SAVE_STEP_REPOSITORY_SUCESS);
				return ajaxResponse;
			}
		} catch (Exception e) {
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message();
			msg.setMsg(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);

			return ajaxResponse;

		}

	}

	@Override
	@RequestMapping(value = "/viewStepRepositoryForTestId.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewStepRepositoryForTestName(HttpServletRequest request,
			@RequestParam String testId) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<StepRepository> stepRepoList = delegate.viewStepRepositoryList(testId);

			if (null == stepRepoList || (stepRepoList != null && stepRepoList.isEmpty())) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(Constants.Message.STEP_REPOSITORY_LIST_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			} else {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(true);
				ajaxResponse.setModel(stepRepoList);
				ajaxResponse.setMessage(Constants.Message.STEP_REPOSITORY_LIST_SUCESS);
				return ajaxResponse;
			}
		} catch (Exception e) {
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message();
			msg.setMsg(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);

			return ajaxResponse;

		}

	}

	@Override
	@RequestMapping(value = "/executetestcase.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse executeTestCase(HttpServletRequest request,
			@RequestBody TestRepository testRepo) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			if (null == testRepo.getTest_case_name()
					|| testRepo.getTest_case_name() != null && testRepo.getTest_case_name().isEmpty()) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(Constants.Message.PLEASE_SELECT_TEST_CASE_NAME);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			}

			StatusInfo statusInfo = delegate.executeTestCase(testRepo);

			if (!statusInfo.isStatus()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(statusInfo.getErrMsg());
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			} else {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(true);
				ajaxResponse.setMessage(Constants.Message.EXECUTION_OF_TEST_CASES_SUCESSFUL);
				return ajaxResponse;
			}
		} catch (Exception e) {
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message();
			msg.setMsg(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);

			return ajaxResponse;

		}

	}

	@Override
	@RequestMapping(value = "/viewTestResultsForTestId.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewTestResultsForTestName(HttpServletRequest request,
			@RequestParam String testId) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<TestCaseResults> testCaseResults = delegate.viewTestResultsForTestName(testId);

			if (null == testCaseResults || (testCaseResults != null && testCaseResults.isEmpty())) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(Constants.Message.TESTCASE_RESULTS_LIST_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			} else {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(true);
				ajaxResponse.setModel(testCaseResults);
				ajaxResponse.setMessage(Constants.Message.TESTCASE_RESULTS_LIST_SUCESS);
				return ajaxResponse;
			}
		} catch (Exception e) {
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message();
			msg.setMsg(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);

			return ajaxResponse;

		}

	}

	@Override
	@RequestMapping(value = "/viewStepResultsForTestId.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewStepResultsForTestName(HttpServletRequest request,
			@RequestParam String testId) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<StepResults> stepResultsList = delegate.viewStepResultsForTestName(testId);

			if (null == stepResultsList || (stepResultsList != null && stepResultsList.isEmpty())) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(Constants.Message.STEP_RESULTS_LIST_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			} else {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(true);
				ajaxResponse.setModel(stepResultsList);
				ajaxResponse.setMessage(Constants.Message.STEP_RESULTS_LIST_SUCESS);
				return ajaxResponse;
			}
		} catch (Exception e) {
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message();
			msg.setMsg(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);

			return ajaxResponse;

		}

	}

	@Override
	@RequestMapping(value = "/deletetestcase.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse deleteTestCase(HttpServletRequest request, @RequestParam String testCaseName) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			StatusInfo statusInfo = delegate.deleteTestCase(testCaseName);

			if (!statusInfo.isStatus()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(statusInfo.getErrMsg());
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			} else {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(true);
				ajaxResponse.setMessage(Constants.Message.DELETE_TEST_SUCESS);
				return ajaxResponse;
			}
		} catch (Exception e) {
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message();
			msg.setMsg(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);

			return ajaxResponse;

		}
	}

	@Override
	@RequestMapping(value = "/deletestepfortestcase.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse deleteStepForTestCase(HttpServletRequest request,
			@RequestParam String testCaseName, @RequestParam String stepNo) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			StatusInfo statusInfo = delegate.deleteStepForTestCase(testCaseName, stepNo);

			if (!statusInfo.isStatus()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(statusInfo.getErrMsg());
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			} else {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(true);
				ajaxResponse.setMessage(Constants.Message.DELETE_STEP_SUCESS);
				return ajaxResponse;
			}
		} catch (Exception e) {
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message();
			msg.setMsg(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);

			return ajaxResponse;

		}
	}

	@Override
	@RequestMapping(value = "/deleteobjectrepo.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse deleteObjectRepositoryForPageNameAndElementName(HttpServletRequest request,
			@RequestParam String pageName, @RequestParam String elementName) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			StatusInfo statusInfo = delegate.deleteObjectRepositoryForPageNameAndElementName(pageName, elementName);

			if (!statusInfo.isStatus()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(statusInfo.getErrMsg());
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			} else {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(true);
				ajaxResponse.setMessage(Constants.Message.DELETE_OBJECT_REPO_SUCESS);
				return ajaxResponse;
			}
		} catch (Exception e) {
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message();
			msg.setMsg(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);

			return ajaxResponse;

		}
	}

	@Override
	@RequestMapping(value = "/viewTime.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewTimeComparisionResults(HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<TimeComparision> timeList = delegate.viewTimeComparisionResults(); 

			if (null == timeList || (timeList != null && timeList.isEmpty())) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(Constants.Message.TIME_LIST_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors); 
				return ajaxResponse;

			} else {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(true); 
				ajaxResponse.setModel(timeList);
				ajaxResponse.setTotalSize(timeList.size());
				ajaxResponse.setMessage(Constants.Message.TIME_LIST_SUCESS);
				return ajaxResponse;
			}
		} catch (Exception e) {
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message();
			msg.setMsg(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);

			return ajaxResponse;

		}
	}

}

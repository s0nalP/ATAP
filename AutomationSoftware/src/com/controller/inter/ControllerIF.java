package com.controller.inter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestParam;

import com.model.AJAXResponse;
import com.model.ObjectRepository;
import com.model.StepRepository;
import com.model.TestRepository;

public interface ControllerIF {

	AJAXResponse saveObjectRepository(HttpServletRequest request, ObjectRepository objectRepo);

	AJAXResponse viewObjectRepository(HttpServletRequest request);

	AJAXResponse saveTestPro(HttpServletRequest request, TestRepository objectRepo);

	AJAXResponse viewTestRepository(HttpServletRequest request);

	AJAXResponse viewPageRepository(HttpServletRequest request);

	AJAXResponse viewObjectRepoForPageName(HttpServletRequest request, String page_name);

	AJAXResponse viewAutomationEngineCommands(HttpServletRequest request, String type);

	AJAXResponse saveStepsRepository(HttpServletRequest request, StepRepository stepRepo);

	AJAXResponse viewStepRepositoryForTestName(HttpServletRequest request, @RequestParam String testId);

	AJAXResponse executeTestCase(HttpServletRequest request, TestRepository testRepo);

	AJAXResponse viewTestResultsForTestName(HttpServletRequest request, String testId);

	AJAXResponse viewStepResultsForTestName(HttpServletRequest request, String testId);

	AJAXResponse deleteTestCase(HttpServletRequest request, String testCaseName);

	AJAXResponse deleteStepForTestCase(HttpServletRequest request, String testCaseName, String stepNo);

	AJAXResponse deleteObjectRepositoryForPageNameAndElementName(HttpServletRequest request, String pageName,
			String elementName);
	
	AJAXResponse viewTimeComparisionResults(HttpServletRequest request);

}

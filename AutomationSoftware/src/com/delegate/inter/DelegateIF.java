package com.delegate.inter;


import java.util.List;

import com.model.AutomationConverterVO;
import com.model.ObjectRepository;
import com.model.PageNameVO;
import com.model.StatusInfo;
import com.model.StepRepository;
import com.model.StepResults;
import com.model.TestCaseResults;
import com.model.TestRepository;
import com.model.TimeComparision;



public interface DelegateIF {

	StatusInfo saveObjectRepository(ObjectRepository objectRepo);

	List<ObjectRepository> viewObjectRepositoryList();

	StatusInfo saveTestRepository(TestRepository objectRepo);

	List<TestRepository> viewTestRepository();

	List<PageNameVO> viewPageNames();

	List<ObjectRepository> viewObjectRepoForPageName(String page_name);

	List<AutomationConverterVO> viewAutomationEngineCommandsForType(String type);

	StatusInfo saveStepsRepository(StepRepository stepRepo);

	List<StepRepository> viewStepRepositoryList(String testId);

	StatusInfo executeTestCase(TestRepository testRepo);

	List<TestCaseResults> viewTestResultsForTestName(String testId);

	List<StepResults> viewStepResultsForTestName(String testId);

	StatusInfo deleteTestCase(String testCaseName);

	StatusInfo deleteStepForTestCase(String testCaseName, String stepNo);

	StatusInfo deleteObjectRepositoryForPageNameAndElementName(String pageName, String elementName);

	List<TimeComparision> viewTimeComparisionResults(); 



}

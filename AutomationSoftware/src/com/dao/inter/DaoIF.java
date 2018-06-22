package com.dao.inter;


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


public interface DaoIF {

	
	StatusInfo insertObjectRepository(ObjectRepository objectRepo);

	StatusInfo updateObjectRepo(ObjectRepository stepRepo);

	StatusInfo checkObjectRepoExist(ObjectRepository objectRepo);

	List<ObjectRepository> retriveAllObjectRepository();

	StatusInfo checkTestRepoExist(TestRepository testRepo);

	StatusInfo insertTestRepository(TestRepository testRepo);

	List<TestRepository> retriveAllTestRepository();

	List<PageNameVO> viewPageNames();

	List<ObjectRepository> retriveAllObjectRepositoryForPageName(String page_name);

	List<AutomationConverterVO> viewAutomationEngineCommandsForType(String type);

	StatusInfo checkStepRepoExist(StepRepository stepRepo);

	StatusInfo insertStepRepository(StepRepository stepRepo);

	StatusInfo updateStepRepo(StepRepository stepRepo);

	List<StepRepository> viewStepRepositoryList(String testId);

	List<StepRepository> viewStepRepositoryOrderByStepNo(String test_case_name);

	StatusInfo saveTestCaseResults(TestCaseResults testCaseResults);

	ObjectRepository retriveObjectRepositoryForPageNameAndElementName(StepRepository stepRepository);

	StatusInfo insertBatchStepResults(List<StepResults> stepResults);

	StatusInfo deleteTestCaseResults(TestCaseResults testCaseResults);

	StatusInfo deleteStepResultsForTestCase(String testcasename);
 
	List<TestCaseResults> viewTestResultsForTestName(String testId);

	List<StepResults> viewStepResultsForTestName(String testId);

	String retriveTestCaseInfoForTestCaseName(String test_case_name);

	StatusInfo deleteTestCase(String testCaseName);

	StatusInfo deleteStepForTestCase(String testCaseName, String stepNo);

	StatusInfo deleteObjectRepositoryForPageNameAndElementName(String pageName, String elementName);

	StatusInfo insertTimeComparision(TimeComparision timeComparision);

	List<TimeComparision> viewTimeComparisionResults();     

}

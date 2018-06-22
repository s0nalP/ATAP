package com.delegate.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.delegate.inter.DelegateIF;
import com.model.AutomationConverterVO;
import com.model.ObjectRepository;
import com.model.PageNameVO;
import com.model.StatusInfo;
import com.model.StepRepository;
import com.model.StepResults;
import com.model.TestCaseResults;
import com.model.TestRepository;
import com.model.TimeComparision;
import com.service.inter.ServiceIF;

public class DelegateImpl implements DelegateIF {

	@Autowired
	private ServiceIF service;

	public ServiceIF getService() {
		return service;
	}

	public void setService(ServiceIF service) {
		this.service = service;
	}

	@Override
	public StatusInfo saveObjectRepository(ObjectRepository objectRepo) {
		return service.saveObjectRepository(objectRepo);
	}

	@Override
	public List<ObjectRepository> viewObjectRepositoryList() {
		return service.viewObjectRepositoryList();
	}

	@Override
	public StatusInfo saveTestRepository(TestRepository testRepo) {
		return service.saveTestRepository(testRepo);
	}

	@Override
	public List<TestRepository> viewTestRepository() {
		return service.viewTestRepository();
	}

	@Override
	public List<PageNameVO> viewPageNames() {
		return service.viewPageNames();
	}

	@Override
	public List<ObjectRepository> viewObjectRepoForPageName(String page_name) {
		return service.viewObjectRepoForPageName(page_name);
	}

	@Override
	public List<AutomationConverterVO> viewAutomationEngineCommandsForType(String type) {
		return service.viewAutomationEngineCommandsForType(type);
	}

	@Override
	public StatusInfo saveStepsRepository(StepRepository stepRepo) {
		return service.saveStepsRepository(stepRepo);
	}

	@Override
	public List<StepRepository> viewStepRepositoryList(String testId) {
		return service.viewStepRepositoryList(testId);
	}

	@Override
	public StatusInfo executeTestCase(TestRepository testRepo) {
		return service.executeTestCase(testRepo);
	}

	@Override
	public List<TestCaseResults> viewTestResultsForTestName(String testId) {
		return service.viewTestResultsForTestName(testId);
	}

	@Override
	public List<StepResults> viewStepResultsForTestName(String testId) {
		return service.viewStepResultsForTestName(testId);
	}

	@Override
	public StatusInfo deleteTestCase(String testCaseName) {
		return service.deleteTestCase(testCaseName);
	}

	@Override
	public StatusInfo deleteStepForTestCase(String testCaseName, String stepNo) {
		return service.deleteStepForTestCase(testCaseName,stepNo);
	}

	@Override
	public StatusInfo deleteObjectRepositoryForPageNameAndElementName(String pageName, String elementName) {
		return service.deleteObjectRepositoryForPageNameAndElementName(pageName,elementName);
	}

	@Override
	public List<TimeComparision> viewTimeComparisionResults() {
		return service.viewTimeComparisionResults();
	}

}

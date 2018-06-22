package com.service.impl;

import com.constants.Constants;
import com.dao.inter.DaoIF;
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

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

public class ServiceImpl implements ServiceIF {

	@Autowired
	private DaoIF dao;

	public DaoIF getDao() {
		return dao;
	}

	public void setDao(DaoIF dao) {
		this.dao = dao;
	}

	@Override
	public StatusInfo saveObjectRepository(ObjectRepository objectRepo) {
		StatusInfo statusInfo = new StatusInfo();
		try {

			statusInfo = dao.checkObjectRepoExist(objectRepo);

			if (statusInfo.isExist()) {

				statusInfo = dao.updateObjectRepo(objectRepo);

				if (!statusInfo.isStatus()) {
					statusInfo = new StatusInfo();
					statusInfo.setStatus(false);
					statusInfo.setErrMsg(Constants.Message.OBJECT_REPOSITORY_UPDATE_FAILED);
					return statusInfo;
				} else {
					statusInfo.setStatus(true);
					return statusInfo;
				}

			}

			statusInfo = dao.insertObjectRepository(objectRepo);
			if (!statusInfo.isStatus()) {
				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo.setErrMsg(Constants.Message.OBJECT_REPOSITORY_SAVE_FAILED);
				return statusInfo;
			}

		} catch (Exception e) {

			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());

			return statusInfo;
		}

		statusInfo.setStatus(true);
		return statusInfo;

	}

	@Override
	public List<ObjectRepository> viewObjectRepositoryList() {

		List<ObjectRepository> objRepoList = null;

		try {

			objRepoList = dao.retriveAllObjectRepository();

		} catch (Exception e) {

			return null;
		}
		return objRepoList;

	}

	@Override
	public StatusInfo saveTestRepository(TestRepository testRepo) {
		StatusInfo statusInfo = new StatusInfo();
		try {

			statusInfo = dao.checkTestRepoExist(testRepo);

			if (statusInfo.isExist()) {

				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo.setErrMsg(Constants.Message.DUPLICATE_TEST_CASE_REPO);
				return statusInfo;
			}

			statusInfo = dao.insertTestRepository(testRepo);
			if (!statusInfo.isStatus()) {
				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo.setErrMsg(Constants.Message.TEST_REPOSITORY_SAVE_FAILED);
				return statusInfo;
			}

		} catch (Exception e) {

			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());

			return statusInfo;
		}

		statusInfo.setStatus(true);
		return statusInfo;
	}

	@Override
	public List<TestRepository> viewTestRepository() {
		List<TestRepository> testTestRepoList = null;

		try {

			testTestRepoList = dao.retriveAllTestRepository();

		} catch (Exception e) {

			return null;
		}
		return testTestRepoList;
	}

	@Override
	public List<PageNameVO> viewPageNames() {
		List<PageNameVO> pageNameVOList = null;

		try {

			pageNameVOList = dao.viewPageNames();

		} catch (Exception e) {

			return null;
		}
		return pageNameVOList;
	}

	@Override
	public List<ObjectRepository> viewObjectRepoForPageName(String page_name) {
		List<ObjectRepository> objRepoList = null;

		try {

			objRepoList = dao.retriveAllObjectRepositoryForPageName(page_name);

		} catch (Exception e) {

			return null;
		}
		return objRepoList;
	}

	@Override
	public List<AutomationConverterVO> viewAutomationEngineCommandsForType(String type) {
		List<AutomationConverterVO> automationConverterVOList = null;

		try {

			automationConverterVOList = dao.viewAutomationEngineCommandsForType(type);

		} catch (Exception e) {

			return null;
		}
		return automationConverterVOList;
	}

	@Override
	public StatusInfo saveStepsRepository(StepRepository stepRepo) {
		StatusInfo statusInfo = new StatusInfo();
		try {

			statusInfo = dao.checkStepRepoExist(stepRepo);

			if (statusInfo.isExist()) {

				statusInfo = dao.updateStepRepo(stepRepo);

				if (!statusInfo.isStatus()) {
					statusInfo = new StatusInfo();
					statusInfo.setStatus(false);
					statusInfo.setErrMsg(Constants.Message.STEP_REPOSITORY_UPDATE_FAILED);
					return statusInfo;
				} else {
					statusInfo.setStatus(true);
					return statusInfo;
				}

			}

			statusInfo = dao.insertStepRepository(stepRepo);
			if (!statusInfo.isStatus()) {
				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo.setErrMsg(Constants.Message.STEP_REPOSITORY_SAVE_FAILED);
				return statusInfo;
			}

		} catch (Exception e) {

			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());

			return statusInfo;
		}

		statusInfo.setStatus(true);
		return statusInfo;
	}

	@Override
	public List<StepRepository> viewStepRepositoryList(String testId) {
		List<StepRepository> objStepList = null;

		try {

			objStepList = dao.viewStepRepositoryList(testId);

		} catch (Exception e) {

			return null;
		}
		return objStepList;
	}

	private WebDriver driver;

	@Override
	public StatusInfo executeTestCase(TestRepository testRepo) {

		StatusInfo statusInfo = new StatusInfo();
		try {

			String testCaseDesc = dao.retriveTestCaseInfoForTestCaseName(testRepo.getTest_case_name());

			testRepo.setTest_case_desc(testCaseDesc);

			if (null == testCaseDesc || testCaseDesc != null && testCaseDesc.isEmpty()) {
				statusInfo.setErrMsg(Constants.Message.TEST_CASE_DESC_EMPTY);
				statusInfo.setStatus(true);
				return statusInfo;
			}

			List<StepRepository> stepRepositoryList = dao.viewStepRepositoryOrderByStepNo(testRepo.getTest_case_name());

			if (null == stepRepositoryList || (stepRepositoryList != null && stepRepositoryList.isEmpty())) {
				statusInfo.setErrMsg(Constants.Message.NO_STEPS_FOUND_FOR_EXECUTION);
				statusInfo.setStatus(true);
				return statusInfo;
			}

			// driver = new FirefoxDriver();

			File inputFile = new ClassPathResource("chromedriver.exe").getFile();
			System.setProperty("webdriver.chrome.driver", inputFile.getCanonicalPath());

			driver = new ChromeDriver();

			TestCaseResults testCaseResults = new TestCaseResults();

			List<StepResults> stepResults = new ArrayList<StepResults>();

			boolean isOverallStatus = false;

			testCaseResults.setTestcasename(testRepo.getTest_case_name());
			testCaseResults.setTestCaseInfo(testRepo.getTest_case_desc());

			testCaseResults.setTestState(Constants.Keys.STARTED);

			Long startTime = System.currentTimeMillis();

			if (stepRepositoryList != null && !stepRepositoryList.isEmpty()) {

				for (StepRepository stepRepository : stepRepositoryList) {

					String actionType = stepRepository.getAction_type();

					String valueToUse = stepRepository.getElement_value();

					if (actionType != null) {

						if (actionType.equals(Constants.AUTOMATIONACTIONS.LOADURL)) {

							try {

								StepResults stepResults2 = new StepResults();

								stepResults2.setStepNo(stepRepository.getStepno());
								stepResults2.setTestCaseName(stepRepository.getTestCaseName());

								// Web Driver Command to Load URL

								driver.get(valueToUse);

								stepResults2.setStatus(true);
								stepResults2.setTestCaseName(testRepo.getTest_case_name());
								stepResults2.setErrorMsg("Loaded the URL Sucessful" + valueToUse);

								stepResults.add(stepResults2);

							} catch (Exception e) {

								isOverallStatus = false;

								StepResults stepResults2 = new StepResults();

								stepResults2.setStepNo(stepRepository.getStepno());
								stepResults2.setTestCaseName(stepRepository.getTestCaseName());

								stepResults2.setStatus(false);
								stepResults2.setErrorMsg(e.getMessage());

								stepResults.add(stepResults2);

								testCaseResults.setErrMsg("Faile Due to Exception in Loading URL");
								testCaseResults.setStatus(false);
								testCaseResults.setTestState(Constants.Keys.FAILED);

								driver.quit();

								break;
							}

						} else if (actionType.equals(Constants.AUTOMATIONACTIONS.SENDKEYS)) {

							try {

								StepResults stepResults2 = new StepResults();

								stepResults2.setStepNo(stepRepository.getStepno());
								stepResults2.setTestCaseName(stepRepository.getTestCaseName());

								// Web Driver Command to Load URL

								ObjectRepository objectRepository = dao
										.retriveObjectRepositoryForPageNameAndElementName(stepRepository);

								if (null == objectRepository) {

									stepResults2 = new StepResults();

									stepResults2.setStepNo(stepRepository.getStepno());
									stepResults2.setTestCaseName(stepRepository.getTestCaseName());

									stepResults2.setStatus(false);
									stepResults2.setErrorMsg("Failed Due to Object Not Found");

									stepResults.add(stepResults2);

									testCaseResults.setErrMsg("Failed Due to Object Not Found");
									testCaseResults.setStatus(false);
									testCaseResults.setTestState(Constants.Keys.FAILED);
									break;
								} else {

									String identified_by = objectRepository.getIdentification_type();

									String value = objectRepository.getElement_value();

									if (identified_by.equals(Constants.AUTOMATIONIDENTIFICATION.NAME)) {

										// Web Driver Command to Send Keys By
										// Name
										driver.findElement(By.name(value)).clear();
										driver.findElement(By.name(value)).sendKeys(valueToUse);

										stepResults2 = new StepResults();

										stepResults2.setStepNo(stepRepository.getStepno());
										stepResults2.setTestCaseName(stepRepository.getTestCaseName());

										stepResults2.setStatus(true);
										stepResults2.setTestCaseName(testRepo.getTest_case_name());
										stepResults2.setErrorMsg("Entering Value By Name is Sucessful" + valueToUse);

										stepResults.add(stepResults2);

									} else if (identified_by.equals(Constants.AUTOMATIONIDENTIFICATION.ID)) {

										// Web Driver Command to Send Keys By ID
										driver.findElement(By.id(value)).clear();
										driver.findElement(By.id(value)).sendKeys(valueToUse);

										stepResults2 = new StepResults();

										stepResults2.setStepNo(stepRepository.getStepno());
										stepResults2.setTestCaseName(stepRepository.getTestCaseName());

										stepResults2.setStatus(true);
										stepResults2.setTestCaseName(testRepo.getTest_case_name());
										stepResults2.setErrorMsg("Entering Value By ID is Sucessful" + valueToUse);

										stepResults.add(stepResults2);

									} else if (identified_by.equals(Constants.AUTOMATIONIDENTIFICATION.XPATH)) {

										//// Web Driver Command to Send Keys By
										//// XPATH
										driver.findElement(By.xpath(value)).clear();
										driver.findElement(By.xpath(value)).sendKeys(valueToUse);

										stepResults2 = new StepResults();

										stepResults2.setStepNo(stepRepository.getStepno());
										stepResults2.setTestCaseName(stepRepository.getTestCaseName());

										stepResults2.setStatus(true);
										stepResults2.setTestCaseName(testRepo.getTest_case_name());
										stepResults2.setErrorMsg("Entering Value By Xpath is Sucessful" + valueToUse);

										stepResults.add(stepResults2);

									} else {

										stepResults2 = new StepResults();

										stepResults2.setStepNo(stepRepository.getStepno());
										stepResults2.setTestCaseName(stepRepository.getTestCaseName());

										stepResults2.setStatus(false);
										stepResults2.setErrorMsg("Failed Due to Object Not Identified");

										stepResults.add(stepResults2);

										testCaseResults.setErrMsg("Failed Due to Object Not Identified");
										testCaseResults.setStatus(false);
										testCaseResults.setTestState(Constants.Keys.FAILED);
										break;

									}

								}

							} catch (Exception e) {

								isOverallStatus = false;

								StepResults stepResults2 = new StepResults();

								stepResults2.setStepNo(stepRepository.getStepno());
								stepResults2.setTestCaseName(stepRepository.getTestCaseName());

								stepResults2.setStatus(false);
								stepResults2.setErrorMsg(e.getMessage());

								stepResults.add(stepResults2);

								testCaseResults.setErrMsg("Faile Due to Exception in Send Keys");
								testCaseResults.setStatus(false);
								testCaseResults.setTestState(Constants.Keys.FAILED);
								driver.quit();

								break;
							}

						} // End of Send Keys

						else if (actionType.equals(Constants.AUTOMATIONACTIONS.BUTTONCLICK)) {

							try {

								StepResults stepResults2 = new StepResults();

								stepResults2.setStepNo(stepRepository.getStepno());
								stepResults2.setTestCaseName(stepRepository.getTestCaseName());

								//

								ObjectRepository objectRepository = dao
										.retriveObjectRepositoryForPageNameAndElementName(stepRepository);

								if (null == objectRepository) {

									stepResults2 = new StepResults();

									stepResults2.setStepNo(stepRepository.getStepno());
									stepResults2.setTestCaseName(stepRepository.getTestCaseName());

									stepResults2.setStatus(false);
									stepResults2.setErrorMsg("Failed Due to Object Not Found -Button");

									stepResults.add(stepResults2);

									testCaseResults.setErrMsg("Failed Due to Object Not Found -Button");
									testCaseResults.setStatus(false);
									testCaseResults.setTestState(Constants.Keys.FAILED);
									break;
								} else {

									String identified_by = objectRepository.getIdentification_type();

									String value = objectRepository.getElement_value();

									if (identified_by.equals(Constants.AUTOMATIONIDENTIFICATION.NAME)) {

										// Web Driver Command to Click Button By
										// NAME

										driver.findElement(By.name(value)).click();

										stepResults2 = new StepResults();

										stepResults2.setStepNo(stepRepository.getStepno());
										stepResults2.setTestCaseName(stepRepository.getTestCaseName());

										stepResults2.setStatus(true);
										stepResults2.setTestCaseName(testRepo.getTest_case_name());
										stepResults2.setErrorMsg("Clicking Button By Name is Sucessful" + valueToUse);

										stepResults.add(stepResults2);

									} else if (identified_by.equals(Constants.AUTOMATIONIDENTIFICATION.ID)) {

										// Web Driver Command to Click Button By
										// ID

										driver.findElement(By.id(value)).click();

										stepResults2 = new StepResults();

										stepResults2.setStepNo(stepRepository.getStepno());
										stepResults2.setTestCaseName(stepRepository.getTestCaseName());

										stepResults2.setStatus(true);
										stepResults2.setTestCaseName(testRepo.getTest_case_name());
										stepResults2.setErrorMsg("Clicking Button By ID is Sucessful" + valueToUse);

										stepResults.add(stepResults2);

									} else if (identified_by.equals(Constants.AUTOMATIONIDENTIFICATION.XPATH)) {

										//// Web Driver Command to Click Button
										//// By Xpath

										driver.findElement(By.xpath(value)).click();

										stepResults2 = new StepResults();

										stepResults2.setStepNo(stepRepository.getStepno());
										stepResults2.setTestCaseName(stepRepository.getTestCaseName());

										stepResults2.setStatus(true);
										stepResults2.setTestCaseName(testRepo.getTest_case_name());
										stepResults2.setErrorMsg("Clicking Button By Xpath is Sucessful" + valueToUse);

										stepResults.add(stepResults2);
									} else {

										stepResults2 = new StepResults();

										stepResults2.setStepNo(stepRepository.getStepno());
										stepResults2.setTestCaseName(stepRepository.getTestCaseName());

										stepResults2.setStatus(false);
										stepResults2.setErrorMsg("Failed Due to Object Not Identified Button");

										stepResults.add(stepResults2);

										testCaseResults.setErrMsg("Failed Due to Object Not Identified Button");
										testCaseResults.setStatus(false);
										testCaseResults.setTestState(Constants.Keys.FAILED);
										break;

									}

								}

							} catch (Exception e) {

								isOverallStatus = false;

								StepResults stepResults2 = new StepResults();

								stepResults2.setStepNo(stepRepository.getStepno());
								stepResults2.setTestCaseName(stepRepository.getTestCaseName());

								stepResults2.setStatus(false);
								stepResults2.setErrorMsg(e.getMessage());

								stepResults.add(stepResults2);

								testCaseResults.setErrMsg("Faile Due to Exception in Clicking Button");
								testCaseResults.setStatus(false);
								testCaseResults.setTestState(Constants.Keys.FAILED);
								driver.quit();
								break;
							}

						} // End of Button Click
						else if (actionType.equals(Constants.AUTOMATIONACTIONS.DELAY)) {

							try {

								StepResults stepResults2 = new StepResults();

								stepResults2.setStepNo(stepRepository.getStepno());
								stepResults2.setTestCaseName(stepRepository.getTestCaseName());

								//

								ObjectRepository objectRepository = dao
										.retriveObjectRepositoryForPageNameAndElementName(stepRepository);

								if (null == objectRepository) {

									stepResults2 = new StepResults();

									stepResults2.setStepNo(stepRepository.getStepno());
									stepResults2.setTestCaseName(stepRepository.getTestCaseName());

									stepResults2.setStatus(false);
									stepResults2.setErrorMsg("Failed Due to Object Not Found -Button");

									stepResults.add(stepResults2);

									testCaseResults.setErrMsg("Failed Due to Object Not Found -Button");
									testCaseResults.setStatus(false);
									testCaseResults.setTestState(Constants.Keys.FAILED);
									break;
								} else {

									String identified_by = objectRepository.getIdentification_type();

									String value = objectRepository.getElement_value();

									Thread.sleep(new Long(valueToUse));

									stepResults2 = new StepResults();

									stepResults2.setStepNo(stepRepository.getStepno());
									stepResults2.setTestCaseName(stepRepository.getTestCaseName());

									stepResults2.setStatus(true);
									stepResults2.setTestCaseName(testRepo.getTest_case_name());
									stepResults2.setErrorMsg("Delay Value to Use =" + valueToUse);
									stepResults.add(stepResults2);
								}

							} catch (Exception e) {

								isOverallStatus = false;

								StepResults stepResults2 = new StepResults();

								stepResults2.setStepNo(stepRepository.getStepno());
								stepResults2.setTestCaseName(stepRepository.getTestCaseName());

								stepResults2.setStatus(false);
								stepResults2.setErrorMsg(e.getMessage());

								stepResults.add(stepResults2);

								testCaseResults.setErrMsg("Faile Due to Exception in Clicking Button");
								testCaseResults.setStatus(false);
								testCaseResults.setTestState(Constants.Keys.FAILED);
								driver.quit();
								break;
							}

						} // End of Delay Else if Condition

						else if (actionType.equals(Constants.AUTOMATIONACTIONS.LINKCLICK)
								|| actionType.equals(Constants.AUTOMATIONACTIONS.CLICKLINK)) {

							try {

								StepResults stepResults2 = new StepResults();

								stepResults2.setStepNo(stepRepository.getStepno());
								stepResults2.setTestCaseName(stepRepository.getTestCaseName());

								//

								ObjectRepository objectRepository = dao
										.retriveObjectRepositoryForPageNameAndElementName(stepRepository);

								if (null == objectRepository) {

									stepResults2 = new StepResults();

									stepResults2.setStepNo(stepRepository.getStepno());
									stepResults2.setTestCaseName(stepRepository.getTestCaseName());

									stepResults2.setStatus(false);
									stepResults2.setErrorMsg("Failed Due to Object Not Found -Link");

									stepResults.add(stepResults2);

									testCaseResults.setErrMsg("Failed Due to Object Not Found -Link");
									testCaseResults.setStatus(false);
									testCaseResults.setTestState(Constants.Keys.FAILED);
									break;
								} else {

									String identified_by = objectRepository.getIdentification_type();

									String value = objectRepository.getElement_value();

									if (identified_by.equals(Constants.AUTOMATIONIDENTIFICATION.NAME)) {

										// Web Driver Command to Click Button By
										// NAME

										driver.findElement(By.name(value)).click();

										stepResults2 = new StepResults();

										stepResults2.setStepNo(stepRepository.getStepno());
										stepResults2.setTestCaseName(stepRepository.getTestCaseName());

										stepResults2.setStatus(true);
										stepResults2.setTestCaseName(testRepo.getTest_case_name());
										stepResults2.setErrorMsg("Clicking Link By Name is Sucessful" + valueToUse);

										stepResults.add(stepResults2);

									} else if (identified_by.equals(Constants.AUTOMATIONIDENTIFICATION.ID)) {

										// Web Driver Command to Click Button By
										// ID

										driver.findElement(By.id(value)).click();

										stepResults2 = new StepResults();

										stepResults2.setStepNo(stepRepository.getStepno());
										stepResults2.setTestCaseName(stepRepository.getTestCaseName());

										stepResults2.setStatus(true);
										stepResults2.setTestCaseName(testRepo.getTest_case_name());
										stepResults2.setErrorMsg("Clicking Button By ID is Sucessful" + valueToUse);

										stepResults.add(stepResults2);

									} else if (identified_by.equals(Constants.AUTOMATIONIDENTIFICATION.XPATH)) {

										//// Web Driver Command to Click Button
										//// By Xpath

										driver.findElement(By.xpath(value)).click();

										stepResults2 = new StepResults();

										stepResults2.setStepNo(stepRepository.getStepno());
										stepResults2.setTestCaseName(stepRepository.getTestCaseName());

										stepResults2.setStatus(true);
										stepResults2.setTestCaseName(testRepo.getTest_case_name());
										stepResults2.setErrorMsg("Clicking Link By Xpath is Sucessful" + valueToUse);

										stepResults.add(stepResults2);
									} else {

										stepResults2 = new StepResults();

										stepResults2.setStepNo(stepRepository.getStepno());
										stepResults2.setTestCaseName(stepRepository.getTestCaseName());

										stepResults2.setStatus(false);
										stepResults2.setErrorMsg("Failed Due to Object Not Identified Button");

										stepResults.add(stepResults2);

										testCaseResults.setErrMsg("Failed Due to Object Not Identified Button");
										testCaseResults.setStatus(false);
										testCaseResults.setTestState(Constants.Keys.FAILED);
										break;

									}

								}

							} catch (Exception e) {

								isOverallStatus = false;

								StepResults stepResults2 = new StepResults();

								stepResults2.setStepNo(stepRepository.getStepno());
								stepResults2.setTestCaseName(stepRepository.getTestCaseName());

								stepResults2.setStatus(false);
								stepResults2.setErrorMsg(e.getMessage());

								stepResults.add(stepResults2);

								testCaseResults.setErrMsg("Faile Due to Exception in Clicking Button");
								testCaseResults.setStatus(false);
								testCaseResults.setTestState(Constants.Keys.FAILED);
								driver.quit();
								break;
							}

						} // End of Button Click
						else if (actionType.equals(Constants.AUTOMATIONACTIONS.HTMLTEXTVIEW)) {

							try {

								StepResults stepResults2 = new StepResults();

								stepResults2.setStepNo(stepRepository.getStepno());
								stepResults2.setTestCaseName(stepRepository.getTestCaseName());

								//

								ObjectRepository objectRepository = dao
										.retriveObjectRepositoryForPageNameAndElementName(stepRepository);

								if (null == objectRepository) {

									stepResults2 = new StepResults();

									stepResults2.setStepNo(stepRepository.getStepno());
									stepResults2.setTestCaseName(stepRepository.getTestCaseName());

									stepResults2.setStatus(false);
									stepResults2.setErrorMsg("Failed Due to Object Not Found -HTML");

									stepResults.add(stepResults2);

									testCaseResults.setErrMsg("Failed Due to Object Not Found -HTML");
									testCaseResults.setStatus(false);
									testCaseResults.setTestState(Constants.Keys.FAILED);
									isOverallStatus = false;
									break;
								} else {

									String identified_by = objectRepository.getIdentification_type();

									String value = objectRepository.getElement_value();

									if (identified_by.equals(Constants.AUTOMATIONIDENTIFICATION.NAME)) {

										// Web Driver Command to Click Button By
										// NAME

										String messageOnUI = driver.findElement(By.name(value)).getText();

										if (null == messageOnUI) {

											stepResults2 = new StepResults();

											stepResults2.setStepNo(stepRepository.getStepno());
											stepResults2.setTestCaseName(stepRepository.getTestCaseName());

											stepResults2.setStatus(false);
											stepResults2.setTestCaseName(testRepo.getTest_case_name());
											stepResults2.setErrorMsg("Finding HTML is Failed" + messageOnUI);

											stepResults.add(stepResults2);
											isOverallStatus = false;

										} else if (messageOnUI != null && messageOnUI.isEmpty()) {

											if (messageOnUI.contains(valueToUse)) {

												stepResults2 = new StepResults();

												stepResults2.setStepNo(stepRepository.getStepno());
												stepResults2.setTestCaseName(stepRepository.getTestCaseName());

												stepResults2.setStatus(true);
												stepResults2.setTestCaseName(testRepo.getTest_case_name());
												stepResults2.setErrorMsg("Message on UI and Expected Message are Same"
														+ "UI =" + messageOnUI + "Expected Message =" + valueToUse);

												stepResults.add(stepResults2);

											} else {

												stepResults2 = new StepResults();

												stepResults2.setStepNo(stepRepository.getStepno());
												stepResults2.setTestCaseName(stepRepository.getTestCaseName());

												stepResults2.setStatus(true);
												stepResults2.setTestCaseName(testRepo.getTest_case_name());
												stepResults2.setErrorMsg(
														"Message on UI and Expected Message are Not Same" + "UI ="
																+ messageOnUI + "Expected Message =" + valueToUse);

												stepResults.add(stepResults2);
												isOverallStatus = false;
											}

										} else {

											stepResults2 = new StepResults();

											stepResults2.setStepNo(stepRepository.getStepno());
											stepResults2.setTestCaseName(stepRepository.getTestCaseName());

											stepResults2.setStatus(true);
											stepResults2.setTestCaseName(testRepo.getTest_case_name());
											stepResults2.setErrorMsg("Finding HTML By Name is Failed" + valueToUse);
											isOverallStatus = false;
											stepResults.add(stepResults2);
										}

									} else if (identified_by.equals(Constants.AUTOMATIONIDENTIFICATION.ID)) {

										// Web Driver Command to Click Button By
										// ID

										String messageOnUI = driver.findElement(By.id(value)).getText();

										if (null == messageOnUI) {

											stepResults2 = new StepResults();

											stepResults2.setStepNo(stepRepository.getStepno());
											stepResults2.setTestCaseName(stepRepository.getTestCaseName());

											stepResults2.setStatus(false);
											stepResults2.setTestCaseName(testRepo.getTest_case_name());
											stepResults2.setErrorMsg("Finding HTML is Failed" + messageOnUI);

											stepResults.add(stepResults2);

											isOverallStatus = false;
											break;

										} else if (messageOnUI != null && !messageOnUI.isEmpty()) {

											if (messageOnUI.contains(valueToUse)) {

												stepResults2 = new StepResults();

												stepResults2.setStepNo(stepRepository.getStepno());
												stepResults2.setTestCaseName(stepRepository.getTestCaseName());

												stepResults2.setStatus(true);
												stepResults2.setTestCaseName(testRepo.getTest_case_name());
												stepResults2.setErrorMsg("Message on UI and Expected Message are Same"
														+ "UI =" + messageOnUI + "Expected Message =" + valueToUse);

												stepResults.add(stepResults2);

											} else {

												stepResults2 = new StepResults();

												stepResults2.setStepNo(stepRepository.getStepno());
												stepResults2.setTestCaseName(stepRepository.getTestCaseName());

												stepResults2.setStatus(true);
												stepResults2.setTestCaseName(testRepo.getTest_case_name());
												stepResults2.setErrorMsg(
														"Message on UI and Expected Message are Not Same" + "UI ="
																+ messageOnUI + "Expected Message =" + valueToUse);

												stepResults.add(stepResults2);
												isOverallStatus = false;
												break;
											}

										} else {

											stepResults2 = new StepResults();

											stepResults2.setStepNo(stepRepository.getStepno());
											stepResults2.setTestCaseName(stepRepository.getTestCaseName());

											stepResults2.setStatus(true);
											stepResults2.setTestCaseName(testRepo.getTest_case_name());
											stepResults2.setErrorMsg("Finding HTML By Name is Failed" + valueToUse);

											stepResults.add(stepResults2);

											isOverallStatus = false;
											break;
										}

									} else if (identified_by.equals(Constants.AUTOMATIONIDENTIFICATION.XPATH)) {

										//// Web Driver Command to Click Button
										//// By Xpath

										String messageOnUI = driver.findElement(By.xpath(value)).getText();

										if (null == messageOnUI) {

											stepResults2 = new StepResults();

											stepResults2.setStepNo(stepRepository.getStepno());
											stepResults2.setTestCaseName(stepRepository.getTestCaseName());

											stepResults2.setStatus(false);
											stepResults2.setTestCaseName(testRepo.getTest_case_name());
											stepResults2.setErrorMsg("Finding HTML is Failed" + messageOnUI);

											stepResults.add(stepResults2);
											isOverallStatus = false;
											break;

										} else if (messageOnUI != null && !messageOnUI.isEmpty()) {

											if (messageOnUI.contains(valueToUse)) {

												stepResults2 = new StepResults();

												stepResults2.setStepNo(stepRepository.getStepno());
												stepResults2.setTestCaseName(stepRepository.getTestCaseName());

												stepResults2.setStatus(true);
												stepResults2.setTestCaseName(testRepo.getTest_case_name());
												stepResults2.setErrorMsg("Message on UI and Expected Message are Same"
														+ "UI =" + messageOnUI + "Expected Message =" + valueToUse);

												stepResults.add(stepResults2);

											} else {

												stepResults2 = new StepResults();

												stepResults2.setStepNo(stepRepository.getStepno());
												stepResults2.setTestCaseName(stepRepository.getTestCaseName());

												stepResults2.setStatus(true);
												stepResults2.setTestCaseName(testRepo.getTest_case_name());
												stepResults2.setErrorMsg(
														"Message on UI and Expected Message are Not Same" + "UI ="
																+ messageOnUI + "Expected Message =" + valueToUse);

												stepResults.add(stepResults2);
												isOverallStatus = false;
												break;
											}

										} else {

											stepResults2 = new StepResults();

											stepResults2.setStepNo(stepRepository.getStepno());
											stepResults2.setTestCaseName(stepRepository.getTestCaseName());

											stepResults2.setStatus(true);
											stepResults2.setTestCaseName(testRepo.getTest_case_name());
											stepResults2.setErrorMsg("Finding HTML By Name is Failed" + valueToUse);

											stepResults.add(stepResults2);
											isOverallStatus = false;
											break;
										}

									}

								}

							} catch (Exception e) {

								isOverallStatus = false;

								StepResults stepResults2 = new StepResults();

								stepResults2.setStepNo(stepRepository.getStepno());
								stepResults2.setTestCaseName(stepRepository.getTestCaseName());

								stepResults2.setStatus(false);
								stepResults2.setErrorMsg(e.getMessage());

								stepResults.add(stepResults2);

								testCaseResults
										.setErrMsg("Faile Due to Exception in Getting HTML ELEMENT TEXT EXTRATION");
								testCaseResults.setStatus(false);
								testCaseResults.setTestState(Constants.Keys.FAILED);
								driver.quit();
								isOverallStatus = false;
								break;
							}

						} // End of HTML VIEW

					}
					isOverallStatus = true;

				} // End of For Loop

				driver.quit();

				if (isOverallStatus) {
					testCaseResults.setTestState(Constants.Keys.SUCESS);
				} else {
					testCaseResults.setTestState(Constants.Keys.FAILED);
				}

				testCaseResults.setTestcasename(testRepo.getTest_case_name());
				testCaseResults.setTestCaseInfo(testRepo.getTest_case_desc());

				testCaseResults.setDateTime(new Date().toString());

				Long stopTime = System.currentTimeMillis();

				Long timeTaken = stopTime - startTime;

				double timeInSeconds = ((double) timeTaken / (double) 1000);

				double timeForOld = 10;

				if (stepResults != null && !stepResults.isEmpty()) {

					timeForOld = stepResults.size() * 10;

				}

				TimeComparision timeComparision = new TimeComparision();
				timeComparision.setTimeTakenOld(timeForOld);
				timeComparision.setTimeTakenProposed(timeInSeconds);

				statusInfo = dao.insertTimeComparision(timeComparision);

				if (!statusInfo.isStatus()) {

					statusInfo = new StatusInfo();
					statusInfo.setStatus(false);
					statusInfo.setErrMsg(Constants.Message.COULD_NOT_SAVE_COMPARISION_TIME);
					return statusInfo;

				}

				// Removing Old Test Case Results
				statusInfo = dao.deleteTestCaseResults(testCaseResults);

				if (!statusInfo.isStatus()) {

					statusInfo = new StatusInfo();
					statusInfo.setStatus(false);
					statusInfo.setErrMsg(Constants.Message.DEL_TEST_CASE_RESULTS_FAILED);
					return statusInfo;

				}
				statusInfo = dao.deleteStepResultsForTestCase(testCaseResults.getTestcasename());

				// Removing old Step Results
				if (!statusInfo.isStatus()) {

					statusInfo = new StatusInfo();
					statusInfo.setStatus(false);
					statusInfo.setErrMsg(Constants.Message.DEL_TEST_CASE_STEP__RESULTS_FAILED);
					return statusInfo;

				}

				// Storing the Test Case Results
				statusInfo = dao.saveTestCaseResults(testCaseResults);

				if (!statusInfo.isStatus()) {

					statusInfo = new StatusInfo();
					statusInfo.setStatus(false);
					statusInfo.setErrMsg(Constants.Message.SAVE_TEST_RESULTS_FALED);
					return statusInfo;

				}

				statusInfo = dao.insertBatchStepResults(stepResults);

				if (!statusInfo.isStatus()) {
					statusInfo = new StatusInfo();
					statusInfo.setStatus(false);
					statusInfo.setErrMsg(Constants.Message.SAVE_BATCH_STEP_RESULTS_FAILED);
					return statusInfo;
				}

			}
		} catch (Exception e) {
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());

			return statusInfo;

		}
		statusInfo.setStatus(true);

		return statusInfo;

	}

	@Override
	public List<TestCaseResults> viewTestResultsForTestName(String testId) {
		List<TestCaseResults> testCaseResultsList = null;

		try {

			testCaseResultsList = dao.viewTestResultsForTestName(testId);

		} catch (Exception e) {

			return null;
		}
		return testCaseResultsList;
	}

	@Override
	public List<StepResults> viewStepResultsForTestName(String testId) {
		List<StepResults> stepResultsList = null;

		try {

			stepResultsList = dao.viewStepResultsForTestName(testId);

		} catch (Exception e) {

			return null;
		}
		return stepResultsList;
	}

	@Override
	public StatusInfo deleteTestCase(String testCaseName) {
		return dao.deleteTestCase(testCaseName);
	}

	@Override
	public StatusInfo deleteStepForTestCase(String testCaseName, String stepNo) {
		return dao.deleteStepForTestCase(testCaseName, stepNo);
	}

	@Override
	public StatusInfo deleteObjectRepositoryForPageNameAndElementName(String pageName, String elementName) {
		return dao.deleteObjectRepositoryForPageNameAndElementName(pageName, elementName);
	}

	@Override
	public List<TimeComparision> viewTimeComparisionResults() {

		List<TimeComparision> timeComparisions = null;
		try {

			timeComparisions = dao.viewTimeComparisionResults();

		} catch (Exception e) {

			return null;
		}
		return timeComparisions;

	}

}

package com.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.constants.Constants;
import com.dao.inter.DaoIF;
import com.gargoylesoftware.htmlunit.StorageHolder.Type;
import com.model.AutomationConverterVO;
import com.model.ObjectRepository;
import com.model.PageNameVO;
import com.model.StatusInfo;
import com.model.StepRepository;
import com.model.StepResults;
import com.model.TestCaseResults;
import com.model.TestRepository;
import com.model.TimeComparision;

public class DAOImpl implements DaoIF {

	protected SimpleJdbcTemplate template;

	public SimpleJdbcTemplate getTemplate() {
		return template;
	}

	public void setTemplate(SimpleJdbcTemplate template) {
		this.template = template;
	}

	protected NamedParameterJdbcTemplate namedJdbcTemplate;

	public NamedParameterJdbcTemplate getNamedJdbcTemplate() {
		return namedJdbcTemplate;
	}

	public void setNamedJdbcTemplate(NamedParameterJdbcTemplate namedJdbcTemplate) {
		this.namedJdbcTemplate = namedJdbcTemplate;
	}

	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Autowired
	protected MessageSource sqlProperties;

	public MessageSource getSqlProperties() {
		return sqlProperties;
	}

	public void setSqlProperties(MessageSource sqlProperties) {
		this.sqlProperties = sqlProperties;
	}

	protected JdbcTemplate jdbcTemplate;

	/**
	 * 
	 */
	public void init() {
		template = new SimpleJdbcTemplate(dataSource);
		namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	// INSERT INTO
	// OBJECTREPO(PAGENAME,ELEMENTNAME,ELEMENTVALUE,OBJECTTYPE,IDENTIFICATIONTYPE)\
	// VALUES(:PAGENAME,:ELEMENTNAME,:ELEMENTVALUE,:OBJECTTYPE,:IDENTIFICATIONTYPE)
	@Override
	public StatusInfo insertObjectRepository(ObjectRepository objectRepo) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(Constants.SQLKeys.INSERT_OBJECT_REPOSITORY_INFO_SQL, null, null);
			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("PAGENAME", objectRepo.getPage_name());
			paramMap.put("ELEMENTNAME", objectRepo.getElement_name());
			paramMap.put("ELEMENTVALUE", objectRepo.getElement_value());
			paramMap.put("OBJECTTYPE", objectRepo.getObject_type());
			paramMap.put("IDENTIFICATIONTYPE", objectRepo.getIdentification_type());

			namedJdbcTemplate.update(sql, paramMap);

			statusInfo.setStatus(true);
			return statusInfo;

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
	public StatusInfo updateObjectRepo(ObjectRepository objectRepo) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(
					Constants.SQLKeys.UPDATE_OBJECT_REPOSITORY_INFO_WHERE_PAGENAME_AND_ELEMENTNAME_SQL, null, null);
			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("PAGENAME", objectRepo.getPage_name());
			paramMap.put("ELEMENTNAME", objectRepo.getElement_name());
			paramMap.put("ELEMENTVALUE", objectRepo.getElement_value());
			paramMap.put("OBJECTTYPE", objectRepo.getObject_type());
			paramMap.put("IDENTIFICATIONTYPE", objectRepo.getIdentification_type());

			namedJdbcTemplate.update(sql, paramMap);

			statusInfo.setStatus(true);
			return statusInfo;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setErrMsg(e.getMessage());
			statusInfo.setStatus(false);
			return statusInfo;

		}
	}

	// SELECT PAGENAME,ELEMENTNAME,ELEMENTVALUE,OBJECTTYPE,IDENTIFICATIONTYPE
	// FROM OBJECTREPO \
	// WHERE ELEMENTNAME=:ELEMENTNAME AND PAGENAME=:PAGENAME
	@Override
	public StatusInfo checkObjectRepoExist(ObjectRepository objectRepo) {
		StatusInfo statusInfo = new StatusInfo();

		try {
			String sql = sqlProperties.getMessage(Constants.SQLKeys.SELECT_OBJECT_REPO_FOR_PAGENAME_AND_ELEMENTNAME_SQL,
					null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("ELEMENTNAME", objectRepo.getElement_name());
			paramMap.put("PAGENAME", objectRepo.getPage_name());

			ObjectRepository objectRepository = namedJdbcTemplate.queryForObject(sql, paramMap, new ObjectRepoMapper());

			if (null == objectRepository) {
				statusInfo.setExist(false);

				return statusInfo;
			} else {
				statusInfo.setExist(true);
				statusInfo.setStatus(true);
				return statusInfo;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());

			statusInfo.setErrMsg(e.getMessage());
			statusInfo.setStatus(false);

			return statusInfo;
		}
	}

	private final class ObjectRepoMapper implements RowMapper<ObjectRepository> {

		public ObjectRepository mapRow(ResultSet rs, int arg1) throws SQLException {
			ObjectRepository objectRepos = new ObjectRepository();

			objectRepos.setElement_name(rs.getString("ELEMENTNAME"));
			objectRepos.setElement_value(rs.getString("ELEMENTVALUE"));
			objectRepos.setIdentification_type(rs.getString("IDENTIFICATIONTYPE"));
			objectRepos.setObject_type(rs.getString("OBJECTTYPE"));
			objectRepos.setPage_name(rs.getString("PAGENAME"));

			return objectRepos;

		}

	}

	@Override
	public List<ObjectRepository> retriveAllObjectRepository() {
		List<ObjectRepository> objectRepositories = null;
		try {
			String sql = sqlProperties.getMessage(Constants.SQLKeys.SELECT_OBJECT_REPO_ALL_SQL, null, null);

			Map<String, Object> paramMap = null;

			objectRepositories = namedJdbcTemplate.query(sql, paramMap, new ObjectRepoMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}

		return objectRepositories;
	}

	@Override
	public StatusInfo checkTestRepoExist(TestRepository testRepo) {
		StatusInfo statusInfo = new StatusInfo();

		try {
			String sql = sqlProperties.getMessage(Constants.SQLKeys.SELECT_TEST_REPO_FOR_TESTNAME_SQL, null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("TESTCASENAME", testRepo.getTest_case_name());

			TestRepository objectRepository = namedJdbcTemplate.queryForObject(sql, paramMap, new TestRepoMapper());

			if (null == objectRepository) {
				statusInfo.setExist(false);

				return statusInfo;
			} else {
				statusInfo.setExist(true);
				statusInfo.setStatus(true);
				return statusInfo;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());

			statusInfo.setErrMsg(e.getMessage());
			statusInfo.setStatus(false);

			return statusInfo;
		}
	}

	private final class TestRepoMapper implements RowMapper<TestRepository> {

		public TestRepository mapRow(ResultSet rs, int arg1) throws SQLException {
			TestRepository testRepoVO = new TestRepository();

			testRepoVO.setTest_case_desc(rs.getString("TESTCASEDESC"));
			testRepoVO.setTest_case_name(rs.getString("TESTCASENAME"));

			return testRepoVO;

		}

	}

	// INSERT INTO TESTREPO(TESTCASENAME,TESTCASEDESC)
	// VALUES(:TESTCASENAME,:TESTCASEDESC)
	@Override
	public StatusInfo insertTestRepository(TestRepository testRepo) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(Constants.SQLKeys.INSERT_TEST_REPOSITORY_INFO_SQL, null, null);
			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("TESTCASENAME", testRepo.getTest_case_name());
			paramMap.put("TESTCASEDESC", testRepo.getTest_case_desc());

			namedJdbcTemplate.update(sql, paramMap);

			statusInfo.setStatus(true);
			return statusInfo;

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
	public List<TestRepository> retriveAllTestRepository() {
		List<TestRepository> testRepositoryList = null;
		try {
			String sql = sqlProperties.getMessage(Constants.SQLKeys.SELECT_TEST_REPO_ALL_SQL, null, null);

			Map<String, Object> paramMap = null;

			testRepositoryList = namedJdbcTemplate.query(sql, paramMap, new TestRepoMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}

		return testRepositoryList;
	}

	@Override
	public List<PageNameVO> viewPageNames() {
		List<PageNameVO> testRepositoryList = null;
		try {
			String sql = sqlProperties.getMessage(Constants.SQLKeys.SELECT_PAGE_NAME_REPO_ALL_SQL, null, null);

			Map<String, Object> paramMap = null;

			testRepositoryList = namedJdbcTemplate.query(sql, paramMap, new PageNameMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}

		return testRepositoryList;
	}

	private final class PageNameMapper implements RowMapper<PageNameVO> {

		public PageNameVO mapRow(ResultSet rs, int arg1) throws SQLException {
			PageNameVO pageNameVO = new PageNameVO();

			pageNameVO.setPageName(rs.getString("PAGENAME"));

			return pageNameVO;

		}

	}

	@Override
	public List<ObjectRepository> retriveAllObjectRepositoryForPageName(String page_name) {
		List<ObjectRepository> objectRepositories = null;
		try {
			String sql = sqlProperties.getMessage(Constants.SQLKeys.SELECT_OBJECT_REPO_ALL_FOR_PAGE_NAME_SQL, null,
					null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("PAGENAME", page_name);

			objectRepositories = namedJdbcTemplate.query(sql, paramMap, new ObjectRepoMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}

		return objectRepositories;
	}

	@Override
	public List<AutomationConverterVO> viewAutomationEngineCommandsForType(String type) {
		List<AutomationConverterVO> objectRepositories = null;
		try {
			String sql = sqlProperties.getMessage(Constants.SQLKeys.SELECT_AUTOMATION_FOR_ELEMENT_TYPE_SQL, null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ELEMENTTYPE", type);

			objectRepositories = namedJdbcTemplate.query(sql, paramMap, new AutomationConverterVOMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}

		return objectRepositories;
	}

	private final class AutomationConverterVOMapper implements RowMapper<AutomationConverterVO> {

		public AutomationConverterVO mapRow(ResultSet rs, int arg1) throws SQLException {
			AutomationConverterVO automationConverterVO = new AutomationConverterVO();

			automationConverterVO.setActionId(rs.getInt("ACTIONID"));
			automationConverterVO.setActionStatement(rs.getString("ACTIONSTATEMENT"));
			automationConverterVO.setActionType(rs.getString("ACTIONTYPE"));
			automationConverterVO.setElementType(rs.getString("ELEMENTTYPE"));

			return automationConverterVO;

		}

	}

	@Override
	public StatusInfo checkStepRepoExist(StepRepository stepRepo) {
		StatusInfo statusInfo = new StatusInfo();

		try {
			String sql = sqlProperties.getMessage(Constants.SQLKeys.SELECT_STEP_REPO_FOR_STEP_NO_AND_TESTCASENAME_SQL,
					null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("STEPNO", stepRepo.getStepno());
			paramMap.put("TESTCASENAME", stepRepo.getTestCaseName());

			StepRepository stepRepository = namedJdbcTemplate.queryForObject(sql, paramMap, new StepRepoMapper());

			if (null == stepRepository) {
				statusInfo.setExist(false);

				return statusInfo;
			} else {
				statusInfo.setExist(true);
				statusInfo.setStatus(true);
				return statusInfo;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());

			statusInfo.setErrMsg(e.getMessage());
			statusInfo.setStatus(false);

			return statusInfo;
		}
	}

	private final class StepRepoMapper implements RowMapper<StepRepository> {

		public StepRepository mapRow(ResultSet rs, int arg1) throws SQLException {
			StepRepository stepRepo = new StepRepository();

			stepRepo.setAction_type(rs.getString("ACTIONTYPE"));
			stepRepo.setElement_name(rs.getString("ELEMENTNAME"));
			stepRepo.setElement_value(rs.getString("ELEMENTVALUE"));
			stepRepo.setPage_name(rs.getString("PAGENAME"));
			stepRepo.setStep_desc(rs.getString("STEPDESC"));
			stepRepo.setStepno(rs.getInt("STEPNO"));
			stepRepo.setTestCaseName(rs.getString("TESTCASENAME"));

			return stepRepo;

		}

	}

	@Override
	public StatusInfo insertStepRepository(StepRepository stepRepo) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(Constants.SQLKeys.INSERT_STEP_REPOSITORY_INFO_SQL, null, null);
			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("ACTIONTYPE", stepRepo.getAction_type());
			paramMap.put("ELEMENTNAME", stepRepo.getElement_name());
			paramMap.put("ELEMENTVALUE", stepRepo.getElement_value());
			paramMap.put("PAGENAME", stepRepo.getPage_name());
			paramMap.put("STEPDESC", stepRepo.getStep_desc());
			paramMap.put("STEPNO", stepRepo.getStepno());
			paramMap.put("TESTCASENAME", stepRepo.getTestCaseName());

			namedJdbcTemplate.update(sql, paramMap);

			statusInfo.setStatus(true);
			return statusInfo;

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
	public StatusInfo updateStepRepo(StepRepository stepRepo) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(
					Constants.SQLKeys.UPDATE_STEP_REPOSITORY_INFO_WHERE_TESTCASENAME_AND_STEPNO_SQL, null, null);
			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("ACTIONTYPE", stepRepo.getAction_type());
			paramMap.put("ELEMENTNAME", stepRepo.getElement_name());
			paramMap.put("ELEMENTVALUE", stepRepo.getElement_value());
			paramMap.put("PAGENAME", stepRepo.getPage_name());
			paramMap.put("STEPDESC", stepRepo.getStep_desc());
			paramMap.put("STEPNO", stepRepo.getStepno());
			paramMap.put("TESTCASENAME", stepRepo.getTestCaseName());

			namedJdbcTemplate.update(sql, paramMap);

			statusInfo.setStatus(true);
			return statusInfo;

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
	public List<StepRepository> viewStepRepositoryList(String testId) {
		List<StepRepository> objectRepositories = null;
		try {
			String sql = sqlProperties.getMessage(Constants.SQLKeys.SELECT_STEP_REPO_ALL_FOR_TEST_CASE_NAME_SQL, null,
					null);

			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("TESTCASENAME", testId);

			objectRepositories = namedJdbcTemplate.query(sql, paramMap, new StepRepoMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}

		return objectRepositories;
	}

	@Override
	public List<StepRepository> viewStepRepositoryOrderByStepNo(String test_case_name) {
		List<StepRepository> objectRepositories = null;
		try {
			String sql = sqlProperties.getMessage(
					Constants.SQLKeys.SELECT_STEP_REPO_ALL_FOR_TEST_CASE_NAME_ORDER_BY_STEP_NO_SQL, null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("TESTCASENAME", test_case_name);

			objectRepositories = namedJdbcTemplate.query(sql, paramMap, new StepRepoMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}

		return objectRepositories;
	}

	@Override
	public StatusInfo saveTestCaseResults(TestCaseResults testCaseResults) {

		StatusInfo statusInfo = new StatusInfo();
		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(Constants.SQLKeys.INSERT_TEST_CASE_RESULTS_SQL, null, null);
			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("DATETIMEINFO", testCaseResults.getDateTime());
			paramMap.put("STATUSOFTEST", testCaseResults.isStatus() == true ? 1 : 0);
			paramMap.put("TESTSTATE", testCaseResults.getTestState());
			paramMap.put("TESTCASEINFO", testCaseResults.getTestCaseInfo());
			paramMap.put("ERRMSG", testCaseResults.getErrMsg());
			paramMap.put("TESTCASENAME", testCaseResults.getTestcasename());

			namedJdbcTemplate.update(sql, paramMap);

			statusInfo.setStatus(true);

			return statusInfo;

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
	public ObjectRepository retriveObjectRepositoryForPageNameAndElementName(StepRepository stepRepository) {
		ObjectRepository objectRepositories = null;
		try {
			String sql = sqlProperties.getMessage(Constants.SQLKeys.SELECT_OBJECT_REPO_FOR_PAGENAME_AND_ELEMENTNAME_SQL,
					null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("ELEMENTNAME", stepRepository.getElement_name());
			paramMap.put("PAGENAME", stepRepository.getPage_name());

			objectRepositories = namedJdbcTemplate.queryForObject(sql, paramMap, new ObjectRepoMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}

		return objectRepositories;
	}

	// INSERT INTO
	// STEPRESULTS(STATUSOFSTEP,STEPNO,TESTCASENAME,ERRMSG)VALUES(?,?,?,?)
	@Override
	public StatusInfo insertBatchStepResults(List<StepResults> stepResults) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(Constants.SQLKeys.INSERT_BATCH_STEP_RESULTS_INFO_SQL, null, null);
			Map<String, Object> paramMap = new HashMap<String, Object>();

			List<Map<String, Object>> batchValues = new ArrayList(stepResults.size());
			for (StepResults step : stepResults) {
				/*
				 * batchValues.add(new MapSqlParameterSource( "STATUSOFSTEP" ,
				 * step.isStatus() == true ? 1 : 0) .addValue("STEPNO" ,
				 * step.getStepNo()) .addValue("TESTCASENAME" ,
				 * step.getTestCaseName()) .addValue("ERRMSG" ,
				 * step.getErrorMsg()) .getValues());
				 */

				jdbcTemplate.update(sql,
						new Object[] { step.isStatus() == true ? 1 : 0, step.getStepNo(), step.getTestCaseName(),
								step.getErrorMsg() },
						new int[] { Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.VARCHAR });

			}

			statusInfo.setStatus(true);
			return statusInfo;

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
	public StatusInfo deleteTestCaseResults(TestCaseResults testCaseResults) {
		StatusInfo statusInfo = null;

		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(Constants.SQLKeys.DELETE_TESTCASERESULTS_WHERE_TESTCASENAME_SQL, null,
					null);

			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("TESTCASENAME", testCaseResults.getTestcasename());

			namedJdbcTemplate.update(sql, queryMap);
			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}

	}

	@Override
	public StatusInfo deleteStepResultsForTestCase(String testcasename) {
		StatusInfo statusInfo = null;

		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(Constants.SQLKeys.DELETE_STEPRESULTS_WHERE_TESTCASENAME_SQL, null,
					null);

			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("TESTCASENAME", testcasename);

			namedJdbcTemplate.update(sql, queryMap);
			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	@Override
	public List<TestCaseResults> viewTestResultsForTestName(String testId) {
		List<TestCaseResults> testCaseResultsList = null;
		try {
			String sql = sqlProperties.getMessage(Constants.SQLKeys.SELECT_TEST_CASE_RESULTS_FOR_TEST_NAME_SQL, null,
					null);

			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("TESTCASENAME", testId);

			testCaseResultsList = namedJdbcTemplate.query(sql, paramMap, new TestCaseResultsMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}

		return testCaseResultsList;
	}

	private final class TestCaseResultsMapper implements RowMapper<TestCaseResults> {

		public TestCaseResults mapRow(ResultSet rs, int arg1) throws SQLException {
			TestCaseResults testCaseResults = new TestCaseResults();

			testCaseResults.setDateTime(rs.getString("DATETIMEINFO"));
			testCaseResults.setErrMsg(rs.getString("ERRMSG"));
			testCaseResults.setStatus(rs.getInt("STATUSOFTEST") == 1 ? true : false);
			testCaseResults.setTestCaseInfo(rs.getString("TESTCASEINFO"));
			testCaseResults.setTestcasename(rs.getString("TESTCASENAME"));
			testCaseResults.setTestState(rs.getString("TESTSTATE"));

			return testCaseResults;

		}

	}

	@Override
	public List<StepResults> viewStepResultsForTestName(String testId) {
		List<StepResults> stepResultsList = null;
		try {
			String sql = sqlProperties.getMessage(Constants.SQLKeys.SELECT_TEST_STEP_RESULTS_FOR_TEST_NAME_SQL, null,
					null);

			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("TESTCASENAME", testId);

			stepResultsList = namedJdbcTemplate.query(sql, paramMap, new StepResultsMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}

		return stepResultsList;
	}

	private final class StepResultsMapper implements RowMapper<StepResults> {

		public StepResults mapRow(ResultSet rs, int arg1) throws SQLException {
			StepResults stepResults = new StepResults();

			stepResults.setErrorMsg(rs.getString("ERRMSG"));
			stepResults.setStatus(rs.getInt("STATUSOFSTEP") == 1 ? true : false);
			stepResults.setStepNo(rs.getInt("STEPNO"));
			stepResults.setTestCaseName(rs.getString("TESTCASENAME"));

			return stepResults;

		}

	}

	@Override
	public String retriveTestCaseInfoForTestCaseName(String test_case_name) {
		String objectRepositories = null;
		try {
			String sql = sqlProperties.getMessage(Constants.SQLKeys.SELECT_TESTCASEDESC_FOR_TESTCASE_NAME_SQL, null,
					null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("TESTCASENAME", test_case_name);

			objectRepositories = namedJdbcTemplate.queryForObject(sql, paramMap, new TestCaseMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}

		return objectRepositories;
	}

	private final class TestCaseMapper implements RowMapper<String> {

		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			return rs.getString("TESTCASEDESC");

		}

	}

	@Override
	public StatusInfo deleteTestCase(String testCaseName) {
		StatusInfo statusInfo = null;

		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(Constants.SQLKeys.DELETE_TESTCASE_WHERE_TESTCASENAME_SQL, null, null);

			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("TESTCASENAME", testCaseName);

			namedJdbcTemplate.update(sql, queryMap);
			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	@Override
	public StatusInfo deleteStepForTestCase(String testCaseName, String stepNo) {
		StatusInfo statusInfo = null;

		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(
					Constants.SQLKeys.DELETE_STEP_FROM_TESTCASE_WHERE_TESTCASENAME_AND_STEPNO_SQL, null, null);

			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("TESTCASENAME", testCaseName);
			queryMap.put("STEPNO", stepNo);

			namedJdbcTemplate.update(sql, queryMap);
			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	@Override
	public StatusInfo deleteObjectRepositoryForPageNameAndElementName(String pageName, String elementName) {
		StatusInfo statusInfo = null;

		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(
					Constants.SQLKeys.DELETE_OBJECTREPO_FROM_OBJECTREPO_WHERE_PAGENAME_AND_ELEMENTNAME_SQL, null, null);

			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("PAGENAME", pageName);
			queryMap.put("ELEMENTNAME", elementName);

			namedJdbcTemplate.update(sql, queryMap);
			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	@Override
	public StatusInfo insertTimeComparision(TimeComparision timeComparision) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(Constants.SQLKeys.INSERT_TIME_COMPARISION_INFO_SQL, null, null);
			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("PREVIOUS", timeComparision.getTimeTakenOld());
			paramMap.put("PROPOSED", timeComparision.getTimeTakenProposed());

			namedJdbcTemplate.update(sql, paramMap);

			statusInfo.setStatus(true);
			return statusInfo;

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
	public List<TimeComparision> viewTimeComparisionResults() {

		List<TimeComparision> timeComparisionList = null;
		try {
			String sql = sqlProperties.getMessage(Constants.SQLKeys.SELECT_TIME_COMPARSION_ALL_SQL, null, null);

			Map<String, Object> paramMap = null;

			timeComparisionList = namedJdbcTemplate.query(sql, paramMap, new TimeComparisionMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
		return timeComparisionList;
	}

	private final class TimeComparisionMapper implements RowMapper<TimeComparision> {

		public TimeComparision mapRow(ResultSet rs, int arg1) throws SQLException {
			TimeComparision timeComparision = new TimeComparision();

			double timeold = rs.getDouble("PREVIOUS");

			double timeProp = rs.getDouble("PROPOSED");

			int iterationNo = rs.getInt("ITERATIONNO");

			timeComparision.setIterationNo(iterationNo);

			if (timeold >= timeProp) {

				timeComparision.setTimeTakenOld(timeold);
				timeComparision.setTimeTakenProposed(timeProp);

			} else {
				timeComparision.setTimeTakenOld(timeProp);
				timeComparision.setTimeTakenProposed(timeold);
			}

			return timeComparision;

		}

	}

}

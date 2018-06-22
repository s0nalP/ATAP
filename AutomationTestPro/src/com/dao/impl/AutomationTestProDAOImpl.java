package com.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.constants.GeneticConstants;
import com.constants.HardCodedSQL;
import com.dao.inter.AutomationTestProDaoIF;
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

public class AutomationTestProDAOImpl implements AutomationTestProDaoIF {

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

	@Override
	public List<String> retriveUserIds() {

		System.out.println("METHOD[retriveUserIds()]");
		try {
			System.out.println("SQL Proprty" + sqlProperties);
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.RETRIVE_REGISTER_USERIDS_SQL, null,
					null);
			System.out.println("SQL=" + sql);
			if (null == sql || sql.isEmpty()) {
				sql = HardCodedSQL.RETRIVE_REGISTER_USERIDS_SQL;
			}
			return jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	// INSERT INTO
	// LOGIN(FIRSTNAME,LASTNAME,PASSWORD,EMAIL,USERID)VALUES(?,?,?,?,?)

	@Override
	public StatusInfo insertLogin(RegisterUser registerUser) {
		StatusInfo insertLoginStatus = null;
		try {
			insertLoginStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.INSERT_LOGIN_SQL, null, null);
			System.out.println("SQL----" + sql);
			if (null == sql || sql.isEmpty()) {
				sql = HardCodedSQL.INSERT_LOGIN_SQL;
			}

			jdbcTemplate.update(sql,
					new Object[] { registerUser.getFirstName(), registerUser.getLastName(),
							registerUser.getUserPassword(), registerUser.getEmailId(), registerUser.getUserId() },
					new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR });
			insertLoginStatus.setStatus(true);
			return insertLoginStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertLoginStatus = new StatusInfo();
			insertLoginStatus.setErrMsg(e.getMessage());
			insertLoginStatus.setStatus(false);
			return insertLoginStatus;

		}
	}

	@Override
	public String retrivePassword(String userId) {
		try {
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.RETRIVE_PASSWORD_WHERE_USERID_SQL, null,
					null);
			if (null == sql || sql.isEmpty()) {
				sql = HardCodedSQL.RETRIVE_PASSWORD_WHERE_USERID_SQL;
			}
			return jdbcTemplate.queryForList(sql, String.class, userId).get(0);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public int retriveLoginType(String userId) {
		try {
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.RETRIVE_LOGINTYPE_WHERE_USERID_SQL, null,
					null);
			if (null == sql || sql.isEmpty()) {
				sql = HardCodedSQL.RETRIVE_LOGINTYPE_WHERE_USERID_SQL;
			}
			return jdbcTemplate.queryForList(sql, Integer.class, userId).get(0);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return -1;
		}
	}

	@Override
	public List<String> retriveAccountNoList() {
		try {
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.RETRIVE_ACCCOUNTNOS_SQL, null, null);
			if (null == sql || sql.isEmpty()) {
				sql = HardCodedSQL.RETRIVE_ACCCOUNTNOS_SQL;
			}
			return jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}

	}

	@Override
	public String retriveIPINForAccNo(String accountNo) {
		try {
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.RETRIVE_IPIN_WHERE_ACCNO_SQL, null,
					null);
			if (null == sql || sql.isEmpty()) {
				sql = HardCodedSQL.RETRIVE_IPIN_WHERE_ACCNO_SQL;
			}
			return jdbcTemplate.queryForList(sql, String.class, accountNo).get(0);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public double retriveBalanceForAccount(String accountNo) {
		try {
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.RETRIVE_BALANCE_WHERE_ACCNO_SQL, null,
					null);
			if (null == sql || sql.isEmpty()) {
				sql = HardCodedSQL.RETRIVE_BALANCE_WHERE_ACCNO_SQL;
			}
			return jdbcTemplate.queryForList(sql, Double.class, accountNo).get(0);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return 0;
		}
	}

	@Override
	public StatusInfo updateBalance(String accountNo, double newBalance) {

		StatusInfo hotelInsertStatus = null;
		try {
			hotelInsertStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.UPDATE_BALANCE_SQL, null, null);
			System.out.println("SQL----" + sql);
			if (null == sql || sql.isEmpty()) {
				sql = HardCodedSQL.UPDATE_BALANCE_SQL;
			}
			jdbcTemplate.update(sql, new Object[] { newBalance, accountNo }, new int[] { Types.DOUBLE, Types.VARCHAR });
			hotelInsertStatus.setStatus(true);
			return hotelInsertStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			hotelInsertStatus = new StatusInfo();
			hotelInsertStatus.setErrMsg(e.getMessage());
			hotelInsertStatus.setStatus(false);
			return hotelInsertStatus;

		}

	}

	@Override
	public StatusInfo insertTransaction(TransactionModel transactionModel) {
		StatusInfo transInsertStatus = null;
		try {
			transInsertStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.INSERT_TRANS_SQL, null, null);
			System.out.println("SQL----" + sql);
			if (null == sql || sql.isEmpty()) {
				sql = HardCodedSQL.INSERT_TRANS_SQL;
			}

			jdbcTemplate.update(sql, new Object[] { transactionModel.getCustomerId(), transactionModel.getPackId() },
					new int[] { Types.VARCHAR, Types.INTEGER });
			transInsertStatus.setStatus(true);
			return transInsertStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			transInsertStatus = new StatusInfo();
			transInsertStatus.setErrMsg(e.getMessage());
			transInsertStatus.setStatus(false);
			return transInsertStatus;

		}
	}

	@Override
	// INSERT INTO HISTORYRATING(TOURISTPACKID,CUSTNAME,RATING)VALUES(?,?,?)
	public StatusInfo addRating(BookRating historyRating) {
		StatusInfo historyRatingStatus = null;
		try {
			historyRatingStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.INSERT_HISTORY_SQL, null, null);

			jdbcTemplate.update(sql, new Object[] { historyRating.getBookId(), historyRating.getRating(), },
					new int[] { Types.INTEGER, Types.INTEGER });
			historyRatingStatus.setStatus(true);
			return historyRatingStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			historyRatingStatus = new StatusInfo();
			historyRatingStatus.setErrMsg(e.getMessage());
			historyRatingStatus.setStatus(false);
			return historyRatingStatus;

		}
	}

	@Override
	// SELECT TOURISTPACKID,RATING,CUSTNAME,HISTORYID FROM HISTORYRATING
	public List<BookRating> retriveRating() {
		try {
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.RETRIVE_BOOKRATING_SQL, null, null);
			SqlParameterSource paramMap = null;
			return namedJdbcTemplate.query(sql, paramMap, new RatingMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class RatingMapper implements RowMapper<BookRating> {

		public BookRating mapRow(ResultSet rs, int arg1) throws SQLException {
			BookRating historyRatingObj = new BookRating();

			historyRatingObj.setBookId(rs.getInt(GeneticConstants.DatabaseColumns.BOOKID_COL));
			historyRatingObj.setRating(rs.getInt(GeneticConstants.DatabaseColumns.RATING_COL));

			historyRatingObj.setBookName(rs.getString(GeneticConstants.DatabaseColumns.BOOKNAME_COL));

			return historyRatingObj;

		}

	}

	@Override
	public StatusInfo deleteFromBestHistoryRating() {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.DELETE_BESTHISTORY_SQL, null, null);
			jdbcTemplate.update(sql);
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
	public StatusInfo insertRating(BookRating bookRating) {
		StatusInfo insertHistoryRatingStatus = null;
		try {
			insertHistoryRatingStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.INSERT_BOOKRATING_SQL, null, null);
			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put(GeneticConstants.Keys.BOOKID_KEY, bookRating.getBookId());
			paramMap.put(GeneticConstants.Keys.RATING_KEY, bookRating.getRating());

			namedJdbcTemplate.update(sql, paramMap);

			insertHistoryRatingStatus.setStatus(true);
			return insertHistoryRatingStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertHistoryRatingStatus = new StatusInfo();
			insertHistoryRatingStatus.setErrMsg(e.getMessage());
			insertHistoryRatingStatus.setStatus(false);
			return insertHistoryRatingStatus;

		}
	}

	@Override
	public List<BookRating> retriveBookRating() {
		try {
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.RETRIVE_BESTHISTORY_SQL, null, null);

			SqlParameterSource paramMap = null;
			return namedJdbcTemplate.query(sql, paramMap, new BookRatingMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class BookRatingMapper implements RowMapper<BookRating> {

		public BookRating mapRow(ResultSet rs, int arg1) throws SQLException {
			BookRating bookModel = new BookRating();

			bookModel.setBookId(rs.getInt(GeneticConstants.DatabaseColumns.BOOKID_COL));
			bookModel.setBookName(rs.getString(GeneticConstants.DatabaseColumns.BOOKNAME_COL));
			bookModel.setRating(rs.getInt(GeneticConstants.DatabaseColumns.RATING_COL));
			return bookModel;

		}

	}

	@Override
	public List<BookPackModel> retriveAllBooksPack() {
		try {
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.RETRIVE_BOOKPACKS_SQL, null, null);
			if (null == sql || sql.isEmpty()) {
				sql = HardCodedSQL.RETRIVE_BOOKPACKS_SQL;
			}
			SqlParameterSource paramMap = null;
			return namedJdbcTemplate.query(sql, paramMap, new BookPackModelMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class BookPackModelMapper implements RowMapper<BookPackModel> {

		public BookPackModel mapRow(ResultSet rs, int arg1) throws SQLException {
			BookPackModel bookModel = new BookPackModel();

			bookModel.setAuthor(rs.getString(GeneticConstants.DatabaseColumns.AUTHOR_COL));
			bookModel.setBookId(rs.getInt(GeneticConstants.DatabaseColumns.BOOKID_COL));
			bookModel.setBookLoc(rs.getString(GeneticConstants.DatabaseColumns.BOOKLOC_COL));
			bookModel.setBookName(rs.getString(GeneticConstants.DatabaseColumns.BOOKNAME_COL));
			bookModel.setBookOverview(rs.getString(GeneticConstants.DatabaseColumns.BOOKOVERVIEW_COL));
			bookModel.setBookPrice(rs.getDouble(GeneticConstants.DatabaseColumns.BOOKPRICE_COL));
			bookModel.setCatId(rs.getInt(GeneticConstants.DatabaseColumns.CATID_COL));
			bookModel.setEdition(rs.getString(GeneticConstants.DatabaseColumns.EDITION_COL));
			bookModel.setPublisher(rs.getString(GeneticConstants.DatabaseColumns.PUBLISHER_COL));
			bookModel.setRating(rs.getInt(GeneticConstants.DatabaseColumns.RATING_COL));
			bookModel.setSubCatId(rs.getString(GeneticConstants.DatabaseColumns.SUBCATID_COL));

			return bookModel;

		}

	}

	@Override
	public int retriveRatingForBookId(int bookId) {
		try {
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.RETRIVE_RATING_WHERE_BOOKID_SQL, null,
					null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(GeneticConstants.Keys.BOOKID_KEY, bookId);
			return namedJdbcTemplate.queryForInt(sql, paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return -1;
		}
	}

	@Override
	public List<BookPackModel> retriveAllBooksPackForCat(int catId) {
		try {
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.RETRIVE_BOOKPACKS_WHERE_CATID_SQL, null,
					null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(GeneticConstants.Keys.CATID, catId);

			return namedJdbcTemplate.query(sql, paramMap, new BookPackModelMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public StatusInfo insertOrderDetails(OrderDetailsModel orderDetails) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.INSERT_ORDERDETAILS_SQL, null, null);
			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put(GeneticConstants.Keys.BOOKID_KEY, orderDetails.getBookId());
			paramMap.put(GeneticConstants.Keys.QUANTITY_KEY, orderDetails.getQuantity());

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
	public String retriveEmailForUserId(String loginId) {

		String email = null;
		try {
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.RETRIVE_EMAIL_FOR_LOGINID, null, null);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(GeneticConstants.Keys.LOGINID_KEY, loginId);
			email = (String) namedJdbcTemplate.queryForObject(sql, paramMap, String.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return email;
	}

	@Override
	public StatusInfo insertOrderInfo(OrderInfoDB orderInfoDB) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.INSERT_ORDERINFO_SQL, null, null);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(GeneticConstants.Keys.LOGINID_KEY, orderInfoDB.getLoginId());
			paramMap.put(GeneticConstants.Keys.ORDERDATE_KEY, orderInfoDB.getDate());
			paramMap.put(GeneticConstants.Keys.TOTALAMT_KEY, orderInfoDB.getTotalAmount());
			paramMap.put(GeneticConstants.Keys.EMAIL_KEY, orderInfoDB.getEmail());

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
	public List<String> retriveUsersFromSettings() {
		try {
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.RETRIVE_SETTINGSUSER_SQL, null, null);
			SqlParameterSource paramMap = null;
			return namedJdbcTemplate.queryForList(sql, paramMap, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public StatusInfo insertSettings(int threshold, String loginId) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.INSERT_SETTINGS_SQL, null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(GeneticConstants.Keys.LOGINID_KEY, loginId);
			paramMap.put(GeneticConstants.Keys.THRESHOLD_KEY, threshold);
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
	public StatusInfo updateSettings(int threshold, String loginId) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.UPDATE_SETTINGS_SQL, null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(GeneticConstants.Keys.THRESHOLD_KEY, threshold);
			paramMap.put(GeneticConstants.Keys.LOGINID_KEY, loginId);
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
	public int retriveSettingsForUser(String loginId) {
		int threshold = 0;
		try {
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.RETRIVE_THRESHOLD_FOR_LOGINID, null,
					null);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put(GeneticConstants.Keys.LOGINID_KEY, loginId);

			threshold = namedJdbcTemplate.queryForInt(sql, map);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return -1;
		}
		return threshold;
	}

	@Override
	public List<Integer> retriveDistinctsBooksFromOrderDetails(String loginId) {

		List<Integer> bookIds = null;
		try {

			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.RETRIVE_BOOKIDS_FROM_ORDER_DETAILS_SQL,
					null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(GeneticConstants.Keys.LOGINID_KEY, loginId);

			bookIds = namedJdbcTemplate.queryForList(sql, paramMap, Integer.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Message = " + e.getMessage());
			return null;
		}

		return bookIds;

	}

	@Override
	public int retriveBookCountForBookIdAndLoginId(Integer bookId, String loginId) {
		int count = 0;
		try {

			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.RETRIVE_BOOKIDS_FROM_BOOKID_LOGINID_SQL,
					null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(GeneticConstants.Keys.LOGINID_KEY, loginId);
			paramMap.put(GeneticConstants.Keys.BOOKID_KEY, bookId);
			count = namedJdbcTemplate.queryForInt(sql, paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Message = " + e.getMessage());
			return -1;
		}

		return count;
	}

	@Override
	public BookPackModel retriveBookDetailsForBookId(Integer recommendedBook) {
		try {
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.RETRIVE_BOOKPACKS_WHERE_BOOKID_SQL, null,
					null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(GeneticConstants.Keys.BOOKID_KEY, recommendedBook);

			return namedJdbcTemplate.queryForObject(sql, paramMap, new BookPackModelMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<BookRating> retriveRatingRank() {
		try {
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.RETRIVE_BOOKRATING_NOT_ZERO_SQL, null,
					null);
			SqlParameterSource paramMap = null;
			return namedJdbcTemplate.query(sql, paramMap, new RatingMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	// INSERT INTO USERINFO(USERID,NOOFBYTES,NOOFADV,PAGEID,TIMEOFSTAY)
	// VALUES(:userId,:noOfBytes,:pageId,:timeOfStay,:noOfAdv)
	public StatusInfo insertStatistics(UserInfo userInfo) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.INSERT_USER_INFO, null, null);
			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put(GeneticConstants.Keys.USERID_KEY, userInfo.getLoginId());
			paramMap.put(GeneticConstants.Keys.NOOFBYTES_KEY, userInfo.getNoOfBytes());
			paramMap.put(GeneticConstants.Keys.PAGEID_KEY, userInfo.getPageId());
			paramMap.put(GeneticConstants.Keys.TIMEOFSTAY_KEY, userInfo.getTimeOfStay());

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
	public int retriveNoOfAdvForUserId(String loginId, int pageId) {
		int noOfAdv = 0;
		try {
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.RETRIVE_NOOFADV_WHERE_USERID_PAGEID_SQL,
					null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put(GeneticConstants.Keys.USERID_KEY, loginId);
			paramMap.put(GeneticConstants.Keys.PAGEID_KEY, pageId);

			noOfAdv = namedJdbcTemplate.queryForInt(sql, paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return 0;
		}
		return noOfAdv;
	}

	@Override
	public List<String> retriveDistinctUserIdsFromStats() {
		try {
			String sql = sqlProperties
					.getMessage(GeneticConstants.DATABASESQL.RETRIVE_DISTINCTUSERIDS_FROM_USERINFO_SQL, null, null);
			SqlParameterSource paramMap = null;
			return namedJdbcTemplate.queryForList(sql, paramMap, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<Integer> retriveDistinctPageIdsFromStatsForUserId(String userId) {
		try {
			String sql = sqlProperties.getMessage(
					GeneticConstants.DATABASESQL.RETRIVE_DISTINCT_PAGEIDS_FROM_USERINFO_WHERE_USERID_SQL, null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(GeneticConstants.Keys.USERID_KEY, userId);
			return namedJdbcTemplate.queryForList(sql, paramMap, Integer.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	// SELECT SUM(NOOFBYTES) AS TOTALBYTES,SUM(TIMEOFSTAY) AS TOTALTIME FROM
	// USERINFO WHERE USERID=:userId AND PAGEID=:pageId
	public UserInfo retriveTotalUserInfoForUserIdAndPageId(String userId, Integer pageIdTemp) {
		try {
			String sql = sqlProperties.getMessage(
					GeneticConstants.DATABASESQL.RETRIVE_USERINFO_FROM_USERINFO_WHERE_USERID_PAGEID_SQL, null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(GeneticConstants.Keys.USERID_KEY, userId);
			paramMap.put(GeneticConstants.Keys.PAGEID_KEY, pageIdTemp);

			return namedJdbcTemplate.queryForObject(sql, paramMap, new UserInfoSpecialMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	// SELECT SUM(NOOFBYTES) AS TOTALBYTES,SUM(TIMEOFSTAY) AS
	// TOTALTIME,MAX(NOOFADV) AS TOTALADV FROM USERINFO WHERE USERID=:userId AND
	// PAGEID=:pageId

	private final class UserInfoSpecialMapper implements RowMapper<UserInfo> {

		public UserInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			UserInfo userInfo = new UserInfo();

			userInfo.setNoOfBytes(rs.getDouble("TOTALBYTES"));
			userInfo.setTimeOfStay(rs.getDouble("TOTALTIME"));
			userInfo.setNoOfAdv(rs.getInt("TOTALADV"));

			return userInfo;

		}

	}

	@Override
	public int retriveCountForPageIdAndUserId(String userId, Integer pageIdTemp) {
		int email = 0;
		try {
			String sql = sqlProperties.getMessage(
					GeneticConstants.DATABASESQL.RETRIVE_COUNT_FROM_USERINFO_WHERE_USERID_PAGEID, null, null);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(GeneticConstants.Keys.USERID_KEY, userId);
			paramMap.put(GeneticConstants.Keys.PAGEID_KEY, pageIdTemp);
			email = (Integer) namedJdbcTemplate.queryForObject(sql, paramMap, Integer.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return email;
	}

	@Override
	public List<String> retriveDistinctUsersFromStats() {
		try {
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.RETRIVE_DISTINCTS_USER_SQL, null, null);
			SqlParameterSource paramMap = null;
			return namedJdbcTemplate.queryForList(sql, paramMap, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public int retriveCountDistinctUserIdForPageId(Integer pageIdTemp) {
		try {
			String sql = sqlProperties.getMessage(
					GeneticConstants.DATABASESQL.RETRIVE_DISTINCT_COUNT_OF_USERID_WHERE_PAGEID_SQL, null, null);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pageId", pageIdTemp);
			List<String> list = namedJdbcTemplate.queryForList(sql, map, String.class);

			if (null == list) {
				return 0;
			} else {
				return list.size();
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return -1;
		}
	}

	@Override
	public StatusInfo cleanAllAdvitisementForUserIdFromAdv(String userId) {

		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.DELETE_ADV_USERID_SQL, null, null);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			namedJdbcTemplate.update(sql, map);
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
	public StatusInfo insertAdvitisement(String userId, int pageId, String advertiseMent) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.INSERT_ADVITISEMENT_SQL, null, null);
			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("advDet", advertiseMent);
			paramMap.put("userId", userId);
			paramMap.put("pageId", pageId);

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
	public StatusInfo updateNoOfAdvForPageIdUserId(int pageId, String userId) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.UPDATE_NOOFADV_SQL, null, null);
			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("pageId", pageId);
			paramMap.put("userId", userId);

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
	public String retriveAdvForUserIdAndPageId(String loginId, String pageId) {

		try {
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.RETRIVE_ADV_FOR_LOGINID_AND_PAGEID, null,
					null);
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("userId", loginId);
			map.put("pageId", pageId);

			List<String> value = namedJdbcTemplate.queryForList(sql, map, String.class);

			if (value == null || value.isEmpty()) {
				return null;
			} else {
				return value.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}

	}

	@Override
	public StatusInfo updateBudget(BudgetVO budgetVO) {
		StatusInfo insertBudgetStatus = null;
		try {
			insertBudgetStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.UPDATE_BUDGET_SQL, null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("LOGINID", budgetVO.getUserId());
			paramMap.put("AMOUNT", budgetVO.getBudget());

			namedJdbcTemplate.update(sql, paramMap);

			insertBudgetStatus.setStatus(true);
			return insertBudgetStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertBudgetStatus = new StatusInfo();
			insertBudgetStatus.setErrMsg(e.getMessage());
			insertBudgetStatus.setStatus(false);
			return insertBudgetStatus;

		}
	}

	@Override
	public double retriveTotalPurchase(String loginId) {
		try {
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.RETRIVE_SUM_BUDGET_SQL_FOR_LOGINID, null,
					null);

			// SELECT AMOUNT AS SUM(TOTALAMOUNT) FROM ORDERINFO WHERE
			// LOGINID=:LOGINID

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("LOGINID", loginId);
			return namedJdbcTemplate.queryForObject(sql, paramMap, Double.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return -1;
		}
	}

	@Override
	public double retriveBudgetForLoginId(String loginId) {
		try {
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.RETRIVE_BUDGET_FOR_LOGINID_SQL, null,
					null);

			// SELECT AMOUNT AS SUM(TOTALAMOUNT) FROM ORDERINFO WHERE
			// LOGINID=:LOGINID

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("LOGINID", loginId);
			return namedJdbcTemplate.queryForObject(sql, paramMap, Double.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return -1;
		}
	}

	@Override
	public List<String> retriveUserIdsFromBudget() {
		try {
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.RETRIVE_USERIDS_FROM_BUDGET_SQL, null,
					null);
			return jdbcTemplate.queryForList(sql, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public StatusInfo insertBudget(BudgetVO budgetVO) {
		StatusInfo insertBudgetStatus = null;
		try {
			insertBudgetStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.INSERT_BUDGET_SQL, null, null);
			// INSERT INTO BUDGET(LOGINID,AMOUNT) VALUES(:LOGINID,:AMOUNT)

			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("LOGINID", budgetVO.getUserId());
			paramMap.put("AMOUNT", budgetVO.getBudget());

			namedJdbcTemplate.update(sql, paramMap);

			insertBudgetStatus.setStatus(true);
			return insertBudgetStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertBudgetStatus = new StatusInfo();
			insertBudgetStatus.setErrMsg(e.getMessage());
			insertBudgetStatus.setStatus(false);
			return insertBudgetStatus;

		}
	}

	@Override
	public StatusInfo insertHabitatFileVO(HabitatFileVO habitatFileVO) {
		StatusInfo insertHabitatStatus = null;
		try {
			insertHabitatStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.INSERT_HABITATFILE_SQL, null, null);
			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put(GeneticConstants.DatabaseColumns.SESSIONAPPID_COL, habitatFileVO.getSessionName());
			paramMap.put(GeneticConstants.DatabaseColumns.ACTIONNAME_COL, habitatFileVO.getActionName());
			paramMap.put(GeneticConstants.DatabaseColumns.ACTIONTYPE_COL, habitatFileVO.getActionType());
			paramMap.put(GeneticConstants.DatabaseColumns.IPADDRESS_COL, habitatFileVO.getIpAddress());
			paramMap.put(GeneticConstants.DatabaseColumns.USERNAME_COL, habitatFileVO.getUserName());
			paramMap.put(GeneticConstants.DatabaseColumns.DATE_COL, habitatFileVO.getDate());

			namedJdbcTemplate.update(sql, paramMap);

			insertHabitatStatus.setStatus(true);
			return insertHabitatStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertHabitatStatus = new StatusInfo();
			insertHabitatStatus.setErrMsg(e.getMessage());
			insertHabitatStatus.setStatus(false);
			return insertHabitatStatus;

		}
	}

	@Override
	public List<UserInfo> retriveUserList() {
		try {
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.RETRIVE_REGISTER_USERIDS_SQL, null,
					null);
			Map<String, Object> paramMap = null;

			return namedJdbcTemplate.query(sql, paramMap, new UserIdMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class UserIdMapper implements RowMapper<UserInfo> {
		public UserInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			UserInfo userInfo = new UserInfo();
			userInfo.setLoginId(rs.getString("USERID"));
			return userInfo;
		}
	}

	@Override
	public StatusInfo deleteOldLicenseInfo(String loginId) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.DELETE_LICENSEUSER_WHERE_USERNAME_SQL,
					null, null);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("USERNAME", loginId);
			namedJdbcTemplate.update(sql, paramMap);
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
	public StatusInfo insertBlockLicensesForUser(List<LicenseInfoStoreBackend> licenseStoreBackendList) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.INSERT_LICENSES_FOR_USER_SQL, null,
					null);

			List<Map<String, Object>> batchValues = new ArrayList(licenseStoreBackendList.size());
			for (LicenseInfoStoreBackend licenseStoreBackend : licenseStoreBackendList) {
				batchValues.add(new MapSqlParameterSource("USERNAME", licenseStoreBackend.getUserId())
						.addValue("LICENSEID", licenseStoreBackend.getLicenseId())
						.addValue("LICENSENAME", licenseStoreBackend.getLicenseName()).getValues());
			}

			int[] updateCounts = namedJdbcTemplate.batchUpdate(sql,
					batchValues.toArray(new Map[licenseStoreBackendList.size()]));

			if (updateCounts.length > 0) {
				statusInfo.setStatus(true);
			} else {
				statusInfo.setStatus(false);
			}

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
	public List<LicenseInfoStoreBackend> retriveLicenseListForUser(String userId) {
		try {
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.RETRIVE_LICENSEINFO_WHERE_USERNAME_SQL,
					null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(GeneticConstants.DatabaseColumns.USERNAME_COL, userId);

			return namedJdbcTemplate.query(sql, paramMap, new LicenseInfoStoreBackendMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class LicenseInfoStoreBackendMapper implements RowMapper<LicenseInfoStoreBackend> {

		public LicenseInfoStoreBackend mapRow(ResultSet rs, int arg1) throws SQLException {
			LicenseInfoStoreBackend licenseInfoStoreForBackend = new LicenseInfoStoreBackend();

			licenseInfoStoreForBackend.setLicenseId(rs.getInt("LICENSEID"));
			licenseInfoStoreForBackend.setLicenseName(rs.getString("LICENSENAME"));
			licenseInfoStoreForBackend.setUserId(rs.getString("USERNAME"));
			return licenseInfoStoreForBackend;

		}

	}

	@Override
	public List<String> retriveLicenseNamesForUser(String userId) {
		try {
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.RETRIVE_LICENSENAME_WHERE_USERNAME_SQL,
					null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(GeneticConstants.DatabaseColumns.USERNAME_COL, userId);

			return namedJdbcTemplate.query(sql, paramMap, new LicenseNameMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class LicenseNameMapper implements RowMapper<String> {

		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			return rs.getString(GeneticConstants.DatabaseColumns.LICENSENAME_COL);
		}

	}

	@Override
	public List<HabitatFileVO> retriveSessionForUsers(String userId) {
		try {
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.RETRIVE_HABITATFILE_SESSION_WHERE_USERNAME_SQL, null,
					null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(GeneticConstants.Keys.USERNAME_KEY, userId);

			return namedJdbcTemplate.query(sql, paramMap, new HabitatFileVOMapperSession());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}
	
	private final class HabitatFileVOMapperSession implements RowMapper<HabitatFileVO> {
		
		public HabitatFileVO mapRow(ResultSet rs, int arg1) throws SQLException {
			HabitatFileVO habitatFileVO = new HabitatFileVO(); 
			
			habitatFileVO.setSessionName(rs.getString(GeneticConstants.DatabaseColumns.SESSIONAPPID_COL));
			
			return habitatFileVO;
		}
	}
	
	private final class HabitatFileVOMapper implements RowMapper<HabitatFileVO> {

		public HabitatFileVO mapRow(ResultSet rs, int arg1) throws SQLException {
			HabitatFileVO habitatFileVO = new HabitatFileVO(); 

			habitatFileVO.setActionName(rs.getString(GeneticConstants.DatabaseColumns.ACTIONNAME_COL));
			habitatFileVO.setActionType(rs.getString(GeneticConstants.DatabaseColumns.ACTIONTYPE_COL));
			habitatFileVO.setDate(rs.getString(GeneticConstants.DatabaseColumns.DATE_COL));
			habitatFileVO.setIpAddress(rs.getString(GeneticConstants.DatabaseColumns.IPADDRESS_COL));
			habitatFileVO.setSessionName(rs.getString(GeneticConstants.DatabaseColumns.SESSIONAPPID_COL));
			habitatFileVO.setSessionId(rs.getInt(GeneticConstants.DatabaseColumns.SESSIONID_COL));
			habitatFileVO.setUserName(rs.getString(GeneticConstants.DatabaseColumns.USERNAME_COL));
			
			Timestamp timestamp =rs.getTimestamp(GeneticConstants.DatabaseColumns.TIMEOFACTION_COL);
			
			Date date = timestamp;
			habitatFileVO.setTimeValue(date);
			habitatFileVO.setTimeStampInStr(String.valueOf(timestamp));
			return habitatFileVO;

		}

	}

	@Override
	public List<HabitatFileVO> viewHabitatFileForUserIdAndSessionId(String userId, String sessionId) {
		try {
			String sql = sqlProperties.getMessage(GeneticConstants.DATABASESQL.RETRIVE_HABITATFILE_WHERE_USERNAME_AND_SESSIONAPP_SQL, null,
					null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(GeneticConstants.Keys.USERNAME_KEY, userId);
			paramMap.put(GeneticConstants.DatabaseColumns.SESSIONAPPID_COL, sessionId);

			return namedJdbcTemplate.query(sql, paramMap, new HabitatFileVOMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

}

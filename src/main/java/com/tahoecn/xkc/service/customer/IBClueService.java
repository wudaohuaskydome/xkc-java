package com.tahoecn.xkc.service.customer;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tahoecn.xkc.converter.Result;
import com.tahoecn.xkc.model.customer.BClue;
import com.tahoecn.xkc.model.miniprogram.vo.customerreport.MBrokerReportVO;
import com.tahoecn.xkc.model.vo.Customer;
import com.tahoecn.xkc.model.vo.RegisterRuleBaseModel;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YYY
 * @since 2019-07-03
 */
public interface IBClueService extends IService<BClue> {

    Map<String, Object> mBrokerCustomerDetail_Select(String clueID);

    Map<String,Object> ValidateForReport(String userID, String mobile, String projectId, Map<String, Object> map);

    boolean IsExistReportProtectClue_Select(String projectId, String mobile);

    List<Map<String, Object>> RuleClueList_Select(String projectId, String mobile, String userID);

    String getMessage(int invalidType, Map<String, Object> map);

    String GetMessageForReturn(int invalidType, Map<String, Object> map);

    boolean createClue(String channelOrgId, Map<String, Object> ruleValidate, RegisterRuleBaseModel UserRule, int status, Map paramMap);

	/**
	 * 思为小程序自由经纪人/自渠人员报备客户生成线索
	 *
	 * @param channelOrgId
	 * @param ruleValidate
	 * @param UserRule
	 * @param status
	 * @param mBrokerReportVO
	 * @return
	 */
	boolean createClue(String channelOrgId, Map<String, Object> ruleValidate, RegisterRuleBaseModel UserRule, int status, MBrokerReportVO mBrokerReportVO);

	boolean kfCreateClue(String channelOrgId, Map<String, Object> ruleValidate, RegisterRuleBaseModel UserRule, int status, Map paramMap, Integer flag) throws Exception;
	/**
	 * 案场助手统计
	 */
	Map<String, Object> CaseFieDetail_Select(Map<String, Object> paramMap);
	/**
	 * 客户信息
	 */
	List<Map<String,Object>> CaseFieCustomerDetail_Select(Map<String, Object> paramMap);
	/**
	 * 获取顾问接待组数
	 */
	public Map<String, Object> DayTotalCount_Select(String ProjectID, String SaleUserID);
	/**
	 * 客户无效信息
	 */
	List<Map<String,Object>> CaseFieInvalDetail_Select(Map<String, Object> paramMap);

	/**
	 * 验证是否存超过到访保护期
	 */
	Map<String, Object> IsOverdueCome_Select(Map<String, Object> obj);

	/**
	 * 扫码确认线索无效后，更新线索信息
	 */
	void ClueConfirmInvalid_Update(Map parameter);

	/**
	 * 根据手机号、项目id获取有效线索
	 */
	List<Map<String, Object>> getClueList(Map<String, Object> obj);

	Object ClueConfirm_Update(Map<String, Object> parameter);

	/**
	 * 待确认查询
	 */
	IPage<Map<String, Object>> CaseFieToBeConfirmedList_Select(IPage page, String Status, String ProjectID, String sqlWhere);

	/**
	 * 待分配
	 */
	IPage<Map<String, Object>> CaseFieDistributionList_Select(IPage page, String ProjectID,String UserID, String sqlWhere);
	/**
	 * 报备信息列表
	 */
	IPage<Map<String, Object>> CaseFielInquiriesList_Select(IPage page, String projectID, String sqlWhere);
	
	/*
	 * 客户详情
	 */
	public Customer detail(String clueId);
	
	/*
	 * 我的客户列表
	 */
	public List<Customer> listMyCustomers(String reportUserId, String order, String nameOrMobile, String status);
	
	/*
	 * 报备
	 */
	public Result report(String reportUserId, String intentProjectId, String customerName, String mobile, String gender, String remark, String projectName);


	void updateComeOverdueTimeByDay(Integer extendArriveProDays, String id);

	void updateComeOverdueTimeByDate(Date extendArriveProEndDate, String id);

	void updateTradeOverdueTimeByDay(Integer extendSigningProDays, String id);

	void updateTradeOverdueTimeByDate(Date extendSigningProEndDate, String id);

	/*
	 * 我的客户列表分类统计
	 */
	public Map<String, Object> listMyCustomersCount(String reportUserId);
}

package com.tahoecn.xkc.mapper.channel;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tahoecn.xkc.model.channel.BChanneluser;
import com.tahoecn.xkc.model.dto.ChannelInsertDto;
import com.tahoecn.xkc.model.miniprogram.vo.UserRegisterVO;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author YYY
 * @since 2019-06-25
 */
public interface BChanneluserMapper extends BaseMapper<BChanneluser> {

    List<Map<String, String>> AgenApproverList();

    void ChannelDetail_InsertN(ChannelInsertDto channelInsertDto);

    void ChannelStatus_UpdateN(@Param("ID")String id, @Param("UserID")String userID, @Param("Status")String status);

    int ChannelAgenUserNameIsExist_SelectN(@Param("UserName")String userName,@Param("sqlWhere")String sqlWhere);

    int ChannelAgenMobileIsExist_SelectN(@Param("Mobile")String mobile,@Param("sqlWhere")String sqlWhere);

    HashMap<String, Object> ChannelUserCurrency_Find(@Param("Mobile")String mobile);

    List<Map<String, Object>> BrokerMyCenter_Select(@Param("BrokerID")String brokerID);

    int mBrokerChannelUserCardDetail_Update(@Param("UserID")String userID, @Param("CertificatesName")String certificatesName,
                                            @Param("CertificatesType")String certificatesType, @Param("CertificatesNo")String certificatesNo,
                                            @Param("CertificatesPicFace")String certificatesPicFace, @Param("CertificatesPicBack")String certificatesPicBack);

    int mBrokerChannelUserBankCardDetail_Update(@Param("UserID")String userID, @Param("BankCardPerson")String bankCardPerson, @Param("BankCardCreate")String bankCardCreate, @Param("BankCard")String bankCard, @Param("BankCardProvince")String bankCardProvince, @Param("BankCardCity")String bankCardCity, @Param("BankCardArea")String bankCardArea, @Param("BankCardBranch")String bankCardBranch, @Param("BankCardPic")String bankCardPic);

    Map<String, Object> mBrokerChannelUserDetail_Select(@Param("UserID")String userID);

    int mBrokerChannelUserDetail_Upate(@Param("UserID")String userID, @Param("Name")String name, @Param("Gender")String gender, @Param("Mobile")String mobile, @Param("ChannelTypeID")String channelTypeID);

    Map<String, Object> ChannelUser_Find(@Param("Mobile")String mobile,@Param("Password") String password);
    
    /*
	 * 用户信息查询
	 */
	List<BChanneluser> ChannelUser_Detail_FindById(Map<String, Object> map);

	/**
	 * 修改密码-判断原密码是否正确
	 */
	List<BChanneluser> ChannelUserPassWord_Select(Map<String, Object> map);
	/**
	 * 修改密码
	 */
	boolean ChannelUserPassWord_Update(Map<String, Object> map);
	/**
	 * 忘记密码修改
	 */
	boolean ChannelUserForgetPassWord_Update(Map<String, Object> map);

    String getChannelOrgID(@Param("UserID") String userID);

    Map<String, Object> GetReportUserInfo_Select(@Param("UserID")String userID, @Param("IntentProjectID")String intentProjectID, @Param("ChannelIdentify")String ChannelIdentify);

	/*
   	 * 删除兼职成员
   	 */
	void mChannelTempPerson_Delete(Map<String, Object> map);

	/*
	 * 专员考勤
	 */
	Map<String, Object> mChannelCheckClockTotal_Select(Map<String, Object> map);

	/*
	 * 详细考勤
	 *
	 */
	List<Map<String, Object>> mChannelTaskCheckClockList_Select(IPage page, @Param("ChannelTaskID")String ChannelTaskID, @Param("CheckDate")String CheckDate, @Param("where")String where);

	/*
	 * 详细考勤数量
	 */
	int mChannelTaskCheckClockList_SelectAllCount(Map<String, Object> map);

	/*
	 * 打卡状态查询
	 */
	List<Map<String, Object>> mChannelCheckClockPage_Select(Map<String, Object> map);

	/*
	 * 考勤信息查询
	 */
	List<Map<String, Object>> mChannelCheckClockPerson_Select(IPage page, @Param("UserID")String UserID, @Param("CheckDate")String CheckDate);

	/*
	 * 考勤信息数量统计
	 */
	int mChannelCheckClockPerson_SelectAllCount(Map<String, Object> map);

	/*
	 *  兼职考勤日历打卡信息
	 */
	List<Map<String, Object>> mChannelCheckClockCountDaily_Select(Map<String, Object> map);

	/*
	 * 考勤异常
	 */
	List<Map<String, Object>> mChannelUserAbnormal_Select(IPage page, @Param("ChannelTaskID")String ChannelTaskID, @Param("SiteUrl")String SiteUrl);

	/*
	 * 考勤异常数量统计
	 */
	int mChannelUserAbnormal_SelectAllCount(Map<String, Object> map);

	/**
	 * 获取分销渠道所属机构ID
	 */
	List<Map<String, Object>> GetChannelOrgID_Select(Map<String, Object> obj);

	void insertBChannelUser(Map<String, Object> map);

    IPage<Map<String, Object>> AgenList_SelectN(IPage page, @Param("where")String where);

    IPage<Map<String,Object>> ChannelOrgList_Select(IPage<Object> objectPage, @Param("sql")String s);

	Integer getB_PojectChannelOrgRel(@Param("OrgID")String orgID,@Param("ProjectID")String projectID);

	void ChannelOrgImport_Insert_B_PojectChannelOrgRel(@Param("orgID")String orgID, @Param("projectID")String projectID, @Param("userID")String userID);

	void ChannelOrgImport_Insert_B_ClueRule_AdviserGroup(@Param("RuleIDs")String RuleIDs, @Param("OrgID")String OrgID, @Param("UserID")String UserID);

    List<Map<String, Object>> AgenChannelTypeList_SelectN();

    Map<String, Object> ChannelUser_Detail_FindByIdN(@Param("id") String id);

    IPage<Map<String, Object>> mChannelStoreUserList_SelectN(IPage page, @Param("StoreID")String storeID, @Param("sqlWhere")String sqlWhere, @Param("Parameter")String Parameter);

    void ChannelUserDetail_UpateN( @Param("name")String name, @Param("gender")String gender,
                                   @Param("userID")String userID, @Param("sqlUpdate")String sqlUpdate);

    int CustomerCount(@Param("id")String id);

    void QuitUser_Update(@Param("id")String id, @Param("userID")String userID);

    IPage<Map<String, Object>> mCustomerTCList_SelectN(IPage page, @Param("userID")String userID, @Param("keyWord")String keyWord);

    void mCustomerTCTransfer_Update(@Param("ids")String ids, @Param("transferID")String transferID, @Param("userID")String userID);

    Map<String,Object> ChannelOrgRuleInfoDetail_Select(@Param("projectId")String projectId, @Param("adviserGroupID")String adviserGroupID);

    Map<String, Object> checkUser(@Param("username")String username, @Param("mobile")String mobile);

    Map<String, Object> getUserInfo(@Param("id")String id);

	/**
	 * 思为小程序用户注册校验是否存在
	 *
	 * @param mobile
	 * @return
	 */
	int isExist(@Param("mobile") String mobile);

	/**
	 * 思为小程序客储用户中心验证
	 *
	 * @param userRegisterVO
	 * @return
	 */
	BChanneluser getBChannelUser(@Param("userRegister") UserRegisterVO userRegisterVO);
}

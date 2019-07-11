package com.tahoecn.xkc.service.sys;


import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tahoecn.xkc.converter.Result;
import com.tahoecn.xkc.model.vo.UnreadCountVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YYY
 * @since 2019-06-17
 */
public interface ISystemMessageService {

	/**
	 * 未读消息数
	 */
    List<UnreadCountVo> UnreadCountListByMessageType_Select(Map<String, Object> map);


    /**
     * 消息列表-普通消息
     */
    List<Map<String,Object>> SystemMessageListByMessageType_Select(Map<String, Object> map);

    /**
     * 消息列表-普通消息-总数
     */
    int SystemMessageListByMessageType_SelectCount(Map<String, Object> map);
    
	/**
	 * 更新消息信息
	 */
	void updMessage(Map<String, Object> map);

	/**
	 * 消息列表-渠道任务消息
	 */
	List<Map<String,Object>> ListByMessageTypeChannelTask_Select(Map<String, Object> map);

	/**
	 * 消息列表-渠道任务消息-总数
	 */
	int ListByMessageTypeChannelTask_SelectCount(Map<String, Object> map);

	/**
	 * 消息列表-机会消息-自渠
	 */
	List<Map<String,Object>> SystemMessageListByMessageTypeOpportunityZQ_Select(Map<String, Object> map);

	/**
	 * 消息列表-机会消息-自渠-总数
	 */
	int SystemMessageListByMessageTypeOpportunityZQ_SelectCount(Map<String, Object> map);

	/**
	 * 更新消息信息-自渠
	 */
	void updMessageZQ(Map<String, Object> map);


	/**
	 * 消息列表-丢失消息
	 */
	List<Map<String,Object>> ListByMessageTypeKHDS_Select(Map<String, Object> map);


	/**
	 * 消息列表-丢失消息-总数
	 */
	int ListByMessageTypeKHDS_SelectCount(Map<String, Object> map);

	/**
	 * 顾问客户消息列表
	 */
	List<Map<String,Object>> SystemMessageListByMessageTypeOpportunity_Select(Map<String, Object> map);

	/**
	 * 顾问客户消息列表-总数
	 */
	int SystemMessageListByMessageTypeOpportunity_SelectCount(Map<String, Object> map);

	/**
	 * 拓客客户消息列表
	 */
	List<Map<String,Object>> SystemMessageListByMessageTypeClue_Select(Map<String, Object> map);

	/**
	 * 拓客客户消息列表-总数
	 */
	int SystemMessageListByMessageTypeClue_SelectCount(Map<String, Object> map);
	
	/**
	 * 该项目是否有分享项目信息
	 */
	int IsExistsShareProject(Map<String, Object> map);

	/**
	 * 消息详情
	 */
	List<Map<String, Object>> SystemMessageDetail_Select(Map<String, Object> map);

	/**
	 * 消息添加
	 */
	void SystemMessageDetail_Insert(Map<String, Object> map);

	/**
	 * 设消息为已读
	 */
	void SystemMessageReadDetail_Update(Map<String, Object> map);
    /**
     * H5消息列表
     */
    List<Map<String, Object>> mMessageAllList_Select(IPage page, String userID);
    
    /**
     * 消息处理状态更新
     * @param BizIDs
     * @param BizType
     * @param msgHandType
     * @return
     */
    public Result DetailByHandle_Update(String[] BizIDs, String BizType,int msgHandType);

	/**
	 * 设置动态消息已读
	 */
	void mMessageDynamicReadList_Update(Map<String, Object> map);
	
	/**
     * 消息添加
     * @param UserID
     * @param ProjectID
     * @param BizID
     * @param BizType
     * @param Subject
     * @param Content
     * @param Receiver
     * @param msgType
     * @param IsNeedPush
     * @return
     */
    public Result Detail_Insert(String UserID, String ProjectID, String BizID, String BizType, String Subject, String Content, String Receiver, String msgType, Boolean IsNeedPush);
	
}

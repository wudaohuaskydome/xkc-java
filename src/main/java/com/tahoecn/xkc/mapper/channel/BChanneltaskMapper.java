package com.tahoecn.xkc.mapper.channel;

import com.tahoecn.xkc.model.channel.BChanneltask;
import com.tahoecn.xkc.model.channel.BChanneltaskarea;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author YYY
 * @since 2019-07-04
 */
public interface BChanneltaskMapper extends BaseMapper<BChanneltask> {

	/*
	 * 新建任务
	 */
	String mChannelTask_Insert(Map<String, Object> map) ;
	void mChannelTask_Insert2(Map<String, Object> map);
	void mChannelTask_Insert3(Map<String, Object> map);
	Map<String, Object> mChannelTask_Insert4(Map<String, Object> map);

	/*
	 * 结束任务
	 */
	void mChannelTaskClose_Update(Map<String, Object> map);
	void mChannelTaskClose_Update2(Map<String, Object> map);
	List<BChanneltask> mChannelTaskClose_Update3(Map<String, Object> map);
	
	/*
	 * 专员-是否有进行中的任务
	 */
	List<Map<String, Object>> mChannelTaskIsHaveUndoneZQ_Select(Map<String, Object> map);
	/*
	 *  兼职考勤日历打卡信息
	 */
	List<Map<String, Object>> mChannelTaskIsHaveUndoneJZ_Select(Map<String, Object> map);

	/*
	 * 打卡
	 */
	boolean mChannelCheckClock_Insert(Map<String, Object> map);

	/*
	 * 获取当天第一条打卡记录
	 */
	Map<String, Object> mChannelCheckClockTopOne_Select(Map<String, Object> map);

	/*
	 * 获取当天第一条打卡记录
	 */
	Map<String, Object> mChannelCheckClockByDeviceCode_Select(Map<String, Object> map);

	
	/*
	 * 分配专员客户
	 */
	void mChannelTempPersonQuitCus_Update(Map<String, Object> map);

	void mChannelTempPersonQuitCus_Update2(Map<String, Object> map);

	void mChannelTempPersonQuitCus_Update3(Map<String, Object> map);
	
	/*
	 * 领取任务
	 */
	int mChannelTaskAccept_Insert(Map<String, Object> map);
	void mChannelTaskAccept_Insert2(Map<String, Object> map);
	void mChannelTaskAccept_Insert3(Map<String, Object> map);
	/*
	 * 更新兼职归属关系
	 */
	void mChannelTaskAccept_Update(Map<String, Object> map);
	/*
	 * 领取的任务状态
	 */
	List<Map<String, Object>> mChannelTaskAccept_Select(Map<String, Object> map);
	
	/*
	 * 写入任务结束信息
	 */
	void mChannelLeaderQuit_Update(Map<String, Object> map);
	/*
	 * 强制结束任务 
	 */
	void mChannelLeaderQuit_Insert(Map<String, Object> map);
	void mChannelLeaderQuit_Update2(Map<String, Object> map);
	/*
	 * 写入将分配的线索跟进记录
	 */
	void mChannelLeaderQuit_Insert2(Map<String, Object> map);
	/*
	 * 分配线索
	 */
	void mChannelLeaderQuit_Update3(Map<String, Object> map);
	/*
	 * 分配兼职 
	 */
	void mChannelLeaderQuit_Update4(Map<String, Object> map);
	
	/*
	 * 经理团队列表
	 */
	IPage<Map<String, Object>> mChannelLeaderList_Select(IPage page, @Param("ProjectID")String ProjectID, @Param("sqlWhere")String sqlWhere);

	/*
	 * 是否保存锁房图片到本地
	 */
	void CustomerLockRoomClientSaveDetail_Update(Map<String, Object> map);
	
	/*
	 * 经理作战图任务列表
	 */
	List<Map<String, Object>> mChannelLeaderTaskList_Select(Map<String, Object> map);
	
	/*
	 * 判断该兼职是否有所属专员
	 */
	Map<String, Object> mChannelUserByID_Select(@Param("UserID")String UserID);
	
	/*
	 * 任务列表查询
	 */
	IPage<Map<String, Object>> mChannelTaskList_Select(IPage page, @Param("whereStr")StringBuilder whereStr, @Param("JZCode")StringBuilder JZCode);
	

}

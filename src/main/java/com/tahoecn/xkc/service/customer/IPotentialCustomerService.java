package com.tahoecn.xkc.service.customer;

import com.alibaba.fastjson.JSONObject;
import com.tahoecn.xkc.converter.Result;

public interface IPotentialCustomerService {
	
	/**
	 * 潜在客户标签新增
	 * @param paramAry
	 * @return
	 */
	public Result mCustomerPotentialTagDetail_Insert(JSONObject paramAry);
	
	/**
	 * 用户关注潜在客户列表
	 * @param paramAry
	 * @return
	 */
	public Result mUserFollowPotentialList_Select(JSONObject paramAry);
	
	/**
	 * 用户关注潜在客户新增
	 * @param paramAry
	 * @return
	 */
	public Result mUserFollowPotentialDetail_Insert(JSONObject paramAry);
	
	/**
	 * 用户关注潜在客户删除
	 * @param paramAry
	 * @return
	 */
	public Result mUserFollowPotentialDetail_Delete(JSONObject paramAry);
	
	/**
	 * 复制潜在客户
	 * @param paramAry
	 * @return
	 */
	public Result mCustomerPotentialCopyList_Insert(JSONObject paramAry);
	
	/**
	 * 自渠潜在客户基本信息
	 * @param paramAry
	 * @return
	 */
	public Result mCustomerPotentialZQBaseDetail_Select(JSONObject paramAry);
	
	/**
	 * 自渠潜在客户详细信息
	 * @param paramAry
	 * @return
	 */
	public Result mCustomerPotentialZQDetail_Select(JSONObject paramAry);
}

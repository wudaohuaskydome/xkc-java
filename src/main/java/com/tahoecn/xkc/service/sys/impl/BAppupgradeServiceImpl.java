package com.tahoecn.xkc.service.sys.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tahoecn.xkc.mapper.sys.BAppupgradeMapper;
import com.tahoecn.xkc.model.sys.BAppupgrade;
import com.tahoecn.xkc.service.sys.IBAppupgradeService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author YYY
 * @since 2019-07-03
 */
@Service
public class BAppupgradeServiceImpl extends ServiceImpl<BAppupgradeMapper, BAppupgrade> implements IBAppupgradeService {

	@Autowired
	private BAppupgradeMapper bAppupgradeMapper;
	/**
	 * 获取版本信息-默认第一条
	 */
	@Override
	public List<BAppupgrade> SystemAppVersion_Select(Map<String, Object> map) {
		return bAppupgradeMapper.SystemAppVersion_Select(map);
	}
	/**
	 * APP版本信息
	 */
	@Override
	public List<Map<String,Object>> SystemAppVersionList_Select() {
		return bAppupgradeMapper.SystemAppVersionList_Select();
	}
	/**
	 * APP版本信息修改
	 */
	@Override
	public void SystemAppVersion_Update(String versionID,String AppVersionCode,String Url) {
		BAppupgrade ba = new BAppupgrade();
		ba.setId(versionID);
		ba.setAppVersionCode(AppVersionCode);
		ba.setAppVersionName(AppVersionCode + "版本");
		ba.setUrl(Url);
		bAppupgradeMapper.updateById(ba);
	}

}

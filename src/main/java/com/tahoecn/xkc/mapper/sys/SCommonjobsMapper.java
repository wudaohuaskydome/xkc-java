package com.tahoecn.xkc.mapper.sys;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tahoecn.xkc.model.sys.SCommonjobs;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author YYY
 * @since 2019-06-26
 */
public interface SCommonjobsMapper extends BaseMapper<SCommonjobs> {
	
	List<SCommonjobs> SystemCommonJobsList_Select(Map<String, Object> map);
	
	List<SCommonjobs> SystemCommonJobsList_SelectList();
	
	String SystemCommonJobNameIsExists_Select(Map<String, Object> map);
	
	void SystemCommonJobStatus_Update(Map<String, Object> map);
	
	void SystemCommonJob_Delete(Map<String, Object> map);
	
	void SystemCommonJob_Insert(Map<String, Object> map);
	
	void SystemCommonJob_Update(Map<String, Object> map);
}

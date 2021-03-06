package com.tahoecn.xkc.mapper.project;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tahoecn.xkc.model.project.VProjectroom;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author YYY
 * @since 2019-07-06
 */
public interface VProjectroomMapper extends BaseMapper<VProjectroom> {
	/**
	 * 获取项目房间列表信息
	 */
	List<Map<String, Object>> ProjectRoomList_Select(Map<String,Object> map);
	/**
	 * 获取项目房间列表信息(营销经理)
	 */
	List<Map<String, Object>> ProjectRoomListYXJL_Select(Map<String, Object> paramMap);

    List<Map<String, Object>> RoomTypeList_Select(@Param("BuildingID") String buildingID);
}

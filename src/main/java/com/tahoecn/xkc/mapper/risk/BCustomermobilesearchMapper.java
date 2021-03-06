package com.tahoecn.xkc.mapper.risk;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tahoecn.xkc.model.risk.BCustomermobilesearch;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author YYY
 * @since 2020-05-08
 */
public interface BCustomermobilesearchMapper extends BaseMapper<BCustomermobilesearch> {

    /**
     * @description: 风控查询搜电数据
     * @return:
     * @author: 张晓东
     * @time: 2020/5/8 18:06
     */
    List<Map<String, Object>> fkSearchMobileInfo(@Param("projectID") String projectID, @Param("mobile") String mobile, @Param("createTime") Date createTime);
}

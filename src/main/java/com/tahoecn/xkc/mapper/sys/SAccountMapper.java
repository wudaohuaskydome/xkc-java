package com.tahoecn.xkc.mapper.sys;

import com.tahoecn.xkc.model.sys.SAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author YYY
 * @since 2019-06-21
 */
public interface SAccountMapper extends BaseMapper<SAccount> {

    HashMap<String,String> getUserJob(@Param("userName") String userName);
    HashMap<String,String> getUserPorject(@Param("userId") String userId,@Param("productID") String productID);
    List<HashMap<String,String>> getUserProduct(@Param("userName") String userName);

    List<HashMap<String,String>> getUserWXApp(@Param("userName") String userName);
    List<HashMap<String,String>> getUserJobs(@Param("userName") String userName,@Param("productID") String productID);
    List<HashMap<String,String>> getUserJobMenus(@Param("userName") String userName,@Param("productID") String productID);
    List<HashMap<String,String>> getJobFunctions(@Param("userName") String userName,@Param("productID") String productID);
    List<HashMap<String,String>> getJobProject(@Param("userName") String userName);
}

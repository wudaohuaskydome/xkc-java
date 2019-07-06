package com.tahoecn.xkc.service.customer.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tahoecn.xkc.mapper.customer.BCustomerMapper;
import com.tahoecn.xkc.model.customer.BCustomer;
import com.tahoecn.xkc.service.customer.IBCustomerService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author YYY
 * @since 2019-07-05
 */
@Service
public class BCustomerServiceImpl extends ServiceImpl<BCustomerMapper, BCustomer> implements IBCustomerService {


    @Override
    public IPage<HashMap<String, Object>> customerChangePageList_Select(IPage page,String projectID, String sqlWhere) {
        return baseMapper.customerChangePageList_Select(page,projectID,sqlWhere);
    }
}

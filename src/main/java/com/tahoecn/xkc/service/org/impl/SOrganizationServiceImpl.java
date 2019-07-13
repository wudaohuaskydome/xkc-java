package com.tahoecn.xkc.service.org.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tahoecn.xkc.common.utils.ThreadLocalUtils;
import com.tahoecn.xkc.mapper.org.SOrganizationMapper;
import com.tahoecn.xkc.model.org.SOrganization;
import com.tahoecn.xkc.service.org.ISOrganizationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author YYY
 * @since 2019-07-06
 */
@Service
public class SOrganizationServiceImpl extends ServiceImpl<SOrganizationMapper, SOrganization> implements ISOrganizationService {

    @Override
    public IPage<Map<String,Object>> SystemOrganization_Select(IPage page,String authCompanyID, String orgID, String productID, String pid, String status) {
        return baseMapper.SystemOrganization_Select(page,authCompanyID, orgID, productID, pid, status);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean SystemOrganizationDetail_Updatez(SOrganization organization) {
        try {
            SOrganization byId = this.getById(organization.getId());
            String OldFullPath=byId.getFullPath();
            String Pid=byId.getPid();
            String FullPath=this.getById(Pid).getFullPath();
            String NewFullPath;
            if (StringUtils.isBlank(FullPath)){
                NewFullPath=organization.getOrgName();
            }else {
                NewFullPath=FullPath+"/"+organization.getOrgName();
            }
            String ProductID=byId.getProductID();
            organization.setFullPath(NewFullPath);
            organization.setEditor(ThreadLocalUtils.getUserName());
            organization.setEditTime(new Date());
            this.updateById(organization);
            if (StringUtils.isNotBlank(OldFullPath)){
                QueryWrapper<SOrganization> wrapper=new QueryWrapper<>();
                wrapper.eq("ProductID",ProductID);
                wrapper.likeRight("FullPath",OldFullPath+"/");
                List<SOrganization> list = this.list(wrapper);
                for (SOrganization sOrganization : list) {
                    sOrganization.setFullPath(sOrganization.getFullPath().replace(OldFullPath,NewFullPath));
                    this.updateById(sOrganization);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    @Override
    public List<Map<String, Object>> SystemOrganizationChec_Select(String pid) {
        return baseMapper.SystemOrganizationChec_Select(pid);
    }
}

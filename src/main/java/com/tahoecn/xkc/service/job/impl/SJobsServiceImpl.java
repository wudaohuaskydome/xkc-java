package com.tahoecn.xkc.service.job.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tahoecn.xkc.common.utils.ThreadLocalUtils;
import com.tahoecn.xkc.mapper.job.SJobsMapper;
import com.tahoecn.xkc.model.job.SJobs;
import com.tahoecn.xkc.model.job.SJobsmenurel;
import com.tahoecn.xkc.model.job.SJobsuserrel;
import com.tahoecn.xkc.model.sys.SAccount;
import com.tahoecn.xkc.model.sys.SCommonjobsfunctionsrel;
import com.tahoecn.xkc.model.sys.SCommonjobsmenurel;
import com.tahoecn.xkc.model.sys.SMenus;
import com.tahoecn.xkc.service.job.ISJobsService;
import com.tahoecn.xkc.service.job.ISJobsmenurelService;
import com.tahoecn.xkc.service.job.ISJobsuserrelService;
import com.tahoecn.xkc.service.sys.ISAccountService;
import com.tahoecn.xkc.service.sys.ISMenusService;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @since 2019-07-04
 */
@Service
public class SJobsServiceImpl extends ServiceImpl<SJobsMapper, SJobs> implements ISJobsService {

    @Autowired
    private ISAccountService accountService;
    @Autowired
    private ISJobsuserrelService jobsuserrelService;
    @Autowired
    private ISJobsmenurelService jobsmenurelService;
    @Autowired
    private ISMenusService menusService;

    @Override
    public IPage<Map<String,Object>> SystemJobList_Select(IPage page, String authCompanyID, String productID, String orgID) {
        return baseMapper.SystemJobList_Select(page,authCompanyID,productID,orgID);
    }

    @Override
    public IPage<SAccount> SystemUserList_Select(IPage page, String jobID, String authCompanyID) {
        return baseMapper.SystemUserList_Select(page,jobID,authCompanyID);
    }

    @Override
    public IPage<Map<String,Object>> SystemJobAllList_Select(IPage page, String authCompanyID, String productID, String orgID) {
        return baseMapper.SystemJobAllList_Select(page,authCompanyID,productID,orgID);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean SystemJobUser_Insert(SAccount account, String jobID) {

        try {
            account.setPassword("C8837B23FF8AAA8A2DDE915473CE0991");
            account.setCreator(ThreadLocalUtils.getUserName());
            account.setCreateTime(new Date());
            account.setIsDel(0);
            boolean save = accountService.save(account);
            SJobsuserrel jobsuserrel=new SJobsuserrel();
            jobsuserrel.setJobID(jobID);
            jobsuserrel.setAccountID(account.getId());
            boolean save1 = jobsuserrelService.save(jobsuserrel);
            if (save&&save1){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return false;
    }

    @Override
    public IPage<SAccount> SystemOrgUserList_Select(IPage page, String userName, String employeeName, String jobID) {
        return baseMapper.SystemOrgUserList_Select(page,userName,employeeName,jobID);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean SystemJobUserRel_Insert(String userIDS, String jobID) {
        try {
            baseMapper.SystemJobUserRel_Insert(userIDS,jobID);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }

    }

    @Override
    public boolean SystemJobAuth_Insert(String menus, String jobID) {
        boolean save =false;
        try {
            String[] menusSplit = menus.split(",");
            //删除原功能
            QueryWrapper<SJobsmenurel> wrapper=new QueryWrapper<>();
            wrapper.eq("JobID",jobID);
//            wrapper.in("MenuID",oldMenusSplit);
            List<SJobsmenurel> list = jobsmenurelService.list(wrapper);
            //判断menuID在原menu还是xkc menu  如果原menu不删除
            for (SJobsmenurel jobsmenurel : list) {
                String menuID = jobsmenurel.getMenuID();
                SMenus byId = menusService.getById(menuID);
                if (byId==null){
                    jobsmenurelService.removeById(jobsmenurel);
                }
            }
//            QueryWrapper<SCommonjobsfunctionsrel> queryWrapper=new QueryWrapper<>();
//            queryWrapper.eq("JobID",jobID);
////            queryWrapper.in("FuncID",oldFunctionsSplit);
//            List<SCommonjobsfunctionsrel> list1 = commonjobsfunctionsrelService.list(queryWrapper);
//            for (SCommonjobsfunctionsrel sCommonjobsfunctionsrel : list1) {
//                commonjobsfunctionsrelService.removeById(sCommonjobsfunctionsrel);
//            }
            //新增新菜单
            if (menusSplit.length!=0){
                for (String s : menusSplit) {
                    SJobsmenurel jobsmenurel=new SJobsmenurel();
                    jobsmenurel.setJobID(jobID);
                    jobsmenurel.setMenuID(s);
                     save = jobsmenurelService.save(jobsmenurel);
                }
            }
//        if (functionsSplit.length!=0){
//            for (String s : functionsSplit) {
//                SCommonjobsfunctionsrel commonjobsfunctionsrel=new SCommonjobsfunctionsrel();
//                commonjobsfunctionsrel.setJobID(jobID);
//                commonjobsfunctionsrel.setFuncID(s);
//                commonjobsfunctionsrelService.save(commonjobsfunctionsrel);
//            }
//        }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return save;
    }
}

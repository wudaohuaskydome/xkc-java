package com.tahoecn.xkc.service.miniprogram.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tahoecn.core.json.JSONResult;
import com.tahoecn.xkc.common.enums.CertificatesIdEnum;
import com.tahoecn.xkc.common.enums.ChannelTypeIdEnum;
import com.tahoecn.xkc.common.enums.SexEnum;
import com.tahoecn.xkc.common.enums.TipsEnum;
import com.tahoecn.xkc.common.utils.ResultUtil;
import com.tahoecn.xkc.mapper.channel.BChannelorgMapper;
import com.tahoecn.xkc.mapper.channel.BChanneluserMapper;
import com.tahoecn.xkc.mapper.dict.SDictionaryMapper;
import com.tahoecn.xkc.mapper.opportunity.BOpportunityMapper;
import com.tahoecn.xkc.mapper.sys.SAccountMapper;
import com.tahoecn.xkc.mapper.sys.SysAccessRecordMapper;
import com.tahoecn.xkc.model.channel.BChannelorg;
import com.tahoecn.xkc.model.channel.BChanneluser;
import com.tahoecn.xkc.model.dict.SDictionary;
import com.tahoecn.xkc.model.miniprogram.vo.UserRegisterVO;
import com.tahoecn.xkc.model.opportunity.BOpportunity;
import com.tahoecn.xkc.model.sys.SAccount;
import com.tahoecn.xkc.model.sys.SysAccessRecord;
import com.tahoecn.xkc.service.miniprogram.IUserRegisterService;
import com.tahoecn.xkc.service.sys.ISysAccessManagementService;
import com.tahoecn.xkc.service.sys.ISysAccessRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 思为小程序外渠角色注册接口服务实现类
 * </p>
 *
 * @author zhaoyan
 * @since 2020-03-31
 */
@Service
public class UserRegisterServiceImpl extends ServiceImpl<BChanneluserMapper, BChanneluser> implements IUserRegisterService {

    @Resource
    private ISysAccessManagementService sysAccessManagementService;

    @Resource
    private BChanneluserMapper bChanneluserMapper;

    @Resource
    private ISysAccessRecordService sysAccessRecordService;

    @Resource
    private SysAccessRecordMapper sysAccessRecordMapper;

    @Resource
    private BChannelorgMapper bChannelorgMapper;

    @Resource
    private SDictionaryMapper sDictionaryMapper;

    @Resource
    private BOpportunityMapper bOpportunityMapper;

    @Resource
    private SAccountMapper sAccountMapper;

    @Override
    @Transactional
    public JSONResult userRegister(HttpServletRequest request, UserRegisterVO userRegisterVO) throws Exception {
        // 记录接口访问信息
        SysAccessRecord sysAccessRecord = sysAccessRecordService.getSysAccessRecord(request);
        // 白名单校验
/*        jsonResult = sysAccessManagementService.handlerIpVerification(request);
        if (null != jsonResult) {
            sysAccessRecord.setInterfaceState(String.valueOf(jsonResult.getCode()));
            sysAccessRecord.setReason(jsonResult.getMsg());
            sysAccessRecordMapper.insert(sysAccessRecord);
            return jsonResult;
        }*/
        // userRegisterVO为空校验
        if (null == userRegisterVO) {
            sysAccessRecord.setInterfaceState("1");
            sysAccessRecord.setReason("userRegisterVO不能为空!");
            sysAccessRecordMapper.insert(sysAccessRecord);
            return new JSONResult(1, "userRegisterVO不能为空!");
        }
        // 数字签名验证
/*        jsonResult = RSAUtil.verifySignature(RsaConstants.privateKeyStr, userRegisterVO.getCiphertext(), RsaConstants.signature);
        if (null != jsonResult) {
            sysAccessRecord.setInterfaceState(String.valueOf(jsonResult.getCode()));
            sysAccessRecord.setReason(jsonResult.getMsg());
            sysAccessRecordMapper.insert(sysAccessRecord);
            return jsonResult;
        }*/
        // 查询用户是否重复
        int count = bChanneluserMapper.isExist(userRegisterVO.getMobile());
        if (count > 0) {
            sysAccessRecord.setInterfaceState("1");
            sysAccessRecord.setReason("用户已存在");
            sysAccessRecordMapper.insert(sysAccessRecord);
            return new JSONResult(1, "用户已存在");
        }
        // 用户新增
        BChanneluser bChanneluser = new BChanneluser();
        bChanneluser.setId(UUID.randomUUID().toString());
        bChanneluser.setUserName(userRegisterVO.getMobile());
        bChanneluser.setMobile(userRegisterVO.getMobile());
        bChanneluser.setName(userRegisterVO.getName());
        bChanneluser.setGender(SexEnum.getEnumByCode(Integer.valueOf(userRegisterVO.getGender())).getMessage());
        bChanneluser.setChannelOrgCode(userRegisterVO.getChannelOrgCode());
        // 判断注册用户是否是中介同行
        if (userRegisterVO.getChannelTypeID().equals(ChannelTypeIdEnum.MIDDLE.getCode())) {
            QueryWrapper<BChannelorg> query = new QueryWrapper<BChannelorg>();
            query.eq("OrgCode", userRegisterVO.getChannelOrgCode());
            BChannelorg bChannelorg = bChannelorgMapper.selectOne(query);
            if (null == bChannelorg) {
                sysAccessRecord.setInterfaceState("1");
                sysAccessRecord.setReason("机构不存在");
                sysAccessRecordMapper.insert(sysAccessRecord);
                return new JSONResult(1, "机构不存在");
            }
            bChanneluser.setChannelOrgID(bChannelorg.getId());
            bChanneluser.setJob(2);
        }else{
            //验证老业主
            Integer oldOwner = this.bOpportunityMapper.selectCount(new QueryWrapper<BOpportunity>() {{
                eq("Status", 5);
                eq("IsDel", 0);
                eq("CustomerMobile", userRegisterVO.getMobile());
            }});
            if (userRegisterVO.getChannelTypeID().equals("EB4AD331-F4AD-46D6-889A-D45575ECEE66")) {
                if (null == oldOwner || oldOwner < 1) {
                    return ResultUtil.setJsonResult(TipsEnum.Failed.getCode(), "注册失败，您不能注册老业主");
                }
            }
            if (oldOwner > 0 && !userRegisterVO.getChannelTypeID().equals("EB4AD331-F4AD-46D6-889A-D45575ECEE66")) {
                return ResultUtil.setJsonResult(TipsEnum.Failed.getCode(), "注册失败，您只能注册老业主");
            }
            //验证员工
            Integer staff = this.sAccountMapper.selectCount(new QueryWrapper<SAccount>() {{
                eq("Mobile", userRegisterVO.getMobile());
                eq("Status", 1);
                eq("IsDel", 0);
            }});
            if (userRegisterVO.getChannelTypeID().equals("725FA5F6-EC92-4DC6-8D47-A8E74B7829AD")) {
                if (null == staff || staff < 1) {
                    return ResultUtil.setJsonResult(TipsEnum.Failed.getCode(), "注册失败，您不能注册泰禾员工");
                }
            }
            if (staff > 0 && !userRegisterVO.getChannelTypeID().equals("725FA5F6-EC92-4DC6-8D47-A8E74B7829AD")) {
                return ResultUtil.setJsonResult(TipsEnum.Failed.getCode(), "注册失败，您只能注册泰禾员工");
            }
            bChanneluser.setJob(3);
        }
        bChanneluser.setChannelTypeID(ChannelTypeIdEnum.getEnumByCode(Integer.valueOf(userRegisterVO.getChannelTypeID())).getMessage());
        bChanneluser.setApprover("99");
        bChanneluser.setApprovalStatus(1);
        Date date = new Date();
        bChanneluser.setApprovalDate(date);
        bChanneluser.setCreator(bChanneluser.getId());
        bChanneluser.setCreateTime(date);
        bChanneluser.setIsDel(0);
        bChanneluser.setStatus(1);
        SDictionary channelTypeSDictionary = new SDictionary();
        channelTypeSDictionary = sDictionaryMapper.selectById(bChanneluser.getChannelTypeID());
        bChanneluser.setChannelType(channelTypeSDictionary.getDictName());
        bChanneluser.setCertificatesNo(userRegisterVO.getCertificatesNo());
        bChanneluser.setCertificatesType(CertificatesIdEnum.getEnumByCode(Integer.valueOf(userRegisterVO.getCertificatesType())).getMessage());
        SDictionary certificatesSDictionary = new SDictionary();
        certificatesSDictionary = sDictionaryMapper.selectById(bChanneluser.getCertificatesType());
        bChanneluser.setCertificatesName(certificatesSDictionary.getDictName());
        bChanneluser.setPassword("C8837B23FF8AAA8A2DDE915473CE0991");//设置默认密码123321
        bChanneluserMapper.insert(bChanneluser);
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", bChanneluser.getId());
        sysAccessRecord.setInterfaceState("0");
        sysAccessRecord.setReason("成功");
        sysAccessRecordMapper.insert(sysAccessRecord);
        return ResultUtil.setJsonResult(new JSONResult<>(), map, null, TipsEnum.Success.getCode(), TipsEnum.Success.getMsg());
    }
}

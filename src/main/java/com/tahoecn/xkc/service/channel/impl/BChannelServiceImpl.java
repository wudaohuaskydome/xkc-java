package com.tahoecn.xkc.service.channel.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tahoecn.xkc.mapper.channel.BChanneluserMapper;
import com.tahoecn.xkc.mapper.customer.BClueMapper;
import com.tahoecn.xkc.mapper.customer.BCustomerMapper;
import com.tahoecn.xkc.mapper.customer.BCustomerpotentialMapper;
import com.tahoecn.xkc.model.customer.BClue;
import com.tahoecn.xkc.model.vo.ChannelRegisterModel;
import com.tahoecn.xkc.model.vo.ImmissionRule;
import com.tahoecn.xkc.model.vo.ProtectRule;
import com.tahoecn.xkc.model.vo.RegisterRuleBaseModel;
import com.tahoecn.xkc.service.channel.IBChannelService;
import com.tahoecn.xkc.service.customer.IBClueService;
import com.tahoecn.xkc.service.rule.IBClueruleService;

import cn.hutool.core.date.DateTime;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author YYY
 * @since 2019-06-25
 */
@Service
public class BChannelServiceImpl extends ServiceImpl<BClueMapper,BClue> implements IBChannelService {
	@Resource
	private BChanneluserMapper bChanneluserMapper;
	@Autowired
    private IBClueruleService clueruleService;
	@Autowired
	private BClueMapper bClueMapper;
	@Autowired
	private BCustomerpotentialMapper bCustomerpotentialMapper;
	@Autowired
	private BCustomerMapper bCustomerMapper;
	@Autowired
    private IBClueService iBClueService;
	
	@Override
	public ChannelRegisterModel newChannelRegisterModel(String userId, String adviserGroupID, String projecId){
        String channelOrgId = GetChannelOrgID(userId);
        //如果渠道是分销中介，则把渠道身份赋值成机构ID
        adviserGroupID = StringUtils.isEmpty(channelOrgId) ? adviserGroupID : channelOrgId;
        //获取当前渠道的准入规则
        Map<String,Object> UserRule1 = clueruleService.getRegisterRule(projecId,adviserGroupID);
        RegisterRuleBaseModel UserRule = loadModel(UserRule1);//JSON.parseObject(JSON.toJSONString(UserRule1), RegisterRuleBaseModel.class);
//        this.UserRule = new RegisterRuleBaseModel(adviserGroupID, projecId);
        ChannelRegisterModel model = new ChannelRegisterModel();
        model.setChannelUserId(userId);
        model.setChannelOrgId(channelOrgId);
        model.setUserRule(UserRule);
        return model;
    }
	private RegisterRuleBaseModel loadModel(Map<String, Object> obj) {
		ImmissionRule imm = new ImmissionRule();
		imm.setIsOnlyAllowNew((int)obj.get("IsOnlyAllowNew"));
        imm.setOverdueTime((int)obj.get("OverdueTime"));
        imm.setProtectTypeID((int)obj.get("ProtectTypeID"));
        imm.setValidationMode((int)obj.get("ValidationMode"));
        imm.setOldOwnerLimit((int)obj.get("OldOwnerLimit"));

        ProtectRule pro = new ProtectRule();
        pro.setIsPreIntercept((int)obj.get("IsPreIntercept"));
        pro.setPreInterceptTime((int)obj.get("PreInterceptTime"));
        pro.setIsProtect((int)obj.get("IsProtect"));
        pro.setIsProtectVisit((int)obj.get("IsProtectVisit"));
        pro.setIsSelect((int)obj.get("IsSelect"));
        pro.setProtectTime((int)obj.get("ProtectTime"));
        pro.setProtectVisitTime((int)obj.get("ProtectVisitTime"));
        pro.setUserBehaviorID(obj.get("UserBehaviorID")==null|| "".equals(obj.get("UserBehaviorID")) ?0:Integer.parseInt(obj.get("UserBehaviorID").toString()));
        pro.setIsPermanent((int)obj.get("IsPermanent"));
        
        RegisterRuleBaseModel UserRule = new RegisterRuleBaseModel();
        UserRule.setRuleID(obj.get("ID").toString());
        UserRule.setCalMode((int)obj.get("CalMode"));
        UserRule.setRuleType((int)obj.get("RuleType"));
        UserRule.setProtectSource((int)obj.get("ProtectSource"));
        UserRule.setImmissionRule(imm);
        UserRule.setProtectRule(pro);
		return UserRule;
	}
	/**
     * 获取渠道人员所属组织ID
     */
    private String GetChannelOrgID(String userId){
        Map<String,Object> obj = new HashMap<String,Object>();
        obj.put("UserID", userId);
        List<Map<String,Object>> data = bChanneluserMapper.GetChannelOrgID_Select(obj);
        return (data != null && data.size() > 0) ? data.get(0).get("ChannelOrgID").toString() : "";
    }
    /**
     * 验证报备客户是否有效
     */
	@Override
	public Map<String, Object> ValidateForReport(String mobile, String projectId,
			ChannelRegisterModel channelRegisterModel) {
		Map<String, Object> msg = new HashMap<String, Object>();
        if (clueruleService.IsRepeatedReg(mobile, projectId, channelRegisterModel.getChannelUserId())){
            msg.put("Tag", false);
            msg.put("InvalidType", 9);
            return msg;
        }
        //竞争带看并且是现场确认模式
        if (channelRegisterModel.getUserRule().getRuleType() == 1 
        		&& channelRegisterModel.getUserRule().getImmissionRule().getValidationMode() == 1){
            //判断是否存在报备保护模式的有效线索，由于两种模式互斥，所以需要单独判断
            if (clueruleService.isExistReportProtectClue(mobile, projectId)){
            	msg.put("Tag", false);
                msg.put("InvalidType", 4);
                return msg;
            }else{
                //仅提示报备成功
            	msg.put("Tag", true);
                msg.put("InvalidType", 0);
                return msg;
            }
        }else{//实时确认
            return InstantConfirmation(mobile, projectId,channelRegisterModel);
        }
	}
	/**
	 * 实时确认
	 */
	private Map<String, Object> InstantConfirmation(String phone, String projectId, 
			ChannelRegisterModel channelRegisterModel) {
		Map<String, Object> msg = new HashMap<String, Object>();
        if (channelRegisterModel.getUserRule().getImmissionRule().getIsOnlyAllowNew() == 1){//仅允许报备新客户
            //验证该项目是否已存在销售机会
            boolean flag = clueruleService.IsExistOpportunity(phone, projectId);
            if (flag){//存在销售机会
            	msg.put("Tag", false);
                msg.put("InvalidType", 2);
            }else{//不存在销售机会
                msg = validateClue(phone, projectId,channelRegisterModel);
            }
        } else{//允许报备满足条件的老客户（新客户+不在渠道保护期和案场保护期的老客户）
            //老业主限制，0.不允许报备老业主 1.仅允许报备其它项目老业主
            if (channelRegisterModel.getUserRule().getImmissionRule().getOldOwnerLimit() == 0){
                //验证是否集团老业主
                if (IsOwner(phone)){
                	msg.put("Tag", false);
                    msg.put("InvalidType", 1);
                    return msg;
                }
            }else{
                //验证是否是本项目老业主
            	List<Map<String, Object>> a1 = bCustomerMapper.IsProjectOwner_Select(projectId, phone);
                if (a1 == null || a1.size() == 0 ){
                	msg.put("Tag", false);
                    msg.put("InvalidType", 11);
                    return msg;
                }
            }
            msg = validateClue(phone, projectId, channelRegisterModel);
            if ((boolean)msg.get("Tag") == false)
                return msg;
            msg = validateOpp(phone, projectId);
        }
        return msg;
	}
	/**
	 * 验证是否满足不存在机会或机会没有置业顾问
	 */
	private Map<String, Object> validateOpp(String phone, String projectId) {
		Map<String, Object> msg = new HashMap<String, Object>();
		Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("ProjectID", projectId);
        obj.put("CustomerMobile", phone);
		Map<String,Object> opp = bCustomerpotentialMapper.ValidOpp_Select(obj);
        //不存在销售机会
        if(opp==null||opp.size()==0){
        	msg.put("Tag", true);
            msg.put("InvalidType", 0);
        }else{//存在销售机会
            if(StringUtils.isEmpty(opp.get("SaleUserID").toString()))
            {
                msg.put("Tag", true);
                msg.put("InvalidType", 0);
                msg.put("IsExsitOpp", true);
                msg.put("OppID", opp.get("ID").toString());
            }else{
            	msg.put("Tag", false);
                msg.put("InvalidType", 6);
            }
        }
        return msg;
	}
	/**
	 * 判断客户是否是集团老业主
	 */
	private boolean IsOwner(String phone) {
		Map<String,Object> temp = new HashMap<String,Object>();
    	temp.put("Mobile", phone);
        Map<String,Object> map = bCustomerpotentialMapper.IsOwner_Select(temp);
        if(map == null || map.size() == 0){
        	return false;
        }else{
        	return true;
        }
	}
	/**
	 * 其他条件满足的情况下，验证报备的线索是否有效
	 */
	private Map<String, Object> validateClue(String phone, String projectId,ChannelRegisterModel channelRegisterModel) {
		Map<String, Object> msg = new HashMap<String, Object>();
        //查询项目下存在该手机号的有效线索
        List<Map<String,Object>> clues = bClueMapper.RuleClueList_Select(phone, projectId,channelRegisterModel.getChannelUserId());
        //该项目已经存在已确认线索
        for(Map<String,Object> c : clues){
        	if((int)c.get("Status") == 2){
        		//报备保护
        		if (channelRegisterModel.getUserRule().getRuleType() == 0){
        			msg.put("InvalidType", 4);
        		}else{
        			msg.put("InvalidType", 5);
        		}
        		msg.put("Tag", false);
        		return msg;
        	}
        }
        if (clues.size() == 0){//该手机号为新客户（在允许报备老客户时，此段无意义，仅存在于报备新客户）
        	msg.put("Tag", true);
        	msg.put("InvalidType", 0);
            return msg;
        }
        if (clues.size() == 1){//该手机号线索已存在，此条件下需要进一步证明规则类型
            int ruleType = (int) clues.get(0).get("RuleType");
            if (channelRegisterModel.getUserRule().getRuleType() == ruleType && ruleType == 1){//线索为竞争带看，并且当前渠道用户规则也为竞争带看
            	msg.put("Tag", true);
            	msg.put("InvalidType", 0);
            }else{//线索为报备保护
            	msg.put("Tag", false);
            	msg.put("InvalidType", 4);
            }
        }else{//该手机号存在多条线索，说明报备模式为竞争带看
            int ruleType = (int) clues.get(0).get("RuleType");
            if (channelRegisterModel.getUserRule().getRuleType() == ruleType){//当前渠道的报备规则也为竞争带看
            	msg.put("Tag", true);
            	msg.put("InvalidType", 0);
            }else{//当前渠道的报备规则为报备保护，报备规则模式不同则互斥
            	msg.put("Tag", false);
            	msg.put("InvalidType", 4);
            }
        }
        return msg;
	}
	/**
	 * 错误提示信息，用于返回到页面展示
	 */
	@Override
	public String GetMessageForReturn(int invalidType, RegisterRuleBaseModel userRule) {
		String msg = "";
        switch (invalidType){
            case 0:
                msg = userRule.getRuleType() == 1 ? "报备成功，为避免其他渠道抢先带看，请尽快到案场进行带客确认！" : "报备成功，您可以在“我的客户”中查看客户的最新状态！";
                break;
            case 1:
                msg = "报备无效，该客户为集团老业主！";
                break;
            case 2:
                msg = "报备无效，该客户为项目老客户！";
                break;
            case 3:
                msg = "报备无效，不满足防截客条件！";
                break;
            case 4:
                msg = "报备无效，该客户已被其他渠道报备！";
                break;
            case 5:
                msg = "报备无效，该客户已被其他渠道带看！";
                break;
            case 6:
                msg = "报备无效，该客户处于保护期内！";
                break;
            case 7:
                msg = "报备无效，该客户到访逾期！";
                break;
            case 8:
                msg = "报备无效，该客户成交逾期！";
                break;
            case 9:
                msg = "报备无效，该客户已被您报备！";
                break;
            case 10:
                msg = "该客户在案场跟进过程中丢失！";
                break;
            case 11:
                msg = "报备无效，该客户为本项目老业主！";
                break;
        }
        return msg;
	}
	/**
	 * 现场确认验证
	 */
	@Override
	public Map<String,Object> ValidateForConfirmation(String ClueID,ChannelRegisterModel channel) {
		Map<String,Object> msg = new HashMap<String,Object>();
		QueryWrapper<BClue> wrapper = new QueryWrapper<BClue>();
        wrapper.eq("ID", ClueID);
        wrapper.eq("IsDel", 0);
        BClue obj = iBClueService.getOne(wrapper);
        String phone = obj.getMobile();
        String projectId = obj.getIntentProjectID();
        //该线索已经被确认过
        if (obj.getStatus() == 2){
            msg.put("Tag", false);
            msg.put("InvalidType", -1);
            msg.put("Message", "该线索已被案场确认过！");
            return msg;
        }
        //判断待验证线索是否已经失效
        if (obj.getStatus() == 3){
        	msg.put("Tag", false);
            msg.put("InvalidType", obj.getInvalidType());
            msg.put("Message", obj.getInvalidReason());
            return msg;
        }
        //到访超过保护期
        if (IsOverdueCome(obj)){
        	msg.put("Tag", false);
            msg.put("InvalidType", 7);
            return msg;
        }
        //是否在防截客时间到访
        if (IsPreIntercept(obj.getCreateTime(),channel)){
        	msg.put("Tag", false);
            msg.put("InvalidType", 3);
            return msg;
        }
        msg = InstantConfirmation(phone, projectId,channel);
        return msg;
	}
	private boolean IsPreIntercept(Date createTime,ChannelRegisterModel channel) {
		if (channel.getUserRule().getProtectRule().getIsPreIntercept() == 1){
            //报备时间+防截客周期小于等于当前时间
            if ((createTime.getMinutes()+channel.getUserRule().getProtectRule().getPreInterceptTime()) <= DateTime.now().getMinutes()){
                return false;
            }else{
                return true;
            }
        }
        else//没有防截客机制
            return false;
	}
	private boolean IsOverdueCome(BClue clue) {
        Map<String,Object> obj = new HashMap<String,Object>();
        obj.put("ClueID", clue.getId());
        Map<String,Object> data = iBClueService.IsOverdueCome_Select(obj);
        return Integer.parseInt(data.get("IsOverdueCome").toString()) == 0 ? false : true;
	}
}

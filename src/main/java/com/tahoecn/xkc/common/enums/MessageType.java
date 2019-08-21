package com.tahoecn.xkc.common.enums;

public enum MessageType {

	当日待跟进("487F2C39-779D-097B-455B-799AC0B3CBB4"),//今日待跟进
	当日跟进逾期("12D36558-A8A1-20D4-58E5-612338026AE7"),//跟进即将逾期
	当日认购逾期("B5CB4E80-B0D2-959A-7FFB-1C391FF9AD9E"),//认购即将逾期
	当日签约逾期("BE78B012-2536-DFDC-0D20-A5A86DD3470F"),//签约即将逾期
	当日回款逾期("0F9709DD-A8FC-6106-99FC-688E10C760B1"),//回款即将逾期
	到访通知("448A9DA4-2F56-4268-AF1E-39983394E2A8"),//到访通知
	系统通知("69331990-DBF4-0A2F-80CD-7BC424AA8985"),
	渠道任务通知("7D7663A7-2867-43D1-89E8-73459A137A1B"),
	带看通知("448A9DA4-2F56-4268-AF1E-39983394E2A8"),
	认筹通知("FE54E9CB-C0EC-45A9-B139-A47BAC9DA7CF"),
	认购通知("814E3403-C0F3-43DC-9763-7C175595DA0F"),
	签约通知("0F45C158-A9B0-40A9-B9DD-6333A4E99245"),
	退房通知("EB7423A9-3C97-4E87-BDF2-2C1DDCC55FE2"),
	无效通知("3698A744-03D6-4715-A642-70455A1C7E9A"),
	客户丢失通知("DD81CE6E-C855-F065-DAA2-7D0C80310088"),
	今日待跟进("487F2C39-779D-097B-455B-799AC0B3CBB4"),
	待完善首访客户资料("1EBCEBA7-FA9C-4BB8-BE3F-215FE2C2A474"),
	首访信息录入逾期("C701046A-CD40-40D2-A30C-CA769D54AC24"),
	跟进即将逾期("12D36558-A8A1-20D4-58E5-612338026AE7"),
	认购即将逾期("B5CB4E80-B0D2-959A-7FFB-1C391FF9AD9E"),
	签约即将逾期("BE78B012-2536-DFDC-0D20-A5A86DD3470F"),
	回款即将逾期("0F9709DD-A8FC-6106-99FC-688E10C760B1"),
	回收提醒("76D9C77E-CD2D-CB4C-4DBC-8F19CDAFE607"),
	到访提醒("39443858-376C-D2C5-8B12-8BF69D837A14"),
	跟进逾期("AD357DE2-4C3C-4EFA-B2A4-94DED89E0248"),
	认购逾期("0848126B-E21C-40DD-82C0-1ACFF0A39552"),
	签约逾期("D138B44C-932D-43A4-8735-4A36AA1784A2"),
	回款逾期("87BE4B55-8920-4204-8FD6-77AF5130BCD2"),
	分配待跟进("55B04B9F-CA2F-4115-AA93-57E64048425F"),
	客户即将失效("F8D73CFB-013E-4BF5-B5CF-D19A5D9C4449"),
	客户失效通知("60C4BA4B-6610-442B-95AC-3517BF62CA92"),
	到访即将逾期("7F7EB34F-F52A-003B-3090-36741A4AA025"),
	成交即将逾期("62CDEE57-534E-2750-1D85-EB2878405DC3"),
	首访信息录入逾期催办("B6B9FDB7-4982-45F2-B41A-93016EFA359F"),
	跟进逾期催办("B7A1F161-7F1F-4A83-AD6C-A2308D74C3C6"),
	认购逾期催办("1FB38070-8CDC-4746-B19E-48E345F8FBE0"),
	签约逾期催办("DB4BC5BC-6784-4570-A996-F7F007E041B7"),
	回款逾期催办("5C1BC740-D89B-42AA-8053-7BF05DFC5A4C"),
	四类消息通知("0000"),//客户丢失通知,回收提醒,系统通知,到访提醒
	催办("1111"),//首访信息录入逾期催办,跟进逾期催办,认购逾期催办,签约逾期催办,回款逾期催办
	楼盘动态("4936AA45-FDC0-4E86-B8EC-AD4A74E77322"),
	预约客户("6DE67909-F856-40E2-959E-1A69EE22D756"),;
	
	// 成员变量  
	private String TypeID;
	
    // 构造方法  
    private MessageType(String TypeID) {  
        this.TypeID = TypeID;  
    }  
    //接口方法  
    public String getTypeID() {  
        return this.TypeID;  
    } 
}

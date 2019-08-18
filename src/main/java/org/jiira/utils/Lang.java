package org.jiira.utils;


public class Lang {
	public static enum TypeEnum {
		Int, String, Boolean
	}

	public static enum LanguageEmum {
		Java, Lua, CSharp
	}

	// 枚举名称
	// proto类型
	public static final String ProtoEnumName = "ProtoTypeEnum";
	// 物品类型
	public static final String BigEnumName = "BigTypeEnum";
	public static final String SmallEnumName = "SmallTypeEnum";
	// 游戏类型
	public static final String GameTypeEnum = "GameTypeEnum";
	// 排行类型
	public static final String RankTypeEnum = "RankTypeEnum";
	// 聊天累心
	public static final String ChatTypeEnum = "ChatTypeEnum";
	// 账户状态类型
	public static final String AccountTypeEnum = "AccountTypeEnum";
	// 队伍类型
	public static final String TeamTypeEnum = "TeamTypeEnum";
	//错误码
	public static final String ErrorCodeEnum = "ErrorCodeEnum";
	//单条更新消息
	public static final String SingleUpdateTypeEnum = "SingleUpdateTypeEnum";
	//单条更新消息
	public static final String BattleMessageTypeEnum = "BattleMessageTypeEnum";
	//任务类型
	public static final String MissionTypeEnum = "MissionTypeEnum";
	//任务需求类型
	public static final String MissionConditionTypeEnum = "MissionConditionTypeEnum";
	//任务类别
	public static final String MissionNameTypeEnum = "MissionNameTypeEnum";

	public static final String ClassName = "CommandCollection";

	// 枚举数组
	public static final FunParameter[] BigEnumList = { FunParameter.n("None", 0, 0, "没有"), FunParameter.n("Exp", 33, 33, "经验"),
			FunParameter.n("Currency", 66, 66, "游戏币"), FunParameter.n("Diamond", 88, 88, "钻石"),
			FunParameter.n("Card", 1000, 4999, "卡牌类"), FunParameter.n("Pack", 5000, 5199, "奖励包"),
			FunParameter.n("Skin", 6000, 6399, "皮肤类"), FunParameter.n("Equip", 7000, 9999, "装备类"), };
	public static final FunParameter[] SmallEnumList = { FunParameter.n("None", 0, 0, "没有"), FunParameter.n("Exp", 33, 33, "经验"),
			FunParameter.n("Currency", 66, 66, "游戏币"), FunParameter.n("Diamond", 88, 88, "钻石"),
			FunParameter.n("Card", 1000, 1999, "普通卡牌"), FunParameter.n("ADVCard", 2000, 2999, "技能卡牌"),
			FunParameter.n("BlinkCard", 3000, 3999, "普通闪卡"), FunParameter.n("ADVBlinkCard", 4000, 4999, "技能闪卡"),
			FunParameter.n("FreePack", 5000, 5099, "免费奖励包"), FunParameter.n("PayPack", 5100, 5199, "付费奖励包"),
			FunParameter.n("PopoSkin", 6000, 6099, "聊天气泡皮肤"), FunParameter.n("DialogueSkin", 6100, 6199, "对白气泡皮肤"),
			FunParameter.n("CardSkin", 6200, 6299, "卡牌皮肤"), FunParameter.n("ModelSkin", 6300, 6399, "角色形象皮肤"),
			FunParameter.n("SkillEquip", 7000, 9499, "技能装备"), FunParameter.n("AttributeEquip", 9500, 9999, "属性装备"), };

	public static final EnumParameter[] GameTypeEnumList = {
			EnumParameter.n("Leisure", "休闲模式"),
			EnumParameter.n("Competition", "竞技模式"),
			EnumParameter.n("Gay", "约战"),
	};
	
	public static final EnumParameter[] BattleMessageTypeEnumList = {
			EnumParameter.n("Greet", "问候"),
			EnumParameter.n("Amazing", "惊叹"),
			EnumParameter.n("Happy", "开心"),
			EnumParameter.n("Sad", "悲伤"),
			EnumParameter.n("Start", "开场白"),
	};
	public static final EnumParameter[] RankTypeEnumList = {
			EnumParameter.n("Leisure", ""),
			EnumParameter.n("Competition", ""),
			EnumParameter.n("Gay", ""),
	};
	public static final EnumParameter[] ChatTypeEnumList = {
			EnumParameter.n("World", ""),
			EnumParameter.n("Organization", ""),
			EnumParameter.n("Single", ""),
	};
	public static final EnumParameter[] AccountTypeEnumList = {
			EnumParameter.n("Offline", ""),
			EnumParameter.n("Online", ""),
			EnumParameter.n("Error", ""),
	};
	public static final EnumParameter[] TeamTypeEnumList = {
			EnumParameter.n("None", ""),
			EnumParameter.n("Red", ""),
			EnumParameter.n("Blue", ""),
	};
	
	public static final EnumParameter[] ErrorCodeEnumList = {
		EnumParameter.n("SocketDisconnectError", "与服务器断开连接"),
		EnumParameter.n("UserNameOrPassWordError", "用户名或密码错误"),
		EnumParameter.n("AccountNotFoundError", "用户不存在"),
		EnumParameter.n("NickNameExistError", "昵称已存在"),
		EnumParameter.n("AccountIsFoundError", "用户已存在"),
		EnumParameter.n("AccountCreateError", "用户创建失败"),
		EnumParameter.n("CardGroupError", "卡组设置失败"),
		EnumParameter.n("BuyCardError", "卡牌合成失败"),
		EnumParameter.n("SellCardError", "卡牌分解失败"),
		EnumParameter.n("MatchRivalError", "请求匹配失败"),
		EnumParameter.n("CancelMatchError", "取消匹配失败"),
		EnumParameter.n("SettingDefaultCardGroupError", "默认卡组设置失败"),
		EnumParameter.n("AccountError", "帐号异常"),
		EnumParameter.n("RepeatLoginError", "帐号已在登录状态"),
		EnumParameter.n("AccountOfflineError", "目标用户是离线状态"),
		EnumParameter.n("BalanceIsNotEnoughError", "余额不足"),
		EnumParameter.n("DataBaseError", "数据库操作失败"),
		EnumParameter.n("CoolingTimeDidNoArriveError", "冷却时间未结束"),
		//好友
		EnumParameter.n("FirendListError", "获取好友列表失败"),
		EnumParameter.n("FirendInfoError", "获取好友信息失败"),
		EnumParameter.n("AddFirendError", "添加好友失败"),
		EnumParameter.n("AddFirendToMysqlError", "添加好友更新数据库失败"),
		//货币
		EnumParameter.n("CurrencyToMysqlError", "游戏币更新数据库失败"),
		EnumParameter.n("DiamondToMysqlError", "钻石更新数据库失败"),
		EnumParameter.n("LackOfCurrencyError", "游戏币不足"),
		EnumParameter.n("LackOfDiamondError", "钻石不足"),
		//战斗
		EnumParameter.n("FightError", "战斗异常"),
		EnumParameter.n("FightReadyError", "战斗准备阶段异常"),
		EnumParameter.n("FightRoomCloseError", "战斗房间已关闭"),
		EnumParameter.n("FightStepError", "战斗步骤异常（出牌位置或不存在该手牌）"),
		EnumParameter.n("FightStepNoTeamError", "错误的出牌队伍"),
		//购买
		EnumParameter.n("BuySkinError", "皮肤购买失败"),
		EnumParameter.n("BuyGiftError", "礼包购买失败"),
		EnumParameter.n("SellSkinError", "皮肤出售失败"),
		EnumParameter.n("SellGiftError", "礼包出售失败"),
		//邮件
		EnumParameter.n("MailRemoveError", "邮件删除失败"),
		EnumParameter.n("MailSendError", "邮件发送失败"),
		//卡组
		EnumParameter.n("NotFoundCardListError", "没有找到卡牌列表"),
		EnumParameter.n("NotFoundCardGroupError", "没有找到默认卡组"),
		EnumParameter.n("CardGroupUseError", "卡组装载失败"),
		EnumParameter.n("CardGroupUnUseError", "卡组卸载失败"),
		EnumParameter.n("CardGroupEnditError", "卡组编辑失败"),
		EnumParameter.n("CardNotFoundError", "卡牌不存在"),
		//卡牌装备
		EnumParameter.n("CardEquipUseError", "卡牌装备装载失败"),
		EnumParameter.n("CardEquipUnUseError", "卡牌装备卸载失败"),
		//物品
		EnumParameter.n("NotFoundItemError", "物品不存在"),
		EnumParameter.n("UpdateSkinError", "皮肤更新失败"),
		EnumParameter.n("ItemListError", "物品列表获取失败"),
		EnumParameter.n("UpdateHeadError", "更新头像失败"),
		//签到
		EnumParameter.n("ToDaySignInError", "今天已经签到过了"),
		EnumParameter.n("MonthSignInError", "本月签到已结束"),
		EnumParameter.n("SignInError", "签到失败"),
		//等级礼包
		EnumParameter.n("LevelGiftInfoError", "等级礼包信息错误"),
		//礼包
		EnumParameter.n("GiftNotFoundError", "礼包不存在"),
		EnumParameter.n("UseGiftError", "使用礼包错误"),
		EnumParameter.n("UseGiftMysqlError", "使用礼包错误[Mysql]"),
		EnumParameter.n("GiftInfoError", "礼包信息错误"),
		EnumParameter.n("GiftOpenError", "打开礼包错误"),
		//
		EnumParameter.n("ToDayReceiveError", "今天已经领取过了"),
		EnumParameter.n("NoRepeatPurchaseError", "不能重复购买"),
		EnumParameter.n("FundOverdueError", "基金已过期"),
		//任务
		EnumParameter.n("RefreshMissionError", "没有可刷新的任务"),
		EnumParameter.n("MissionRefreshTimeToMysqlError", "任务冷却时间更新数据库失败"),
		//新手引导阶段更新失败
		EnumParameter.n("GuideStepRefreshError", "新手引导阶段更新失败"),
		//
		EnumParameter.n("HookError", "操你妈用外挂"),
	};
	
	public static final EnumParameter[] SingleUpdateTypeEnumList = {
			EnumParameter.n("None", "无"),
			EnumParameter.n("Exp", "经验"),
			EnumParameter.n("Level", "等级"),
			EnumParameter.n("Currency", "游戏币"),
			EnumParameter.n("Diamond", "钻石"),
			EnumParameter.n("win", "胜"),
			EnumParameter.n("fail", "负"),
			EnumParameter.n("dogfall", "平"),
			EnumParameter.n("udSkin", "穿戴的皮肤"),
			EnumParameter.n("Item", "物品"),
			EnumParameter.n("LevelGiftState", "等级礼包"),
			EnumParameter.n("FundDiamond", "钻石基金日期更新"),
			EnumParameter.n("EndFundDiamondDay", "钻石基金最后领取天数更新"),
			EnumParameter.n("FundCurrency", "游戏币基金日期更新"),
			EnumParameter.n("EndFundCurrencyDay", "游戏币基金最后领取天数更新"),
			EnumParameter.n("SignIn", "签到更新"),
			EnumParameter.n("SignInCount", "签到次数更新"),
	};
	public static final EnumParameter[] MissionTypeEnumList = {
			EnumParameter.n("Money", "货币数量"),
			EnumParameter.n("CardCount", "卡牌数量"),
			EnumParameter.n("WinCount", "获胜次数"),
			EnumParameter.n("BattleCount", "比赛次数"),
			EnumParameter.n("CardUseCount", "放置卡牌次数"),
			EnumParameter.n("CardFlipCount", "翻转卡牌次数"),
			EnumParameter.n("Consume", "商城消费"),
			EnumParameter.n("PopCount", "发气泡次数"),
			EnumParameter.n("FailCount", "失败次数"),
			EnumParameter.n("WinContry", "胜利国家限制"),
	};
	public static final EnumParameter[] MissionConditionTypeEnumList = {
			EnumParameter.n("None", "无"),
			EnumParameter.n("All", "全部"),
			EnumParameter.n("Money", "任意货币"),
			EnumParameter.n("Currency", "游戏币"),
			EnumParameter.n("Diamond", "钻石"),
			EnumParameter.n("AllCard", "任意卡牌"),
			EnumParameter.n("Card", "普通卡牌"),
			EnumParameter.n("BlinkCard", "闪卡"),
			EnumParameter.n("AllBattle", "任意比赛"),
			EnumParameter.n("AllPVPBatle", "任意PVP比赛"),
			EnumParameter.n("CompetitionPVPBatle", "竞技PVP比赛"),
			EnumParameter.n("LeisurePVPBatle", "休闲PVP比赛"),
			EnumParameter.n("AllPVEBatle", "任意PVE比赛"),
			EnumParameter.n("TutorialPVEBatle", "教学PVE比赛"),
			EnumParameter.n("TryPVEBatle", "试玩PVE比赛"),
			EnumParameter.n("OrdinaryPVEBatle", "普通PVE比赛"),
	};
	public static final EnumParameter[] MissionNameTypeEnumList = {
			EnumParameter.n("Daily", "每日"),
			EnumParameter.n("Achievement", "成就"),
	};
}

package com.work.result;

import java.util.HashMap;
import java.util.Map;

/**
 * 结果代码枚举类
 * <p>
 * <pre>
 *     迁移自原项目的依赖包中的EResultCode类，增加message属性。并用只用到的原有code数据。
 *     编码规范：
 *     AABBCCCC  总长度6位
 *     AA    : 系统类型，范围：00-ZZ；00,99段保留
 *     BB    : 模块类型，范围：00-ZZ
 *     CC    : 错误码，范围：00-ZZ
 * </pre>
 *
 * @author zhangyt
 */
public enum ResultCode {

    /**
     * 成功
     */
    SUCCESS("000000", "成功"),

    /**
     * app success code
     */
    APP_SUCCESS("0","成功"),

    /**
     * 系统异常
     * AA = 99
     */
    ERROR("999999", "系统异常"),
    ERROR_PARAMETER("999998", "参数错误"),
    ERROR_DATA_DUPLICATE("999997", "数据重复"),
    ERROR_DATABASE("999996", "数据库异常"),
    ERROR_RESOURCE_NOT_FOUND("999995", "资源未找到"),
    /** 微服务调用异常,包括404、timeout*/
    ERROR_MICROSERVICES("999994", "微服务调用异常"),
    /** app 通用异常返回信息 */
    ERROR_APP_COMMON("999993", "系统开小差了，请稍后再试~"),
    ERROR_SGIN("999992", "签名验证失败"),
    ERROR_ENCRYPTION("999991", "加密失败"),
    /** 强制升级 */
    ERROR_FORCE_UPGRADE("900001", "学而思培优发布新版本啦，点击“立即升级”体验更多新功能吧~"),

    /**
     * cloudlearn-common
     * AA == 00
     * BB == 01 阿里云文件上传(ALIOSS)
     */
    COMMON_ALIOSS_NOT_INIT("000101", "OSS尚未初始化"),
    COMMON_ALIOSS_SAVEOSSFILE("000102", "保存文件到阿里云异常"),
    COMMON_ALIOSS_GETOSSFILE("000103", "从阿里云获取文件异常"),

    /**
     * 学生年级有误
     */
    ERROR_GRADE("900002", "学员的在读年级有误，请到学而思APP中修改"),
    /**
     * 学生性别有误
     */
    ERROR_SEX("900003", "学员性别有误"),


    /**
     * cloudlearn-passport系统
     * AA = 10
     */
    LOGIN_100000("100000", "登录"),
    LOGIN_100001("100001", "用户名或密码错误"),
    LOGIN_100002("100002", "登录异常"),
    LOGIN_100003("100003", "您的登录状态已失效，请重新登录"),
    LOGIN_100004("100004", "用户名不能为空"),
    LOGIN_100005("100005", "密码不能为空"),
    LOGIN_100006("100006", "密码长度过长"),
    LOGIN_100007("100007", "缺失版本信息"),
    LOGIN_100008("100008", "token不能为空"),
    LOGIN_100009("100009", "参数不能为空"),
    LOGIN_100010("100010", "密码串解密失败"),
    LOGIN_100011("100011", "发送验证码失败"),
    LOGIN_100012("100012", "手机号不能为空"),
    LOGIN_100013("100013", "手机号格式不正确"),
    LOGIN_100014("100014", "User-Agent不能为空"),
    LOGIN_100015("100015", "加密参数不能为空"),
    LOGIN_100016("100016", "密码串过长"),
    LOGIN_100017("100017", "设备唯一标识不能为空"),
    LOGIN_100018("100018", "加密随机数不能为空"),
    LOGIN_100019("100019", "token不能为空"),
    LOGIN_100020("100020", "UID不能为空"),
    LOGIN_100021("100021", "token生成与发送环境不一致, 请重新登录"),
    LOGIN_100022("100022", "token不存在, 请重新登录"),
    LOGIN_100023("100023", "token校验失败, 请重新登录"),
    LOGIN_100024("100024", "保存登录信息失败, 请重新登录"),
    LOGIN_100025("100025", "错误的token"),
    LOGIN_100026("100026", "错误的登录类型"),
    LOGIN_100027("100027", "重复的随机数"),
    LOGIN_100028("100028", "验证码错误"),
    LOGIN_100029("100029", "验证token有效性失败"),
    LOGIN_100030("100030", "强制登录状态不能为空"),
    LOGIN_30001("30001", "用户被踢出"),
    LOGIN_30002("30005", "token已过期"),
    LOGIN_30005("30005", "用户确认登录操作"),
    LOGIN_30006("30006", "当前账号已在其他设备上登录，若不是本人登录，请及时修改密码"),



    /** 管理员异常code */
    ADMINLOGIN_100101("100101", "未查询到管理员账号"),
    ADMINLOGIN_100102("100102", "设备不匹配"),

    /**
     * CLOUDLEARN_IPS_XXGL_TRANSLATOR 业务系统转接
     * AA = 11
     */
    XXGL_TRANSLATOR_110000("110000", "业务系统转接"),
    XXGL_CLASSAUDIT_110001("110001", "班级开班未审核"),
    
    
    YTK_120001("120001", "云听课接口异常"),

    /**
     * cloudlearn-ips-uplevel关卡答题系统
     * AA = 20
     */
    UPLEVEL_200000("200000", "关卡答题系统"),

    /**
     * cloudlearn-ips-web后台管理系统
     * AA = 21
     */
    WEB_210000("210000", "web后台管理系统"),

    /**
     * CLOUDLEARN_IPS_TASKCENTER任务中心系统
     * AA = 22
     */
    TASKCENTER_220000("220000", "任务中心系统"),

    /**
     * CLOUDLEARN_IPS_ACTIVITY活动系统
     * AA = 23
     */
    ACTIVITY_230000("230000", "活动系统"),

    /**
     * CLOUDLEARN_IPS_ENCOURAGE激励系统
     * AA = 24
     */
    ENCOURAGE_240000("240000", "激励系统"),

    /**
     * CLOUDLEARN_IPS_STUDYCENTER学习中心
     * AA = 25
     */
    STUDYCENTER_250000("250000", "学习中心"),

    /**
     * CLOUDLEARN_IPS_XESTEACHER教师端对接系统
     * AA = 26
     */
    XESTEACHER_260000("260000", "教师端对接系统"),

    /**
     * CLOUDLEARN_IPS_ITSDTS ITS系统对接
     * AA = 27
     */
    ITSDTS_270000("270000", "ITS系统对接"),

    /**
     * CLOUDLEARN_IPS_FILEUPLOAD 文件上传
     * AA = 28
     */
    FILEUPLOAD_280000("280000", "文件上传"),

    /**
     * CLOUDLEARN_IPS_ERRBOOK 错题本系统
     * AA = 29
     */
    ERRBOOK_290000("290000", "错题本系统"),
    ERRBOOK_290100("290100", "错题本打印pdf系统"),
    ERRBOOK_290200("290200", "错题pdf生成中"),

    /**
     * CLOUDLEARN_IPS_SCIENCE_MARKET 数学-计算小超市
     * AA = 31
     */
    SCIENCE_MARKET_310000("310000", "数学-计算小超市"),

    /**
     * CLOUDLEARN_IPS_SCIENCE_EXERCISE 数学-练习册
     * AA = 32
     */
    SCIENCE_EXERCISE_320000("320000", "数学-练习册"),

    /**
     * CLOUDLEARN_IPS_CHINESE_LIBRARY 语文-图书馆 51开头为语文图书馆错误码，510001向下排，123...ABC
     * AA = 51
     */
    CHINESE_LIBRARY_510000("510000", "语文-图书馆"),
    CHINESE_LIBRARY_510001("510001", "未找到图书"),

    /**
     * CLOUDLEARN_IPS_CHINESE_RADIO 语文-电台 52开头为语文电台错误码，520001向下排，123...ABC
     * AA = 52
     */
    CHINESE_RADIO_520000("520000", "语文-电台"),

    /**
     * CLOUDLEARN_IPS_ENGLISH_LIBRARY 英语-绘本馆
     * AA = 71
     */
    ENGLISH_LIBRARY_710000("710000", "英语-绘本馆"),

    /**
     * CLOUDLEARN_IPS_ENGLISH_THEATRE 英语-磨耳朵-电台
     * AA = 72
     */
    ENGLISH_THEATRE_720000("720000", "英语-磨耳朵-电台"),

    /** 在线课程异常 */
    ONLINE_ERROR_810000("810000", "连接课堂失败~请刷新页面重试。如仍有问题可联系辅导老师协助处理。"),
    ONLINE_NOT_SUPPORT_810001("810001", "为了更好上课体验，请在iPad上使用学而思培优HD或使用PC客户端上课。（可联系辅导老师获取下载安装方式）"),
    ONLINE_PHONE_NOT_810002("810002", "为了更好上课体验，请在iPad上使用学而思大语文或使用PC客户端上课。（可联系辅导老师获取下载安装方式）"),
    ONLINE_PHONE_NOT_SUPPORT_810003("810003", "为了更好上课体验，请使用PC客户端上课。（可联系辅导老师获取下载安装方式）")


    ;


    private static Map<String, ResultCode> resultCodeMap2Code = new HashMap<>();

    static {
        for (ResultCode resultCode : ResultCode.values()) {
            resultCodeMap2Code.put(resultCode.code,resultCode);
        }
    }

    /**
     * code
     */
    public String code;
    /**
     * 信息
     */
    public String message;

    ResultCode(String code, String message) {
        this.setCode(code);
        this.setMessage(message);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static ResultCode getResultCodeByCode(String code){
        return resultCodeMap2Code.getOrDefault(code,null);
    }

}

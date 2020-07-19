package com.five.questionSystem.common;


/**
 * 自定义业务异常处理类
 */
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 3152616724785436891L;

    public BusinessException(String frdMessage) {
        super(createFriendlyErrMsg(frdMessage));
    }
    public BusinessException(Throwable throwable) {
        super(throwable);
    }
    public BusinessException(Throwable throwable, String frdMessage) {
        super(throwable);
    }


    private static String createFriendlyErrMsg(String msgBody) {
        String prefixStr = "抱歉,";
        String suffixStr = " 请稍后再试或与管理员联系！";

        StringBuffer friendlyErrMsg = new StringBuffer();
        friendlyErrMsg.append(prefixStr)
                .append(msgBody)
                .append(suffixStr);

        return friendlyErrMsg.toString();
    }
}
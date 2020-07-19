package com.five.questionSystem.common;


import org.apache.shiro.authc.AuthenticationException;


/**
 * 自定义业务异常处理类,友好提示
 */
public class UserDisableException extends AuthenticationException {
    private static final long serialVersionUID = 3152616724785436891L;

    public UserDisableException(String frdMessage) {
        super(createFriendlyErrMsg(frdMessage));
    }

    public UserDisableException(Throwable throwable) {
        super(throwable);
    }

    public UserDisableException(Throwable throwable, String frdMessage) {
        super(throwable);
    }


    private static String createFriendlyErrMsg(String msgBody) {
        String prefixStr = "抱歉,";
        String suffixStr = " 请稍后再试!";

        StringBuffer friendlyErrMsg = new StringBuffer();
        friendlyErrMsg.append(prefixStr)
                .append(msgBody)
                .append(suffixStr);

        return friendlyErrMsg.toString();
    }
}
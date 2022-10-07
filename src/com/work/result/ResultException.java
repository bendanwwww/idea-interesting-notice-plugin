package com.work.result;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 抛异方式上层应用的基础对象
 *
 * @author zhangyt
 */
public class ResultException extends RuntimeException {

    /** 错误码 */
    private ResultCode code;
    /** 传递给ResultVo的指令，异常信息是否需要显示 */
    private Boolean showMessage;

    public ResultException() { }

    public ResultException(ResultCode code) {
        /** 抛出异常打印message信息 */
        super(code.getMessage());
        this.code = code;
    }

    public ResultException(String message) {
        /** 没有错误码，默认错误 */
        super(message);
        this.code = ResultCode.ERROR;
    }

    public ResultException(ResultCode code, String message) {
        /** 抛出异常打印message信息 */
        super(message);
        this.code = code;
    }

    public ResultException(ResultCode code, String message, Boolean showMessage) {
        super(message);
        this.code = code;
        this.showMessage = showMessage;
    }

    public ResultException(ResultCode code, Boolean showMessage) {
        super(code.getMessage());
        this.code = code;
        this.showMessage = showMessage;
    }
    public ResultException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResultCode getCode() {
        return code;
    }

    public void setCode(ResultCode code) {
        this.code = code;
    }

    public Boolean getShowMessage() {
        return showMessage;
    }

    public void setShowMessage(Boolean showMessage) {
        this.showMessage = showMessage;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("code", code)
                .append("showMessage", showMessage)
                .toString();
    }
}

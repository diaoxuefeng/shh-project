package com.diao.base.enums;

/**
 * @author dxf
 */
public class ResponseEnum {

    public enum ResponseCode{

        SUCCWESS(10000,"成功"),

        ERROR(500,"失败"),
        ;

        private Integer code;

        private String message;

        ResponseCode(Integer code,String message){
            this.code = code;
            this.message = message;
        }

        public Integer getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}

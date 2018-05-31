package com.diao.base.Exception;

public class BusinessException extends GenericException{

    private static final long serialVersionUID = -4435979898433185113L;
    private String code;

    public BusinessException(String code, String message) {
        super(code, message);
    }

    public BusinessException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof BusinessException)) {
            return false;
        } else {
            BusinessException other = (BusinessException)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                return super.equals(o);
            }
        }
    }

    @Override
    protected boolean canEqual(Object other) {
        return other instanceof BusinessException;
    }

    @Override
    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        result = result * 59 + super.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "BusinessException()";
    }
}

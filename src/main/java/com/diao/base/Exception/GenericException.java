package com.diao.base.Exception;

public class GenericException extends RuntimeException{

    private static final long serialVersionUID = -2269536687119318933L;
    private String code;

    public GenericException() {
    }

    public GenericException(String code, String message) {
        super(message);
        this.code = code;
    }

    public GenericException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof GenericException)) {
            return false;
        } else {
            GenericException other = (GenericException)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (!super.equals(o)) {
                return false;
            } else {
                Object this$code = this.getCode();
                Object other$code = other.getCode();
                if (this$code == null) {
                    if (other$code != null) {
                        return false;
                    }
                } else if (!this$code.equals(other$code)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof GenericException;
    }

    @Override
    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        result = result * 59 + super.hashCode();
        Object $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        return result;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    @Override
    public String toString() {
        return "GenericException(code=" + this.getCode() + ")";
    }
}

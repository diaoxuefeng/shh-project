package com.diao.base.dto;

import com.diao.base.dto.utils.SpringContextUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.core.env.Environment;

import java.io.Serializable;
import java.util.Optional;


public class RestResponseHeader implements Serializable {
    private static final String KEY_APP = "spring.application.name";

    private String code = "10000";
    private String app;
    private String message;

    public RestResponseHeader(String code, String message) {
        this.code = code;
        this.message = message;
        this.app = (String)Optional.ofNullable(this.app).orElse(((Environment)SpringContextUtils.getBean(Environment.class)).getProperty("spring.application.name"));
    }

    public String getCode() {
        return this.code;
    }

    public String getApp() {
        return this.app;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof RestResponseHeader)) {
            return false;
        }

        RestResponseHeader that = (RestResponseHeader) o;

        return new EqualsBuilder()
                .append(code, that.code)
                .append(app, that.app)
                .append(message, that.message)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(code)
                .append(app)
                .append(message)
                .toHashCode();
    }
}

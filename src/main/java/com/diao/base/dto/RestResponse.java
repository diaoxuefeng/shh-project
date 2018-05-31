package com.diao.base.dto;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

public class RestResponse<T> implements Serializable {

    private RestResponseHeader header;
    private T body = null;

    public RestResponseHeader getHeader() {
        return header;
    }

    public void setHeader(RestResponseHeader header) {
        this.header = header;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof RestResponse)) {
            return false;
        }

        RestResponse<?> that = (RestResponse<?>) o;

        return new EqualsBuilder()
                .append(header, that.header)
                .append(body, that.body)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(header)
                .append(body)
                .toHashCode();
    }
}

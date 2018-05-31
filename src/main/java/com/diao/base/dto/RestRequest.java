package com.diao.base.dto;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;


/**
 * @author dxf
 * @param <T>
 */
public class RestRequest<T> implements Serializable {

    private T body = null;

    public RestRequest(){}

    public RestRequest(T body){
        this.body = body;
    }

    public T getBody(){
        return body;
    }

    public void setBody(T body){
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof RestRequest)) {
            return false;
        }

        RestRequest<?> that = (RestRequest<?>) o;

        return new EqualsBuilder()
                .append(body, that.body)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(body)
                .toHashCode();
    }
}

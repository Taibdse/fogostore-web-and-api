package com.example.fogostore.builder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultBuilder<T> implements Serializable {
    private Map<String, String> errors = new HashMap<>();
    private boolean success;
    private T data;
    private String message;
    private String exception;

    public static ResultBuilder build(){
        return new ResultBuilder();
    }

    public ResultBuilder success(boolean success){
        this.setSuccess(success);
        return this;
    }

    public ResultBuilder message(String message){
        this.setMessage(message);
        return this;
    }

    public ResultBuilder exception(String exception){
        this.setException(exception);
        return this;
    }

    public ResultBuilder data(T data){
        this.setData(data);
        return this;
    }

    public ResultBuilder errors(String key, String value){
        this.getErrors().put(key, value);
        return this;
    }

    public ResultBuilder errors(Map<String, String> errors){
        for(Map.Entry<String, String> entry: errors.entrySet()){
            this.getErrors().put(entry.getKey(), entry.getValue());
        }
        return this;
    }
}
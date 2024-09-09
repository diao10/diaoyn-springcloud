package com.diaoyn.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author diaoyn
 * @create 2024-03-29 15:03:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseVO<T> implements Serializable {


    private int code;
    private String message;
    private T data;

    public ResponseVO(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public static <T> ResponseVO<T> success() {
        ResponseVO<T> vo = new ResponseVO<>();
        vo.setCode(200);
        vo.setMessage("成功");
        return vo;
    }

    public static <T> ResponseVO<T> success(T t) {
        ResponseVO<T> vo = success();
        vo.setData(t);
        return vo;
    }

    public static <T> ResponseVO<T> fail() {
        ResponseVO<T> vo = new ResponseVO<>();
        vo.setCode(500);
        vo.setMessage("失败");
        return vo;
    }

    public static <T> ResponseVO<T> fail(String message) {
        ResponseVO<T> vo = fail();
        vo.setMessage(message);
        return vo;
    }

}

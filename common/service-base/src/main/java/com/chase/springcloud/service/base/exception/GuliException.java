package com.chase.springcloud.service.base.exception;

import com.chase.springcloud.common.base.model.ResultCodeEnum;
import lombok.Data;

/**
 * @author helen
 * @since 2020/4/15
 */
@Data
public class GuliException extends RuntimeException {

    private Integer code;

    public GuliException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public GuliException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "GuliException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}

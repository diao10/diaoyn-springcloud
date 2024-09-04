package com.example.alone.handle;

import com.example.alone.config.BizException;
import com.example.alone.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

import java.util.List;

/**
 * @author diaoyn
 * @create 2024-03-29 15:28:37
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public ResponseVO<Void> exceptionHandle(BindException exception) {
        BindingResult result = exception.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return ResponseVO.fail(fieldErrors.get(0).getDefaultMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseVO<Void> myExceptionHandle(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return ResponseVO.fail(fieldErrors.get(0).getDefaultMessage());
    }

    @ResponseBody
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseVO<?> handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return ResponseVO.fail("数据库中已存在该记录");
    }

    /**
     * BizException
     */
    @ExceptionHandler(BizException.class)
    public ResponseVO<?> handleBizTipException(BizException e) {
        log.error(e.getMessage());
        return ResponseVO.fail(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseVO<Void> doHandleRuntimeException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseVO.fail("系统错误");
    }
}
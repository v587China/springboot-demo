package com.ultra.util;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.ultra.common.Result;

public class CommonUtil {

    private CommonUtil() {
    }

    /**
     * 
     * @Title fieldIllegalMess
     * @Description 对象属性校验错误信息
     * @return Result
     */
    public static ResponseEntity<Result> fieldIllegalMess(BindingResult bindingResult, MessageSource messageSource) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bindingResult.getErrorCount(); i++) {
            FieldError fieldError = bindingResult.getFieldErrors().get(i);
            sb.append(fieldError.getField() + messageSource.getMessage(fieldError, null));
            if (i < bindingResult.getErrorCount() - 1) {
                sb.append(",");
            }
        }
        return ResponseEntityGenerator.bad(sb.toString());
    }

    /**
     * 
     * @Title transIds2Str
     * @Description 将IDlist转换成字符串，使用分隔符
     * @return StringBuilder
     */
    public static StringBuilder transIds2Str(List<Long> ids, String splitChar) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ids.size(); i++) {
            sb.append(ids.get(i).toString());
            if (i != ids.size() - 1) {
                sb.append(splitChar);
            }
        }
        return sb;
    }
}

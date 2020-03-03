package com.stip.net.config;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionResolver {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);

    /**
            *处理所有不可知异常
     *
     * @param e 异常
     * @return json结果
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Map<String,Object> handleException(Exception e) {
    	Map<String,Object> jsonResult=new HashMap<String, Object>(1);
    	logger.error(e.getMessage(), e);
    	
    	jsonResult.put("message", "当前用户过多，请稍后再试。");
    	
        return jsonResult;
    }

}

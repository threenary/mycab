package com.yourcab.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Business exception")
public class BusinessException extends Exception
{
    static final long serialVersionUID = -3387516993334229948L;


    public BusinessException(String message)
    {
        super(message);
    }

}

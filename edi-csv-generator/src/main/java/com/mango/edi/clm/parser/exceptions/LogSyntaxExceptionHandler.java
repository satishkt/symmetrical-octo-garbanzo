package com.mango.edi.clm.parser.exceptions;

import com.berryworks.edireader.error.EDISyntaxExceptionHandler;
import com.berryworks.edireader.error.RecoverableSyntaxException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class LogSyntaxExceptionHandler implements EDISyntaxExceptionHandler {

    Logger logger = org.slf4j.LoggerFactory.getLogger(LogSyntaxExceptionHandler.class);

    @Override
    public boolean process(RecoverableSyntaxException syntaxException) {
        logger.error("Syntax Exception. class: " + syntaxException.getClass().getName() + "  message:" + syntaxException.getMessage());
        return Boolean.TRUE;
    }
}

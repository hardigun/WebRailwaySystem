package com.tsystems.webrailwaysystem.utils;

import com.tsystems.webrailwaysystem.entities.Message;
import com.tsystems.webrailwaysystem.enums.EMessageType;
import com.tsystems.webrailwaysystem.exceptions.RailwaySystemException;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Date: 22.10.13
 */
@Component
public class ExceptionHandler extends ExceptionHandlerExceptionResolver {

    private static Logger LOGGER = Logger.getLogger("webrailwaysystem");

    @Override
    protected ModelAndView doResolveHandlerMethodException(HttpServletRequest req, HttpServletResponse resp,
                                                           HandlerMethod handlerMethod, Exception exc) {
        exc.printStackTrace();

        RailwaySystemException railwaySystemException = new RailwaySystemException();
        if(exc instanceof RailwaySystemException) {
            railwaySystemException = (RailwaySystemException) exc;
            LOGGER.debug(exc);
        } else if(exc instanceof DataIntegrityViolationException) {
            if(((org.hibernate.exception.ConstraintViolationException) exc.getCause()).getSQLState().equals("23505")) {
                railwaySystemException = new RailwaySystemException("Same object already exist in the database");
            }
            LOGGER.debug(((ConstraintViolationException) exc.getCause()).getSQLException().getMessage());
        } else if(exc instanceof HibernateException) {
            /* for example, happens something bad with connection to database */
            LOGGER.error(exc);
        } else {
            LOGGER.warn(exc);
        }

        String view = "index";
        Map<String, Object> uiModel = new HashMap<String, Object>();
        Class clazz = handlerMethod.getBean().getClass();
        try {
            Field viewField = clazz.getDeclaredField("view");
            String clazzView = (String) viewField.get(handlerMethod.getBean());
            if(!clazzView.isEmpty()) {
                view = clazzView;
            }

            Field uiModelField = clazz.getDeclaredField("uiModel");
            uiModel = (Map<String, Object>) uiModelField.get(handlerMethod.getBean());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if(uiModel != null) {
            Iterator iter = uiModel.entrySet().iterator();
            while(iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String key = (String) entry.getKey();
                Object obj= entry.getValue();
                req.setAttribute(key, obj);
            }
        }
        req.setAttribute("message", new Message(railwaySystemException.getMessage(), EMessageType.ERROR));
        return new ModelAndView(view);
    }
}

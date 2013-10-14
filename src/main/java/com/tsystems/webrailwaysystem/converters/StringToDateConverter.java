package com.tsystems.webrailwaysystem.converters;

import com.tsystems.webrailwaysystem.utils.ApplicationUtil;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * Date: 13.10.13
 */
public class StringToDateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String date) {
        return ApplicationUtil.parseStrToDate(date);
    }

}

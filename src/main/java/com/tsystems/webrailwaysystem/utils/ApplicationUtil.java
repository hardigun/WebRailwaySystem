package com.tsystems.webrailwaysystem.utils;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Contract: use for client local purposes
 *
 * Date: 24.09.13
 */
public class ApplicationUtil {

    public static Date parseStrToDate(String parseStr) {
        Date date = null;
        String[] dateFormatsArray = new String[] {
                "dd.MM.yyyy HH:mm",
                "dd.MM.yyyy"
        };
        for(String dateFormatStr : dateFormatsArray) {
            try {
                date = new SimpleDateFormat(dateFormatStr).parse(parseStr);
                break;
            } catch (ParseException e) {

            }
        }
        return date;
    }

}

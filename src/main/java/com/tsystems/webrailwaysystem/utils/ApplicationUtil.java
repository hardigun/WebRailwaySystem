package com.tsystems.webrailwaysystem.utils;

import com.tsystems.webrailwaysystem.exceptions.EncryptionGenerationException;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
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

    public static String getMD5(String str) throws EncryptionGenerationException {
        String md5Str = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] md5Array = md.digest();

            StringBuffer md5StrBuf = new StringBuffer();
            for(byte i = 0; i < md5Array.length; i++) {
                if((0xFF & md5Array[i]) < 0x10) {
                    md5StrBuf.append("0").append(Integer.toHexString(0xFF & md5Array[i]));
                } else {
                    md5StrBuf.append(Integer.toHexString(0xFF & md5Array[i]));
                }
            }

            if(md5StrBuf.length() == 0) {
                throw new Exception();
            }

            md5Str = md5StrBuf.toString();
        } catch(Exception exc) {
            throw new EncryptionGenerationException();
        }

        return md5Str;
    }

}

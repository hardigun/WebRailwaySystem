package com.tsystems.webrailwaysystem.tags;

import com.tsystems.webrailwaysystem.entities.SheduleItemEntity;
import com.tsystems.webrailwaysystem.entities.StationEntity;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Date: 13.10.13
 */
public class SheduleTableTag extends SimpleTagSupport {

    private static Logger LOGGER = Logger.getLogger("webrailwaysystem");

    private List<SheduleItemEntity> sheduleItemsList = new ArrayList<SheduleItemEntity>();

    private String radioClass = "sheduleItemRadio";

    public void doTag() throws JspException {
        PageContext pageContext = (PageContext) getJspContext();
        JspWriter out = pageContext.getOut();
        try {
            StringBuffer resultBuf = new StringBuffer();
            resultBuf.append("<table class='CSSTableGenerator'><tr><td></td><td>Train number</td><td>Date and time</td></tr>");
            for(SheduleItemEntity sheduleItem : this.getSheduleItemsList()) {
                resultBuf.append("<tr>");

                StationEntity station = new StationEntity();
                if(sheduleItem.getRoute().getStationsList().iterator().hasNext()) {
                    station = sheduleItem.getRoute().getStationsList().iterator().next();
                }
                GregorianCalendar dateStation = new GregorianCalendar();
                dateStation.setTime(sheduleItem.getDepartureDate());
                dateStation.add(Calendar.MINUTE, station.getTimeOffset());

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

                resultBuf.append("<td><input type='radio' name='sheduleItemsGroup' class='").append(this.radioClass)
                         .append("' shedule_item_id='").append(String.valueOf(sheduleItem.getId())).append("' /></td>");
                resultBuf.append("<td>").append(sheduleItem.getTrain().getTrainNumber()).append("</td>");
                resultBuf.append("<td>").append(simpleDateFormat.format(dateStation.getTime())).append("</td>");

                resultBuf.append("<tr>");
            }
            resultBuf.append("</table>");
            out.println(resultBuf.toString());
        } catch(Exception exc) {
            exc.printStackTrace();
            LOGGER.warn(exc);
        }
    }

    public List<SheduleItemEntity> getSheduleItemsList() {
        return sheduleItemsList;
    }

    public void setSheduleItemsList(List<SheduleItemEntity> sheduleItemsList) {
        this.sheduleItemsList = sheduleItemsList;
    }

    public String getRadioClass() {
        return radioClass;
    }

    public void setRadioClass(String radioClass) {
        this.radioClass = radioClass;
    }
}

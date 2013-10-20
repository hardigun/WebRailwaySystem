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
public class StationsTableTag extends SimpleTagSupport {

    private static Logger LOGGER = Logger.getLogger("webrailwaysystem");

    private List<SheduleItemEntity> sheduleItemsList = new ArrayList<SheduleItemEntity>();

    private String radioClass = "sheduleItemRadio";

    public void doTag() throws JspException {
        PageContext pageContext = (PageContext) getJspContext();
        JspWriter out = pageContext.getOut();
        try {
            StringBuffer resultBuf = new StringBuffer();
            resultBuf.append("<table class='CSSTableGenerator'><tr><td></td><td>Train number</td><td>Departure</td><td>Arrival</td></tr>");

            for(SheduleItemEntity sheduleItem : this.getSheduleItemsList()) {
                resultBuf.append("<tr>");

                StationEntity stationFrom = sheduleItem.getRoute().getStationsList().get(0);
                StationEntity stationTo = sheduleItem.getRoute().getStationsList().get(1);

                GregorianCalendar dateStationFrom = new GregorianCalendar();
                dateStationFrom.setTime(sheduleItem.getDepartureDate());
                dateStationFrom.add(Calendar.MINUTE, stationFrom.getTimeOffset());

                GregorianCalendar dateStationTo = new GregorianCalendar();
                dateStationTo.setTime(sheduleItem.getDepartureDate());
                dateStationTo.add(Calendar.MINUTE, stationTo.getTimeOffset());

                resultBuf.append("<td><input type='radio' name='sheduleItemsGroup' class='").append(this.radioClass)
                        .append("' shedule_item_id='").append(String.valueOf(sheduleItem.getId())).append("' /></td>");
                resultBuf.append("<td>").append(sheduleItem.getTrain().getTrainNumber()).append("</td>");

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

                StringBuffer strBuf = new StringBuffer();
                strBuf.append(stationFrom.getStationInfo().getTitle());
                strBuf.append(" ").append(simpleDateFormat.format(dateStationFrom.getTime()));
                resultBuf.append("<td>").append(strBuf).append("</td>");

                strBuf = new StringBuffer();
                strBuf.append(stationTo.getStationInfo().getTitle());
                strBuf.append(" ").append(simpleDateFormat.format(dateStationTo.getTime()));
                resultBuf.append("<td>").append(strBuf).append("</td>");

                resultBuf.append("</tr>");
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

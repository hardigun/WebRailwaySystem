package com.tsystems.webrailwaysystem.tags;

import com.tsystems.webrailwaysystem.entities.RouteEntity;
import com.tsystems.webrailwaysystem.entities.SheduleItemEntity;
import com.tsystems.webrailwaysystem.entities.TicketEntity;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Date: 15.10.13
 */
public class TrainsByRoutesTreeTag extends SimpleTagSupport {

    private static Logger LOGGER = Logger.getLogger("webrailwaysystem");

    private Map<RouteEntity, List<SheduleItemEntity>> sheduleItemsByRoutesMap =
            new HashMap<RouteEntity, List<SheduleItemEntity>>();

    public void doTag() throws JspException {
        JspContext context = (JspContext) getJspContext();
        JspWriter out = context.getOut();
        try {
            StringBuffer resultBuf = new StringBuffer();
            resultBuf.append("<div id='routesTrainsTree'><ul><li id='route_all' class='expanded folder'>Routes");

            /* display with SheduleItem info as inner ul element */
            Iterator routesMapIter = this.sheduleItemsByRoutesMap.entrySet().iterator();
            if(this.sheduleItemsByRoutesMap.size() > 0) {
                resultBuf.append("<ul>");
            }
            while(routesMapIter.hasNext()) {
                Map.Entry entry = (Map.Entry) routesMapIter.next();
                RouteEntity route = (RouteEntity) entry.getKey();
                List<SheduleItemEntity> sheduleItemsList = (List<SheduleItemEntity>) entry.getValue();

                resultBuf.append("<li id='route_").append(route.getId())
                        .append("' class='folder'>").append(route.getRouteNumber()).append("<ul>");
                for(SheduleItemEntity sheduleItem : sheduleItemsList) {
                    resultBuf.append("<li id='train_").append(sheduleItem.getDepartureDate().getTime()).append("'>")
                             .append(sheduleItem.getTrain().toString()).append("<ul>");

                    resultBuf.append("<li id='shedule_item_").append(sheduleItem.getId())
                            .append("'>").append(this.getSheduleItemInfo(sheduleItem));
                    resultBuf.append("</ul>");
                }
                resultBuf.append("</ul>");
            }
            if(this.sheduleItemsByRoutesMap.size() > 0) {
                resultBuf.append("</ul>");
            }
            resultBuf.append("</ul></div>");

            /* return result html */
            out.println(resultBuf.toString());

        } catch(Exception exc) {
            exc.printStackTrace();
            LOGGER.warn(exc);
        }
    }

    private String getSheduleItemInfo(SheduleItemEntity sheduleItem) {
        StringBuffer sheduleItemInfoBuf = new StringBuffer();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        sheduleItemInfoBuf.append("Departure date: ")
                          .append(simpleDateFormat.format(sheduleItem.getDepartureDate()).toString());

        sheduleItemInfoBuf.append(" Tickets ").append(sheduleItem.getTicketsList().size())
                .append(" from ")
                .append(sheduleItem.getTrain().getCapacity());

        int confirmedCount = 0;
        for(TicketEntity ticketEntity : sheduleItem.getTicketsList()) {
            if(ticketEntity.isSaleConfirmed()) {
                confirmedCount += 1;
            }
        }
        sheduleItemInfoBuf.append(" Tickets confirm ").append(confirmedCount)
                .append(" from ")
                .append(sheduleItem.getTicketsList().size());

        return sheduleItemInfoBuf.toString();
    }

    public Map<RouteEntity, List<SheduleItemEntity>> getSheduleItemsByRoutesMap() {
        return sheduleItemsByRoutesMap;
    }

    public void setSheduleItemsByRoutesMap(Map<RouteEntity, List<SheduleItemEntity>> sheduleItemsByRoutesMap) {
        this.sheduleItemsByRoutesMap = sheduleItemsByRoutesMap;
    }
}

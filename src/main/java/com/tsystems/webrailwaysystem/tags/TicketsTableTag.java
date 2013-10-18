package com.tsystems.webrailwaysystem.tags;

import com.tsystems.webrailwaysystem.entities.TicketEntity;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 17.10.13
 */
public class TicketsTableTag extends SimpleTagSupport {

    private static Logger LOGGER = Logger.getLogger("webrailwaysystem");

    private List<TicketEntity> ticketsList = new ArrayList<TicketEntity>();

    private String checkboxClass = "ticketConfirmCheckbox";

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    @Override
    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
        JspWriter out = pageContext.getOut();
        try {
            StringBuffer outBuf = new StringBuffer();
            String tHeader = "<tr><td></td><td>Train number</td><td>Route number</td><td>Departure date</td>" +
                             "<td>Passenger surname</td><td>Passenger name</td><td>Passenger birthday</td>" +
                             "<td>Is ticket confirmed</td>";
            outBuf.append("<table id='ticketsTable'>").append(tHeader);

            for(TicketEntity ticket : this.ticketsList) {
                outBuf.append("<tr><td><input type='checkbox' class='").append(this.checkboxClass).append("' id='ticket_")
                      .append(ticket.getId()).append("' /></td>");
                outBuf.append("<td>").append(ticket.getSheduleItem().getTrain().getTrainNumber()).append("</td>");
                outBuf.append("<td>").append(ticket.getSheduleItem().getRoute().getRouteNumber()).append("</td>");
                outBuf.append("<td>").append(this.simpleDateFormat.format(ticket.getSheduleItem().getDepartureDate()))
                      .append("</td>");
                outBuf.append("<td>").append(ticket.getPassenger().getSurname()).append("</td>");
                outBuf.append("<td>").append(ticket.getPassenger().getName()).append("</td>");
                outBuf.append("<td>").append(this.simpleDateFormat.format(ticket.getPassenger().getBirthday()))
                      .append("</td>");
                outBuf.append("<td id='confirm-status_").append(ticket.getId()).append("'>")
                      .append(ticket.isSaleConfirmed()).append("</td></tr>");
            }

            out.println(outBuf.toString());
        } catch(Exception exc) {
            exc.printStackTrace();
            LOGGER.warn(exc);
        }
    }

    public List<TicketEntity> getTicketsList() {
        return ticketsList;
    }

    public void setTicketsList(List<TicketEntity> ticketsList) {
        this.ticketsList = ticketsList;
    }

    public String getCheckboxClass() {
        return checkboxClass;
    }

    public void setCheckboxClass(String checkboxClass) {
        this.checkboxClass = checkboxClass;
    }
}

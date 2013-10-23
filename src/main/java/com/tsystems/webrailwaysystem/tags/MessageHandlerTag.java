package com.tsystems.webrailwaysystem.tags;

import com.tsystems.webrailwaysystem.entities.Message;
import com.tsystems.webrailwaysystem.enums.EMessageType;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;


/**
 * Date: 20.10.13
 */
public class MessageHandlerTag extends SimpleTagSupport {

    private static Logger LOGGER = Logger.getLogger("webrailwaysystem");

    private Message message;

    @Override
    public void doTag() throws JspException, IOException {
        PageContext context = (PageContext) getJspContext();
        JspWriter out = context.getOut();
        try {
            if(message == null) {
                return;
            }
            StringBuffer outBuf = new StringBuffer();
            outBuf.append("<div");

            switch (message.getType()) {
                case INFO:
                    outBuf.append(" class='message-info'>");
                    break;
                case SUCCESS:
                    outBuf.append(" class='message-success'>");
                    break;
                case ERROR:
                    outBuf.append(" class='message-error'>");
                    break;
                default:
                    outBuf.append(">");
                    break;
            }

            outBuf.append(message.getText()).append("</div>");
            outBuf.append("<br/>");
            out.println(outBuf.toString());

        } catch(Exception exc) {
            exc.printStackTrace();
            LOGGER.warn(exc);
        }
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}

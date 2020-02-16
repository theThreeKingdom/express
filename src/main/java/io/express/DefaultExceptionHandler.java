package io.express;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Default exception handler which just print the exception trace on web page.
 *
 * @author Nixin
 */
public class DefaultExceptionHandler implements ExceptionHandler {
    /**
     * Handle exception that print stack trace on HTML page.
     */
    public void handle(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
        response.setCharacterEncoding("utf-8");
        PrintWriter pw = response.getWriter();
        pw.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
        pw.write("<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><title>Exception</title>");
        pw.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
        pw.write("<link class=\"resources library\" rel=\"stylesheet\" type=\"text/css\" href=\"/css/error.css\">");
        pw.write("</head><body><div id=\"container\">");
        pw.write("<img class=\"png\" src=\"/images/404.png\" />");
        pw.write("<img class=\"png msg\" src=\"/images/404_msg.png\" />");
        pw.write("<p><a href=\"/\"><img class=\"png\" src=\"/images/404_to_index.png\"/></a></p></div>");
        pw.write("<div id=\"cloud\" class=\"png\"></div>");
        pw.write("<pre style=\"display:none\">");
        //e.printStackTrace(pw);
        pw.write("</pre></body></html>");
        pw.flush();
    }
}

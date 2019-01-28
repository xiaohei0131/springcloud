package com.aicloud.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "router", urlPatterns = "/*")
public class RouterServlet extends HttpServlet {
    private static RouterUtil routerUtil = new RouterUtil();

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            routerUtil.send(req, resp);
        } catch (Exception e) {
            if (req.getHeader("x-requested-with") != null
                    && req.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
                PrintWriter writer = resp.getWriter();
                writer.write(e.getMessage());
                writer.flush();
            } else {
                throw new ServletException(e.getMessage(), e);
            }
        }
    }
}

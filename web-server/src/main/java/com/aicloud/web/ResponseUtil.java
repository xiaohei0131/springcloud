package com.aicloud.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ResponseUtil {
    public void resolveResp(HttpServletResponse servletResponse, ResponseEntity responseEntity) throws IOException {
        addResponseHeaders(servletResponse, responseEntity);
        writeResponse(servletResponse, responseEntity);
    }

    private void addResponseHeaders(HttpServletResponse servletResponse, ResponseEntity responseEntity) {
        HttpHeaders httpHeaders = responseEntity.getHeaders();
        for (Map.Entry<String, List<String>> entry : httpHeaders.entrySet()) {
            String headerName = entry.getKey();
            List<String> headerValues = entry.getValue();
            for (String headerValue : headerValues) {
                servletResponse.addHeader(headerName, headerValue);
            }
        }
    }

    private void writeResponse(HttpServletResponse servletResponse, ResponseEntity responseEntity) throws IOException {
        if (servletResponse.getCharacterEncoding() == null) { // only set if not set
            servletResponse.setCharacterEncoding("UTF-8");
        }
        if (responseEntity.hasBody()) {
            byte[] body = (byte[]) responseEntity.getBody();
            ServletOutputStream outputStream = servletResponse.getOutputStream();
            outputStream.write(body);
            outputStream.flush();
        }
    }
}

package com.aicloud.gateway.fliter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

@Component
public class GlobalFallbackprovider implements FallbackProvider {
    private static final Logger logger = LoggerFactory.getLogger(GlobalFallbackprovider.class);
    private static HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    @Override
    public String getRoute() {
        // 表明是为哪个微服务提供回退，*表示为所有微服务提供回退
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        logger.error("服务【" + route + "】不可用。", cause);
        return new ClientHttpResponse() {
            @Override
            public HttpHeaders getHeaders() {
                // headers设定
                HttpHeaders headers = new HttpHeaders();
                MediaType mt = new MediaType("application", "json", Charset.forName("UTF-8"));
                headers.setContentType(mt);
                return headers;
            }

            @Override
            public InputStream getBody() throws IOException {
                /*Map<String,Object> message = new HashMap<>();
                message.put("code",-1);
                message.put("message","服务不可用，请稍后再试。");
                return new ByteArrayInputStream(message.toString().getBytes());*/
                return new ByteArrayInputStream("{\"code\":-1,\"message\":\"服务不可用，请稍后再试。\"}".getBytes());
            }

            @Override
            public HttpStatus getStatusCode() throws IOException {
                return status;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.INTERNAL_SERVER_ERROR.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
            }

            @Override
            public void close() {

            }
        };
    }
}

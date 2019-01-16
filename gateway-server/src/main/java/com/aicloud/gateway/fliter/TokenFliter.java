package com.aicloud.gateway.fliter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class TokenFliter extends ZuulFilter {
    private static final Logger logger = LoggerFactory.getLogger(TokenFliter.class);

    /**
     * 前置过滤器。
     * <p>
     * 但是在 zuul 中定义了四种不同生命周期的过滤器类型：
     * <p>
     * 1、pre：可以在请求被路由之前调用；
     * <p>
     * 2、route：在路由请求时候被调用；
     * <p>
     * 3、post：在route和error过滤器之后被调用；
     * <p>
     * 4、error：处理请求时发生错误时被调用；
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤的优先级，数字越大，优先级越低。
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * 是否执行该过滤器。
     * <p>
     * true：说明需要过滤；
     * <p>
     * false：说明不要过滤；
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器的具体逻辑。
     *
     * @return
     */
    @Override
    public Object run() {
        return  null;
       /* RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        logger.info("--->>> TokenFilter {},{}",
                request.getMethod(), request.getRequestURL().toString());
        String token = request.getParameter("token");
        // 获取请求的参数
        if (StringUtils.isNotBlank(token)) {
            ctx.setSendZuulResponse(true);//对请求进行路由
            ctx.setResponseStatusCode(200);
            ctx.set("isSuccess", true);
            return null;
        } else {
            ctx.setSendZuulResponse(false); //不对其进行路由
            ctx.setResponseStatusCode(400);
            ctx.setResponseBody("token is empty");
            ctx.set("isSuccess", false);
            return null;
        }*/

    }
}

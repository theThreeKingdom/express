package com.sample;

import io.express.ActionContext;
import io.express.Mapping;
import io.express.renderer.Renderer;
import io.express.renderer.TemplateRenderer;
import io.express.renderer.TextRenderer;
import io.express.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName Sample
 * @Description a sample to test
 * @Author nixin
 * @Date 2020/3/1
 * @Version 1.0.0
 **/
public class Sample {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Mapping("/")
    public Renderer home() {
        return new TextRenderer("Hello world");
    }

    @Mapping("/test.html")
    public Renderer test(){
        Map<String,Object> model = new HashMap<String,Object>();
        try{
            String client_ip = ActionContext.getActionContext().getHttpServletRequest().getHeader("X-Real-IP");

            if(client_ip == null || client_ip.length() == 0 || "unknown".equalsIgnoreCase(client_ip))
                client_ip = ActionContext.getActionContext().getHttpServletRequest().getHeader("x-forwarded-for");
            if(client_ip == null || client_ip.length() == 0 || "unknown".equalsIgnoreCase(client_ip))
                client_ip = ActionContext.getActionContext().getHttpServletRequest().getHeader("Proxy-Client-IP");
            if(client_ip == null || client_ip.length() == 0 || "unknown".equalsIgnoreCase(client_ip))
                client_ip = ActionContext.getActionContext().getHttpServletRequest().getHeader("WL-Proxy-Client-IP");
            if(client_ip == null || client_ip.length() == 0 || "unknown".equalsIgnoreCase(client_ip))
                client_ip = ActionContext.getActionContext().getHttpServletRequest().getRemoteAddr();

            log.info("client ip = " +client_ip);
            model.put("client",client_ip);
        }catch (Exception e){

        }
        return new TemplateRenderer("/test.html","model",model);
    }
}

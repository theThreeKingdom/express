package io.express.template;

import io.express.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @ClassName ThymeleafTemplateFactory
 * @Description TODO
 * @Author nixin
 * @Date 2021/12/31
 * @Version 1.0.0
 **/
public class ThymeleafTemplateFactory extends TemplateFactory {
    private final Logger log = LoggerFactory.getLogger(getClass());


    private String inputEncoding = "UTF-8";
    private String outputEncoding = "UTF-8";

    @Override
    public void init(Config config) {
        String webAppPath = config.getServletContext().getRealPath("/");
        if (webAppPath == null) {
            String err = "Cannot get web application path. Are you deploy the application as a .war file?";
            log.warn(err);
            throw new ExceptionInInitializerError(err);
        }
        if (!webAppPath.endsWith("/") && !webAppPath.endsWith("\\"))
            webAppPath = webAppPath + File.separator;
        log.info("Detect web application path: " + webAppPath);
        log.info("init ThymeleafTemplateFactory...");


    }

    @Override
    public Template loadTemplate(String path) throws Exception {
        if (log.isDebugEnabled())
            log.debug("Load Thymeleaf template '" + path + "'.");

        return new ThymeleafTemplate(null,null,outputEncoding);
    }
}

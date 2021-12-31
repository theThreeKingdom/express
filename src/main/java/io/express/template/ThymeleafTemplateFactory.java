package io.express.template;

import io.express.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName ThymeleafTemplateFactory
 * @Description TODO
 * @Author nixin
 * @Date 2021/12/31
 * @Version 1.0.0
 **/
public class ThymeleafTemplateFactory extends TemplateFactory {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void init(Config config) {

    }

    @Override
    public Template loadTemplate(String path) throws Exception {
        return null;
    }
}

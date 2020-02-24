package io.express.template;

import io.express.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TemplateFactory which uses JSP.
 */
public class JspTemplateFactory extends TemplateFactory {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public Template loadTemplate(String path) throws Exception {
        if (log.isDebugEnabled())
            log.debug("Load JSP template '" + path + "'.");
        return new JspTemplate(path);
    }

    public void init(Config config) {
        log.info("JspTemplateFactory init ok.");
    }
}

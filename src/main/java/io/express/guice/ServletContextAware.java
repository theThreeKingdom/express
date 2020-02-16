package io.express.guice;

import javax.servlet.ServletContext;

/**
 * Guice module which implements this interface will automatically get the
 * ServletContext object in web application.
 */
public interface ServletContextAware {
    /**
     * Called by GuiceContainerFactory when initialize module.
     *
     * @param servletContext The ServletContext object.
     */
    void setServletContext(ServletContext servletContext);

}

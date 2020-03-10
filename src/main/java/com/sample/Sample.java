package com.sample;

import io.express.Mapping;
import io.express.renderer.Renderer;
import io.express.renderer.TemplateRenderer;
import io.express.renderer.TextRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}

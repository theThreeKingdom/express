package io.express.template;

import org.thymeleaf.context.EngineContext;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @ClassName Thymeleaf
 * @Description TODO
 * @Author nixin
 * @Date 2021/12/31
 * @Version 1.0.0
 **/
public class ThymeleafTemplate implements Template {

    private org.thymeleaf.TemplateEngine template;
    private String contentType;
    private String encoding;

    public ThymeleafTemplate(org.thymeleaf.TemplateEngine template,String contentType,String encoding){
        this.template = template;
        this.contentType = contentType;
        this.encoding = encoding;
    }

    @Override
    public void render(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws Exception {
        StringBuilder sb = new StringBuilder(64);
        sb.append(contentType == null ? "text/html" : contentType).append(";charset=").append(encoding == null ? "UTF-8" : encoding);
        response.setContentType(sb.toString());
        response.setCharacterEncoding(encoding == null ? "UTF-8" : encoding);

        // init context:
        org.thymeleaf.context.Context context = new org.thymeleaf.context.Context();
        org.thymeleaf.templateresolver.ITemplateResolver tr = new org.thymeleaf.templateresolver.ServletContextTemplateResolver(request.getServletContext());
        template.setTemplateResolver(tr);
        template.process("",context,response.getWriter());

    }
}

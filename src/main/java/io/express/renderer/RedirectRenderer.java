package org.express.portal.renderer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedirectRenderer extends Renderer
{
	private String path;
	
	public RedirectRenderer(String path)
	{
		this.path = path;
	}
	
	@Override
	public void render(ServletContext context, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		response.sendRedirect(path);
	}
}

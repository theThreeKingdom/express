package io.express;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Method annotation for mapping URL.<br/> For example:<br/>
 *
 * <pre>
 * public class Blog
 * {
 * 	&#064;Mapping(&quot;/&quot;)
 * 	public String index()
 *    {
 * 		// handle index page...
 *    }
 *
 * 	&#064;Mapping(&quot;/blog/$1&quot;)
 * 	public String show(int id)
 *    {
 * 		// show blog with id...
 *    }
 *
 * 	&#064;Mapping(&quot;/blog/edit/$1&quot;)
 * 	public void edit(int id)
 *    {
 * 		// edit blog with id...
 *    }
 * }
 * </pre>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mapping {
    String value();
}

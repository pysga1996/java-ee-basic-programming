package tutorial;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author thanhvt
 * @project jax-ws-demo
 */
public class CorsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = ((HttpServletResponse) servletResponse);
        httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.addHeader("Access-Control-Allow-Credentials", "false");
        httpServletResponse.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT, PATCH");
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        httpServletResponse.addHeader("Access-Control-Allow-Headers", "Origin, Accept, x-requested-with, Content-Type, SOAPAction, Access-Control-Allow-Headers, Access-Control-Response-Headers, Access-Control-Allow-Methods, Access-Control-Allow-Origin");
        filterChain.doFilter(servletRequest, httpServletResponse); // Goes to default servlet.
    }
}

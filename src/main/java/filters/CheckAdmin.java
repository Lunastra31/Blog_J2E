package filters;

import java.io.IOException;
import java.rmi.ServerException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rene
 */
@WebFilter(filterName = "CheckAdmin", urlPatterns = {"/admin/*"})
public class CheckAdmin implements Filter {

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            doBefore(request, response);
            chain.doFilter(request, response);
            doAfter(request, response);
        } catch (ServletException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void destroy() {
    }

    private void doBefore(ServletRequest request, ServletResponse response) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            resp.sendRedirect(
                    req.getServletContext().getContextPath() + "/");
            throw new ServletException("You are not admin");
        }
        

    }

    private void doAfter(ServletRequest request, ServletResponse response) {
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Rene
 */
@WebFilter(urlPatterns = "/*")
public class SetEncoding implements Filter {

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        doBefore(request, response);
        chain.doFilter(request, response);
        doAfter(request, response);

    }

    @Override
    public void destroy() {
    }

    private void doBefore(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        try {
            req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SetEncoding.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void doAfter(ServletRequest request, ServletResponse response) {
    }

}

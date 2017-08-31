package com.qxcmp.framework.web.filter;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(
        filterName = "CompressResponseFilter",
        urlPatterns = {"/*"}
)
public class CompressResponseFilter extends GenericFilterBean {

    private HtmlCompressor compressor = new HtmlCompressor();

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws IOException, ServletException {

        CharResponseWrapper responseWrapper = new CharResponseWrapper((HttpServletResponse) resp);

        chain.doFilter(req, responseWrapper);

        String servletResponse = responseWrapper.toString();
        resp.getWriter().write(compressor.compress(servletResponse));
    }
}

package cn.edu.xmu.g12.g12ooadgoods.config;


import cn.edu.xmu.g12.g12ooadgoods.controller.CouponController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class HeaderConfig extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(HeaderConfig.class);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        logger.info(httpServletRequest.getRequestURL().toString());
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}

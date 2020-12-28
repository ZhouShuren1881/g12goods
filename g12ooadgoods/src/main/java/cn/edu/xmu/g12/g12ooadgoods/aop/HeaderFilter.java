package cn.edu.xmu.g12.g12ooadgoods.aop;


import com.alibaba.fastjson.JSON;
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
public class HeaderFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(HeaderFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        logger.info(request.getMethod()+ ": " +request.getRequestURL().toString()+" - FilterDone");
        var paramMap = request.getParameterMap();
        if (paramMap != null && paramMap.size() != 0 )
            logger.info("Param: "+ JSON.toJSONString(request.getParameterMap()));
        else
            logger.info("Param: "+"{ }");
        response.setContentType("application/json;charset=UTF-8");
        filterChain.doFilter(request, response);
    }
}

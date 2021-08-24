package com.adminpro20.clientes;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;

@Component
@EnableWebMvc
@Order(Ordered.HIGHEST_PRECEDENCE)
//public class RequestFilter implements Filter {
    public class RequestFilter implements WebMvcConfigurer {
//    @Override
//    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
//
//        HttpServletResponse response = (HttpServletResponse) res;
//        HttpServletRequest request = (HttpServletRequest) req;
//        response.setHeader("Access-Control-Allow-Origin", "https://triger.admin-pro20.com");
////        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
////        response.setHeader("Access-Control-Allow-Origin", "http://cli.admin-vedatech.com");
//        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
//        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//
//        if (!(request.getMethod().equalsIgnoreCase("OPTIONS"))) {
//            try {
//                chain.doFilter(req, res);
//            } catch(Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            System.out.println("Pre-flight");
//            response.setHeader("Access-Control-Allow-Methods", "POST,PUT,GET,DELETE");
//            response.setHeader("Access-Control-Max-Age", "3600");
//            response.setHeader("Access-Control-Allow-Headers", "authorization, content-type," +
//                    "access-control-request-headers,access-control-request-method,accept,origin,authorization,x-requested-with");
//            response.setStatus(HttpServletResponse.SC_OK);
//        }
//
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {}
//
//
//    @Override
//    public void destroy() {
//
//    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:4200","http://localhost:4200","https://triger.admin-pro20.com","https://conta.admin-pro20.com");
    }


}



//package com.example.spa_case.util;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//
//@WebFilter(filterName = "UTF8Filter" , urlPatterns = "/*")
//public class UTF8Filter extends HttpFilter {
//    @Override
//    protected void doFilter(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain chain)
//            throws IOException, ServletException {
//        httpServletRequest.setCharacterEncoding("UTF-8");
//        httpServletResponse.setCharacterEncoding("UTF-8");
//        chain.doFilter(httpServletRequest, httpServletResponse);
//    }
//}
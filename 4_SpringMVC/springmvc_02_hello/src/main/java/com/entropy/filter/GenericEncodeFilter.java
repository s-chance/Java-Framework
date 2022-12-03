package com.entropy.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class GenericEncodeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 处理response的字符编码
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setContentType("text/html;charset=UTF-8");
        // 转型为与协议相关对象
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        // 对request包装增强
        HttpServletRequest myRequest = new MyRequest(request);
        filterChain.doFilter(myRequest, response);
    }

    // 自定义HttpServletRequest的包装类
    class MyRequest extends HttpServletRequestWrapper {

        private HttpServletRequest request;
        private boolean hasEncode; // 是否进行编码
        public MyRequest(HttpServletRequest request) {
            super(request);
            this.request = request;
        }

        // 增强原有的方法
        @Override
        public Map getParameterMap() {
            // 获取请求方式
            String method = request.getMethod();
            if (method.equalsIgnoreCase("post")) {
                // post请求乱码处理
                try {
                    request.setCharacterEncoding("utf-8");
                    return request.getParameterMap();
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            } else if (method.equalsIgnoreCase("get")) {
                // get请求乱码处理
                Map<String, String[]> parameterMap = request.getParameterMap();
                if (!hasEncode) { // 手动编码逻辑只需运行一次
                    for (String s : parameterMap.keySet()) {
                        String[] values = parameterMap.get(s);
                        if (values != null) {
                            for (int i = 0; i < values.length; i++) {
                                try {
                                    values[i] = new String(values[i].getBytes("ISO-8859-1"), "UTF-8");
                                } catch (UnsupportedEncodingException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }
                    hasEncode = true;
                }
                return parameterMap;
            }
            return super.getParameterMap();
        }

        // 只获取一个值
        @Override
        public String getParameter(String name) {
            Map<String, String[]> parameterMap = getParameterMap();
            String[] values = parameterMap.get(name);
            if (values == null) {
                return null;
            }
            return values[0]; // 返回第一个值即可
        }

        // 获取所有值
        @Override
        public String[] getParameterValues(String name) {
            Map<String, String[]> parameterMap = getParameterMap();
            String[] values = parameterMap.get(name);
            return values;
        }
    }

    @Override
    public void destroy() {

    }
}

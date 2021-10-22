package com.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter(
	urlPatterns = {"/member/*","/post/*"},
	dispatcherTypes = {DispatcherType.REQUEST}
)
public class LoginFilter implements Filter {

	Collection<String> urls;
    /**
     * Default constructor. 
     */
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}
	
	
	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub

		//Goal:All Page need login first
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		HttpSession session = req.getSession(true);
		String servletPath = req.getServletPath();
		String contextPath = req.getContextPath();
		
		System.out.println("login filter-"+session.getAttribute("memberId"));
		//do not login before, foward login page
		if(session.getAttribute("memberId") == null && !filterURLExclude(req.getServletPath())) {
			session.setAttribute("target", servletPath);
			System.out.println("login target:contextPath:"+contextPath+"--servletPath:"+ servletPath);
			//RequestDispatcher rd = request.getRequestDispatcher("/index");
			//rd.forward(req, res);
			
			res.sendRedirect(contextPath+"/index");
		}else {
			// pass the request along the filter chain
			
			chain.doFilter(request, response);
			System.out.println("login filter -already login");
		}
	}
	
	private boolean filterURLExclude(String url){
		List<String> excludeURLs = new ArrayList<String>();
		System.out.println("current url:---"+url);
		excludeURLs.add("/member/addMember");	
		excludeURLs.add("/member/insert");
		if(excludeURLs.contains(url)){
			System.out.println("exclude url true");
			return true;
		}
		return false;
	}
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		urls = new ArrayList<String>();
	//	Enumeration<String> initParams = fConfig.getInitParameterNames();
		 
		for(Enumeration<String> initParams = fConfig.getInitParameterNames(); initParams.hasMoreElements();) {
			urls.add(initParams.nextElement());
		}
	}
	
	
}

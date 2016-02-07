package com.softserveinc.booklibrary.session.util;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.booklibrary.session.persist.facade.impl.AuthorFacade;

/**
 * This is Servlet filter.This class gives open user transactoin untill user
 * page loading and close it, when page absolutly loaded. This needs for JPA
 * Lazy loading approach.
 *
 */
public class ConnectionFilter implements Filter {

	private static Logger log = LoggerFactory.getLogger(ConnectionFilter.class);

	@Resource
	private UserTransaction utx;

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			utx.begin();
			chain.doFilter(request, response);
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}

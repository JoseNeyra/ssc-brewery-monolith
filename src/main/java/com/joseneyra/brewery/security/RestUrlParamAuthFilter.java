package com.joseneyra.brewery.security;

import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;


// This is outdated and not necessary. It should be avoided. Use JpaUserDetailsService Instead
public class RestUrlParamAuthFilter extends AbstractRestAuthFilter{
    public RestUrlParamAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    protected String getPassword(HttpServletRequest request) {
        return request.getParameter("Api-Secret");
    }

    @Override
    protected String getUserName(HttpServletRequest request) {
       return request.getParameter("Api-Key");
    }


}

package com.example.zuuldemo.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import java.util.Optional;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;

public class ZuulPostFilter extends ZuulFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final OAuth2AuthorizedClientService clientService;

    public ZuulPostFilter(OAuth2AuthorizedClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        //return Ordered.HIGHEST_PRECEDENCE;
    	return 777;
    }

    @Override
    public boolean shouldFilter() {
/*    	RequestContext ctx = RequestContext.getCurrentContext();
        logger.debug("In OktaAuthenticationFilter() shouldFilter " );
    	if(ctx.getRequest().getHeader("authorization") != null) {
            logger.debug("In OktaAuthenticationFilter() shouldFilter: " + false );
    		return false;
    	};*/
    	return false;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        Optional<String> authorizationHeader = getAuthorizationHeader();
        authorizationHeader.ifPresent(s -> ctx.addZuulResponseHeader("Authorization", s));
        return null;
    }

    private Optional<String> getAuthorizationHeader() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	OAuth2AccessToken accessToken = null;
    	if(authentication instanceof OAuth2AuthenticationToken) {
	    	OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
	    	OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(
	    			oauthToken.getAuthorizedClientRegistrationId(),
	    			oauthToken.getName());
	    	accessToken = client.getAccessToken();
    	}else {
    		accessToken = null;
    	}

    	if (accessToken == null) {
    		return Optional.empty();
    	}else {
    		String tokenType = accessToken.getTokenType().getValue();
    		String authorizationHeaderValue = String.format("%s %s", tokenType, accessToken.getTokenValue());
    		return Optional.of(authorizationHeaderValue);
    	}
    }
}

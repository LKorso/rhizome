package com.rhizome.services.api;

import org.springframework.security.oauth2.provider.ClientDetails;

public interface OauthClientDetailsService {

    ClientDetails getClientDetails(String clientId);

}

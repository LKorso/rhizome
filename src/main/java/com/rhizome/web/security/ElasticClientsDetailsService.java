package com.rhizome.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import com.rhizome.services.implementation.ElasticOauthClientDetailsService;

@Service
public class ElasticClientsDetailsService implements ClientDetailsService {

    @Autowired
    private ElasticOauthClientDetailsService elasticOauthClientDetailsService;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return elasticOauthClientDetailsService.getClientDetails(clientId);
    }

}

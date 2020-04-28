package com.rhizome.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Service;

import com.rhizome.repositories.oauth.OauthClientDetailsRepository;
import com.rhizome.services.api.OauthClientDetailsService;

@Service
public class ElasticOauthClientDetailsService implements OauthClientDetailsService {

    @Autowired
    private OauthClientDetailsRepository oauthClientDetailsRepository;

    @Override
    public ClientDetails getClientDetails(String clientId) {
        return oauthClientDetailsRepository.findOne(clientId);
    }
}

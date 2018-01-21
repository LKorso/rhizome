package com.rhizome.domain.oauth;

import static java.util.Objects.isNull;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.rhizome.domain.converters.GrantedAuthorityConverter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Document(indexName = "oauth_client_details", type = "oauthClientDetails", shards = 1, replicas = 0)
public class OauthClientDetails implements ClientDetails {

    @Id
    private String clientId;

    private Set<String> resourceIds;

    private String clientSecret;

    private Set<String> scope;

    private Set<String> authorizedGrantTypes;

    private Set<String> registeredRedirectUri;

    @JsonDeserialize(converter = GrantedAuthorityConverter.class)
    private Collection<GrantedAuthority> authorities;

    private Integer accessTokenValiditySeconds;

    private Integer refreshTokenValiditySeconds;

    private boolean autoApprove;

    private Map<String, Object> additionalInformation;

    public OauthClientDetails() {
    }

    @Override
    public boolean isSecretRequired() {
        return !isBlank(clientSecret);
    }

    @Override
    public boolean isScoped() {
        return !isNull(scope) && !scope.isEmpty();
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return autoApprove;
    }

}

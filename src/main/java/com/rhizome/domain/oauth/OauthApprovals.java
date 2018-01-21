package com.rhizome.domain.oauth;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Document(indexName = "oauthApprovals", type = "oauthApprovals", shards = 1, replicas = 0)
public class OauthApprovals {

    @Id
    private String userId;

    private String clientId;

    private String scope;

    private String status;

    private Date expiresAt;

    private Date lastUpdatedAt;

    public OauthApprovals() {
    }
}

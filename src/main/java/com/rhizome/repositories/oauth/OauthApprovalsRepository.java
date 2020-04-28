package com.rhizome.repositories.oauth;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.stereotype.Repository;

import com.rhizome.domain.oauth.OauthApprovals;

@Repository
public interface OauthApprovalsRepository extends ElasticsearchCrudRepository<OauthApprovals, String> {

    List<OauthApprovals> findByUserIdAndClientId(String userId, String clientId);

}

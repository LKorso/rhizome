package com.rhizome.repositories.oauth;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.stereotype.Repository;

import com.rhizome.domain.oauth.OauthClientDetails;

@Repository
public interface OauthClientDetailsRepository extends ElasticsearchCrudRepository<OauthClientDetails, String> {
}

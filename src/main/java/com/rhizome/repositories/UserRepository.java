package com.rhizome.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.stereotype.Repository;

import com.rhizome.domain.User;

@Repository
public interface UserRepository extends ElasticsearchCrudRepository<User, Integer> {

    User findUserByEmail(String email);

    default boolean existsByEmail(String email) {
        return findUserByEmail(email) != null;
    }

}

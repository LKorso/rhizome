package com.program.repositories;

import com.program.domain.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface UserRepository extends ElasticsearchCrudRepository<User, String> {
}

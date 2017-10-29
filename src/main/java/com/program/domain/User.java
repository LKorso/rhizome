package com.program.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@AllArgsConstructor
@Document(indexName = "user", type = "user", shards = 1, replicas = 0)
public class User {

    @Id
    private String id;

    private String firtsName;

    private String lastName;

    private String email;

    public User() {
    }
}

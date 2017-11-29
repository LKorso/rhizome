package com.rhizome.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@Document(indexName = "user", type = "user", shards = 1, replicas = 0)
public class User {

    @Id
    private String email;

    private String firtsName;

    private String lastName;

    private String password;

    private List<String> roles = Collections.singletonList("USER");

    public User() {
    }

    public User(String email, String firtsName, String lastName, String password) {
        this.email = email;
        this.firtsName = firtsName;
        this.lastName = lastName;
        this.password = password;
    }
}

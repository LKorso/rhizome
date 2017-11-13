package com.program.web.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class Credentials {

    private String email;

    private String password;

    private List<String> roles;
}

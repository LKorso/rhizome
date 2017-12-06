package com.rhizome.web.dto;

import com.rhizome.web.validation.annotations.EmailValidation;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Accessors(chain = true)
public class Credentials {

    @EmailValidation
    private String email;

    @NotNull
    @NotEmpty
    private String password;

    private List<String> roles;
}

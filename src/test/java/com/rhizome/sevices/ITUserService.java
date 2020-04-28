package com.rhizome.sevices;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rhizome.configuration.EmbeddedElasticsearchConfiguration;
import com.rhizome.domain.User;
import com.rhizome.services.implementation.UserService;

@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EmbeddedElasticsearchConfiguration.class)
public class ITUserService {

    @Autowired
    private UserService testInstance;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @BeforeEach
    void before() {
        elasticsearchTemplate.deleteIndex(User.class);
        elasticsearchTemplate.createIndex(User.class);
        elasticsearchTemplate.putMapping(User.class);
        elasticsearchTemplate.refresh(User.class);
    }

    @Test
    void newUserSaved() {
        // given
        User newUser = createUser();

        // when
        User actual = testInstance.save(newUser);

        // then
        assertAll(
                () -> assertEquals(actual.getFirstName(), newUser.getFirstName()),
                () -> assertEquals(actual.getLastName(), newUser.getLastName()),
                () -> assertEquals(actual.getEmail(), newUser.getEmail()));
    }

    @Test
    void userFoundByEmail() {
        // given
        String userEmail = "user@email.com";
        User user = createUser();
        user.setEmail(userEmail);
        testInstance.save(user);

        // when
        User actual = testInstance.findByEmail(userEmail);

        // then
        assertAll(
                () -> assertEquals(actual.getFirstName(), user.getFirstName()),
                () -> assertEquals(actual.getLastName(), user.getLastName()));
    }

    @Test
    void userDeleted() {
        // given
        User user = createUser();
        String userId = user.getId();

        // when
        testInstance.delete(user);

        // then
        assertFalse(testInstance.find(userId).isPresent());
    }

    @Test
    void allUsersWereFound() {
        // given
        User userOne = createUser();
        userOne.setId("11");
        User userTwo = createUser();
        userOne.setId("12");
        testInstance.save(userOne);
        testInstance.save(userTwo);

        // when
        List<User> users = testInstance.findAll();

        // then
        assertThat(users.size(), is(2));
    }

    private User createUser() {
        return new User("10", "mail@gamil.com", "FirstName", "LastName", "password");
    }

}

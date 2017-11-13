package com.program.services;

import com.program.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ITUserService {

    @Autowired
    private UserService testInstance;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Before
    public void before() {
        elasticsearchTemplate.deleteIndex(User.class);
        elasticsearchTemplate.createIndex(User.class);
        elasticsearchTemplate.putMapping(User.class);
        elasticsearchTemplate.refresh(User.class);
    }

    @Test
    public void newUserSaved() {
        // given
        User newUser = createUser();

        // when
        User actual = testInstance.save(newUser);

        // then
        assertEquals(actual.getFirtsName(), newUser.getFirtsName());
        assertEquals(actual.getLastName(), newUser.getLastName());
        assertEquals(actual.getEmail(), newUser.getEmail());
    }

    @Test
    public void userFound() {
        // given
        String userEmail = "user@email.com";
        User user = createUser();
        user.setEmail(userEmail);
        User savedUser = testInstance.save(user);

        // when
        User actual = testInstance.find(userEmail);

        // then
        assertEquals(actual.getFirtsName(), user.getFirtsName());
        assertEquals(actual.getLastName(), user.getLastName());
        assertEquals(actual.getEmail(), user.getEmail());

    }

    @Test
    public void userDeleted() {
        // given
        String userId = "USERID";
        User user = createUser();
        User savedUser = testInstance.save(user);

        // when
        testInstance.delete(user);

        // then
        assertNull(testInstance.find(userId));
    }

    @Test
    public void allUserWereFound() {
        // given
        User userOne = createUser();
        userOne.setEmail("emailOne");
        User userTwo = createUser();
        userTwo.setEmail("emailTwo");
        testInstance.save(userOne);
        testInstance.save(userTwo);

        // when
        List<User> users = testInstance.findAll();

        // then
        assertThat(users.size(), is(2));
    }

    private User createUser() {
        User user = new User("mail@gamil.com", "FirstName", "LastName", "password");
        return user;
    }

}

package com.rhizome.web.resource_processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.rhizome.domain.User;

@Component
public class UserResourceProcessor implements ResourceProcessor<Resource<User>> {

    @Autowired
    private RepositoryEntityLinks links;

    @Override
    public Resource<User> process(Resource<User> resource) {
        User user = resource.getContent();
        if (isCurrentUser(user)) {
            resource.add(links.linkToSingleResource(User.class, user.getId()).withRel("update"));
        }
        return resource;
    }

    private boolean isCurrentUser(User user) {
        String loggedUserEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getEmail().equals(loggedUserEmail);
    }

}

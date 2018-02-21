package com.rhizome.repositories;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.update.UpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rhizome.domain.User;

@Repository
public class UserUpdateRepository {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;
    @Autowired
    private ObjectMapper jsonMapper;

    public void update(User user) {
        elasticsearchOperations.update(prepareUpdateQuery(user));
    }

    private UpdateQuery prepareUpdateQuery(User user) {
        UpdateQuery updateQuery = new UpdateQuery();
        updateQuery.setId(user.getEmail());
        updateQuery.setClazz(user.getClass());
        updateQuery.setUpdateRequest(prepareUpdateRequest(user));
        return updateQuery;
    }

    private UpdateRequest prepareUpdateRequest(User user) {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("user");
        updateRequest.type("user");
        updateRequest.id(user.getEmail());
        updateRequest.doc(prepareRequestBody(user));
        return updateRequest;
    }

    private byte[] prepareRequestBody(User user) {
        try {
            return jsonMapper.writeValueAsBytes(extractOnlyUpdatedFields(user));
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    private Map<String, Object> extractOnlyUpdatedFields(User user) {
        Map<String, Object> result = new HashMap<>();
        for (Field field : Arrays.asList(User.class.getDeclaredFields())) {
            if (!field.isAnnotationPresent(Id.class)) {
                Object value = getFieldValue(user, field);
                if (value != null) {
                    result.put(field.getName(), value);
                }
            }
        }
        return result;
    }

    private Object getFieldValue(User instance, Field field) {
        field.setAccessible(true);
        try {
            return field.get(instance);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Field " + field.getName() + " is not declared in class " + User.class);
        }
    }

}
package com.api.domain.users.repository;

import com.api.domain.users.entity.Email;
import org.springframework.data.repository.CrudRepository;

public interface UserRedisRepository extends CrudRepository<Email, String> {
}

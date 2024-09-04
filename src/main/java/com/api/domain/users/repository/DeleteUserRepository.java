package com.api.domain.users.repository;

import com.api.domain.users.entity.DeleteUser;
import org.springframework.data.repository.CrudRepository;

public interface DeleteUserRepository extends CrudRepository<DeleteUser, String> {
}

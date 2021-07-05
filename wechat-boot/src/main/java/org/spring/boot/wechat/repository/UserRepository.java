package org.spring.boot.wechat.repository;

import org.spring.boot.wechat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}

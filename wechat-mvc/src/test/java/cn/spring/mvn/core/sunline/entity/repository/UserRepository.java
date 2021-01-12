package cn.spring.mvn.core.sunline.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.spring.mvn.core.sunline.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

}

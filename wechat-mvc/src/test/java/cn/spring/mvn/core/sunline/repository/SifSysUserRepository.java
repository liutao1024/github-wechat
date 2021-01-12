package cn.spring.mvn.core.sunline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.spring.mvn.core.sunline.domain.SifSysUser;
import cn.spring.mvn.core.sunline.domain.SifSysUserPk;

public interface SifSysUserRepository extends JpaRepository<SifSysUser, SifSysUserPk>,JpaSpecificationExecutor<SifSysUser> {
//	JpaRepository<SifSysUser, SifSysUserPk>,JpaSpecificationExecutor<SifSysUser>
}

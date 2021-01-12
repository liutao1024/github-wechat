package cn.spring.mvn.core.sunline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.spring.mvn.core.sunline.domain.SifSysRole;
import cn.spring.mvn.core.sunline.domain.SifSysRolePk;

public interface SifSysRoleRepository extends
		JpaRepository<SifSysRole, SifSysRolePk>, JpaSpecificationExecutor<SifSysRole> {
	
}

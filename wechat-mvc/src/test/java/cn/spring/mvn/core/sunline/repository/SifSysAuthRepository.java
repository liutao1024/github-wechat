package cn.spring.mvn.core.sunline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.spring.mvn.core.sunline.domain.SifSysAuth;
import cn.spring.mvn.core.sunline.domain.SifSysAuthPk;

public interface SifSysAuthRepository extends JpaRepository<SifSysAuth, SifSysAuthPk>, JpaSpecificationExecutor<SifSysAuth> {

}

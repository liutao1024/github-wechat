package cn.spring.mvn.core.sunline.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import cn.spring.mvn.core.sunline.dao.SifSysTeamDao;
import cn.spring.mvn.core.sunline.domain.SifSysTeam;
import cn.spring.mvn.core.sunline.domain.SifSysTeamPk;
import cn.spring.mvn.core.sunline.exception.SumpException;
import cn.spring.mvn.core.sunline.service.SifSysTeamService;

@Service("SifSysTeamService")
public class SifSysTeamServiceImpl implements SifSysTeamService{
	
	@Autowired
	private SifSysTeamDao sifSysTeamDao;

//	@Override
//	public SifSysTeam expandEntity(SifSysTeam entity) {
//		SifSysTeamPk id = new SifSysTeamPk(entity.getRegisterCd(), entity.getTeamCd());
//		return sifSysTeamDao.findOne(id);
//	}
	@Override
	public boolean checkUnique(SifSysTeam entity) {
		SifSysTeamPk id = new SifSysTeamPk(entity.getRegisterCd(), entity.getTeamCd());
		return !sifSysTeamDao.exists(id);
	}
	@Override
	public SifSysTeam saveEntity(SifSysTeam entity) throws SumpException {
		if(!checkUnique(entity)){
			throw new SumpException("1001");
		}
		return sifSysTeamDao.save(entity);
	}
	@Override
	public List<SifSysTeam> saveEntities(List<SifSysTeam> entities) throws SumpException {
		for(SifSysTeam entity:entities){
			if(!checkUnique(entity)){
				throw new SumpException("1001");
			}
		}
		return sifSysTeamDao.save(entities);
	}
	@Override
	public void deleteEntity(SifSysTeam entity) {
		sifSysTeamDao.delete(entity);
	}
	@Override
	public void deleteEntities(List<SifSysTeam> entities) {
		sifSysTeamDao.delete(entities);
	}
	@Override
	public void deleteAllInBatch() {
		sifSysTeamDao.deleteAllInBatch();
	}
	@Override
	public Page<SifSysTeam> queryAll(Pageable pageable) {
		return sifSysTeamDao.findAll(pageable);
	}
	@Override
	public List<SifSysTeam> queryByTemplate(SifSysTeam tmp) {
		return sifSysTeamDao.queryByTemplate(tmp);
	}
	@Override
	public List<SifSysTeam> queryByTemplateWithSort(SifSysTeam tmp, Sort sort) {
		return sifSysTeamDao.queryByTemplate(tmp, sort);
	}
	@Override
	public Page<SifSysTeam> queryByTemplateWithPage(SifSysTeam tmp,
			Pageable pageable) {
		return sifSysTeamDao.queryByTemplate(tmp, pageable);
	}

}

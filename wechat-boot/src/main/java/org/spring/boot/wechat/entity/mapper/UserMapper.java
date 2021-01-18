package org.spring.boot.wechat.entity.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.spring.boot.wechat.entity.User;

@Mapper
public interface UserMapper {
	@Insert("")
	public boolean insertOne();
	@Delete("")
	public boolean deleteOneByPK();
	@Select("select cron from cron limit 1")
	public User selectOneByPK();
	@Update("")
	public boolean updateByPK();
}

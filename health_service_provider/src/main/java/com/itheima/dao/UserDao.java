package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.SysUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserDao {

	/**
	 * 基于名字，获取用户信息
	 * @param username
	 * @return
	 */
	SysUser findByUsername(String username);

	Page<SysUser> findPage();

	void add(SysUser sysUser);
	@Insert("INSERT INTO `itcast_health`.`t_user_role` \n" +
			"\t(`user_id`, \n" +
			"\t`role_id`\n" +
			"\t)\n" +
			"\tVALUES\n" +
			"\t(#{sysUserId}, \n" +
			"\t#{roleId}\n" +
			"\t);\n")
	void setRelations(@Param("sysUserId") Integer sysUserId, @Param("roleId") Integer roleId);
	@Select("select role_id from t_user_role where user_id = #{id}")
	List<Integer> findRoleIdsById(Integer id);

	SysUser findById(Integer id);
	@Update("UPDATE `itcast_health`.`t_user` \n" +
			"\tSET\n" +
			"\t`birthday` = #{birthday} , \n" +
			"\t`gender` = #{gender} , \n" +
			"\t`username` = #{username} , \n" +
			"\t`password` = #{password} , \n" +
			"\t`remark` = #{remark} , \n" +
			"\t`station` = #{station} , \n" +
			"\t`telephone` =#{telephone}\n" +
			"\t\n" +
			"\tWHERE\n" +
			"\t`id` = #{id} ;")
	void edit(SysUser sysUser);
	@Delete("DELETE FROM `itcast_health`.`t_user_role` \n" +
			"\tWHERE\n" +
			"\t`user_id` = #{user_id} ;\n")
	void delRelation(Integer id);
	@Delete("DELETE FROM `itcast_health`.`t_user` \n" +
			"\tWHERE\n" +
			"\t`id` = #{id} ;")
	void delById(Integer id);
}
package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
public interface RoleDao {

    Set<Role> findRolesByUserId(Integer userId);

    @Select("select * from t_role")
    List<Role> findRolesAll();

    Page<Role> findPage(String queryString);
    void add(Role role);
    @Insert("INSERT INTO `itcast_health`.`t_role_permission` \n" +
            "\t(`role_id`, \n" +
            "\t`permission_id`\n" +
            "\t)\n" +
            "\tVALUES\n" +
            "\t(#{roleId}, \n" +
            "\t#{permissionId}\n" +
            "\t);")
    void setPermissionRelations(@Param("roleId") Integer roleId, @Param("permissionId")  Integer permissionId);
    @Insert("INSERT INTO `itcast_health`.`t_role_menu` \n" +
            "\t(`role_id`, \n" +
            "\t`menu_id`\n" +
            "\t)\n" +
            "\tVALUES\n" +
            "\t(#{roleId}, \n" +
            "\t#{menuId}\n" +
            "\t);\n")
    void setMenuRelations(@Param("roleId") Integer roleId,@Param("menuId") Integer menuId);

    @Select("select * from t_role where id = #{id}")
    Role findById(Integer id);
    @Select("SELECT \t\n" +
            "\t`permission_id`\n" +
            "\t \n" +
            "\tFROM \n" +
            "\t`itcast_health`.`t_role_permission` WHERE role_id =#{id} ")
    List<Integer> findPermissionIdsById(Integer roleId);
    @Select("SELECT `menu_id`\n" +
            "\t \n" +
            "\tFROM \n" +
            "\t`itcast_health`.`t_role_menu` WHERE role_id = #{id}")
    List<Integer> findMenuIdsById(Integer id);
    @Update("UPDATE `itcast_health`.`t_role` \n" +
            "\tSET\n" +
            "\t`name` = #{name} , \n" +
            "\t`keyword` = #{keyword} , \n" +
            "\t`description` = #{description}\n" +
            "\t\n" +
            "\tWHERE\n" +
            "\t`id` = #{id} ;")
    void edit(Role role);
    @Delete("DELETE FROM `itcast_health`.`t_role_permission` \n" +
            "\tWHERE\n" +
            "\t`role_id` = #{id} ;\n")
    void delPermissionRelation(Integer id);
    @Delete("DELETE FROM `itcast_health`.`t_role_menu` \n" +
            "\tWHERE\n" +
            "\t`role_id` = #{id};")
    void delMenuRelation(Integer id);
    @Select("SELECT COUNT(0) FROM t_user_role WHERE role_id =#{id}")
    int findUserById(Integer roleId);
    @Delete("DELETE FROM `itcast_health`.`t_role` \n" +
            "\tWHERE\n" +
            "\t`id` = #{id} ;")
    void delById(Integer roleId);
}

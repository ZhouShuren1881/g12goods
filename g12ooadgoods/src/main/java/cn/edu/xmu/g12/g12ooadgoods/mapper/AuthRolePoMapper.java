package cn.edu.xmu.g12.g12ooadgoods.mapper;

import cn.edu.xmu.g12.g12ooadgoods.model.po.AuthRolePo;
import cn.edu.xmu.g12.g12ooadgoods.model.po.AuthRolePoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AuthRolePoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_role
     *
     * @mbg.generated
     */
    int deleteByExample(AuthRolePoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_role
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_role
     *
     * @mbg.generated
     */
    int insert(AuthRolePo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_role
     *
     * @mbg.generated
     */
    int insertSelective(AuthRolePo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_role
     *
     * @mbg.generated
     */
    List<AuthRolePo> selectByExample(AuthRolePoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_role
     *
     * @mbg.generated
     */
    AuthRolePo selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_role
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") AuthRolePo record, @Param("example") AuthRolePoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_role
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") AuthRolePo record, @Param("example") AuthRolePoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_role
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(AuthRolePo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_role
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(AuthRolePo record);
}
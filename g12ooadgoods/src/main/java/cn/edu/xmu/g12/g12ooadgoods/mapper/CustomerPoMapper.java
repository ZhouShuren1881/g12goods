package cn.edu.xmu.g12.g12ooadgoods.mapper;

import cn.edu.xmu.g12.g12ooadgoods.model.po.CustomerPo;
import cn.edu.xmu.g12.g12ooadgoods.model.po.CustomerPoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CustomerPoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer
     *
     * @mbg.generated
     */
    int deleteByExample(CustomerPoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer
     *
     * @mbg.generated
     */
    int insert(CustomerPo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer
     *
     * @mbg.generated
     */
    int insertSelective(CustomerPo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer
     *
     * @mbg.generated
     */
    List<CustomerPo> selectByExample(CustomerPoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer
     *
     * @mbg.generated
     */
    CustomerPo selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") CustomerPo record, @Param("example") CustomerPoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") CustomerPo record, @Param("example") CustomerPoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(CustomerPo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(CustomerPo record);
}
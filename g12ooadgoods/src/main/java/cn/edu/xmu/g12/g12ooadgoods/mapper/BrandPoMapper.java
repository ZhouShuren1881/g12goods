package cn.edu.xmu.g12.g12ooadgoods.mapper;

import cn.edu.xmu.g12.g12ooadgoods.model.po.BrandPo;
import cn.edu.xmu.g12.g12ooadgoods.model.po.BrandPoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BrandPoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table brand
     *
     * @mbg.generated
     */
    int deleteByExample(BrandPoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table brand
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table brand
     *
     * @mbg.generated
     */
    int insert(BrandPo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table brand
     *
     * @mbg.generated
     */
    int insertSelective(BrandPo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table brand
     *
     * @mbg.generated
     */
    List<BrandPo> selectByExample(BrandPoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table brand
     *
     * @mbg.generated
     */
    BrandPo selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table brand
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") BrandPo record, @Param("example") BrandPoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table brand
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") BrandPo record, @Param("example") BrandPoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table brand
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(BrandPo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table brand
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(BrandPo record);
}
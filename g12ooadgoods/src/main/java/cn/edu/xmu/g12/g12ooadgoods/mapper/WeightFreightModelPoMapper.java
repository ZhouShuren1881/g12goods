package cn.edu.xmu.g12.g12ooadgoods.mapper;

import cn.edu.xmu.g12.g12ooadgoods.model.po.WeightFreightModelPo;
import cn.edu.xmu.g12.g12ooadgoods.model.po.WeightFreightModelPoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WeightFreightModelPoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weight_freight_model
     *
     * @mbg.generated
     */
    int deleteByExample(WeightFreightModelPoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weight_freight_model
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weight_freight_model
     *
     * @mbg.generated
     */
    int insert(WeightFreightModelPo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weight_freight_model
     *
     * @mbg.generated
     */
    int insertSelective(WeightFreightModelPo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weight_freight_model
     *
     * @mbg.generated
     */
    List<WeightFreightModelPo> selectByExample(WeightFreightModelPoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weight_freight_model
     *
     * @mbg.generated
     */
    WeightFreightModelPo selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weight_freight_model
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") WeightFreightModelPo record, @Param("example") WeightFreightModelPoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weight_freight_model
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") WeightFreightModelPo record, @Param("example") WeightFreightModelPoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weight_freight_model
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(WeightFreightModelPo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table weight_freight_model
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(WeightFreightModelPo record);
}
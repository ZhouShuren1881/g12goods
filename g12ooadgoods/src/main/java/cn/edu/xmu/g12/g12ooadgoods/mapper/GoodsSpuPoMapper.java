package cn.edu.xmu.g12.g12ooadgoods.mapper;

import cn.edu.xmu.g12.g12ooadgoods.model.po.GoodsSpuPo;

public interface GoodsSpuPoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_spu
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_spu
     *
     * @mbg.generated
     */
    int insert(GoodsSpuPo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_spu
     *
     * @mbg.generated
     */
    int insertSelective(GoodsSpuPo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_spu
     *
     * @mbg.generated
     */
    GoodsSpuPo selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_spu
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(GoodsSpuPo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_spu
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(GoodsSpuPo record);
}
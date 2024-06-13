package com.liteMallReplicate.litemalldb.dao;

import org.apache.ibatis.annotations.Param;
import java.util.List;
import com.liteMallReplicate.litemalldb.domain.LitemallCoupon;
import com.liteMallReplicate.litemalldb.domain.LitemallCouponExample;

public interface LitemallCouponMapper {
    long countByExample(LitemallCouponExample example);

    int deleteByExample(LitemallCouponExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LitemallCoupon record);

    LitemallCoupon selectOneExample(LitemallCouponExample exmaple);

    LitemallCoupon selectOneByExampleSelective(@Param("example") LitemallCouponExample example, @Param("selective") LitemallCoupon.Column ... selective);

    List<LitemallCoupon> selectByExampleSelective(@Param("example") LitemallCouponExample example, @Param("selective") LitemallCoupon.Column ... selective);

    List<LitemallCoupon> selectByExample(LitemallCouponExample example);

    LitemallCoupon selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") LitemallCoupon.Column ... selective);

    LitemallCoupon selectByPrimaryKey(Integer id);

    LitemallCoupon selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDelete") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") LitemallCoupon record, @Param("example") LitemallCouponExample example);

    int updateByPrimaryKeySelective(LitemallCoupon record);

    int logicalDeleteByExample(@Param("example") LitemallCouponExample example);

    int logicalDeleteByPrimaryKey(Integer id);

}

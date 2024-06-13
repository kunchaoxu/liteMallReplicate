package com.liteMallReplicate.litemalldb.service;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import com.liteMallReplicate.litemalldb.dao.LitemallCouponUserMapper;
import com.liteMallReplicate.litemalldb.dao.LitemallCouponMapper;
import com.liteMallReplicate.litemalldb.domain.LitemallCoupon.Column;
import com.liteMallReplicate.litemalldb.domain.*;
import com.liteMallReplicate.litemalldb.Util.CouponConstant;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class LitemallCouponService {
    @Resource
    private LitemallCouponMapper couponMapper;
    @Resource
    private LitemallCouponUserMapper couponUserMapper;

    private Column[] result = new Column[] {Column.id, Column.name, Column.desc, Column.tag,
    Column.days, Column.startTime, Column.endTime, Column.discount, Column.min};

    public List<LitemallCoupon> queryList(int offset, int limit, String sort, String order) {
        return queryList(LitemallCouponExample.newAndCreateCriteria(), offset, limit, sort, order);
    }

    public List<LitemallCoupon> queryList(LitemallCouponExample.Criteria criteria, int offset, int limit, String sort, String order) {
        criteria.andTypeEqualTo(CouponConstant.TYPE_COMMON).addStatusEqualTo(CouponConstant.STATUS_NORMAL).andDeletedEqualTo(false);
        criteria.example().setOrderByClause(sort + " " + order);
        return couponMapper.selectByExampleSelective(criteria.example(), result);
    }

    public List<LitemallCoupon> queryAvailableList(Integer userId, int offset, int limit) {
        assert userId != null;
        LitemallCouponExample.Criteria c = LitemallCouponExample.newAndCreateCriteria();
        List<LitemallCouponUser> used = couponUserMapper.selectByExample(
                LitemallCouponUserExample.newAndCreateCriteria().addUserIdEqualTo(userId).example()
        );
        if (used != null && !used.isEmpty()) {
            c.addIdNotIn(used.stream().map(LitemallCouponUser::getCouponId).collect(Collectors.toList()))
        }
        return queryList(c, offset, limit, "add_time", "desc");
    }

    public List<LitemallCoupon> queryList(int offset, int limit) {
        return queryList(offset, limit, "add_time", "desc");
    }

    public LitemallCoupon findById(int id) {return couponMapper.selectByPrimaryKey(id); }

    public LitemallCoupon findByCode(String code) {
        LitemallCouponExample example = new LitemallCouponExample();
        example.or().addCodeEqualTo(code).andTypeEqualTo(CouponConstant.TYPE_CODE).andStatusEqualTo(CouponConstant.STATUS_NORMAL);
        List<LitemallCoupon> couponList = couponMapper.selectByExample(example);
        if (couponList.size() > 1) {
            throw new RuntimeException("");
        } else if (couponList.size() == 0) {
            return null;
        } else {
            return couponList.get(0);
        }
    }

    public List<LitemallCoupon> queryRegister() {
        LitemallCouponExample example = new LitemallCouponExample();
        example.or().andTypeEqualTo(CouponConstant.TYPE_REGISTER).andStatusEqualTo(CouponConstant.STATUS_NORMAL);
        return couponMapper.selectByExample(example);
    }

    public List<LitemallCoupon> querySelective(String name, Short type, Short status, Integer page, Integer limit, String sort, String order) {
        LitemallCouponExample example = new LitemallCouponExample();
        LitemallCouponExample.Criteria criteria = example.createCriteria();

        return couponMapper.selectByExample(example);
    }

    public void add(LitemallCoupon coupon) {
        coupon.setAddTime(LocalDateTime.now());
        coupon.setUpdateTime(LocalDateTime.now());
        couponMapper.updateByPrimaryKeySelective(coupon);
    }

    public int updateById(LitemallCoupon coupon) {
        coupon.setUpdateTime(LocalDateTime.now());
        return couponMapper.updateByPrimaryKeySelective(coupon);
    }

    public void deleteById(Integer id) {
        couponMapper.logicalDeleteByPrimaryKey(id);
    }

    private String getRandomNum(Integer num) {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        base += "0123456789";

        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < num; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public String generateCode() {
        String code = getRandomNum(8);
        while(findByCode(code) != null) {
            code = getRandomNum(8);
        }
        return code;
    }

    public List<LitemallCoupon> queryExpired() {
        LitemallCouponExample example = new LitemallCouponExample();
        example.or().andStatusEqualTo(CouponConstant.STATUS_NORMAL);
        return couponMapper.selectByExample(example);
    }
}

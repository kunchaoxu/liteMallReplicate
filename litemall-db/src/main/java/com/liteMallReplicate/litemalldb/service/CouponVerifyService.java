package com.liteMallReplicate.litemalldb.service;

import com.liteMallReplicate.litemalldb.Util.CouponConstant;
import com.liteMallReplicate.litemalldb.domain.LitemallCoupon;
import com.liteMallReplicate.litemalldb.domain.LitemallCouponUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class CouponVerifyService {
    @Autowired
    private LitemallCouponUserService couponUserService;
    @Autowired
    private LitemallCouponService couponService;
    @Autowired
    private LitemallGoodsService goodsService;

    public LitemallCoupon checkCoupon(Integer userId, Integer couponId, Integer userCouponId, BigDecimal checkedGoodsPrice, List<LitemallCart> cartList) {
        LitemallCoupon coupon = couponService.findById(couponId);
        if (coupon == null || coupon.getDeleted()) {
            return null;
        }

        LitemallCouponUser couponUser = couponUserService.findById(userCouponId);
        if (couponUser == null) {
            couponUser = couponUserService.queryOne(userId, couponId);
        } else if (!couponId.equals(couponUser.getCouponId())) {
            return null;
        }

        if (couponUser == null) {
            return null;
        }

        Short timeType = coupon.getTimeType();
        Short days = coupon.getDays();
        LocalDateTime now = LocalDateTime.now();
        if (timeType.equals(CouponConstant.TIME_TYPE_TIME)) {
            if (now.isBefore(coupon.getStartTime()) || now.isAfter(coupon.getEndTime())) {
                return null;
            }
        } else if(timeType.equals(CouponConstant.TIME_TYPE_DAYS)) {
            LocalDateTime expired = couponUser.getAddTime().plusDays(days);
            if (now.isAfter(expired)) {
                return null;
            }
        } else {
            return null;
        }

        Map<Integer, List<LitemallCart>> cartMap = new HashMap<>();
        List<Integer> goodsValueList = new ArrayList<>(Arrays.asList(coupon.getGoodsValue()));
        Short goodType = coupon.getGoodsType();

        if (goodType.equals(CouponConstant.GOODS_TYPE_CATEGORY) ||
        goodType.equals(CouponConstant.GOODS_TYPE_ARRAY)) {
            for (LitemallCart cart : cartList) {
                Integer key = goodType.equals(CouponConstant.GOODS_TYPE_ARRAY) ? cart.getGoodsId():
                        goodsService.findById(cart.getGoodsId()).getCategoryId();
                List<LitemallCart> carts = cartMap.get(key);
                if (carts == null) {
                    carts = new LinkedList<>();
                }
                carts.add(cart);
                cartMap.put(key, carts);
            }

            goodsValueList.retainAll(cartMap.keySet());
            BigDecimal total = new BigDecimal(0);

            for (Integer goodsId: goodsValueList) {
                List<LitemallCart> carts = cartMap.get(goodsId);
                for (LitemallCart cart: carts) {
                    total = total.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));

                }
            }

            if (total.compareTo(coupon.getMin()) == -1) {
                return null;
            }
        }

        Short status = coupon.getStatus();
        if (!status.equals(CouponConstant.STATUS_NORMAL)) {
            return null;
        }

        if (checkedGoodsPrice.compareTo(coupon.getMin()) == -1) {
            return null;
        }
        return coupon;
    }
}

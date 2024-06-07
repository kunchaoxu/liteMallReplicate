package com.liteMallReplicate.litemalldb.service;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class LitemallCouponService {
    @Resource
    private LitemallCouponMapper couponMapper;
    @Resource
    private LitemallCouponUserMapper couponUserMapper;
}

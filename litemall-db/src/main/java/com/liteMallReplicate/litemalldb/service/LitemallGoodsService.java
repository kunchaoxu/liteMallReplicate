package com.liteMallReplicate.litemalldb.service;

import com.liteMallReplicate.litemalldb.domain.LitemallGoods.Column;
import org.springframework.stereotype.Service;

@Service
public class LitemallGoodsService {
    Column[] columns = new Column[]{Column.id, Column.name, Column.brief, Column.picUrl, Column.isHot,
            Column.isNew, Column.counterPrice, Column.retailPrice};

}

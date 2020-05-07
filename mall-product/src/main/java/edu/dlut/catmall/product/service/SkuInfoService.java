package edu.dlut.catmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.dlut.common.utils.PageUtils;
import edu.dlut.catmall.product.entity.SkuInfoEntity;

import java.util.Map;

/**
 * sku信息
 *
 * @author raymond
 * @email qq740567396@gmail.com
 * @date 2020-04-29 23:53:30
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSkuInfo(SkuInfoEntity skuInfoEntity);

    PageUtils queryPageByCondition(Map<String, Object> params);
}


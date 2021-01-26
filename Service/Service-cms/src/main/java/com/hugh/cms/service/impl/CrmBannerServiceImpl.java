package com.hugh.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hugh.cms.entity.CrmBanner;
import com.hugh.cms.mapper.CrmBannerMapper;
import com.hugh.cms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author hugh
 * @since 2021-01-15
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    @Cacheable(value = "banner", key = "'selectBannerList'")
    public List<CrmBanner> selectAllBanner() {
        // 以ID为key进行降序排列，查询前两条记录
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.orderByDesc("id");
        // SQL语句拼接：last方法会将参数拼接在SQL语句后面
        // 只能调用一次，多次调用以最后一次为准，有SQL注入的风险
        wrapper.last("limit 2");
        return baseMapper.selectList(wrapper);
    }

}

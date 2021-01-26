package com.hugh.cms.mapper;

import com.hugh.cms.entity.CrmBanner;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 首页banner表 Mapper 接口
 * </p>
 *
 * @author hugh
 * @since 2021-01-15
 */
@Repository
@Mapper
public interface CrmBannerMapper extends BaseMapper<CrmBanner> {

}

package com.ultra.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ultra.dao.SaMapper;
import com.ultra.dao.entity.Sa;
import com.ultra.dao.vo.SaVO;
import com.ultra.service.SaService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @since 2018-12-19
 */
@Service
public class SaServiceImpl extends ServiceImpl<SaMapper, Sa> implements SaService {

    @Override
    public List<SaVO> selectSaVOList() {
        return baseMapper.selectSaVOList();
    }

}

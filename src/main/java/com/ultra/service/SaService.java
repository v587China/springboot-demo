package com.ultra.service;

import com.ultra.dao.entity.Sa;
import com.ultra.dao.vo.SaVO;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @since 2018-12-19
 */
public interface SaService extends IService<Sa> {
    /**
     * 
     * @Title selectSaVOList
     * @Description 查询所有监控区(id,name)
     * @return List<SaVO>
     */
    List<SaVO> selectSaVOList();
}

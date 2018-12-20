package com.ultra.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ultra.dao.entity.Sa;
import com.ultra.dao.vo.SaVO;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @since 2018-12-19
 */
public interface SaMapper extends BaseMapper<Sa> {

    /**
     * 
     * @Title selectSaVOList
     * @Description 查询所有监控区(id,name)
     * @return List<SaVO>
     */
    List<SaVO> selectSaVOList();
}

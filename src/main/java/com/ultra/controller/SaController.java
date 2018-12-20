package com.ultra.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ultra.common.Result;
import com.ultra.controller.dto.SaDTO;
import com.ultra.dao.entity.Sa;
import com.ultra.dao.vo.SaVO;
import com.ultra.service.SaService;
import com.ultra.util.CommonUtil;
import com.ultra.util.GisMapUtil;
import com.ultra.util.ResponseEntityGenerator;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @since 2018-12-19
 */
@RestController
@RequestMapping("/sa")
public class SaController extends BaseController<SaService, Sa> {

    @Value("${file.path:/home/file/}")
    private String picPath;
    private static final String filedStr = "name,pic_path";

    /**
     * 
     * @Title select
     * @Description 搜索并分页
     * @return ResponseEntity<List<Sa>>
     */
    @GetMapping
    public ResponseEntity<Result> select(HttpServletRequest request) {
        return super.select(request, filedStr);
    }

    /**
     * 
     * @Title select
     * @Description 返回id,name
     * @return ResponseEntity<List<Sa>>
     */
    @GetMapping("/all")
    public ResponseEntity<Result> select() {
        return super.select(null);
    }

    /**
     * 
     * @Title select
     * @Description 返回id,name
     * @return ResponseEntity<List<SaVO>>
     */
    @GetMapping("/vos")
    public ResponseEntity<Result> selectVOs() {
        try {
            List<SaVO> saVOList = baseService.selectSaVOList();
            return ResponseEntityGenerator.ok(saVOList);
        } catch (Exception e) {
            logger.error("", e);
            return ResponseEntityGenerator.bad(null);
        }
    }

    /**
     * 
     * @Title insert
     * @Description 添加
     * @return ResponseEntity<String>
     */
    @PostMapping
    public ResponseEntity<Result> insertOrUpdate(@Valid @RequestBody SaDTO saDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return CommonUtil.fieldIllegalMess(bindingResult, messageSource);
        }
        Sa sa = null;
        try {
            sa = new Sa();
            sa.setName(saDTO.getName());
            long time = System.currentTimeMillis();
            sa.setGmtModified(time);
            MultipartFile file = saDTO.getFile();
            // 新增/修改判断
            if (saDTO.getId() == null) {
                // 新增
                sa.setGmtCreate(time);
                if (saDTO.getFile() == null) {
                    return ResponseEntityGenerator.bad("image is null.");
                }
                boolean picResult = fixPicFields(file, sa);
                if (!picResult) {
                    return ResponseEntityGenerator.bad("image is illegal.");
                }
                boolean result = baseService.insert(sa);
                if (!result) {
                    logger.warn("sa:" + sa + ",insert failure,unknown error.");
                    return ResponseEntityGenerator.bad("insert failure,unknown error.");
                }
            } else {
                // 修改,创建时间不变
                sa.setGmtCreate(null);
                sa.setId(saDTO.getId());
                // 文件为空,原图片属性不变
                if (file == null) {
                    sa.setWidth(null);
                    sa.setHeight(null);
                    sa.setPicPath(null);
                } else {
                    boolean picResult = fixPicFields(file, sa);
                    if (!picResult) {
                        return ResponseEntityGenerator.bad("image is illegal.");
                    }
                }
                boolean result = baseService.updateById(sa);
                if (!result) {
                    logger.warn("sa:" + sa + ",update failure,unknown error.");
                    return ResponseEntityGenerator.bad("update failure,unknown error.");
                }
            }
            return ResponseEntityGenerator.ok();
        } catch (Exception e) {
            logger.error("sa:" + sa, e);
            return ResponseEntityGenerator.error(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<Result> delete(@RequestParam List<Serializable> ids) {
        return super.delete(ids);
    }

    /**
     * @Title fixPicFields
     * @Description 解析图片,存储图片,获取图片信息填充Sa对象
     * @return void
     */
    private boolean fixPicFields(MultipartFile file, Sa sa) throws FileNotFoundException, IOException {
        String fileName = UUID.randomUUID()
                + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        File fileSourcePath = new File(picPath);
        // 判断文件父目录是否存在
        if (!fileSourcePath.exists()) {
            fileSourcePath.mkdir();
        }
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(fileSourcePath, fileName)));
        out.write(file.getBytes());
        out.flush();
        out.close();
        Double[] extents = GisMapUtil.getExtents(picPath + fileName);
        if (extents == null) {
            return false;
        }
        sa.setWidth(extents[2]);
        sa.setHeight(extents[3]);
        sa.setPicPath(fileName);
        return true;
    }
}

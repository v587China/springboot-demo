package com.ultra.controller.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class SaDTO {

    @ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "图片")
    private MultipartFile file;
    @NotBlank(message = " ${attr.required}")
    @Length(min = 1, max = 30, message = " ${attr.range.length}")
    @ApiModelProperty(value = "名称")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SaDTO [id=" + id + ", file=" + file + ", name=" + name + "]";
    }

}

package com.chase.springcloud.service.blog.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagRespDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private List<TagRespDto> children = new ArrayList<>();
}
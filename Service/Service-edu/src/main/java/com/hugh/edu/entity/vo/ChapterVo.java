package com.hugh.edu.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hugh on 2021/1/12
 */
@Data
public class ChapterVo {
    private String id;
    private String label;
    private List<VideoVo> children = new ArrayList<VideoVo>();
}

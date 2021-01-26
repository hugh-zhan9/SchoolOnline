package com.hugh.edu.entity.subjectVo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hugh on 2021/1/11
 */
@Data
public class FirstSubject {
    private String id;
    private String label;
    private List<SecondSubject> children = new ArrayList<SecondSubject>();
}

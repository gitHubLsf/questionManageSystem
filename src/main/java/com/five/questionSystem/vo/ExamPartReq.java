package com.five.questionSystem.vo;

import com.github.pagehelper.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamPartReq {
    private String type;
    private String difficulty;
    private String grade;
    private Integer question_count;

    public void valiation() {
        if (Objects.isNull(question_count) || question_count < 1) {
            this.question_count = 1;
        }
        if (!StringUtil.isEmpty(type)) {
            type = type.replaceAll("\\s*", "");
        }
        if (!StringUtil.isEmpty(difficulty)) {
            difficulty = difficulty.replaceAll("\\s*", "");
        }
        if (!StringUtil.isEmpty(grade)) {
            grade = grade.replaceAll("\\s*", "");
        }
    }
}

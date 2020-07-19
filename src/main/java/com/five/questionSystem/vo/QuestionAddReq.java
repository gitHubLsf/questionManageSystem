package com.five.questionSystem.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.StringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionAddReq {
    private String name;
    private Integer type;
    private Integer difficulty;
    private Integer grade;

    public void voliation() {
        if (!StringUtils.isEmpty(name)) {
            name = name.replaceAll("\\s*", "");
        }
    }
}

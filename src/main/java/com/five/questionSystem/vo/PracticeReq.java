package com.five.questionSystem.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.StringUtils;

import java.util.Objects;


/**
 * 查询练习的请求体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PracticeReq {
    private Integer page;
    private Integer limit;
    private String name;
    private String userName;

    public void valiation() {
        if (Objects.isNull(page) || page < 1) {
            this.page = 1;
        }
        if (Objects.isNull(limit) || limit < 5) {
            this.limit = 10;
        }
        if (!StringUtils.isEmpty(name)) {
            name = name.replaceAll("\\s*", "");
        }
        if (!StringUtils.isEmpty(userName)) {
            userName = userName.replaceAll("\\s*", "");
        }
    }
}

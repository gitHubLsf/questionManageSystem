package com.five.questionSystem.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Objects;


/**
 * 查询日志的请求体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogReq {
    private Integer page;
    private Integer limit;
    private String createBy;


    public void volidation() {
        if (Objects.isNull(page) || page < 1) {
            page = 1;
        }
        if (Objects.isNull(limit) || limit < 5) {
            limit = 10;
        }
        if (!StringUtils.isEmpty(createBy)) {
            createBy = createBy.replaceAll("\\s*", "");
        }
    }
}

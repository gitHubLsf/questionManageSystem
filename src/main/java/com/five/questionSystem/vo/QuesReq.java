package com.five.questionSystem.vo;

import com.github.pagehelper.util.StringUtil;
import com.mysql.jdbc.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;


/**
 * 查询错题的请求体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuesReq {
    private Integer page;
    private Integer limit;
    private String name;
    private Integer type;
    private Integer difficulty;
    private Integer grade;


    public void volidation() {
        if (Objects.isNull(page) || page < 1) {
            page = 1;
        }
        if (Objects.isNull(limit) || limit < 5) {
            limit = 10;
        }
        if (!StringUtil.isEmpty(name)) {
            name = name.replaceAll("\\s*", "");
        }
    }
}

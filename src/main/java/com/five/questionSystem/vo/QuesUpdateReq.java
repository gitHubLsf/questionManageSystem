package com.five.questionSystem.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.StringUtils;


/**
 * 修改问题时的请求体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuesUpdateReq {
    private Integer id;
    private String name;
    private Integer type;
    private Integer board;
    private Integer difficulty;
    private Integer grade;

    public void valiation() {
        if (!StringUtils.isEmpty(name)) {
            name = name.replaceAll("\\s*", "");
        }
    }
}

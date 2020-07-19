package com.five.questionSystem.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;


/**
 * 查询角色时的请求体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleReq {
    private Integer page;
    private Integer limit;
    private Integer id;

    public void valiation() {
        if (Objects.isNull(page) || page < 1) {
            this.page = 1;
        }
        if (Objects.isNull(limit) || limit < 5) {
            this.limit = 10;
        }
    }
}

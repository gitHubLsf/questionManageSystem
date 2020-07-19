package com.five.questionSystem.vo;

import com.alibaba.druid.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * 修改角色时的请求体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleUpdateReq {
    private Integer id;
    private String roleName;
    private String roleDes;
    private Integer[] ps;


    public void volidation() {
        if (!StringUtils.isEmpty(roleName)) {
            roleName = roleName.replaceAll("\\s*", "");
        }
        if (!StringUtils.isEmpty(roleDes)) {
            roleDes = roleDes.replaceAll("\\s*", "");
        }
    }
}

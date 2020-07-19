package com.five.questionSystem.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePassword {
    private String oldPassword;
    private String newPassword;
}

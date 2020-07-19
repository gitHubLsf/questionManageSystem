package com.five.questionSystem.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Types implements Comparable<Types> {
    private String name;
    private Integer value;

    public Types(Integer value) {
        this.value = value;
    }

    @Override
    public int compareTo(Types o) {
        return value.compareTo(o.getValue());
    }
}

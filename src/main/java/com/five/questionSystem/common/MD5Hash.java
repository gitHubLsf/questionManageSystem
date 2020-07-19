package com.five.questionSystem.common;

import org.apache.shiro.crypto.hash.Md5Hash;


/**
 * 加密算法
 */
public class MD5Hash {

    /**
     * 密码加密,使用MD5算法,加盐siggy,散列一次
     */
    public static String md5(String password) {
        return new Md5Hash(password, "siggy").toString();
    }


    /**
     * 计算字符串 name 的哈希值
     */
    public static String questionHash(String name) {
        return new Md5Hash(name).toString();
    }
}

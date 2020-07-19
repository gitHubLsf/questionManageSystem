package com.five.questionSystem.common;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;


/**
 * 验证码
 */
@Controller
@RequestMapping("/code")
public class VerCode {

    /**
     * 获取验证码
     */
    @RequestMapping("/getCode")
    public void getCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 通知浏览器不要缓存
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        response.setHeader("expires", "0");

        // 在内存中创建一个长80，宽30的图片，默认黑色背景
        // 参数一：长
        // 参数二：宽
        // 参数三：颜色
        int width = 80;
        int height = 30;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // 获取画笔
        Graphics g = image.getGraphics();
        // 设置画笔颜色为灰色
        g.setColor(Color.GRAY);
        // 填充图片
        g.fillRect(0, 0, width, height);

        // 产生随机验证码
        String checkCode = getCheckCode();
        // 将验证码放入session中
        request.getSession().setAttribute("CHECKCODE_SERVER", checkCode);

        // 设置画笔颜色为黄色
        g.setColor(Color.YELLOW);
        // 设置字体的小大
        g.setFont(new Font("黑体", Font.BOLD, 24));
        // 向图片上写入验证码
        g.drawString(checkCode, 15, 25);

        // 将内存中的图片输出到浏览器
        // 参数一：图片对象
        // 参数二：图片的格式，如PNG,JPG,GIF
        // 参数三：图片输出到哪里去
        ImageIO.write(image, "PNG", response.getOutputStream());
    }


    /**
     * 产生随机字符串
     */
    private String getCheckCode() {
        //清空旧数据
        str.setLength(0);
        int size = base.length();
        for (int i = 0; i < codeLength; i++) {
            str.append(base.charAt(random.nextInt(size)));
        }
        return str.toString();
    }


    // 验证码长度
    private int codeLength = 4;
    private StringBuffer str = new StringBuffer();
    private String base = "0123456789ABCDEFGabcdefg";
    private Random random = new Random();
}

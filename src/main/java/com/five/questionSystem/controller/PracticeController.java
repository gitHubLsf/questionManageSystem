package com.five.questionSystem.controller;

import com.five.questionSystem.common.DownloadUtils;
import com.five.questionSystem.common.Logs;
import com.five.questionSystem.entity.ExamPaper;
import com.five.questionSystem.entity.User;
import com.five.questionSystem.service.PracticeService;
import com.five.questionSystem.vo.PracticeReq;
import com.five.questionSystem.vo.ExamPartReq;
import com.five.questionSystem.vo.RespInfo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;


/**
 * 练习管理类
 */
@Controller
@RequestMapping("/practice")
public class PracticeController {


    @RequestMapping("{id}")
    @ResponseBody
    public ExamPaper queryById(@PathVariable("id") Integer id) {
        return practiceService.queryById(id);
    }


    /**
     * 教师添加练习给学生
     */
    @RequestMapping(value = "/add/teacher", method = RequestMethod.POST)
    @ResponseBody
    @Logs(operationName = "添加练习", operationType = "add_practice")
    public RespInfo teacherAddPractice(@RequestBody ExamPartReq[] parts, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("userInfo");
        int flag = practiceService.teacherAddPractice(parts, user.getId()) ? 0 : -1;
        respInfo.setCode(flag);
        return respInfo;
    }


    /**
     * 删除练习
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    @Logs(operationName = "删除练习", operationType = "delete_practice")
    public RespInfo delete(Integer id) {
        practiceService.deleteById(id);
        respInfo.setCode(0);
        return respInfo;
    }


    /**
     * 批量删除练习
     */
    @RequestMapping(value = "/batchDelete")
    @ResponseBody
    @Logs(operationName = "批量删除练习", operationType = "batchDel_practice")
    public RespInfo batchDelete(Integer[] ids) {
        practiceService.batchDelete(ids);
        respInfo.setCode(0);
        return respInfo;
    }


    /**
     * 下载练习
     */
    @RequestMapping("/download")
    @Logs(operationName = "下载练习", operationType = "download_practice")
    public void export(Integer id, HttpServletRequest request, HttpServletResponse servletResponse) throws Exception {
        //根据 id 查询试卷的名称
        ExamPaper examPaper = this.practiceService.queryById(id);
        String name = examPaper.getName() + ".doc";

        String url = "http://localhost:8080/practice/view?id=" + id;
        DownloadUtils.htmlToWord2(url, name, request.getSession().getServletContext().getRealPath("/file"));

        String filePath = request.getSession().getServletContext().getRealPath("file/" + name);
        File file = new File(filePath);
        String fileName = file.getName();
        fileName = URLEncoder.encode(fileName, "utf-8");

        servletResponse.addHeader("Connection", "close");
        servletResponse.addHeader("Content-Type", "application/octet-stream");
        servletResponse.addHeader("Content-Transfer-Encoding", "chunked");
        servletResponse.addHeader("Access-Control-Allow-Origin", "*");
        servletResponse.addHeader("Content-Disposition",
                "attachment;filename=" + fileName);
        servletResponse.setContentType("application/OCTET-STREAM");

        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = new byte[1024];
        ServletOutputStream outputStream = servletResponse.getOutputStream();
        int len = 0;
        while ((len = fileInputStream.read(bytes)) > 0) {
            outputStream.write(bytes, 0, len);
        }

        outputStream.close();
        fileInputStream.close();
    }


    /**
     * 管理员查看系统练习库
     */
    @RequestMapping(value = "/admin/list")
    @ResponseBody
    public RespInfo querySysPractice(PracticeReq practiceReq) {
        return practiceService.querySysPractice(practiceReq);
    }


    /**
     * 学生或教师查看自己创建的练习
     */
    @RequestMapping(value = "/list/person")
    @ResponseBody
    public RespInfo queryPersonPractice(PracticeReq practiceReq, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("userInfo");
        return practiceService.queryPersonPractice(practiceReq, user.getId());
    }


    /**
     * 学生查看老师发布的练习
     */
    @RequestMapping(value = "/list/underTea")
    @ResponseBody
    public RespInfo queryPraUnderTea(PracticeReq practiceReq, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("userInfo");
        return practiceService.queryPraUnderTea(practiceReq, user.getId());
    }


    /**
     * 根据 id 查询练习信息
     */
    @RequestMapping("/update/{id}")
    public ModelAndView update(@PathVariable("id") Integer id) {
        ExamPaper paper = practiceService.queryPracticeById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("paper", paper);
        modelAndView.setViewName("practice/practiceEdit");
        return modelAndView;
    }


    /**
     * 修改练习名称
     */
    @RequestMapping("/update")
    @ResponseBody
    @Logs(operationName = "修改练习", operationType = "edit_practice")
    public RespInfo updatePractice(PracticeReq req) {
        practiceService.update(req);
        respInfo.setCode(0);
        return respInfo;
    }


    @RequestMapping("/view")
    public ModelAndView view(Integer id) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        ExamPaper examPaper = practiceService.getById(id);
        modelAndView.addObject("paper", examPaper);
        modelAndView.setViewName("download/view");
        return modelAndView;
    }


    @Autowired
    private PracticeService practiceService;

    @Autowired
    private RespInfo respInfo;

}

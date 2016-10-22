package edu.zh.controller;

import com.baidu.ueditor.ActionEnter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xsTao on 2016/8/16.
 */
@Controller
public class SpringMvcUeditorSupport {
    private String rootPath = null;
    
    @RequestMapping(value = {"ueController"})
    @ResponseBody
    public void index(HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            request.setCharacterEncoding("utf-8");
            response.setHeader("Content-Type", "text/html");
            String tmpRootPath = request.getServletContext().getRealPath("/");
            rootPath = rootPath != null && !"".equals(rootPath) ? rootPath : tmpRootPath;
            response.getWriter().write(new ActionEnter(request, rootPath).exec());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getRootPath() {
        return this.rootPath;
    }
}

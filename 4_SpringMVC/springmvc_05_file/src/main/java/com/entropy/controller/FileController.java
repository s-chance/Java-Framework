package com.entropy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Controller
public class FileController {
    // @RequestParam("file") 将name=file的控件得到的文件封装成CommonsMultipartFile对象
    // 批量上传创建CommonsMultipartFile的数组即可
    @RequestMapping("/upload")
    public String upload(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) throws IOException {
        // 获取文件名
        String originalFilename = file.getOriginalFilename();
        // 文件为空直接返回首页
        if ("".equals(originalFilename)) {
            return "redirect:/index.jsp";
        }
        System.out.println("上传文件: " + originalFilename);
        // 上传文件保存路径
        String path = request.getServletContext().getRealPath("/upload");
        // 若该路径不存在则自动创建
        File realPath = new File(path);
        if (!realPath.exists()) {
            realPath.mkdir();
        }
        System.out.println("上传文件保存路径: " + realPath);

        // 文件输入流
        InputStream inputStream = file.getInputStream();
        // 文件输出流
        FileOutputStream fileOutputStream = new FileOutputStream(new File(realPath, originalFilename));
        // 读写
        int len = 0;
        byte[] bytes = new byte[1024];
        while ((len = inputStream.read(bytes)) != -1) {
            fileOutputStream.write(bytes, 0, len);
            fileOutputStream.flush();
        }
        fileOutputStream.close();
        inputStream.close();
        return "redirect:/index.jsp";
    }

    // 采用CommonsMultipartFile自带的transferTo方法实现文件上传
    @RequestMapping("/upload2")
    public String upload2(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) throws IOException {
        // 上传文件保存路径
        String path = request.getServletContext().getRealPath("/upload");
        File realPath = new File(path);
        if (!realPath.exists()) {
            realPath.mkdir();
        }
        System.out.println("上传文件保存路径: " + realPath);

        // 通过CommonsMultipartFile的方法直接写文件
        file.transferTo(new File(realPath + "/" + file.getOriginalFilename()));

        return "redirect:/index.jsp";
    }

    @RequestMapping("/download")
    public String download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取下载图片的路径
        String path = request.getServletContext().getRealPath("/upload");
        File[] files = new File(path).listFiles();
        String fileName = files[0].getName();
        // 1.设置response响应头
        response.reset(); // 清空缓存
        response.setCharacterEncoding("UTF-8"); // 设置字符编码
        response.setContentType("multipart/form-data"); // 设置二进制流传输数据
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
        File file = new File(path, fileName);
        // 2.读取文件
        InputStream fileInputStream = new FileInputStream(file);
        // 3.写入文件
        ServletOutputStream outputStream = response.getOutputStream();

        byte[] bytes = new byte[1024];
        int len = 0;
        // 4.读写操作
        while ((len = fileInputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, len);
            outputStream.flush();
        }
        outputStream.close();
        fileInputStream.close();
        return null;
    }
}

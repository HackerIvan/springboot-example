package com.kingboy.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * @author kingboy--KingBoyWorld@163.com
 * @date 2017/11/26 下午10:43
 * @desc 文件上传下载.
 */
@RestController
@RequestMapping(value = "/file")
public class FileController {

    /**
     * 文件上传
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        //获取文件的大小，以size为准
        long size = file.getSize();
        int available = file.getInputStream().available();
        int length = file.getBytes().length;
        //获取参数名，也就是"file"
        String name = file.getName();
        //获取文件原本的名称
        String originName = file.getOriginalFilename();

        //保存文件
        String fileName = UUID.randomUUID().toString();
        String filePath = "/Users/kingboy/Desktop/" + fileName;
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return String.valueOf(fileName);
    }


    /**
     * 文件下载
     * @param fileName
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/{fileName}")
    public void download(@PathVariable String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try(ServletOutputStream outputStream = response.getOutputStream()){
            String filePath = "/Users/kingboy/Desktop/" + fileName;
            Files.copy(Paths.get(filePath), outputStream);
            response.setContentType("application/x-download");
            response.setHeader("Content-Disposition", "attachment;filename=test.txt");
        }

    }

}

package com.qst.agrifinancetrade.controller;

import com.qst.agrifinancetrade.common.Result;
import com.qst.agrifinancetrade.common.StatusCode;
import com.qst.agrifinancetrade.exception.FileFormatException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author QST
 * @Description 文件上传
 * @Date 2021-08-24
 */
@Api(tags = "文件上传接口")
@RestController
@RequestMapping("/file")
public class FilesUploadController {
    @Value("${application.upload-path}")
    private String fileDirectory;

    //    头像上传
    @ApiOperation(value = "头像上传")
    @PostMapping("/upload/{type}")
    public Result<String> upload(@PathVariable("type") String type,
                                 @RequestParam("file") MultipartFile file) throws Exception {
        System.out.println("请求图片");
        String originalFilename = file.getOriginalFilename();
        String tail = originalFilename.substring(originalFilename.lastIndexOf("."));
//    图片常见的格式有：bmp、jpg、tiff、gif、pcx、tga、exif、fpx、svg、psd、cdr、pcd、dxf、ufo、eps、ai、raw等
        if (!tail.equals(".jpg") & !tail.equals(".gif") & !tail.equals(".png") & !tail.equals(" eg") &
                !tail.equals(".webp") & !tail.equals(".mp4")) {
            throw new FileFormatException("请传入正确格式文件");
        }
//        创建目录
        String header = UUID.randomUUID().toString().replaceAll("-", "");
        String newFileName = header + tail;
        String targetFileLocation = fileDirectory + File.separator + type;
        File file1 = new File(targetFileLocation);
        System.out.println("目标目录路径: " + targetFileLocation);
        System.out.println("目标目录是否存在: " + file1.exists());
        if (!file1.exists()) {
            boolean mkdirsResult = file1.mkdirs();
            System.out.println("创建目录结果: " + mkdirsResult);
            if (!mkdirsResult) {
                System.err.println("无法创建目录: " + targetFileLocation);
                throw new IOException("无法创建目录: " + targetFileLocation);
            }
        }
        //创建文件
        String targetFileName = targetFileLocation + File.separator + newFileName;
        File targetFile = new File(targetFileName);
        System.out.println("目标文件路径: " + targetFileName);
        System.out.println("目标文件是否存在: " + targetFile.exists());
        if (!targetFile.exists()) {
            boolean createFileResult = targetFile.createNewFile();
            System.out.println("创建文件结果: " + createFileResult);
            if (!createFileResult) {
                System.err.println("无法创建文件: " + targetFileName);
                throw new IOException("无法创建文件: " + targetFileName);
            }
        }
        
        // 检查文件是否可写
        if (!targetFile.canWrite()) {
            System.err.println("文件不可写: " + targetFileName);
            throw new IOException("文件不可写: " + targetFileName);
        }
        
        try {
            file.transferTo(targetFile);
            System.out.println("文件传输完成");
        } catch (IOException e) {
            System.err.println("文件传输失败: " + e.getMessage());
            throw e;
        }
        
        // 验证文件是否真正创建并输出详细信息
        System.out.println("文件上传后验证:");
        System.out.println("文件是否存在: " + targetFile.exists());
        System.out.println("文件大小: " + targetFile.length() + " 字节");
        // 输出文件完整路径
        System.out.println("文件上传成功，路径: " + targetFile.getAbsolutePath());
        return new Result<String>(true, StatusCode.OK, "上传成功", newFileName);
    }
    //    资料上传
    @ApiOperation(value = "资料上传")
    @PostMapping("/uploadInfo/{type}")
    public Result<String> uploadInfo(@PathVariable("type") String type,
                                 @RequestParam("file") MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        String tail = originalFilename.substring(originalFilename.lastIndexOf("."));
        if (!tail.equals(".jpg") & !tail.equals(".gif") & !tail.equals(".png") & !tail.equals(" eg") &
                !tail.equals(".webp") & !tail.equals(".mp4")) {
            throw new FileFormatException("请传入正确格式文件");
        }
//        创建目录
        String header = UUID.randomUUID().toString().replaceAll("-", "");
//        Calendar calendar = Calendar.getInstance();
//        Date time = calendar.getTime();
//        String header = time.toString();
        String newFileName = header + tail;
        String targetFileLocation = fileDirectory + File.separator + type;
        File file1 = new File(targetFileLocation);
        if (!file1.exists()) {
            file1.mkdirs();
        }
        //创建文件
        String targetFileName = targetFileLocation + File.separator + newFileName;
        File targetFile = new File(targetFileName);
        if (!targetFile.exists()) {
            targetFile.createNewFile();
        }
        file.transferTo(targetFile);
        // 输出文件完整路径
        System.out.println("文件上传成功，路径: " + targetFile.getAbsolutePath());
        return new Result<String>(true, StatusCode.OK, "上传成功", newFileName);
    }
}
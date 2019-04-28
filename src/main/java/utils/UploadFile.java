package utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

import javax.servlet.http.HttpServletRequest;
import java.io.File;


import java.text.SimpleDateFormat;
import java.util.*;


public class UploadFile {
    public static boolean deleteFile(String url) {
        File file = new File(url);
        return file.delete();
    }

    public static FileItem getFileItem(List<FileItem> fileItemList) {

        for (FileItem fileItem : fileItemList) {
            if (!fileItem.isFormField()){
                return fileItem;
            }
        }
        return null;
    }


    public static List<FileItem> getFileItemList(HttpServletRequest request) {
        // 磁盘文件工厂
        // 创建一个文件上传解析器
        ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
        // 中文处理
        upload.setHeaderEncoding("UTF-8");

        List<FileItem> fileItemList = new ArrayList<>();
        try {
            RequestContext requestContext = new ServletRequestContext(request);
            fileItemList = upload.parseRequest(requestContext);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }

        return fileItemList;
    }

    public static String saveHeadImage(FileItem fileItem, String phone) {
        // 磁盘文件工厂
        // 创建一个文件上传解析器
        ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
        // 中文处理
        upload.setHeaderEncoding("UTF-8");

        String path = "/images/users/";
        // 如果目录不存在则创建
        File uploadDir = new File(path);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("-yyyyMMddHHmmss");
        String savePathAndName = saveFile(fileItem, path, phone + simpleDateFormat.format(date));
        return savePathAndName;
    }

    private static String saveFile(FileItem fileItem, String savePath) {
        // 文件名字
        String fileName = new File(fileItem.getName()).getName();
        // 文件存储路径
        String savePathAndName = savePath + fileName;
        // 创建文件
        File storeFile = new File(savePathAndName);
        // 保存文件到硬盘
        try {
            fileItem.write(storeFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return savePathAndName;
    }

    private static String saveFile(FileItem fileItem, String savePath, String saveName) {
        // 文件名字
        String fileName = new File(fileItem.getName()).getName();
        saveName += fileName.substring(fileName.lastIndexOf("."));

        // 文件存储路径
        String savePathAndName = savePath + saveName;
        // 创建文件
        File storeFile = new File(savePathAndName);
        if (storeFile.exists()) {
            storeFile.delete();
        }
        // 保存文件到硬盘
        try {
            fileItem.write(storeFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return savePathAndName;
    }

    /**
     * 保存发现图片
     * @param fileItemList
     * @param discoveryID
     * @return 图片 url
     */
    public static String saveDiscoveryImage(List<FileItem> fileItemList, String discoveryID) {
        // 磁盘文件工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 创建一个文件上传解析器
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 中文处理
        upload.setHeaderEncoding("UTF-8");

        String path = "/images/discovery/" + discoveryID + "/";
        // 如果目录不存在则创建
        File uploadDir = new File(path);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }


        JsonArray jsonArray = new JsonArray();
        for(FileItem fileItem : fileItemList) {

            // 如果 fileitem 中封装的是文件
            if (!fileItem.isFormField()) {
                String savePathAndName = saveFile(fileItem, path);
                // 保存到 JsonArray 中
                jsonArray.add(savePathAndName);
                System.out.println("UploadFile:" + savePathAndName);
            }
        }
        return jsonArray.toString();
    }

    public static Map<String, String> getParameterMap(List<FileItem> fileItemList) {
        // key-value 键值对
        Map<String, String> parameterMap = new HashMap<>();

        // 创建一个文件上传解析器
        ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
        // 中文处理
        upload.setHeaderEncoding("UTF-8");


        try {
            for(FileItem fileItem : fileItemList) {
                // 如果 fileitem 中封装的普通数据
                if (fileItem.isFormField()) {

                    String name = fileItem.getFieldName();
                    String value = null;
                    try {
                        value = fileItem.getString("UTF-8");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    parameterMap.put(name, value);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return parameterMap;
    }

}

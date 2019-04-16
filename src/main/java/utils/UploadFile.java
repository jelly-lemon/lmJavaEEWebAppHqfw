package utils;

import com.google.gson.JsonArray;
import com.sun.deploy.net.HttpRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UploadFile {
    // 上传配置
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB


    /**
     * 保存图片到服务器
     * @param request   请求
     //* @param discoveryID 文章唯一编号，主键
     */
    /*public static String saveFileToDisk(HttpServletRequest request, String discoveryID) {
        JsonArray jsonArray = new JsonArray();  // 返回保存的文件名

        // 配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 设置临时存储目录
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        // 构造上传对象
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 设置最大文件上传值
        upload.setFileSizeMax(MAX_FILE_SIZE);
        // 设置最大请求值 (包含文件和表单数据)
        upload.setSizeMax(MAX_REQUEST_SIZE);
        // 中文处理
        upload.setHeaderEncoding("UTF-8");
        // 构造存储上传的文件的路径
        // 这个路径相对当前应用的目录
        String uploadPath = request.getServletContext().getRealPath("/") + "images/discovery/" + discoveryID;
        // 如果目录不存在则创建
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        // 打印该目录路径
        System.out.println("UploadFile:We created a directory:" + uploadPath);
        try {
            // 解析请求的内容提取文件数据
            //@SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);

            // 如果有数据
            if (formItems != null && formItems.size() > 0) {
                // 迭代表单数据
                for (FileItem item : formItems) {
                    // 处理不在表单中的字段
                    if (!item.isFormField()) {
                        // 文件名字
                        String fileName = new File(item.getName()).getName();
                        // 文件存储路径
                        String filePathAndName = uploadPath + File.separator + fileName;
                        // 保存在 JsonArray 中
                        jsonArray.add(filePathAndName);
                        // 创建文件
                        File storeFile = new File(filePathAndName);
                        // 在控制台输出文件的上传路径
                        System.out.println(filePathAndName);
                        // 保存文件到硬盘
                        item.write(storeFile);
                    }
                }
            }
            return jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }*/


    public static List<FileItem> getFileItemList(HttpServletRequest request) {
        // 磁盘文件工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 创建一个文件上传解析器
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 中文处理
        upload.setHeaderEncoding("UTF-8");
        // 构造存储上传的文件的路径
        // 这个路径相对当前应用的目录
        //String uploadPath = request.getServletContext().getRealPath("/") + "images/discovery/" + discoveryID;
        // 如果目录不存在则创建
        /*File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }*/

        List<FileItem> fileItemList = new ArrayList<>();

        try {
            RequestContext requestContext = new ServletRequestContext(request);
            fileItemList = upload.parseRequest(requestContext);

        } catch (FileUploadException e) {
            e.printStackTrace();
        }

        return fileItemList;
    }


    /**
     * 提取请求当中的键值对
     * @param request   请求
     * @return  键值对 Map
     */
    /*public static Map<String, String> getParameterMap(HttpServletRequest request) {
        // key-value 键值对
        Map<String, String> parameterMap = new HashMap<>();

        // 磁盘文件工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 创建一个文件上传解析器
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            RequestContext requestContext = new ServletRequestContext(request);
            List<FileItem> fileItemList = upload.parseRequest(requestContext);

            for(FileItem fileItem : fileItemList) {
                // 如果 fileitem 中封装的是普通输入项的数据
                if (fileItem.isFormField()) {
                    String name = fileItem.getFieldName();
                    String value = fileItem.getString("UTF-8");
                    parameterMap.put(name, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parameterMap;
    }
*/
    /**
     * 保存 request 里面的文件到指定路径
     * @param request    请求上下文
     * @param savePath  保存路径
     */
    /*public static void saveFileToDisk(HttpServletRequest request, String savePath) {
        // 磁盘文件工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 创建一个文件上传解析器
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            RequestContext requestContext = new ServletRequestContext(request);
            List<FileItem> fileItemList = upload.parseRequest(requestContext);

            for(FileItem fileItem : fileItemList) {
                // 如果 fileitem 中封装的是文件
                if (!fileItem.isFormField()) {
                    //fileItem.getName()
                    // 文件名字
                    String fileName = new File(fileItem.getName()).getName();
                    // 文件存储路径
                    String savePathAndName = request.getServletContext().getRealPath("/") + savePath + File.separator + fileName;
                    // 创建文件
                    File storeFile = new File(savePathAndName);
                    // 在控制台输出文件的上传路径
                    //System.out.println(savePathAndName);
                    // 保存文件到硬盘
                    fileItem.write(storeFile);
                    System.out.println("UploadFile:" + savePathAndName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /**
     * 保存用户上传的图片
     * @param request   请求
     * @param discoveryID   发现 ID
     * @return  JsonArray 格式字符串
     */
    /*public static String saveDiscoveryImage(HttpServletRequest request, String discoveryID) {
        //String imgURL = "";
        JsonArray jsonArray = new JsonArray();

        // 磁盘文件工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 创建一个文件上传解析器
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            RequestContext requestContext = new ServletRequestContext(request);
            List<FileItem> fileItemList = upload.parseRequest(requestContext);

            for(FileItem fileItem : fileItemList) {

                // 如果 fileitem 中封装的是文件
                if (!fileItem.isFormField()) {
                    System.out.println("UploadFile:" + "发现一个文件");
                    //fileItem.getName()
                    // 文件名字
                    String fileName = new File(fileItem.getName()).getName();
                    // 保存到 JsonArray 中
                    jsonArray.add(fileName);
                    // 文件存储路径
                    String savePathAndName = request.getServletContext().getRealPath("/") + "images" + File.separator + "discovery" + File.separator + discoveryID + File.separator + fileName;
                    // 创建文件
                    File storeFile = new File(savePathAndName);
                    // 在控制台输出文件的上传路径
                    //System.out.println(savePathAndName);
                    // 保存文件到硬盘
                    fileItem.write(storeFile);
                    System.out.println("UploadFile:" + savePathAndName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonArray.toString();
    }*/

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

        String path = File.separator + "images" + File.separator + "discovery" + File.separator + discoveryID + File.separator;
        // 如果目录不存在则创建
        File uploadDir = new File(path);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }



        JsonArray jsonArray = new JsonArray();
        for(FileItem fileItem : fileItemList) {

            // 如果 fileitem 中封装的是文件
            if (!fileItem.isFormField()) {
                // 文件名字
                String fileName = new File(fileItem.getName()).getName();
                // 文件存储路径
                String savePathAndName = path + fileName;
                // 创建文件
                File storeFile = new File(savePathAndName);
                // 保存文件到硬盘
                try {
                    fileItem.write(storeFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

        for(FileItem fileItem : fileItemList) {
            // 如果 fileitem 中封装的是普通输入项的数据
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

        return parameterMap;
    }

}

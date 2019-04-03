package utils;

import com.google.gson.JsonArray;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

import java.util.ArrayList;
import java.util.List;


public class UploadFile {
    // 上传配置
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB


    /**
     * 保存图片到服务器
     * @param request   请求
     * @param article_id    文章唯一编号，主键
     */
    public static String saveFile(HttpServletRequest request, String article_id) {
        //List<String> fileNameList = new ArrayList<>();   // 返回保存的文件名
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
        String uploadPath = request.getServletContext().getRealPath("/") + "images/article/" + article_id;
        // 如果目录不存在则创建
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            if (!uploadDir.mkdir()) return null; // 创建失败则返回
        }
        // 打印该目录路径
        System.out.println("We created a directory：" + uploadPath);
        try {
            // 解析请求的内容提取文件数据
            @SuppressWarnings("unchecked")
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
    }
}

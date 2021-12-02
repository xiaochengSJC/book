package com.example.book.controller;


import com.example.book.config.CommonResponse;
import com.example.book.config.ProjectConstants;
import com.example.book.model.Book;
import com.example.book.model.BookType;
import com.example.book.model.Borrow;
import com.example.book.model.User;
import com.example.book.service.BookService;
import com.example.book.service.UserService;
import com.example.book.utils.Md5Util;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    UserService userService;

    @PostMapping("/getBookInfo")
    public CommonResponse getBookInfo(@RequestBody Map<String,Object> map, HttpSession session){
        String typeId = map.get("typeId")==null?"":map.get("typeId").toString();
        String bookName = map.get("bookName")==null?"":map.get("bookName").toString();
        String pageSize = map.get("pageSize")==null?"":map.get("pageSize").toString();
        String pageNum = map.get("pageNum")==null?"":map.get("pageNum").toString();
        List<Book> list = bookService.getBookInfo(typeId,bookName);
        if(list == null){
            return new CommonResponse(ProjectConstants.ERROR_CODE,"查询失败",null);
        }
        list.forEach(bean -> {
            String status = bean.getStatus();
            String id = bean.getTypeId();
            BookType bookType = bookService.getBookTypeById(id);
            String typeName = bookType.getTypeName();
            bean.setTypeId(typeName);
            if("0".equals(status)){
                bean.setStatus("可借");
            }else{
                bean.setStatus("已借出");
            }
        });
        map.put("list",list);
        return new CommonResponse(ProjectConstants.SUCCESS_CODE,"查询成功",map);
    }


    @PostMapping(value = "/uploadFile")
    public CommonResponse uploadFile(@RequestParam Map<String,Object> map,@RequestParam("file") MultipartFile file) throws IOException {
        String typeId = map.get("typeId")==null?"":map.get("typeId").toString();
        // 文件名
        String fileName = file.getOriginalFilename();
        // 在file文件夹中创建名为fileName的文件
        assert fileName != null;
        int split = fileName.lastIndexOf(".");
        // 文件后缀，用于判断上传的文件是否是合法的
        String suffix = fileName.substring(split+1,fileName.length());
        //判断文件类型，因为我这边是图片，所以只设置三种合法格式
        String url = "";
        String path = PropertiesUtil.class.getClassLoader().getResource("upload/").getPath();
        if("jpg".equals(suffix) || "jpeg".equals(suffix) || "png".equals(suffix)) {
            // 正确的类型，保存文件
            try {
                path = path + "/" + typeId;
                File upload = new File(path);
                if(!upload.exists()) {
                    upload.mkdirs();
                }
                File savedFile = new File(path  +"/" +fileName);
                file.transferTo(savedFile);
                url = savedFile.getAbsolutePath();
                System.out.println("图片上传完毕，存储地址为："+"/"+ typeId);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else {
            //错误的类型，返回错误提示
            return new CommonResponse(ProjectConstants.SUCCESS_CODE,"文件上传失败,请重试！",null);
        }
        File savedFile;
        return new CommonResponse(ProjectConstants.SUCCESS_CODE,"文件上传成功",typeId +"/"+fileName);
    }

    /**
     * 添加书籍
     */
    @PostMapping("/addBookInfo")
    public CommonResponse addBookInfo(@RequestBody Map<String,Object> map, HttpSession session){
        String bookName = map.get("bookName")==null?"":map.get("bookName").toString();
        String card = map.get("card")==null?"":map.get("card").toString();
        String typeId = map.get("typeId")==null?"":map.get("typeId").toString();
        String authName = map.get("authName")==null?"":map.get("authName").toString();
        String press = map.get("press")==null?"":map.get("press").toString();
        String status = map.get("status")==null?"":map.get("status").toString();
        String bookPic = map.get("bookPic")==null?"":map.get("bookPic").toString();
        String description = map.get("description")==null?" ":map.get("description").toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(new Date());
        Book book = new Book();
        book.setBookName(bookName);
        book.setCard(card);
        book.setBookPic(bookPic);
        book.setDescription(description);
        book.setTypeId(typeId);
        book.setAuthName(authName);
        book.setPress(press);
        book.setStatus("0");
        book.setCreateTime(format);
        int count = bookService.addBook(book);
        System.out.println(count);
        if (count != 1) {
            return new CommonResponse(ProjectConstants.ERROR_CODE,"添加书籍失败",null);
        }
        return new CommonResponse(ProjectConstants.SUCCESS_CODE,"添加书籍成功",null);
    }

    /**
     * 查询书籍信息
     */

    @PostMapping("/getBookInfoById")
    @ResponseBody
    //@CrossOrigin
    public CommonResponse getBookInfoById(@RequestBody Map<String,Object> map, HttpSession session) {
        String id = map.get("id")==null?" ":map.get("id").toString();
        Book book = bookService.getBook(id);
        if (book == null) {
            return new CommonResponse(ProjectConstants.ERROR_CODE,"查询读者信息失败",null);
        }
        return new CommonResponse(ProjectConstants.SUCCESS_CODE,"查询成功",book);
    }

    /**
     * 更新书籍
     */
    @PostMapping("/editBookInfo")
    public CommonResponse updateBookInfo(@RequestBody Map<String,Object> map, HttpSession session){
        String id = map.get("id")==null?" ":map.get("id").toString();
        String bookName = map.get("bookName")==null?" ":map.get("bookName").toString();
        String card = map.get("card")==null?" ":map.get("card").toString();
        String typeId = map.get("typeId")==null?" ":map.get("typeId").toString();
        String authName = map.get("authName")==null?" ":map.get("authName").toString();
        String press = map.get("press")==null?" ":map.get("press").toString();
        String bookPic = map.get("bookPic")==null?" ":map.get("bookPic").toString();
        String description = map.get("description")==null?" ":map.get("description").toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(new Date());
        Book book = bookService.getBook(id);
        book.setBookName(bookName);
        book.setCard(card);
        book.setBookPic(bookPic);
        book.setDescription(description);
        book.setTypeId(typeId);
        book.setAuthName(authName);
        book.setPress(press);
        book.setStatus("0");
        book.setCreateTime(format);
        int count = bookService.updateBook(book);
        System.out.println(count);
        if (count != 1) {
            return new CommonResponse(ProjectConstants.ERROR_CODE,"修改书籍失败",null);
        }
        return new CommonResponse(ProjectConstants.SUCCESS_CODE,"修改书籍成功",null);
    }

    /**
     * 修改读者信息
     */

    @PostMapping("/deleteUserInfo")
    @ResponseBody
    //@CrossOrigin
    public CommonResponse deleteBookInfo(@RequestBody Map<String,Object> map, HttpSession session) {
        String id = map.get("id")==null?" ":map.get("id").toString();
        int count = bookService.deleteBook(id);
        if (count != 1) {
            return new CommonResponse(ProjectConstants.ERROR_CODE,"删除书籍失败",null);
        }
        return new CommonResponse(ProjectConstants.SUCCESS_CODE,"删除书籍失败",null);
    }

    /**
     * 查询书籍种类
     */
    @PostMapping("/getBookType")
    public CommonResponse getBookType(@RequestBody Map<String,Object> map, HttpSession session){
        String typeName = map.get("typeName")==null?"":map.get("typeName").toString();
        String pageSize = map.get("pageSize")==null?"":map.get("pageSize").toString();
        String pageNum = map.get("pageNum")==null?"":map.get("pageNum").toString();
        List<BookType> list = bookService.getBookType(typeName);
        if(list == null){
            return new CommonResponse(ProjectConstants.ERROR_CODE,"查询失败",null);
        }
        map.put("list",list);
        map.put("total",0);
        return new CommonResponse(ProjectConstants.SUCCESS_CODE,"查询成功",map);
    }

    /**
     * 根据id查询书籍种类
     */
    @PostMapping("/getBookTypeById")
    public CommonResponse getBookTypeById(@RequestBody Map<String,Object> map, HttpSession session){
        String id = map.get("id")==null?"":map.get("id").toString();
        BookType bookType = bookService.getBookTypeById(id);
        if (bookType == null) {
            return new CommonResponse(ProjectConstants.ERROR_CODE,"查询书籍种类失败",null);
        }


        return new CommonResponse(ProjectConstants.SUCCESS_CODE,"查询成功",bookType);
    }

    /**
     * 添加书籍
     */
    @PostMapping("/addBookType")
    public CommonResponse addBookType(@RequestBody Map<String,Object> map, HttpSession session){
        String typeName = map.get("typeName")==null?"":map.get("typeName").toString();
        BookType bookType = new BookType();
        bookType.setTypeName(typeName);
        int count = bookService.addBookType(bookType);
        System.out.println(count);
        if (count != 1) {
            return new CommonResponse(ProjectConstants.ERROR_CODE,"添加书籍种类失败",null);
        }
        return new CommonResponse(ProjectConstants.SUCCESS_CODE,"添加书籍种类成功",null);
    }

    /**
     * 更新书籍
     */
    @PostMapping("/editBookType")
    public CommonResponse updateBookType(@RequestBody Map<String,Object> map, HttpSession session){
        String id = map.get("id")==null?"":map.get("id").toString();
        String typeName = map.get("typeName")==null?"":map.get("typeName").toString();
        BookType bookType = new BookType();
        bookType.setId(Integer.parseInt(id));
        bookType.setTypeName(typeName);
        int count = bookService.updateBookType(bookType);
        System.out.println(count);
        if (count != 1) {
            return new CommonResponse(ProjectConstants.ERROR_CODE,"添加书籍失败",null);
        }
        return new CommonResponse(ProjectConstants.SUCCESS_CODE,"添加书籍成功",null);
    }

    /**
     * 删除书籍
     */
    @PostMapping("/deleteBookType")
    public CommonResponse deleteBookType(@RequestBody Map<String,Object> map, HttpSession session){
        String id = map.get("id")==null?" ":map.get("id").toString();
        int count = bookService.deleteBookType(id);
        if (count != 1) {
            return new CommonResponse(ProjectConstants.ERROR_CODE,"删除书籍失败",null);
        }
        return new CommonResponse(ProjectConstants.SUCCESS_CODE,"删除书籍失败",null);
    }

    /**
     * 获取图片地址
     */
    @RequestMapping(value = "/showImg")

    @ResponseBody
    public void showImg(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String,Object> map) {
        String typeId = map.get("typeId")==null?"":map.get("typeId").toString();
        String fileName = map.get("fileName")==null?"":map.get("fileName").toString();
        String path = PropertiesUtil.class.getClassLoader().getResource("upload/").getPath();
        File imgFile = new File(path+"/"+typeId+"/"+fileName);
        FileInputStream fin = null;
        OutputStream output = null;
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition","attachment;fileName="+fileName);
        response.setHeader("Content-type","application/octet-stream");
        try {
            output = response.getOutputStream();
            fin = new FileInputStream(imgFile);
            byte[] arr = new byte[1024 * 10];
            int n;
            while ((n = fin.read(arr)) != -1) {
                output.write(arr, 0, n);
            }
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

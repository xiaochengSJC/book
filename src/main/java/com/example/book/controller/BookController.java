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
        String card = map.get("card")==null?"":map.get("card").toString();
        String authName = map.get("authName")==null?"":map.get("authName").toString();
        String press = map.get("press")==null?"":map.get("press").toString();
        String pageSize = map.get("pageSize")==null?"":map.get("pageSize").toString();
        String pageNum = map.get("pageNum")==null?"":map.get("pageNum").toString();
        List<Book> list = bookService.getBookInfo(typeId,bookName,pageSize,pageNum,authName,card,press);
        if(list == null){
            return new CommonResponse(ProjectConstants.ERROR_CODE,"????????????",null);
        }
        list.forEach(bean -> {
            String status = bean.getStatus();
            String id = bean.getTypeId();
            BookType bookType = bookService.getBookTypeById(id);
            String typeName = bookType.getTypeName();
            bean.setTypeId(typeName);
            if("0".equals(status)){
                bean.setStatus("??????");
            }else{
                bean.setStatus("?????????");
            }
        });
        int total = bookService.getBookCount(typeId,bookName,authName,card,press);
        map.put("list",list);
        map.put("total",total);
        return new CommonResponse(ProjectConstants.SUCCESS_CODE,"????????????",map);
    }


    @PostMapping(value = "/uploadFile")
    public CommonResponse uploadFile(@RequestParam Map<String,Object> map,@RequestParam("file") MultipartFile file) throws IOException {
        String typeId = map.get("typeId")==null?"":map.get("typeId").toString();
        // ?????????
        String fileName = file.getOriginalFilename();
        // ???file????????????????????????fileName?????????
        assert fileName != null;
        int split = fileName.lastIndexOf(".");
        // ????????????????????????????????????????????????????????????
        String suffix = fileName.substring(split+1,fileName.length());
        //?????????????????????????????????????????????????????????????????????????????????
        String url = "";
        String path = PropertiesUtil.class.getClassLoader().getResource("upload/").getPath();
        if("jpg".equals(suffix) || "jpeg".equals(suffix) || "png".equals(suffix)) {
            // ??????????????????????????????
            try {
                path = path + "/" + typeId;
                File upload = new File(path);
                if(!upload.exists()) {
                    upload.mkdirs();
                }
                File savedFile = new File(path  +"/" +fileName);
                file.transferTo(savedFile);
                url = savedFile.getAbsolutePath();
                System.out.println("???????????????????????????????????????"+"/"+ typeId);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else {
            //????????????????????????????????????
            return new CommonResponse(ProjectConstants.SUCCESS_CODE,"??????????????????,????????????",null);
        }
        File savedFile;
        return new CommonResponse(ProjectConstants.SUCCESS_CODE,"??????????????????",typeId +"/"+fileName);
    }

    /**
     * ????????????
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
            return new CommonResponse(ProjectConstants.ERROR_CODE,"??????????????????",null);
        }
        return new CommonResponse(ProjectConstants.SUCCESS_CODE,"??????????????????",null);
    }

    /**
     * ??????????????????
     */

    @PostMapping("/getBookInfoById")
    @ResponseBody
    //@CrossOrigin
    public CommonResponse getBookInfoById(@RequestBody Map<String,Object> map, HttpSession session) {
        String id = map.get("id")==null?" ":map.get("id").toString();
        Book book = bookService.getBook(id);
        if (book == null) {
            return new CommonResponse(ProjectConstants.ERROR_CODE,"????????????????????????",null);
        }
        return new CommonResponse(ProjectConstants.SUCCESS_CODE,"????????????",book);
    }

    /**
     * ????????????
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
            return new CommonResponse(ProjectConstants.ERROR_CODE,"??????????????????",null);
        }
        return new CommonResponse(ProjectConstants.SUCCESS_CODE,"??????????????????",null);
    }

    /**
     * ??????????????????
     */

    @PostMapping("/deleteBookInfo")
    @ResponseBody
    //@CrossOrigin
    public CommonResponse deleteBookInfo(@RequestBody Map<String,Object> map, HttpSession session) {
        String id = map.get("id")==null?" ":map.get("id").toString();
        int count = bookService.deleteBook(id);
        if (count != 1) {
            return new CommonResponse(ProjectConstants.ERROR_CODE,"??????????????????",null);
        }
        return new CommonResponse(ProjectConstants.SUCCESS_CODE,"??????????????????",null);
    }

    /**
     * ??????????????????
     */
    @PostMapping("/getBookType")
    public CommonResponse getBookType(@RequestBody Map<String,Object> map, HttpSession session){
        String typeName = map.get("typeName")==null?"":map.get("typeName").toString();
        String pageSize = map.get("pageSize")==null?"":map.get("pageSize").toString();
        String pageNum = map.get("pageNum")==null?"":map.get("pageNum").toString();
        List<BookType> list = bookService.getBookType(typeName,pageSize,pageNum);
        if(list == null){
            return new CommonResponse(ProjectConstants.ERROR_CODE,"????????????",null);
        }
        map.put("list",list);
        map.put("total",0);
        return new CommonResponse(ProjectConstants.SUCCESS_CODE,"????????????",map);
    }

    /**
     * ??????id??????????????????
     */
    @PostMapping("/getBookTypeById")
    public CommonResponse getBookTypeById(@RequestBody Map<String,Object> map, HttpSession session){
        String id = map.get("id")==null?"":map.get("id").toString();
        BookType bookType = bookService.getBookTypeById(id);
        if (bookType == null) {
            return new CommonResponse(ProjectConstants.ERROR_CODE,"????????????????????????",null);
        }


        return new CommonResponse(ProjectConstants.SUCCESS_CODE,"????????????",bookType);
    }

    /**
     * ????????????
     */
    @PostMapping("/addBookType")
    public CommonResponse addBookType(@RequestBody Map<String,Object> map, HttpSession session){
        String typeName = map.get("typeName")==null?"":map.get("typeName").toString();
        BookType bookType = new BookType();
        bookType.setTypeName(typeName);
        int count = bookService.addBookType(bookType);
        System.out.println(count);
        if (count != 1) {
            return new CommonResponse(ProjectConstants.ERROR_CODE,"????????????????????????",null);
        }
        return new CommonResponse(ProjectConstants.SUCCESS_CODE,"????????????????????????",null);
    }

    /**
     * ????????????
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
            return new CommonResponse(ProjectConstants.ERROR_CODE,"??????????????????",null);
        }
        return new CommonResponse(ProjectConstants.SUCCESS_CODE,"??????????????????",null);
    }

    /**
     * ????????????
     */
    @PostMapping("/deleteBookType")
    public CommonResponse deleteBookType(@RequestBody Map<String,Object> map, HttpSession session){
        String id = map.get("id")==null?" ":map.get("id").toString();
        int count = bookService.deleteBookType(id);
        if (count != 1) {
            return new CommonResponse(ProjectConstants.ERROR_CODE,"??????????????????",null);
        }
        return new CommonResponse(ProjectConstants.SUCCESS_CODE,"??????????????????",null);
    }

    /**
     * ??????????????????
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

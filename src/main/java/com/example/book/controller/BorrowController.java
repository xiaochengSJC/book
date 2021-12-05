package com.example.book.controller;


import com.example.book.config.CommonResponse;
import com.example.book.config.ProjectConstants;
import com.example.book.model.Book;
import com.example.book.model.BookType;
import com.example.book.model.Borrow;
import com.example.book.model.User;
import com.example.book.service.BookService;
import com.example.book.service.UserService;
import com.github.pagehelper.PageHelper;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/borrow")
public class BorrowController {

    @Autowired
    BookService bookService;

    @Autowired
    UserService userService;

    /**
     * 管理员查询借阅记录
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/getBorrowRecord")
    public CommonResponse getBorrowRecord(@RequestBody Map<String,Object> map, HttpSession session){
        String typeId = map.get("typeId")==null?"":map.get("typeId").toString();
        String bookName = map.get("bookName")==null?"":map.get("bookName").toString();
        String userName = map.get("userName")==null?"":map.get("userName").toString();
        String borrowTime = map.get("borrowTime")==null?"":map.get("borrowTime").toString();
        String realTime = map.get("realTime")==null?"":map.get("realTime").toString();
        String pageSize = map.get("pageSize")==null?"":map.get("pageSize").toString();
        String pageNum = map.get("pageNum")==null?"":map.get("pageNum").toString();
        List<Map<String,Object>> list = bookService.getRecord(typeId,bookName,borrowTime,userName,realTime,pageSize,pageNum);
        list.forEach(stringObjectMap -> {
            System.out.println(stringObjectMap);
            String status = stringObjectMap.get("status")==null?"":stringObjectMap.get("status").toString();
            if("0".equals(status)){
                stringObjectMap.put("status","未逾期");
            }else{
                stringObjectMap.put("status","已逾期");
            }
            String state = stringObjectMap.get("state")==null?"":stringObjectMap.get("state").toString();
            if("0".equals(state)){
                stringObjectMap.put("state","已归还");
            }else{
                stringObjectMap.put("state","未归还");
            }
        });
        int total = 0;

        if(list == null){
            return new CommonResponse(ProjectConstants.ERROR_CODE,"查询失败",null);
        }
        map.put("list",list);
        map.put("total",total);
        return new CommonResponse(ProjectConstants.SUCCESS_CODE,"查询成功",map);
    }


    /**
     *  借书
     *  borrowBook
     */
    @PostMapping("/borrowBook")
    @Transactional
    public CommonResponse borrowBook(@RequestBody Map<String,Object> map, HttpSession session){
        try{
            String bookId = map.get("id")==null?" ":map.get("id").toString();
            String backTime = map.get("backTime")==null?" ":map.get("backTime").toString();
            String userId = map.get("userId")==null?" ":map.get("userId").toString();
            User user = userService.getUserInfoById(userId);
            int maxNum = user.getMaxNum();
            List<Map<String,Object>> list = bookService.getBorrowNum(userId);
            int num = 0;
            if(list.size()>0){
               num = Integer.parseInt(list.get(0).get("num").toString());
            }
            if(num>maxNum){
                return new CommonResponse(ProjectConstants.ERROR_CODE,"该读者所借书籍已超，不能再借",null);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println(backTime);
            String borrowTime = sdf.format(new Date());
            Borrow borrow = new Borrow();
            borrow.setBookId(bookId);
            borrow.setUserId(userId);
            borrow.setBackTime(backTime);
            borrow.setBorrowTime(borrowTime);
            int count =  bookService.addBorrowRecord(borrow);
            if(count != 1){
                return new CommonResponse(ProjectConstants.ERROR_CODE,"借书失败，请重试",null);
            }
            Book book = bookService.getBook(bookId);
            book.setStatus("1");
            bookService.updateBook(book);
            return new CommonResponse(ProjectConstants.SUCCESS_CODE,"借书成功",null);
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new CommonResponse(ProjectConstants.ERROR_CODE,"借书失败",null);
        }

    }

    /**
     *  读者查看借阅记录
     *  backBook
     */
    @PostMapping("/getBorrowList")
    public CommonResponse getBorrowList(@RequestBody Map<String,Object> map, HttpSession session){
        try{
            String typeId = map.get("typeId")==null?"":map.get("typeId").toString();
            String bookName = map.get("bookName")==null?"":map.get("bookName").toString();
            String userName = map.get("userName")==null?"":map.get("userName").toString();
            String borrowTime = map.get("borrowTime")==null?"":map.get("borrowTime").toString();
            String pageSize = map.get("pageSize")==null?"":map.get("pageSize").toString();
            String pageNum = map.get("pageNum")==null?"":map.get("pageNum").toString();
            List<Map<String,Object>> list = bookService.getBorrowList(typeId,bookName,borrowTime,userName,pageSize,pageNum);
            int total = 0;

            if(list == null){
                return new CommonResponse(ProjectConstants.ERROR_CODE,"查询失败",null);
            }
            map.put("list",list);
            map.put("total",total);
            return new CommonResponse(ProjectConstants.SUCCESS_CODE,"查询成功",map);
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResponse(ProjectConstants.ERROR_CODE,"查询失败",null);
        }

    }

    /**
     *  还书
     *  backBook
     */
    @PostMapping("/showBorrow")
    public CommonResponse showBorrow(@RequestBody Map<String,Object> map, HttpSession session){
        try{
            String id = map.get("id")==null?" ":map.get("id").toString();
            Borrow borrow = bookService.getBorrowById(id);
            if(borrow == null){
                return new CommonResponse(ProjectConstants.ERROR_CODE,"查询失败",null);
            }
            return new CommonResponse(ProjectConstants.SUCCESS_CODE,"查询成功",borrow);
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResponse(ProjectConstants.ERROR_CODE,"查询失败",null);
        }

    }

    /**
     *  还书
     *  backBook
     */
    @PostMapping("/backBook")
    public CommonResponse backBook(@RequestBody Map<String,Object> map, HttpSession session){
        try{
            String bookId = map.get("bookId")==null?" ":map.get("bookId").toString();
            String realTime = map.get("realTime")==null?" ":map.get("realTime").toString();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Borrow borrow = bookService.getBorrowById(bookId);
            String backTime = borrow.getBackTime();
            long time1 = sdf.parse(realTime).getTime();
            long time2 = sdf.parse(backTime).getTime();
            String status = "";
            if(time1 > time2){
                status = "1";
            }else{
                status = "0";
            }
            borrow.setStatus(status);
            borrow.setRealTime(realTime);
            int count =  bookService.updateBorrowRecord(borrow);
            if(count != 1){
                return new CommonResponse(ProjectConstants.ERROR_CODE,"还书失败，请重试",null);
            }
            Book book = bookService.getBook(bookId);
            book.setStatus("0");
            bookService.updateBook(book);
            return new CommonResponse(ProjectConstants.SUCCESS_CODE,"还书成功",null);
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResponse(ProjectConstants.ERROR_CODE,"还书失败",null);
        }

    }

}

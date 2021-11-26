package com.example.book.controller;

import com.example.book.config.CommonResponse;
import com.example.book.config.ProjectConstants;
import com.example.book.model.User;
import com.example.book.service.UserService;
import com.example.book.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin//支持跨域
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 查询读者信息
     */

    @PostMapping("/getUserInfo")
    @ResponseBody
    //@CrossOrigin
    public CommonResponse getUserInfo(@RequestBody Map<String,Object> map, HttpSession session) {
        String keyword = map.get("keyword")==null?"":map.get("keyword").toString();
        String pageSize = map.get("pageSize")==null?"":map.get("pageSize").toString();
        String pageNum = map.get("pageNum")==null?"":map.get("pageNum").toString();
        List<User> list = userService.selectUserInfo(keyword,pageSize,pageNum);
        int total =userService.totalUser(keyword,pageSize,pageNum);
        map.put("list",list);
        map.put("total",total);
        if (list == null) {
            return new CommonResponse(ProjectConstants.ERROR_CODE,"查询失败，请重试",null);
        }
        session.setAttribute("userList", list);
        return new CommonResponse(ProjectConstants.SUCCESS_CODE,"查询成功",map);
    }

    /**
     * 添加读者信息
     */

    @PostMapping("/addUserInfo")
    @ResponseBody
    //@CrossOrigin
    public CommonResponse addUserInfo(@RequestBody Map<String,Object> map, HttpSession session) {
        String userName = map.get("userName")==null?"":map.get("userName").toString();
        String name = map.get("name")==null?"":map.get("name").toString();
        String password = map.get("password")==null?"":map.get("password").toString();
        String email = map.get("email")==null?"":map.get("email").toString();
        String phone = map.get("phone")==null?"":map.get("phone").toString();
        String lendNum = map.get("lendNum")==null?"":map.get("lendNum").toString();
        String maxNum = map.get("maxNum")==null?"":map.get("maxNum").toString();
        if(" ".equals(lendNum)){
            lendNum = "0";
        }
        if(" ".equals(maxNum)){
            maxNum = "0";
        }
        // 验证用户名是否已经注册
        User exsitUser = userService.selectInfoByName(userName);
        if (exsitUser != null) {
            return new CommonResponse(ProjectConstants.ERROR_CODE,"该账号已存在",null);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(new Date());
        User user = new User();
        user.setUserName(userName);
        user.setName(name);
        user.setPassword(Md5Util.MD5(password));
        user.setEmail(email);
        user.setPhone(phone);
        user.setRoleId(1);
        user.setLendNum(Integer.parseInt(lendNum));
        user.setMaxNum(Integer.parseInt(maxNum));
        user.setCreateTime(format);
        int count = userService.addUser(user);
        System.out.println(count);
        if (count != 1) {
            return new CommonResponse(ProjectConstants.ERROR_CODE,"添加用户失败",null);
        }
        return new CommonResponse(ProjectConstants.SUCCESS_CODE,"添加成功",null);
    }

    /**
     * 查询读者信息
     */

    @PostMapping("/getUserInfoById")
    @ResponseBody
    //@CrossOrigin
    public CommonResponse getUserById(@RequestBody Map<String,Object> map, HttpSession session) {
        String id = map.get("id")==null?" ":map.get("id").toString();
        User user = userService.getUserInfoById(id);
        if (user == null) {
            return new CommonResponse(ProjectConstants.ERROR_CODE,"查询读者信息失败",null);
        }
        return new CommonResponse(ProjectConstants.SUCCESS_CODE,"查询成功",user);
    }

    /**
     * 修改读者信息
     */

    @PostMapping("/editUserInfo")
    @ResponseBody
    //@CrossOrigin
    public CommonResponse editUserInfo(@RequestBody Map<String,Object> map, HttpSession session) {
        String id = map.get("id")==null?"":map.get("id").toString();
        String userName = map.get("userName")==null?"":map.get("userName").toString();
        String name = map.get("name")==null?"":map.get("name").toString();
        String password = map.get("password")==null?"":map.get("password").toString();
        String email = map.get("email")==null?"":map.get("email").toString();
        String phone = map.get("phone")==null?"":map.get("phone").toString();
        String lendNum = map.get("lendNum")==null?"":map.get("lendNum").toString();
        String maxNum = map.get("maxNum")==null?"":map.get("maxNum").toString();
        if(" ".equals(lendNum)){
            lendNum = "0";
        }
        if(" ".equals(maxNum)){
            maxNum = "0";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(new Date());
        User user = new User();
        user.setId(Integer.parseInt(id));
        user.setUserName(userName);
        user.setName(name);
        user.setPassword(Md5Util.MD5(password));
        user.setEmail(email);
        user.setPhone(phone);
        user.setRoleId(1);
        user.setLendNum(Integer.parseInt(lendNum));
        user.setMaxNum(Integer.parseInt(maxNum));
        user.setCreateTime(format);
        int count = userService.editUser(user);
        System.out.println(count);
        if (count != 1) {
            return new CommonResponse(ProjectConstants.ERROR_CODE,"修改失败",null);
        }
        return new CommonResponse(ProjectConstants.SUCCESS_CODE,"修改成功",null);
    }

    /**
     * 删除读者信息
     */

    @PostMapping("/deleteUserInfo")
    @ResponseBody
    //@CrossOrigin
    public CommonResponse deleteUserInfo(@RequestBody Map<String,Object> map, HttpSession session) {
        String id = map.get("id")==null?" ":map.get("id").toString();
        int count = userService.deleteUser(id);
        if (count != 1) {
            return new CommonResponse(ProjectConstants.ERROR_CODE,"删除用户失败",null);
        }
        return new CommonResponse(ProjectConstants.SUCCESS_CODE,"删除成功",null);
    }
}

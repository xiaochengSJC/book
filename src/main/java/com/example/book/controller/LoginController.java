package com.example.book.controller;

import com.example.book.config.CommonResponse;
import com.example.book.config.ProjectConstants;
import com.example.book.model.Menu;
import com.example.book.model.User;
import com.example.book.service.MenuService;
import com.example.book.service.UserService;
import com.example.book.utils.Md5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户注册登录页面
 *        @CrossOrigin("http://127.0.0.1:8848")
 *    public String login() {
 * 		return "/login";
 *    }
 */
@RestController
@CrossOrigin//支持跨域
@RequestMapping("/user")
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * 用户注册
     */
    @PostMapping("/register")
    @ResponseBody
    public CommonResponse register(@RequestBody Map<String,Object> map,HttpSession session) {

        String userName = map.get("userName")==null?" ":map.get("userName").toString();
        String name = map.get("name")==null?" ":map.get("name").toString();
        String password = map.get("password")==null?" ":map.get("password").toString();
        String email = map.get("email")==null?" ":map.get("email").toString();
        String phone = map.get("phone")==null?" ":map.get("phone").toString();
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            return new CommonResponse(ProjectConstants.ERROR_CODE,"账号和密码不能为空",null);
        }
        // 验证用户名是否已经注册
        User exsitUser = userService.selectInfoByName(userName);
        if (exsitUser != null) {
            return new CommonResponse(ProjectConstants.ERROR_CODE,"该账号已存在",null);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(new Date());
        int roleId = 1;
        if("admin".equals(userName)){
            roleId = 0;
        }
        User user = new User();
        user.setUserName(userName);
        user.setName(name);
        user.setPassword(Md5Util.MD5(password));
        user.setEmail(email);
        user.setPhone(phone);
        user.setRoleId(roleId);
        user.setLendNum(0);
        user.setMaxNum(0);
        user.setCreateTime(format);
        int count = userService.addUser(user);
        System.out.println(count);
        if (count != 1) {
            return new CommonResponse(ProjectConstants.ERROR_CODE,"注册失败",null);
        }
        return new CommonResponse(ProjectConstants.SUCCESS_CODE,"注册成功",null);
    }

    /**
     * 用户登录
     */

    @PostMapping("/login")
    @ResponseBody
    //@CrossOrigin
    public CommonResponse login(@RequestBody Map<String,Object> map,HttpSession session) {
        String userName = map.get("userName")==null?" ":map.get("userName").toString();
        String password = map.get("password")==null?" ":map.get("password").toString();
        User exsitUser = userService.selectInfoByName(userName);
        if (exsitUser == null) {
            return new CommonResponse(ProjectConstants.ERROR_CODE,"该账号未注册",null);
        }
        String pwd =Md5Util.MD5(password);
        System.out.println(pwd);
        if (!exsitUser.getPassword().equals(pwd)) {
            return new CommonResponse(ProjectConstants.ERROR_CODE,"密码错误,请重新输入",null);
        }
        session.setAttribute("loginUser", exsitUser);
        return new CommonResponse(ProjectConstants.SUCCESS_CODE,"登录成功",exsitUser);
    }

    /**
     * 获取主页菜单
     */
    @PostMapping("/getMenu")
    @ResponseBody
    //@CrossOrigin
    public CommonResponse getMenu(@RequestBody Map<String,Object> map,HttpSession session) {
       try{
           String roleId = map.get("roleId")==null?" ":map.get("roleId").toString();
           if(" ".equals(roleId)){
               roleId = "0";
           }
           List<Menu> list = menuService.getMenuByRoleId(roleId);
           list.forEach(menu -> {
               int id = menu.getId();
               List<Menu> list2 = menuService.getMenuById(id);
               menu.setList(list2);
           });
           return new CommonResponse(ProjectConstants.SUCCESS_CODE,"查询成功",list);
       }catch (Exception e){
           e.printStackTrace();
           return new CommonResponse(ProjectConstants.SUCCESS_CODE,"查询失败",null);
       }

    }

}

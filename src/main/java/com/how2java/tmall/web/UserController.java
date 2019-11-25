package com.how2java.tmall.web;
import com.how2java.tmall.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.how2java.tmall.pojo.User;
import com.how2java.tmall.service.UserService;
import com.how2java.tmall.util.Page4Navigator;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;

@RestController
public class UserController {
	@Autowired UserService userService;

    @GetMapping("/users")
    public Page4Navigator<User> list(@RequestParam(value = "start", defaultValue = "0") int start,@RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
    	start = start<0?0:start;
    	Page4Navigator<User> page = userService.list(start,size,5);
        return page;
    }
    /**
     * 登录验证
     */
    @PostMapping("/forelogin")
    public Object login(@RequestBody User userParam, HttpSession session){
        String name = userParam.getName();
        name = HtmlUtils.htmlEscape(name);
        User user = userService.get(name, userParam.getPassword());
        if(null == user){
            String message = "账号密码错误";
            return Result.fail(message);
        }else {
            session.setAttribute("user",user);
            return Result.success();
        }
    }
    /**
     * 注册
     */
    @PostMapping("/foreregister")
    public Object register(@RequestBody User user){
        String name = user.getName();
        String password = user.getPassword();
        name=  HtmlUtils.htmlEscape(name);
        user.setName(name);
        boolean exist = userService.isExist(name);
        if(exist){
            String message = "用户名已经被使用,不能使用";
            return Result.fail(message);
        }
        user.setPassword(password);
        userService.add(user);
        return  Result.success();
    }
}


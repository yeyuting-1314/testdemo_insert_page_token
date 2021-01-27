package testdemo.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testdemo.base.Result;
import testdemo.system.dao.UserMapper;
import testdemo.system.dto.User;
import testdemo.system.service.UserService;
import testdemo.util.PageBean;
import testdemo.util.Results;
import testdemo.util.TokenUtil;
import testdemo.util.interceptor.AuthenticationInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper ;

    @Autowired
    TokenUtil tokenUtil ;

    @Override
    public User selectOne(int id){
        return userMapper.selectOne(id) ;
    }

    /*
     * 根据用户名查询某一条数据
     * */
    public User selectByName(String name){
        return userMapper.selectByName(name) ;
    }

    @Override
    public List<User> select(){
        return userMapper.select();
    }

    @Override
    public Integer insertOne(User user){
        return userMapper.insertOne(user);
    }

    @Override
    public Integer insertMany(List<User> userList){
        return userMapper.insertMany(userList) ;
    }

    public Integer updateById(User user){
        return userMapper.updateById(user ) ;
    }
    @Override
    public Integer deleteOne(int id){
        return userMapper.deleteOne(id) ;
    }

    @Override
    public List<User> selectForPage1(int startIndex , int pageSize){
        return userMapper.selectForPage1(startIndex , pageSize) ;

    }

    public List<User> selectForPage2(Map<String, Object> map){

        return userMapper.selectForPage2(map);
    }

    public Integer selectCount(){
        return userMapper.selectCount() ;
    }

    public List<User> selectForPage3(PageBean pageBean){
        return userMapper.selectForPage3(pageBean) ;
    }

    public List<User> selectForPage4(Map<String, Object> map){
        return userMapper.selectForPage4(map) ;
    }

    public Integer selectCount2(String keywords){
        return userMapper.selectCount2(keywords) ;
    }

    public Result loginCheck(User user , HttpServletResponse response){
        User user1 = userMapper.selectByName(user.getUserName()) ;
        if(user1 == null ){
            //response.sendRedirect("/login");
            return Results.failure("用户不存在,") ;
        }
        if(!user1.getPassword().equals(user.getPassword())){
            return Results.failure("密码输入错误") ;
        }
        String token = tokenUtil.generateToken(user1) ;
        System.out.println("token:" + token);
        user.setToken(token);

        //修改 生成的token存到redis里面
        //token返回给用户 用户新建一个属性 用于存放token

        // 设置cookie的值
        Cookie cookie = new Cookie("token" , token) ;

        // 作用域：为”/“时，以在webapp文件夹下的所有应用共享cookie
        cookie.setPath("/");
        response.addCookie(cookie);
        System.out.println("cookie:"+cookie);

        //以key value形式 将user（包括token）和code返回给前端 Result方法参数变一下
        return Results.successWithData(user) ;


    }
}

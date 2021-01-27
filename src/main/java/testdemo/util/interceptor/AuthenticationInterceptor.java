package testdemo.util.interceptor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import testdemo.util.TokenUtil;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//自定义拦截器类

public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired TokenUtil tokenUtil ;

    @Override
    public boolean preHandle (HttpServletRequest request ,
                              HttpServletResponse response , Object o )throws Exception{
        //黑名单  有token  没有token
        //从request请求头里面获取
        String token = tokenUtil.getToken(request) ; //检查请求中是否存在token ， 如果不存在就直接跳转到登陆页面
        if(StringUtil.isEmpty(token)){
            System.out.println("不存在token");
            response.sendRedirect("/login");

            //也要return一个result
            return false ;
        }
        return true ;
    }


}

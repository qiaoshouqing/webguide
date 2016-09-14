package top.glimpse.webguide.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import top.glimpse.webguide.data.UserRepository;
import top.glimpse.webguide.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by joyce on 16-8-12.
 */

public class UserInterceptor extends HandlerInterceptorAdapter {


    private String domin = "http://115.28.46.65:8080/webguide";
    private String local = "http://localhost:8080/webguide";


    @Autowired
    private UserRepository userRepository;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (
                request.getRequestURL().toString().equals(domin + "/login") ||
                request.getRequestURL().toString().equals(domin + "/checkLogin") ||
                request.getRequestURL().toString().equals(domin + "/home_android") ||
                request.getRequestURL().toString().equals(domin + "/home_android") ||
                request.getRequestURL().toString().equals(domin + "/isNet") ||
                request.getRequestURL().toString().equals(domin + "/getPhone") ||
                request.getRequestURL().toString().equals(domin + "/getVersionCode") ||

                request.getRequestURL().toString().equals(local + "/login") ||
                request.getRequestURL().toString().equals(local + "/checkLogin") ||
                request.getRequestURL().toString().equals(local + "/home_android") ||
                request.getRequestURL().toString().equals(local + "/home_android") ||
                request.getRequestURL().toString().equals(local + "/isNet") ||
                request.getRequestURL().toString().equals(local + "/getPhone") ||
                request.getRequestURL().toString().equals(local + "/getVersionCode") ||





                request.getSession().getAttribute("uid") != null) {
            return true;
        } else {
            response.sendRedirect("/webguide/login");
            return false;
        }
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if(request.getSession().getAttribute("uid") != null) {
            User user = userRepository.getUser((int)request.getSession().getAttribute("uid"));
            modelAndView.getModel().put("user",user);
        }
        else {
            modelAndView.getModel().put("user",null);

        }
    }

}

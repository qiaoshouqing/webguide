package top.glimpse.webguide.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import top.glimpse.webguide.data.UserRepository;
import top.glimpse.webguide.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Created by joyce on 16-8-4.
 *
 */
@Controller
public class UserController {


    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //前往登陆页
    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String login(Model model) {

        return "login";
    }

    //检查登陆状态
    @RequestMapping(value="/checkLogin", method = RequestMethod.POST)
    @ResponseBody
    public String checkLogin(HttpServletRequest request, Model model, User user) {

        System.out.println(user.getName() + "__" + user.getPassword());

        User result = userRepository.login(user);

        if (result==null) {
            return "fail";
        }
        else if (result.getName().equals(user.getName()) && result.getPassword().equals(user.getPassword()) ) {
            request.getSession().setAttribute("uid", result.getUid());
            return "sucess";
        }
        return "fail";

    }

    //注册
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public User signup(User user) {

        return userRepository.signup(user);

    }

    //退出
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request, User user) {

        request.getSession().removeAttribute("uid");

        return new ModelAndView("redirect:/login");

    }


    //进入修改页
    @RequestMapping(value = "/changePass", method = RequestMethod.GET)
    public String changePass(HttpServletRequest request) {

        return "changePass";
    }

    //产生修改
    @RequestMapping(value = "/changePass", method = RequestMethod.POST)
    @ResponseBody
    public String changePassword(HttpServletRequest request,  String old, String password) {

        int uid = (int)request.getSession().getAttribute("uid");
        String password_database = userRepository.getPassword(uid);
        if(password_database.equals(old)) {
            userRepository.setPassword(new User(uid, password));
            return "sucess";
        }
        else {
            return "fail";
        }

    }

}

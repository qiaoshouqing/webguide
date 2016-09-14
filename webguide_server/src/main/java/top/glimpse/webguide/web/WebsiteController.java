package top.glimpse.webguide.web;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import top.glimpse.webguide.data.WebsiteRepository;
import top.glimpse.webguide.entity.Website;
import top.glimpse.webguide.util.HostInfo;
import top.glimpse.webguide.util.MD5;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by joyce on 16-8-14.
 */
@Controller
public class WebsiteController {


//    public static String domain = HostInfo.getHostIp() + ":8080/webguide/";
    public static String domain = "http://115.28.46.65" + ":8080/webguide";


    private WebsiteRepository websiteRepository;


    @Autowired
    public WebsiteController(WebsiteRepository websiteRepository) {
        this.websiteRepository = websiteRepository;
    }


/******************************android端*********************************************/

//    android端以json传值
    @RequestMapping(value = "/home_android", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public List<Website> home_android() {

        List<Website> websites = websiteRepository.getAll();
        for(int i = 0;i < websites.size();i++) {
            websites.get(i).setLogo(domain + "/" + websites.get(i).getLogo());
        }

        return websites;
    }



/******************************后台管理系统*********************************************/
//    主页
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {

        List<Website> websites = websiteRepository.getAll();
        for(int i = 0;i < websites.size();i++) {
            websites.get(i).setLogo(websites.get(i).getLogo());
        }

        model.addAttribute("websites", websites);

        return "index";
    }


//  前往添加页面
    @RequestMapping(value = "/editor/0", method = RequestMethod.GET)
    public String editor(Model model) {

        model.addAttribute("website",  null);

        return "editor";
    }


//  前往编辑页面
    @RequestMapping(value = "/gotoupdate/{wid}", method = RequestMethod.GET)
    public String editor(Model model,
                         @PathVariable int wid) {


        Website website = websiteRepository.getOne(wid);
        website.setLogo(website.getLogo());
        model.addAttribute("website", website);

        return "editor";
    }


    //上传图片
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public String upload(HttpServletRequest request, @RequestParam("pic")CommonsMultipartFile file) {
        System.out.println("上传");
        System.out.println(file.getOriginalFilename());
        System.out.println(System.currentTimeMillis() + ".png");
        System.out.println(request.getSession().getServletContext().getRealPath("/WEB-INF/resources/image/"));

        String fileName = System.currentTimeMillis() + ".png";
        try {
            FileUtils.writeByteArrayToFile(new File(request.getSession().getServletContext().getRealPath("/WEB-INF/resources/image/") + fileName), file.getBytes());
            return "resources/image/" + fileName;
        } catch(IOException e) {
            e.printStackTrace();
            return "上传失败";
        }
    }




//  发布
    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public ModelAndView post(Model model, Website website) {

        websiteRepository.postOne(website);

        return new ModelAndView("redirect:/");
    }

//  更新
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(Model model,Website website) {

        System.out.println(website + "\n\n\n");

        websiteRepository.updateOne(website);

        return new ModelAndView("redirect:/");
    }

//    删除
    @RequestMapping(value = "/delete/{wid}", method = RequestMethod.GET)
    public ModelAndView delete(Model model,
                         @PathVariable int wid) {

        websiteRepository.deleteOne(wid);

        return new ModelAndView("redirect:/");
    }


    //    session测试
    @RequestMapping(value = "/session", method = RequestMethod.GET)
    @ResponseBody
    public String session(HttpServletRequest request) {

        request.getSession().setAttribute("hello","hello");
        String hello = (String) request.getSession().getAttribute("hello");

        return hello;
    }




    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(Model model) {


        return "search";
    }

    //    获取激活码
    @RequestMapping(value = "/getActiveCode/{deviceId}", method = RequestMethod.GET)
    @ResponseBody
    public String getDeviceId(HttpServletRequest request,
                              @PathVariable String deviceId) {

        String result =  MD5.GetMD5Code(deviceId + "sprite");
        String acticeCode = "";
        for(int i = 0;i < result.length();i += 4) {
            if(i + 4 >= result.length()) {
                acticeCode += result.substring(i, i + 4);
            }
            else {
                acticeCode += result.substring(i, i + 4) + "-";
            }
        }
        return acticeCode;

    }







}

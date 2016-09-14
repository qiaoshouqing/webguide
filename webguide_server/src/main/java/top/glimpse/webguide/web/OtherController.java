package top.glimpse.webguide.web;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import top.glimpse.webguide.data.OtherRepository;
import top.glimpse.webguide.data.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

import static top.glimpse.webguide.web.WebsiteController.domain;

/**
 * Created by joyce on 16-8-17.
 */
@Controller
public class OtherController {

    private OtherRepository otherRepository;

    @Autowired
    public OtherController(OtherRepository otherRepository) {
        this.otherRepository = otherRepository;
    }

    //检查网络是否连接
    @RequestMapping(value = "/isNet", method = RequestMethod.GET)
    @ResponseBody
    public String isNet() {

        return "yes";
    }

    //    进入修改客服电话页面
    @RequestMapping(value = "/goChangePhone", method = RequestMethod.GET)
    public String goChangePhone(HttpServletRequest request, Model model) {

        String phone = otherRepository.getValue("phone");
        model.addAttribute("current", phone);

        return "changePhone";
    }


    //    修改客服电话
    @RequestMapping(value = "/changePhone", method = RequestMethod.POST)
    @ResponseBody
    public String changePhone(HttpServletRequest request,String phone) {


        otherRepository.setValue("phone", phone);

        return phone;
    }

    //    获取客服电话
    @RequestMapping(value = "/getPhone", method = RequestMethod.GET)
    @ResponseBody
    public String getPhone(HttpServletRequest request) {

        return otherRepository.getValue("phone");
    }



    //前往上传应用页面
    @RequestMapping(value = "/goUploadApp",method = RequestMethod.GET)
    public String goUploadApp(HttpServletRequest request) {


        return "uploadApp";

    }

    //上传应用
    @RequestMapping(value = "/uploadApp",method = RequestMethod.POST)
    @ResponseBody
    public String uploadApp(HttpServletRequest request, @RequestParam("apk")CommonsMultipartFile file,String versionCode) {

        String fileName = "webguide.apk";

        System.out.println(versionCode);
        try {
            FileUtils.writeByteArrayToFile(new File(request.getSession().getServletContext().getRealPath("/WEB-INF/resources/app/") + fileName), file.getBytes());

            otherRepository.setValue("version", versionCode);

            return "上传成功";
        } catch(IOException e) {
            e.printStackTrace();
            return "上传失败";
        }
    }



    //    设置新的版本号
    @RequestMapping(value = "/uploadVersionCode/{versionCode}", method = RequestMethod.GET)
    @ResponseBody
    public String uploadVersionCode(HttpServletRequest request,
                                    @PathVariable String versionCode) {

        otherRepository.setValue("version", versionCode);


        System.out.println("sucess");
        return "sucess";
    }


    //    获取版本号
    @RequestMapping(value = "/getVersionCode", method = RequestMethod.GET)
    @ResponseBody
    public String getVersionCode(HttpServletRequest request) {

        return otherRepository.getValue("version");
    }


}

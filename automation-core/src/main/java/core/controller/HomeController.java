package core.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * home index
 */
@Controller
public class HomeController {

    @RequestMapping(path = "/")
    public void home(HttpServletResponse response) throws IOException {
        ClassPathResource cpr = new ClassPathResource("templates/index.html");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        OutputStream os = response.getOutputStream();
        byte[] indexHtml = FileCopyUtils.copyToByteArray(cpr.getInputStream());
        os.write(indexHtml);
    }


    @RequestMapping(path = "/Tasks")
    public void tasks(HttpServletResponse response) throws IOException {
        ClassPathResource cpr = new ClassPathResource("templates/index.html");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        OutputStream os = response.getOutputStream();
        byte[] indexHtml = FileCopyUtils.copyToByteArray(cpr.getInputStream());
        os.write(indexHtml);
    }
}

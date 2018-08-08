package com.kingboy.controller.code;

import com.kingboy.common.tools.utils.VerifyCodeUtils;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author kingboy--KingBoyWorld@163.com
 * @date 2017/12/1 上午1:52
 * @desc 验证码.
 */
@RestController
public class ImageCodeController {

    private static final String IMAGE_CODE = "imageCode";

    private static final String IMAGE_CODE_TIME = "imageCodeTime";

    SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @GetMapping("/code/image")
    public void getImageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //2.放入Session
        String code = VerifyCodeUtils.generateVerifyCode(4);
        sessionStrategy.setAttribute(new ServletWebRequest(request), IMAGE_CODE, code);
        sessionStrategy.setAttribute(new ServletWebRequest(request), IMAGE_CODE_TIME, LocalDateTime.now().plusSeconds(60));
        //3. 返回图片流
        VerifyCodeUtils.outputImage(100, 30, response.getOutputStream(), code);

    }

}

package me.hhjeong.springbootcms.main.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @GetMapping(name = "/")
    public @ResponseBody
    String main() {
        return "this is main page.";
    }

}

package com.meteor.whitelisttest.controller;

import com.meteor.whitelist.annotation.AWhiteList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class WhiteListController {
    private static Logger logger = Logger.getLogger(WhiteListController.class.getName());
    @GetMapping("/hello")
    @AWhiteList(userName = "userName", returnValue = "{\"data\":\"用户不在白名单内\"}")
    public Object hello(@RequestParam String userName){
        logger.info(userName);
        return "用户通过白名单";
    }
}

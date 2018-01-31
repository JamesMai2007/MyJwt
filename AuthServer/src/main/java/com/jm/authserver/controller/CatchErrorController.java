package com.jm.authserver.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;

@Controller
public class CatchErrorController implements ErrorController {

    @Override
    public String getErrorPath() {
        System.out.println("========== error");
        return "login";
    }
}

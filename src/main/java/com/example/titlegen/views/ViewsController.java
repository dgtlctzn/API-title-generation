package com.example.titlegen.views;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class ViewsController {

    @GetMapping(path = "/")
    public String index() {
        return "index.html";
    }
}

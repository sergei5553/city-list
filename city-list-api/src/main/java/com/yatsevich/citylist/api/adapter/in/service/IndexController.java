package com.yatsevich.citylist.api.adapter.in.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

  @RequestMapping("/")
  public String index() {
    return "forward:/public/index.html";
  }

}

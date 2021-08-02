package com.richard.srblog.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * the default routing controller , used to load frontend sap only
 * @class IndexController 
 * @author Richard
 *
 */
@Controller
@RequestMapping(value = "/")
public class IndexController {
	
	@RequestMapping(value="/index")
	public String index() {
		return "index";
	}
}

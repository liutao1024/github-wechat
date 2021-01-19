package org.spring.boot.wechat.controller;

import org.spring.boot.wechat.menu.MenuMain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/menue")
public class MenuController {
	private MenuMain menue;

	@Autowired
	public MenuController(MenuMain menue) {
		super();
		this.menue = menue;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public void home() {
		menue.createMenu();
	}
}

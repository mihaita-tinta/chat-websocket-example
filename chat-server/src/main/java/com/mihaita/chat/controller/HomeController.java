package com.mihaita.chat.controller;

import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

	@RequestMapping("/")
	public String home() {
        return "redirect:/index.html";
	}
	

	@RequestMapping(value="/android-debug.apk", produces={MediaType.APPLICATION_OCTET_STREAM_VALUE})
	public @ResponseBody FileSystemResource apk() throws IOException {
        return new FileSystemResource(new ClassPathResource("android-debug.apk").getFile());
	}
}

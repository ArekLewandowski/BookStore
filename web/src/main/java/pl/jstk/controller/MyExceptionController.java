package pl.jstk.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MyExceptionController implements ErrorController {

	@RequestMapping(value = "/error")
	public ModelAndView noAccess() {
		ModelAndView modelAndView = new ModelAndView("403");
		String error = "You shall not pass";
		modelAndView.addObject("error", error);
		return modelAndView;
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}

}

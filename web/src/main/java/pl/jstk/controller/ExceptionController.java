package pl.jstk.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ExceptionController {

	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public ModelAndView noAccess() {
		ModelAndView modelAndView = new ModelAndView("403");
		String error = "You shall not pass";
		modelAndView.addObject("error", error);
		return modelAndView;
	}

}

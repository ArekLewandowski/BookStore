package pl.jstk.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

@RestController
public class SearchController {

	@Autowired
	private BookService bookService;

	@SuppressWarnings("null")
	@RequestMapping(value = "books/search/findbook", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView searchBook(@RequestParam(required = false) String title,
			@RequestParam(required = false) String authors) {
		ModelAndView modelAndView = new ModelAndView("findbook");
		List<BookTo> lista = new ArrayList<>();
		List<BookTo> books = new ArrayList<>();
		if (!isEmpty(authors) && !isEmpty(title)) {
			lista = bookService.findBooksByAuthor(authors);
			for (BookTo bookTo2 : lista) {
				if (title.equals(bookTo2.getTitle())) {
					books.add(bookTo2);
				}
			}
		} else if (isEmpty(title)) {
			books = bookService.findBooksByAuthor(authors);
		} else {
			books = bookService.findBooksByTitle(title);
		}
		if (books.isEmpty()) {
			ModelAndView noBooksMAV = new ModelAndView("nobook");
			return noBooksMAV;
		}
		modelAndView.addObject("bookList", books);
		return modelAndView;
	}
	
	public static boolean isEmpty(final String string) {
		return string == null || string.trim().isEmpty();
	}
}

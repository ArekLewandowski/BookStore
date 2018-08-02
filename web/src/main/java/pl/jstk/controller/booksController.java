package pl.jstk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

@RestController
public class BooksController {

	@Autowired
	private BookService bookService;

	@RequestMapping(value = "/books", method = RequestMethod.GET)
	public ModelAndView allBooks() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("bookList", bookService.findAllBooks());
		return modelAndView;
	}

	@RequestMapping(value = "/books/book{id}", method = RequestMethod.GET)
	public ModelAndView bookDetails(@RequestParam long id, ModelAndView modelAndView) {
		modelAndView = new ModelAndView("book");
		modelAndView.addObject("book", bookService.findBooksById(id));
		return modelAndView;
	}

	@RequestMapping(value = "/books/delete{id}", method = RequestMethod.GET)
	public ModelAndView bookDelete(@RequestParam long id, ModelAndView modelAndView) {
		modelAndView = new ModelAndView("delete");
		modelAndView.addObject("book", bookService.findBooksById(id));
		bookService.deleteBook(id);
		return modelAndView;
	}

	@RequestMapping(value = "/books/add", method = RequestMethod.GET)
	public ModelAndView addBook() {
		ModelAndView modelAndView = new ModelAndView("addBook");
		modelAndView.addObject("newBook", new BookTo());
		return modelAndView;
	}

	@RequestMapping(value = "/books/search", method = RequestMethod.GET)
	public ModelAndView searchBook() {
		ModelAndView modelAndView = new ModelAndView("searchBook");
		modelAndView.addObject("newBook", new BookTo());
		return modelAndView;
	}

	@RequestMapping(value = "/greeting", method = RequestMethod.POST)
	public ModelAndView greeting(@ModelAttribute("newBook") BookTo bookTo) {
		ModelAndView modelAndView = new ModelAndView("greeting");
		bookService.saveBook(bookTo);
		return modelAndView;
	}

}

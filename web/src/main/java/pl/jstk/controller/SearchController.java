package pl.jstk.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;



@RestController
public class SearchController {

	@Autowired
	private BookService bookService;
	
	@RequestMapping(value = "books/search/findbook", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView searchBook(@ModelAttribute("newBook") BookTo bookTo){
		ModelAndView modelAndView = new ModelAndView("findbook");
		String author = bookTo.getAuthors();
		String title = bookTo.getTitle();		
		List<BookTo> lista = new ArrayList<>();
		List<BookTo> books = new ArrayList<>();	
		lista = bookService.findAllBooks();
		for (BookTo bookTo2 : lista) {
//			if (author!=null && title!=null) {
//				if (bookTo2.getAuthors().equals(author) && bookTo2.getTitle().equals(title)) {
//							books.add(bookTo2);
//				}
//			}else if (author!=null) {
//				if (bookTo2.getAuthors().equals(author)) {
//					books.add(bookTo2);
//				}
//			}else if (title!=null) {
//				if (bookTo2.getTitle().equals(title)) {
//					books.add(bookTo2);
//				}
//			}else{
//				modelAndView.setViewName("nobook");
//			}
			
			if ((author!=null && bookTo2.getAuthors().equals(author)) ||
			(title!=null && bookTo2.getTitle().equals(title))) {
				books.add(bookTo2);
			}
		}
		
		modelAndView.addObject("bookList", books);
        return modelAndView;       
	}
}



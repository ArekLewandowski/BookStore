package pl.jstk.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.ModelAndView;

import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ControllerTests {
	
	@Autowired
	private BooksController booksController;
	
	@Mock 
	private BookService bookService;
	

	@Before
	public void setup(){
		List<BookTo> books = new ArrayList<>();
				books.add(new BookTo());
		Mockito.when(bookService.findAllBooks()).thenReturn(books);
	}
	@Test
	public void shouldShowBooks(){
			
		//given
		ModelAndView mav = booksController.allBooks();
		
		//when
		String name = mav.getViewName();
		
		//then
		assertEquals("books", name);
	}
}
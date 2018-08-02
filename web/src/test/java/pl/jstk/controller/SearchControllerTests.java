package pl.jstk.controller;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration
public class SearchControllerTests {

	private MockMvc mockMvc;

	@Mock
	private BookService bookService;

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private BooksController booksController;

	@Autowired
	private SearchController searchController;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(new BooksController()).build();
		MockitoAnnotations.initMocks(bookService);
		Mockito.reset(bookService);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
		ReflectionTestUtils.setField(booksController, "bookService", bookService);

	}

	@WithMockUser(username = "Arek", roles = { "ADMIN" })
	@Test
	public void testBookSearch() throws Exception {

		// given
		List<BookTo> books = new ArrayList<>();
		List<BookTo> book = new ArrayList<>();
		
		BookTo book1 = new BookTo();
		book1.setAuthors("Andrzej Sapkowski");
		book1.setId(1L);
		book1.setTitle("Narrenturm");
		
		BookTo book2 = new BookTo();
		book2.setAuthors("Andrzej Sapkowski");
		book2.setId(2L);
		book2.setTitle("Bozy bojownicy");
		
		books.add(book1);
		books.add(book2);
		
		Mockito.when(bookService.findBooksByAuthor("Andrzej Sapkowski")).thenReturn(books);
		
		book.add(book1);
		Mockito.when(bookService.findBooksByTitle("Narrenturm")).thenReturn(book);

		// when
		ResultActions resultActions = mockMvc.perform(get("/books/search/findbook?title=&authors=Andrzej+Sapkowski"));

		// then
		resultActions.andExpect(status().isOk()).andExpect(view().name("findbook")).andDo(print())
				.andExpect(content().string(containsString("")));

	}
	
	@WithMockUser(username = "Arek", roles = { "ADMIN" })
	@Test
	public void testBookSearchByTitle() throws Exception {

		// given
		List<BookTo> books = new ArrayList<>();
		List<BookTo> book = new ArrayList<>();
		
		BookTo book1 = new BookTo();
		book1.setAuthors("Andrzej Sapkowski");
		book1.setId(1L);
		book1.setTitle("Narrenturm");
		
		BookTo book2 = new BookTo();
		book2.setAuthors("Andrzej Sapkowski");
		book2.setId(2L);
		book2.setTitle("Bozy bojownicy");
		
		books.add(book1);
		books.add(book2);
		
		Mockito.when(bookService.findBooksByAuthor("Andrzej Sapkowski")).thenReturn(books);
		
		book.add(book1);
		Mockito.when(bookService.findBooksByTitle("Narrenturm")).thenReturn(book);

		// when
		ResultActions resultActions = mockMvc.perform(get("/books/search/findbook?title=Narrenturm&authors="));

		// then
		resultActions.andExpect(status().isOk()).andExpect(view().name("findbook")).andDo(print())
				.andExpect(content().string(containsString("")));

	}


	@WithMockUser(username = "Arek", roles = { "ADMIN" })
	@Test
	public void testNoBookSearch() throws Exception {

		// given
		List<BookTo> books = new ArrayList<>();
		Mockito.when(bookService.findBooksByAuthor("Andrzej Sapkowski")).thenReturn(books);

		// when
		ResultActions resultActions = mockMvc.perform(get("/books/search/findbook?title=&authors=Andrzej+Sapkowski"));

		// then
		resultActions.andExpect(status().isOk()).andExpect(view().name("nobook")).andDo(print())
				.andExpect(content().string(containsString("")));

	}

}

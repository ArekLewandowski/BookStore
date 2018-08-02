
package pl.jstk.controller;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
public class ControllerTest {

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
	public void testBooksPage() throws Exception {
		// given when
		List<BookTo> books = new ArrayList<>();
		books.add(new BookTo());
		Mockito.when(bookService.findAllBooks()).thenReturn(books);
		ResultActions resultActions = mockMvc.perform(get("/books"));
		// then
		resultActions.andExpect(status().isOk()).andExpect(view().name("books")).andDo(print())
				.andExpect(content().string(containsString("")));

	}


	@WithMockUser(username = "Arek", roles = { "ADMIN" })
	@Test
	public void testBookPage() throws Exception {
		// given when
		List<BookTo> books = new ArrayList<>();
		BookTo book = new BookTo();
		book.setAuthors("Andrzej Sapkowski");
		book.setId(1L);
		book.setTitle("Narrenturm");
		BookTo book2 = new BookTo();
		book2.setAuthors("Andrzej Sapkowski");
		book2.setId(2L);
		book2.setTitle("Bozy bojownicy");
		books.add(book);
		books.add(book2);
		Mockito.when(bookService.findBooksById(1)).thenReturn(book);
		ResultActions resultActions = mockMvc.perform(get("/books/book?id=1"));
		// then
		resultActions.andExpect(status().isOk()).andExpect(view().name("book")).andDo(print())
				.andExpect(content().string(containsString("")));

	}

	@WithMockUser(username = "Arek", roles = { "ADMIN" })
	@Test
	public void testFaildToGetBookPage() throws Exception {
		// given when
		List<BookTo> books = new ArrayList<>();
		BookTo book = new BookTo();
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
		Mockito.when(bookService.findBooksById(4)).thenReturn(book);
		ResultActions resultActions = mockMvc.perform(get("/books/book?id=4"));
		// then
		resultActions.andExpect(status().isOk()).andExpect(view().name("book")).andDo(print())
				.andExpect(content().string(containsString("")));

	}

	@WithMockUser(username = "Arek", roles = { "ADMIN" })
	@Test
	public void testBookDelete() throws Exception {
		// given
		List<BookTo> books = new ArrayList<>();
		BookTo book = new BookTo();
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
		Mockito.when(bookService.findBooksById(1)).thenReturn(book1);
		// when
		ResultActions resultActions = mockMvc.perform(get("/books/delete?id=1"));
		// then
		resultActions.andExpect(status().isOk()).andExpect(view().name("delete")).andDo(print())
				.andExpect(content().string(containsString("")));

	}

	@WithMockUser(username = "User", roles = { "USER" })
	@Test
	public void testBookDeleteByUser() throws Exception {
		// given
		List<BookTo> books = new ArrayList<>();
		BookTo book = new BookTo();
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
		Mockito.when(bookService.findBooksById(1)).thenReturn(book1);
		// when
		ResultActions resultActions = mockMvc.perform(get("/books/delete?id=1"));
		// then
		resultActions.andExpect(status().isForbidden());

	}

	@WithMockUser(username = "Arek", roles = { "ADMIN" })
	@Test
	public void testBookAddGreeting() throws Exception {
		// given		
		BookTo book1 = new BookTo();
		book1.setAuthors("Andrzej Sapkowski");
		book1.setId(1L);
		book1.setTitle("Narrenturm");	
		Mockito.when(bookService.saveBook(book1)).thenReturn(book1);
		// when
		ResultActions resultActions = mockMvc.perform(post("/greeting", book1));
		// then
		resultActions.andExpect(status().isOk()).andExpect(view().name("greeting")).andDo(print())
				.andExpect(content().string(containsString("")));

	}
	
	@WithMockUser(username = "Arek", roles = { "ADMIN" })
	@Test
	public void testBookAdd() throws Exception {
		// given		

		// when
		ResultActions resultActions = mockMvc.perform(get("/books/add"));
		// then
		resultActions.andExpect(status().isOk()).andExpect(view().name("addBook")).andDo(print())
				.andExpect(content().string(containsString("")));

	}
	
	@WithMockUser(username = "Arek", roles = { "ADMIN" })
	@Test
	public void testBookSearchPage() throws Exception {
		// given		

		// when
		ResultActions resultActions = mockMvc.perform(get("/books/search"));
		// then
		resultActions.andExpect(status().isOk()).andExpect(view().name("searchBook")).andDo(print())
				.andExpect(content().string(containsString("")));

	}

}

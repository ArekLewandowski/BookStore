package pl.jstk.controller;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration
public class LogControllerTests {

	private MockMvc mockMvc;

	@Mock
	private BookService bookService;

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private BooksController booksController;

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
	public void testLoginPage() throws Exception {
		// given
		// when
		
		ResultActions resultActions = mockMvc.perform(get("/login"));
		// then
		resultActions.andExpect(status().isOk()).andExpect(view().name("login")).andDo(print())
				.andExpect(content().string(containsString("")));

	}
	
	@WithMockUser(username = "Arek", roles = { "ADMIN" })
	@Test
	public void testLogoutPage() throws Exception {
		// given
		// when
		
		ResultActions resultActions = mockMvc.perform(get("/logout"));
		// then
		resultActions.andExpect(status().isFound());

	}

}

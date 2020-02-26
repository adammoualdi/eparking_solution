// package com.parkingapp.server;

// import org.junit.jupiter.api.Test;
// import static org.junit.Assert.*;

// import org.junit.Before;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.test.context.support.WithMockUser;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.ResultActions;
// import org.springframework.test.web.servlet.ResultMatcher;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;
// import org.springframework.web.context.WebApplicationContext;

// @SpringBootTest
// class DemoApplicationTests {

// 	@Autowired
// 	private AuthenticationManager authenticationManager;

// 	@Autowired
// 	private WebApplicationContext context;

// 	private MockMvc mvc;

// //     @Before
// //     public void setup() {
// //         mvc = MockMvcBuilders
// //           .webAppContextSetup(context)
// //           .apply(springSecurity())
// //           .build();
// //     }

// 	@Test
// 	void contextLoads() {
// 		System.out.println("TRUE");
// 		assertTrue(true);
// 	}

// 	@Test
// 	void correctLogin() {
// 		try {
// 			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("username1", "password123"));
// 		} catch (Exception e) {
// 			System.out.println(e);
// 			assertTrue(false);
// 		}
// 		assertTrue(true);
// 	}

// 	@Test
// 	void incorrectLogin() {
// 		try {
// 			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("", ""));
// 		} catch (Exception e) {
// 			System.out.println(e);
// 			assertTrue(true);
// 		}
// 	}

// 	@Test
// 	@WithMockUser(username = "user1", password = "pwd", roles = "USER")
// 	public void mytest1() throws Exception {
//     	// mockMvc.perform(MockMvcRequestBuilders.get("/someApi"))
//         	// .andExpect(ResultMatcher.status().isOk());
// }

// }

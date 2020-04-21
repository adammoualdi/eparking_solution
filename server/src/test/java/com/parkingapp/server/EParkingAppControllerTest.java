// package com.parkingapp.server;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.parkingapp.server.controller.MainController;
// import com.parkingapp.server.domain.Role;
// import com.parkingapp.server.domain.UserInfo;
// import com.parkingapp.server.repository.BookingRepository;
// import com.parkingapp.server.repository.CarRepository;
// import com.parkingapp.server.repository.LocationRepo;
// import com.parkingapp.server.repository.RoleRepo;
// import com.parkingapp.server.repository.UserInfoRepo;

// import org.aspectj.lang.annotation.Before;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.test.context.ContextConfiguration;
// import org.springframework.test.context.junit4.SpringRunner;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;
// import org.springframework.web.context.WebApplicationContext;

// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// import static org.junit.Assert.*;

// @RunWith(SpringRunner.class)
// // @WebMvcTest(secure = false)
// @SpringBootTest
// @AutoConfigureMockMvc
// public class EParkingAppControllerTest {

//     @Autowired
//     MockMvc mockMvc;

//     @Autowired
//     ObjectMapper objectMapper;

//     @Autowired
//     WebApplicationContext context;
    
// 	@Autowired
// 	private RoleRepo roleRepo;
		
// 	@Autowired
// 	private UserInfoRepo userInfoRepo;
	
// 	@Autowired
// 	private BookingRepository bookingRepo;
	
// 	@Autowired
// 	private CarRepository carRepo;
	
// 	@Autowired
//     private LocationRepo locationRepo;

//     @Test
//     public void testMainController() throws Exception {
//         // mockMvc.perform(post("/test").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
//         mockMvc.perform(MockMvcRequestBuilders.get("/test")
//         // .content(asJsonString(new EmployeeVO(null, "firstName4", "lastName4", "email4@mail.com")))
//       .contentType(MediaType.APPLICATION_JSON)
//       .accept(MediaType.APPLICATION_JSON))
//       .andExpect(status().isOk());
//     //   .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").exists());
//     }
// }
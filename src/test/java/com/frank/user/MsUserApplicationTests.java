package com.frank.user;

import com.frank.user.api.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MsUserApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	UserController userController;

	@Test
	void contextLoads() {
		assertThat(userController).isNotNull();

	}

	@Test
	public void userSmithShoudlExists() throws Exception {
		assertThat(this.restTemplate.withBasicAuth("user","password").getForObject("http://localhost:" + port + "/api/userdetails/1",
				String.class)).contains("Smith");
	}

	@Test
	public void userIdShouldBeLong() throws Exception {
		assertThat(this.restTemplate.withBasicAuth("user","password").getForObject("http://localhost:" + port + "/api/userdetails/1a",
				String.class)).contains("userId's type must be Long");
	}

}

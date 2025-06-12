package com.phaiecobyte.spring_security;

import com.phaiecobyte.spring_security.entity.Role;
import com.phaiecobyte.spring_security.entity.User;
import com.phaiecobyte.spring_security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@SpringBootApplication
public class SpringSecurityApplication implements CommandLineRunner {
	private final UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User defaultUser = userRepository.findByRole(Role.ROLE_ADMIN);
		if(null == defaultUser){
			User user = new User();

			user.setEmail("admin@email.com");
			user.setForename("@2025");
			user.setSurname("admin");
			user.setRole(Role.ROLE_ADMIN);
			user.setPassword(new BCryptPasswordEncoder().encode("admin@2025"));

			userRepository.save(user);
		}
	}
}

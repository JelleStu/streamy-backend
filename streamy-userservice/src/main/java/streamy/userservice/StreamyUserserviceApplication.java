package streamy.userservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import streamy.userservice.model.AppUser;
import streamy.userservice.model.Role;
import streamy.userservice.repository.IRoleRepo;
import streamy.userservice.service.UserService;

import java.util.ArrayList;
import java.util.UUID;


@EnableEurekaClient
@EnableMongoRepositories
@SpringBootApplication
@Slf4j
public class StreamyUserserviceApplication {

	public static final String topicExchangeName = "streamy-exchange";
	public static final String queueName = "streamy-user-service";

	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(topicExchangeName);
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("streamy.userservice");
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	/*@Bean
	CommandLineRunner runner(UserService userService, IRoleRepo roleRepo){
		return args -> {
			ArrayList<Role> roles = new ArrayList<Role>();
			roles.add(roleRepo.findRoleByName("ROLE_USER"));
			AppUser appUser = new AppUser(UUID.randomUUID(), "admin@testmessage.nl", "admin", passwordEncoder().encode("admin"),roles );
			appUser.setEmail("test@test.nl");
			userService.deleteUser(appUser);
		};
	}*/

	public static void main(String[] args) {
		SpringApplication.run(StreamyUserserviceApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

}

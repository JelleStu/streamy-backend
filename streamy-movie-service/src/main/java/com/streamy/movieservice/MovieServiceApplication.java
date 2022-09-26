package com.streamy.movieservice;

import com.streamy.movieservice.messaging.Receiver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.UUID;

@EnableEurekaClient
@EnableMongoRepositories
@Slf4j
@SpringBootApplication
public class MovieServiceApplication {

	public static final String topicExchangeName = "streamy-exchange";
	static final String queueName = "streamy-movie-service";


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
		return BindingBuilder.bind(queue).to(exchange).with("streamy.movieservice");
	}

	@Bean
	SimpleMessageListenerContainer container(MessageListenerAdapter listenerAdapter, ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);

		log.info(container.getConnectionFactory().getHost());
		log.info(connectionFactory.getHost());
		log.info(connectionFactory.getUsername());
		log.info(Integer.toString(connectionFactory.getPort()));
		container.setQueueNames(queueName);
		container.setMessageListener(listenerAdapter);

		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}

/*	@Bean
	CommandLineRunner runner(MovieService movieService, IMovieRepository movieRepo) {
		return args -> {
			Movie movie = new Movie(UUID.randomUUID(), "Avatar: the way of water", "https://streamy.blob.core.windows.net/thumbnails/avatar-the-way-of-water-thumbnail.jpg", "https://streamy.blob.core.windows.net/movies/avatar-the-way-of-water-movie.mp4" );
			movieService.addMovie(movie);
		};
	}*/
	public static void main(String[] args) {
		SpringApplication.run(MovieServiceApplication.class, args);
	}

}

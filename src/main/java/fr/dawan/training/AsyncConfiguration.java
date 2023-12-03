package fr.dawan.training;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync //activation du support du mode asynchrone
public class AsyncConfiguration {

	//création d'un pool de threads qui exécutera les différentes tâches asynchrones
	@Bean("myTaskExecutor")
	public Executor asyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(6);
		executor.setQueueCapacity(100);
		executor.setThreadNamePrefix("asyncThread-");
		executor.initialize();
		return executor;
	}
}

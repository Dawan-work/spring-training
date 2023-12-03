package fr.dawan.training.tasks;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyTasks {

	private final static Logger ROOT_LOGGER = LoggerFactory.getLogger(MyTasks.class);
	
//	@Scheduled(fixedDelay = 3000) //un délai fixe en 2 appels (après la fin du traitement)
//	@Async("myTaskExecutor")
//	public void task1() {
//		ROOT_LOGGER.info("TASK1.....");
//	}
	
	//Old way (Java only)
//	public void task1WithJava() {
//		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//		final Runnable beeper = new Runnable() {
//		       public void run() { 
//		    	   ROOT_LOGGER.info("TASK1 WITH JAVA.....");
//		       }
//		 };
//		 scheduler.scheduleWithFixedDelay(beeper, 1, 3, TimeUnit.SECONDS);   
//	}
	
	
//	@Scheduled(fixedRate = 5000) //une durée fixe entre 2 appels
//	@Async("myTaskExecutor")
//	public void task2() {
//		ROOT_LOGGER.info("TASK2.....");
//	}
//	
//	@Scheduled(cron = "*/1 * * * * *") //toutes les 1min
//	@Async("myTaskExecutor")
//	public void task3() {
//		ROOT_LOGGER.info("TASK3.....");
//	}
	
}

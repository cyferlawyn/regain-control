package de.cydev;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import de.cydev.entities.TaskEntity;
import de.cydev.repositories.TaskRepository;

@SpringBootApplication
public class RegainControlApplication
{
	private static final Logger log = LoggerFactory.getLogger(RegainControlApplication.class);
	
	public static void main(String[] args)
	{
		SpringApplication.run(RegainControlApplication.class);
	}
	
	@Bean
	public CommandLineRunner demo(TaskRepository repository) {
		return (args) -> {
			// save a couple of tasks
			repository.save(new TaskEntity("Do A!", "Default"));
			repository.save(new TaskEntity("Do B!", "Default"));
			repository.save(new TaskEntity("Do C!", "Default"));
			repository.save(new TaskEntity("Do D!", "Default"));
			repository.save(new TaskEntity("Do E!", "Custom"));

			// fetch all tasks
			log.info("Tasks found with findAll():");
			log.info("-------------------------------");
			for (TaskEntity task : repository.findAll()) {
				log.info(task.toString());
			}
			log.info("");

			// fetch an individual task by ID
			TaskEntity task = repository.findOne(1L);
			log.info("Task found with findOne(1L):");
			log.info("--------------------------------");
			log.info(task.toString());
			log.info("");

			// fetch tasks by category
			log.info("Tasks found with findByCategory('Custom'):");
			log.info("--------------------------------------------");
			for (TaskEntity customTask : repository.findByCategory("Custom")) {
				log.info(customTask.toString());
			}
			log.info("");
		};
	}
}

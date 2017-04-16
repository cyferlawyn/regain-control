package de.cydev;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import de.cydev.model.tasks.Priority;
import de.cydev.model.tasks.Task;
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
	public CommandLineRunner demo(TaskRepository taskRepository) {
		return (args) -> {
			// save a couple of tasks
			taskRepository.save(new Task("Do A!", Priority.LOW));
			taskRepository.save(new Task("Do B!", Priority.LOW));
			taskRepository.save(new Task("Do C!", Priority.MEDIUM));
			taskRepository.save(new Task("Do D!", Priority.HIGH));
			taskRepository.save(new Task("Do E!", Priority.HIGH));

			// fetch all tasks
			log.info("Tasks found with findAll():");
			log.info("-------------------------------");
			for (Task task : taskRepository.findAll()) {
				log.info(task.toString());
			}
			log.info("");

			// fetch an individual task by ID
			Task task = taskRepository.findOne(1L);
			log.info("Task found with findOne(1L):");
			log.info("--------------------------------");
			log.info(task.toString());
			log.info("");
		};
	}
}

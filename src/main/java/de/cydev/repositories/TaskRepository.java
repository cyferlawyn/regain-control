package de.cydev.repositories;

import org.springframework.data.repository.CrudRepository;

import de.cydev.model.tasks.Task;

public interface TaskRepository extends CrudRepository<Task, Long>
{
	
}

package de.cydev.repositories;

import org.springframework.data.repository.CrudRepository;

import de.cydev.model.tasks.LiveTask;

public interface LiveTaskRepository extends CrudRepository<LiveTask, Long>
{
	
}

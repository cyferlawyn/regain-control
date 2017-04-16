package de.cydev.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.cydev.entities.TaskEntity;

public interface TaskRepository extends CrudRepository<TaskEntity, Long>
{
	List<TaskEntity> findByCategory(String category);
}

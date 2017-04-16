package de.cydev.repositories;

import org.springframework.data.repository.CrudRepository;

import de.cydev.model.tasks.VaultTask;

public interface VaultTaskRepository extends CrudRepository<VaultTask, Long>
{
	
}

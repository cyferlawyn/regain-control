package de.cydev.repositories;

import org.springframework.data.repository.CrudRepository;

import de.cydev.model.lists.VaultList;

public interface VaultListRepository extends CrudRepository<VaultList, Long>
{
	VaultList findByTitle(String title);
}

package de.cydev.repositories;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;

import de.cydev.model.lists.LiveList;

public interface LiveListRepository extends CrudRepository<LiveList, Long>
{
	LiveList findByDate(Date date);
}

package de.cydev.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import de.cydev.model.tasks.VaultTask;

public interface VaultTaskRepository extends CrudRepository<VaultTask, Long>
{
	//@Query("select v from VaultTask v, Task t, LiveTask l where v.taskId = t.id and t.id = l.taskId and l.id = :id")
	@Query("  select v "
			+ " from VaultTask v, "
			+ "      LiveTask l, "
			+ "      Task t "
			+ " join v.task t1 "
			+ " join l.task t2 "
			+ "where t1.id = t2.id "
			+ "  and l.id = :id")
	VaultTask getVaultTaskForLiveTaskId(@Param("id") Long id);
}

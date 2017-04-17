package de.cydev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.cydev.model.tasks.Status;
import de.cydev.model.tasks.VaultTask;
import de.cydev.repositories.VaultTaskRepository;

@Controller
public class VaultTaskController
{
	@Autowired
	private TaskController taskController;

	@Autowired
	private VaultTaskRepository vaultTaskRepository;

	public VaultTask createVaultTask(VaultTask vaultTask, boolean createTask)
	{
		if (createTask)
		{
			taskController.createTask(vaultTask.getTask());
		}

		vaultTaskRepository.save(vaultTask);

		return vaultTask;
	}

	public VaultTask getVaultTaskById(Long id)
	{
		return vaultTaskRepository.findOne(id);
	}
	
	public VaultTask getVaultTaskByLiveTaskId(Long id)
	{
		return vaultTaskRepository.getVaultTaskForLiveTaskId(id);
	}
	
	public VaultTask updateVaultTaskStatus(VaultTask vaultTask, Status status)
	{
		vaultTask.setStatus(status);
		vaultTaskRepository.save(vaultTask);
		
		return vaultTask;
	}
}

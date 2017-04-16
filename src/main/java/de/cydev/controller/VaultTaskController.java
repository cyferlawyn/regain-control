package de.cydev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.cydev.model.tasks.VaultTask;
import de.cydev.repositories.VaultTaskRepository;

@Controller
public class VaultTaskController
{
	@Autowired
	private TaskController taskController;
	
	@Autowired
	private VaultTaskRepository vaultTaskRepository;
	
	public VaultTask createVaultTask(VaultTask vaultTask)
	{
		taskController.createTask(vaultTask.getTask());
		
		vaultTaskRepository.save(vaultTask);
		
		return vaultTask;
	}
}

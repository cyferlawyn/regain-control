package de.cydev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.cydev.model.tasks.LiveTask;
import de.cydev.repositories.LiveTaskRepository;

@Controller
public class LiveTaskController
{
	@Autowired
	private TaskController taskController;
	
	@Autowired
	private LiveTaskRepository liveTaskRepository;
	
	public LiveTask createLiveTask(LiveTask liveTask)
	{
		taskController.createTask(liveTask.getTask());
		
		liveTaskRepository.save(liveTask);
		
		return liveTask;
	}
}

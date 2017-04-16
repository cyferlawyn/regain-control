package de.cydev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.cydev.model.tasks.Task;
import de.cydev.repositories.TaskRepository;

@Controller
public class TaskController
{
	@Autowired
	TaskRepository taskRepository;
	
	public Task createTask(Task task)
	{
		return taskRepository.save(task);
	}
	
	public Task getTaskById(Long id)
	{
		return taskRepository.findOne(id);
	}
	
	public Task updateTask(Long id, Task updatedTask)
	{
		Task task = taskRepository.findOne(id);
		
		if (task != null)
		{
			task.setTitle(updatedTask.getTitle());
			task.setPriority(updatedTask.getPriority());
			
			taskRepository.save(task);
			
			return task;
		}
		
		return null;
	}
	
	public void deleteTask(Long id)
	{
		taskRepository.delete(id);
	}
}

package de.cydev.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.cydev.entities.TaskEntity;
import de.cydev.repositories.TaskRepository;

@RestController
public class TaskController
{
	@Autowired
	private TaskRepository taskRepository;

	@RequestMapping("/getTaskById")
	public TaskEntity getTaskById(@RequestParam(value = "id") Long id)
	{
		return taskRepository.findOne(id);
	}

	@RequestMapping("/getTasksByCategory")
	public List<TaskEntity> getTaskByCategory(@RequestParam(value = "category") String category)
	{
		return taskRepository.findByCategory(category);
	}
}

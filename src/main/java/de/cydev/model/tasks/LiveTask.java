package de.cydev.model.tasks;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class LiveTask
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne
	private Task task;
	
	private Status status;

	public LiveTask()
	{
		
	}
	
	public LiveTask(Task task, Status status)
	{
		this.task = task;
		this.status = status;
	}
	
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Task getTask()
	{
		return task;
	}

	public void setTask(Task task)
	{
		this.task = task;
	}

	public Status getStatus()
	{
		return status;
	}

	public void setStatus(Status status)
	{
		this.status = status;
	}
}

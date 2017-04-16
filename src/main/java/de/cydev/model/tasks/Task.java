package de.cydev.model.tasks;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Task implements Serializable
{
	private static final long serialVersionUID = 46627101071927231L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String title;
	
	private Priority priority;
	
	public Task()
	{
		
	}
	
	public Task(String title, Priority priority)
	{
		this.title = title;
		this.priority = priority;
	}
	
	@Override
	public String toString()
	{
		return String.format("Task[id=%d, title=%s, priority=%s", id, title, priority);
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public Priority getPriority()
	{
		return priority;
	}

	public void setPriority(Priority priority)
	{
		this.priority = priority;
	}
}

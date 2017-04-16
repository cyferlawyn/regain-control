package de.cydev.model.lists;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import de.cydev.model.tasks.LiveTask;

@Entity
public class LiveList
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Date date;
	
	@ManyToMany
	private List<LiveTask> tasks;
	
	public LiveList()
	{
		
	}
	
	public LiveList(Date date)
	{
		this.date = date;
		this.tasks = new ArrayList<>();
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public List<LiveTask> getTasks()
	{
		return tasks;
	}

	public void setTasks(List<LiveTask> tasks)
	{
		this.tasks = tasks;
	}
}

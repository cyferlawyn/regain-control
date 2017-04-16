package de.cydev.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TaskEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String title;
	
	private String category;

	public TaskEntity()
	{
		
	}

	public TaskEntity(String title, String category)
	{
		this.title = title;
		this.category = category;
	}
	
	@Override
	public String toString()
	{
		return String.format("Task[id=%d, title='%s', category='%s']", id, title, category);
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

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}
}

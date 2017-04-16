package de.cydev.model.lists;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import de.cydev.model.tasks.VaultTask;

@Entity
public class VaultList
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(unique = true)
	private String title;

	private String tag;

	@ManyToMany
	private List<VaultTask> tasks;

	public VaultList()
	{

	}

	public VaultList(String title, String tag)
	{
		this.title = title;
		this.tag = tag;
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

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getTag()
	{
		return tag;
	}

	public void setTag(String tag)
	{
		this.tag = tag;
	}

	public List<VaultTask> getTasks()
	{
		return tasks;
	}

	public void setTasks(List<VaultTask> tasks)
	{
		this.tasks = tasks;
	}
}

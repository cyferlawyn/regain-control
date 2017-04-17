package de.cydev.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.cydev.model.lists.VaultList;
import de.cydev.model.tasks.VaultTask;
import de.cydev.repositories.VaultListRepository;

@Controller
public class VaultListController
{
	@Autowired
	private VaultListRepository vaultListRepository;
	
	public VaultList createVaultList(VaultList vaultList)
	{
		vaultListRepository.save(vaultList);
		
		return vaultList;
	}

	public VaultList getVaultListById(Long id)
	{
		return vaultListRepository.findOne(id);
	}
	
	public VaultList getVaultListByTitle(String title)
	{
		return vaultListRepository.findByTitle(title);
	}
	
	public VaultList addVaultTask(VaultList vaultList, VaultTask vaultTask)
	{
		vaultList.getTasks().add(vaultTask);
		vaultList = vaultListRepository.save(vaultList);
		
		return vaultList;
	}

	public List<String> getVaultListTitles()
	{
		List<String> vaultListTitles = new ArrayList<>();
		
		Iterable<VaultList> vaultLists = vaultListRepository.findAll();
		for (VaultList vaultList : vaultLists)
		{
			vaultListTitles.add(vaultList.getTitle());
		}
		
		return vaultListTitles;
	}
}

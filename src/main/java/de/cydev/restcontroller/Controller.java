package de.cydev.restcontroller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.cydev.controller.LiveListController;
import de.cydev.controller.LiveTaskController;
import de.cydev.controller.VaultListController;
import de.cydev.controller.VaultTaskController;
import de.cydev.model.lists.LiveList;
import de.cydev.model.lists.VaultList;
import de.cydev.model.tasks.Priority;
import de.cydev.model.tasks.Status;
import de.cydev.model.tasks.Task;
import de.cydev.model.tasks.VaultTask;

@RestController
public class Controller
{
	@Autowired
	private LiveTaskController liveTaskController;

	@Autowired
	private LiveListController liveListController;

	@Autowired
	private VaultTaskController vaultTaskController;

	@Autowired
	private VaultListController vaultListController;

	@GetMapping("/getVaultLists")
	public ResponseEntity<Iterable<VaultList>> getVaultLists()
	{
		return ResponseEntity.ok(vaultListController.getVaultLists());
	}
	
	@GetMapping("/getLiveListByDate")
	public ResponseEntity<LiveList> getLiveListByDate(
	        @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date)
	{
		LiveList liveList = liveListController.getLiveListByDate(date);

		if (liveList == null)
		{
			liveList = new LiveList(date);
			liveListController.createLiveList(liveList);
		}

		return ResponseEntity.ok(liveList);
	}

	@PostMapping("/createVaultList")
	public ResponseEntity<VaultList> createVaultList(@RequestParam("title") String title,
	        @RequestParam("tag") String tag)
	{
		VaultList vaultList = new VaultList(title, tag);

		try
		{
			vaultListController.createVaultList(vaultList);
			return ResponseEntity.ok(vaultList);			
		}
		catch (Exception e)
		{
			return ResponseEntity.badRequest().build();
		}

	}

	@PostMapping("/createVaultTask")
	public ResponseEntity<VaultList> createVaultTask(@RequestParam("vaultListTitle") String vaultListTitle,
	        @RequestParam("taskTitle") String taskTitle, @RequestParam("priority") Priority taskPriority)
	{
		VaultList vaultList = vaultListController.getVaultListByTitle(vaultListTitle);
		if (vaultList == null)
		{
			return ResponseEntity.notFound().build();
		}

		Task task = new Task(taskTitle, taskPriority);

		VaultTask vaultTask = new VaultTask(task, Status.CREATED);
		vaultTask = vaultTaskController.createVaultTask(vaultTask);

		vaultList = vaultListController.addVaultTask(vaultList, vaultTask);

		return ResponseEntity.ok(vaultList);
	}		
}

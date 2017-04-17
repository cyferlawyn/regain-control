package de.cydev.restcontroller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import de.cydev.model.tasks.LiveTask;
import de.cydev.model.tasks.Priority;
import de.cydev.model.tasks.Status;
import de.cydev.model.tasks.Task;
import de.cydev.model.tasks.VaultTask;

@RestController
@CrossOrigin
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

	@GetMapping("/getVaultListTitles")
	public ResponseEntity<List<String>> getVaultListTitles()
	{
		return ResponseEntity.ok(vaultListController.getVaultListTitles());
	}

	@GetMapping("/getVaultListByTitle")
	public ResponseEntity<VaultList> getVaultListByTitle(@RequestParam("title") String title)
	{
		return ResponseEntity.ok(vaultListController.getVaultListByTitle(title));
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
		vaultTask = vaultTaskController.createVaultTask(vaultTask, true);

		vaultList = vaultListController.addVaultTask(vaultList, vaultTask);

		return ResponseEntity.ok(vaultList);
	}

	@PostMapping("/createLiveTask")
	public ResponseEntity<LiveList> createLiveTask(
	        @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
	        @RequestParam("taskTitle") String taskTitle, @RequestParam("taskPriority") Priority taskPriority)
	{
		LiveList liveList = liveListController.getLiveListByDate(date);

		if (liveList == null)
		{
			liveList = new LiveList(date);
			liveListController.createLiveList(liveList);
		}

		Task task = new Task(taskTitle, taskPriority);

		LiveTask liveTask = new LiveTask(task, Status.CREATED);
		liveTask = liveTaskController.createLiveTask(liveTask, true);

		liveList = liveListController.addLiveTask(liveList, liveTask);

		return ResponseEntity.ok(liveList);
	}

	@PostMapping("/pullVaultTaskToLiveList")
	public ResponseEntity<LiveList> pullVaultTaskToLiveList(
	        @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
	        @RequestParam("vaultTaskId") Long vaultTaskId)
	{
		LiveList liveList = liveListController.getLiveListByDate(date);

		if (liveList == null)
		{
			liveList = new LiveList(date);
			liveListController.createLiveList(liveList);
		}

		VaultTask vaultTask = vaultTaskController.getVaultTaskById(vaultTaskId);

		if (vaultTask == null)
		{
			return ResponseEntity.notFound().build();
		}

		LiveTask liveTask = new LiveTask(vaultTask.getTask(), Status.CREATED);
		liveTask = liveTaskController.createLiveTask(liveTask, false);

		liveList = liveListController.addLiveTask(liveList, liveTask);

		return ResponseEntity.ok(liveList);
	}

	@PostMapping("/stashLiveTaskInVaultList")
	public ResponseEntity<LiveTask> stashLiveTaskInVaultList(@RequestParam("liveTaskId") Long liveTaskId,
	        @RequestParam("vaultListTitle") String vaultListTitle)
	{
		VaultList vaultList = vaultListController.getVaultListByTitle(vaultListTitle);

		if (vaultList == null)
		{
			return ResponseEntity.notFound().build();
		}

		LiveTask liveTask = liveTaskController.getLiveTaskById(liveTaskId);

		if (liveTask == null)
		{
			return ResponseEntity.notFound().build();
		}

		liveTaskController.updateLiveTaskStatus(liveTask, Status.STASHED);

		VaultTask vaultTask = new VaultTask(liveTask.getTask(), Status.CREATED);
		vaultTask = vaultTaskController.createVaultTask(vaultTask, false);

		vaultListController.addVaultTask(vaultList, vaultTask);

		return ResponseEntity.ok(liveTask);
	}

	@PostMapping("/carryOverLiveTask")
	public ResponseEntity<LiveTask> carryOverLiveTask(@RequestParam("liveTaskId") Long liveTaskId,
	        @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date)
	{
		LiveTask liveTask = liveTaskController.getLiveTaskById(liveTaskId);

		if (liveTask == null)
		{
			return ResponseEntity.notFound().build();
		}

		liveTaskController.updateLiveTaskStatus(liveTask, Status.CARRIED_OVER);

		LiveTask carriedOverLiveTask = new LiveTask(liveTask.getTask(), Status.CREATED);
		liveTaskController.createLiveTask(carriedOverLiveTask, false);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, 24);
		
		LiveList liveList = liveListController.getLiveListByDate(calendar.getTime());

		if (liveList == null)
		{
			liveList = new LiveList(calendar.getTime());
			liveListController.createLiveList(liveList);
		}
		
		liveListController.addLiveTask(liveList, carriedOverLiveTask);
		
		return ResponseEntity.ok(liveTask);
	}
	
	@PostMapping("/finishLiveTask")
	public ResponseEntity<LiveTask> finishLiveTask(@RequestParam("liveTaskId") Long liveTaskId)
	{
		LiveTask liveTask = liveTaskController.getLiveTaskById(liveTaskId);

		if (liveTask == null)
		{
			return ResponseEntity.notFound().build();
		}

		liveTaskController.updateLiveTaskStatus(liveTask, Status.COMPLETED);
		
		VaultTask vaultTask = vaultTaskController.getVaultTaskByLiveTaskId(liveTask.getId());
		
		if (vaultTask != null)
		{
			vaultTaskController.updateVaultTaskStatus(vaultTask, Status.COMPLETED);
		}
		
		return ResponseEntity.ok(liveTask);
	}
	
	@PostMapping("/deleteLiveTask")
	public ResponseEntity<LiveTask> deleteLiveTask(@RequestParam("liveTaskId") Long liveTaskId)
	{
		LiveTask liveTask = liveTaskController.getLiveTaskById(liveTaskId);

		if (liveTask == null)
		{
			return ResponseEntity.notFound().build();
		}

		liveTaskController.updateLiveTaskStatus(liveTask, Status.DELETED);
		
		VaultTask vaultTask = vaultTaskController.getVaultTaskByLiveTaskId(liveTask.getId());
		
		if (vaultTask != null)
		{
			vaultTaskController.updateVaultTaskStatus(vaultTask, Status.COMPLETED);
		}
		
		return ResponseEntity.ok(liveTask);
	}
}

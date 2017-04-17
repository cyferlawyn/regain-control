package de.cydev.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.cydev.model.lists.LiveList;
import de.cydev.model.tasks.LiveTask;
import de.cydev.repositories.LiveListRepository;

@Controller
public class LiveListController
{
	@Autowired
	private LiveListRepository liveListRepository;
	
	public LiveList createLiveList(LiveList liveList)
	{
		liveListRepository.save(liveList);
		
		return liveList;
	}
	
	public LiveList getLiveListByDate(Date date)
	{
		return liveListRepository.findByDate(date);
	}

	public LiveList addLiveTask(LiveList liveList, LiveTask liveTask)
	{
		liveList.getTasks().add(liveTask);
		liveList = liveListRepository.save(liveList);
		
		return liveList;
	}
}

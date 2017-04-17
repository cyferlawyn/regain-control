var $calendarContainer;
var $liveListContainer;
var $vaultContainer;
var $vaultTitleContainer;
var $dumpContainer;

var liveTaskPriority = "MEDIUM";

$(document).ready(function() {
	setup();
});

function setup() {
	$calendarContainer = $('#calendarContainer'); 
	$liveListContainer = $('#liveListContainer'); 
	$vaultContainer = $('#vaultContainer');
	$vaultTitleContainer = $('#vaultTitleContainer');
	$dumpContainer = $('#dumpContainer');
	
	$calendarContainer.html("Loading...");
	$liveListContainer.html("Loading...");
	$vaultContainer.html("Loading...");
	$vaultTitleContainer.html("Loading...");
	$dumpContainer.html("Loading...");
	
	getLiveList(new Date().toISOString());
	getVaultTitles();
}

function updateLiveListContainer(data) {
	var html = "<ul>";
	
	for (var i = 0; i < data.tasks.length; i++) {
		html = html + "<li>" + data.tasks[i].task.title + " (" + data.tasks[i].status + ")</li>"
	}
	
	html = html + "</ul>";
	$liveListContainer.html(html);
}

function updateVaultTitleContainer(data) {
	var html = "<ul>";
	
	for (var i = 0; i < data.length; i++) {
		if (i < 1) {
			getVaultListByTitle(data[i]);
		}
		
		html = html + "<li onClick=\"getVaultListByTitle('" + data[i] + "')\">" + data[i] + "</li>"		
	}
	
	html = html + "</ul>";
	$vaultTitleContainer.html(html);
}

function updateVaultContainer(data) {
	var html = "<ul>";
	
	for (var i = 0; i < data.tasks.length; i++) {
		html = html + "<li>" + data.tasks[i].task.title + " (" + data.tasks[i].status + ")</li>"
	}
	
	html = html + "</ul>";
	$vaultContainer.html(html);
}

function getLiveList(date) {
	$.ajax({
        url: "http://192.168.0.19:8080/getLiveListByDate?date=" + date
    }).then(function(data) {
       updateLiveListContainer(data);
    });
}

function getVaultTitles() {
	$.ajax({
        url: "http://192.168.0.19:8080/getVaultListTitles"
    }).then(function(data) {
       updateVaultTitleContainer(data);
    });
}

function getVaultListByTitle(title) {
	$.ajax({
        url: "http://192.168.0.19:8080/getVaultListByTitle?title=" + title
    }).then(function(data) {
       updateVaultContainer(data);
    });
}

function selectLiveTaskPriority(priority) {
	liveTaskPriority = priority;
	if (priority == "LOW") {
		$("#liveTaskPriorityButton").html("Priority: Low <span class=\"caret\"></span>");
	} else if (priority == "MEDIUM") {
		$("#liveTaskPriorityButton").html("Priority: Medium <span class=\"caret\"></span>");
	} else {
		$("#liveTaskPriorityButton").html("Priority: High <span class=\"caret\"></span>");
	}
} 

function createLiveTask() {
	$.ajax({
        url: "http://192.168.0.19:8080/createLiveTask?date=" + new Date().toISOString() + "&taskTitle=" + $("#liveTaskTitleInput").val() + "&taskPriority=" + liveTaskPriority,
        method: "POST"
    }).then(function(data) {
    	getLiveList(new Date().toISOString());
    });
}
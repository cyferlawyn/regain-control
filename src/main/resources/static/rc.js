var $calendarContainer;
var $liveListContainer;
var $vaultContainer;
var $vaultTitleContainer;
var $dumpContainer;

var liveTaskPriority = "MEDIUM";
var vaultTaskPriority = "MEDIUM";

var selectedLiveListDate = new Date().toISOString();
var selectedVaultListTitle = null;

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
	
	getLiveList(selectedLiveListDate);
	getVaultTitles();
}

function updateLiveListContainer(data) {
	var html = "<div class=\"panel panel-default\">";
	html = html + "<div class=\"panel-heading\">"
	html = html + "<h3 class=\"panel-title\">Live list for " + new Date(data.date).toISOString().substr(0, 10) + "</h3>"
	html = html + "</div>"
	html = html + "<div class=\"panel-body\">"
	html = html + "<ul>";
	
	for (var i = 0; i < data.tasks.length; i++) {
		html = html + "<li>" + data.tasks[i].task.title + " (" + data.tasks[i].status + ")";
		html = html + "<button class=\"btn btn-default\" type=\"button\" onClick=\"finishLiveTask(" + data.tasks[i].id + ")\">Finish</button>";
		html = html + "<button class=\"btn btn-default\" type=\"button\" onClick=\"deleteLiveTask(" + data.tasks[i].id + ")\">Delete</button>";
		html = html + "<button class=\"btn btn-default\" type=\"button\" onClick=\"stashLiveTaskInVaultList(" + data.tasks[i].id + ")\">Stash</button>";
		html = html + "</li>";
	}
	
	html = html + "</ul>";
	html = html + "</div>";
	html = html + "</div>";
	
	$liveListContainer.html(html);
}

function updateVaultTitleContainer(data) {
	var html = "<div class=\"panel panel-default\">";
	html = html + "<div class=\"panel-heading\">"
	html = html + "<h3 class=\"panel-title\">Vault lists</h3>"
	html = html + "</div>"
	html = html + "<div class=\"panel-body\">"
	html = html + "<ul>";
	
	for (var i = 0; i < data.length; i++) {				
		html = html + "<li onClick=\"getVaultListByTitle('" + data[i] + "')\">" + data[i] + "</li>"		
	}
	
	if (data.length > 0) {
		if (selectedVaultListTitle == null) {
			getVaultListByTitle(data[0]);
		}
	} else {
		$vaultContainer.html("No vault lists found");
	}
	
	html = html + "</ul>";
	html = html + "</div>";
	html = html + "</div>";
	
	$vaultTitleContainer.html(html);
}

function updateVaultContainer(data) {
	var html = "<div class=\"panel panel-default\">";
	html = html + "<div class=\"panel-heading\">"
	html = html + "<h3 class=\"panel-title\">" + data.title + "</h3>"
	html = html + "</div>"
	html = html + "<div class=\"panel-body\">"
	html = html + "<ul>";
	
	for (var i = 0; i < data.tasks.length; i++) {
		html = html + "<li>" + data.tasks[i].task.title + " (" + data.tasks[i].status + ")</li>"
	}
	
	html = html + "</ul>";
	html = html + "</div>";
	html = html + "</div>";
	
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
       selectedVaultListTitle = data.title;
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

function selectVaultTaskPriority(priority) {
	liveTaskPriority = priority;
	if (priority == "LOW") {
		$("#liveTaskPriorityButton").html("Priority: Low <span class=\"caret\"></span>");
	} else if (priority == "MEDIUM") {
		$("#liveTaskPriorityButton").html("Priority: Medium <span class=\"caret\"></span>");
	} else {
		$("#liveTaskPriorityButton").html("Priority: High <span class=\"caret\"></span>");
	}
}

function createVaultList() {
	$.ajax({
        url: "http://192.168.0.19:8080/createVaultList?title=" + $("#vaultListTitleInput").val() + "&tag=" + $("#vaultListTagInput").val(),
        method: "POST"
    }).then(function(data) {
    	getVaultTitles();
    });
}

function createLiveTask() {
	$.ajax({
        url: "http://192.168.0.19:8080/createLiveTask?date=" + new Date().toISOString() + "&taskTitle=" + $("#liveTaskTitleInput").val() + "&taskPriority=" + liveTaskPriority,
        method: "POST"
    }).then(function(data) {
    	getLiveList(selectedLiveListDate);
    });
}

function createVaultTask() {
	$.ajax({
        url: "http://192.168.0.19:8080/createVaultTask?vaultListTitle=" + selectedVaultListTitle + "&taskTitle=" + $("#vaultTaskTitleInput").val() + "&taskPriority=" + vaultTaskPriority,
        method: "POST"
    }).then(function(data) {
    	getVaultListByTitle(selectedVaultListTitle);
    });
}

function finishLiveTask(liveTaskId) {
	$.ajax({
        url: "http://192.168.0.19:8080/finishLiveTask?liveTaskId=" + liveTaskId,
        method: "POST"
    }).then(function(data) {
    	getLiveList(selectedLiveListDate);
    });
}

function deleteLiveTask(liveTaskId) {
	$.ajax({
        url: "http://192.168.0.19:8080/deleteLiveTask?liveTaskId=" + liveTaskId,
        method: "POST"
    }).then(function(data) {
    	getLiveList(selectedLiveListDate);
    });
}

function stashLiveTaskInVaultList(liveTaskId, vaultListTitle) {
	$.ajax({
        url: "http://192.168.0.19:8080/stashLiveTaskInVaultList?liveTaskId=" + liveTaskId + "&vaultListTitle=" + vaultListTitle,
        method: "POST"
    }).then(function(data) {
    	getLiveList(selectedLiveListDate);
    });
}
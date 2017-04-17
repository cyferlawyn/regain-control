var $calendarContainer;
var $liveListContainer;
var $vaultContainer;
var $vaultTitleContainer;
var $dumpContainer;

function bootstrapPage() {
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
		html = html + "<li>" + data.tasks[i].task.title + "</li>"
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
		html = html + "<li>" + data.tasks[i].task.title + "</li>"
	}
	
	html = html + "</ul>";
	$vaultContainer.html(html);
}

function getLiveList(date) {
	$.ajax({
        url: "http://localhost:8080/getLiveListByDate?date=" + date
    }).then(function(data) {
       updateLiveListContainer(data);
    });
}

function getVaultTitles() {
	$.ajax({
        url: "http://localhost:8080/getVaultListTitles"
    }).then(function(data) {
       updateVaultTitleContainer(data);
    });
}

function getVaultListByTitle(title) {
	$.ajax({
        url: "http://localhost:8080/getVaultListByTitle?title=" + title
    }).then(function(data) {
       updateVaultContainer(data);
    });
}

$(document).ready(function() {
	bootstrapPage();
});
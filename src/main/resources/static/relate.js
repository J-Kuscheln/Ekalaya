function asLeader(){
	if(document.getElementsByName("memberId").item(0).value == "") console.log("test");
	if(document.getElementsByName("projectId").item(0).value == "") document.getElementsByName("projectId").item(0).value == "test";
	document.getElementsByName("toDo").item(0).value = "asLeader";
}

function asMember(){
	document.getElementsByName("toDo").item(0).value = "asMember";
}

function removeLeader(){
	document.getElementsByName("toDo").item(0).value = "removeAsLeader";
}

function removeMember(){
	document.getElementsByName("toDo").item(0).value = "removeAsMember";
}
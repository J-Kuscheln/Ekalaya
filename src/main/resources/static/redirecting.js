function goToUri(toDo){
	let url="";
	let jsonBody="";
	switch(toDo){
		
		case "memberDetails":
			url = "http://localhost:8080/members/" + document.getElementsByName("memberIdInfo").item(0).value;
			console.log(url);
			location.href = url;
			return false;
			
		case "projectDetails":
			url = "http://localhost:8080/projects/" + document.getElementsByName("projectIdInfo").item(0).value;
			location.href = url;
			return false;
			
		case "memberDelete":
			url = "http://localhost:8080/members/" + document.getElementsByName("memberIdDelete").item(0).value;
		    return fetch(url, {
		        method: 'DELETE'
		    }).then(response => console.log(response));

		case "projectDelete":
			url = "http://localhost:8080/projects/" + document.getElementsByName("projectIdDelete").item(0).value;
			return fetch(url, {
		        method: 'DELETE'
		    }).then(response => console.log(response));

		case "memberUpdate":
			url = "http://localhost:8080/members/" + document.getElementsByName("memberIdUpdate").item(0).value;
			if(document.getElementsByName("memberIdUpdate").item(0).value == ""
				|| document.getElementsByName("firstNameUpdate").item(0).value == ""
				|| document.getElementsByName("lastNameUpdate").item(0).value == ""
				|| document.getElementsByName("positionUpdate").item(0).value == ""
				|| document.getElementsByName("phoneUpdate").item(0).value == ""
				|| document.getElementsByName("birthdayUpdate").item(0).value == ""
				)return false;
				
			if (document.getElementsByName("photoUrlUpdate").item(0).value == "") document.getElementsByName("photoUrlUpdate").item(0).value = "null";

			jsonBody=`{"id":"` + document.getElementsByName("memberidUpdate").item(0).value
			+ `",\n"firstName":"` + document.getElementsByName("firstNameUpdate").item(0).value
			+ `",\n"lastName":"` + document.getElementsByName("lastNameUpdate").item(0).value
			+ `",\n"photoUrl":` + document.getElementsByName("photoUrlUpdate").item(0).value
			+ `,\n"position":"` + document.getElementsByName("positionUpdate").item(0).value
			+ `",\n"email":"` + null
			+ `",\n"phone":"` + document.getElementsByName("phoneUpdate").item(0).value
			+ `",\n"birthday":"` + document.getElementsByName("birthdayUpdate").item(0).value
			+ `",\n"joinDate":` + null
			+ `,\n"modifiedDate":` + null
			+ `,\n"leadedProjects":` + null
			+ `,\n"memberProjects":` + null
			+ `,\n"finishedProjects":` + null + `}`;
			return fetch(url,{
				method:'PUT',
				body: jsonBody,
				headers: {'content-type': 'application/json'}
			})
			
		case "projectUpdate":	
		/*
			document.getElementsByName("projectIdUpdate").item(0),value = data.id;
			document.getElementsByName("nameUpdate").item(0),value = data.name;
			document.getElementsByName("statusUpdate").item(0),value = data.status;
			document.getElementsByName("descUpdate").item(0),value = data.description;		
		*/
			url = "http://localhost:8080/projects/" + document.getElementsByName("projectIdUpdate").item(0).value;
			if(document.getElementsByName("projectIdUpdate").item(0).value == ""
				|| document.getElementsByName("nameUpdate").item(0).value == ""
				|| document.getElementsByName("statusUpdate").item(0).value == ""
				|| document.getElementsByName("descUpdate").item(0).value == ""
				)return false;
				
			jsonBody=`{"id":"` + document.getElementsByName("projectIdUpdate").item(0).value
			+ `",\n"name":"` + document.getElementsByName("nameUpdate").item(0).value
			+ `",\n"status":"` + document.getElementsByName("statusUpdate").item(0).value
			+ `",\n"description":"` + document.getElementsByName("descUpdate").item(0).value
			+ `",\n"createdDate":` + null
			+ `,\n"modifiedDate":` + null
			+ `,\n"projectLeaders":` + null
			+ `,\n"projectMembers":` + null
			+ `,\n"milestones":` + null
			+ `}`;
			
			console.log(jsonBody);
			return fetch(url,{
				method: "PUT",
				body: jsonBody,
				headers:{'content-type': 'application/json'}
			})
	}
}

function retrieveData(who){
	let id = "";
	if(who=="members")id = document.getElementsByName("memberIdUpdateSelect").item(0).value;
	else id = document.getElementsByName("projectIdUpdateSelect").item(0).value;
	let url = "http://localhost:8080/" + who + "/" + id;
	let res = null;
	let JsonBody = "";
	fetch(url,{
		method:'GET'
	}).then(response =>response.json()
	.then(data=>{
		switch(who){
			case "members":
				/*
					<input type="text" name="idUpdate" style="display:none"/><br>
					
					<label for="firstNameUpdate">First Name(*):</label>
					<input type="text" name="firstNameUpdate" value="first name"/><br>
					
					<label for="lastNameUpdate">Last Name(*):</label>
					<input type="text" name="lastNameUpdate" placeholder="last name"/><br>
					
					<label for="birthdayUpdate">Birthday(*):</label>
					<input type="date" name="birthdayUpdate" placeholder=/><br>
					
					<label for="positionUpdate">Position(*):</label>
					<input type="text" name="positionUpdate" placeholder="CEO/CFO/Member/..."/><br>
					
					<label for="phoneUpdate">Phone:</label>
					<input type="text" name="phoneUpdate" placeholder="+49123456789"/><br>
				*/
				document.getElementsByName("memberIdUpdate").item(0).value = data.id;
				document.getElementsByName("firstNameUpdate").item(0).value = data.firstName;
				document.getElementsByName("lastNameUpdate").item(0).value = data.lastName;
				document.getElementsByName("birthdayUpdate").item(0).value = data.birthday;
				document.getElementsByName("positionUpdate").item(0).value = data.position;
				document.getElementsByName("phoneUpdate").item(0).value = data.phone;
				document.getElementById("updateMemberForm").setAttribute("style", "display:block");
				break;
			case "projects":
				console.log(url);
				/*
					<input type="text" name="projectIdUpdate" style="display:none"/><br>
					<label for="nameUpdate">Name:</label>
					<input type="text" name="nameUpdate" /><br>
					<label for="statusUpdate">Status:</label>
					<input type="text" name="statusUpdate" /><br>
					<label for="descUpdate">Description:</label>
					<textarea name="descUpdate" placeholder="Project Description" style="margin-top:30px; width:1000px; height: 80px; vertical-align: middle;"></textarea><br>
				*/
				document.getElementsByName("projectIdUpdate").item(0).value = data.id;
				document.getElementsByName("nameUpdate").item(0).value = data.name;
				document.getElementsByName("statusUpdate").item(0).value = data.status;
				document.getElementsByName("descUpdate").item(0).value = data.description;
				document.getElementById("updateProjectForm").setAttribute("style", "display:block");
		}
	})
	)
}
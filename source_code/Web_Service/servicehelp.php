<?php 
/**
 * PHP Web Service Helper File for Duke Laundry App. For Intro to Database Systems
 * final project. This file helps our service.php. Essentially, it is the web service. 
 * The service.php just calls the main method in this file and has nothing else in it.
 *
 * @author Rhondu Smithwick
 *
 * Backend to frontend connection info:
 * When updating database:
 * @param operation = makeUpdate REQUIRED
 * @param netID is the netID of student using OR ooo/available when being made out of order/ in order OR
 *								cleanUp (rest of parameters are optional)
 * @param machineID = id of machine being used REQUIRED 
 * @param duration is how long machine will be used for REQUIRED 
 * 								 OR  NULL if being made out of order/ in order
 * @param timeStamp current time in seconds (provided by PHP)
 * 
 * Wehn a user is moving through the app:
 * @param operation = moveThroughApp REQUIRED
 * @param campus the campus user is moving to (East, West, or Central) in App OPTIONAL
 * @param dorm the dorm user is moving to in App OPTIONAL
 * @param room the room the user is moving to in APP OPTIONAL, but REQUIRES dorm if present
 *
 * When a user wants to view machines in some location:
 * @param operation = viewMachines REQUIRED
 * @param campus the campus user is viewing machines on (East, West or Central) OPTIONAL
 * @param dorm the dorm user is view machines in OPTIONAL
 * @param room the room user is viewing machines in OPTIONAL, but required dorm if present
 *
 * When a user wants to view nearBy dorms:
 * @param operation = nearBy REQUIRED
 * @param netID = student REQUIRED
*/

/**
 * This function will take the $_GET array and run functions 
 * based on it. The key parameter is our operation, which will determine which 
 * functions we do. Machines that need to made available are always made available
 * anytime the web service is accessed. 
*/
function main()
{
	$timeStamp = floatval(date("H")) * 3600 + 
		floatval(date("i")) * 60 + 
		floatval(date("s"));

	// make available all machines that need to be
	$sqlToAvail = makeAvailable($timeStamp);

	$operation = $_GET['operation'];
	if ($operation == 'makeUpdate') {
		$sqlUpdate = makeUpdate($_GET['netID'], $_GET['machineID'], floatval($_GET['duration']), $timeStamp, $_GET['status']);
	} 
	
	elseif ($operation == 'moveThroughApp') {
		$sqlQuery = moveThroughApp($_GET['campus'], $_GET['dorm']);
	} 

	elseif ($operation == 'viewMachines') {
		$sqlQuery = viewMachines($_GET['campus'], $_GET['dorm'], $_GET['room'], $_GET['status']);
	} 

	elseif ($operation == 'nearBy') {
		$sqlQuery = nearBy($_GET['netID']);
	} 

	else {
		echo "Please enter a valid operation"; 
	}

	runQueries($sqlToAvail, $sqlUpdate, $sqlQuery);
}


/**
 * Establishes and checks connection to the database and runs the provided queries. 
 * Finally, it outputs JSON that the App will parse. 
 *
 * @param sqlToAvail the SQl query to make machines available
 * @param sqlUpdate the SQL query to update the database
 * @param sqlQuery the SQL query that will output information from the database 
*/
function runQueries($sqlToAvail, $sqlUpdate, $sqlQuery)
{
	$link = dbConnect();
	if (mysqli_connect_errno()) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}
	
	if (isset($sqlToAvail)) {
		mysqli_query($link, $sqlToAvail);
	}

	if (isset($sqlUpdate)) {
		mysqli_query($link, $sqlUpdate);
	}

	if (isset($sqlQuery)) {
		$result = mysqli_query($link, $sqlQuery);
		if (isset($result)) { 
			$resultArray = array();
			$tempArray = array();
			while($row = $result->fetch_object()) {
				$tempArray = $row;
			  array_push($resultArray, $tempArray);
			}
			echo json_encode($resultArray);
		}
	}

	mysqli_close($link);
}

/**
 * Connect to the database. 
*/
function dbConnect()
{
	$servername = "--redacted---";
	$username = "---redacted---";
	$password = "---redacted---";
	$dbname = "---redacted---";
	$link = mysqli_connect($servername, $username, $password, $dbname);
	return $link;
}

/**
 * Makes machines available that need to be. 
 *
 * @param timeStamp the current time in seconds
 * @return a SQL query that makes machines available 
*/
function makeAvailable($timeStamp)
{
	if (isset($timeStamp)) {
		$sqlToAvail = 
			"UPDATE MachineUse
			SET status = 'available', 
			studentID = NULL, 
			startTime = NULL, 
			endTime = NULL
			WHERE  ABS($timeStamp - startTime) 
			>= duration";
	}
	return $sqlToAvail;
}

/**
 * Updates the database manually. This applies when a machine becomes used by a student
 * or becomes in order/ out of order. We also include our database reset (cleanUp) in this function. 
 * @param netID the netID of the user (actual netID if being used, null if cleanUp or out of order/ in order)
 * @param machineID the machineID of machine being modified (null if cleanUp)
 * @param duration the duration that the machine will be in use or out of order (null if cleanUp or in order)
 * @param timeStamp the current time in seconds
 * @param status how the database is being modified (cleaenUp, busy, ooo, available)
 * @return a SQL query that modifies the database appropiately
*/
function makeUpdate($netID, $machineID, $duration, $timeStamp, $status)
{
	if (isset($netID)) {
		if ($netID == "cleanUp") {
			// Reset Machine. 
			$sqlUpdate = 
				"UPDATE MachineUse
				SET status = 'available',
				studentID = NULL,
				startTime = NULL,
				duration = NULL,
				endTime = NULL";
		}

		else if (isset($machineID) && isset($duration) && isset($timeStamp) && isset($status)) {
			if ($netID != "---") {
				// Change a machine from available to busy.
				$endTime = date("g:iA", ($timeStamp + $duration +18000) % 86400);
				$sqlUpdate = 
					"UPDATE MachineUse
					SET status = '$status', 
					startTime = $timeStamp, 
					endTime = '$endTime',
					studentID = '$netID',
					duration = $duration
					WHERE machineID = '$machineID'";
			}

			else {
				// Change a machine to either inorder (available) or out of order.
				$sqlUpdate = 
					"UPDATE MachineUse
					SET status = '$status', 
					studentID = NULL,
					duration = NULL,
					endTime = NULL
					WHERE machineID = '$machineID'";
			}
		}
	}

	return $sqlUpdate;
}

/**
 * Move through the application. This function is for when the user clicks on a portion of the 
 * application to find information. 
 * 
 * @param campus the campus the student is moving into (East, West, Central)
 * @param dorm the dorm the student is moving into
 * @return a SQL query that will give the relevant information
*/
function moveThroughApp($campus, $dorm)
{
	if (isset($dorm)) {
		// Get all rooms in a dorm.
		$sqlQuery = 
			"SELECT DISTINCT laundryRoom 
			FROM Machine
			WHERE dormName = '$dorm'
			ORDER BY laundryRoom"; 
	} 

	else if (isset($campus)) { 
		// Get all dorms on a campus.
		$sqlQuery = 
			"SELECT DISTINCT dormName 
			FROM Machine
			WHERE campus = '{$campus}'
			ORDER BY dormName";
	}

	else {
		// Get the campuses.
		$sqlQuery = 
			"SELECT DISTINCT campus 
			FROM Machine
			ORDER BY campus";
	}
	
	return $sqlQuery;
}

/**
 * The user wants to view the machines in a specific area. If they wish to 
 * view machines utside a room, a status must be provided. 
 *
 * @param campus the campus to view machines on
 * @param dorm the dorm to view machines on
 * @param room the room to view machines on
 * @param status the status for which machines should be
 * @return a SQL query that will get the appropiate information
*/
function viewMachines($campus, $dorm, $room, $status)
{
	if (isset($room) && isset($dorm)) { 
		if (isset($status)) {
			// View machines in a specific room of a status in a dorm
			$sqlQuery = 
				"SELECT dormName, laundryRoom,
				machineType, machineNum, endTime, studentID
				FROM MachineUse JOIN Machine
				ON MachineUse.machineID = Machine.ID
				WHERE dormName = '$dorm'  
				AND laundryRoom = '$room'  
				AND status = '$status'
				ORDER BY machineNum";
		}

		else {
			// View machines in a specific room in a dorm 
			$sqlQuery = 
				"SELECT dormName, laundryRoom, 
				machineType, machineNum,
				status, endTime, studentID 
				FROM MachineUse JOIN Machine
				ON MachineUse.machineID = Machine.ID
				WHERE dormName = '$dorm'  
				AND laundryRoom = '$room'  
				ORDER BY machineNum";
		}
	}


	else if (isset($dorm)) {
		if (isset($status)) {
			// View machines in a dorm on a campus of a status.
			$sqlQuery = 
				"SELECT dormName, laundryRoom,
				machineType, machineNum, endTime
				FROM MachineUse JOIN Machine
				ON MachineUse.machineID = Machine.ID
				WHERE dormName = '$dorm' 
				AND status = '$status'
				ORDER BY laundryRoom, machineNum";
		} 
		else {
			// View all the machines in a dorm. 
			$sqlQuery = 
				"SELECT laundryRoom, status,
				machineType, machineNum, 
				endTime, studentID 
				FROM MachineUse JOIN Machine
				ON MachineUse.machineID = Machine.ID
				WHERE dormName = '$dorm'    
				ORDER BY laundryRoom, machineNum";
		}
	}

	else if (isset($campus) && isset($status)) {	
		// View all the machines on a campus of a status. 
		$sqlQuery = 
			"SELECT dormName, laundryRoom, 
			machineType, machineNum, endTime
			FROM MachineUse JOIN Machine
			ON MachineUse.machineID = Machine.ID
			WHERE campus = '$campus'
			AND status = '$status'
			ORDER BY dormName, laundryRoom, machineNum";
	}

	else {
		// Get everything from Machine 
		$sqlQuery = 
			"SELECT * FROM Machine
			ORDER BY campus, dormName, laundryRoom, machineNum";
	}
	return $sqlQuery;
}

/**
 * When a student wants to view their nearBy dorms.
 * 
 * @param netID a student's netID
 * @return a SQL query with the appropiate information
*/
function nearBy($netID)
{
	if (isset($netID)) {
		$sqlQuery = 
			"SELECT * 
			FROM NearDorms
			WHERE dorm = (
			SELECT dormLivesIn
			FROM Student
			WHERE netID =  '$netID'
			)";
	}

	return $sqlQuery;
}

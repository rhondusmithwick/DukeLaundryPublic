Sensitive information and the iPhone App itself have been removed from this repository. You may request the iPhone App by emailing rhondu.smithwick@duke.edu. 

Please see the Final Report for more details on the Project.
See members.txt for a full description of each member's efforts.  
 
HOW TO RUN DUKE LAUNDRY: 
————————————————————————————————————————————————————————————————————————————————————————————————
TABLE OF CONTENTS:
- Navigating Through the Files
- Database Connection Info (---redacted---)
- Steps to Use the iPhone App and Simulation
- Sample Web Service Queries (---redacted---)
- Limitations to Current Implementation?

————————————————————————————————————————————————————————————————————————————————————————————————
NAVIGATING THROUGH THE FILES:
-Database Setup (folder) = populate the database/ certificate (Laundry.pem ---redacted---) to allow login to EC2 instance
-Generate machine data (folder) = Java code to create Machine and MachineUse data
-Generate netIDs & simulation (folder) = Java code to create student netIDs and simulation data for use of machines
-iPhone app (folder) = the iPhone app
-Web Service (folder) = PHP API for communication between database and simulation/frontend. Includes
        -service.php: A publicly available php file that is our web service; is “helped” by 
	servicehelp.php (which it requires); has barely anything in it
        -servicehelp.php: A file (not publicly available) that actually contains the code for the 
	      web service
-Simulation runner (folder) = Python to run the simulation

————————————————————————————————————————————————————————————————————————————————————————————————
DATABASE CONNECTION INFO (not required, but recommended):
---redacted--- 
————————————————————————————————————————————————————————————————————————————————————————————————
STEPS TO USE THE IPHONE APP AND SIMULATION:
1. Run the iPhone app in XCode (Note: You must have XCode downloaded):
   1. Open “Duke Laundry.xcodeproj” in XCode.
   2. Click on the “Play/Run button” in the upper left corner next to the “close, minimize, expand” buttons (or on mac, press command + r)
   3. XCode’s simulator software will open and you’ll see a virtual iPhone. At runtime, the app will be installed on the virtual iPhone and launch Duke Laundry app. 
   4. First screen should be the screen that asks for netID and choosing a campus. For a sample simulation, there are thousands of netIDs, campuses, dorms, and laundry rooms to choose from that all work. For one example, do the following:
      a. Input netID: fjw10 (all lowercase)
      b. Choose “East” for campus → click the next blue arrow button
      c. Choose “Gilbert-Addoms” for dorm → click the next blue arrow button
      d. Choose “002R” for laundry room → click the next blue arrow button
      e. Run the Python Simulation 
         - Will change many rooms, but we focus on this one 
      f. You will now see the laundry room and its machines updating their statuses in real time
      g. You may also, throughout this navigation at any point, click to see other views such as “View all machines”, “Machines by status”, “Nearby Laundry Rooms”, etc. 
2. Navigate as user wills. Sample steps are provided in final report’s screenshots
3. While the simulation is running, use the iPhone app to see visual updates in the database (say in a specific room or of a specific status) and items should be changing 
* If the grader is in PHPAdmin, they can get a bird’s eye view of what’s happening during the simulation by being on the MachineUse table and clicking the status column to put it into descending order. 
4. The grader may directly test the PHP API using urls if they desire. 
   - Sample web service queries are provided below

————————————————————————————————————————————————————————————————————————————————————————————————
SAMPLE WEB SERVICE QUERIES
---redacted---

————————————————————————————————————————————————————————————————————————————————————————————————
LIMITATIONS TO CURRENT IMPLEMENTATION?
We are currently using hypothesized Schema (see Survey and Assumptions section of Final Report). We are also using simulated (that is, manufactured based on supposition) data. Hence, if Duke were to use our Application, we would need to refit the backend and frontend to the real data and schema. We note that as of now, real data is impossible to obtain.
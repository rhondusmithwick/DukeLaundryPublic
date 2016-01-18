import java.util.ArrayList;
import java.util.Random;


public class laundryMachines {
	ArrayList<Machine> machines = new ArrayList<Machine>();
	int	id = 1;

	public class Machine {
		String id, status, student_id, machine_type;
		double start_time, duration;
	
		public Machine(String id, String status, String student_id, String machine_type, 
				double start_time, double duration) {
			this.id = id;
			this.status = status;
			this.student_id = student_id;
			this.machine_type = machine_type;
			this.start_time = start_time;
			this.duration = duration;
		}
		
		public Machine(String id, String machine_type, double duration) {
			this.id = id;
			this.machine_type = machine_type;
			this.duration = duration;
			this.status = null;
			this.student_id = null;
			this.start_time = 0;
		}
	}
	
	// Based on the machine locations created, make all the machines for machineUse
	public void makeMachines() {
		for (int i = 0; i < 290; i++) {
			String id, status, student_id, machine_type = null;
			double start_time = 0;
			double duration = 0;
			id = machines.get(i).id;
			status = "available";
			machine_type = machines.get(i).machine_type;
			duration = machines.get(i).duration;
			//INSERT INTO Machine VALUES('1', 'available', NULL, 29, NULL, 'washer');
			System.out.println("INSERT into MachineUse VALUES('" + id + "', '" + machine_type + "', '" +
					status + "', NULL, NULL, NULL" + ", NULL);");
		}   
	}
	
	// initialize and make all the machine locations/ machines for Machine
	public void makeMachineLocations() {
		//each room has 4 washers and 6 dryers
		// 2 rooms per campus
		// East: Epworth etc
		// West: Wannamaker etc
		// Central: TheCube 
		String[] campuses = {"East", "West", "Central"};
		String[] eastdorms = {"Alspaugh", "Bassett", "Brown", "Pegram", "East House", 
				"Epworth", "Giles", "Jarvis", "Wilson", "Gilbert-Addoms", "Southgate", 
				"Bell Tower", "Blackwell", "Randolph"};
        String[] westdorms = {"CravenA", "CravenB", "CravenC", "Craven D", "CravenE", 
        		"CravenF", "CrowellG", "CrowellH", "CrowellBB", "CrowellCC", "CrowellDD", 
        		"CrowellEE", "Edens1", "Edens2", "FewFF", "FewGG", "FewHH", "Keohane4A", 
        		"Keohane4B", "Keohane4E", "KilgoI", "KilgoK & L", "KilgoJ", "KilgoM & N", 
        		"KilgoO &P", "Wannamaker"};
        String[] centraldorms = {"Alpha Delta Pi", "Alpha Phi", "Black Cultural Living", 
        		"Chi Omega", "Delta Delta Delta", "Delta Gamma", "Delta Kappa Epsilon", 
        		"Jam!", "Kappa Alpha", "Kappa Alpha Theta", "Kappa Kappa Gamma", 
        		"Multicultural Greek", "Mundi", "Narnia", "Nexus", "Pi Beta Phi", 
        		"Pi Kappa Phi", "Psi Upsilon", "Smart Home", "Sigma Nu", 
        		"Sigma Phi Epsilon", "The Cube", "The Rabbit Hole", "Ubuntu", 
        		"Zeta Tau Alpha"};
		String[] laundryRooms = {"001T", "002R"};
		String[] washerID = {"A1", "A2", "A3", "A4"};
		String[] dryerID = {"B1", "B2", "B3", "B4", "B5", "B6", "B7"};
		int washers = 4;
		int dryers = 6;
		String campus, dorm_name, laundry_room, machine_num, machine_id = null;

		
		//INSERT INTO MachineLoc VALUES('West', 'wannamaker', '001', 'a1', '1');
//		machineLocationsHelper(eastdorms, "East");
//		machineLocationsHelper(westdorms, "West");
//		machineLocationsHelper(centraldorms, "Central");
		// for each campus
		for (int i = 0; i < campuses.length; i++) {
            // for each dorm on that campus
            if (i == 0) {
                // do all the east dorms
                machineLocationsHelper(eastdorms, "East");
            } else if (i == 1) {
                // do all west dorms
                machineLocationsHelper(westdorms, "West");
            } else if (i == 2) {
                // do all central dorms
                machineLocationsHelper(centraldorms, "Central");
            }

			
		}
	}
    
    public void machineLocationsHelper(String[]dorms, String campus) {
    	String[] laundryRooms = {"001T", "002R"};
    	String[] washerID = {"A1", "A2", "A3", "A4"};
        String[] dryerID = {"B1", "B2", "B3", "B4", "B5", "B6", "B7"};
        int washers = 4;
        int dryers = 6;
        String[] campuses = {"East", "West", "Central"};
        String dorm_name, laundry_room, machine_num, machine_id = null;

        //for each laundry room in the dorm:
        for (int i = 0; i < dorms.length; i++) {
	        for (int l = 0; l < 2; l++) {
	            // make 4 washers
	            for (int j = 0; j < washers; j++) {
	                dorm_name = dorms[i];
	                laundry_room = laundryRooms[l];
	                machine_id = Integer.toString(id);
	                machine_num = washerID[j];
//	                System.out.println("INSERT into Machine VALUES('" + machine_id + "', '" + machine_num +
//	                		"', '" + campus + "', '" + dorm_name + "', '" + laundry_room + "');");
	                Machine m = new Machine(machine_id, "washer", 0.100694);
	                machines.add(m);
	                id++;
	            }
	            // and 6 dryers
	            for (int k = 0; k < dryers; k++) {
	                dorm_name = dorms[i];
	                laundry_room = laundryRooms[l];
	                machine_id = Integer.toString(id);
	                machine_num = dryerID[k];
	                // id, machine_num, campus, dorm_name, laundry_room
//	                System.out.println("INSERT into Machine VALUES('" + machine_id + "', '" + machine_num +
//	                		"', '" + campus + "', '" + dorm_name + "', '" + laundry_room + "');");
	                Machine m = new Machine(machine_id, "dryer", 0.20833);
	                machines.add(m);
	                id++;
	            }
	        }
        }
    }
	
	public static void main(String[] args) {
		laundryMachines l = new laundryMachines();
		l.makeMachineLocations();
		l.makeMachines();

	}

}

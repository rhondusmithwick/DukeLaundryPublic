import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

public class MachineUsageSimulator {
	private static ArrayList<Integer> washerIdList = new ArrayList<Integer>();
	private static int	myId = 1;
	
	private static Map<String, ArrayList<Integer>> myMachineMap = new HashMap<String, ArrayList<Integer>>();
    
	/**
	 * populates the myMachineMap with the range of machine ids for each dorm
	 */
    public static void makeMachineLocations() {
		String[] campuses = {"East", "West", "Central"};
		String[] eastdorms = {"Alspaugh", "Bassett", "Brown", "Pegram", "East House", 
				"Epworth", "Giles", "Jarvis", "Wilson", "Gilbert-Addoms", "Southgate", 
				"Bell Tower", "Blackwell", "Randolph"};
		String[] westdorms = {"CravenA", "CravenB", "CravenC", "Craven D", "CravenE", 
				"CravenF", "CrowellG", "CrowellH", "CrowellBB", "CrowellCC", "CrowellDD", 
				"CrowellEE", "Edens1", "Edens2", "FewFF", "FewGG", "FewHH", "Keohane4A", 
				"Keohane4B", "Keohane4E", "KilgoI", "KilgoK & L", "KilgoJ", "KilgoM & N", 
				"KilgoO & P", "Wannamaker"};
		String[] centraldorms = {"Alpha Delta Pi", "Alpha Phi", "Black Cultural Living", 
				"Chi Omega", "Delta Delta Delta", "Delta Gamma", "Delta Kappa Epsilon", 
				"Jam!", "Kappa Alpha", "Kappa Alpha Theta", "Kappa Kappa Gamma", 
				"Multicultural Greek", "Mundi", "Narnia", "Nexus", "Pi Beta Phi", 
				"Pi Kappa Phi", "Psi Upsilon", "Smart Home", "Sigma Nu", 
				"Sigma Phi Epsilon", "The Cube", "The Rabbit Hole", "Ubuntu", 
		"Zeta Tau Alpha"};
		
		machineLocationsHelper(eastdorms, "East");
		machineLocationsHelper(westdorms, "West");
		machineLocationsHelper(centraldorms, "Central");
	}
    
    /**
	 * helper function for populating the map
	 */
    public static void machineLocationsHelper(String[] dorms, String campus) {
		int washers = 4;
		int dryers = 6;
		
    	//for each laundry room in the dorm:
		for (int i = 0; i < dorms.length; i++) {
			ArrayList<Integer> currRange = new ArrayList<Integer>();
			currRange.add(myId);
			
			System.out.println(dorms[i]);
			System.out.println(currRange);
			myMachineMap.put(dorms[i], currRange);
			
			for (int l = 0; l < 2; l++) {
				// make 4 washers
				for (int j = 0; j < washers; j++) {
					washerIdList.add(myId);
					myId++;
				}
				// and 6 dryers
				for (int k = 0; k < dryers; k++) {
					myId++;
				}
			}
			
			currRange.add(myId);
			System.out.println(currRange);
			myMachineMap.put(dorms[i], currRange);
			System.out.println(myMachineMap);
		}
	}
    
	
	/**
	 * Reads a file of all student net ids within a specific dorm.
	 * @param fileName of Dorm netids
	 * @return
	 */
	private static ArrayList<String> readFile(String fileName){
		ArrayList<String> names = new ArrayList<String>();
		
		String line = null;

		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			// Note that write() does not automatically append a newline character.
			while((line = bufferedReader.readLine()) != null) {
				names.add(line);
			}

			// Always close files.
			bufferedReader.close();         
		}
		catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");                
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		return names;
	}

	/**
	 * writes to a file the simulation of machines in use at a certain timestamp
	 * @param fileName
	 * @param sim
	 */
	private static void writeFile(String fileName, Map<Double, List<ArrayList<String>>> sim){

		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter("sim/" + fileName+ "output.txt");
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			Set keys = sim.keySet();
			for (Iterator i = keys.iterator(); i.hasNext();) {
				Double key = (Double) i.next();
				List<ArrayList<String>> values = (List<ArrayList<String>>) sim.get(key);

				for(ArrayList<String> eachPerson : values){
					bufferedWriter.write(key.toString());
					for(String item : eachPerson){
						bufferedWriter.write(", " + item);
					}
					bufferedWriter.newLine();
				}
			}
			
			bufferedWriter.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Determines if a certain student in a dorm is currently using a machine.
	 * @return
	 */
	private static boolean usingMachine(){
		Random random = new Random();
		int r = random.nextInt(2);
		if(r==0) return false;
		else return true;
	}
	
	/**
	 * Generates a random Machine ID that the student will be using.
	 * @return
	 */
	private static int generateMachineId(String dormName){
		ArrayList<Integer> machineRange = myMachineMap.get(dormName);
		System.out.println(machineRange);
		Random random = new Random();
		int machine_id = random.nextInt((machineRange.get(1) - machineRange.get(0))) + machineRange.get(0);
		return machine_id;
	}
	
	/**
	 * Determines if the machine is a washer or dryer based upon its ID. All the IDs are associated with a type in the DB table.
	 * @param machine_id
	 * @return
	 */
	private static String generateMachineType(int machine_id){
		if(washerIdList.contains(machine_id)){
			return "washer";
		}else{
			return "dryer";
		}
	}
	
	/**
	 * Generates a random time stamp in intervals of 6.25 seconds (which represents 30 min of a 24 hr day in our simulation).
	 * @return
	 */
	private static double generateTimeStamp(){
		Random random = new Random();
		int numIntervals = (int) (300.0/6.25);
		double r = random.nextInt(numIntervals); 
		double simTimeStamp = r*(6.25);
		return simTimeStamp;
	}
	
	/**
	 * determines whether or not a machine is in order or out of order. There is a 5% chance that the machine is out of order.
	 * @return true if in order
	 */
	private static boolean generateMachineActive(){
		Random random = new Random();
		int num = random.nextInt(100);
		return num>4;
	}
	
	/**
	 * generates the machine duration dependent on the machine type
	 * @param machineType
	 * @return
	 */
	private static double generateMachineDuration(String machineType, boolean machineActive){
		Integer[] washerTimes = {25,27,29};
		
		if(machineActive==false){
			return 75;
		}else if(machineType=="washer"){
//			Random rand = new Random();
//			int num = rand.nextInt(3);
//			return washerTimes[num];
			return 6.25;
		}else if(machineType=="dryer"){
			return 12.5;
		}else{
			System.out.println("incorrect machine type");
			return 0.0;
		}
	}
	
	/**
	 * This generates the simulation and students using a machine at a specific time stamp.
	 * @param names of students
	 * @return
	 */
	private static Map<Double, List<ArrayList<String>>> generateUsage(ArrayList<String> names, String dormName){
		Map<Double, List<ArrayList<String>>> simulation = new TreeMap<Double, List<ArrayList<String>>>();

		for (String name : names){
			if(usingMachine() == true){ //checks if the person is using a machine
				ArrayList<String> info = new ArrayList<String>(); //each person
				double timeStamp = generateTimeStamp(); 
				System.out.println(timeStamp);
				int machine_id = generateMachineId(dormName);
				String machine_type = generateMachineType(machine_id);
				
				boolean machine_active = generateMachineActive();
				
				double machine_duration = generateMachineDuration(machine_type, machine_active);
				
				info.add(machine_id+"");
				info.add(machine_type);
				info.add(machine_duration+"");
				
				if(machine_active){
					info.add(name);
					info.add("busy");
				}else{
//					info.add("null");
					info.add("---");
					info.add("ooo");
				}
				
				//if a machine is out of order, immediately simulate a time that the machine would become back in order
				if(info.get(4).equals("ooo")){
					double backInOrder = (timeStamp + 75); //time the machine goes back in order (6 hours)
					ArrayList<String> newinfo = new ArrayList<String>(); 
					newinfo.add(info.get(0));
					newinfo.add(info.get(1));
					newinfo.add(info.get(2));
					newinfo.add("---");
					newinfo.add("available");
					
					if(simulation.containsKey(backInOrder)){ //checks if people are already using a machine at a certain time
						ArrayList<ArrayList<String>> currList = (ArrayList<ArrayList<String>>) simulation.get(backInOrder);
						currList.add(newinfo);
						simulation.put(backInOrder, currList);
					}else{
						ArrayList<ArrayList<String>> currList = new ArrayList<ArrayList<String>>();
						currList.add(newinfo);
						simulation.put(backInOrder, currList);
					}
				}

				//otherwise insert into the simulation data
				if(simulation.containsKey(timeStamp)){ //checks if people are already using a machine at a certain time
					ArrayList<ArrayList<String>> currList = (ArrayList<ArrayList<String>>) simulation.get(timeStamp);
					currList.add(info);
					simulation.put(timeStamp, currList);
				}else{
					ArrayList<ArrayList<String>> currList = new ArrayList<ArrayList<String>>();
					currList.add(info);
					simulation.put(timeStamp, currList);
				}
			}
		}
		System.out.println(simulation);
	    return simulation;
	}

	
	public static void main (String[] args){
		makeMachineLocations();
		
		String[] allDorms = {"Alspaugh", "Bassett", "Brown", "Pegram", "East House", 
				"Epworth", "Giles", "Jarvis", "Wilson", "Gilbert-Addoms", "Southgate", 
				"Bell Tower", "Blackwell", "Randolph","CravenA", "CravenB", "CravenC", "Craven D", "CravenE", 
				"CravenF", "CrowellG", "CrowellH", "CrowellBB", "CrowellCC", "CrowellDD", 
				"CrowellEE", "Edens1", "Edens2", "FewFF", "FewGG", "FewHH", "Keohane4A", 
				"Keohane4B", "Keohane4E", "KilgoI", "KilgoK & L", "KilgoJ", "KilgoM & N", 
				"KilgoO & P", "Wannamaker","Alpha Delta Pi", "Alpha Phi", "Black Cultural Living", 
				"Chi Omega", "Delta Delta Delta", "Delta Gamma", "Delta Kappa Epsilon", 
				"Jam!", "Kappa Alpha", "Kappa Alpha Theta", "Kappa Kappa Gamma", 
				"Multicultural Greek", "Mundi", "Narnia", "Nexus", "Pi Beta Phi", 
				"Pi Kappa Phi", "Psi Upsilon", "Smart Home", "Sigma Nu", 
				"Sigma Phi Epsilon", "The Cube", "The Rabbit Hole", "Ubuntu","Zeta Tau Alpha"};
		
		for(String dorm : allDorms){
			String fileName = "netids/" + dorm + ".txt";
			String dormName = fileName.substring(7, fileName.length()-4);
			ArrayList<String> netids = readFile(fileName);
			Map<Double, List<ArrayList<String>>> answer = generateUsage(netids, dormName);
			writeFile(dormName, answer);
		}
	}
}

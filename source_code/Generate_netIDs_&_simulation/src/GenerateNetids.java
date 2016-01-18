import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class GenerateNetids {
	private static Set<String> myAllNetIds = new HashSet<String>();
	private static String alph = "abcdefghijklmnopqrstuvwxyz";
	private static String nums = "0123456789";
	
	private static String generateNetid(){
		String myNetId = "";
		for(int i=0; i<3; i++){
			Random random = new Random();
			int r = random.nextInt(26);
			myNetId += alph.charAt(r);
		}
		
		for(int i=0; i<2; i++){
			Random random = new Random();
			int r = random.nextInt(10);
			myNetId += nums.charAt(r);
		}
		
		return myNetId;
	}
	
	public static Set<String> netidDorm(int n){
	    Set<String> netids = new TreeSet<String>();
	    while (netids.size() < n){
	    	String netid = "";
	    	for(int i=0; i<3; i++){
	    		netid = generateNetid();
	    	}
	        if(!myAllNetIds.contains(netid)){
	            netids.add(netid);
	            myAllNetIds.add(netid);
	        }
	    }
	    return netids;
	}

	public static void writeFile(String dorm, int n){
	    Set<String> Dorm_Netids = netidDorm(n);
	    FileWriter fileWriter;
		try {
			fileWriter = new FileWriter("netids/"+ dorm + ".txt");
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		
			for(String currString : Dorm_Netids) {
		        bufferedWriter.write(currString);
		        bufferedWriter.newLine();
			}
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	    
	public static void main (String[] args){
	    writeFile("Wannamaker",200);
	    writeFile("Epworth",50);
	    writeFile("The Cube",40);
	    writeFile("Alspaugh",250);
	    writeFile("East House",90);
	    writeFile("Kappa Alpha Theta",50);
	    writeFile("Kappa Kappa Gamma",50);
	    writeFile("Multicultural Greek",30);
	    writeFile("Mundi",30);
	    writeFile("Narnia",20);
	    writeFile("Nexus",60);
	    writeFile("Pi Beta Phi",50);
	    writeFile("Pi Kappa Phi",50);
	    writeFile("Psi Upsilon",50);
	    writeFile("Smart Home",10);
	    writeFile("Sigma Nu",50);
	    writeFile("Sigma Phi Epsilon",50);
	    writeFile("Giles",250);
	    writeFile("The Rabbit Hole",30);
	    writeFile("Ubuntu",70);
	    writeFile("Zeta Tau Alpha",50);
	    writeFile("Jarvis",100);
	    writeFile("Wilson",200);
	    writeFile("Gilbert-Addoms",250);
	    writeFile("Southgate",200);
	    writeFile("Bassett",250);
	    writeFile("Bell Tower",200);
	    writeFile("Blackwell",200);
	    writeFile("Randolph",200);
	    writeFile("CravenA",75);
	    writeFile("CravenB",75);
	    writeFile("CravenC",75);
	    writeFile("Craven D",75);
	    writeFile("CravenE",75);
	    writeFile("CravenF",75);
	    writeFile("CrowellG",75);
	    writeFile("Brown",150);
	    writeFile("CrowellH",75);
	    writeFile("CrowellBB",75);
	    writeFile("CrowellCC",75);
	    writeFile("CrowellDD",75);
	    writeFile("CrowellEE",75);
	    writeFile("Edens1",200);
	    writeFile("Edens2",200);
	    writeFile("FewFF",100);
	    writeFile("FewGG",100);
	    writeFile("FewHH",100);
	    writeFile("Pegram",200);
	    writeFile("Keohane4A",100);
	    writeFile("Keohane4B",100);
	    writeFile("Keohane4E",150);
	    writeFile("KilgoI",75);
	    writeFile("KilgoK & L",100);
	    writeFile("KilgoJ",75);
	    writeFile("KilgoM & N",100);
	    writeFile("KilgoO & P",100);
	    writeFile("Alpha Delta Pi",50);
	    writeFile("Black Cultural Living",50);
	    writeFile("Chi Omega",100);
	    writeFile("Delta Delta Delta",75);
	    writeFile("Delta Gamma",50);
	    writeFile("Delta Kappa Epsilon",75);
	    writeFile("Jam!",30);
	    writeFile("Kappa Alpha",50);
	    writeFile("Alpha Phi",40);
	}
}

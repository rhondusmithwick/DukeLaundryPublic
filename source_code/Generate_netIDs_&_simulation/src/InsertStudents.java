import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class InsertStudents {
	private static ArrayList<String> allInfo = new ArrayList<String>();
	
	private static void readAndWriteFile(String fileName){
		String dorm = fileName.substring(7, fileName.length()-4);
		String line = null;

		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			FileWriter fileWriter = new FileWriter("inserts/" + dorm+ "InsertStatements.txt");
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			while((line = bufferedReader.readLine()) != null) {
				bufferedWriter.write("INSERT INTO Student VALUES ("+ "'" + line + "'" + ",'" + dorm + "'"+ ");");
				bufferedWriter.newLine();
			}
			
			bufferedWriter.close();
			bufferedReader.close();         
		}
		catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");                
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	private static void readLargeFile(String fileName){
		String line = null;

		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			// Note that write() does not automatically append a newline character.
			while((line = bufferedReader.readLine()) != null) {
				allInfo.add(line);
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
//		return allInfo;
	}
	
	private static void writeLargeFile(ArrayList<String> info){
		try {
			FileWriter fileWriter2 = new FileWriter("AllInsertStatements.txt");
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter2);

			for (String thing : info) {
				bufferedWriter.write(thing);
				System.out.println(thing);
				bufferedWriter.newLine();
			}

			bufferedWriter.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	

	public static void main (String[] args){
		ArrayList<String> fileNames = new ArrayList<String>();
		
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
			fileNames.add("inserts/" + dorm+ "InsertStatements.txt");
			readAndWriteFile(fileName);
		}
		
		for(String name : fileNames){
			readLargeFile(name);
		}
		
		writeLargeFile(allInfo);
		
//		readAndWriteFile("src/Epworth.txt");
//		readAndWriteFile("src/The Cube.txt");
//		readAndWriteFile("src/Wannamaker.txt");
//		readAndWriteFile("src/Alspaugh.txt");
//		readAndWriteFile("src/East House.txt");
//		readAndWriteFile("src/Kappa Alpha Theta.txt");
//	    readAndWriteFile("src/Kappa Kappa Gamma.txt");
//	    readAndWriteFile("src/Multicultural Greek.txt");
//	    readAndWriteFile("src/Mundi.txt");
//	    readAndWriteFile("src/Narnia.txt");
//	    readAndWriteFile("src/Nexus.txt");
//	    readAndWriteFile("src/Pi Beta Phi.txt");
//	    readAndWriteFile("src/Pi Kappa Phi.txt");
//	    readAndWriteFile("src/Psi Upsilon.txt");
//	    readAndWriteFile("src/Smart Home.txt");
//	    readAndWriteFile("src/Sigma Nu.txt");
//	    readAndWriteFile("src/Sigma Phi Epsilon.txt");
//	    readAndWriteFile("src/Giles.txt");
//	    readAndWriteFile("src/The Rabbit Hole.txt");
//	    readAndWriteFile("src/Ubuntu.txt");
//	    readAndWriteFile("src/Zeta Tau Alpha.txt");
//	    readAndWriteFile("src/Jarvis.txt");
//	    readAndWriteFile("src/Wilson.txt");
//	    readAndWriteFile("src/Gilbert-Addoms.txt");
//	    readAndWriteFile("src/Southgate.txt");
//	    readAndWriteFile("src/Bassett.txt");
//	    readAndWriteFile("src/Bell Tower.txt");
//	    readAndWriteFile("src/Blackwell.txt");
//	    readAndWriteFile("src/Randolph.txt");
//	    readAndWriteFile("src/CravenA.txt");
//	    readAndWriteFile("src/CravenB.txt");
//	    readAndWriteFile("src/CravenC.txt");
//	    readAndWriteFile("src/Craven D.txt");
//	    readAndWriteFile("src/CravenE.txt");
//	    readAndWriteFile("src/CravenF.txt");
//	    readAndWriteFile("src/CrowellG.txt");
//	    readAndWriteFile("src/Brown.txt");
//	    readAndWriteFile("src/CrowellH.txt");
//	    readAndWriteFile("src/CrowellBB.txt");
//	    readAndWriteFile("src/CrowellCC.txt");
//	    readAndWriteFile("src/CrowellDD.txt");
//	    readAndWriteFile("src/CrowellEE.txt");
//	    readAndWriteFile("src/Edens1.txt");
//	    readAndWriteFile("src/Edens2.txt");
//	    readAndWriteFile("src/FewFF.txt");
//	    readAndWriteFile("src/FewGG.txt");
//	    readAndWriteFile("src/FewHH.txt");
//	    readAndWriteFile("src/Pegram.txt");
//	    readAndWriteFile("src/Keohane4A.txt");
//	    readAndWriteFile("src/Keohane4B.txt");
//	    readAndWriteFile("src/Keohane4E.txt");
//	    readAndWriteFile("src/KilgoI.txt");
//	    readAndWriteFile("src/KilgoK & L.txt");
//	    readAndWriteFile("src/KilgoJ.txt");
//	    readAndWriteFile("src/KilgoM & N.txt");
//	    readAndWriteFile("src/KilgoO & P.txt");
//	    readAndWriteFile("src/Alpha Delta Pi.txt");
//	    readAndWriteFile("src/Black Cultural Living.txt");
//	    readAndWriteFile("src/Chi Omega.txt");
//	    readAndWriteFile("src/Delta Delta Delta.txt");
//	    readAndWriteFile("src/Delta Gamma.txt");
//	    readAndWriteFile("src/Delta Kappa Epsilon.txt");
//	    readAndWriteFile("src/Jam!.txt");
//	    readAndWriteFile("src/Kappa Alpha.txt");
	}
	
}

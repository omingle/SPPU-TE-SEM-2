package AssemblerPass2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class AssignA2 {
	
	ArrayList<allTabRow> SYMTAB;
	ArrayList<allTabRow> LITTAB;
	
	public AssignA2() {
		SYMTAB = new ArrayList<allTabRow>();
		LITTAB = new ArrayList<allTabRow>();
	}
	
	public void readTabs() {
		BufferedReader br;
		String line;
		
		try {
			br = new BufferedReader(new FileReader("SYMTAB.txt"));
			while((line = br.readLine()) != null) {
				String split[] = line.split("\\s+");
				SYMTAB.add(new allTabRow(split[1], Integer.parseInt(split[2]), Integer.parseInt(split[0])));
			}
			br.close();
			
			br = new BufferedReader(new FileReader("LITTAB.txt"));
			while((line = br.readLine()) != null) {
				String split[] = line.split("\\s+");
				LITTAB.add(new allTabRow(split[1], Integer.parseInt(split[2]), Integer.parseInt(split[0])));
			}
			br.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void generateMCode() throws Exception {
		readTabs();
		
		BufferedReader br;
		BufferedWriter bw;
		
		br = new BufferedReader(new FileReader("input.txt"));
		bw = new BufferedWriter(new FileWriter("output.txt"));
		
		String line, icode;
		int curLC = 0;
		
		while((line = br.readLine()) != null) {
			String split[] = line.split("\\t+");
			
			if(!split[1].contains("AD")) {
				bw.write(split[0] + "\t");
				curLC = Integer.parseInt(split[0].replaceAll("\\D", ""));
			}
			
			if(split[1].contains("AD")) {
//				if(split[1].contains("AD"))
//					bw.write("\t");
					
//				bw.write("+\n");
				
				continue;
			}
			else if(split[1].contains("DL, 02")) {
				int storage = Integer.parseInt(split[2].replaceAll("\\D", ""));
				for(int i = curLC+1; i<curLC+storage; i++)
					bw.write("\n" + i + ".");
				
				bw.write("\n");
			}
			else if(split.length == 2 && split[1].contains("IS")) {
				int op = Integer.parseInt(split[1].replaceAll("\\D", ""));
				icode = "+\t" + String.format("%02d", op) + "\t0\t" + String.format("%03d", 0) + "\n";
				bw.write(icode);
			}
			else if(split.length == 3) {
				if(split[1].contains("DL, 01")) {
					int constant = Integer.parseInt(split[2].replaceAll("\\D", ""));
					icode = "+\t00\t0\t" + String.format("%03d", constant) + "\n";
					bw.write(icode);
				}
				else if(split[1].contains("IS")) {
					int op = Integer.parseInt(split[1].replaceAll("\\D", ""));
					
					if(op == 10) {
						if(split[2].contains("S")) {
							int symIndex = Integer.parseInt(split[2].replaceAll("\\D", ""));
							icode = "+\t" + String.format("%02d", op) + "\t0\t" + String.format("%03d", SYMTAB.get(symIndex-1).getAddress()) + "\n";
							bw.write(icode);
						}
						else if(split[2].contains("L")) {
							int litIndex = Integer.parseInt(split[2].replaceAll("\\D", ""));
							icode = "+\t" + String.format("%02d", op) + "\t0\t" + String.format("%03d", LITTAB.get(litIndex-1).getAddress()) + "\n";
							bw.write(icode);
						}
					}
				}
			}
			else if(split.length == 4 && split[1].contains("IS")) {
				int op = Integer.parseInt(split[1].replaceAll("\\D", ""));
				int reg = Integer.parseInt(split[2].replaceAll("\\D", ""));
				
				if(split[3].contains("S")) {
					int symIndex = Integer.parseInt(split[3].replaceAll("\\D", ""));
					icode = "+\t" + String.format("%02d", op) + "\t" + reg + "\t" + String.format("%03d", SYMTAB.get(symIndex-1).getAddress()) + "\n";
					bw.write(icode);
				}
				else if(split[3].contains("L")) {
					int litIndex = Integer.parseInt(split[3].replaceAll("\\D", ""));
					icode = "+\t" + String.format("%02d", op) + "\t" + reg + "\t" + String.format("%03d", LITTAB.get(litIndex-1).getAddress()) + "\n";
					bw.write(icode);
				}
			}
		}
		bw.close();
		br.close();
		
		// Sorting m/c code according to LC
		
		ArrayList<String> al = new ArrayList<String>();
		LinkedHashMap<String, String> lhm = new LinkedHashMap<String, String>(); 
		
		br = new BufferedReader(new FileReader("output.txt"));

		while((line = br.readLine()) != null) {
			al.add(line);
		}
		br.close();
		
		Collections.sort(al);
		
		for(int i=0; i<al.size(); i++) {
			String split[] = al.get(i).split("\\t+");
			if(split.length == 5)
				lhm.put(split[0], split[1] + "\t" + split[2] + "\t" + split[3] + "\t" + split[4]);
			else
				lhm.put(split[0], "");
		}
			
		bw = new BufferedWriter(new FileWriter("output.txt"));
		
		for(Map.Entry<String, String> m : lhm.entrySet()) {
			bw.write(m.getKey() + "\t" + m.getValue() + "\n");
			System.out.println(m.getKey() + "\t" + m.getValue());
		}
		  
		bw.close();
	}
	
	public static void main(String args[]) throws Exception {
		AssignA2 passOne = new AssignA2();
		passOne.generateMCode();
	}
}

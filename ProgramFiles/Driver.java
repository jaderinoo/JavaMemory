import java.io.FileNotFoundException;

import java.io.FileReader;
import java.util.Scanner;

import java.math.*;

/*
 * Jad El-khatib
 * Cosc 321 Project 2
 */

public class Driver {
	static Scanner in = new Scanner(System.in);
	static int blocksize = 4;static int cachelines = 8;static int cachetime = 250;static int memtime = 750;
	public static void main(String[] args) {

		//Main Menu
		System.out.println("Please choose an option: \n1: Configure the system \n2: Run a simulation using an input file \n3: Quit.");

		int main = in.nextInt();
		switch (main) {
        case 1:  						// Configure the system
        	Configure();
        	break;
        	
        case 2: 						// Run sim with input file
        	Simulate(blocksize, cachelines, cachetime, memtime);
        	break;
        			
        case 3:							//quit
        	System.exit(0);
        	break;
        	
        default: 
        	System.out.println("Please make a selection");
        	break;
        	
		}
	}
	
	public static void Configure() {

		//Configuration Menu
		System.out.println("Please choose an option: \n1: Block/Cache line size (# of words) \n2: Cache memory size (# of cache lines) \n3: Cache access time (nsec) \n4: Memory access time (nsec) \n5: Return");
		int config = in.nextInt();
		switch (config) {
        case 1:  						// Edit Block/Cache line size
        	System.out.println("Current int = " + blocksize);
        	blocksize = in.nextInt();
        	Configure();
        	break;
        	
        case 2: 						// Cache memory size
        	System.out.println("Current int = " + cachelines);
        	cachelines = in.nextInt();
        	Configure();
        	break;
        	
        case 3:							// Cache access time
        	System.out.println("Current int = " + cachetime);
        	cachetime = in.nextInt();
        	Configure();
        	break;
        	
        case 4:							// Memory access time
        	System.out.println("Current int = " + memtime);
        	memtime = in.nextInt();
        	Configure();
        	break;
        	
        case 5:							// Menu
        	main(null);
        	break;
        	
        default: 
        	System.out.println("Please make a selection");
        	Configure();
        	break;
		}
	}
	
	public static void Simulate(int blocksize, int cachelines, int cachetime, int memtime) {		// run sim with input file 
		System.out.println("\nthe current setup is: \nCache line size: " + blocksize + "\nCache memory size: " + cachelines + "\nCache access time: " + cachetime + "\nMemory access time: " + memtime);		// test to make sure vars work
		
		// load input
		 try {
			System.out.println("Please enter an input file name: ");
			String inputfile = in.next();
			Scanner inFile = new Scanner(new FileReader(inputfile));
			System.out.println("");
			for (int i = 0; i <= 5; i++){
				
			int lower = 0;
			int upper = 0; 
			
			inFile.nextLine();	// Skips the first line of the input file
			
			lower = inFile.nextInt();		// gets the low
			upper = inFile.nextInt();		// gets the high
			
			System.out.println("The selected range is: " + lower + " - " + upper);		// Detects the low bound and high bound of the range selected.
			
			double Effectiveaccesstime = 0;
			int hitratio = 0;
			int misscount = 0;
			int totallines = ((upper) / blocksize) + 1;
			int Cacheblock[] = new int[cachelines];
			int lines = (totallines) - (lower/blocksize);
			int hits = lines * (blocksize-1);
			int index = 0;
			
			for (int x = 0; index < totallines; x++){
				Cacheblock[x] = index * blocksize; 
				index++; 
				if(x == cachelines - 1){
					x = -1;
				}
			}
			
			for (int x = (cachelines - 1); x >= 0; x--)
			{
				if((x == 0) || (Cacheblock[x] != 0 && x != 0))
				System.out.println("Line " + x + ": " + Cacheblock[x]);
			}
						
			hits -= (lower % blocksize);
			hits -= ((blocksize-1) - (upper % blocksize));
			
			System.out.println("hits: " + hits + "\tmiss: " + lines);			
			misscount = lines;
			
			// calc hit ratio
			
			hitratio = ( hits * 100 ) / (hits+misscount) ;		
			System.out.println("Hitratio = " + hitratio + "%");

			// calc effective access time
			
			Effectiveaccesstime = ((hits)*(cachetime) + (misscount)*(memtime)) / (misscount+hits);
			System.out.println("EffectiveAccessTime: " + Effectiveaccesstime +"\n");
			
			}
		} catch (FileNotFoundException e) {
			System.out.println("File load Error");
			e.printStackTrace();
			System.exit(0);
		}	 
		 System.out.println("");
		main(null);
	}
	
}
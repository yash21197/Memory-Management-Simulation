
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class MemorySimulatorBase{
	static final char FREE_MEMORY = '.';
	static final char RESERVED_MEMORY = '#';
	int CURRENT_TIME = -1;
	char[] main_memory;
	ArrayList<Process> processes;
	
	public MemorySimulatorBase(String fileName){
		main_memory = new char[Externals.MAIN_MEMORY_SIZE];
		processes = InputFileParser.parseInputFile(fileName);
		initializeMainMemory();
	}
	
	public abstract int getNextSlot(int slotSize);
	
	public void timeStep(){
		CURRENT_TIME++;
		while(!eventOccursAt(CURRENT_TIME)){
			CURRENT_TIME++;
		}
		
		ArrayList<Process> toRemove = new ArrayList<Process>();
		for (Process p : processes) {
			if (p.getEndTime() == CURRENT_TIME) {
				removeFromMemory(p);
				toRemove.add(p);
			}
		} 
		for (Process p : toRemove) {
			processes.remove(p);
		}
		
		for (Process p : processes) {
			if (p.getStartTime() == CURRENT_TIME) {
				putInMemory(p);
			}
		}
	}

	public void timeStepUntil(int t){
		while(CURRENT_TIME < t){
			CURRENT_TIME++;
			while(!eventOccursAt(CURRENT_TIME) && CURRENT_TIME < t){
				CURRENT_TIME++;
			}
			
			ArrayList<Process> toRemove = new ArrayList<Process>();
			for (Process p : processes) {
				if (p.getEndTime() == CURRENT_TIME) {
					removeFromMemory(p);
					toRemove.add(p);
				}
			} 
			for (Process p : toRemove) {
				processes.remove(p);
			}
			
			for (Process p : processes) {
				if (p.getStartTime() == CURRENT_TIME) {
					putInMemory(p);
				}
			}
		}
	}
	
	public boolean eventOccursAt(int time){
		for(int i=0;i<processes.size();i++){
			Process p = processes.get(i);
			if (p.getStartTime() == time || p.getEndTime() == time) {
				return true;
			}
		}
		return false;
	}
	
	public void putInMemory(Process p){
		int targetSlot = getNextSlot(p.getSize());
		if(targetSlot == -1){
			defragment();
			targetSlot = getNextSlot(p.getSize());
			if(targetSlot == -1){
				Externals.outOfMemoryExit();
			}
		}

		for(int i=0;i<p.getSize();i++){
			main_memory[i+targetSlot] = p.getPid();
		}
	}
	
	public void removeFromMemory(Process p){
		for (int i=0;i<main_memory.length;i++){
			if(main_memory[i] == p.getPid()){
				main_memory[i] = FREE_MEMORY;
			}
		}
	}


	public void initializeMainMemory(){
		for(int i=0;i<Externals.Reserve_size && i<main_memory.length;i++){
			main_memory[i] = RESERVED_MEMORY;
		}
		for(int i=Externals.Reserve_size;i<main_memory.length;i++){
			main_memory[i] = FREE_MEMORY;
		}
	}

	public void printMemory(){
		System.out.print("Memory at time " + CURRENT_TIME + ":");
		for(int i=0;i<main_memory.length;i++){
			if(i%Externals.Reserve_size==0){
				System.out.println("");
			}
			System.out.print(main_memory[i]);
		}
		System.out.println();
	}
	
	public void defragment(){
		HashMap<Character,Integer> processesMoved = new HashMap<Character,Integer>();
		DecimalFormat f = new DecimalFormat("##.00");
		
		System.out.println("Performing defragmentation...");
		
		int destination=main_memory.length;
		
		for(int i=0;i<main_memory.length;i++){
			if(main_memory[i] == FREE_MEMORY){
				destination = i;
				break;
			}
		}

		if(destination!=main_memory.length){
			for(int i=destination+1;i<main_memory.length;i++){
				if(main_memory[i] != FREE_MEMORY && main_memory[i] != RESERVED_MEMORY && i != destination){
					main_memory[destination] = main_memory[i];
					main_memory[i] = FREE_MEMORY; 
					destination++;
					processesMoved.put(main_memory[i], null);
				}
			}
		}
		
		int numMoved = processesMoved.size();
		int freeBlockSize = main_memory.length - destination;
		double percentage = (double)freeBlockSize / (double)main_memory.length;
		
		System.out.println("Defragmentation completed.");
		System.out.println("Relocated " + numMoved + " processes " + "to create a free memory block of " + freeBlockSize + " units " + "(" + f.format(percentage * 100) + "% of total memory).");
	}
		
	public int processesRemaining() {
		return processes.size();
	}
}
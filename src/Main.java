import java.util.Scanner;

public class Main{
	public static void main(String[] args){
		if(args.length != 2){
			Externals.invalidUsageExit();
		}
		
		String simName = args[1].trim();
		String inputFileName = args[0].trim();
		MemorySimulatorBase sim = null;
		Scanner scanner = new Scanner(System.in);
		int userInput;
		
		if(simName.equals("first")){
			sim = new FirstFitMemorySimulator(inputFileName);
		}else if(simName.equals("best")){
			sim = new BestFitMemorySimulator(inputFileName);
		}else if(simName.equals("next")){
			sim = new NextFitMemorySimulator(inputFileName);
		}else if(simName.equals("worst")){
			sim = new WorstFitMemorySimulator(inputFileName);
		}else if(simName.equals("noncontig")){
			sim = new NonContiguousMemorySimulator(inputFileName);
		}else{
			Externals.invalidUsageExit();
		}
		
		sim.timeStepUntil(0);
		sim.printMemory();
		
		while(sim.processesRemaining() > 0){
			userInput = 0;
			System.out.print(simName+"_memory_simulator> ");
			userInput = scanner.nextInt();
			sim.timeStepUntil(userInput);
			sim.printMemory();
		}
		
		System.out.println("No more events to process... exiting!");
		scanner.close();
	}
}
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class InputFileParser{

	public static ArrayList<Process> parseInputFile(String fileName){
		ArrayList<Process> processList = new ArrayList<Process>();
		try{
			BufferedReader br = new BufferedReader(new FileReader(fileName));	
			String line = br.readLine();
			while((line = br.readLine()) != null){
				ArrayList<Process> processesInLine = parseInputLine(line);
				processList.addAll(processesInLine);
			}
			br.close();
		}catch(IOException ioe){
			System.err.println("Couldn't read input file '" + fileName + "'");
			System.exit(1);
		}
		
		Collections.sort(processList);
		return processList;
	}

	public static ArrayList<Process> parseInputLine(String line){
		ArrayList<Process> processesToReturn = new ArrayList<Process>();
		String lineParts[] = line.split("\\s");

		if(lineParts.length < 4  || lineParts.length % 2 != 0){
			Externals.malformedInput(line);
		}
		
		char pid = lineParts[0].charAt(0);
		int memorySize = Integer.parseInt(lineParts[1]);

		for (int i=2;i<lineParts.length;i+=2){
			int start = Integer.parseInt( lineParts[i] );
			int end = Integer.parseInt( lineParts[i+1] );

			//Also, times must be strictly increasing
			if(start > end){
				//Time wasn't strictly increasing
				Externals.inputTimeError(line);
			}
			processesToReturn.add(new Process(pid, memorySize, start, end));
		}
		
		return processesToReturn;
	}
}
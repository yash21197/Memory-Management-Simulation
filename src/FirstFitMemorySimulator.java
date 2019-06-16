public class FirstFitMemorySimulator extends MemorySimulatorBase{

	public FirstFitMemorySimulator(String fileName){
		super(fileName);
	}

	@Override
	public int getNextSlot(int slotSize){
		int blocksize = 0;
		
		int i;
		for(i=0;i<main_memory.length;i++){
			if(main_memory[i] == FREE_MEMORY && blocksize < slotSize)
				blocksize++;
			else{
				if(blocksize >= slotSize)
					return i - blocksize;
				blocksize = 0;
			}
		}
		
		if(blocksize >= slotSize)
			return i - blocksize;
		blocksize = 0;
		
		return -1;
	}
}
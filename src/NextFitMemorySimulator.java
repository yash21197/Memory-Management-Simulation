public class NextFitMemorySimulator extends MemorySimulatorBase{
	
	protected int lastpos=0;

	public NextFitMemorySimulator(String fileName){
		super(fileName);
	}

	@Override
	public int getNextSlot(int slotSize){
		int blocksize = 0;
		
		int i;
		for(i=lastpos;i<main_memory.length;i++){
			if(main_memory[i] == FREE_MEMORY && blocksize < slotSize)
				blocksize++;
			else{
				if(blocksize >= slotSize){
					lastpos = i;
					return i - blocksize;
				}
				blocksize = 0;
			}
		}
		
		if(blocksize >= slotSize){
			lastpos = i;
			return i - blocksize;
		}
		blocksize = 0;
		
		for(i=0;i<lastpos;i++){
			if(main_memory[i] == FREE_MEMORY && blocksize < slotSize)
				blocksize++;
			else{
				if(blocksize >= slotSize){
					lastpos = i;
					return i - blocksize;
				}
				blocksize = 0;
			}
		}
		
		if(blocksize >= slotSize){
			lastpos = i;
			return i - blocksize;
		}
		blocksize = 0;
		
		return -1;
		
	}
}
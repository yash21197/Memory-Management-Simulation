public class NonContiguousMemorySimulator extends MemorySimulatorBase{

	public NonContiguousMemorySimulator(String fileName){
		super(fileName);
	}

	@Override
	public int getNextSlot(int slotSize){
		throw new IllegalArgumentException("This method isn't relevant " +
				"for this memory allocation strategy");
	}
	
	@Override
	public void putInMemory(Process p){
		int remainingToPlace = p.getSize();
		for(int i=0;i<main_memory.length && remainingToPlace>0;i++){
			if(main_memory[i] == FREE_MEMORY){
				main_memory[i] = p.getPid();
				remainingToPlace--;
			}
		}
		if(remainingToPlace > 0){
			Externals.outOfMemoryExit();
		}
	}
}
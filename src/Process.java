public class Process implements Comparable<Process>{
	char pid;
	int size;
	int start_time;
	int end_time;

	public Process(char pid, int size, int start_time, int end_time){
		this.pid 			= pid;
		this.size 			= size;
		this.start_time 	= start_time;
		this.end_time 		= end_time;
	}

	public char getPid(){
		return this.pid;			//write extra this.
	}

	public int getSize(){
		return this.size;		//write extra this.
	}

	public int getStartTime(){
		return this.start_time;		//write extra this.
	}
	
	public int getEndTime(){
		return this.end_time;		//write extra this.
	}

	@Override
	public int compareTo(Process o){
		if(this.start_time < o.start_time){
			return -1;
		}else if(this.start_time == o.start_time){
			return 0;
		}else{
			return 1;
		}		
	}
}
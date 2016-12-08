import java.util.*;

/*
 * This class has the main functions needed for the turing machine 
 * calculations
 */
public class TM_juan {
    // initialice fields
    public int[][] states;
    public StringBuilder states_chain = new StringBuilder();
    public int aux;
    public String turing_chain;
    Random randomGenerator = new Random();


/*
 * Initializes the turing machine with the specifies number and states
 * by generating random bits
 */
    public TM_juan(int numberStates, int bitPerState) {
        states = new int [numberStates][bitPerState];
        int rand_int;
        states_chain.append("");

        for (int i = 0; i <numberStates; ++i){
        	for (int j = 0;j <bitPerState; ++j){
        		rand_int = randomGenerator.nextInt(2);
        		states[i][j]= rand_int;
    			states_chain.append(Integer.toString(rand_int));

        	}
    	}
        turing_chain = states_chain.toString(); 
    }
      
    /*
     * Runs a the turing machine in from the matrix representation of the machine
     */
    public String run_machine() {
    	int[] cinta = new int[1024*1024];
    	// el head original es la mitad
    	int head = 1024*512;
    	// run machine
    	int min_index = head;
    	int max_index = head;
    	boolean halt = false;
    	int current_state = 0;
    	int max_iterator = 100;
    	int iterator = 0;
    	while (!halt && iterator<=max_iterator){
    		int current_value = states[current_state][0];
    		int future_value = states[current_state][1];
    		int left_right1 = states[current_state][2];
    		int left_right2 = states[current_state][9];

    		// integer to string suppose it goes from 
    		StringBuilder sb1 = new StringBuilder();
    		sb1.append("");
    		for(int idx=3;idx<=8;idx++){
        		sb1.append(Integer.toString(states[current_state][idx]));
    		}
    		int future_state1 = Integer.parseInt(sb1.toString(), 2);
    		
    		StringBuilder sb2 = new StringBuilder();
    		sb2.append("");
    		for(int idx=10;idx<=15;idx++){
        		sb2.append(Integer.toString(states[current_state][idx]));
    		}
    		int future_state2 = Integer.parseInt(sb2.toString(), 2);

    		if(cinta[head]==current_value){
    			cinta[head]=future_value;
        		if(left_right1==0){
        			head-=1;
        		}else{
        			head+=1;
        		}
        		if(future_state1==63){
        			halt=true;
        		}else{
        			current_state=future_state1;
        		}
    		}
    		else{
        		if(left_right2==0){
        			head-=1;
        		}else{
        			head+=1;
        		}
        		if(future_state2==63){
        			halt=true;
        		}else{
        			current_state=future_state2;
        		}
    		}

    		iterator +=1;
    		if(head<min_index){
    			min_index=head;
    		}
    		if(head>max_index){
    			max_index=head;
    		}
    		
    	}
    	// cut cinta on the significant segment, only where head was eventually
		StringBuilder sb = new StringBuilder();
		sb.append("");
		for(int ct=min_index; ct<=max_index;ct++){
			sb.append(Integer.toString(cinta[ct]));
		}
        return sb.toString();
    }
    
    
    /*
     * runs the turing machine from a given string representation of a machine
     */
    public String run_machine_from_string(String turing_tape) {

    	int[] cinta = new int[1024*1024];
    	// el head original es la mitad
    	int head = 1024*512;
    	// run machine
    	int min_index = head;
    	int max_index = head;
    	boolean halt = false;
    	int current_state = 0;
    	int max_iterator = 100;
    	int iterator = 0;
    	int chain_iterator_module = 0;
    	while (!halt && iterator<=max_iterator){
    		chain_iterator_module = 16*current_state;
    		int current_value = Character.getNumericValue(
    				turing_tape.charAt(chain_iterator_module));
    		int future_value = Character.getNumericValue(
    				turing_tape.charAt(chain_iterator_module+1));
    		int left_right1 = Character.getNumericValue(
    				turing_tape.charAt(chain_iterator_module+2));
    		int left_right2 = Character.getNumericValue(
    				turing_tape.charAt(chain_iterator_module+9));
    		// integer to string suppose it goes from 
    		StringBuilder sb1 = new StringBuilder();
    		sb1.append("");
    		for(int idx=3;idx<=8;idx++){
    			char number_pos=turing_tape.charAt(chain_iterator_module+idx); 
        		sb1.append(Integer.toString(Character.getNumericValue(number_pos)));
    		}
    		int future_state1 = Integer.parseInt(sb1.toString(), 2);
    		
    		StringBuilder sb2 = new StringBuilder();
    		sb2.append("");
    		for(int idx=10;idx<=15;idx++){
    			char number_pos=turing_tape.charAt(chain_iterator_module+idx); 
        		sb2.append(Integer.toString(Character.getNumericValue(number_pos)));
    		}
    		int future_state2 = Integer.parseInt(sb2.toString(), 2);
    		
    		if(cinta[head]==current_value){
    			cinta[head]=future_value;
        		if(left_right1==0){
        			head-=1;
        		}else{
        			head+=1;
        		}
        		if(future_state1==63){
        			halt=true;
        		}else{
        			current_state=future_state1;
        		}
    		}
    		else{
        		if(left_right2==0){
        			head-=1;
        		}else{
        			head+=1;
        		}
        		if(future_state2==63){
        			halt=true;
        		}else{
        			current_state=future_state2;
        		}
    		}

    		iterator +=1;
    		if(head<min_index){
    			min_index=head;
    		}
    		if(head>max_index){
    			max_index=head;
    		}
    		
    	}
    	// cut cinta on the significant segment, only where head was eventually
		StringBuilder sb = new StringBuilder();
		sb.append("");
		for(int ct=min_index; ct<=max_index;ct++){
			sb.append(Integer.toString(cinta[ct]));
		}
        return sb.toString();
    }
    
public String run_machine_from_string_track_writes(String turing_tape) {

	    StringBuilder chain_tracker = new StringBuilder();
	    chain_tracker.append("");

    	int[] cinta = new int[1024*1024];
    	// el head original es la mitad
    	int head = 1024*512;
    	// run machine
    	int min_index = head;
    	int max_index = head;
    	boolean halt = false;
    	int current_state = 0;
    	int max_iterator = 100;
    	int iterator = 0;
    	int chain_iterator_module = 0;
    	while (!halt && iterator<=max_iterator){
    		chain_iterator_module = 16*current_state;
    		int current_value = Character.getNumericValue(
    				turing_tape.charAt(chain_iterator_module));
    		int future_value = Character.getNumericValue(
    				turing_tape.charAt(chain_iterator_module+1));
    		int left_right1 = Character.getNumericValue(
    				turing_tape.charAt(chain_iterator_module+2));
    		int left_right2 = Character.getNumericValue(
    				turing_tape.charAt(chain_iterator_module+9));
    		// integer to string suppose it goes from 
    		StringBuilder sb1 = new StringBuilder();
    		sb1.append("");
    		for(int idx=3;idx<=8;idx++){
    			char number_pos=turing_tape.charAt(chain_iterator_module+idx); 
        		sb1.append(Integer.toString(Character.getNumericValue(number_pos)));
    		}
    		int future_state1 = Integer.parseInt(sb1.toString(), 2);
    		
    		StringBuilder sb2 = new StringBuilder();
    		sb2.append("");
    		for(int idx=10;idx<=15;idx++){
    			char number_pos=turing_tape.charAt(chain_iterator_module+idx); 
        		sb2.append(Integer.toString(Character.getNumericValue(number_pos)));
    		}
    		int future_state2 = Integer.parseInt(sb2.toString(), 2);
    		
    		if(cinta[head]==current_value){
    		    chain_tracker.append(Integer.toString(future_value));
    			cinta[head]=future_value;
        		if(left_right1==0){
        			head-=1;
        		}else{
        			head+=1;
        		}
        		if(future_state1==63){
        			halt=true;
        		}else{
        			current_state=future_state1;
        		}
    		}
    		else{
    		    chain_tracker.append(Integer.toString(cinta[head]));
        		if(left_right2==0){
        			head-=1;
        		}else{
        			head+=1;
        		}
        		if(future_state2==63){
        			halt=true;
        		}else{
        			current_state=future_state2;
        		}
    		}

    		iterator +=1;
    		if(head<min_index){
    			min_index=head;
    		}
    		if(head>max_index){
    			max_index=head;
    		}
    		
    	}
    	// cut cinta on the significant segment, only where head was eventually
		StringBuilder sb = new StringBuilder();
		sb.append("");
		for(int ct=min_index; ct<=max_index;ct++){
			sb.append(Integer.toString(cinta[ct]));
		}
        return chain_tracker.toString();
    }

    
/*
 * runs the turing machine from a string representation of a given turing machine
 * and records the visited states to calculate kolmogorov complexity
 * is close to the previous function but since this one is only called once
 * its done separately to improve performance  
 */
    public int run_machine_from_string_kolmogorov(String turing_tape) {
    	int[] cinta = new int[1024*1024];
    	// el head original es la mitad
    	int head = 1024*512;
    	// run machine
    	int min_index = head;
    	int max_index = head;
    	boolean halt = false;
    	int current_state = 0;
    	int max_iterator = 1000;
    	int iterator = 0;
    	int chain_iterator_module = 0;
    	int[] visited_states = new int[64];
    	while (!halt && iterator<=max_iterator){
        	visited_states[current_state] = 1;
    		chain_iterator_module = 16*current_state;
    		int current_value = Character.getNumericValue(
    				turing_tape.charAt(chain_iterator_module));
    		int future_value = Character.getNumericValue(
    				turing_tape.charAt(chain_iterator_module+1));
    		int left_right1 = Character.getNumericValue(
    				turing_tape.charAt(chain_iterator_module+2));
    		int left_right2 = Character.getNumericValue(
    				turing_tape.charAt(chain_iterator_module+9));
    		// integer to string suppose it goes from 
    		StringBuilder sb1 = new StringBuilder();
    		sb1.append("");
    		for(int idx=3;idx<=8;idx++){
    			char number_pos=turing_tape.charAt(chain_iterator_module+idx); 
        		sb1.append(Integer.toString(Character.getNumericValue(number_pos)));
    		}
    		int future_state1 = Integer.parseInt(sb1.toString(), 2);
    		
    		StringBuilder sb2 = new StringBuilder();
    		sb2.append("");
    		for(int idx=10;idx<=15;idx++){
    			char number_pos=turing_tape.charAt(chain_iterator_module+idx); 
        		sb2.append(Integer.toString(Character.getNumericValue(number_pos)));
    		}
    		int future_state2 = Integer.parseInt(sb2.toString(), 2);
    		
    		if(cinta[head]==current_value){
    			cinta[head]=future_value;
        		if(left_right1==0){
        			head-=1;
        		}else{
        			head+=1;
        		}
        		if(future_state1==63){
        			halt=true;
        		}else{
        			current_state=future_state1;
        		}
    		}
    		else{
        		if(left_right2==0){
        			head-=1;
        		}else{
        			head+=1;
        		}
        		if(future_state2==63){
        			halt=true;
        		}else{
        			current_state=future_state2;
        		}
    		}

    		iterator +=1;
    		if(head<min_index){
    			min_index=head;
    		}
    		if(head>max_index){
    			max_index=head;
    		}
    		
    	}
    	// cut cinta on the significant segment, only where head was eventually
		StringBuilder sb = new StringBuilder();
		sb.append("");
		for(int ct=min_index; ct<=max_index;ct++){
			sb.append(Integer.toString(cinta[ct]));
		}
		int number_visited_states = 0;
		for(int nv=0; nv<64; nv++){
			if(visited_states[nv]==1){
				number_visited_states+=1;
			}
		}
		if(halt){
			number_visited_states+=1;
		}
        return number_visited_states;
    }
        
    /*
     * Computes fitness function between target tape and given tape
     */
    public int run_fitness(String cinta, String target) {

    	int penalty = 0;
    	// penalize greatly the overload 
    	int target_length = target.length();
    	int cinta_length = cinta.length();
    	int length_difference = target_length-cinta_length; 
    	double length_difference_penalty = Math.pow(length_difference, 2);
        

    	if(length_difference > 0){
    		// target is larger than cinta
    	    target = target.substring(0,cinta_length);
    	}
    	else{
    		// cinta is larger than target
    	    cinta = cinta.substring(0,target_length);
    	}
    	
        for(int i=0; i<target.length();i++){
    		if(cinta.charAt(i)!=target.charAt(i)){
    			penalty += 1;
    		}
    	}
    	//int different_bits = penalty;
    	double dpenalty = penalty;
    	double dlength = target.length();
    	double radio_penalty = dpenalty/dlength;
    	if(radio_penalty>.2){
    		penalty+=1000;
    	}
    	penalty += length_difference_penalty;
    	// double cast_penalty = penalty;
        return penalty;
    }

}

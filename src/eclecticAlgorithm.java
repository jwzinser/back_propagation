import java.util.*;


public class eclecticAlgorithm {
    // initialice fields
    public String[] genome_list;
    public int aux;
    Random randomGenerator = new Random();


    // the Bicycle class has
    // one constructor
    public eclecticAlgorithm(String[] genomes) {
    	genome_list=genomes;
    }
       
    // the TM_juan class has
    public void increment(int increment) {
        aux += increment;
    }
    
    public String run_fitness() {
        // todo el proceso para correr la maquina de la cinta 
    	// del total de bits que tiene cada estado
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
    		int left_right = states[current_state][2];
    		// integer to string suppose it goes from 
    		StringBuilder sb = new StringBuilder();
    		sb.append("");
    		for(int idx=2;idx<=7;idx++){
        		sb.append(Integer.toString(states[current_state][idx]));
    		}
    		int future_state = Integer.parseInt(sb.toString(), 2);

    		if(cinta[head]==current_value){
    			cinta[head]=future_value;
    		}
    		if(left_right==0){
    			head-=1;
    		}else{
    			head+=1;
    		}
    		if(future_state==63){
    			halt=true;
    		}else{
    			current_state=future_state;
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


}

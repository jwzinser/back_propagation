package backprop;

import static backprop.BackProp.predict;
import java.util.*;

/*
* This class has the main functions needed for the turing machine
* calculations
*/
public class TM {
    // initialice fields
    public int[][] states;
    public StringBuilder states_chain = new StringBuilder();
    public int aux;
    public String turing_chain;
    Random randomGenerator = new Random();
    
    // have an optional chain that will be added given a turing machine 
    public String create_chain_from_oracle(){
        // check the size of the oracle and if is reduced or not
        // actually it is not needed to have a 
        int rand_int;
        StringBuilder states_chain2 = new StringBuilder();
        states_chain.append("");
        
        for (int i = 0; i <64; ++i){
            for (int j = 0;j <32; ++j){
                rand_int = randomGenerator.nextInt(2);
                states_chain2.append(Integer.toString(rand_int));
            }
        }
        
        String turing_chain_with_oracle = states_chain2.toString();
                return turing_chain_with_oracle;

        }
    
    
    
    /*
    * Initializes the turing machine with the specifies number and states
    * by generating random bits
    */
    public TM(int numberStates, int bitPerState) {
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
        
    public String run_machine_from_string_oracle(String turing_tape,double parametros[][]) {
        
        // in this case the turing_tape has double length
        // every state has 32 bits 
        int[] cinta = new int[1024*1024];
        // el head original es la mitad
        int head = 1024*512;
        int[] last_writes = new int[1024*1024];
        int writer_index = 0;
        int lag = 3;
        double[][] lag_writes = new double[1][lag+1]; // plus one since there is a "y" at the end
        double predicted[];
        double prediction;
        int oracle;
        int quadrant;
        // run machine
        int min_index = head;
        int max_index = head;
        boolean halt = false;
        int current_state = 0;
        int max_iterator = 100;
        int iterator = 0;
        int chain_iterator_module;
        while (!halt && iterator<=max_iterator){
            chain_iterator_module = 32*current_state;
            // this corresponds to the first desicion factor
            int current_value1 = Character.getNumericValue(
                    turing_tape.charAt(chain_iterator_module));
            // this corresponds to the first desicion factor
            int current_value2 = Character.getNumericValue(
                turing_tape.charAt(chain_iterator_module+16));
            // make the prediction with the oracle 
            prediction=0;
            if(writer_index>lag){
                for(int ia=0; ia<lag; ia++){
                    lag_writes[0][ia] =(double) last_writes[writer_index-lag+ia];                             
                }
                lag_writes[0][lag] = 0 ;
                predicted = predict(lag_writes, parametros);
                prediction = predicted[0];
            }
            if(prediction<0.5){
                oracle = 0;
            }else{
                oracle = 1;
            }
            quadrant=0;
            switch(oracle) {
                case 0:
                switch(current_value1){
                    case 0:
                    quadrant=0*8;
                    break;
                    case 1:
                    quadrant=1*8;
                    break;
                };
                case 1:
                switch(current_value1){
                    case 0:
                    quadrant=2*8;
                    break;
                    case 1:
                    quadrant=3*8;
                    break;
                };
             }

            
            
            
            int future_value = Character.getNumericValue(
                    turing_tape.charAt(chain_iterator_module+quadrant+1));
            int left_right1 = Character.getNumericValue(
                    turing_tape.charAt(chain_iterator_module+quadrant+2));

            // integer to string suppose it goes from
            StringBuilder sb1 = new StringBuilder();
            sb1.append("");
            for(int idx=(quadrant+3);idx<=(quadrant+8);idx++){
                char number_pos=turing_tape.charAt(chain_iterator_module+idx);
                sb1.append(Integer.toString(Character.getNumericValue(number_pos)));
            }
            int future_state1 = Integer.parseInt(sb1.toString(), 2);
            

            cinta[head]=future_value;
            last_writes[writer_index] = future_value;
            writer_index += 1;
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

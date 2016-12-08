/*
        Calculate Circle Area using Java Example
        This Calculate Circle Area using Java Example shows how to calculate
        area of circle using it's radius.
*/

import java.io.*;
import java.util.*;


public class turing_machine {
    public static void main(String[] args) throws FileNotFoundException  {
        try {
        	// file name to read
            Scanner file = new Scanner(new FileReader("/Users/juanzinser/Documents/" +
            		"ITAM MCC/Computabilidad y Complejidad/Complejidad de kolmogorov/Corridas " +
            		"de ejemplo/Corrida 2/2_TargetTape.txt"));
            String s = file.next();
            String tm_thing  = s;
            
            // define number or iterations (chains) and generations
            int number_turing_iterations=1000;
            int number_generations = 100;
            
            // initialization of turing_machine, tapes and fitness vectors
            String[] turing_iterations = new String[number_turing_iterations];
            String[] turing_chains = new String[number_turing_iterations];
            double[] turing_iterations_penalties = new double[number_turing_iterations];
            // vetor index and values for getting best and worst machines
            int min_fit_id = 0;
            int min_fit_valor = 1000;
            int max_fit_id = 0;
            int max_fit_valor = 0;
            int penal_valor;
            // get the first set of chains
            for(int gi=0;gi<number_turing_iterations;gi++){
            	// saca las maquinas de turing
            	TM_juan turing = new TM_juan(64,16);
            	turing_chains[gi] = turing.turing_chain;
            	String current_tape = turing.run_machine();
            	turing_iterations[gi] = current_tape;
            	penal_valor = turing.run_fitness(current_tape, tm_thing);
            	turing_iterations_penalties[gi]=penal_valor;
            	if(penal_valor<min_fit_valor){
            		min_fit_valor = penal_valor;
            		min_fit_id = gi;
            	}
            	if(penal_valor>max_fit_valor){
            		max_fit_valor = penal_valor;
            		max_fit_id = gi;
            	}
            }
            // select the best and worst chains from the first iteration
            String best_chain = turing_chains[min_fit_id];
            String worst_chain = turing_chains[max_fit_id];
            Random randomGenerator = new Random();
            /*
             * This is the genetic algorithm for
             * there is a print in each iteration to track the progress
             * we track the resulting tape and the fitness value for each
             * generation
             */
            for(int gix=0;gix<number_generations;gix++){
            	StringBuilder progress_print = new StringBuilder();
            	progress_print.append("iteration number: ");
            	progress_print.append(String.valueOf(gix));
            	progress_print.append(" - with minimum pelalty: ");
            	progress_print.append(String.valueOf(min_fit_valor));
            	progress_print.append(" - and the best chain is: ");
                System.out.println(progress_print.toString());
                System.out.println(turing_iterations[min_fit_id]);
                
                // reseting min-max indexes and values
                min_fit_id = 0;
                min_fit_valor = 10000;
                max_fit_id = 0;
                max_fit_valor = 0;
                // initialize the new vectors for chains, tapes and fitness
                String[] new_turing_chains = new String[number_turing_iterations];
                String[] new_turing_iterations = new String[number_turing_iterations];
                double[] new_turing_iterations_penalties = new double[number_turing_iterations];
                /*
                 * This for fills the vectors by generating the chains, tapes and penalties
                 * with the eclectic genetic algorithm method 
                 */
                for(int ng=0;ng<number_turing_iterations;ng=ng+2){
	        		int rand_cruzamiento_mut = randomGenerator.nextInt(100);
	        		String new_best_string;
	        		String new_worst_string;
	        		// performs the crossover with .9 probability
	        		if(rand_cruzamiento_mut<90){
	            		int initial_pos = randomGenerator.nextInt(1024);
	            		int final_pos = (initial_pos + 512) % 1024;
	
	            		if(initial_pos<final_pos){
	            			// regular way 
	        				String best_half_string = best_chain.substring(initial_pos,final_pos);
	        				String worst_half_string = worst_chain.substring(initial_pos,final_pos);
	        				new_best_string = best_chain.substring(0,initial_pos)+
	        						worst_half_string+best_chain.substring(final_pos);
	        				new_worst_string = worst_chain.substring(0,initial_pos)+
	        						best_half_string+worst_chain.substring(final_pos);
	            		}
	            		else{
	        				String best_half_string_p1 = best_chain.substring(initial_pos);
	        				String best_half_string_p2 = best_chain.substring(0,final_pos);
	        				String worst_half_string_p1 = worst_chain.substring(initial_pos);
	        				String worst_half_string_p2 = worst_chain.substring(0,final_pos);
	        				new_best_string = worst_half_string_p2 + best_chain.substring(final_pos,initial_pos)+
	        						worst_half_string_p1;
	        				new_worst_string = best_half_string_p2 + worst_chain.substring(final_pos,initial_pos)+
	        						best_half_string_p1;
	            		}
	        		}
	        		else{
	        			new_best_string = best_chain;
	        			new_worst_string = worst_chain;
	        		}
	            		
	        	    char[] chars_best = new_best_string.toCharArray();
	        	    char[] chars_worst = new_worst_string.toCharArray();
	        	   
	        	    // performs the mutations
	        		for(int mut=0; mut<1024; mut++){
	        			// for the best string
	        			int rand_int = randomGenerator.nextInt(100);
	            		if(rand_int<5){
	            			if(chars_best[mut]=='0'){
	                    	    chars_best[mut] = '1';
	            			}
	            			else{
	            				chars_best[mut] = '0';
	            			}
	            		}
	        			// for the worst string
	        			int rand_intw = randomGenerator.nextInt(100);
	            		if(rand_intw<5){
	            			if(chars_worst[mut]=='0'){
	            				chars_worst[mut] = '1';
	            			}
	            			else{
	            				chars_worst[mut] = '0';
	            			}
	            		}
	        		}
	        	    new_best_string =  String.valueOf(chars_best);
	        	    new_worst_string =  String.valueOf(chars_worst);
	        	    new_turing_chains[ng] = new_best_string;
	        	    new_turing_chains[ng+1] = new_worst_string;
	            	TM_juan turing = new TM_juan(64,16);
	            	// generates the tapes for the resulting chain best chain
	            	String current_tape = turing.run_machine_from_string(new_best_string);
	        	    new_turing_iterations[ng] = current_tape;
	        	    // calculates fitness for the resulting tapes
	        	    penal_valor = turing.run_fitness(current_tape, tm_thing);
	            	new_turing_iterations_penalties[ng]=penal_valor;
	            	if(penal_valor<min_fit_valor){
	            		min_fit_valor = penal_valor;
	            		min_fit_id = ng;
	            	}
	            	if(penal_valor>max_fit_valor){
	            		max_fit_valor = penal_valor;
	            		max_fit_id = ng;
	            	}
	            	// generates the tapes for the resulting chain worst chain
	            	current_tape = turing.run_machine_from_string(new_worst_string);
	        	    new_turing_iterations[ng+1] = current_tape;
	        	    penal_valor = turing.run_fitness(current_tape, tm_thing);
	            	new_turing_iterations_penalties[ng+1]=penal_valor;
	            	if(penal_valor<min_fit_valor){
	            		min_fit_valor = penal_valor;
	            		min_fit_id = ng+1;
	            	}
	            	if(penal_valor>max_fit_valor){
	            		max_fit_valor = penal_valor;
	            		max_fit_id = ng+1;
	            	}
	        	    		
                }
                turing_iterations = new_turing_iterations;
                turing_chains = new_turing_chains;
                turing_iterations_penalties = new_turing_iterations_penalties;
                best_chain = turing_chains[min_fit_id];
                worst_chain = turing_chains[max_fit_id];
            }
            
            // calculates kolmogorov complexity by checking number of visited states
        	TM_juan turing = new TM_juan(64,16);
        	int kolmogorov = turing.run_machine_from_string_kolmogorov(best_chain);

            System.out.println("\n");
            String best_result = turing_iterations[min_fit_id];
            System.out.println("target tape");
            System.out.println(tm_thing);
            System.out.println("resulting tape");
            System.out.println(best_result);
            System.out.println("States Visited: ");
            System.out.println(kolmogorov);


            file.close();
                
            
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.print("error");

        }      	

    }
}

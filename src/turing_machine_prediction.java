/*
        Calculate Circle Area using Java Example
        This Calculate Circle Area using Java Example shows how to calculate
        area of circle using it's radius.
*/

import java.io.*;
import java.util.*;


public class turing_machine_prediction {
    public static void main(String[] args) throws FileNotFoundException  {
        try {
        	// file name to read
            Scanner file = new Scanner(new FileReader("/Users/juanzinser/Documents/" +
            		"ITAM MCC/Computabilidad y Complejidad/Complejidad de kolmogorov/Corridas " +
            		"de ejemplo/Corrida 2/2_TargetTape.txt"));
            String s = file.next();
            

        	TM_juan turing = new TM_juan(64,16);
        	String turing_chain = turing.turing_chain;
        	String writer_traker = turing.run_machine_from_string_track_writes( turing_chain);
            System.out.println(writer_traker);
            
            // generate the tables to train the neural network
            int n_lag = 3;
            int[][] lag_matrix = new int[writer_traker.length()-n_lag][n_lag+1];
            System.out.println(writer_traker.length()-n_lag);
            System.out.println(n_lag+1);

            for (int i = n_lag; i <(writer_traker.length()-1); ++i){
                for (int j = (n_lag); j >= 0; --j){
                    System.out.println(i);
                    System.out.println(j);

	            	lag_matrix[i-n_lag][j] = Character.getNumericValue(writer_traker.charAt(i-j));
                }
            }

            
            
            // generate train the oracle
            
            
            // generate the new turing machine based on the oracle



            file.close();
                
            
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.print("error");

        }      	

    }
}

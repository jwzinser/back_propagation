package backprop;

import java.util.Random;

public class BackProp {
    
    private static double[][] RandomArray(int n, int m) {
        // http://stackoverflow.com/questions/4328442/how-can-i-display-a-n-x-n-matrix-of-random-numbers-in-java
        double[][] randomMatrix = new double [n][m];
        
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                Double r = rand.nextDouble();
                randomMatrix[i][j] = r;
            }
        }
        return randomMatrix;
    }
    
    public static double dotProduct(double[] a, double[] b) {
        double sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i] * b[i];
        }
        return sum;
    }
    
    public static double[][] NNNP(double LR, int q, double[][] datos, int epochs){
        // In:
        //      LR = Learning Rate,
        //      q = number of nodes in hidden layer,
        //      data matrix: matriz de la forma [x_1|x_2|...|x_p|y] de Nx(p+1),
        //      epochs
        int p = datos[0].length - 1; // Número de variables
        int N = datos.length; // Número de observaciones
        
        double params[][] = RandomArray(q, p+1);
        double[] beta = new double[q];
        double[][] theta = new double[q][p];
        double[] y = new double[N];
        
        for(int i = 0; i < N; i++){
            y[i] = datos[i][p];
        }
        
        for(int i_beta = 0; i_beta < q; i_beta++){
            beta[i_beta] = params[i_beta][p];
        }
        
        for(int i_theta = 0; i_theta < q; i_theta++){
            for(int j_theta = 0; j_theta < p; j_theta++){
                theta[i_theta][j_theta] = params[i_theta][j_theta];
            }
        }
        
        for(int iter = 0; iter < epochs; iter++){
            
            double[][] parciales_theta = new double[q][p];
            double[] parciales_beta = new double[q];
            
            for(int i = 0; i < q; i++){
                parciales_beta[i] = 0;
            }
            
            for(int i = 0; i < q; i++){
                for(int j = 0; j < p; j++) {
                    parciales_theta[i][j] = 0;
                }
            }
            
            for(int k_dat = 0; k_dat < N; k_dat++){
                double[] a_k = new double[q];
                double[] z_k = new double[q];
                
                for(int j = 0; j < q; j++){
//                    double z_jk = 0;
                    for(int ix = 0; ix < p; ix++){
                        z_k[j] += theta[j][ix]*datos[ix][k_dat];
                    }
                    a_k[j] = Math.tanh(z_k[j]);
                }
                
                double w_k = 0;
                
                for(int ix = 0; ix < q; ix++){
                    w_k += beta[ix]*a_k[ix];
                }
                
                double p_k = Math.tanh(w_k);
                
                //double[] parciales_beta = new double[q];
                
                for(int ix = 0; ix < q; ix++){
                    parciales_beta[ix] += -(2/N)*(y[k_dat] - p_k)*(1 - w_k*w_k)*a_k[ix];
                }
                
                for(int i = 0; i < q; i++){
                    for(int j = 0; j < p; j++){
                        parciales_theta[i][j] += -(2/N)*(y[k_dat] - p_k)*(1 - w_k*w_k)*a_k[i];
                    }
                }
                
//                for(int k = 0; k < N; k++){
//                    double z_jk = 0;
//
//                    for(int ix = 0; ix < p; ix++){
//                        z_jk = z_jk + params[j][ix];
//                    }
//                    double a_jk = Math.tanh(z_jk);
//
//                    for(int i_dat = 0; i_dat < N; i_dat++){
//                        double w_k =2;
//                        double y_k = datos[i_dat][p];
//                    }
//
//                    double parcial_beta_lk = 2*(2);
//                    params[p+1][j] = params[p+1][j] + LR * parcial_beta_lk;
//                }
            }
        }
        
        return(params);
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        // double out[][] = RandomArray(2, 3);
        double out[][] = RandomArray(2, 3);
        int n = out.length; // 2
        int m = out[0].length; // 3
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                System.out.println(out[i][j]);
            }
        }
    }
    
}

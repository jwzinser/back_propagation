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
    
    public static double[][] backpropagation(double LR, int q, double[][] datos, int epochs){
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
            
            double[] pred = new double [N];
            
            for(int k_dat = 0; k_dat < N; k_dat++){
                double[] a_k = new double[q];
                double[] z_k = new double[q];
                
                for(int m = 0; m < q; m++){
                    for(int ix = 0; ix < p; ix++){
                        z_k[m] += theta[m][ix]*datos[k_dat][ix];
                    }
                    a_k[m] = Math.tanh(z_k[m]);
                }
                
                double w_k = 0;
                
                for(int ix = 0; ix < q; ix++){
                    w_k += beta[ix]*a_k[ix];
                }
                
                
                double p_k = Math.tanh(w_k);
                
                for(int m = 0; m < q; m++){
                    double prod = -(y[k_dat] - p_k)*(1 - w_k*w_k)*a_k[m];
                    parciales_beta[m] += prod/N;
                    
                }
                
                for(int m = 0; m < q; m++){
                    for(int n = 0; n < p; n++){
                        double prod = - (y[k_dat] - p_k)*(1 - w_k*w_k)*beta[m]*(1-z_k[m]*z_k[m])*datos[k_dat][n];
                        parciales_theta[m][n] += prod/N;
                    }
                }
                pred[k_dat] = p_k;
                
            }
            
            double suma_errores = 0;
            for(int i = 0; i < N; i++){
                suma_errores += Math.pow((pred[i] - y[i]), 2);
            }
            
            for(int m = 0; m < q; m++){
                beta[m] = beta[m] - LR * parciales_beta[m];
                for(int n = 0; n < p; n++){
                    theta[m][n] = theta[m][n] - LR * parciales_theta[m][n];
                }
            }
            
            System.out.print("iter: ");
            System.out.println(iter);
            System.out.print("suma errores al cuadrado: ");
            System.out.println(suma_errores);
            System.out.println();
        }
        
        for(int m = 0; m < q; m++){
            params[m][p] = beta[m];
            for(int n = 0; n < p; n++){
                params[m][n] = theta[m][n];
            }
        }
        
        return(params);
    }
    
    
    public static double[] predict(double[][] datos, double[][] params){
        int p = datos[0].length - 1; // Número de variables
        int N = datos.length; // Número de observaciones
        int q = params.length; // Número de capas ocultas
        
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
        
        
        double[] pred = new double [N];
        
        for(int k_dat = 0; k_dat < N; k_dat++){
            double[] a_k = new double[q];
            double[] z_k = new double[q];
            
            for(int m = 0; m < q; m++){
                for(int ix = 0; ix < p; ix++){
                    z_k[m] += theta[m][ix]*datos[k_dat][ix];
                }
                a_k[m] = Math.tanh(z_k[m]);
            }
            
            double w_k = 0;
            
            for(int ix = 0; ix < q; ix++){
                w_k += beta[ix]*a_k[ix];
            }
            
            double p_k = Math.tanh(w_k);
            pred[k_dat] = p_k;
        }
        
        return(pred);
    }
    
    
    
    
    
    public static void main(String[] args) {
        
        //double datos[][] = new double[][]{{0,1,1},{0,0,0},{1,1,0},{0,1,1},{0,0,0},{1,1,0},{0,1,1},{0,0,0},{1,1,0},{0,1,1},{0,0,0},{1,1,0},{0,1,1},{0,0,0},{1,1,0},{0,1,1},{0,0,0},{1,1,0}};
        //double datos_prueba[][] = new double[][]{{0,1,1},{0,0,0},{1,1,0},{0,1,1},{0,0,0},{1,1,0},{0,1,1},{0,0,0},{1,1,0},{0,1,1},{0,0,0},{1,1,0},{0,1,1},{0,0,0},{1,1,0},{0,1,1},{0,0,0},{1,1,0}};
        
        double datos[][] = new double[][]{{-1,1,1},{-1,-1,-1},{1,1,-1},{-1,1,1},{-1,-1,-1},{1,1,-1},{-1,1,1},{-1,-1,-1},{1,1,-1},{-1,1,1},{-1,-1,-1},{1,1,-1},{-1,1,1},{-1,-1,-1},{1,1,-1},{-1,1,1},{-1,-1,-1},{1,1,-1}};
        double datos_prueba[][] = new double[][]{{-1,1,1},{-1,-1,-1},{1,1,-1},{-1,1,1},{-1,-1,-1},{1,1,-1},{-1,1,1},{-1,-1,-1},{1,1,-1},{-1,1,1},{-1,-1,-1},{1,1,-1},{-1,1,1},{-1,-1,-1},{1,1,-1},{-1,1,1},{-1,-1,-1},{1,1,-1}};
        
//        double datos[][] = RandomArray(40, 5);
//        double datos_prueba[][] = RandomArray(40, 5);
        
        double parametros[][] = backpropagation(0.001, 3, datos, 100);
        
        double predicted[] = predict(datos_prueba, parametros);
        
        System.out.println();
        for(int i = 0; i < predicted.length; i++){
            System.out.println(predicted[i]);
        }
        
        
//        int n = parametros.length; // 2
//        int m = parametros[0].length; // 3
//        for(int i = 0; i < n; i++){
//            for(int j = 0; j < m; j++){
//                System.out.println(parametros[i][j]);
//            }
//        }
    }
    
}

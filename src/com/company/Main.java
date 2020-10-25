package com.company;

public class Main {

    public static void main(String[] args) throws LA.LAException {
	// write your code here

        double[][] A, E, Stable;
        double [][] B ;
        //A = new double[][] {{1,1,1.40}, {3.9,3.6,3.0,141.3}}; //Exemple 6 page 63
        //A = new double[][] {{1,1,1.10000}, {0.025,0.064,0.045}, {2,-1,0,0}}; // Exercice 42
        //A = new double[][] {{30,10,10,350}, {10,10,30,150}}; // Exercice 43
        //A = new double[][] {{10,20,60,440}, {10,8,28,248}}; // Exercice 44
        //A = new double[][] {{1.5,1,0.5,380}, {1.2,0.9,0.6,330}}; // Exercice 45
        /* A = new double[][] {{1,1,0,0,0,0,0,0.625},
                {0,0,1,1,0,0,0,0.75}, {0,0,0,0,1,1,1,1},
                {0,0,1,0,1,0,0,0.625},{1,0,0,1,0,1,0,1},
                {0,1,0,0,0,0,1,0.75},{0,0,0,1,0,0,1,0.625},{0,1,0,1,1,0,0,1}} ; // Exercice 43 page 82
        */
        //A = new double[][] {{2,3,3,25}, {2,4,5,33},{3,2,1,22}}; // exercie 48A page 82
        //A = new double[][] {{2,3,3,25}, {2,4,5,33}}; // Exercice 48B page 82
        //A = new double[][] {{1,0,0,1,1000}, {1,1,0,9,1100},{0,1,1,0,700},{0,0,1,1,600}}; // Exercice 63B page 85
       // A = new double[][] {{0,0,1,-1,-3}, {0,1,0,-3,0},{1,0,0,-45,-135},{1,1,1,1,262}}; // Exercice 67
       // B = new double[][] {{0,0,1,-1,-3}, {0,1,0,-3,0},{1,0,0,-45,-135},{1,1,1,1,262}};

//        double [][] text = new double[][] {{19,1,14,13,26,26},{14,4,17,14,19,1}, {26,26,26,19,14,4}};  Exercice 65
//        A = new double[][] {{2,4,6},{-1,-4,-3},{0,1,-1}} ; Exercice 65
//        double [][] textChiffre = LA.multiply(A,text) ;
//        LA.showMatrix(A);
//        LA.showMatrix(text);
//        LA.showMatrix(textChiffre);
//        LA.showMatrix(LA.multiply(LA.inverse(A),textChiffre)); Exercice 65

//        A = LA.multiply(new double[][] {{40,55,60},{10,10,15},{1,1,1}},
//                LA.inverse(new double[][]{{50,50,45},{0,15,20},{1,1,1}}));
//        LA.showMatrix(A);
//       B = LA.multiply(A, new double[][] {{50,50,45},{0,15,20},{1,1,1}}) ;
//       LA.showMatrix(B);
//       LA.showMatrix(LA.inverse(new double[][]{{50,50,45},{0,15,20},{1,1,1}}));
     /*   double [][] C ;
        double [][] D ;
        A = new double[][] {{0.293,0,0},{0.014,0.207,0.017},{0.044,0.010,0.216}};
        D = new double[][] {{138.213},{17.597},{1786}} ;
        C = LA.power(LA.substract(LA.identity(3), A),-1);
        LA.showMatrix(C);
        LA.showMatrix(LA.multiply(C,D));  Exercice OutPout/Intput

*/

        //A = new double[][] {{20,10,16.20}, {30,12,23.04}};
       // A = LA.randomMatrix(10,11);
/*        LA.showMatrix(A);
        LA.showMatrix(B);
        LA.showMatrix(LA.multiply(A,B));
        LA.showMatrix(LA.multiply(B, A)); */

      //  LA.showVector(LA.solveRegularSystem(A));

       // LA.showMatrix(A);
      //  LA.elimateGj(A);
      //  LA.showMatrix(A);

/* --Exercie Etat stable


        A = new double[][] {{0.983,0.008},{0.017,0.992}} ;
        E = new double[][] {{54000},{77000}} ;

       // LA.showMatrix(LA.multiply(LA.power(A,900), E));

        Stable = new double[][] {{41920},{89080}} ;

        LA.showMatrix(LA.multiply(LA.power(A,10000), Stable));

         --Exercie Etat stable */

        A = new double[][]{{10,10,30,350},{10,30,10,150}};
        B = new double[][]{{4000,4500,4500,4000}, {2000,2600,2400,2200},{5800,6200,6000,6000}} ;

       LA.elimateGj(A);
       LA.showMatrix(A);

       //nouveau test blalba

    }
}
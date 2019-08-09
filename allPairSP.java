import edu.princeton.cs.algs4.*;

class allPairSP{
    
    public double[][] matrix;
    public int n;
    
    public allPairSP(EdgeWeightedDigraph G){
        n = G.V();
        matrix = new double[n][n];
        
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                matrix[i][j] = Double.POSITIVE_INFINITY;
                if(i == j){
                    matrix[i][j] = 0;
                }
            }
        }
        for(DirectedEdge e : G.edges()){
            int i = e.from();
            int j = e.to();
            matrix[i][j] = e.weight();
        }
        print();
        System.out.println();
        computeSP();
        print();
    }
    public void computeSP(){
        for(int k = 0; k < n; k++){
            for(int i = 0; i < n; i++){
                if(i != k && matrix[i][k] < Double.POSITIVE_INFINITY){
                    for(int j = 0; j < n; j++){
                    if(j != k && matrix[k][j] < Double.POSITIVE_INFINITY){
                        matrix[i][j] = Math.min(matrix[i][j], matrix[i][k] + matrix[k][j]);
                    }
                }
                }
                
            }
            System.out.println(k);
            print();
            System.out.println();
        }
    }
    public void print(){
        System.out.print(" ");
        for(int i = 0; i < n; i++){
            System.out.printf("%3s ", (char)(i + 'A'));
        }
        System.out.println();
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(j == 0){
                    System.out.print((char)(i + 'A') + " ");
                }
                if(matrix[i][j] == Double.POSITIVE_INFINITY)
                    System.out.printf("%-4s", "max");
                else
                    System.out.printf("%-3.1f ", matrix[i][j]);
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args){
        EdgeWeightedDigraph graph = new EdgeWeightedDigraph(9);
        
        int A = 0, B = 1, C = 2, D = 3, E = 4, F = 5, G = 6, H = 7, I = 8;
        
        DirectedEdge e1 = new DirectedEdge(A, I, 3);
        DirectedEdge e2 = new DirectedEdge(B, D, 3);
        DirectedEdge e3 = new DirectedEdge(C, B, 5);
        DirectedEdge e4 = new DirectedEdge(C, E, 1);
        DirectedEdge e5 = new DirectedEdge(D, C, 1);
        DirectedEdge e6 = new DirectedEdge(D, E, 1);
        DirectedEdge e7 = new DirectedEdge(D, G, 4);
        DirectedEdge e8 = new DirectedEdge(E, F, 1);
        DirectedEdge e9 = new DirectedEdge(E, G, 2);
        DirectedEdge e10 = new DirectedEdge(F, H, -3);
        DirectedEdge e11 = new DirectedEdge(G, F, -1);
        DirectedEdge e12 = new DirectedEdge(G, H, 2);
        DirectedEdge e13 = new DirectedEdge(G, I, 7);
        DirectedEdge e14 = new DirectedEdge(H, A, -4);
        
        graph.addEdge(e1);
        graph.addEdge(e2);
        graph.addEdge(e3);
        graph.addEdge(e4);
        graph.addEdge(e5);
        graph.addEdge(e6);
        graph.addEdge(e7);
        graph.addEdge(e8);
        graph.addEdge(e9);
        graph.addEdge(e10);
        graph.addEdge(e11);
        graph.addEdge(e12);
        graph.addEdge(e13);
        graph.addEdge(e14);
        
        allPairSP sp = new allPairSP(graph);
        
    }
}
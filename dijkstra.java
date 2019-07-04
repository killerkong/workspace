import edu.princeton.cs.algs4.*;

class dijkstra{
    public static void main(String[] args){
        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K"};
        int A = 0, B = 1, C = 2, D = 3, E = 4, F = 5, G = 6, H = 7, I = 8, J = 9, K = 10;
		int source = A;
		int n = 11;
		EdgeWeightedGraph Graph = new EdgeWeightedGraph(n);
		Edge e1 = new Edge(A, B, 1);
		Edge e2 = new Edge(A, C, 6);
        Edge e3 = new Edge(A, F, 4);
        Edge e4 = new Edge(B, C, 4);
        Edge e5 = new Edge(B, D, 2);
        Edge e6 = new Edge(C, E, 2);
        Edge e7 = new Edge(D, G, 2);
        Edge e8 = new Edge(D, H, 4);
        Edge e9 = new Edge(E, H, 5);
        Edge e10 = new Edge(F, G, 2);
        Edge e11 = new Edge(F, I, 4);
        Edge e12 = new Edge(G, H, 3);
        Edge e13 = new Edge(G, K, 6);
        Edge e14 = new Edge(H, J, 5);
        Edge e15 = new Edge(I, K, 3);
        Edge e16 = new Edge(J, K, 2);
        
		Graph.addEdge(e1);
		Graph.addEdge(e2);
		Graph.addEdge(e3);
		Graph.addEdge(e4);
		Graph.addEdge(e5);
		Graph.addEdge(e6);
		Graph.addEdge(e7);
		Graph.addEdge(e8);
		Graph.addEdge(e9);
        Graph.addEdge(e10);
        Graph.addEdge(e11);
        Graph.addEdge(e12);
        Graph.addEdge(e13);
        Graph.addEdge(e14);
        Graph.addEdge(e15);
        Graph.addEdge(e16);
        
        dijkstra tree = new dijkstra(Graph, 0);
    }
    private Edge[] edgeTo;
    private double[] distTo;
    private IndexMinPQ<Double> pq;
    
    public dijkstra(EdgeWeightedGraph G, int s){
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        
        for(int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        pq = new IndexMinPQ<Double>(G.V());
        distTo[s] = 0.0;
        pq.insert(s, distTo[s]);
        while(!pq.isEmpty())
            relax(G, pq.delMin());
    }
    private void relax(EdgeWeightedGraph G, int v){
        for(Edge e : G.adj(v)){
            int w = e.other(v);
            
        if(distTo[v] + e.weight() < distTo[w]){
            
            edgeTo[w] = e;
            distTo[w] = distTo[v] + e.weight();
            
            System.out.println(w);
            int ch = 'A';
            System.out.println("Insert: " + (char)(w + ch));
            if(pq.contains(w)){
                pq.changeKey(w, distTo[w]);
                //System.out.println("Change Key: " + w);
            }
            else{
                pq.insert(w, distTo[w]);
                //int ch = 'A';
                //System.out.println("Insert: " + (char)(w + ch));
            } 
        }
        }
    }
}
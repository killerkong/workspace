import java.math.BigInteger;
import java.util.Random;

class RabinKarp{
    private long patHash;
    private int M;
    private long Q;
    private long R;
    private long RM;
    
    public static void main(String[] args){
        String txt = "adfagadhelloadgadgaga";
        String pat = "hello";
        RabinKarp rabin = new RabinKarp(pat);
        int index = rabin.search(txt);
        System.out.println(index + " : " + txt.substring(index, txt.length()));
    }
    public RabinKarp(String pat){
        M = pat.length();
        R = 256;
        Q = longRandomPrime();
        
        RM = 1;
        for(int i = 1; i <= M-1; i++){
            RM = (R * RM) % Q;
        }
        patHash = hash(pat, M);
    }
    private long hash(String key, int M){
        long h = 0;
        for(int j = 0; j < M; j++){
            h = (h * R + key.charAt(j)) % Q;
        }
        return h;
    }
    public int search(String txt){
        int N = txt.length();
        if(N < M) return N;
        long txtHash = hash(txt, M);
        if(patHash == txtHash) return 0;
        
        for(int i = M; i < N; i++){
            txtHash = (txtHash + Q - RM * txt.charAt(i - M) % Q) % Q;
            txtHash = (txtHash * R + txt.charAt(i)) % Q;
            if(patHash == txtHash) return i - M + 1;
        }
        return N;
    }
}
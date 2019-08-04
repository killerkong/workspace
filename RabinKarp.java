import java.math.BigInteger;
import java.util.Random;

class RabinKarp{
    private long patHash, pat1Hash, pat2Hash;
    private int M;
    private int M1, M2;
    private long Q;
    private long R;
    private long RM, RM1, RM2;
    private String pat;
    
    public static void main(String[] args){
        String txt = "adfagadhelloadgadgaga";
        String pat = "hello";
        RabinKarp rabin = new RabinKarp(pat);
        int index = rabin.search(txt);
        System.out.println(index + " : " + txt.substring(index, txt.length()));
        
        String txt2 = "adsdfafdfagaghello_worldadagag";
        String pat2 = "hello_world";
        int k = pat2.indexOf('_');
        RabinKarp rabin2 = new RabinKarp(txt2, pat2, k);
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
    public RabinKarp(String txt, String pat, int k){
        this.pat = pat;
        String pat1 = pat.substring(0, k);
        String pat2 = pat.substring(k+1, pat.length());
        
        M = pat.length();
        M1 = pat1.length();
        M2 = pat2.length();
        
        R = 256;
        Q = longRandomPrime();
        
        RM1 = 1; RM2 = 1;
        for(int i = 1; i <= M1-1; i++){
            RM1 = (R * RM1) % Q;
        }
        for(int i = 1; i <= M2-1; i++){
            RM2 = (R * RM2) % Q;
        }
        pat1Hash = hash(pat1, M1);
        pat2Hash = hash(pat2, M2);
        
        int index = search_wildcard2(txt);
        System.out.println(index + " " + txt.substring(index, txt.length()));
    }
    private long hash(String key, int M){
        long h = 0;
        for(int j = 0; j < M; j++){
            h = (h * R + key.charAt(j)) % Q;
        }
        return h;
    }
    public int search_wildcard(String txt, String pat){
        
        M = pat.length();
        int N = txt.length();
        if(N < M) return N;
        long txtHash = hash(txt, M);
        patHash = hash(pat, M);
        if(patHash == txtHash) return 0;
        
        RM = 1;
        for(int i = 1; i <= M-1; i++){
            RM = (R * RM) % Q;
        }
        for(int i = M; i < N; i++){
            txtHash = (txtHash + Q - RM * txt.charAt(i - M) % Q) % Q;
            txtHash = (txtHash * R + txt.charAt(i)) % Q;
            if(patHash == txtHash) return i - M + 1;
        }
        return N;
    }
    public int search_wildcard2(String txt){
        int N = txt.length();
        if(N < M) return N;
        
        long txtHash1 = hash(txt, M1);
        
        long txtHash2 = hash(txt.substring(M1+1, M), M2);
        if(pat1Hash == txtHash1 && pat2Hash == txtHash2) return 0;
        
        for(int i = M1; i < N; i++){
            txtHash1 = (txtHash1 + Q - RM1 * txt.charAt(i - M1) % Q) % Q;
            txtHash1 = (txtHash1 * R + txt.charAt(i)) % Q;
            if(pat1Hash == txtHash1){
                txtHash2 = hash(txt.substring(i+2, Math.min(N, i+M1+2)), M2);
                if(txtHash2 == pat2Hash) return i - M1 + 1;
            }
        }
        return N;
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
    private static long longRandomPrime(){
        BigInteger prime = BigInteger.probablePrime(31, new Random());
        return prime.longValue();
    }
}
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.io.InputStream;
import java.util.Arrays;
import java.util.*;
import java.io.*;
 
public class Solution3 
{
        public static void main(String[] args) throws Exception
        {
                InputStream inputStream = System.in;
                OutputStream outputStream = System.out;
                Parserdoubt in = new Parserdoubt(inputStream);//new FastScanner(inputStream);
		            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
                nCr2 solver = new nCr2();
                int testCount = in.nextInt();
                for (int i = 1; i <= testCount; i++)
                        solver.solve(i, in, out);
                out.close();
        }
}

class nCr2
{
    int MOD = 1000000007;
    int MAXN = 1000001;

	/* This function calculates (a^b)%MOD */
	public long pow(long a, int b)
	{
	    long x=1,y=a; 
	    while(b > 0)
	    {
		if(b%2 == 1)
		{
		    x=(x*y);
		    if(x>MOD) x%=MOD;
		}
		y = (y*y);
		if(y>MOD) y%=MOD; 
		b /= 2;
	    }
	    return x;
	}    

	/*  Modular Multiplicative Inverse
	    Using Euler's Theorem
	    a^(phi(m)) = 1 (mod m)
	    a^(-1) = a^(m-2) (mod m) */
	public long inverseEuler(long n)
	{
	    return pow(n,MOD-2);
	}

	public long nCr(int n, int r)
	{
	    long[] f = new long[n+1];
	    f[0] = 1; f[1] = 1;
	    for (int i=2; i<=n;i++)
		f[i]= (f[i-1]*i) % MOD;
	    return (f[n]*((inverseEuler(f[r]) * inverseEuler(f[n-r])) % MOD)) % MOD;
	}

	public void solve(int testNumber, Parserdoubt pd, PrintWriter pw) throws Exception
	{ 
	    int N = pd.nextInt() - 1;
	    int R = pd.nextInt() - 1;

	    pw.print(nCr(N+R, R) + "\n");

	}//end of solve method()
}
 
class FastPrinter extends PrintWriter {
 
    public FastPrinter(OutputStream out) {
        super(out);
    }
 
    public FastPrinter(Writer out) {
        super(out);
    }
 
 
}

class Parserdoubt
{

	final private int BUFFER_SIZE = 1 << 17;
	private DataInputStream din;
	private byte[] buffer;
	private int bufferPointer, bytesRead;

	public Parserdoubt(InputStream in)
	{	
		din = new DataInputStream(in);
		buffer = new byte[BUFFER_SIZE];
		bufferPointer = bytesRead = 0;
	}

	public String nextString() throws Exception
	{
		StringBuffer sb=new StringBuffer("");
		byte c = read();
		while (c <= ' ') c = read();

		do
		{
			sb.append((char)c);
			c=read();
		}
		while(c>' ');
		return sb.toString();
	}

	public char nextChar() throws Exception
	{

		byte c=read();
		while(c<=' ') c= read();
		return (char)c;
	}

	public int nextInt() throws Exception
	{
		int ret = 0;
		byte c = read();
		while (c <= ' ') c = read();
		
		do
		{
			ret=ret*10+c-'0';
			c=read();
		}
		while (c > ' ');

		return ret;
	}

	public long nextLong() throws Exception
	{
		long ret = 0;
		byte c = read();
		while (c <= ' ') c = read();
		
		do
		{
			ret=ret*10+c-'0';
			c=read();
		}
		while (c > ' ');

		return ret;
	}
	private void fillBuffer() throws Exception
	{
		bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
		if (bytesRead == -1) buffer[0] = -1;
	}

	private byte read() throws Exception
	{
		if (bufferPointer == bytesRead) fillBuffer();
		return buffer[bufferPointer++];
	}

}

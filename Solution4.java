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
 
public class Solution4 
{
        public static void main(String[] args) throws Exception
        {
                InputStream inputStream = System.in;
                OutputStream outputStream = System.out;
                Parserdoubt in = new Parserdoubt(inputStream);//new FastScanner(inputStream);
		            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
                SpecialMultiple solver = new SpecialMultiple();
                int testCount = in.nextInt();
                for (int i = 1; i <= testCount; i++)
                        solver.solve(i, in, out);
                out.close();
        }
}

class SpecialMultiple
{
	public long getSpecialMultiple(int N)
	{
		ArrayList<Long> candidates = new ArrayList<Long>();
		candidates.add(new Long(9));
		while(candidates.size() != 0)
		{
			Long currCandidate = candidates.remove(0);
			if(currCandidate%N != 0)
			{
				candidates.add(new Long(currCandidate*10));
				candidates.add(new Long((currCandidate*10)+9));
			}
			else
			{
				return currCandidate;
			}
		}
		return -1;
	}
	public void solve(int testNumber, Parserdoubt pd, PrintWriter pw) throws Exception
	{ 
	    int N = pd.nextInt();
	    long ans = getSpecialMultiple(N);

	    pw.print(ans + "\n");

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

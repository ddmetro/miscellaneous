import java.io.*;
import java.util.*;
import java.math.*;
 
public class Solution implements Runnable 
{
	int K, C;
 	int[] perm;
	ArrayList<Integer> adj[];
	int[] mark;
	int cmp;
	ArrayList<Integer> component[];
	StringBuffer ans;

	public void dfs(int u)
	{
		mark[u] = cmp;
		int size = adj[u].size();
		for(int count = 0; count < size; count++)
		{
			int v = adj[u].get(count);
			if(mark[v] == 0)
				dfs(v);
		}
	}
 
	public void run()
	{
		try
		{
			K = parser.nextInt();
			perm = new int[K+1];
			perm[0] = -1;//not to be considered
			adj = new ArrayList[K+1];
			mark = new int[K+1];
			mark[0] = -1;//not to be considered

			for(int k = 0; k < K; k++)
			{
				perm[k+1] = parser.nextInt();
			}

			for (int i = 0; i < K; i++) 
			{
				adj[i+1] = new ArrayList<Integer>();
				for(int j = 0; j < K; j++)
				{
					C = parser.nextChar();
					if(C == 'Y')
					{
						adj[i+1].add((j+1));
					}			
				}
			}

			Arrays.fill(mark, 0);
			cmp = 0;
			for(int i = 1; i < K+1; i++)
			{
				if(mark[i] == 0)
				{
					cmp++;
					dfs(i);
				}
			}

			component = new ArrayList[K+1];
			for(int count = 0; count < cmp; count++)
			{
				component[count+1] = new ArrayList<Integer>();
			}

			for(int count = 0; count < K; count++)
			{
				component[mark[count+1]].add(perm[count+1]);
			}

			for(int count = 0; count < cmp; count++)
			{
				Collections.sort(component[count+1]);
			}

			ans = new StringBuffer();
			for(int count = 0; count < K; count++)
			{
				int i = component[mark[count+1]].remove(0);
				ans.append(i+" ");
			}

			output.print(ans+"\n");

			output.flush();
		}
		catch(Exception e)
		{
			System.out.println("Exception occurred:");
			e.printStackTrace();
		}

	}//end of run
 
	public static void main(String[] args) 
	{
	//try { input = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt"))); } catch (Exception e) {}	 
		new Solution().run();
	}
 
	Parserdoubt parser = new Parserdoubt(System.in);
	PrintWriter output = new PrintWriter(System.out);
	StringTokenizer tokenizer = null;
 
}//end of class Main

//Parser class for better IO
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

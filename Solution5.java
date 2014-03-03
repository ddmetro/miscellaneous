import java.io.*;
import java.util.*;
import java.math.*;
 
public class Solution5 implements Runnable 
{
	int T, N;
	int[][] graph;
	boolean[] isVisited;
 
	public void run()
	{
		try
		{
			T = 1;
			for(int t = 0; t < T; t++)
			{
				N = parser.nextInt();
				graph = new int[N+1][N+1];

				isVisited = new boolean[N+1];
				isVisited[0] = true;//not to be considered

				for(int i = 0; i < N; i++)
				{
					for(int j = 0; j < N; j++)
					{
						int u = i+1;
						int v = j+1;
						int c = parser.nextInt();

						graph[u][v] = c;						
					}
				}

				solve();
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception occurred:");
			e.printStackTrace();
		}

	}//end of run

	public void solve()
	{	
		int sum = 0, visitedNodes = 0, startNode = -1, currNode = -1, nodesInPath = 0;
		for(int count = 1; count <= N; count++)
		{
			startNode = count;
			visitedNodes = 1;
			nodesInPath = 1;
			currNode = startNode;

			Arrays.fill(isVisited, false);
			isVisited[0] = true;
			isVisited[currNode] = true;

			sum = 0;
			while(visitedNodes != N)
			{
				int nextNode = getMinimumEdge(currNode);
				int newAvg = (float)(sum + graph[currNode][nextNode])/(float)(visitedNodes+1);
				int oldAvg = (float)(sum)/(float)(visitedNodes);
				if(oldAvg > newAvg)
				{
					sum = sum + graph[currNode][nextNode];
				}
				isVisited[nextNode] = true;
				currNode = nextNode;
				visitedNodes++;
			}

			sum = sum + graph[currNode][startNode];//sum / (visitedNodes+1)			
		}

		int d = 0;
		//print rational number
		while((d = gcd(sum, visitedNodes)) != 1)
		{
			sum = sum/d;
			visitedNodes = visitedNodes/d;
		}
		output.print(sum+"/"+visitedNodes+"\n");
		output.flush();		
	}

	public int gcd(int a, int b)
	{
		a = Math.max(a, b);
		b = Math.min(a, b);

		int d = 0;
		for(d = a;(a % d !=0 || b % d != 0);d--)  {
		}

		return d;
	}

	public int getMinimumEdge(int node)
	{
		int minCost = Integer.MAX_VALUE;
		int nextNode = -1;
		for(int count = 1; count <= N; count++)
		{
			if(!isVisited[count] && graph[node][count] < minCost)
			{
				nextNode = count;
				minCost = graph[node][count];
			}
		}

		return nextNode;
	}
 
	public static void main(String[] args) 
	{
	//try { input = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt"))); } catch (Exception e) {}	 
		new Solution5().run();
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

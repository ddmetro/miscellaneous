import java.io.*;
import java.util.*;
import java.math.*;
 
public class Solution1 implements Runnable 
{
	int T, N, M, K;
	ArrayList<Edge> adj[];
	int[] mark;
	int cmp;
	ArrayList<Integer> component[];
	long totalCostAllComponents;
	PriorityQueue<Integer> edgeWeightsInMSF;

	public void dfs(int u)
	{
		mark[u] = cmp;
		int size = adj[u].size();
		for(int count = 0; count < size; count++)
		{
			Edge e = adj[u].get(count);
			if(mark[e.v] == 0)
				dfs(e.v);
		}
	}
 
	public void run()
	{
		try
		{
			T = parser.nextInt();
			for(int t = 0; t < T; t++)
			{
				N = parser.nextInt();
				adj = new ArrayList[N+1];
				mark = new int[N+1];
				mark[0] = -1;//not to be considered

				M = parser.nextInt();
				K = parser.nextInt();

				for(int count = 0; count < N; count++)
				{
					adj[count+1] = new ArrayList<Edge>();
				}

				for(int m = 0; m < M; m++)
				{
					int u = parser.nextInt();
					int v = parser.nextInt();
					int c = parser.nextInt();

					adj[u].add(new Edge(v, c));
					adj[v].add(new Edge(u, c));
				}

				Arrays.fill(mark, 0);
				cmp = 0;
				for(int i = 1; i < N+1; i++)
				{
					if(mark[i] == 0)
					{
						cmp++;
						dfs(i);
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
		totalCostAllComponents = 0;
		edgeWeightsInMSF = new PriorityQueue<Integer>();

		if(cmp > K)
		{
			output.print("Impossible!\n");
			return;
		}

		component = new ArrayList[N+1];
		for(int count = 0; count < cmp; count++)
		{
			component[count+1] = new ArrayList<Integer>();
		}

		for(int count = 0; count < N; count++)
		{
			component[mark[count+1]].add(count+1);
		}

		for(int count = 0; count < cmp; count++)
		{
			PrimMST p = new PrimMST(N);

			for(int vCount = 0; vCount < component[count+1].size(); vCount++)
			{
				int node = component[count+1].get(vCount);
				p.adj[node] = adj[node];
			}

			p.findMST(component[count+1].get(0));
			totalCostAllComponents += p.totalCost;

			for(int edgeCount = 0; edgeCount < p.value.length; edgeCount++)
			{
				if(p.value[edgeCount] != Integer.MAX_VALUE)
				{
					edgeWeightsInMSF.add(p.value[edgeCount]*(-1));
				}
			}
		}

		int diff = K - cmp;
		for(int count = 0; count < diff; count++)
		{
			int edgeWeight = edgeWeightsInMSF.poll();
			totalCostAllComponents += edgeWeight;
		}

		output.print(totalCostAllComponents+"\n");
		output.flush();		
	}
 
	public static void main(String[] args) 
	{
	//try { input = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt"))); } catch (Exception e) {}	 
		new Solution1().run();
	}
 
	Parserdoubt parser = new Parserdoubt(System.in);
	PrintWriter output = new PrintWriter(System.out);
	StringTokenizer tokenizer = null;
 
}//end of class Main

class Vertex implements Comparable<Vertex>
{
	int id;
	int cost;

	public Vertex(int id, int cost)
	{
		this.id = id;
		this.cost = cost;
	}

	public int hashCode()
	{
		return id;
	}

	public boolean equals(Object obj)
	{
		if(obj == null)
			return false;

		if(!(obj instanceof Vertex))
			return false;

		Vertex v = (Vertex)obj;
		if(v.id == this.id)
			return true;

		return false;
	}

	public int compareTo(Vertex v)
	{
		if(v == null)
			return 1;
		else
		{
			return this.cost - v.cost;
		}
	}

	public String toString()
	{
		return "id: " + id + ", cost: " + cost;
	}
}//end of class Vertex

class Edge
{
	int v;
	int cost;

	public Edge(int v, int c)
	{
		this.v = v;
		this.cost = c;
	}
}

class PrimMST
{
	int N;
	int parent[];
	int value[];
	ArrayList<Edge> adj[];
	PriorityQueue<Vertex> queue;
	boolean inHeap[];
	long totalCost;

	public PrimMST(int n)
	{
		N = n;
		parent = new int[N+1];
		value = new int[N+1];
		adj = new ArrayList[N+1];
		queue = new PriorityQueue<Vertex>();
		inHeap = new boolean[N+1];

		Arrays.fill(parent, -1);
		Arrays.fill(value, Integer.MAX_VALUE);
		totalCost = 0;

		for(int count = 0; count < N; count++)
		{
			adj[count+1] = new ArrayList<Edge>();
		}
	}

	public void findMST(int startNode)
	{
		for(int count = 0; count < N; count++)
		{
			queue.offer(new Vertex(count+1, Integer.MAX_VALUE));
		}

		value[startNode] = 0;
		updateQueue(new Vertex(startNode, 0));

		while(queue.size() != 0)
		{
			Vertex v = queue.poll();
			for(int count = 0; count < adj[v.id].size(); count++)
			{
				Edge e = adj[v.id].get(count);
				if(inHeap[e.v] == false)
				{
					if(e.cost < value[e.v])
					{
						parent[e.v] = v.id;
						value[e.v] = e.cost;
						updateQueue(new Vertex(e.v, e.cost));
					}					
				}
			}

			inHeap[v.id] = true;
			if(v.cost != Integer.MAX_VALUE)
				totalCost = totalCost + v.cost;
			//System.out.println(v);
		}
	}

	public void updateQueue(Vertex v)
	{
		if(queue.contains(v))
		{
			queue.remove(v);
		}
		queue.offer(v);
	}

}//end of class PrimMST

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

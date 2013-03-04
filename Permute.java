public class Permute
{

  	public void permuteString(String str)
	{
		permute(str.substring(1,str.length()), str.charAt(0)+"");

		for(int i = 1; i <= str.length()-1; i++)
		{
			char temp;
			char a[] = str.toCharArray();
			temp = a[i];
			a[i] = a[0];
			a[0] = temp;
			str = new String(a);
			permute(str.substring(1,str.length()), str.charAt(0)+"");	
		}

	}

	public void permute(String s1, String s2)
	{
		
		if(s1 != null && s1.length() == 2)
		{
			System.out.println(s2+s1);
			System.out.println(s2+s1.charAt(1)+s1.charAt(0));
			return;
		}

		permute(s1.substring(1,s1.length()), s2+s1.charAt(0));

		for(int i = 1; i <= s1.length()-1; i++)
		{
			char temp;
			String stemp;
			char a[] = s1.toCharArray();
			temp = a[i];
			a[i] = a[0];
			a[0] = temp;
			stemp = new String(a);
			permute(stemp.substring(1,stemp.length()), s2+stemp.charAt(0));
		}	

	}

	public static void main(String args[])
	{
		Permute abc = new Permute();
		abc.permuteString("mungha");

		//String d = "dhiraj";
		//System.out.println(d.substring(1,d.length()));
	}


}

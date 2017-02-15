package test.target;

public class Test2{
	public static void main(String args[]){
		int x=0;
		int y=1;
		switch(x){
			case 0:
				System.out.println("xは0");
				System.out.println("yは1");
			case 1:
				x++;
			default:
				System.out.println("なにもしない");
		}
		y--;

	}
}
package test;

public class Original{
	public static void main(String args[]){
		int x=0;
		int y=1;
		switch(x){
			case 0:
				System.out.println("xは0");
			case 1:
				System.out.println("xは1");
			default:
				System.out.println("なにもしない");
		}
		for(int i=0;i<10;i++){
			y--;		
		}

	}
}
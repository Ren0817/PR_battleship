import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

public class Statistic {
	private int shot=0;
	private int miss=0;
	private int hit=0;
	
	public void increaseMiss() {
		this.shot++;
		this.miss++;
	}
	
	public void increaseHit() {
		this.shot++;
		this.hit++;
	}
	
	
	public String getMissRate() {
		if(shot==0) {return "-";}
		else {
			 DecimalFormat df = new DecimalFormat("##.00%"); 
		     String rt = df.format((float)miss/shot);//���ص���String���� 
			return rt;
		}
	}
	
	

	public String getHitRate() {
		if(shot==0) {return "-";}
		else {
			 DecimalFormat df = new DecimalFormat("##.00%"); 
		     String rt = df.format((float)hit/shot);//���ص���String���� 
			return rt;
		}
	}

	public int getShot() {
		return shot;
	}

	public void setShot(int shot) {
		this.shot = shot;
	}

	public int getMiss() {
		return miss;
	}

	public void setMiss(int miss) {
		this.miss = miss;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}


	public static void main(String[] args) throws ParseException {
		int a=1099;
	        int b=93; 
	        DecimalFormat df = new DecimalFormat("##.00%"); 
	        String num = df.format((float)a/b);//
	        System.out.println("ABCDEF".contains("A"));
 
    }

	
	
}

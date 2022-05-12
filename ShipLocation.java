import java.text.DecimalFormat;
import java.text.ParseException;

public class ShipLocation {
	private int x=0;
	private int y=0;
	private boolean isvertical;
	private String type;
	private int length;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isIsvertical() {
		return isvertical;
	}

	public void setIsvertical(boolean isvertical) {
		this.isvertical = isvertical;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
}

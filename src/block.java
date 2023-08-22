import java.util.Arrays;

public class block implements Cloneable{

	//type 
	public String T;
	//h or v for horizontal and vertical
	public char Direction;
	// Start and end points tuple
	public int[] StartPoint = new int[2];
	public int[] MiddlePoint = new int[2];
	public int[] EndPoint = new int[2];
	// Size ; Either 2 or 3
	public int Size;

	//for two points block
	public block(String Ty,char direction, int[] startPoint, int[] endPoint, int size) {
		T = Ty;
		Direction = direction;
		StartPoint = startPoint;
		EndPoint = endPoint;
		Size = size;
	}

	//for three points block
	public block(String Ty,char direction, int[] startPoint,int[] middpoint, int[] endPoint, int size) {
		T = Ty;
		Direction = direction;
		MiddlePoint = middpoint;
		StartPoint = startPoint;
		EndPoint = endPoint;
		Size = size;
	}

	// Return the points of the block as a string in the format of "x1,y1,x2,y2"
	public String agent_GetPoints() {
		return StartPoint[0] + "," + StartPoint[1] + "," + EndPoint[0] + "," + EndPoint[1];
	}

	// Method to compare if two blocks are equal or not
	public boolean equals(block b) {
		if (this.T.equals(b.T) &&
				this.Direction == b.Direction &&
				Arrays.equals(this.StartPoint, b.StartPoint) &&
				Arrays.equals(this.EndPoint, b.EndPoint) &&
				this.Size == b.Size) {
			return true;
				}
		return false;
	}

	@Override
	public Object clone() {
		// Implement the clone() method.
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

}

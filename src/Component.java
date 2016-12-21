public class Component {

	private int xMax;
	private int xMin;
	private int yMax;
	private int yMin;
	private int id;
	private int[][] subarray;
	
	public Component(int id){ //initial value
		this.id = id;
		this.xMax = -100;
		this.yMax = -100;
		this.xMin = -100;
		this.yMin = -100;
	}
	
	//get methods 
	public int getXMax(){
		return this.xMax;
	}
	
	public int getYMax(){
		return this.yMax;
	}
	
	public int getXMin(){
		return this.xMin;
	}
	
	public int getYMin(){
		return this.yMin;
	}

	public int[][] getSubArray(){
		return this.subarray;
	}
	
	//set methods 
	public void setXMax(int xMax){
		this.xMax = xMax;
	}
	
	public void setYMax(int yMax){
		this.yMax = yMax;
	}
	
	public void setXMin(int xMin){
		this.xMin = xMin;
	}
	
	public void setYMin(int yMin){
		this.yMin = yMin;
	}
	
	void setSubArray(int[][] arr){ //set the component sub-array
		subarray = new int[xMax-xMin + 1][yMax-yMin + 1];
		for(int i = 0; i < subarray.length; i++){
			for(int j = 0; j < subarray[0].length; j++){
				subarray[i][j] = arr[xMin+i][yMin+j];
			}
		}
	}
}

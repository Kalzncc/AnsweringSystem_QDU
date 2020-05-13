package sixiuSystem;

//判断题类

public class JProblem extends Problem {
	boolean rightans; //正确答案
	boolean getans; //作答答案
	public JProblem() {
		super();
		rightans = getans = false;
		type = 0;
	}
	public JProblem(String text, int points, boolean rightans, String comment) {//构造
		super(text, points, comment, 0);
		this.rightans = rightans;
	}
	public String toString() { //转化题目为String
		return super.toString() + "\t(  )\n";
	}
	public int get_rightans() {
		return rightans ? 1 : 0;
	}
	public int get_getans() throws Exception{
		if (!ans_status) throw new Unanswering();
		return getans ? 1 : 0;
	}
	
	public void set_rightans(int rightans) throws Exception{
		if (rightans > 2) throw new OptionCrossBoundary();
		this.rightans = (rightans == 1);
	}
	
	
	public void answering (int getans) throws Exception {//作答题目,getans为1是选对, 为0是选错
		if (getans >= 2) throw new OptionCrossBoundary();
		this.getans = (getans == 1);
		ans_status = true;
		if (this.getans ^ this.rightans)
			getpoints = 0;
		else getpoints = points;
	}
	public void unanswering () { //放弃作答题目
		ans_status = false;
		getpoints = 0;
	}
}
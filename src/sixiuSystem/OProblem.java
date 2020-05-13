package sixiuSystem;

import java.util.ArrayList;

//单选题类
public class OProblem extends Problem{
	ArrayList<String> option = new ArrayList<String>(); //选项题面
	int option_num; //选项数量,选项最多26个,(26个字母)
	int rightans; //正确答案(0~25代表A~Z),
	int getans; //作答答案
	public OProblem() {
		super();
		option_num = rightans = getans = 0;
		type = 1;
	}
	public OProblem(String text, int points, int rightans, ArrayList<String> option, String comment) throws Exception{
		super(text, points, comment, 1);
		if (option.size() > 26) throw new OptionNumTooLarge();
		if (rightans >= option.size()) throw new OptionCrossBoundary();
		option_num = option.size();
		this.rightans = rightans;
		this.option = (ArrayList<String>) option.clone();
	}
	public String toString() { //转化题目为String
		String res = super.toString();
		for (int i = 0; i < option_num; i++) {
			res += String.valueOf((char)(i+'A')) + " " + option.get(i) + "\n";
		}
		return res;
	}
	public void addoption(String t) throws Exception{ //添加选项
		if (option.size() >= 26) throw new OptionNumTooLarge();
		option_num++;
		option.add(t);
	}
	public void set_rightans(int rightans) throws Exception{
		if (rightans >= option_num) throw new OptionCrossBoundary();
		this.rightans = rightans;
	}
	
	public int get_getans() throws Exception{
		if (!ans_status) throw new Unanswering();
		return getans;
	}
	public int get_rightans() {
		return rightans;
	}
	public String get_option(int index) throws Exception { //得到第index项选项,返回String
		if (index >= option_num) throw new OptionCrossBoundary();
		return option.get(index);
	}
	public ArrayList<String> get_option() {
		return option;
	}
	public int get_option_num() {
		return option.size();
	}
	
	
	
	public void answering(int getans) throws Exception{ //作答题目,作答答案为getans
		if (getans >= option_num) throw new OptionCrossBoundary();
		ans_status = true;
		this.getans = getans;
		if (getans == rightans) getpoints = points;
		else getpoints = 0;
	}
	public void unanswering() { //放弃作答
		ans_status = false;
		getpoints = 0;
	}
}
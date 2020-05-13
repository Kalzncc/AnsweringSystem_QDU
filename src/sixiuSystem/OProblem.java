package sixiuSystem;

import java.util.ArrayList;

//��ѡ����
public class OProblem extends Problem{
	ArrayList<String> option = new ArrayList<String>(); //ѡ������
	int option_num; //ѡ������,ѡ�����26��,(26����ĸ)
	int rightans; //��ȷ��(0~25����A~Z),
	int getans; //�����
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
	public String toString() { //ת����ĿΪString
		String res = super.toString();
		for (int i = 0; i < option_num; i++) {
			res += String.valueOf((char)(i+'A')) + " " + option.get(i) + "\n";
		}
		return res;
	}
	public void addoption(String t) throws Exception{ //���ѡ��
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
	public String get_option(int index) throws Exception { //�õ���index��ѡ��,����String
		if (index >= option_num) throw new OptionCrossBoundary();
		return option.get(index);
	}
	public ArrayList<String> get_option() {
		return option;
	}
	public int get_option_num() {
		return option.size();
	}
	
	
	
	public void answering(int getans) throws Exception{ //������Ŀ,�����Ϊgetans
		if (getans >= option_num) throw new OptionCrossBoundary();
		ans_status = true;
		this.getans = getans;
		if (getans == rightans) getpoints = points;
		else getpoints = 0;
	}
	public void unanswering() { //��������
		ans_status = false;
		getpoints = 0;
	}
}
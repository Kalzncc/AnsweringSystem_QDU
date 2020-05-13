package sixiuSystem;

import java.util.*;


//��ѡ����(�Ȳ鿴OProblem,��ѡ����)
public class MOProblem extends Problem {
	ArrayList<String> option = new ArrayList<String>(); //ѡ����Ŀ
	int option_num; //ѡ������
	ArrayList<Integer> rightans = new ArrayList<Integer>(); //��ȷ������.
								//�����ΪABD,��rightans = {0,1,4}
	ArrayList<Integer> getans = new ArrayList<Integer>();
								//���������
	private ArrayList<Integer> check_ans(ArrayList<Integer> rightans) { //��������������
		HashSet<Integer> h = new HashSet<Integer>(rightans);			//����,ȥ��
		rightans.clear();
		rightans.addAll(h);
		Collections.sort(rightans);
		return rightans;
	}
	private void check_ans_point() { //����rightans�ó����� (ѡ���÷�,��ѡ��һ���)
		boolean[] ria = new boolean[option_num+2];
		boolean[] gea = new boolean[option_num+2];
		for (int i = 0; i <= option_num; i++) ria[i] = gea[i] = false;
		for (int i : rightans) ria[i] = true;
		for (int i : getans) gea[i] =true;
		int flag = 0;
		for (int i = 0; i < option_num; i++)
		{
			if (ria[i] ^ gea[i])
			{
				if (!gea[i]) {
					flag = 1;
				}
				else {
					flag = 2;
					break;
				}
			}
		}
		if (flag == 0) {getpoints = points;}
		if (flag == 1) {getpoints = points >> 1;}
		if (flag == 2) {getpoints = 0;}
		
	}
	public MOProblem() {
		super(); 
		option_num = 0;
		type = 2;
	}
	public MOProblem(String text, int points, ArrayList<Integer> rightans, ArrayList<String> option, String comment) throws Exception{
		super(text, points, comment, 2);
		rightans = check_ans(rightans);
		if (option.size() > 26) throw new OptionNumTooLarge();
		if (rightans.get(rightans.size()-1) >= option.size()) throw new OptionCrossBoundary();
		this.rightans = (ArrayList<Integer>) rightans.clone();
		option_num = option.size();
		this.option = (ArrayList<String>) option.clone();
	}
	
	public int get_option_num() {
		return option_num;
	}
	public ArrayList<String> get_option() {
		return option;
	}
	public String toString() { //ת����ĿΪString
		String res = super.toString();
		for (int i = 0; i < option_num; i++)
			res += String.valueOf((char)(i + 'A')) + " " + option.get(i) + "\n";
		return res;
	}
	public void add_option(String t) throws Exception { //���ѡ��
		if (option_num >= 26) throw new OptionNumTooLarge();
		option_num++;
		option.add(t);
	}
	public void set_rightans (ArrayList<Integer> rightans) throws Exception{
		rightans = check_ans(rightans);
		if (rightans.get(rightans.size()-1) >= option.size()) throw new OptionCrossBoundary();
		this.rightans = (ArrayList<Integer>) rightans.clone();
	}
	public ArrayList<Integer> get_getansA() throws Exception {
		if (!ans_status) throw new Unanswering();
		return getans;
	}
	public String get_option(int index) throws Exception {
		if (index >= option_num) throw new OptionCrossBoundary();
		return option.get(index);
	}
	public ArrayList<Integer> get_rightansA() {
		return rightans;
	}
	public void answering(ArrayList<Integer> getans) throws Exception{ //������Ŀ,���������Ϊgetans
		getans = check_ans(getans);
		if (rightans.get(rightans.size()-1) >= option.size()) throw new OptionCrossBoundary();
		ans_status = true;
		this.getans = (ArrayList<Integer>) getans.clone();
		check_ans_point();
	}
	public void unanswering() { //��������
		getpoints = 0;
		ans_status = false;
		getans.clear();
	}

}

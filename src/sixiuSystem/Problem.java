package sixiuSystem;

import java.util.ArrayList;

// ������, ��������
public abstract class Problem {
	int points, getpoints; // points,�����ֵ, getpoint,����÷�
	String ptext; // ����
	int type;
	String comment; // �𰸽���
	
	boolean ans_status; // ����״̬,falseδ����,true������
	
	public Problem() { // �޲ι���
		ans_status = false;
		getpoints = points = 0;
		ptext = "";
		comment = "";
	}
	public Problem(String ptext, int points, String comment, int type) {
		//��������
		ans_status = false;
		getpoints = 0;
		this.points = points;
		this.ptext = ptext;
		this.comment = comment;
		this.type = type;
	}
	
	public String toString() {
		return ptext + "(" + String.valueOf(points) + "��" + ")\n"; //������ת��ΪString
	}
	
	public boolean get_status() {
		return ans_status;
	}
	public String get_text() {
		return ptext;
	}
	public String get_comment() {
		return comment;
	}
	public int get_points() {
		return  points;
	}
	public int get_getpoints() {
		return getpoints;
	}
	public void set_ptext(String ptext) {
		this.ptext = ptext;
	}
	public void set_ans_status(boolean ans_status) {
		this.ans_status = ans_status;
	}
	public void set_points(int points) {
		this.points = points;
	}
	public void set_getpoints(int getpoints) {
		this.getpoints = getpoints;
	}
	public void set_comment(String comment) {
		this.comment = comment;
	}
	public int get_type() {
		return type;
	}
	
	public int get_option_num() {return 0;}
	public int get_rightans() {return 0;}
	public int get_getans() throws Exception {return 0;}
	public ArrayList<Integer> get_getansA() throws Exception {return null;}
	public ArrayList<Integer> get_rightansA() {return null;}
	public String get_option(int index) throws Exception {return null;}
	public ArrayList<String> get_option() {return null;}
	public void answering(int getans) throws Exception{}
	public void answering(ArrayList<Integer> getans) throws Exception{}
	public void unanswering() {}
	
}

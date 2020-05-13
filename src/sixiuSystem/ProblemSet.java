package sixiuSystem;

import java.util.ArrayList;


import java.io.*;
//��Ŀ�����壺
//title����Ŀ������
//comment����Ŀ������
//point:��Ŀ���ܷ�
//getpoint����Ŀ���ܵ÷�
// time �� ��Ŀ����ʱ
import java.io.ObjectInputStream.GetField;


//��������:
//jproblemset �� �ж�������   jproblemset_num ����
//oproblemset  �� ��ѡ������  oproblemset ����
//oproblemset �� ��ѡ������  moproblemset ����


//get_**() �������õ���Ӧ�ı���


/*1.JProblem get_jproblem(int index)---------���ص�index���ж���
 *2.OProblem get_oproblem(int index)---------���ص�index����ѡ��
 *3.MOProblem get_oproblem(int index)---------���ص�index����ѡ��
*/


/*
 * add_problem(JProblem pr)---------���һ���ж���
 * ����add_problem����һ��
 */


/* remove (int type, int index)
 * �Ƴ���index�����⣨type==0ʱ���Ƴ���index���ж��⣬type==1ʱ���Ƴ���index����ѡ�⣬type==2ʱ���Ƴ���index����ѡ�⣩
 */


/*
 * answering (int type, int index, int ans)
 * ����������type==0ʱ�������index���ж��⣬type==1ʱ�������index����ѡ�⣩
 * �����Ϊans
 * 
 * answering (int index, ArrayList<Integer> ans)
 * �������������index����ѡ�⣬�𰸼���Ϊans
 * 
 */

/*
 * unanswering (int type, int index)
 * ��������������type==0ʱ�����������index���ж��⣬type==1ʱ�����������index����ѡ�⣬type==2ʱ�����������index����ѡ�⣩
 */


/*
 * 
 * void input(String Dir)
 * //����·��Dir
 * //��·������һ������ļ�����׺��Ϊ.pbs����ֱ������һ�����
 * 
 * 
 * 
 * 
 * void output(String Dir)
 * //����ǰ������Dir
 * 
 * 
 *  //����input("D:text/tp.pbs"), ������ȡd���ļ���text�����tp.pbs�ļ��������
 *  //����output("D:text/tp.pbs"), ���������d���ļ���text�����tp.pbs�ļ����������ڵ��⼯���������д��
 *   //��������������ʵ�֡�
 * 
 * 
 */
public class ProblemSet {
	String title;
	String comment;
	ArrayList<JProblem> jproblemset = new ArrayList<JProblem>();
	int jproblem_num;
	ArrayList<OProblem> oproblemset = new ArrayList<OProblem>();
	int oproblem_num;
	ArrayList<MOProblem> moproblemset = new ArrayList<MOProblem>();
	int moproblem_num;
	boolean ans_status;
	int point, getpoint;
	int time;
	public ProblemSet() {
		title = ""; time = 0;
		comment = "";
		point = 0;
		ans_status = false;
		jproblem_num = oproblem_num = moproblem_num = 0;
	}
	public ProblemSet(String title, String comment, int time) {
		this.title = title;
		this.comment = comment;
		this.time = time;
		this.point = 0;
		this.ans_status = false;
		jproblem_num = oproblem_num = moproblem_num = 0;
	}
	public int get_time() {
		return time;
	}
	public String get_title() {
		return title;
	}
	public String get_comment() {
		return comment;
	}
	public int get_jproblem_num() {
		return jproblem_num;
	}
	public int get_oproblem_num() {
		return oproblem_num;
	}
	public int get_moproblem_num() {
		return moproblem_num;
	}
	public int get_point() {
		return point;
	}
	public int get_getpoint() {
		return getpoint;
	}
	public void set_title(String title) {
		this.title = title;
	}
	public void set_comment(String comment) {
		this.comment = comment;
	}
	public JProblem get_jproblem(int index) throws Exception{
		if (index >= jproblem_num) throw new ProblemCrossBoundary();
		return jproblemset.get(index);
	}
	public OProblem get_oproblem(int index) throws Exception{
		if (index >= oproblem_num) throw new ProblemCrossBoundary();
		return oproblemset.get(index);
	}
	public MOProblem get_moproblem(int index) throws Exception{
		if (index >= moproblem_num) throw new ProblemCrossBoundary();
		return moproblemset.get(index);
	}
	public void add_problem(JProblem pr) {
		jproblemset.add(pr); jproblem_num = jproblemset.size();
		point += pr.get_points();
		ans_status = ans_status || pr.get_status();
		if (pr.get_status()) getpoint += pr.get_getpoints();
	}
	public void add_problem(OProblem pr) {
		oproblemset.add(pr); oproblem_num = oproblemset.size();
		point += pr.get_points();
		ans_status = ans_status || pr.get_status();
		if (pr.get_status()) getpoint += pr.get_getpoints();
	}
	public void add_problem(MOProblem pr) {
		moproblemset.add(pr); moproblem_num = moproblemset.size();
		point += pr.get_points();
		ans_status = ans_status || pr.get_status();
		if (pr.get_status()) getpoint += pr.get_getpoints();
	}
	public void insert_problem(int type, int index, Problem p) throws RuntimeException{
		if (type == 0) {
			if (p instanceof JProblem) {
				jproblemset.add(index, (JProblem) p);
				point += p.get_points();
				jproblem_num = jproblemset.size();
			}
			else throw new RuntimeException("Type Error");
		}
		else if (type == 1){
			if (p instanceof OProblem) {
				oproblemset.add(index, (OProblem) p);
				point += p.get_points();
				oproblem_num = oproblemset.size();
			}
			else throw new RuntimeException("Type Error");
		}
		else {
			if (p instanceof MOProblem) {
				moproblemset.add(index, (MOProblem) p);
				point += p.get_points();
				moproblem_num = moproblemset.size();
			}
			else throw new RuntimeException("Type Error");
		}
	}
	public void remove (int type, int index) throws Exception {
		if (type >= 3) throw new ProblemCrossBoundary();
		if (type == 0){
			if (index >= jproblem_num) throw new ProblemCrossBoundary();
			point -= jproblemset.get(index).get_points();
			jproblemset.remove(index);
			jproblem_num = jproblemset.size();
		}
		else if(type == 1) {
			if (index >= oproblem_num) throw new ProblemCrossBoundary();
			point -= oproblemset.get(index).get_points();
			oproblemset.remove(index);
			oproblem_num = oproblemset.size();
		}
		else {
			if (index >= moproblem_num) throw new ProblemCrossBoundary();
			point -= moproblemset.get(index).get_points();
			moproblemset.remove(index);
			moproblem_num = moproblemset.size();
		}
	}
	void answering (int type, int index, int ans) throws Exception{
		if (type >= 2) throw new ProblemCrossBoundary();
		if (type == 0){
			if (index >= jproblem_num) throw new ProblemCrossBoundary();
			JProblem pr = jproblemset.get(index);
			getpoint -= pr.get_getpoints();
			pr.answering(ans);
			getpoint += pr.get_getpoints();
			ans_status = true;
			jproblemset.set(index, pr);
		}
		else if(type == 1) {
			if (index >= oproblem_num) throw new ProblemCrossBoundary();
			OProblem pr = oproblemset.get(index);
			getpoint -= pr.get_getpoints();
			pr.answering(ans);
			getpoint += pr.get_getpoints();
			ans_status = true;
			oproblemset.set(index, pr);
		}
	}
	void answering (int index, ArrayList<Integer> ans) throws Exception {
		if (index >= moproblem_num) throw new ProblemCrossBoundary();
		MOProblem pr = moproblemset.get(index);
		getpoint -= pr.get_getpoints();
		pr.answering(ans);
		getpoint += pr.get_getpoints();
		ans_status = true;
		moproblemset.set(index, pr);
	}
	
	void unanswering (int type, int index) throws Exception{
		if (type >= 3) throw new ProblemCrossBoundary();
		if (type == 0){
			if (index >= jproblem_num) throw new ProblemCrossBoundary();
			JProblem pr = jproblemset.get(index);
			getpoint -= pr.get_getpoints();
			pr.unanswering();
			jproblemset.set(index, pr);
		}
		else if(type == 1) {
			if (index >= oproblem_num) throw new ProblemCrossBoundary();
			OProblem pr = oproblemset.get(index);
			getpoint -= pr.get_getpoints();
			pr.unanswering();
			oproblemset.set(index, pr);
		}
		else {
			if (index >= moproblem_num) throw new ProblemCrossBoundary();
			MOProblem pr = moproblemset.get(index);
			getpoint -= pr.get_getpoints();
			pr.unanswering();
			moproblemset.set(index, pr);
		}
	}
	public void output(String Dir) throws Exception{
		IOpbs fl = new IOpbs(Dir, 0);
		fl.writeString(title);
		fl.writeString(comment);
		fl.writeInt(time);
		fl.writeInt(ans_status?1:0);
		fl.writeInt(point);
		fl.writeInt(getpoint);
		fl.writeInt(jproblem_num);
		for (JProblem te : jproblemset) {
			fl.writeJProblem(te);
		}
		fl.writeInt(oproblem_num);
		for (OProblem te : oproblemset) {
			fl.writeOProblem(te);
		}
		fl.writeInt(moproblem_num);
		for (MOProblem te : moproblemset) {
			fl.writeMOProblem(te);
		}
		
		fl.close();
	}
	public void input(String Dir) throws Exception{
		IOpbs fl = new IOpbs(Dir, 1);
		title = fl.getLine();
		comment = fl.getLine();
		time = fl.getInt();
		ans_status = fl.getInt() == 1;
		point = fl.getInt();
		getpoint = fl.getInt();
		jproblem_num = fl.getInt();
		for (int i = 0; i < jproblem_num; i++)
			jproblemset.add(fl.getJProblem());
		oproblem_num = fl.getInt();
		for (int i = 0; i < oproblem_num; i++)
			oproblemset.add(fl.getOProblem());
		moproblem_num = fl.getInt();
		for (int i = 0; i < moproblem_num; i++)
			moproblemset.add(fl.getMOProblem());
		fl.close();
	}
	public void Debug_print() {
		System.out.println("title: " + title);
		System.out.println("comment: " + comment);
		System.out.println("ans_status: " + ans_status);
		System.out.println("point: " + point);
		System.out.println("getpoint: " + getpoint);
		System.out.println("time: " + time);
		System.out.println("--------------------------------------\n");
		
	}
	public static void main(String[] args) throws Exception{
		ProblemSet pbs = new ProblemSet();
		pbs.input("D:/javatext/test.pbs");
		JProblem tp = new JProblem();
		pbs.add_problem(tp);
		ArrayList<Integer> ans = new ArrayList<Integer>();
		ans.add(0);
		pbs.answering(0, ans);
		pbs.answering(0, 0, 1);
		pbs.answering(1, 0, 1);
		pbs.unanswering(2, 0);
		pbs.output("D:/javatext/testp.pbs");
	}
}

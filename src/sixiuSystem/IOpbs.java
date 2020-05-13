package sixiuSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


//pbs文件读写
public class IOpbs {
	int type;
	ArrayList<String> list = new ArrayList<String>();
	int num;
	File file = null;
	OutputStreamWriter cout = null;
	InputStreamReader cin = null;
	IOpbs(String Dir, int type) throws Exception {
		file = new File(Dir);
		if (type == 0) {
			cout = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
			this.type = 0;
		}
		else {
			cin = new InputStreamReader(new FileInputStream(file), "utf-8");
			this.type = 1;
		}
	}
	public String getLine() throws Exception{
		if (type != 1) throw new FileIOTypeError ();
		int len = 0;
		list.clear();
		while((len = cin.read()) != '\n' && len != -1 && len != '\r') {
			list.add(String.valueOf((char)len));
		}
		if (len == '\r') cin.read();
		return String.join("", list);
	}
	public int getInt() throws Exception{
		if (type != 1) throw new FileIOTypeError ();
		num = 0;
		int len = 0;
		while((len = cin.read()) != '\n' && len != -1 && len != '\r') {
			if (len < '0' || len >'9') { System.out.println((char)len); throw new RuntimeException("文件可能损坏"); }
			num = num * 10 + len - '0';
		}
		if (len == '\r') cin.read();
		return num;
	}
	public void writeString(String text) throws Exception{
		if (type != 0) throw new FileIOTypeError ();
		cout.write(text);
		cout.write("\n");
	}
	public void writeInt(int n) throws Exception{
		if (type != 0) throw new FileIOTypeError ();
		cout.write(String.valueOf(n));
		cout.write("\n");
	}
	public void writeJProblem(JProblem te) throws Exception {
		writeInt(te.points);
		writeInt(te.ans_status?1:0);
		writeInt(te.getpoints);
		writeString(te.ptext);
		writeInt(te.rightans?1:0);
		writeInt(te.getans?1:0);
		writeString(te.comment);
	}
	public void writeOProblem(OProblem te) throws Exception {
		writeInt(te.points);
		writeInt(te.ans_status?1:0);
		writeInt(te.getpoints);
		writeString(te.ptext);
		writeInt(te.option_num);
		for (String x : te.option) {
			writeString(x);
		}
		writeInt(te.rightans);
		writeInt(te.getans);
		writeString(te.comment);
	}
	public void writeMOProblem(MOProblem te) throws Exception {
		writeInt(te.points);
		writeInt(te.ans_status?1:0);
		writeInt(te.getpoints);
		writeString(te.ptext);
		writeInt(te.option_num);
		for (String x : te.option) {
			writeString(x);
		}
		writeInt(te.rightans.size());
		for (int x : te.rightans) {
			writeInt(x);
		}
		writeInt(te.getans.size());
		for (int x : te.getans) {
			writeInt(x);
		}
		writeString(te.comment);
	}
	public JProblem getJProblem() throws Exception {
		int point = getInt();
		boolean ans_status = getInt() == 1;
		int getpoint = getInt();
		String ptext = getLine();
		boolean rightans = getInt() == 1;
		boolean getans = getInt() == 1;
		String comment = getLine();
		JProblem pr;
		try {
			 pr = new JProblem(ptext, point, rightans, comment);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("文件可能损坏");
		}
		pr.ans_status = ans_status;
		pr.getans = getans;
		pr.getpoints = getpoint;
		return pr;
	}
	public MOProblem getMOProblem() throws Exception {
		int point = getInt();
		boolean ans_status = getInt() == 1;
		int getpoint = getInt();
		String ptext = getLine();
		
		
		int option_num = getInt();
		ArrayList<String> opt = new ArrayList<String>();
		for (int i = 0; i < option_num; i++)
			opt.add(getLine());
		
		
		ArrayList<Integer> rightans = new ArrayList<Integer>();
		int rightans_num = getInt();
		for (int i = 0; i < rightans_num; i++)
			rightans.add(getInt());
		
		
		ArrayList<Integer> getans = new ArrayList<Integer>();
		int getans_num = getInt();
		for (int i = 0; i < getans_num; i++)
			getans.add(getInt());
		
		String comment = getLine();
		MOProblem pr = null;
		try {
			pr = new MOProblem(ptext, point, rightans, opt, comment);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("文件可能损坏");
		}
		pr.ans_status = ans_status;
		if (ans_status) {
			pr.answering(getans);
		}
		pr.getpoints = getpoint;
		return pr;
	}
	public OProblem getOProblem() throws Exception {
		int point = getInt();
		boolean ans_status = getInt() == 1;
		int getpoint = getInt();
		String ptext = getLine();
		int option_num = getInt();
		ArrayList<String> opt = new ArrayList<String>();
		for (int i = 0; i < option_num; i++)
			opt.add(getLine());
		int rightans = getInt();
		int getans = getInt();
		String comment = getLine();
		OProblem pr = null;
		try {
			pr = new OProblem(ptext, point, rightans, opt, comment);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("文件可能损坏");
		}
		pr.ans_status = ans_status;
		pr.getans = getans;
		pr.getpoints = getpoint;
		return pr;
	}
	
	public void close() throws Exception{
		if (type == 0) cout.close();
		else cin.close();
	}
}
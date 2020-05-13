package esystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.ObjectInputStream.GetField;
import java.net.SocketException;
import java.security.cert.CertificateNotYetValidException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import sixiuSystem.AnsweringWin;
import sixiuSystem.IssueWin;
import sixiuSystem.ProblemSet;



class Usr {
	String name;
	int point;
	public Usr(String n, int p) {
		name = n; point = p;
	}
	public String toString() {
		return name + " " + point + "\n";
	}
}
public class FtpConnect {
	static final String name = ""; /*username*/
	static final String passwd = ""; /*password*/
	static final String host = ""/*host address*/;
	static final int QueueMaxNum = 4;
	FTPClient ftp = new FTPClient();
	public static void main(String[] args) {
		
	}
	public FtpConnect() throws SocketException, IOException {
		ftp.setConnectTimeout(10 * 1000);
		ftp.connect(host);
		ftp.login(name, passwd);
		if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
			throw new SocketException("无法连接到服务器");
		}
	}
	public void DownLoadIndex() throws IOException {
		DownLoadFile("WorkSpace/Examine/index.txt", ".index");
	}
	public void DownLoadSet(int index) throws IOException {
		DownLoadFile("WorkSpace/Examine/" + index + ".pbs", "set.pbs");
	}
	public void UpLoadSet() throws IOException, RuntimeException {
		DownLoadFile("WorkSpace/Examine/index.txt", ".index");
		Scanner cin = new Scanner(new File("DownLoad/.index"));
		int num = cin.nextInt();
		int que = cin.nextInt();
		cin.close();
		if (que >= QueueMaxNum) throw new RuntimeException("当前服务器审核队列忙,请过你后再试");
		if (que >= QueueMaxNum) return;
		File fp = new File("UpLoad/set.pbs");
		File indexf = new File("DownLoad/.index");
		OutputStreamWriter cout = new OutputStreamWriter(new FileOutputStream(indexf), "utf-8");
		cout.write(String.valueOf(num+1) + "\n" + String.valueOf(que+1));
		cout.close();
		UpLoadFile("WorkSpace/Examine", "index.txt", indexf);
		UpLoadFile("WorkSpace/Examine", String.valueOf(num) +".pbs", fp);
	}
	public int getQueueNum() throws IOException {
		DownLoadFile("WorkSpace/Examine/index.txt", ".index");
		Scanner cin = new Scanner(new File("DownLoad/.index"));
		int num = cin.nextInt();
		int que = cin.nextInt();
		cin.close();
		DownLoadFile("WorkSpace/Examine/nullnum", ".nullnum");
		cin = new Scanner(new File("DownLoad/.nullnum"));
		int nullnum = cin.nextInt();
		cin.close();
		return que - nullnum;
	}
	public void clearQueue() throws IOException {
		DownLoadFile("WorkSpace/Examine/index.txt", ".index");
		Scanner cin = new Scanner(new File("DownLoad/.index"));
		DownLoadFile("WorkSpace/Examine/nullnum", ".nullnum");
		int num = cin.nextInt();
		int que = cin.nextInt();
		cin.close();
		cin = new Scanner(new File("DownLoad/.nullnum"));
		int nullnum = cin.nextInt();
		cin.close();
		for (int i = 1; i <= num-1; i++) {
			ftp.changeWorkingDirectory("/home/ftpuser/WorkSpace/Examine");
			ftp.deleteFile(i + ".pbs");
		}
		File indexf = new File("DownLoad/.index");
		OutputStreamWriter cout = new OutputStreamWriter(new FileOutputStream(indexf), "utf-8");
		cout.write(String.valueOf(1) + "\n" + String.valueOf(nullnum));
		cout.close();
		UpLoadFile("WorkSpace/Examine", "index.txt", indexf);
	}
	public void OpenSet() throws Exception {
		ProblemSet set = new ProblemSet();
		set.input("DownLoad/set.pbs");
		new IssueWin(set, new JFrame("ExamineMode")).setVisible(true);
	}
	public void CheckSet() throws Exception {
		new AnsweringWin("DownLoad/set.pbs", -1, new JFrame("ExamineMode"), 0).setVisible(true);
	}
	public void AddProblemSet() throws IOException {
		ProblemSet set = new ProblemSet();
		try {
			set.input("DownLoad/set.pbs");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("文件读取失败");
		}
		try {
			ftp.changeWorkingDirectory("/home/ftpuser/WorkSpace/ProblemSet");
			DownLoadFile("WorkSpace/ProblemSet/index.txt", ".index");
		} catch (IOException e) {
			e.printStackTrace();
		}
		InputStreamReader cin = new InputStreamReader(new FileInputStream(new File("Download/.index")), "utf-8");
		int n = getInt(cin);
		ArrayList<String> Astring = new ArrayList<String>();
		Astring.clear();
		for (int i = 0; i < n; i++) {
			Astring.add(getLine(cin));
		}
		ftp.changeWorkingDirectory("/home/ftpuser/WorkSpace/ProblemSet");
		ftp.makeDirectory(String.valueOf(n+1));
		ftp.changeWorkingDirectory(String.valueOf(n+1));
		UpLoadFile("WorkSpace/ProblemSet/" + (n+1), "set.pbs", new File("DownLoad/set.pbs"));
		File _rank = new File("UpLoad/.rank");
		OutputStreamWriter cout = new OutputStreamWriter(new FileOutputStream(_rank), "utf-8");
		cout.write("0\n");
		cout.close();
		UpLoadFile("WorkSpace/ProblemSet/" + (n+1), "rank.txt", _rank);
		File _index = new File("UpLoad/.index");
		cout = new OutputStreamWriter(new FileOutputStream(_index), "utf-8");
		cout.write(String.valueOf(n+1));
		cout.write("\n");
		for (String x : Astring) {
			cout.write(x + "\n");
		}
		int num = set.get_jproblem_num() + set.get_oproblem_num() + set.get_moproblem_num();
		cout.write(set.get_title() + "," + num + "," + set.get_point() + "\n");
		cout.close();
		UpLoadFile("WorkSpace/ProblemSet/", "index.txt", _index);
	}
	public int getNullNum() throws IOException {
		DownLoadFile("WorkSpace/Examine/nullnum", ".nullnum");
		Scanner cin = new Scanner(new File("DownLoad/.nullnum"));
		int nullnum = cin.nextInt();
		cin.close();
		return nullnum;
	}
	public void setNullNum (int nullnum) throws IOException {
		File num = new File("UpLoad/.nullnum");
		UpLoadFile("WorkSpace/Examine", "nullnum", num);
		File _index = new File("UpLoad/.index");
		OutputStreamWriter cout = new OutputStreamWriter(new FileOutputStream(_index), "utf-8");
		cout.write(String.valueOf(1) + "\n" + String.valueOf(nullnum));
		cout.close();
		UpLoadFile("WorkSpace/Examine", "index.txt", _index);
	}
	public void List () throws IOException {
		DownLoadFile("WorkSpace/Examine/index.txt", ".index");
		Scanner cin = new Scanner(new File("DownLoad/.index"));
		DownLoadFile("WorkSpace/Examine/nullnum", ".nullnum");
		int num = cin.nextInt();
		int que = cin.nextInt();
		cin.close();
		cin = new Scanner(new File("DownLoad/.nullnum"));
		int nullnum = cin.nextInt();
		cin.close();
		System.out.println("队列数量 :" + (num-1) );
		for (int i = 1; i <= num-1; i++) {
			ftp.changeWorkingDirectory("/home/ftpuser/WorkSpace/Examine");
			DownLoadFile("WorkSpace/Examine/" + i + ".pbs", "set.pbs");
			System.out.print("No." + i + " ");
			ProblemSet set = new ProblemSet();
			try {
				set.input("DownLoad/set.pbs");
			}
			catch (Exception e) {
				System.out.println("<提示信息>文件损坏");
				continue;
			}
			int num1 = set.get_jproblem_num() + set.get_moproblem_num() + set.get_oproblem_num();
			System.out.println(set.get_title() + " 分数" + set.get_point() + " 题目数" + num1);
		}
	}
	public void clear () {
		File fp = new File("DownLoad/.index");
		fp.delete();
		fp = new File("DownLoad/.rank");
		fp.delete();
		fp = new File("DownLoad/.user");
		fp.delete();
		fp = new File("DownLoad/set.pbs");
		fp.delete();
		fp = new File("UpLoad/set.pbs");
		fp.delete();
		fp = new File("UpLoad/.index");
		fp.delete();
		fp = new File("UpLoad/.user");
		fp.delete();
		fp = new File("UpLoad/.rank");
		fp.delete();
	}
	private void DownLoadFile(String Dir, String FileName) throws IOException {
		FileOutputStream fp = new FileOutputStream("DownLoad/" + FileName);
		ftp.changeWorkingDirectory("/home/ftpuser");
		ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
		ftp.retrieveFile(Dir, fp);
		System.out.println("DownLoad ReplyCode : " + ftp.getReplyCode());
		fp.close();
	}
	private void UpLoadFile(String Dir, String FileName, File fp) throws IOException {
		FileInputStream fpt = new FileInputStream(fp);
		ftp.changeWorkingDirectory("/home/ftpuser");
		ftp.changeWorkingDirectory(Dir);
		ftp.setBufferSize(1024);
		ftp.setControlEncoding("Utf-8");
		ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
		ftp.storeFile(FileName, fpt);
		fpt.close();
	}
	private int getInt(InputStreamReader cin) throws IOException {
		int num = 0;
		int len = 0;
		while((len = cin.read()) != '\n' && len != -1 && len != '\r') {
			if (len < '0' || len >'9') { throw new RuntimeException("文件可能损坏"); }
			num = num * 10 + len - '0';
		}
		if (len == '\r') cin.read();
		return num;
	}
	private String getLine(InputStreamReader cin) throws IOException {
		int len = 0;
		ArrayList<String> list = new ArrayList<String>();
		list.clear();
		while((len = cin.read()) != '\n' && len != -1 && len != '\r') {
			list.add(String.valueOf((char)len));
		}
		if (len == '\r') cin.read();
		return String.join("", list);
	}
}

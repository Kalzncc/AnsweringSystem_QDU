package sixiuSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.SocketException;
import java.security.cert.CertificateNotYetValidException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;


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
	static final String host = ""; /*host address*/
	static final int QueueMaxNum = 0;
	FTPClient ftp = new FTPClient();
	public static void main(String[] args) {
		
	}
	public FtpConnect() throws SocketException, IOException {
		//ftp.setConnectTimeout(10 * 1000);
		ftp.connect(host);
		ftp.login(name, passwd);
		if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
			throw new SocketException("无法连接到服务器");
		}
	}
	public void DownLoadIndex() throws IOException {
		DownLoadFile("WorkSpace/ProblemSet/index.txt", ".index");
	}
	public void DownLoadSet(int index) throws IOException {
		DownLoadFile("WorkSpace/ProblemSet/" + index + "/set.pbs", "set.pbs");
	}
	public void DownLoadRank(int index) throws IOException {
		DownLoadFile("WorkSpace/ProblemSet/" + index + "/rank.txt", ".rank");
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
	public void AddRank(int index, String Name, int Piont) throws IOException {
		DownLoadFile("WorkSpace/ProblemSet/" + index + "/rank.txt", ".rank");
		Scanner cin = new Scanner(new File("DownLoad/.rank"));
		int n = cin.nextInt();
		ArrayList<Usr> us = new ArrayList<Usr>();
		for (int i = 0; i < n; i++) {
			us.add(new Usr(cin.next(), cin.nextInt()));
		}
		cin.close();
		us.add(new Usr(Name, Piont));
		us.sort(new Comparator<Usr>() {
			@Override
			public int compare(Usr o1, Usr o2) {
				if (o1.point < o2.point) return 1;
				else if (o1.point > o2.point) return -1;
				else
					return o1.name.compareTo(o2.name);
			}
		});
		File rankf = new File("UpLoad/.rank");
		OutputStreamWriter cout = new OutputStreamWriter(new FileOutputStream(rankf), "utf-8");
		cout.write(String.valueOf(us.size()) + "\n");
		for (Usr x : us) {
			cout.write(x.toString());
		}
		cout.close();
		UpLoadFile("WorkSpace/ProblemSet/" + index + "/", "rank.txt", rankf);
	}
	public void Login( String name, String passwd) throws RuntimeException, IOException{
		DownLoadFile("WorkSpace/User/" + name + ".txt", ".user");
		Scanner cin = new Scanner(new File("DownLoad/.user"));
		String ps = cin.next();
		cin.close();
		boolean flag = false;
		if (!ps.equals(passwd)) { 
			flag = true;
			throw new RuntimeException("密码或用户名错误!");
		}
		if (flag) return;
	}
	public void Signup(String name, String passwd) throws RuntimeException, IOException{
		DownLoadFile("WorkSpace/User/index.txt", ".index");
		Scanner cin = new Scanner(new File("DownLoad/.index"));
		int n = cin.nextInt();
		boolean flag = false;
		ArrayList<String> index = new ArrayList<String>();
		for (int i = 0; i < n; i++) {
			
			String tp = cin.next();
			index.add(tp);
			if (name.equals(tp)) {
				flag = true;
				break;
			}
		}
		cin.close();
		if (flag) {
			throw new RuntimeException("用户名已被注册");
		}
		if (flag) return;
		File indexf = new File("Upload/.index");
		OutputStreamWriter cout = new OutputStreamWriter(new FileOutputStream(indexf), "utf-8");
		index.add(name);
		cout.write(index.size() + "\n");
		for (String x : index) {
			cout.write(x + "\n");
		}
		cout.close();
		UpLoadFile("WorkSpace/User", "index.txt", indexf);
		File user = new File("UpLoad/.user");
		cout = new OutputStreamWriter(new FileOutputStream(user), "utf-8");
		cout.write(passwd);
		cout.close();
		UpLoadFile("WorkSpace/User", name + ".txt", user);
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
		System.out.println(ftp.getReplyCode());
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
	
}

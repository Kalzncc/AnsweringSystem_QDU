package sixiuSystem;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;


public class FtpProblemSetWin extends JFrame{
	FtpConnect con;
	String name;
	JPanel mp = new JPanel(new BorderLayout());
	JList<String> list = new JList<String>();
	DefaultListModel<String> listd = new DefaultListModel<String>();
	int ProblemSetNum = 0;
	FtpLoginWin own;
	class Com {
		String name;
		int point;
		int num;
		public Com(String n, int p, int m) {
			name = n; point = p; num = m;
		}
		public String toString() {
			return name + "   题目数:" + String.valueOf(point) + "   分数:" + String.valueOf(num);
		}
	}
	ArrayList<Com> set = new ArrayList<Com>();
	public static void main(String[] args) {
		new FtpProblemSetWin("", null);
	}
	public FtpProblemSetWin(String name, FtpConnect con) {
		super("在线题目集挑战"+name);
		this.name = name;
		this.con = con;
		
		LoadingWin load = new LoadingWin(this);
		load.setVisible(true);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(800, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel Welcome = new JLabel("欢迎你:" + name);
		Welcome.setFont(new Font(Font.SERIF, Font.BOLD, 18));
		top.add(Welcome);
		JButton Flush = new JButton("刷新");
		JButton Submit = new JButton("上传题目集");
		top.add(Flush);
		top.add(Submit);
		list.setFont(new Font(Font.SERIF, Font.BOLD, 21));
		JScrollPane ListS = new JScrollPane(list);
		ListS.setBorder(BorderFactory.createTitledBorder("题目集列表"));
		mp.add(top, BorderLayout.NORTH);
		mp.add(ListS, BorderLayout.CENTER);
		JButton Start = new JButton("开始答题");
		JButton Rank = new JButton("查看排名");
		JPanel bet = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		bet.add(Start); bet.add(Rank);
		mp.add(bet, BorderLayout.SOUTH);
		add(mp);
		setVisible(true);
		FtpProblemSetWin thiso = this;
		try {
			flush();
		}
		catch (Exception e) {
			load.setVisible(false);
			JOptionPane.showConfirmDialog(thiso, "加载失败,请刷新" , "错误", JOptionPane.CLOSED_OPTION);
		}
		addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
			}
			@Override
			public void windowIconified(WindowEvent e) {
			}
			@Override
			public void windowDeiconified(WindowEvent e) {
			}
			@Override
			public void windowDeactivated(WindowEvent e) {
			}
			@Override
			public void windowClosing(WindowEvent e) {
				int i=JOptionPane.showConfirmDialog(thiso, "确定要退出线上模式吗?", "退出", JOptionPane.YES_NO_OPTION);
				 if(i==JOptionPane.YES_OPTION){
					 dispose();
				 }
			}
			@Override
			public void windowClosed(WindowEvent e) {
			}	
			@Override
			public void windowActivated(WindowEvent e) {	
			}
		});
		load.setVisible(false);
		Flush.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LoadingWin load = new LoadingWin(thiso);
				load.setVisible(true);
				try {
					flush();
				}
				catch (Exception e1) {
					load.setVisible(false);
					JOptionPane.showConfirmDialog(thiso, "加载失败,请刷新" , "错误", JOptionPane.CLOSED_OPTION);
				}
				load.setVisible(false);
			}
		});
		Start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				if (index == -1) return;
				index++;
				if (index > ProblemSetNum) return;
				AnsweringWin tp = null;
				try {
					con.DownLoadSet(index);
					tp = new AnsweringWin("DownLoad/set.pbs", 1, thiso, index);
					con.clear();
					tp.setVisible(true);
				}
				catch (Exception e1) {
					JOptionPane.showConfirmDialog(thiso, "无法从服务器读取文件" , "错误", JOptionPane.CLOSED_OPTION);
					return;
				}
			}
		});
		Rank.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				if (index == -1) return;
				index++;
				if (index > ProblemSetNum) return;
				try {
					new RankWin(thiso, con, index).setVisible(true);;
					con.clear();
				}
				catch (Exception e1) {
					e1.printStackTrace();
					con.clear();
					JOptionPane.showConfirmDialog(thiso, "无法从服务器读取文件" , "错误", JOptionPane.CLOSED_OPTION);
				}
				
			}
		});
		Submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("题库文件(*.pbs)", "pbs");
				jfc.setFileFilter(filter);
				int res = jfc.showDialog(new JLabel(), "选择");
				if (res == JFileChooser.APPROVE_OPTION) {
					File file = jfc.getSelectedFile();
					ProblemSet set = new ProblemSet();
					try {
						set.input(file.getAbsolutePath());
						set.output("UpLoad/set.pbs");
					}
					catch (Exception e1) {
						JOptionPane.showConfirmDialog(thiso, "无法上传文件:"+e1.getMessage() , "错误", JOptionPane.CLOSED_OPTION);
						return;
					}
					try {
						con.UpLoadSet();
						con.clear();
					}
					catch (Exception e1) {
						con.clear();
						JOptionPane.showConfirmDialog(thiso, "无法上传文件:无法上传至服务器,可能是审核区已满,请过后重试!" , "错误", JOptionPane.CLOSED_OPTION);
						return;
					}
					JOptionPane.showConfirmDialog(thiso, "题目集上传完成!请耐心等待审核通过" , "提示", JOptionPane.CLOSED_OPTION);
				}
			}
		});
	}
	public void flush() throws IOException {
		con.DownLoadIndex();
		InputStreamReader cin = new InputStreamReader(new FileInputStream(new File("Download/.index")), "utf-8");
		int n = getInt(cin);
		set.clear();
		for (int i = 0; i < n; i++) {
			String g = getLine(cin);
			String[] p = g.split("\\,");
			set.add(new Com(p[0], Integer.parseInt(p[1]), Integer.parseInt(p[2])  ));
		}
		cin.close();
		listd.clear();
		int cnt = 1;
		for (Com x : set) {
			listd.addElement("No." + cnt + "  " + x.toString());
			cnt++;
		}
		ProblemSetNum = cnt - 1;
		list.setModel(listd);
		con.clear();
	}
	public void Addrank(int index, int point) {
		try {
			con.AddRank(index, name, point);
			con.clear();
		}
		catch (Exception e) {
			JOptionPane.showConfirmDialog(this, "无法上传至服务器" , "错误", JOptionPane.CLOSED_OPTION);
			return;
		}
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

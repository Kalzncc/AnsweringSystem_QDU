package sixiuSystem;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.PopupMenu;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.security.auth.x500.X500Principal;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.WindowConstants;

public class AnsweringWin extends JFrame{
	int mode = 0;
	int index = 0;
	ProblemSet pbs = new ProblemSet();
	JTextArea ProblemArea = new JTextArea();
	Box ProblemOptionArea = Box.createVerticalBox();
	JScrollPane ProblemStatus = new JScrollPane();
	ArrayList<ProblemCom> ProblemB = new ArrayList<ProblemCom>();
	JLabel ProblemSetTitle;
	JPanel TitleAreaBor = new JPanel(new BorderLayout());
	JTextArea ProblemComment;
	JScrollPane ProblemCommentS;
	JScrollPane ProblemSetTitleS;
	JButton ProblemSetSubmit;
	JScrollPane ProTin1;
	String tp;
	JFrame own;
	int Sumpoint;
	public static void main(String[] args) throws Exception{
		try {
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		}
		catch (Exception e) {
		}
		new AnsweringWin("D:/javatext/textp.pbs", 0, null, 0).setVisible(true);
	}
	public AnsweringWin(String Dir, int mode, JFrame own, int index) throws Exception{
		pbs.input(Dir);
		this.mode = mode;
		this.own = own;
		this.index = index;
		setFocusableWindowState(true);
		String title = "思政答题姬";
		if (mode == -1) title += "-ExamineMode";
		setTitle("思政答题姬");
		setSize(1300, 700);
		setLocationRelativeTo(own);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		AnsweringWin thiso = this;
		addWindowListener( new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {}
			@Override
			public void windowIconified(WindowEvent e) {}
			@Override
			public void windowDeiconified(WindowEvent e) {}			
			@Override
			public void windowDeactivated(WindowEvent e) {}
			@Override
			public void windowClosing(WindowEvent e) {
				 int i=JOptionPane.showConfirmDialog(thiso, "确定要退出答题吗?你没有评测的作答将会作废.", "退出答题", JOptionPane.YES_NO_OPTION);
				 if(i==JOptionPane.YES_OPTION){
					 dispose();
				 }
			}
			@Override
			public void windowClosed(WindowEvent e) {}
			@Override
			public void windowActivated(WindowEvent e) {}
		});
		
		
		JSplitPane fleft = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		fleft.setDividerLocation(200);
		// 计时器组件
		ProblemSetTitle = new JLabel("《" + pbs.get_title()+"》");
		ProblemSetTitle.setFont(new Font(Font.SERIF, Font.BOLD, 17));
		ProblemSetTitleS = new JScrollPane(ProblemSetTitle, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		TitleAreaBor.add(ProblemSetTitleS, BorderLayout.NORTH);
		ProblemComment = new JTextArea();
		tp = "";
		tp = "总分:" + pbs.get_point() + "\n";
		tp += "说明:";
		tp += pbs.get_comment();
		
		ProblemComment.setText(tp);
		ProblemComment.setLineWrap(true);
		ProblemComment.setFont(new Font(Font.SERIF, Font.BOLD, 16));
		ProblemComment.setEditable(false);
		ProblemCommentS = new JScrollPane(ProblemComment,  ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		TitleAreaBor.add(ProblemCommentS, BorderLayout.CENTER);
		ProblemSetSubmit = new JButton("提交答案");
		TitleAreaBor.add(ProblemSetSubmit, BorderLayout.SOUTH);
		
		
		
		
		
		fleft.setTopComponent(TitleAreaBor);
		
		//题目概况组件
		SpringLayout ProblemStatusLayout = new SpringLayout();
		JPanel ProblemStatusBox = new JPanel(ProblemStatusLayout);
		
		
		Spring a = AddProblem(0, Spring.constant(5), ProblemStatusBox, ProblemStatusLayout);
		a = AddProblem(1, a, ProblemStatusBox, ProblemStatusLayout);
		a = AddProblem(2, a, ProblemStatusBox, ProblemStatusLayout);
		
		ProblemStatusLayout.getConstraints(ProblemStatusBox).setConstraint(SpringLayout.SOUTH, a);
		
		
		
		

		ProblemStatus = new JScrollPane(ProblemStatusBox, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		fleft.setBottomComponent(ProblemStatus);
		
		ProblemArea.setLineWrap(true);
		ProblemArea.setEditable(false);
		ProblemArea.setFont(new Font(Font.SERIF, Font.BOLD, 23));
		
		JSplitPane fl = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		fl.setDividerLocation(277);
		fl.setEnabled(false);
		
		JSplitPane fright = new JSplitPane(JSplitPane.VERTICAL_SPLIT); 
		JScrollPane ProTin =  new JScrollPane(ProblemArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		ProTin1 = new JScrollPane(ProblemOptionArea);
		fright.setDividerLocation(300);
		fright.setTopComponent(ProTin);
		fright.setBottomComponent(ProTin1);
		fl.setRightComponent(fright);
		fl.setLeftComponent(fleft);
		add(fl);
		
		
		ProblemSetSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i=JOptionPane.showConfirmDialog(thiso, "确定要提交的答案吗?提交后将无法修改.", "提交答案", JOptionPane.YES_NO_OPTION);
				 if(i==JOptionPane.YES_OPTION){
					 try {
						 SubmitAns();
					 }
					 catch (Exception e1) {
						e1.printStackTrace();
					}
					tp = "您的得分:" +  Sumpoint + "/" + pbs.get_point() + "\n";
					tp += "说明:";
					tp += pbs.get_comment();
					ProblemComment.setText(tp);
					if (mode == 1) {
						int j=JOptionPane.showConfirmDialog(thiso, "线上模式确认:您是否要上传本次成绩到服务器?你的名字可能会出现在榜单上!", "提交答案", JOptionPane.YES_NO_OPTION);
						if (j == JOptionPane.YES_OPTION) {
							FtpProblemSetWin ownthis = (FtpProblemSetWin) own;
							ownthis.Addrank(index, Sumpoint);
						}
					}
				 }
			}
		});
		
	}
	private Spring AddProblem(int type, Spring Start, JPanel To, SpringLayout lay) throws Exception {
		JLabel JProblemL;
		if (type == 0)
			JProblemL = new JLabel("判断题");
		else if (type == 1)
			JProblemL = new JLabel("单选题");
		else
			JProblemL = new JLabel("多选题");
		JProblemL.setFont(new Font(Font.SERIF, Font.BOLD, 15));
		To.add(JProblemL);
		SpringLayout.Constraints JProblemLC = lay.getConstraints(JProblemL);
		ArrayList<ProblemCom> JProblemB = new ArrayList<ProblemCom>();
		if (type == 0)
			for (int i = 0; i < pbs.jproblemset.size(); i++) {
				JProblemB.add(new ProblemCom(String.valueOf(i+1), pbs.jproblemset.get(i), lay, this));
			}
		else if (type == 1) 
			for (int i = 0; i < pbs.oproblemset.size(); i++) {
				JProblemB.add(new ProblemCom(String.valueOf(i+1), pbs.oproblemset.get(i), lay, this));
			}
		else 
			for (int i = 0; i < pbs.moproblemset.size(); i++) {
				JProblemB.add(new ProblemCom(String.valueOf(i+1), pbs.moproblemset.get(i), lay, this));
			}
		
		for (ProblemCom x : JProblemB) {
			To.add(x.get_ProblemButton());
		}
		for (ProblemCom x : JProblemB) {
			ProblemB.add(x);
		} 
		JProblemLC.setY(Start);
		JProblemLC.setX(Spring.constant(5));
		Spring Down = JProblemLC.getConstraint(SpringLayout.SOUTH);
		for (int i = 0; i < JProblemB.size(); i++) {
			if (i % 7 == 0) {
				if (i != 0) Down = Spring.sum(
					Spring.constant(3),
					lay.getConstraint(SpringLayout.SOUTH, JProblemB.get(i-1).get_ProblemButton())
				);
				lay.getConstraints(JProblemB.get(i).get_ProblemButton()).setX(Spring.constant(5));
				lay.getConstraints(JProblemB.get(i).get_ProblemButton()).setY(Down);
			}
			else {
				lay.getConstraints(JProblemB.get(i).get_ProblemButton()).setX(
					Spring.sum(
						Spring.constant(3),
						lay.getConstraint(SpringLayout.EAST, JProblemB.get(i-1).get_ProblemButton())
					)
				);
				lay.getConstraints(JProblemB.get(i).get_ProblemButton()).setY(Down);
			}
		}
		if (JProblemB.size() != 0) 
			Down =Spring.sum(
				Spring.constant(3),
				lay.getConstraint(SpringLayout.SOUTH, JProblemB.get(JProblemB.size() - 1).get_ProblemButton())
			);
		return Down;
	}
	private void SubmitAns() throws Exception {
		//System.out.println("Submit Answers");
		ProblemSetSubmit.setEnabled(false);
		for (ProblemCom x : ProblemB) {
			x.setLock();
			System.out.println(x.thiso.get_points() + " " + x.thiso.get_getpoints());
			Sumpoint += x.thiso.get_getpoints();
		}
	}
}

package sixiuSystem;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.PrimitiveIterator.OfDouble;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

public class IssueWin extends JFrame {
	ProblemSet set = new ProblemSet();
	JPanel IssueBor = new JPanel(new BorderLayout());
	Box ProblemInfo = Box.createVerticalBox();
	Box ProblemDetail = Box.createHorizontalBox();
	JPanel ProblemTitle = new JPanel();
	SpringLayout ProblemCommentS = new SpringLayout();
	JPanel ProblemCommentP = new JPanel(ProblemCommentS);
	JPanel[] ProblemDetailP = new JPanel[3];
	JList[] ProblemList = new JList[3];
	JButton[] ProblemRomve = new JButton[3];
	JButton[] ProblemAdd = new JButton[3];
	JButton[] ProblemEdt = new JButton[3];
	DefaultListModel[] dlm = new DefaultListModel[3];
	
	
	JTextField JtitleT;
	JTextArea Comment;
	JButton SubmitB;
	private static final String[] tp = {"判断题", "单选题", "多选题"};
	private static final String title = "思政出题姬";
	public static void main(String[] args) {
		
	}
	public IssueWin(ProblemSet set, JFrame own) {
		super(title);
		if  (own.getTitle() == "ExamineMode")
			setTitle(title + "-ExamineMode");
		this.set = set;
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(430, 480);
		setResizable(false);
		setLocationRelativeTo(own);
		for (int i = 0; i < 3; i++) {
			ProblemList[i] = new JList<String>();
			dlm[i] = new DefaultListModel<String>();
		}
		// Info
			//Title
		ProblemTitle = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel JtitleL = new JLabel("题目集标题");
		JtitleT = new JTextField(34);
		ProblemTitle.add(JtitleL); ProblemTitle.add(JtitleT);
	
		ProblemInfo.add(ProblemTitle);
		
			//Comment
		Comment = new JTextArea();
		JLabel CommentL = new JLabel("题目集说明");
		ProblemCommentP.add(CommentL);
		SpringLayout.Constraints CommentLCon = ProblemCommentS.getConstraints(CommentL);
		CommentLCon.setX(Spring.constant(5));
		CommentLCon.setY(Spring.constant(5));
		SpringLayout.Constraints ProblemCommetPCon = ProblemCommentS.getConstraints(ProblemCommentP);
		ProblemCommetPCon.setConstraint(SpringLayout.SOUTH, Spring.constant(80));
		Comment.setLineWrap(true);
		JScrollPane CommentSc = new JScrollPane(Comment);
		ProblemCommentP.add(CommentSc);
		SpringLayout.Constraints CommentScCon = ProblemCommentS.getConstraints(CommentSc);
		CommentScCon.setX(Spring.constant(84));
		CommentScCon.setY(Spring.constant(5));
		CommentScCon.setWidth(Spring.constant(290));
		CommentScCon.setHeight(Spring.constant(80));
		ProblemInfo.add(ProblemCommentP);
		IssueBor.add(ProblemInfo, BorderLayout.NORTH);
		Spring start = Spring.constant(10);
		for (int i = 0; i < 3; i++)
		{
			initList(start, i);
		}
		//Detail
		
		
		
		IssueBor.add(ProblemDetail, BorderLayout.CENTER);
		JPanel SubmitBP = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		SubmitB = new JButton("导出");
		SubmitB.setPreferredSize(new Dimension(100, 20));
		SubmitBP.add(SubmitB);
		IssueBor.add(SubmitBP, BorderLayout.SOUTH);
		add(IssueBor);
		
		IssueWin thiso = this;
		
		addWindowListener(new WindowListener() {
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
				int i=JOptionPane.showConfirmDialog(thiso, "确定要退出编辑吗?您编辑的题目不会被保存", "退出编辑", JOptionPane.YES_NO_OPTION);
				 if(i==JOptionPane.YES_OPTION){
					 dispose();
				 }
			}
			@Override
			public void windowClosed(WindowEvent e) {}
			@Override
			public void windowActivated(WindowEvent e) {}
		});
		
		for (int i = 0; i <  3; i++)
		{
			int k = i;
			ProblemRomve[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int index = ProblemList[k].getSelectedIndex();
					try {
						set.remove(k, index);
					} catch (Exception e1) {
						return;
					}
					dlm[k].remove(index);
					ProblemList[k].setModel(dlm[k]);
					ProblemList[k].setSelectedIndex(index);
				}
			});
			ProblemAdd[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (k == 0)
						new ProblemEditWin().Open(new JProblem(), thiso);
					if (k == 1)
						new ProblemEditWin().Open(new OProblem(), thiso);
					if (k == 2)
						new ProblemEditWin().Open(new MOProblem(), thiso);
				}
			});
			
			ProblemEdt[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e){
					int index = ProblemList[k].getSelectedIndex();
					if (k == 0) {
						JProblem tp;
						try {
							tp = set.get_jproblem(index);
						} 
						catch (Exception e1) {
							return;
						}
						new ProblemEditWin().Open(tp, thiso);
					}
					else if (k == 1) {
						OProblem tp;
						try {
							tp = set.get_oproblem(index);
						} 
						catch (Exception e1) {
							return;
						}
						new ProblemEditWin().Open(tp, thiso);
					}
					else {
						MOProblem tp;
						try {
							tp = set.get_moproblem(index);
						} 
						catch (Exception e1) {
							return;
						}
						new ProblemEditWin().Open(tp, thiso);
					}
				}
			});
		}
		SubmitB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (JtitleT.getText().equals("")) {
					JOptionPane.showConfirmDialog(thiso, "题目集标题不可以为空!.", "错误", JOptionPane.CLOSED_OPTION);
					return;
				}
				set.set_title(JtitleT.getText().replace("\n", ""));
				set.set_comment(Comment.getText().replace("\n", ""));
				if (own.getTitle() == "ExamineMode") {
					try {
						set.output("DownLoad/set.pbs");
					} catch (Exception e1) {
						e1.printStackTrace();
						JOptionPane.showConfirmDialog(thiso, "文件保存失败,请检查文件名是否合法", "错误", JOptionPane.CLOSED_OPTION);
						return;
					}
					return;
				}
				JFileChooser jfc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("题库文件(*.pbs)", "pbs");
				jfc.setFileFilter(filter);
				jfc.setSelectedFile(new File(JtitleT.getText() + ".pbs"));
				int res = jfc.showSaveDialog(thiso);
				if (res == JFileChooser.APPROVE_OPTION) {
					File file = jfc.getSelectedFile();
					
					try {
						String path = file.getAbsolutePath();
						if (path.length() < 4 || !path.subSequence(path.length() - 4, path.length()).equals(".pbs")) {
							path += ".pbs";
						}
						
						set.output(path);
					} catch (Exception e1) {
						e1.printStackTrace();
						JOptionPane.showConfirmDialog(thiso, "文件保存失败,请检查文件名是否合法", "错误", JOptionPane.CLOSED_OPTION);
						return;
					}
				}
			}
		});
		Openset();
	}
	private Spring initList(Spring sta, int n) {
		SpringLayout JS = new SpringLayout();
		
		
		
		ProblemDetailP[n] = new JPanel(JS);
		ProblemList[n] = new JList<String>();
		JScrollPane listS = new JScrollPane(ProblemList[n], JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		ProblemDetailP[n].add(listS);
		
		
		SpringLayout.Constraints con = JS.getConstraints(listS);
		con.setX(Spring.constant(4)); con.setY(Spring.constant(5));
		con.setWidth(Spring.constant(110));
		con.setHeight(Spring.constant(200));
		
		
		ProblemAdd[n] = new JButton("+");
		ProblemDetailP[n].add(ProblemAdd[n]);
		SpringLayout.Constraints addbCon = JS.getConstraints(ProblemAdd[n]);
		initLittleButton(ProblemAdd[n]);
		addbCon.setX(Spring.constant(4));
		addbCon.setY(con.getConstraint(SpringLayout.SOUTH));
		addbCon.setWidth(Spring.constant(20));
		addbCon.setHeight(Spring.constant(20));
		
		
		ProblemRomve[n] = new JButton("-");
		ProblemDetailP[n].add(ProblemRomve[n]);
		SpringLayout.Constraints rovbCon = JS.getConstraints(ProblemRomve[n]);
		initLittleButton(ProblemRomve[n]);
		rovbCon.setX(Spring.sum(Spring.constant(25), addbCon.getConstraint(SpringLayout.EAST)) );
		rovbCon.setY(con.getConstraint(SpringLayout.SOUTH));
		rovbCon.setWidth(Spring.constant(20));
		rovbCon.setHeight(Spring.constant(20));
		
		ProblemEdt[n] = new JButton("..");
		ProblemDetailP[n].add(ProblemEdt[n]);
		SpringLayout.Constraints edtbCon = JS.getConstraints(ProblemEdt[n]);
		initLittleButton(ProblemEdt[n]);
		edtbCon.setX(Spring.sum(Spring.constant(25), rovbCon.getConstraint(SpringLayout.EAST)));
		edtbCon.setY(con.getConstraint(SpringLayout.SOUTH));
		edtbCon.setWidth(Spring.constant(20));
		edtbCon.setHeight(Spring.constant(20));
		
		SpringLayout.Constraints Con = JS.getConstraints(ProblemDetailP[n]);
		Con.setConstraint(SpringLayout.SOUTH, addbCon.getConstraint(SpringLayout.SOUTH));
		Con.setConstraint(SpringLayout.EAST, con.getConstraint(SpringLayout.EAST));
		Con.setX(Spring.constant(5));
		Con.setY(Spring.constant(5));
		ProblemDetailP[n].setBorder(BorderFactory.createTitledBorder(tp[n]));
		
		ProblemDetail.add(ProblemDetailP[n]);
		return Spring.sum(Spring.constant(5), con.getConstraint(SpringLayout.EAST));
	}
	private void initLittleButton(JButton t) {
		t.setMargin(new Insets(1, 1, 1, 1));
	}
	private void Openset() {
		JtitleT.setText(set.get_title());
		Comment.setText(set.get_comment());
		for (JProblem x : set.jproblemset) {
			dlm[0].addElement(x.get_text());
		}
		for (OProblem x : set.oproblemset) {
			dlm[1].addElement(x.get_text());
		}
		for (MOProblem x : set.moproblemset) {
			dlm[2].addElement(x.get_text());
		}
		for (int i = 0; i < 3; i++)
			ProblemList[i].setModel(dlm[i]);
	}
}

package sixiuSystem;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.List;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.management.RuntimeErrorException;
import javax.print.attribute.standard.RequestingUserName;
import javax.security.auth.x500.X500Principal;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.WindowConstants;

public class ProblemEditWin extends JFrame{
	private static final String title = "题目编辑器";
	IssueWin own;
	Problem op;
	JList<String> ownList;
	DefaultListModel<String> ownListM;
	boolean new_status;
	int type = 0;
	JPanel ProblemTextP = new JPanel();
	JPanel ProblemCommentP = new JPanel();
	JPanel ProblemOptionP = new JPanel();
	JPanel ProblemPointAndRightans = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JTextArea ProblemTextA = new JTextArea();
	JTextArea ProblemCommentA = new JTextArea();
	JTextArea ProblemOptionA = new JTextArea();
	JTextField ProblemRightAnsF = new JTextField();
	JTextField ProblemPointF = new JTextField();
	Box MaxBox = Box.createVerticalBox();
	JButton SubmitB = new JButton();
	JRadioButton[] JOption = new JRadioButton[2];
	ButtonGroup tb = new ButtonGroup();
	JPanel AllC = new JPanel(new BorderLayout());
	public static void main(String[] avgs) {
		//new ProblemEditWin().Open(new MOProblem(), new IssueWin(new ProblemSet()));
		//new ProblemEditWin().Open(new OProblem(), null);
	}

	public ProblemEditWin () {
		super(title);
		setSize(450, 400);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		initArea("题干", ProblemTextP, ProblemTextA);
		initArea("选项", ProblemOptionP, ProblemOptionA);
		initArea("解析", ProblemCommentP, ProblemCommentA);
		SubmitB = new JButton("加入");




		AllC.add(MaxBox, BorderLayout.CENTER);
		AllC.add(ProblemPointAndRightans, BorderLayout.SOUTH);
		add(AllC);
		JFrame thiso = this;
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
				int i=JOptionPane.showConfirmDialog(thiso, "确定要退出编辑吗?您编辑的题目不会被保存", "退出编辑", JOptionPane.YES_NO_OPTION);
				 if(i==JOptionPane.YES_OPTION){
					 own.setEnabled(true);
					 own.getFocusableWindowState();
					 setVisible(false);

				 }
			}
			@Override
			public void windowClosed(WindowEvent e) {
			}
			@Override
			public void windowActivated(WindowEvent e) {
			}
		});


		SubmitB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Problem p;
				try {
					p = GetProblem();
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showConfirmDialog(thiso, "无效题目:" + e1.getMessage(), "错误", JOptionPane.CLOSED_OPTION);
					return;
				}
				if (new_status) {
					if (type == 0)
						own.set.add_problem((JProblem)p);
					else if (type == 1)
						own.set.add_problem((OProblem)p);
					else
						own.set.add_problem((MOProblem)p);
				}
				else {
					int index = ownList.getSelectedIndex();
					try {
						own.set.remove(type, index);
					} catch (Exception e1) {
						e1.printStackTrace();
						return;
					}
					own.set.insert_problem(type, index, p);
				}
				if (new_status) {
					ownListM.addElement(p.get_text());
					ownList.setModel(ownListM);
				}
				else {
					int index = ownList.getSelectedIndex();
					ownListM.remove(index);
					ownListM.add(index, p.get_text());
					ownList.setModel(ownListM);
				}
				own.setEnabled(true);
				own.getFocusableWindowState();
				setVisible(false);
			}
		});
	}


	public void Open(Problem o, IssueWin own) {
		setLocationRelativeTo(own);
		if (o.get_type() == 0) initJField();
		else initField();
		op = o;
		this.own = own;
		ownList = own.ProblemList[o.get_type()];
		ownListM = own.dlm[o.get_type()];
		type = o.get_type();
		own.setEnabled(false);
		OpenProblem(o);
		setVisible(true);
	}
	private void OpenProblem(Problem o) {
		ProblemTextA.setText(o.get_text());
		ProblemCommentA.setText(o.get_comment());
		if( o.get_text().equals("") ) {
			tb.clearSelection();
			ProblemRightAnsF.setText("");
			ProblemPointF.setText("");
			new_status = true;
			return;
		}
		new_status = false;
		ProblemPointF.setText(String.valueOf(o.get_points()));
		if (o.get_type() == 0) {
			JOption[o.get_rightans()^1].setSelected(true);
			JOption[o.get_rightans()].setSelected(false);
		}
		else if (o.get_type() == 1) {
			ProblemRightAnsF.setText(String.valueOf( (char) (o.get_rightans() + 'A') ) );
			ArrayList<String> option = o.get_option();
			String options = "";
			for (String x : option)
				options += x + '\n';
			ProblemOptionA.setText(options);
		}
		else {
			ArrayList<Integer> ans = new ArrayList<Integer>();
			ans = o.get_rightansA();
			String anss = "";
			for (int x : ans)
				anss += String.valueOf((char) (x + 'A') );
			ProblemRightAnsF.setText(anss);
			ArrayList<String> option = o.get_option();
			String options = "";
			for (String x : option)
				options += x + '\n';
			ProblemOptionA.setText(options);
		}
	}
	private void initArea(String title, JPanel p, JTextArea a) {
		SpringLayout ps = new SpringLayout();
		p = new JPanel(ps);
		JLabel Title = new JLabel(title);
		p.add(Title);
		SpringLayout.Constraints TitleCon = ps.getConstraints(Title);
		TitleCon.setX(Spring.constant(5));
		TitleCon.setY(Spring.constant(5));
		a.setLineWrap(true);
		JScrollPane as = new JScrollPane(a);
		p.add(as);
		SpringLayout.Constraints aCon = ps.getConstraints(as);
		aCon.setY(Spring.constant(5));
		aCon.setX(Spring.sum(TitleCon.getConstraint(SpringLayout.EAST), Spring.constant(5)) );
		aCon.setWidth(Spring.constant(330));
		aCon.setHeight(Spring.constant(80));
		MaxBox.add(p);
	}
	private void initField() {
		ProblemOptionA.setEditable(true);
		ProblemOptionA.setText("");
		ProblemPointAndRightans.removeAll();
		JLabel setJ = new JLabel("正确答案");
		ProblemPointAndRightans.add(setJ);
		ProblemRightAnsF = new JTextField(8);
		JLabel setP = new JLabel("分值");
		ProblemPointF = new JTextField(8);
		ProblemPointAndRightans.add(ProblemRightAnsF);
		ProblemPointAndRightans.add(setP);
		ProblemPointAndRightans.add(ProblemPointF);
		SubmitB.setPreferredSize(new Dimension(80, 21));
		ProblemPointAndRightans.add(SubmitB);
	}
	private void initJField() {
		ProblemOptionA.setEditable(false);
		ProblemOptionA.setText("对\n错\n");
		ProblemPointAndRightans.removeAll();
		JLabel setJ = new JLabel("正确答案");
		ProblemPointAndRightans.add(setJ);
		JOption[0] = new JRadioButton("对");
		JOption[1] = new JRadioButton("错");
		tb.add(JOption[0]); tb.add(JOption[1]);
		ProblemPointAndRightans.add(JOption[0]);
		ProblemPointAndRightans.add(JOption[1]);
		ProblemRightAnsF = new JTextField(8);
		JLabel setP = new JLabel("分值");
		ProblemPointF = new JTextField(8);
		ProblemPointAndRightans.add(setP);
		ProblemPointAndRightans.add(ProblemPointF);
		SubmitB.setPreferredSize(new Dimension(80, 21));
		ProblemPointAndRightans.add(SubmitB);
	}
	private Problem GetProblem() throws Exception {
		String ptext = ProblemTextA.getText().replace("\n", "");
		if (ptext.equals("")) throw new RuntimeException("没有设置题干");
		String comment = ProblemCommentA.getText().replace("\n", "");
		if (type == 0) {
			int seleced = -1;
			if (JOption[0].isSelected()) seleced = 0;
			else if (JOption[1].isSelected()) seleced = 1;
			if (seleced == -1) throw new RuntimeException("没有设置正确答案");
			int point = Getpoint();
			return new JProblem(ptext, point, seleced == 0, comment);
		}
		else if (type == 1) {

			ArrayList<String> option = new ArrayList<String>();
			String[] ops = ProblemOptionA.getText().split("[\\n]+");
			if (ops.length == 1 && ops[0].equals("")) throw new RuntimeException("没有设置选项");
			for (String x : ops) {
				option.add(x);
			}
			String rightans = ProblemRightAnsF.getText();
			rightans.replace(" ","");
			rightans.replace(",", "");
			if (rightans.length() != 1) throw new RuntimeException("正确答案设置错误");
			char opc = rightans.charAt(0);
			int ans = 0;
			if (opc >= 'a' && opc <= 'z') {
				ans = opc - 'a';
				if (ans < 0 || ans >= ops.length) throw new RuntimeException("正确答案设置错误");
			}
			else if (opc >= 'A' && opc <= 'Z') {
				ans = opc - 'A';
				if (ans < 0 || ans >= ops.length) throw new RuntimeException("正确答案设置错误");
			}
			else throw new RuntimeException("正确答案设置错误");
			int point = Getpoint();
			return new OProblem(ptext, point, ans, option, comment);
		}
		else {
			ArrayList<String> option = new ArrayList<String>();
			String[] ops = ProblemOptionA.getText().split("[\\n]+");
			if (ops.length == 1 && ops[0].equals("")) throw new RuntimeException("没有设置选项");
			for (String x : ops) {
				option.add(x);
			}
			String rightans = ProblemRightAnsF.getText();
			rightans.replace(" ", "");
			rightans.replace(",", "");
			ArrayList<Integer> ans = new ArrayList<Integer>();
			char[] ric = rightans.toCharArray();
			if (rightans.length() > ops.length || rightans.length() == 0) throw new RuntimeException("正确答案设置错误");
			for (char opc : ric) {
				if (opc >= 'a' && opc <= 'z') {
					if (opc - 'a' >= ops.length) throw new RuntimeException("正确答案设置错误");
					ans.add(opc - 'a');
				}
				else if (opc >= 'A' && opc <= 'Z') {
					if (opc - 'A' >= ops.length) throw new RuntimeException("正确答案设置错误");
					ans.add(opc - 'A');
				}
				else throw new RuntimeException("正确答案设置错误");
			}
			int point = Getpoint();
			return new MOProblem(ptext, point, ans, option, comment);
		}
	}
	private int Getpoint() throws Exception {
		String sp = ProblemPointF.getText();
		char[] spc = sp.toCharArray();
		if (sp.equals("")) throw new RuntimeException("分数设置错误");
		int res = 0;
		for (char x : spc) {
			if (x < '0' || x > '9') throw new RuntimeException("分数设置错误");
			res = res * 10 + x - '0';
		}
		if (sp.length() > 5) throw new RuntimeException("分数过大");
		return res;
	}
}


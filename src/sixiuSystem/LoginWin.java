package sixiuSystem;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.net.SocketException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import org.w3c.dom.Document;


public class LoginWin extends JFrame{
	static final String title = "˼��֪ʶ�ʴ�";
	static final String[] dir = new String[] {"DefaultSet/1.pbs", "DefaultSet/2.pbs", "DefaultSet/3.pbs", "DefaultSet/4.pbs"};
	public static void main(String[] args) {
	}
	public LoginWin() {
		
		super(title);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(460, 490);
		setResizable(false);
		setLocationRelativeTo(null);
		Box vBoxAns = Box.createVerticalBox();
		Box vBoxIss = Box.createVerticalBox();
		Box vBoxInfo = Box.createHorizontalBox();
		Box vBox = Box.createVerticalBox();
		String[] listData = new String[]{"˼���ʴ��⼯1", "˼���ʴ��⼯2", "˼���ʴ��⼯3", "˼���ʴ��⼯4"};
		FlowLayout fl = new FlowLayout(FlowLayout.CENTER);
		fl.setVgap(10);
		fl.setHgap(20);
		JPanel Answering = new JPanel(fl);
		//Answering.setBorder(BorderFactory.createRaisedBevelBorder());
		JLabel AnsweringLabel = new JLabel("Ĭ����Ŀ��:");
		JButton AnsweringButton = new JButton("��ʼ����");
		JComboBox<String> AnsweringComboBox = new JComboBox<String>(listData);
		Answering.add(AnsweringLabel);
		Answering.add(AnsweringComboBox);
		Answering.add(AnsweringButton);
		vBoxAns.add(Answering);
		FlowLayout fl2 = new FlowLayout(FlowLayout.CENTER);
		fl2.setVgap(10);
		fl2.setHgap(10);
		JPanel SelectPbs = new JPanel(fl2);
		//SelectPbs.setBorder(BorderFactory.createRaisedBevelBorder());
		JLabel SelectLabel = new JLabel("������Ŀ��:");
		JTextField SelectField = new JTextField(10);
		JButton SelectPbsAns = new JButton("��ʼ����");
		SelectPbsAns.setEnabled(false);
		JButton SelectPbsSel = new JButton("Ԥ��..");
		SelectPbs.add(SelectLabel);
		SelectPbs.add(SelectField);
		SelectPbs.add(SelectPbsSel);
		SelectPbs.add(SelectPbsAns);
		vBoxAns.add(SelectPbs);
		FlowLayout fl3 = new FlowLayout(FlowLayout.CENTER);
		fl3.setVgap(10);
		fl3.setHgap(20);
		JPanel Issue = new JPanel(fl3);
		//Issue.setBorder(BorderFactory.createRaisedBevelBorder());
		JLabel IssueLabel = new JLabel("�½���Ŀ��:");
		JButton IssueCreate = new JButton("�½��⼯");
		Issue.add(IssueLabel);
		Issue.add(IssueCreate);
		vBoxIss.add(Issue);
		FlowLayout fl4 = new FlowLayout(FlowLayout.CENTER);
		fl4.setVgap(10);
		fl4.setHgap(15);
		JPanel IssueSel = new JPanel(fl4);
		//IssueSel.setBorder(BorderFactory.createRaisedBevelBorder());
		JLabel IssueLabelSel = new JLabel("������Ŀ��:");
		JTextField IssueField = new JTextField(10);
		JButton IssueInput = new JButton("�����⼯");
		IssueInput.setEnabled(false);
		JButton IssueSelect = new JButton("Ԥ��..");
		IssueSel.add(IssueLabelSel);
		IssueSel.add(IssueField);
		IssueSel.add(IssueSelect);
		IssueSel.add(IssueInput);
		
		vBoxIss.add(IssueSel);
		
		JPanel FtpLogin = new JPanel();
		JButton FtpLoginb = new JButton("�����������⼯");
		FtpLogin.setBorder(BorderFactory.createTitledBorder("�����⼯"));
		FtpLogin.add(FtpLoginb);
		JPanel help = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton helpb = new JButton("����");
		help.add(helpb);
		JPanel about = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton aboutb = new JButton("����");
		about.add(aboutb);
		vBoxInfo.add(help);
		vBoxInfo.add(about);
		
		vBoxAns.setBorder(BorderFactory.createTitledBorder("��Ҫ����"));
		vBoxIss.setBorder(BorderFactory.createTitledBorder("��Ҫ����"));
		JPanel jpvBoxA = new JPanel();
		JPanel jpvBoxI = new JPanel();
		jpvBoxA.add(vBoxAns);
		jpvBoxI.add(vBoxIss);
		vBox.add(jpvBoxA);
		vBox.add(jpvBoxI);
		vBox.add(FtpLogin);
		vBox.add(vBoxInfo);
		add(vBox);
		
		
		
		
		
		
		SelectPbsSel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("����ļ�(*.pbs)", "pbs");
				jfc.setFileFilter(filter);
				int res = jfc.showDialog(new JLabel(), "ѡ��");
				if (res == JFileChooser.APPROVE_OPTION) {
					File file = jfc.getSelectedFile();
					SelectField.setText(file.getAbsolutePath());
				}
			}
		});
		SelectField.getDocument().addDocumentListener(new DocumentListener() {	
			@Override
			public void removeUpdate(DocumentEvent e) {
				if(SelectField.getText().equals("")) {
					SelectPbsAns.setEnabled(false);
				}
				else {
					SelectPbsAns.setEnabled(true);
				}
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				if(SelectField.getText().equals("")) {
					SelectPbsAns.setEnabled(false);
				}
				else {
					SelectPbsAns.setEnabled(true);
				}
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});
		IssueField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				if(IssueField.getText().equals("")) {
					IssueInput.setEnabled(false);
				}
				else {
					IssueInput.setEnabled(true);
				}
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				if(IssueField.getText().equals("")) {
					IssueInput.setEnabled(false);
				}
				else {
					IssueInput.setEnabled(true);
				}
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});
		LoginWin spread = this;
		helpb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				HelpWin aw = new HelpWin(spread);
				aw.setVisible(true);
			}
		});
		
		aboutb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AboutWin aw = new AboutWin(spread);
				aw.setVisible(true);
			}
		});
		JFrame thiso = this;
		LoginWin te = this;
		AnsweringButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				System.out.println("AnsweringCombobox selected :" + (AnsweringComboBox.getSelectedIndex() + 1));
				int index = AnsweringComboBox.getSelectedIndex();
				try {
					new AnsweringWin(dir[index], 0, thiso, 0).setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showConfirmDialog(null, "�ļ�����ʧ��	������!." + e1.getMessage(), "����", JOptionPane.CLOSED_OPTION);
					return;
				}
				setExtendedState(JFrame.ICONIFIED); 
			}
		});
		SelectPbsAns.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(SelectField.getText());
				try {
					new AnsweringWin(SelectField.getText(), 0, thiso, 0).setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showConfirmDialog(null, "�ļ�����ʧ��	������!." + e1.getMessage(), "����", JOptionPane.CLOSED_OPTION);
					return;
				}
				setExtendedState(JFrame.ICONIFIED); 
			}
		});
		IssueSelect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("����ļ�(*.pbs)", "pbs");
				jfc.setFileFilter(filter);
				int res = jfc.showDialog(new JLabel(), "ѡ��");
				if (res == JFileChooser.APPROVE_OPTION) {
					File file = jfc.getSelectedFile();
					IssueField.setText(file.getAbsolutePath());
				}
			}
		});
		IssueInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(SelectField.getText());
				try {
					ProblemSet set = new ProblemSet();
					set.input(IssueField.getText());
					new IssueWin(set, thiso).setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showConfirmDialog(null, "�ļ�����ʧ��	������!." + e1.getMessage(), "����", JOptionPane.CLOSED_OPTION);
					return;
				}
				setExtendedState(JFrame.ICONIFIED); 
			}
		});
		IssueCreate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new IssueWin(new ProblemSet(), thiso).setVisible(true);
				setExtendedState(JFrame.ICONIFIED); 
			}
			
		});
		FtpLoginb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LoadingWin load = new LoadingWin(thiso);
				load.setVisible(true);
				FtpLoginWin Win;
				try {
					Win = new FtpLoginWin();
				} catch (SocketException e1) {
					load.setVisible(false);
					return;
				} catch (IOException e1) {
					load.setVisible(false);
					return;
				}
				load.setVisible(false);
				Win.setVisible(true);
				setExtendedState(JFrame.ICONIFIED); 
			}
		});
	}
}

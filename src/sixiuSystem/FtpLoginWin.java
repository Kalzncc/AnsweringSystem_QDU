package sixiuSystem;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.SocketException;

import javax.crypto.interfaces.PBEKey;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class FtpLoginWin extends JFrame {
	String name;
	FtpConnect con;
	LoadingWin load = new LoadingWin(this);
	JLabel LnameL = new JLabel("�û���:  ");
	JLabel SnameL = new JLabel("�û���:    ");
	JLabel LpasswdL = new JLabel("����:     ");
	JLabel SpasswdL = new JLabel("����:        ");
	JLabel ScpasswdL = new JLabel("ȷ������:");
	JTextField LnameF = new JTextField();
	JTextField SnameF = new JTextField();
	JPasswordField LpasswdF = new JPasswordField();
	JPasswordField SpasswdF = new JPasswordField();
	JPasswordField ScpasswdF = new JPasswordField();
	JButton LoginB = new JButton("��½");
	JButton SignupB = new JButton("ע��");
	Box hBox = Box.createHorizontalBox();
	Box vBoxLogin = Box.createVerticalBox();
	Box vBoxSiginup = Box.createVerticalBox();
 	public static void main(String[] args) throws SocketException, IOException {
		new FtpLoginWin().setVisible(true);
	}
	public FtpLoginWin() throws SocketException, IOException {
		super("��½����ע��");
		try {
			con = new FtpConnect();
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(this, "�޷����ӷ�����" , "����", JOptionPane.CLOSED_OPTION);
			throw new SocketException();
			
		}
		load.setVisible(false);
		setSize(500, 340);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		vBoxLogin.add(ConJPanel(LnameL, LnameF));
		vBoxLogin.add(ConJPanel(LpasswdL, LpasswdF));
		vBoxLogin.add(LoginB);
		vBoxLogin.setBorder(BorderFactory.createTitledBorder("��½"));
		vBoxSiginup.add(ConJPanel(SnameL, SnameF));
		vBoxSiginup.add(ConJPanel(SpasswdL, SpasswdF));
		vBoxSiginup.add(ConJPanel(ScpasswdL, ScpasswdF));
		vBoxSiginup.add(SignupB);
		vBoxSiginup.setBorder(BorderFactory.createTitledBorder("ע��"));
		JSplitPane js = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		js.setLeftComponent(vBoxLogin);
		js.setRightComponent(vBoxSiginup);
		js.setDividerLocation(210);
		js.setEnabled(false);
//		hBox.add(vBoxLogin);
//		hBox.add(vBoxSiginup);
		add(js);
		FtpLoginWin thiso = this;
		LoginB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = LnameF.getText();
				if (!name.matches("[a-zA-Z0-9]{3,10}")) {
					JOptionPane.showConfirmDialog(thiso, "�û��������Ϲ淶:�û���������Ϊ3�����10����������ĸ������" , "����", JOptionPane.CLOSED_OPTION);
					return;
				}
				load.setVisible(true);
				load.setLocationRelativeTo(thiso);
				try {
					con.Login(name, LpasswdF.getText());
					con.clear();
				}
				catch (RuntimeException e1) {
					load.setVisible(false);
					con.clear();
					JOptionPane.showConfirmDialog(thiso, "�û������������" , "����", JOptionPane.CLOSED_OPTION);
					
					return;
				}
				catch (IOException e1) {
					load.setVisible(false);
					con.clear();
					JOptionPane.showConfirmDialog(thiso, "�޷���֤�û���������" , "����", JOptionPane.CLOSED_OPTION);
					
					return;
				}
				load.setVisible(false);
				setVisible(false);
				new FtpProblemSetWin(name, con);
			}
		});
		SignupB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = SnameF.getText();
				if (!name.matches("[a-zA-Z0-9]{3,10}")) {
					JOptionPane.showConfirmDialog(thiso, "�û��������Ϲ淶:�û���������Ϊ3�����10����������ĸ������" , "����", JOptionPane.CLOSED_OPTION);
					return;
				}
				String pd = SpasswdF.getText();
				if (!pd.matches("[\\S]{6,16}")) {
					JOptionPane.showConfirmDialog(thiso, "���벻���Ϲ淶:����������Ϊ8�����16����������ĸ���ֵȷǿհ��ַ�" , "����", JOptionPane.CLOSED_OPTION);
					return;
				}
				String pcd = ScpasswdF.getText();
				if (!pd.equals(pcd)) {
					JOptionPane.showConfirmDialog(thiso, "�������벻һ��" , "����", JOptionPane.CLOSED_OPTION);
					return;
				}
				load.setVisible(true);
				load.setLocationRelativeTo(thiso);
				try {
					con.Signup(name, pd);
					con.clear();
				}
				catch (Exception e1) {
					load.setVisible(false);
					JOptionPane.showConfirmDialog(thiso, "ע��ʧ�ܣ�" + e1.getMessage(), "����", JOptionPane.CLOSED_OPTION);
					return;
				}
				load.setVisible(false);
				
				JOptionPane.showConfirmDialog(thiso, "ע��ɹ�!", "��Ϣ", JOptionPane.CLOSED_OPTION);
				
			}
		});
	}
	private JPanel ConJPanel(JLabel a, JTextField b) {
		JPanel s = new JPanel(new FlowLayout(FlowLayout.LEFT));
		b.setPreferredSize(new Dimension(150, 30));
		s.add(a); s.add(b);
		return s;
	}
	private JPanel ConJPanel(JLabel a, JPasswordField b) {
		JPanel s = new JPanel(new FlowLayout(FlowLayout.LEFT));
		b.setPreferredSize(new Dimension(150, 30));
		s.add(a); s.add(b);
		return s;
	}
}

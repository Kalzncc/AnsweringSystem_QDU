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
	JLabel LnameL = new JLabel("用户名:  ");
	JLabel SnameL = new JLabel("用户名:    ");
	JLabel LpasswdL = new JLabel("密码:     ");
	JLabel SpasswdL = new JLabel("密码:        ");
	JLabel ScpasswdL = new JLabel("确认密码:");
	JTextField LnameF = new JTextField();
	JTextField SnameF = new JTextField();
	JPasswordField LpasswdF = new JPasswordField();
	JPasswordField SpasswdF = new JPasswordField();
	JPasswordField ScpasswdF = new JPasswordField();
	JButton LoginB = new JButton("登陆");
	JButton SignupB = new JButton("注册");
	Box hBox = Box.createHorizontalBox();
	Box vBoxLogin = Box.createVerticalBox();
	Box vBoxSiginup = Box.createVerticalBox();
 	public static void main(String[] args) throws SocketException, IOException {
		new FtpLoginWin().setVisible(true);
	}
	public FtpLoginWin() throws SocketException, IOException {
		super("登陆或者注册");
		try {
			con = new FtpConnect();
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(this, "无法连接服务器" , "错误", JOptionPane.CLOSED_OPTION);
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
		vBoxLogin.setBorder(BorderFactory.createTitledBorder("登陆"));
		vBoxSiginup.add(ConJPanel(SnameL, SnameF));
		vBoxSiginup.add(ConJPanel(SpasswdL, SpasswdF));
		vBoxSiginup.add(ConJPanel(ScpasswdL, ScpasswdF));
		vBoxSiginup.add(SignupB);
		vBoxSiginup.setBorder(BorderFactory.createTitledBorder("注册"));
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
					JOptionPane.showConfirmDialog(thiso, "用户名不符合规范:用户名是最少为3个最多10个仅包含字母和数字" , "错误", JOptionPane.CLOSED_OPTION);
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
					JOptionPane.showConfirmDialog(thiso, "用户名或密码错误" , "错误", JOptionPane.CLOSED_OPTION);
					
					return;
				}
				catch (IOException e1) {
					load.setVisible(false);
					con.clear();
					JOptionPane.showConfirmDialog(thiso, "无法验证用户名和密码" , "错误", JOptionPane.CLOSED_OPTION);
					
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
					JOptionPane.showConfirmDialog(thiso, "用户名不符合规范:用户名是最少为3个最多10个仅包含字母和数字" , "错误", JOptionPane.CLOSED_OPTION);
					return;
				}
				String pd = SpasswdF.getText();
				if (!pd.matches("[\\S]{6,16}")) {
					JOptionPane.showConfirmDialog(thiso, "密码不符合规范:密码是最少为8个最多16个仅包含字母数字等非空白字符" , "错误", JOptionPane.CLOSED_OPTION);
					return;
				}
				String pcd = ScpasswdF.getText();
				if (!pd.equals(pcd)) {
					JOptionPane.showConfirmDialog(thiso, "两次密码不一致" , "错误", JOptionPane.CLOSED_OPTION);
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
					JOptionPane.showConfirmDialog(thiso, "注册失败：" + e1.getMessage(), "错误", JOptionPane.CLOSED_OPTION);
					return;
				}
				load.setVisible(false);
				
				JOptionPane.showConfirmDialog(thiso, "注册成功!", "信息", JOptionPane.CLOSED_OPTION);
				
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

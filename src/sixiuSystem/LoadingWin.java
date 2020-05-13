package sixiuSystem;

import java.awt.Window;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class LoadingWin extends JFrame{
	public LoadingWin(JFrame fa) {
		super("载入中");
		setSize(300, 150);
		setResizable(false);
		setLocationRelativeTo(fa);
		String t = "\n\n\t  正在连接服务器...";
		JLabel tp = new JLabel();
		JPanel jp = new JPanel();
		Box vBox = Box.createVerticalBox();
		jp.add(tp);
		vBox.add(Box.createVerticalGlue());
		vBox.add(jp);
		vBox.add(Box.createVerticalGlue());
		tp.setText(t);
		add(vBox);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

	}
}

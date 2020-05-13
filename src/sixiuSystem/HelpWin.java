package sixiuSystem;

import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class HelpWin extends JDialog{
	static final String heString = "\n\n   我要答题: 使用默认题集或者自行导入其他题集进行答题\n\n   我要出题: 自己编辑一套题目集保存在电脑中\n";
	public HelpWin (LoginWin own) {
		own.setEnabled(false);
		setTitle("帮助");
		setSize(520, 250);
		setResizable(false);
		setLocationRelativeTo(own);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		own.setEnabled(false);
		addWindowListener(new WindowListener() {
			@Override
			public void windowClosing(WindowEvent e) {
				own.setEnabled(true);				
			}
			@Override
			public void windowActivated(WindowEvent e) {}
			@Override
			public void windowClosed(WindowEvent e) {}
			@Override
			public void windowDeactivated(WindowEvent e) {}
			@Override
			public void windowDeiconified(WindowEvent e) {}
			@Override
			public void windowIconified(WindowEvent e) {}
			@Override
			public void windowOpened(WindowEvent e) {}
		});
		FlowLayout fl = new FlowLayout(FlowLayout.CENTER);
		fl.setVgap(10);
		fl.setHgap(20);
		JPanel he = new JPanel(fl);
		JTextArea hepT = new JTextArea(heString);
		hepT.setEditable(false);
		he.add(hepT);
		add(hepT);
	}
	
}

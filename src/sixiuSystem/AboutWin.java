package sixiuSystem;

import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class AboutWin extends JDialog {
	static final String heString = "\n	     本程序由软工二班政治实践小组\n\n	      程昊宇	        刘金辉\n	      齐福领	        马明皓\n\n	               协作完成";
	public static void main(String[] args) {
		LoginWin o = new LoginWin();
		AboutWin t = new AboutWin(o);
		t.setVisible(true);
	}
	public AboutWin (LoginWin own) {
		own.setEnabled(false);
		setTitle("关于");
		setSize(500, 250);
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

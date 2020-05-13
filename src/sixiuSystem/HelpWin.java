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
	static final String heString = "\n\n   ��Ҫ����: ʹ��Ĭ���⼯�������е��������⼯���д���\n\n   ��Ҫ����: �Լ��༭һ����Ŀ�������ڵ�����\n";
	public HelpWin (LoginWin own) {
		own.setEnabled(false);
		setTitle("����");
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

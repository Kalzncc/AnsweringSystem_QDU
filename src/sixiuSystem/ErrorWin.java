package sixiuSystem;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class ErrorWin extends JDialog{
	static final String heString = "\n\n         	             Œƒº˛∂¡»Î ß∞‹!\n	                    «Î÷ÿ ‘\n";
	public static void main(String[] args) {
		LoginWin o = new LoginWin();
		ErrorWin t = new ErrorWin(o);
		t.setVisible(true);
	}
	public ErrorWin (LoginWin owner) {
		owner.setEnabled(false);
		setTitle("¥ÌŒÛ!");
		setSize(350, 200);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		owner.setEnabled(false);
		addWindowListener(new WindowListener() {
			@Override
			public void windowClosing(WindowEvent e) {
				owner.setEnabled(true);				
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
		hepT.setForeground(Color.red);
		hepT.setEditable(false);
		he.add(hepT);
		add(hepT);
	}
}

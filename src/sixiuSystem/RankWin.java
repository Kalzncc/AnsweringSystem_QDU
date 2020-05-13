package sixiuSystem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.xml.crypto.Data;

public class RankWin extends JFrame{
	public RankWin (JFrame own, FtpConnect con, int index) throws IOException {
		super("No."+index+"�⼯�����а�(ǰ20��)");
		con.DownLoadRank(index);
		JScrollPane jts = new JScrollPane();
		JTable jt;
		setSize(300, 400);
		setResizable(false);
		setLocationRelativeTo(own);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Object[] name = {"����", "�û���", "�÷�"};
		Scanner cin = new Scanner(new File("DownLoad/.rank"));
		int n = cin.nextInt();
		cin.nextLine();
		Object[][] data = new Object[20][3];
		ArrayList<String[]> mp = new ArrayList<String[]>();
		for (int i = 0; i < Math.min(20, n); i++) {
			String el = cin.nextLine();
			el = (i+1) + " " + el;
			String[] datat = el.split(" ");
			for (int j = 0; j < 3; j++) data[i][j] = datat[j];
		}
		jt = new JTable(data, name);
		jt.getTableHeader().setFont(new Font(null, Font.BOLD, 14));  // ���ñ�ͷ����������ʽ
        jt.getTableHeader().setForeground(Color.RED);                // ���ñ�ͷ����������ɫ
        jt.getTableHeader().setResizingAllowed(false);               // ���ò������ֶ��ı��п�
        jt.getTableHeader().setReorderingAllowed(false); 
        jt.setRowHeight(30);
        jt.setFont(new Font(Font.SERIF, Font.BOLD, 13));
		jt.setPreferredScrollableViewportSize(new Dimension(300, 400));
		jt.setEnabled(false);
		jts = new JScrollPane(jt);
		add(jts);
		con.clear();
	}
}

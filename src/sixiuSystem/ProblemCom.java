package sixiuSystem;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.security.auth.x500.X500Principal;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.SliderUI;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;



public class ProblemCom {
	private JButton ProblemButton;
	Problem thiso;
	ButtonGroup OptionSel;
	JRadioButton[] Option;
	String Ptext;
	JCheckBox[] OptionA;
	JPanel ProblemcancelL = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	JButton Problemcancel;
	JTextArea Comment;
	JScrollPane CommentS;
	int status;
	ArrayList<Integer> selected = new ArrayList<Integer>();
	public ProblemCom(String text, Problem o, SpringLayout lay, AnsweringWin owner) throws Exception {
		thiso = o;
		Ptext = new String("");
		status = 0;
		if (thiso.get_type() == 0)
			Ptext += "判断题-" + text + "\n";
		else if (thiso.get_type() == 1)
			Ptext += "单选题-" + text + "\n";
		else
			Ptext += "多选题-" + text + "\n";
		Ptext += "    " + thiso.get_text() + "(" + thiso.get_points() + "分)";
		ProblemButton = new JButton(text);
		Problemcancel = new JButton("取消答题");
//		Problemcancel.setOpaque(false);
//		Problemcancel.setBorderPainted(true);
		ProblemButton.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.normal));
		OptionSel = new ButtonGroup();
		ProblemButton.setOpaque(false);
		ProblemButton.setMargin(new Insets(0, 0, 0, 0));
		ProblemButton.setBorderPainted(true);
		lay.getConstraints(ProblemButton).setWidth(Spring.constant(30));
		lay.getConstraints(ProblemButton).setHeight(Spring.constant(30));
		if (!o.get_status()) {
			ProblemButton.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.normal));
		}
		else {
			ProblemButton.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.blue));
		}
		
		if (thiso.get_type() == 0) {
			Option = new JRadioButton[3];
			Option[0] = new JRadioButton("对");
			Option[0].setFont(new Font(Font.SERIF, Font.BOLD, 23));
			Option[1] = new JRadioButton("错");
			Option[1].setFont(new Font(Font.SERIF, Font.BOLD, 23));
			OptionSel.add(Option[0]);
			OptionSel.add(Option[1]);
			
			if (thiso.get_status())
				selected.add(thiso.get_getans());
			Option[1].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					selected.clear();
					selected.add(0);
					ProblemButton.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.blue));
					owner.ProblemStatus.revalidate();
					owner.ProblemStatus.repaint();
				}
			});
			Option[0].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					selected.clear();
					selected.add(1);
					ProblemButton.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.blue));
					owner.ProblemStatus.revalidate();
					owner.ProblemStatus.repaint();
				}
			});
			
		}
		else if (thiso.get_type() == 1) {
			Option = new JRadioButton[thiso.get_option_num() + 2];
			for (int i = 0; i < thiso.get_option_num(); i++) {
				Option[i] = new JRadioButton(String.valueOf((char)(i + 'A'))+ ". " + thiso.get_option(i));
				Option[i].setFont(new Font(Font.SERIF, Font.BOLD, 23));
				OptionSel.add(Option[i]);
			}
			
			if (thiso.get_status())
					selected.add(thiso.get_getans());
			
			for (int i = 0; i < thiso.get_option_num(); i++) {
				int tt = i;
				Option[i].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						selected.clear();
						selected.add(tt);
						ProblemButton.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.blue));

						owner.ProblemStatus.revalidate();
						owner.ProblemStatus.repaint();
					}
				});
			}
		}
		else {
			OptionA = new JCheckBox[thiso.get_option_num() + 2];
			for (int i = 0; i < thiso.get_option_num(); i++) {
				OptionA[i] = new JCheckBox(String.valueOf((char)(i + 'A'))+ ". " + thiso.get_option(i));
				OptionA[i].setFont(new Font(Font.SERIF, Font.BOLD, 23));
				
				//OptionSel.add(OptionA[i]);
			}
			if (thiso.get_status())
				selected = (ArrayList<Integer>) thiso.get_getansA().clone();
			for (int i = 0; i < thiso.get_option_num(); i++) {
				int tt = i;
				OptionA[tt].addChangeListener(new ChangeListener() {		
					@Override
					public void stateChanged(ChangeEvent e) {
						if (OptionA[tt].isSelected()) {
							selected.add(tt);
							ProblemButton.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.blue));

						}
						else {
							for (int i = 0; i < selected.size(); i++) {
								if (selected.get(i) == tt) selected.remove(i);
							}
							if (selected.size() == 0) {
								ProblemButton.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.normal));

							}
						}
						owner.ProblemStatus.revalidate();
						owner.ProblemStatus.repaint();
					}
				});
			}
		}
		
		ProblemButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				owner.ProblemArea.setText(Ptext);
				owner.ProblemOptionArea.removeAll();
				OptionSel.clearSelection();
				if (thiso.get_type() == 0) {
					if (selected.size() != 0)
						Option[selected.get(0)^1].setSelected(true);
					owner.ProblemOptionArea.add(Option[0]);
					owner.ProblemOptionArea.add(Option[1]);
				}
				else if (thiso.get_type() == 1) {
					if (selected.size() != 0)
						Option[selected.get(0)].setSelected(true);
					for (int i = 0; i < thiso.get_option_num(); i++)
						owner.ProblemOptionArea.add(Option[i]);
				}
				else {
					if (selected.size() != 0) {
						for (int i = 0; i < selected.size(); i++)
							OptionA[selected.get(i)].setSelected(true);
					}
					for (int i = 0; i < thiso.get_option_num(); i++)
						owner.ProblemOptionArea.add(OptionA[i]);
						
				}
//				if (status == 1) {
//					owner.ProTin1.add(CommentS);
//				}
				owner.ProblemOptionArea.add(Problemcancel);
				owner.ProTin1.revalidate();
				owner.ProTin1.repaint();
			}
		});
		Problemcancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ProblemButton.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.normal));

				selected.clear();
				if (thiso.get_type() == 0 || thiso.get_type() == 1)
					OptionSel.clearSelection();
				else
					for (int i = 0; i < thiso.get_option_num(); i++)
						OptionA[i].setSelected(false);
				owner.ProblemOptionArea.revalidate();
				owner.ProblemOptionArea.repaint();
			}
		});
	}
	
	public JButton get_ProblemButton () {
		return ProblemButton;
	}
	public void setLock() throws Exception{
		status = 1;
		Problemcancel.setEnabled(false);
		if (thiso.get_type() == 0) {
			Option[0].setEnabled(false);
			Option[1].setEnabled(false);
		}
		else if(thiso.get_type() == 1) {
			for (int i = 0; i < thiso.get_option_num(); i++)
				Option[i].setEnabled(false);
		}
		else {
			for (int i = 0; i < thiso.get_option_num(); i++) {
				OptionA[i].setEnabled(false);
			}
		}
		String comment = "";
		if (thiso.get_type() == 1 || thiso.get_type() == 0) {
			thiso.unanswering();
			if (!selected.isEmpty()) {
				thiso.answering(selected.get(0));
			}
		}
		else {
			thiso.unanswering();
			if (!selected.isEmpty()) {
				thiso.answering(selected);
			}
		}
		if (thiso.get_points() > thiso.get_getpoints()) {
			if (thiso.get_getpoints() == 0)
				ProblemButton.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));
			else
				ProblemButton.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
		}
		else {
			ProblemButton.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
		}
		comment = "\n\n";
		comment += "本题您的得分:" + thiso.get_getpoints() + "/" + thiso.get_points() + "\n"; 
		comment += "正确答案 : ";
		if(thiso.get_type() == 0) {
			comment += thiso.get_rightans() == 1 ? "对" : "错";
		}
		else if (thiso.get_type() == 1) {
			comment += String.valueOf((char) (thiso.get_rightans() + 'A') );
		}
		else {
			ArrayList<Integer> ans = thiso.get_rightansA();
			for (int x : ans) {
				comment += String.valueOf((char) (x + 'A') );
			}
		}
		comment += "\n" + "解析:" + thiso.get_comment();
		Ptext += comment;
//		Comment.setFont(new Font(Font.SERIF, Font.BOLD, 20));
//		Comment.setEditable(false);
//		Comment.setLineWrap(false);
//		CommentS = new JScrollPane(Comment, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//		owner.ProblemOptionArea.revalidate();
//		owner.ProblemOptionArea.repaint();
	}
}

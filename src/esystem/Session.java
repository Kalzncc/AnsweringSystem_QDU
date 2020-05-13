package esystem;

import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import sixiuSystem.IssueWin;

public class Session {
	public static void main(String args[]) {
		setWin();
		System.out.println("Connect to ....");
		FtpConnect con = null;
		try {
			con = new FtpConnect();
		}
		catch (Exception e) {
			System.err.println("Fail to Connect");
			System.exit(0);
		}
		Scanner cin = new Scanner(System.in);
		printMenu();
		while(true) {
			System.out.print("[ExamineMode]>");
			String op = cin.nextLine();
			if (op.equals("get_num")) {
				System.out.println("Connect to ....");
				try {
					System.out.println("当前队列人数:" + con.getQueueNum());
				}
				catch (Exception e) {
					System.err.println("Exception");
					continue;
				}
			}
			else if (op.equals("get_max")) {
				System.out.println("Connect to ....");
				try {
					System.out.println("当前队列最大值:" + -con.getNullNum());
				}
				catch (Exception e) {
					System.err.println("Exception");
					continue;
				}
			}
			else if (op.matches("get_index[ ]+[\\d]+")) {
				String[] tp = op.split(" ");
				int num = Integer.parseInt(tp[1]);
				System.out.println("Connect to ....");
				try {
					con.DownLoadSet(num);
				}
				catch (Exception e) {
					System.err.println("Exception");
					continue;
				}
			}
			else if (op.equals("open")) {
				try {
					con.OpenSet();
				}
				catch (Exception e) {
					System.err.println("Exception");
					continue;
				}
			}
			else if (op.equals("check")) {
				try {
					con.CheckSet();
				}
				catch (Exception e) {
					System.err.println("Exception");
					continue;
				}
			}
			else if (op.equals("ls")) {
				System.out.println("Connect to ....");
				try {
					con.List();
				} catch (IOException e) {
					System.err.println("Exception");
					continue;
				}
			}
			else if (op.equals("put")) {
				System.out.println("Connect to ....");
				try {
					con.AddProblemSet();
				}
				catch (Exception e) {
					System.err.println("Exception");
					continue;
				}
			}		 
			else if (op.matches("set_queue[ ]+[\\d]+")) {
				String[] tp = op.split(" ");
				int num = Integer.parseInt(tp[1]);
				System.out.print("重置审核队列需要清空队列,你确定要执行吗?(y确认):");
				if (!cin.nextLine().equals("y")) continue;
				System.out.println("Connect to ....");
				try {
					con.clearQueue();
					File nullnum = new File("UpLoad/.nullnum");
					OutputStreamWriter cout = new OutputStreamWriter(new FileOutputStream(nullnum), "utf-8");
					cout.write(String.valueOf(-num));
					cout.close();
					con.setNullNum(num);
				}
				catch (Exception e) {
					System.err.println("Exception");
				}
			}
			else if (op.equals("clear")) {
				System.out.print("你确定要清空审核队列吗?(y确认):");
				if (!cin.nextLine().equals("y")) continue;
				System.out.println("Connect to ....");
				try {
					con.clearQueue();
				}
				catch (Exception e) {
					System.err.println("Exception");
					continue;
				}
			}
			else if (op.equals("cls")) {
				try {
					Runtime.getRuntime().exec("cls");
				} catch (IOException e) {
					System.err.println("Exception");
					continue;
				}
			}
			else if (op.equals("exit")) {
				System.exit(0);
			}
			else {
				if (op.equals("")) continue;
				else System.out.println("Undefine Order");
			}
		}
	}
	static void printMenu() {
		System.out.println("----ExamineMode-----");
		System.out.println("get_num         : 获取当前队列文件数量");
		System.out.println("get_max         : 获取当前队列最大文件数量");
		System.out.println("ls              : 列举队列所有题目集信息");
		System.out.println("get_index <数字> : 下载第<数字>个文件到本地");
		System.out.println("open            : 打开编辑本地题集");
		System.out.println("check            : 打开预览本地题集");
		System.out.println("put             : 上传本地题集(通过审核)");
		System.out.println("clear           : 清空审核队列");
		System.out.println("cls             : 清屏");
		System.out.println("set_queue <数字> : 设置审核队列最大文件数量(会清空当前队列)");
		System.out.println("exit            : 退出");
		System.out.println("--------------------");
	}
	static void setWin () {
		try {
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencyAppleLike;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			
		}
		catch (Exception e) {
		}
		UIManager.put("RootPane.setupButtonVisible",false);
		Font font = new Font("黑体",Font.PLAIN,16);
        UIManager.put("Button.font", font);
        UIManager.put("CheckBox.font", font);
        UIManager.put("CheckBoxMenuItem.acceleratorFont", font);
        UIManager.put("CheckBoxMenuItem.font", font);
        UIManager.put("ColorChooser.font", font);
        UIManager.put("ComboBox.font", font);
        UIManager.put("DesktopIcon.font", font);
        UIManager.put("EditorPane.font", font);
        UIManager.put("FormattedTextField.font", font);
        UIManager.put("InternalFrame.titleFont", font);
        UIManager.put("Label.font", font);
        UIManager.put("List.font", font);
        UIManager.put("Menu.acceleratorFont", font);
        UIManager.put("Menu.font", font);
        UIManager.put("MenuBar.font", font);
        UIManager.put("MenuItem.acceleratorFont", font);
        UIManager.put("MenuItem.font", font);
        UIManager.put("OptionPane.font", font);
        UIManager.put("Panel.font", font);
        UIManager.put("PasswordField.font", font);
        UIManager.put("PopupMenu.font", font);
        UIManager.put("ProgressBar.font", font);
        UIManager.put("RadioButton.font", font);
        UIManager.put("RadioButtonMenuItem.acceleratorFont", font);
        UIManager.put("RadioButtonMenuItem.font", font);
        UIManager.put("ScrollPane.font", font);
        UIManager.put("Spinner.font", font);
        UIManager.put("TabbedPane.font", font);
        UIManager.put("Table.font", font);
        UIManager.put("TableHeader.font", font);
        UIManager.put("TextArea.font", font);
        UIManager.put("TextField.font", font);
        UIManager.put("TextPane.font", font);
        UIManager.put("TitledBorder.font", font);
        UIManager.put("ToggleButton.font", font);
        UIManager.put("ToolBar.font", font);
        UIManager.put("ToolTip.font", font);
        UIManager.put("Tree.font", font);
        UIManager.put("Viewport.font", font);
	}
}

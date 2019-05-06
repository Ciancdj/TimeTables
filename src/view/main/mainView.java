package view.main;

import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Calendar;
import java.util.Queue;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

import control.main.operaControl;
import model.business.ImgPanel;
import model.business.inForm;
import model.business.operaOther;


/**
* @author Cianc
* @version 创建时间：2019年4月30日 上午10:41:01
* @ClassName mainView
* @Description to build main view
*/
public class mainView extends JFrame{
	JPanel mainPanel = new JPanel();
	int WIDTH = inForm.WIDTH;
	int HEIGHT = inForm.HEIGHT;
	public mainView() {
		super("课程表");
		this.setBounds(650, 50, WIDTH, HEIGHT);
		mainPanel.setLayout(null);
		mainPanel.setBounds(0, 0, WIDTH, HEIGHT);
		mainPanel.setBackground(Color.white);
		Container c = this.getContentPane();
		c.add(mainPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void buildCourse() {
		Calendar date = Calendar.getInstance();
		courseView(date);
	}
	
	/**
	 * courese View
	 * to show people coures
	 */
	private void courseView(Calendar Date) {
		mainPanel.removeAll();
		ImgPanel coursePanel = new ImgPanel(inForm.imgPath);
		coursePanel.setLayout(null);
		coursePanel.setBounds(0, 0, WIDTH, HEIGHT);
		/*
		 * create the nav in top
		 */
		int Width = WIDTH - 10;
		int Height = 845;
		int T = Height % 13;
		Height -= T;
		Calendar tem = (Calendar) inForm.courseStart.clone();
		int week = operaOther.returnWeek(inForm.courseStart);
		/*
		 * building course in chess
		 */
		addCourse(operaControl.buildCourse(week, 0), Width, Height, T);
		addCourse(operaControl.buildCourse(week, 1), Width, Height, T);
		/*
		 * build main chess
		 */
		for(int index = 0; index < 13; index++) {
			JLabel nav = null;
			if(index == 0) {
				nav = new JLabel("<html>第<br>"+week+"<br>周<br></html>");
				nav.setBounds(0, 0, 60, (Height/13 + T));
				for(int i = 0; i < 7; i++) {
					JLabel top = new JLabel(inForm.WEEK[i]);
					top.setBounds(60 + (int)((Width - 60)/7)*i, 5, (int)((Width - 60)/7), (Height/13 + T)/2);
					top.setHorizontalAlignment(JLabel.CENTER);
					top.setFont(new Font("黑体",Font.CENTER_BASELINE,15));
					coursePanel.add(top);
					JLabel bot = new JLabel((tem.get(Calendar.DAY_OF_MONTH)) + "");
					tem.add(Calendar.DAY_OF_MONTH, 1);
					bot.setBounds(60 + (int)((Width - 60)/7)*i, (Height/13 + T)/2, (int)((Width - 60)/7), (Height/13 + T)/2);
					bot.setHorizontalAlignment(JLabel.CENTER);
					bot.setFont(new Font("黑体",Font.CENTER_BASELINE,15));
					coursePanel.add(bot);
					JLabel bor = new JLabel();
					bor.setBounds(60 + (int)((Width - 60)/7)*i, 0, (int)((Width - 60)/7), (Height/13 + T));
					bor.setBorder(BorderFactory.createLineBorder(Color.black, 2));
					coursePanel.add(bor);
				}
			} else {
				nav = new JLabel(index+"");
				nav.setBounds(0, (Height/13 + T) + (Height/13)*(index-1), 60, (Height/13));
//				for(int i = 0; i < 7; i++) {
//					JLabel chess = new JLabel("");
//					chess.setBounds((int)((Width - 60)/7)*i + 60, (Height/13 + T) + (Height/13)*(index-1),
//							(int)((Width - 60)/7), (Height/13));
//					chess.setHorizontalAlignment(JLabel.CENTER);
//					chess.setBorder(BorderFactory.createLineBorder(Color.black, 1));
//					coursePanel.add(chess);
//				}
			}
			nav.setBorder(BorderFactory.createLineBorder(Color.black, 2));
			nav.setHorizontalAlignment(JLabel.CENTER);
			coursePanel.add(nav);
		}
		JButton []button = {
				new JButton("课程表"), new JButton("设置")
		};
		for(int index = 0; index < button.length; index++) {
			button[index].setHorizontalAlignment(JLabel.CENTER);
			button[index].setFont(new Font("黑体",Font.BOLD,30));
			button[index].setBackground(Color.WHITE);
			button[index].setBounds(WIDTH * index / 2, HEIGHT-105, WIDTH/2, 60);
			coursePanel.add(button[index]);
		}
		button[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				installView();
			}
		});
		mainPanel.add(coursePanel);
		mainPanel.updateUI();
	}
	
	public void addCourse(Queue<Object[]> que, int Width, int Height, int T) {
		while(!que.isEmpty()) {
			Color color = inForm.color[(int)(1+Math.random()*(inForm.color.length-1-0))];
			Object []obj = que.poll();
			// classID \ classRoom \ courseName \ teacherName \ dayIndex \ startIndex \ endIndex
			JTextArea []area = {
					new JTextArea(), new JTextArea()
			};
			area[0].setText((String)obj[2]);
			area[1].setText((String)obj[0]);
			area[0].setBounds(((int)obj[4]-1) * (int)((Width - 60)/7) + 60, (Height/13 + T) + (Height/13) * ((int)obj[5]-1)
					, (int)((Width - 60)/7), (int)(((int)obj[6] - (int)obj[5] + 1) * (Height/13) / 2));
			area[1].setBounds(((int)obj[4]-1) * (int)((Width - 60)/7) + 60, (Height/13 + T) + (Height/13) * 
					((int)obj[5]-1) + (int)(((int)obj[6] - (int)obj[5] + 1) * (Height/13) / 2), (int)((Width - 60)/7), 
					(int)((int)obj[6] - (int)obj[5] + 1) * (Height/13) - (int)(((int)obj[6] - (int)obj[5] + 1) * (Height/13) / 2));
			for(int index = 0; index < area.length; index++) {
				area[index].setBackground(color);
				area[index].setFont(new Font("黑体",Font.BOLD,18));
				area[index].setLineWrap(true);
				area[index].setEditable(false);
				area[index].setDisabledTextColor(Color.white);
				mainPanel.add(area[index]);
			}
			JLabel bor = new JLabel();
			bor.setBorder(BorderFactory.createLineBorder(Color.black,1));
			bor.setBounds(((int)obj[4]-1) * (int)((Width - 60)/7) + 60, (Height/13 + T) + (Height/13) * ((int)obj[5]-1),
					(int)((Width - 60)/7), (int)((int)obj[6] - (int)obj[5] + 1) * (Height/13));
			mainPanel.add(bor);
		}
	}
	
	public void loginView() {
		mainPanel.removeAll();
		ImgPanel loginPanel = new ImgPanel(inForm.imgPath);
		loginPanel.setLayout(null);
		loginPanel.setBounds(0, 0, WIDTH, HEIGHT);
		/*
		 * 该处做背景图片设置
		 */
		loginPanel.setBackground(Color.WHITE);
		JButton []button = {
				new JButton("<-"), new JButton("登陆")
		};
		button[0].setBounds(0, 0, 80, 60);
		button[1].setBounds(20,500,550,100);
		for(int index = 0; index < button.length; index++) {
			button[index].setHorizontalAlignment(JLabel.CENTER);
			button[index].setFont(new Font("黑体",Font.BOLD,30));
			button[index].setBackground(Color.WHITE);
			loginPanel.add(button[index]);
		}
		button[1].setForeground(Color.WHITE);
		button[1].setBackground(Color.BLUE);
		JLabel headLine = new JLabel("绑定");
		headLine.setHorizontalAlignment(JLabel.CENTER);
		headLine.setFont(new Font("黑体",Font.BOLD,30));
		headLine.setBackground(Color.WHITE); 
		headLine.setBounds(80,0,WIDTH-98,60);
		headLine.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		loginPanel.add(headLine);
		JLabel []tips = {
			new JLabel("账号"), new JLabel("密码"),
			new JLabel(), new JLabel("")
		};
		tips[0].setBounds(20,230,120,60);
		tips[1].setBounds(20,350,120,60);
		tips[2].setBorder(BorderFactory.createTitledBorder("请在下方输入您的账号"));
		tips[3].setBorder(BorderFactory.createTitledBorder("请在下方输入您的密码"));
		tips[2].setBounds(10,200,550,100);
		tips[3].setBounds(10,330,550,100);
		for(int index = 0; index < tips.length; index++) {
			tips[index].setFont(new Font("Microsoft Yahei",Font.BOLD,30));
			tips[index].setHorizontalAlignment(JLabel.CENTER);
			loginPanel.add(tips[index]);
		}
		JTextField account = new JTextField();
		account.setFont(new Font("Microsoft Yahei",Font.BOLD,30));
		account.setBounds(140,230,400,60);
		loginPanel.add(account);
		JPasswordField password = new JPasswordField();
		password.setFont(new Font("Microsoft Yahei",Font.BOLD,30));
		password.setBounds(140,350,400,60);
		button[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				installView();
			}
		});
		button[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String acc = account.getText();
				String pas = new String(password.getPassword());
				int judge = operaControl.bingLogin(acc, pas);
				if(judge == -1) {
					JOptionPane.showMessageDialog(null, "账号或网络异常，请确认后重新输入！");
					return;
				}
				operaControl.holdLogin(acc,pas);
				try {
					operaControl.upTable();
				} catch (NullPointerException e) {
					JOptionPane.showMessageDialog(null, "程序加载异常，请关闭后重试！错误代码：actionError：buttonOne");
					return;
				}
				buildCourse();	
			}
		});
		loginPanel.add(password);
		mainPanel.add(loginPanel);
		mainPanel.updateUI();
	}
	
	/**
	 * install view 
	 * to show a view which include some function to get change
	 */
	public void installView() {
		mainPanel.removeAll();
		JPanel installPanel = new JPanel();
		installPanel.setLayout(null);
		installPanel.setBounds(0, 0, WIDTH, HEIGHT);
		/*
		 * 该处做背景图片的设置
		 */
		installPanel.setBackground(Color.white);
		/*
		 * use database to get the name accound
		 * if database is empty, show name and accound to null
		 */
		String name = "null", number = "("+"null"+")";
		if(!inForm.Name.equals("") && !inForm.Account.equals("")) {
			name = inForm.Name;
			number = "("+inForm.Account+")";
		}
		JLabel []infor = {
				new JLabel(name), new JLabel(number)
		};
		infor[0].setBounds(0,30,WIDTH,40);
		infor[1].setBounds(0,90,WIDTH,40);
		for(int index = 0; index < infor.length; index++) {
			infor[index].setHorizontalAlignment(JLabel.CENTER);
			infor[index].setFont(new Font("黑体",Font.BOLD,40 - index*15));
			installPanel.add(infor[index]);
		}
		JLabel line = new JLabel();
		line.setOpaque(true);
		line.setBackground(Color.GRAY);
		line.setBounds(0, 150, WIDTH, 20);
		installPanel.add(line);
		/**
		 * this place add the scopePanel
		 */
		JPanel installBox = new JPanel();
		installBox.setLayout(null);
		installBox.setBackground(Color.GRAY);
		JButton []install = {
			new JButton("绑定学号"), new JButton("更新课表"),
			new JButton("设置背景图片"), new JButton("调整当前周"), 
			new JButton("恢复初始化")
		};
		int T;
		for(T = 0; T < install.length; T++) {
			install[T].setHorizontalAlignment(JLabel.CENTER);
			install[T].setFont(new Font("黑体",Font.BOLD,30));
			install[T].setBackground(Color.white);
			install[T].setBounds(0, 10 * T + T * 60, WIDTH, 60);
			installBox.add(install[T]);
		}
		installBox.setPreferredSize(new Dimension(WIDTH,T*30));
		install[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				loginView();
			}
		});
		install[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				operaControl.upTable();
			}
		});
		install[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int result = 0;
				File file = null;
				JFileChooser fileChooser = new JFileChooser();
				String path = null;
				FileSystemView fsv = FileSystemView.getFileSystemView(); 
				fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
				fileChooser.setDialogTitle("请选择图片");
				fileChooser.setApproveButtonText("确认");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				result = fileChooser.showOpenDialog(fileChooser);
				if (JFileChooser.APPROVE_OPTION == result) {
					path=fileChooser.getSelectedFile().getPath();
				}
				operaControl.holdImg(path);
			}
		});
		install[3].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String input = JOptionPane.showInputDialog(null,"请输入当前是第几周：\n","切换周数",JOptionPane.PLAIN_MESSAGE);
				//int input = Integer.parseInt();
				if(input == null) {
					JOptionPane.showMessageDialog(null, "请输入正确的周数！");
					return;
				} else {
					operaControl.holdTime(Integer.parseInt(input));
					buildCourse();
				}
			}
		});
		install[4].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(JOptionPane.showConfirmDialog(null, "是否确认初始化?\n(若是初始化，程序会退出，请重启程序)", "初始化确认",JOptionPane.YES_NO_OPTION) == 0) {
					operaControl.reduction();
					buildCourse();
				}
			}
		});
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(installBox);
		scrollPane.setBounds(0, 170, WIDTH, HEIGHT - 275);
		installPanel.add(scrollPane);
		JButton []button = {
				new JButton("课程表"), new JButton("设置")
		};
		for(int index = 0; index < button.length; index++) {
			button[index].setHorizontalAlignment(JLabel.CENTER);
			button[index].setFont(new Font("黑体",Font.BOLD,30));
			button[index].setBackground(Color.WHITE);
			button[index].setBounds(WIDTH * index / 2, HEIGHT-105, WIDTH/2, 60);
			installPanel.add(button[index]);
		}
		button[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				buildCourse();
			}
		});
		mainPanel.add(installPanel);
		mainPanel.updateUI();
	}
}

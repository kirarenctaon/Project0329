package page;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainApp extends JFrame implements ActionListener{
	JButton[] menu=new JButton[3]; // bt_login, bt_content, bt_etc;
	JPanel p_north;	
	URL[] url=new URL[3];
	String[] path={"/login.png", "/content.png", "/etc.png"};	
	
	//페이지들을 보유한다!!
	JPanel p_center;//페이지들이 붙을 공간
	LoginForm loginForm;
	Content content;
	Etc etc;
	
	JPanel[] page=new JPanel[3];

	public MainApp() {
		p_north=new JPanel();

		for(int i=0;i<path.length;i++){
			url[i]=this.getClass().getResource(path[i]);
			menu[i] = new JButton(new ImageIcon(url[i]));
			p_north.add(menu[i]);
			//리스너와 부착도 for문 내에서 함
			menu[i].addActionListener(this);
		}
		add(p_north, BorderLayout.NORTH);
		
		//로그인 폼 생성
		p_center = new JPanel();
		page[0] = new LoginForm(); //로그인폰
		page[1] = new Content(); //컨텐츠
		page[2]  = new Etc(); //기타페이지
		/*
		loginForm = new LoginForm(); //로그인폰
		content = new Content(); //컨텐츠
		etc = new Etc(); //기타페이지
		*/
		
		p_center.add(page[0]); //얘도 패널이라 붙여야함, borderLayout으로 하면 맨 마지막에 붙이 녀석이 덮어버리므로 flow로 가야한다. 
		p_center.add(page[1]);
		p_center.add(page[2]);
		add(p_center);
		
		setSize(700, 600);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		for(int i=0;i<page.length;i++){
			if(obj==menu[i]){ 				
				page[i].setVisible(true);
			}else{	
				page[i].setVisible(false);
			}
		}
		/*
		if(obj==menu[0]){ 		//로그인폼 O, 컨텐츠 X, 기타 X
			loginForm.setVisible(true);
			content.setVisible(false);
			etc.setVisible(false);
		}else if(obj==menu[1]){ //로그인폼 X, 컨텐츠 O, 기타 X
			loginForm.setVisible(false);
			content.setVisible(true);
			etc.setVisible(false);
		}else if(obj==menu[2]){ //로그인폼 X, 컨텐츠 X, 기타 O
			loginForm.setVisible(false);
			content.setVisible(false);
			etc.setVisible(true);
		}
		*/
	}
	
	public static void main(String[] args) {
		new MainApp();
	}

}

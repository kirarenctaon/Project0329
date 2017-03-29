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
	
	//���������� �����Ѵ�!!
	JPanel p_center;//���������� ���� ����
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
			//�����ʿ� ������ for�� ������ ��
			menu[i].addActionListener(this);
		}
		add(p_north, BorderLayout.NORTH);
		
		//�α��� �� ����
		p_center = new JPanel();
		page[0] = new LoginForm(); //�α�����
		page[1] = new Content(); //������
		page[2]  = new Etc(); //��Ÿ������
		/*
		loginForm = new LoginForm(); //�α�����
		content = new Content(); //������
		etc = new Etc(); //��Ÿ������
		*/
		
		p_center.add(page[0]); //�굵 �г��̶� �ٿ�����, borderLayout���� �ϸ� �� �������� ���� �༮�� ��������Ƿ� flow�� �����Ѵ�. 
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
		if(obj==menu[0]){ 		//�α����� O, ������ X, ��Ÿ X
			loginForm.setVisible(true);
			content.setVisible(false);
			etc.setVisible(false);
		}else if(obj==menu[1]){ //�α����� X, ������ O, ��Ÿ X
			loginForm.setVisible(false);
			content.setVisible(true);
			etc.setVisible(false);
		}else if(obj==menu[2]){ //�α����� X, ������ X, ��Ÿ O
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

package game.word;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GamePanel extends JPanel implements ItemListener{
	GameWindow gameWindow;
	
	JPanel p_west; // ���� ��Ʈ�� ����
	JPanel p_center; //�ܾ� �׷��� ó�� ����
	
	JLabel la_user; //���� �α��� ������
	JLabel la_score; //���� ����
	Choice choice; //�ܾ� ���� ����ڽ�
	JTextField t_input; //�����Է�â
	JButton bt_start, bt_pause; //���ӽ��� ��ư
	String res="C:/java_workspace/project0329/res/";
	
	FileInputStream fis;
	InputStreamReader reader; //������ ������� ���ڽ�Ʈ��
	BufferedReader buffer; //���ڱ�� ���۽�Ʈ��
	
	//������ �ܾ ��Ƴ���! ���ӿ� ��Ա� ����
	ArrayList<String> wordList=new ArrayList<String>();
	
	public GamePanel(GameWindow gameWindow) {
		this.gameWindow=gameWindow;
		setLayout(new BorderLayout());
		
		p_west = new JPanel();
	
		//�� ������ ���ݺ��� �׸��� �׸� ����, ���� �͸�Ʈ�Ϸ� ǥ��
		p_center = new JPanel(){
			@Override
			public void paint(Graphics g) {
				g.drawString("����", 200, 500);
			}
		};
		
		la_user = new JLabel("batman"+"��");
		la_score = new JLabel("0"+"��");
		choice = new Choice();
		t_input = new JTextField(12); 
		bt_start = new JButton("start");
		bt_pause = new JButton("pause");
		
		p_west.setPreferredSize(new Dimension(150, 700));
		p_west.setBackground(Color.ORANGE);
		
		choice.add("�� �ܾ��� ����");
		choice.setPreferredSize(new Dimension(135, 40));
		choice.addItemListener(this);
		
		p_west.add(la_user);
		p_west.add(choice);
		p_west.add(t_input);
		p_west.add(bt_start);
		p_west.add(bt_pause);
		p_west.add(la_score);
		
		add(p_west, BorderLayout.WEST);
		add(p_center, BorderLayout.CENTER);
		
		setVisible(false); //������ ���� ����
		setPreferredSize(new Dimension(900, 700));
		
		getCategory();
	}
	
	//���̽� ������Ʈ�� ä���� ���ϸ� �����ϱ�
	public void getCategory(){
		File file=new File(res);
		File[] files=file.listFiles(); //�ľ�+���丮 �����ִ� �迭��ȯ
		
		for(int i=0;i<files.length;i++){
			if(files[i].isFile()){
				String name=files[i].getName();
				String[] arr=name.split("\\.");//Ư����ɹ��ڸ� �Ϲ� ���ڷ� ����
				if(arr[1].equals("txt")){ //split�� ���� �ΰ��� �и��� �Ǵµ� .�� �ִ� �κ��� �ι�°��
					choice.add(name);
				}
			}
		}// for�� ��
	}

	//�ܾ� �о����
	public void getWord(){
		int index=choice.getSelectedIndex();
		
		if(index!=0){//ù��° ��Ҵ� ����
			String name=choice.getSelectedItem();
			System.out.println(res+name);
			
			try {
				fis = new FileInputStream(res+name);
				reader = new InputStreamReader(fis, "utf-8");
				//��Ʈ�� ���� ó�� ���ر��� �ø�
				buffer=new BufferedReader(reader); //���۴� �츮������ ������ �ʴ� /n�� �������� ������
				
				String data;
				while(true){
					data=buffer.readLine();//����
					if(data==null)break;
					System.out.println(data);
					wordList.add(data);
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(buffer!=null){
					try {
						buffer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(reader!=null){
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(fis!=null){
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}//try catch finally��
		}
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		//System.out.println("�� �ٲ��?");
		getWord();
	}
}

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
	
	JPanel p_west; // 왼쪽 컨트롤 영역
	JPanel p_center; //단어 그래픽 처리 영역
	
	JLabel la_user; //게임 로그인 유저명
	JLabel la_score; //게임 점수
	Choice choice; //단어 선택 드랍박스
	JTextField t_input; //게임입력창
	JButton bt_start, bt_pause; //게임시작 버튼
	String res="C:/java_workspace/project0329/res/";
	
	FileInputStream fis;
	InputStreamReader reader; //파일을 대상으로 문자스트림
	BufferedReader buffer; //문자기반 버퍼스트림
	
	//조사한 단어를 담아놓자! 게임에 써먹기 위해
	ArrayList<String> wordList=new ArrayList<String>();
	
	public GamePanel(GameWindow gameWindow) {
		this.gameWindow=gameWindow;
		setLayout(new BorderLayout());
		
		p_west = new JPanel();
	
		//이 영역은 지금부터 그림을 그릴 영역, 내부 익명스트일로 표현
		p_center = new JPanel(){
			@Override
			public void paint(Graphics g) {
				g.drawString("고등어", 200, 500);
			}
		};
		
		la_user = new JLabel("batman"+"님");
		la_score = new JLabel("0"+"점");
		choice = new Choice();
		t_input = new JTextField(12); 
		bt_start = new JButton("start");
		bt_pause = new JButton("pause");
		
		p_west.setPreferredSize(new Dimension(150, 700));
		p_west.setBackground(Color.ORANGE);
		
		choice.add("▼ 단어집 선택");
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
		
		setVisible(false); //최조에 등장 안함
		setPreferredSize(new Dimension(900, 700));
		
		getCategory();
	}
	
	//초이스 컴포넌트에 채워질 파일명 조사하기
	public void getCategory(){
		File file=new File(res);
		File[] files=file.listFiles(); //파알+디렉토리 섞여있는 배열반환
		
		for(int i=0;i<files.length;i++){
			if(files[i].isFile()){
				String name=files[i].getName();
				String[] arr=name.split("\\.");//특수기능문자를 일반 문자로 만듬
				if(arr[1].equals("txt")){ //split에 의해 두개로 분리가 되는데 .이 있는 부분은 두번째임
					choice.add(name);
				}
			}
		}// for문 끝
	}

	//단어 읽어오기
	public void getWord(){
		int index=choice.getSelectedIndex();
		
		if(index!=0){//첫번째 요소는 빼자
			String name=choice.getSelectedItem();
			System.out.println(res+name);
			
			try {
				fis = new FileInputStream(res+name);
				reader = new InputStreamReader(fis, "utf-8");
				//스트림 버퍼 처리 수준까지 올림
				buffer=new BufferedReader(reader); //버퍼는 우리눈에는 보이지 않는 /n을 기준으로 구분함
				
				String data;
				while(true){
					data=buffer.readLine();//한줄
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
			}//try catch finally끝
		}
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		//System.out.println("나 바꿨어?");
		getWord();
	}
}

package game.word;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

public class GamePanel extends JPanel implements ItemListener, Runnable, ActionListener{
	GameWindow gameWindow;
	
	JPanel p_west; // 왼쪽 컨트롤 영역
	JPanel p_center; //단어 그래픽 처리 영역
	
	JLabel la_user; //게임 로그인 유저명
	JLabel la_score; //게임 점수
	Choice choice; //단어 선택 드랍박스
	JTextField t_input; //게임입력창
	JButton bt_start, bt_pause, bt_stop; //게임시작 버튼
	String res="C:/java_workspace/project0329/res/";
	
	FileInputStream fis;
	InputStreamReader reader; //파일을 대상으로 문자스트림
	BufferedReader buffer; //문자기반 버퍼스트림
	
	//조사한 단어를 담아놓자! 게임에 써먹기 위해
	ArrayList<String> wordList=new ArrayList<String>();
	Thread thread;// 단어게임을 진행할 쓰레드
	ArrayList<Word> words=new ArrayList<Word>();
	
	boolean flag=true;
	boolean isDown=true;
	
	public GamePanel(GameWindow gameWindow) {
		this.gameWindow=gameWindow;
		setLayout(new BorderLayout());
		
		p_west = new JPanel();
	
		//이 영역은 지금부터 그림을 그릴 영역, 내부 익명스트일로 표현
		p_center = new JPanel(){
			@Override
			public void paintComponent(Graphics g) { //paintComponent
				//기존 그림 지우기
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, 750, 700);
				g.setColor(Color.BLUE);
			    //g.drawString("고등어", 0, y);
				
				//모든 워드들에 대한 render호출
				for(int i=0;i<words.size();i++){
					words.get(i).render(g);
				}
			}
		};
		
		la_user = new JLabel("batman"+"님");
		la_score = new JLabel("0"+"점");
		choice = new Choice();
		t_input = new JTextField(12); 
		bt_start = new JButton("start");
		bt_pause = new JButton("pause");
		bt_stop = new JButton("stop");
	
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
		p_west.add(bt_stop);
		p_west.add(la_score);
		
		add(p_west, BorderLayout.WEST);
		add(p_center, BorderLayout.CENTER);
		
		//버튼과 리스너 연결
		bt_start.addActionListener(this);
		bt_pause.addActionListener(this);
		bt_stop.addActionListener(this);
		
		//텍스트 필드와 리스너 연결
		t_input.addKeyListener(new KeyAdapter() {
		
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					//System.out.println("키눌렀어?");
					//화면에 존재하는 words와 입력값 비교하여 같으면  words에서 객체 삭제
					String value=t_input.getText();
					for(int i=0;i<words.size();i++){ //wordList는 모아둔것일뿐이므로 객체넣어둔 words랑 비교해야함
						if(words.get(i).name.equals(value)){
							words.remove(i);
							t_input.setText("");
						}
						
					}
				}
			}
		}); 
		
		
		setVisible(false); //최조에 등장 안함
		setPreferredSize(new Dimension(900, 700));
		
		getCategory();
	}
	
	//초이스 컴포넌트에 채워질 파일명 조사하기
	public void getCategory(){
		File file=new File(res);
		File[] files=file.listFiles(); //파일+디렉토리 섞여 있는 배열반환
		
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
			//System.out.println(res+name);
			
			try {
				fis = new FileInputStream(res+name);
				reader = new InputStreamReader(fis, "utf-8");
				
				//스트림 버퍼 처리 수준까지 올림
				buffer=new BufferedReader(reader); //버퍼는 우리눈에는 보이지 않는 /n을 기준으로 구분함
				
				String data;
				//기존의 wordList를 비운다!!
				wordList.removeAll(wordList);
				
				while(true){
					data=buffer.readLine();//한줄
					if(data==null)break;
					//System.out.println(data);
					
					wordList.add(data);
				}	
				System.out.println("현재까지 wordList는"+wordList.size());
				//준비된 단어를 화면에 보여주기
				createWord();
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
	
	public void createWord(){
		for(int i=0;i<wordList.size();i++){
			String name=wordList.get(i);
			Word word = new Word(name, (i*75)+10, 100);
			words.add(word); //워드 객체 명단 만들기
		}	
	}
	
	//게임시작
	public void startGame(){
		if(thread==null){
			flag=true;//while문 작동하도록
			thread = new Thread(this);//Runnable의 run이 아닌 패널의 run을 움직이게함
			thread.start();
		}
	}
	
	//게임중지 or 계속
	public void pauseGame(){
		isDown=!isDown;
	}
	
	/* 게임종료시 필요한 행동강령!! (rollback처럼 처음으로 돌아가자)
		 1. wordList(단어들이 들어있는 배열) 비우기
		 2. words(Word인스턴스들이 들어있는 배열) 비우기
		 3. choice 초기화 (index=0)
		 4. flag=false
		 5. thread를 null로 다시 초기화
	 */
	public void stopGame(){
		wordList.removeAll(wordList); // 1. wordList(단어들이 들어있는 배열) 비우기
		words.removeAll(words); //2. words(Word인스턴스들이 들어있는 배열) 비우기
		choice.select(0); //첫번째 요소 강제 선택  3. choice 초기화 (index=0) 
		flag=false;  //while문 중지 목저  4. flag=false 
		thread=null;
	}
	
		
	//단어 내려오는 효과. 모든 단어들의 y값 증가시키고, p_center패널로 하여금 그림을 다시 그리게 유도함
	//public void down(){	} //객체 지향이라 필요없으니까 지우자

	@Override
	public void itemStateChanged(ItemEvent e) {
		//System.out.println("나 바꿨어?");
		getWord();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		
		if(obj==bt_start){
			startGame();
		}else if(obj==bt_pause){
			pauseGame();
		}else if(obj==bt_stop){
			stopGame();
		}
	}
	
	@Override
	public void run() {
		while(flag){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//down(); 객체 지향이라 필요없으니까 지우자
			//모든 단어들에 대해서 tick();
			
			if(isDown){
				for(int i=0;i<words.size();i++){
					words.get(i).tick();
				}
				//모든 단어들에 대해서 render();를 못부르니까 repaint()로 간접호출
				p_center.repaint();
			}
		}
	}
}

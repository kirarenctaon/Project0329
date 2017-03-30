package homework;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

public class CopyMain extends JFrame implements ActionListener, Runnable{
	JProgressBar bar;
	JButton bt_open, bt_save, bt_copy;
	JTextField t_open, t_save;	
	JFileChooser chooser;
	File file;//읽어 들일 파일, 복사 원본, 퍼센트 계산에도 사용됨
	Thread thread;//복사를 실행할 전용 쓰레드
	//메인 메서드는 우리가 알고 있는 그 실행부라 불리는 어플리케이션의 운영을 담당하는 역할을 수행한다. 
	//따라서 절대 무한루프나 대기상태에 빠지게 해서는 안된다. 
	
	long total; //원본 파일의 전체용량
	
	public CopyMain() {
		bar = new JProgressBar();
		bt_open = new JButton("열기");
		bt_save = new JButton("저장");
		bt_copy = new JButton("복사실행");
		
		t_open = new JTextField(35);
		t_save = new JTextField(35);
		chooser = new JFileChooser("c:/java_workspace/project0327/");
		
		bar.setPreferredSize(new Dimension(450, 50));
		bar.setString("0%");
		
		setLayout(new FlowLayout());
		
		add(bar);
		add(bt_open);
		add(t_open);
		add(bt_save);
		add(t_save);
		add(bt_copy);
		
		//버튼과 리스너 연결
		bt_open.addActionListener(this);
		bt_save.addActionListener(this);
		bt_copy.addActionListener(this);
				
		setSize(500, 200);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		bar.setStringPainted(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		/* 다중 else if 문, ()안에 판단조건 넣음
		switch(버튼){
			여는버튼이라면
			저장버튼이라면
		}*/
		Object obj=e.getSource();
		if(obj==bt_open){
			open();
		}else if(obj==bt_save){
			save();			
		}else if(obj==bt_copy){
			//메인이 직접 복사를 수행하지 말고 쓰레드에게 시키자
			//쓰레드 생성장게 Runnable 구현객체를 인수로 넣으면, Runnable 객체에서 재정한 run()메서드를 수행한다. 
			thread = new Thread(this); //즉, 여기서 this를 생략하면 Runnable의 run이 수행
			thread.start();
		}
	}

	public void open(){
		int result=chooser.showOpenDialog(this); //parent는 바깥쪽 컴포넌트, window를 말함
		
		if(result==JFileChooser.APPROVE_OPTION){
			file=chooser.getSelectedFile();
			t_open.setText(file.getAbsolutePath());
			total=file.length();
		}
	}
	
	public void save(){
		int result=chooser.showOpenDialog(this);
		
		if(result==JFileChooser.APPROVE_OPTION){
			File file=chooser.getSelectedFile();
			t_save.setText(file.getAbsolutePath());
		}
	}
	
	public void copy(){
		FileInputStream fis=null;
		FileOutputStream fos=null;
		
		try {
			fis=new FileInputStream(t_open.getText()); //빨대에 꽃음
			fos=new FileOutputStream(t_save.getText());//만들어질 파일경로 적음
			
			int data;	
			int count=0;
			
			while(true){
				data=fis.read(); //생성된 스트림을 통해 바이트기반의 데이터 읽기
				if(data==-1) break;
				count++;
				fos.write(data); //1byte 출력
				int v=(int)getPercent(count); //bar.setValue()에서 int값만 받으므로 강제형변환!!
				bar.setValue(v); //프로그래스바에 적용
				bar.setString(v+"%");
			}		
			JOptionPane.showMessageDialog(this, "복사완료");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{//데이터 흐름을 닫음
			if(fos!=null){
				try {
					fos.close();
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
		}//finally 
	}
	
	@Override
	public void run() {
		copy();
	}
	
	//현재 진행율 구하기 공식
	//진행율 = 100% * 현재 읽어들인 데이터 / 전체파일용량  --> 메서드화
	public long getPercent(int currentRead){
		return (100*currentRead)/total; //total은 long이고 currentRead는 int라서 long으로 자동형변환됨
	}
	
	public static void main(String[] args) {
		new CopyMain();
	}

}

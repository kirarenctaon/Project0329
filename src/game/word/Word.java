//게임에 등장할 대상 단어가 각각 y축의 값을 따로 갖고, 대량으로 만들어져야 하기 때문에 
//결국 재사용을 위한 코드집합이 클래스 .java로 가자

//객체별로 interval을 넣으면 비동기화되어 화면이 깜빡거리게 되니 게임당 쓰레드는 하나만 두자

//게임을 만든다는 건 1. 데이터(물리량)의 변화 2. 반영(다시그리기)
package game.word;

import java.awt.Graphics;

public class Word {
	String name;//이 객체가 담게될 단어!!
	int x;
	int y;
	int verX;
	int velY;//단어가 움직일 속도
	
	//이 단어가 태어날때 갖추어야할 초기화값
	public Word(String name, int x, int y) {
		this.name=name;
		this.x=x;
		this.y=y;
	}
	
	//이 객체에 반영될 데이터 변화코드
	public void tick(){
		y+=5;
	}
	
	//그 반영된 데이터를 이용하여 화면에 그리기
	public void render(Graphics g){//Graphics g는 컴포넌트의 g, 앞으로 자바를 하면서 나만의 객체를 그려지게 하려면 컴포넌트의 g를 가져야한다. 
		g.drawString(name, x, y); //객체지향이니까 그림은 패널이 그려주는게 아니라 스스로 그려야함
	}
	
}

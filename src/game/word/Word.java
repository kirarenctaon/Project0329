//���ӿ� ������ ��� �ܾ ���� y���� ���� ���� ����, �뷮���� ��������� �ϱ� ������ 
//�ᱹ ������ ���� �ڵ������� Ŭ���� .java�� ����

//��ü���� interval�� ������ �񵿱�ȭ�Ǿ� ȭ���� �����Ÿ��� �Ǵ� ���Ӵ� ������� �ϳ��� ����

//������ ����ٴ� �� 1. ������(������)�� ��ȭ 2. �ݿ�(�ٽñ׸���)
package game.word;

import java.awt.Graphics;

public class Word {
	String name;//�� ��ü�� ��Ե� �ܾ�!!
	int x;
	int y;
	int verX;
	int velY;//�ܾ ������ �ӵ�
	
	//�� �ܾ �¾�� ���߾���� �ʱ�ȭ��
	public Word(String name, int x, int y) {
		this.name=name;
		this.x=x;
		this.y=y;
	}
	
	//�� ��ü�� �ݿ��� ������ ��ȭ�ڵ�
	public void tick(){
		y+=5;
	}
	
	//�� �ݿ��� �����͸� �̿��Ͽ� ȭ�鿡 �׸���
	public void render(Graphics g){//Graphics g�� ������Ʈ�� g, ������ �ڹٸ� �ϸ鼭 ������ ��ü�� �׷����� �Ϸ��� ������Ʈ�� g�� �������Ѵ�. 
		g.drawString(name, x, y); //��ü�����̴ϱ� �׸��� �г��� �׷��ִ°� �ƴ϶� ������ �׷�����
	}
	
}

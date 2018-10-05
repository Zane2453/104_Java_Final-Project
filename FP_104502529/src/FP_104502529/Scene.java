package FP_104502529;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Scene extends JFrame implements KeyListener,ActionListener {

	Font font = new Font("OK", 1, 20);//�]�w�r���j�p
	
	private Timer timer1 = new Timer(1000,this);//�ĤH1�ʧ@���p�ɾ�
	private Timer timer2 = new Timer(1000,this);//�ĤH2�ʧ@���p�ɾ�
	private Timer timer3 = new Timer(1000,this);//�ĤH3�ʧ@���p�ɾ�
	private Timer btimer = new Timer(1000,this);//���u�z�����p�ɾ�
	private Timer rtimer = new Timer(1000,this);//���K�������p�ɾ�
	
	private JLabel[][] grounds = new JLabel[16][16];//�C������l
	
	private ImageIcon backgd;//�C���a������
	private ImageIcon player;//�D������
	private ImageIcon backwl;//�C���������
	private ImageIcon enemy;//�ĤH����
	private ImageIcon bomb;//���u����
	private ImageIcon fire;//�z������K����
	
	private int playerx;//�D����row�y��
	private int playery;//�D����column�y��
	private int bombx; //���u��row�y��
	private int bomby; //���u��column�y��
	private int [] wallx = new int [50];//�����row�y��
	private int [] wally = new int [50];//�����column�y��
	private int [] enemyx = new int [3];//�ĤH��row�y��
	private int [] enemyy = new int [3];//�ĤH��column�y��
	private int keyboardInputCode;
	private int [] direction = new int [3];//�ĤH���ʪ���V
	
	private boolean wall=false;//�D�����ʤ�V�O�_�����
	private boolean ewall=false;//�ĤH���ʤ�V���_�����
	private boolean havebomb=false;//�O�_�����u
	private boolean t1=false;//�Ǫ�1�O�_����
	private boolean t2=false;//�Ǫ�2�O�_����
	private boolean t3=false;//�Ǫ�3�O�_����
	
	public Scene() 
	{
		this.setTitle("Bomberman Jetters");
		this.setSize(800,800);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new GridLayout(16,16));
		
		ImageIcon backgd = new ImageIcon("ground.jpg");
		ImageIcon player = new ImageIcon("player.png");
		ImageIcon backwl = new ImageIcon("wall.jpg");
		ImageIcon enemy = new ImageIcon("enemy.png");
		ImageIcon bomb = new ImageIcon("bumb.png");
		ImageIcon fire = new ImageIcon("fire.jpg");
		
		Random ran=new Random();
		
		playerx = 0;
		playery = 0;
		
		enemyx[0] = ran.nextInt(15);//�ĤH1����m�]�w
		enemyy[0] = ran.nextInt(15);
		if(enemyx[0]==0&&enemyy[0]==0)
		{
			enemyx[0] = ran.nextInt(15);
			enemyy[0] = ran.nextInt(15);
		}
		
		enemyx[1] = ran.nextInt(15);//�ĤH2����m�]�w
		enemyy[1] = ran.nextInt(15);
		if(enemyx[1]==0&&enemyy[1]==0)
		{
			enemyx[1] = ran.nextInt(15);
			enemyy[1] = ran.nextInt(15);
		}
		
		enemyx[2] = ran.nextInt(15);//�ĤH3����m�]�w
		enemyy[2] = ran.nextInt(15);
		if(enemyx[2]==0&&enemyy[2]==0)
		{
			enemyx[2] = ran.nextInt(15);
			enemyy[2] = ran.nextInt(15);
		}
		
		for(int i=0;i<50;i++){//�]�w�����l��m
			wallx[i] = ran.nextInt(16);
			wally[i] = ran.nextInt(16);
			if(wallx[i]==0&&wally[i]==0)
			{
				wallx[i] = ran.nextInt(16);
				wally[i] = ran.nextInt(16);
			}
			
			if(wallx[i]==enemyx[0]&&wally[i]==enemyy[0])
			{
				wallx[i] = ran.nextInt(16);
				wally[i] = ran.nextInt(16);
			}
			
			if(wallx[i]==enemyx[1]&&wally[i]==enemyy[1])
			{
				wallx[i] = ran.nextInt(16);
				wally[i] = ran.nextInt(16);
			}
			
			if(wallx[i]==enemyx[2]&&wally[i]==enemyy[2])
			{
				wallx[i] = ran.nextInt(16);
				wally[i] = ran.nextInt(16);
			}
			
		}
		
		for(int i=0;i<16;i++)//�]�w������l���A
		{
			for(int j=0;j<16;j++)
			{
				if(i==playery && j==playerx)
				{
					grounds[i][j]=new JLabel();
					grounds[i][j].setIcon(player);
				}
				else
				{
					grounds[i][j]=new JLabel();
					grounds[i][j].setIcon(backgd);
				}
				for(int k=0;k<50;k++)
				{
					if(i==wally[k] && j==wallx[k])
					{
						grounds[i][j]=new JLabel();
						grounds[i][j].setIcon(backwl);
					}
				}
				
				if(i==enemyy[0] && j==enemyx[0])
				{
					grounds[i][j]=new JLabel();
					grounds[i][j].setIcon(enemy);
				}
				
				if(i==enemyy[1] && j==enemyx[1])
				{
					grounds[i][j]=new JLabel();
					grounds[i][j].setIcon(enemy);
				}
				
				if(i==enemyy[2] && j==enemyx[2])
				{
					grounds[i][j]=new JLabel();
					grounds[i][j].setIcon(enemy);
				}
				
				add(grounds[i][j]);
			}
		}
		
		timer1.start();
		timer2.start();
		timer3.start();
		this.addKeyListener(this);
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==timer1)
		{
			ImageIcon backgd = new ImageIcon("ground.jpg");
			ImageIcon player = new ImageIcon("player.png");
			ImageIcon backwl = new ImageIcon("wall.jpg");
			ImageIcon enemy = new ImageIcon("enemy.png");
			ImageIcon bomb = new ImageIcon("bumb.png");
			ImageIcon fire = new ImageIcon("fire.jpg");
			
			Random ran=new Random();
			
			direction[0]=ran.nextInt(3)+1;//���Ǫ�1�M�w�n���W�U���k���䲾��
			
			if(direction[0]==1)//�Ǫ�1�V�W���ʳ]�w
			{
				for(int j=0;j<50;j++)
				{
					if(enemyy[0]-1 == wally[j] && enemyx[0]==wallx[j])
						ewall=true;
				}
				if(ewall==true)
					grounds[enemyy[0]][enemyx[0]].setIcon(enemy);
				else
				{
					grounds[enemyy[0]][enemyx[0]].setIcon(backgd);
					if(enemyy[0]>0)
						enemyy[0]=enemyy[0]-1;
					grounds[enemyy[0]][enemyx[0]].setIcon(enemy);
				}
				ewall=false;
			}
			else if(direction[0]==2)//�Ǫ�1�V�U���ʳ]�w
			{
				for(int j=0;j<50;j++)
				{
					if(enemyy[0]+1 == wally[j] && enemyx[0] == wallx[j])
						ewall=true;
				}
				if(ewall==true)
					grounds[enemyy[0]][enemyx[0]].setIcon(enemy);
				else
				{
					grounds[enemyy[0]][enemyx[0]].setIcon(backgd);
					if(enemyy[0]<15)
						enemyy[0]=enemyy[0]+1;
					grounds[enemyy[0]][enemyx[0]].setIcon(enemy);
				}
				ewall=false;
			}
			else if(direction[0]==3)//�Ǫ�1�V�����ʳ]�w
			{
				for(int j=0;j<50;j++)
				{
					if(enemyy[0] == wally[j] && enemyx[0]-1==wallx[j])
						ewall=true;
				}
				if(ewall==true)
					grounds[enemyy[0]][enemyx[0]].setIcon(enemy);
				else
				{
					grounds[enemyy[0]][enemyx[0]].setIcon(backgd);
					if(enemyx[0]>0)
						enemyx[0]=enemyx[0]-1;
					grounds[enemyy[0]][enemyx[0]].setIcon(enemy);
				}
			}
			else if(direction[0]==4)//�Ǫ�1�V�k���ʳ]�w
			{
				for(int j=0;j<50;j++)
				{
					if(enemyy[0] == wally[j] && enemyx[0]+1==wallx[j])
						ewall=true;
				}
				if(ewall==true)
					grounds[enemyy[0]][enemyx[0]].setIcon(enemy);
				else
				{
					grounds[enemyy[0]][enemyx[0]].setIcon(backgd);
					if(enemyx[0]<15)
						enemyx[0]=enemyx[0]+1;
					grounds[enemyy[0]][enemyx[0]].setIcon(enemy);
				}
			}
			this.repaint();
		}
		
		else if(e.getSource()==timer2)
		{
			ImageIcon backgd = new ImageIcon("ground.jpg");
			ImageIcon player = new ImageIcon("player.png");
			ImageIcon backwl = new ImageIcon("wall.jpg");
			ImageIcon enemy = new ImageIcon("enemy.png");
			ImageIcon bomb = new ImageIcon("bumb.png");
			ImageIcon fire = new ImageIcon("fire.jpg");
			
			Random ran=new Random();
			
			direction[1]=ran.nextInt(3)+1;//���Ǫ�2�M�w�n���W�U���k���䲾��
			
			if(direction[1]==1)//�Ǫ�2�V�W���ʳ]�w
			{
				for(int j=0;j<50;j++)
				{
					if(enemyy[1]-1 == wally[j] && enemyx[1]==wallx[j])
						ewall=true;
				}
				if(ewall==true)
					grounds[enemyy[1]][enemyx[1]].setIcon(enemy);
				else
				{
					grounds[enemyy[1]][enemyx[1]].setIcon(backgd);
					if(enemyy[1]>0)
						enemyy[1]=enemyy[1]-1;
					grounds[enemyy[1]][enemyx[1]].setIcon(enemy);
				}
				ewall=false;
			}
			else if(direction[1]==2)//�Ǫ�2�V�U���ʳ]�w
			{
				for(int j=0;j<50;j++)
				{
					if(enemyy[1]+1 == wally[j] && enemyx[1] == wallx[j])
						ewall=true;
				}
				if(ewall==true)
					grounds[enemyy[1]][enemyx[1]].setIcon(enemy);
				else
				{
					grounds[enemyy[1]][enemyx[1]].setIcon(backgd);
					if(enemyy[1]<15)
						enemyy[1]=enemyy[1]+1;
					grounds[enemyy[1]][enemyx[1]].setIcon(enemy);
				}
				ewall=false;
			}
			else if(direction[1]==3)//�Ǫ�2�V�����ʳ]�w
			{
				for(int j=0;j<50;j++)
				{
					if(enemyy[1] == wally[j] && enemyx[1]-1==wallx[j])
						ewall=true;
				}
				if(ewall==true)
					grounds[enemyy[1]][enemyx[1]].setIcon(enemy);
				else
				{
					grounds[enemyy[1]][enemyx[1]].setIcon(backgd);
					if(enemyx[1]>0)
						enemyx[1]=enemyx[1]-1;
					grounds[enemyy[1]][enemyx[1]].setIcon(enemy);
				}
			}
			else if(direction[1]==4)//�Ǫ�2�V�k���ʳ]�w
			{
				for(int j=0;j<50;j++)
				{
					if(enemyy[1] == wally[j] && enemyx[1]+1==wallx[j])
						ewall=true;
				}
				if(ewall==true)
					grounds[enemyy[1]][enemyx[1]].setIcon(enemy);
				else
				{
					grounds[enemyy[1]][enemyx[1]].setIcon(backgd);
					if(enemyx[1]<15)
						enemyx[1]=enemyx[1]+1;
					grounds[enemyy[1]][enemyx[1]].setIcon(enemy);
				}
			}
			this.repaint();
		}
		
		else if(e.getSource()==timer3)
		{
			ImageIcon backgd = new ImageIcon("ground.jpg");
			ImageIcon player = new ImageIcon("player.png");
			ImageIcon backwl = new ImageIcon("wall.jpg");
			ImageIcon enemy = new ImageIcon("enemy.png");
			ImageIcon bomb = new ImageIcon("bumb.png");
			ImageIcon fire = new ImageIcon("fire.jpg");
			
			Random ran=new Random();
			
			direction[2]=ran.nextInt(3)+1;//���Ǫ�3�M�w�n���W�U���k���䲾��
			
			if(direction[2]==1)//�Ǫ�3�V�W���ʳ]�w
			{
				for(int j=0;j<50;j++)
				{
					if(enemyy[2]-1 == wally[j] && enemyx[2]==wallx[j])
						ewall=true;
				}
				if(ewall==true)
					grounds[enemyy[2]][enemyx[2]].setIcon(enemy);
				else
				{
					grounds[enemyy[2]][enemyx[2]].setIcon(backgd);
					if(enemyy[2]>0)
						enemyy[2]=enemyy[2]-1;
					grounds[enemyy[2]][enemyx[2]].setIcon(enemy);
				}
				ewall=false;
			}
			else if(direction[2]==2)//�Ǫ�3�V�U���ʳ]�w
			{
				for(int j=0;j<50;j++)
				{
					if(enemyy[2]+1 == wally[j] && enemyx[2] == wallx[j])
						ewall=true;
				}
				if(ewall==true)
					grounds[enemyy[2]][enemyx[2]].setIcon(enemy);
				else
				{
					grounds[enemyy[2]][enemyx[2]].setIcon(backgd);
					if(enemyy[2]<15)
						enemyy[2]=enemyy[2]+1;
					grounds[enemyy[2]][enemyx[2]].setIcon(enemy);
				}
				ewall=false;
			}
			else if(direction[2]==3)//�Ǫ�3�V�����ʳ]�w
			{
				for(int j=0;j<50;j++)
				{
					if(enemyy[2] == wally[j] && enemyx[2]-1==wallx[j])
						ewall=true;
				}
				if(ewall==true)
					grounds[enemyy[2]][enemyx[2]].setIcon(enemy);
				else
				{
					grounds[enemyy[2]][enemyx[2]].setIcon(backgd);
					if(enemyx[2]>0)
						enemyx[2]=enemyx[2]-1;
					grounds[enemyy[2]][enemyx[2]].setIcon(enemy);
				}
			}
			else if(direction[2]==4)//�Ǫ�3�V�k���ʳ]�w
			{
				for(int j=0;j<50;j++)
				{
					if(enemyy[2] == wally[j] && enemyx[2]+1==wallx[j])
						ewall=true;
				}
				if(ewall==true)
					grounds[enemyy[2]][enemyx[2]].setIcon(enemy);
				else
				{
					grounds[enemyy[2]][enemyx[2]].setIcon(backgd);
					if(enemyx[2]<15)
						enemyx[2]=enemyx[2]+1;
					grounds[enemyy[2]][enemyx[2]].setIcon(enemy);
				}
			}
			this.repaint();
		}
		
		else if(e.getSource()==btimer)
		{
			ImageIcon backgd = new ImageIcon("ground.jpg");
			ImageIcon player = new ImageIcon("player.png");
			ImageIcon backwl = new ImageIcon("wall.jpg");
			ImageIcon enemy = new ImageIcon("enemy.jpg");
			ImageIcon bomb = new ImageIcon("bumb.png");
			ImageIcon fire = new ImageIcon("fire.jpg");
			
			if((bomby==playery&&bombx==playerx)||(bomby+1==playery&&bombx==playerx)||(bomby-1==playery&&bombx==playerx)||(bomby==playery&&bombx+1==playerx)||(bomby==playery&&bombx-1==playerx))
			{
				if(bomby>0&&bomby<15&&bombx>0&&bombx<15)
				{
					timer1.stop();
					timer2.stop();
					timer3.stop();
					grounds[bomby][bombx].setIcon(fire);
					grounds[bomby+1][bombx].setIcon(fire);
					grounds[bomby-1][bombx].setIcon(fire);
					grounds[bomby][bombx+1].setIcon(fire);
					grounds[bomby][bombx-1].setIcon(fire);
					JDialog dialog = new JDialog();
					dialog.setUndecorated(true);
					JLabel label = new JLabel( new ImageIcon("lose.jpg") );//�[�J�鱼����
					dialog.add( label );
					dialog.pack();
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);
				}
				else if(bomby==0&&bombx>0&&bombx<15)
				{
					timer1.stop();
					timer2.stop();
					timer3.stop();
					grounds[bomby][bombx].setIcon(fire);
					grounds[bomby+1][bombx].setIcon(fire);
					grounds[bomby][bombx+1].setIcon(fire);
					grounds[bomby][bombx-1].setIcon(fire);
					JDialog dialog = new JDialog();
					dialog.setUndecorated(true);
					JLabel label = new JLabel( new ImageIcon("lose.jpg") );//�[�J�鱼����
					dialog.add( label );
					dialog.pack();
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);
				}
				else if(bomby==15&&bombx>0&&bombx<15)
				{
					timer1.stop();
					timer2.stop();
					timer3.stop();
					grounds[bomby][bombx].setIcon(fire);
					grounds[bomby-1][bombx].setIcon(fire);
					grounds[bomby][bombx+1].setIcon(fire);
					grounds[bomby][bombx-1].setIcon(fire);
					JDialog dialog = new JDialog();
					dialog.setUndecorated(true);
					JLabel label = new JLabel( new ImageIcon("lose.jpg") );//�[�J�鱼����
					dialog.add( label );
					dialog.pack();
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);
				}
				else if(bombx==0&&bomby>0&&bomby<15)
				{
					timer1.stop();
					timer2.stop();
					timer3.stop();
					grounds[bomby][bombx].setIcon(fire);
					grounds[bomby+1][bombx].setIcon(fire);
					grounds[bomby-1][bombx].setIcon(fire);
					grounds[bomby][bombx+1].setIcon(fire);
					JDialog dialog = new JDialog();
					dialog.setUndecorated(true);
					JLabel label = new JLabel( new ImageIcon("lose.jpg") );//�[�J�鱼����
					dialog.add( label );
					dialog.pack();
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);
				}
				else if(bombx==15&&bomby>0&&bomby<15)
				{
					timer1.stop();
					timer2.stop();
					timer3.stop();
					grounds[bomby][bombx].setIcon(fire);
					grounds[bomby+1][bombx].setIcon(fire);
					grounds[bomby-1][bombx].setIcon(fire);
					grounds[bomby][bombx-1].setIcon(fire);
					JDialog dialog = new JDialog();
					dialog.setUndecorated(true);
					JLabel label = new JLabel( new ImageIcon("lose.jpg") );//�[�J�鱼����
					dialog.add( label );
					dialog.pack();
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);
				}
				else if(bombx==0&&bomby==0)
				{
					timer1.stop();
					timer2.stop();
					timer3.stop();
					grounds[bomby][bombx].setIcon(fire);
					grounds[bomby+1][bombx].setIcon(fire);
					grounds[bomby][bombx+1].setIcon(fire);
					JDialog dialog = new JDialog();
					dialog.setUndecorated(true);
					JLabel label = new JLabel( new ImageIcon("lose.jpg") );//�[�J�鱼����
					dialog.add( label );
					dialog.pack();
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);
				}
				else if(bombx==15&&bomby==0)
				{
					timer1.stop();
					timer2.stop();
					timer3.stop();
					grounds[bomby][bombx].setIcon(fire);
					grounds[bomby+1][bombx].setIcon(fire);
					grounds[bomby][bombx-1].setIcon(fire);
					JDialog dialog = new JDialog();
					dialog.setUndecorated(true);
					JLabel label = new JLabel( new ImageIcon("lose.jpg") );//�[�J�鱼����
					dialog.add( label );
					dialog.pack();
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);
				}
				else if(bombx==0&&bomby==15)
				{
					timer1.stop();
					timer2.stop();
					timer3.stop();
					grounds[bomby][bombx].setIcon(fire);
					grounds[bomby-1][bombx].setIcon(fire);
					grounds[bomby][bombx+1].setIcon(fire);
					JDialog dialog = new JDialog();
					dialog.setUndecorated(true);
					JLabel label = new JLabel( new ImageIcon("lose.jpg") );//�[�J�鱼����
					dialog.add( label );
					dialog.pack();
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);
				}
				else if(bombx==15&&bomby==15)
				{
					timer1.stop();
					timer2.stop();
					timer3.stop();
					grounds[bomby][bombx].setIcon(fire);
					grounds[bomby-1][bombx].setIcon(fire);
					grounds[bomby][bombx-1].setIcon(fire);
					JDialog dialog = new JDialog();
					dialog.setUndecorated(true);
					JLabel label = new JLabel( new ImageIcon("lose.jpg") );//�[�J�鱼����
					dialog.add( label );
					dialog.pack();
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);
				}
			}
			
			if((bomby==enemyy[0]&&bombx==enemyx[0])||(bomby+1==enemyy[0]&&bombx==enemyx[0])||(bomby-1==enemyy[0]&&bombx==enemyx[0])||(bomby==enemyy[0]&&bombx+1==enemyx[0])||(bomby==enemyy[0]&&bombx-1==enemyx[0]))
			{
				if(bomby>0&&bomby<15&&bombx>0&&bombx<15)
				{
					timer1.stop();
					t1=true;
					grounds[bomby][bombx].setIcon(fire);
					grounds[bomby+1][bombx].setIcon(fire);
					grounds[bomby-1][bombx].setIcon(fire);
					grounds[bomby][bombx+1].setIcon(fire);
					grounds[bomby][bombx-1].setIcon(fire);
					enemyx[0]=0;
					enemyy[0]=0;
				}
				else if(bomby==0&&bombx>0&&bombx<15)
				{
					timer1.stop();
					t1=true;
					grounds[bomby][bombx].setIcon(fire);
					grounds[bomby+1][bombx].setIcon(fire);
					grounds[bomby][bombx+1].setIcon(fire);
					grounds[bomby][bombx-1].setIcon(fire);
					enemyx[0]=0;
					enemyy[0]=0;
				}
				else if(bomby==15&&bombx>0&&bombx<15)
				{
					timer1.stop();
					t1=true;
					grounds[bomby][bombx].setIcon(fire);
					grounds[bomby-1][bombx].setIcon(fire);
					grounds[bomby][bombx+1].setIcon(fire);
					grounds[bomby][bombx-1].setIcon(fire);
					enemyx[0]=0;
					enemyy[0]=0;
				}
				else if(bombx==0&&bomby>0&&bomby<15)
				{
					timer1.stop();
					t1=true;
					grounds[bomby][bombx].setIcon(fire);
					grounds[bomby+1][bombx].setIcon(fire);
					grounds[bomby-1][bombx].setIcon(fire);
					grounds[bomby][bombx+1].setIcon(fire);
					enemyx[0]=0;
					enemyy[0]=0;
				}
				else if(bombx==15&&bomby>0&&bomby<15)
				{
					timer1.stop();
					t1=true;
					grounds[bomby][bombx].setIcon(fire);
					grounds[bomby+1][bombx].setIcon(fire);
					grounds[bomby-1][bombx].setIcon(fire);
					grounds[bomby][bombx-1].setIcon(fire);
					enemyx[0]=0;
					enemyy[0]=0;
				}
				else if(bombx==0&&bomby==0)
				{
					timer1.stop();
					t1=true;
					grounds[bomby][bombx].setIcon(fire);
					grounds[bomby+1][bombx].setIcon(fire);
					grounds[bomby][bombx+1].setIcon(fire);
					enemyx[0]=0;
					enemyy[0]=0;
				}
				else if(bombx==15&&bomby==0)
				{
					timer1.stop();
					t1=true;
					grounds[bomby][bombx].setIcon(fire);
					grounds[bomby+1][bombx].setIcon(fire);
					grounds[bomby][bombx-1].setIcon(fire);
					enemyx[0]=0;
					enemyy[0]=0;
				}
				else if(bombx==0&&bomby==15)
				{
					timer1.stop();
					t1=true;
					grounds[bomby][bombx].setIcon(fire);
					grounds[bomby-1][bombx].setIcon(fire);
					grounds[bomby][bombx+1].setIcon(fire);
					enemyx[0]=0;
					enemyy[0]=0;
				}
				else if(bombx==15&&bomby==15)
				{
					timer1.stop();
					t1=true;
					grounds[bomby][bombx].setIcon(fire);
					grounds[bomby-1][bombx].setIcon(fire);
					grounds[bomby][bombx-1].setIcon(fire);
					enemyx[0]=0;
					enemyy[0]=0;
				}
			}
			
			if((bomby==enemyy[1]&&bombx==enemyx[1])||(bomby+1==enemyy[1]&&bombx==enemyx[1])||(bomby-1==enemyy[1]&&bombx==enemyx[1])||(bomby==enemyy[1]&&bombx+1==enemyx[1])||(bomby==enemyy[1]&&bombx-1==enemyx[1]))
			{
				if(bomby>0&&bomby<15&&bombx>0&&bombx<15)
				{
					timer2.stop();
					t2=true;
					grounds[bomby][bombx].setIcon(fire);
					grounds[bomby+1][bombx].setIcon(fire);
					grounds[bomby-1][bombx].setIcon(fire);
					grounds[bomby][bombx+1].setIcon(fire);
					grounds[bomby][bombx-1].setIcon(fire);
					enemyx[1]=0;
					enemyy[1]=0;
				}
				else if(bomby==0&&bombx>0&&bombx<15)
				{
					timer2.stop();
					t2=true;
					grounds[bomby][bombx].setIcon(fire);
					grounds[bomby+1][bombx].setIcon(fire);
					grounds[bomby][bombx+1].setIcon(fire);
					grounds[bomby][bombx-1].setIcon(fire);
					enemyx[1]=0;
					enemyy[1]=0;
				}
				else if(bomby==15&&bombx>0&&bombx<15)
				{
					timer2.stop();
					t2=true;
					grounds[bomby][bombx].setIcon(fire);
					grounds[bomby-1][bombx].setIcon(fire);
					grounds[bomby][bombx+1].setIcon(fire);
					grounds[bomby][bombx-1].setIcon(fire);
					enemyx[1]=0;
					enemyy[1]=0;
				}
				else if(bombx==0&&bomby>0&&bomby<15)
				{
					timer2.stop();
					t2=true;
					grounds[bomby][bombx].setIcon(fire);
					grounds[bomby+1][bombx].setIcon(fire);
					grounds[bomby-1][bombx].setIcon(fire);
					grounds[bomby][bombx+1].setIcon(fire);
					enemyx[1]=0;
					enemyy[1]=0;
				}
				else if(bombx==15&&bomby>0&&bomby<15)
				{
					timer2.stop();
					t2=true;
					grounds[bomby][bombx].setIcon(fire);
					grounds[bomby+1][bombx].setIcon(fire);
					grounds[bomby-1][bombx].setIcon(fire);
					grounds[bomby][bombx-1].setIcon(fire);
					enemyx[1]=0;
					enemyy[1]=0;
				}
				else if(bombx==0&&bomby==0)
				{
					timer2.stop();
					t2=true;
					grounds[bomby][bombx].setIcon(fire);
					grounds[bomby+1][bombx].setIcon(fire);
					grounds[bomby][bombx+1].setIcon(fire);
					enemyx[1]=0;
					enemyy[1]=0;
				}
				else if(bombx==15&&bomby==0)
				{
					timer2.stop();
					t2=true;
					grounds[bomby][bombx].setIcon(fire);
					grounds[bomby+1][bombx].setIcon(fire);
					grounds[bomby][bombx-1].setIcon(fire);
					enemyx[1]=0;
					enemyy[1]=0;
				}
				else if(bombx==0&&bomby==15)
				{
					timer2.stop();
					t2=true;
					grounds[bomby][bombx].setIcon(fire);
					grounds[bomby-1][bombx].setIcon(fire);
					grounds[bomby][bombx+1].setIcon(fire);
					enemyx[1]=0;
					enemyy[1]=0;
				}
				else if(bombx==15&&bomby==15)
				{
					timer2.stop();
					t2=true;
					grounds[bomby][bombx].setIcon(fire);
					grounds[bomby-1][bombx].setIcon(fire);
					grounds[bomby][bombx-1].setIcon(fire);
					enemyx[1]=0;
					enemyy[1]=0;
				}
			}
			
			if((bomby==enemyy[2]&&bombx==enemyx[2])||(bomby+1==enemyy[2]&&bombx==enemyx[2])||(bomby-1==enemyy[2]&&bombx==enemyx[2])||(bomby==enemyy[2]&&bombx+1==enemyx[2])||(bomby==enemyy[2]&&bombx-1==enemyx[2]))
			{
				if(bomby>0&&bomby<15&&bombx>0&&bombx<15)
				{
					timer3.stop();
					t3=true;
					grounds[bomby][bombx].setIcon(fire);
					grounds[bomby+1][bombx].setIcon(fire);
					grounds[bomby-1][bombx].setIcon(fire);
					grounds[bomby][bombx+1].setIcon(fire);
					grounds[bomby][bombx-1].setIcon(fire);
					enemyx[2]=0;
					enemyy[2]=0;
				}
				else if(bomby==0&&bombx>0&&bombx<15)
				{
					timer3.stop();
					t3=true;
					grounds[bomby][bombx].setIcon(fire);
					grounds[bomby+1][bombx].setIcon(fire);
					grounds[bomby][bombx+1].setIcon(fire);
					grounds[bomby][bombx-1].setIcon(fire);
					enemyx[2]=0;
					enemyy[2]=0;
				}
				else if(bomby==15&&bombx>0&&bombx<15)
				{
					timer3.stop();
					t3=true;
					grounds[bomby][bombx].setIcon(fire);
					grounds[bomby-1][bombx].setIcon(fire);
					grounds[bomby][bombx+1].setIcon(fire);
					grounds[bomby][bombx-1].setIcon(fire);
					enemyx[2]=0;
					enemyy[2]=0;
				}
				else if(bombx==0&&bomby>0&&bomby<15)
				{
					timer3.stop();
					t3=true;
					grounds[bomby][bombx].setIcon(fire);
					grounds[bomby+1][bombx].setIcon(fire);
					grounds[bomby-1][bombx].setIcon(fire);
					grounds[bomby][bombx+1].setIcon(fire);
					enemyx[2]=0;
					enemyy[2]=0;
				}
				else if(bombx==15&&bomby>0&&bomby<15)
				{
					timer3.stop();
					t3=true;
					grounds[bomby][bombx].setIcon(fire);
					grounds[bomby+1][bombx].setIcon(fire);
					grounds[bomby-1][bombx].setIcon(fire);
					grounds[bomby][bombx-1].setIcon(fire);
					enemyx[2]=0;
					enemyy[2]=0;
				}
				else if(bombx==0&&bomby==0)
				{
					timer3.stop();
					t3=true;
					grounds[bomby][bombx].setIcon(fire);
					grounds[bomby+1][bombx].setIcon(fire);
					grounds[bomby][bombx+1].setIcon(fire);
					enemyx[2]=0;
					enemyy[2]=0;
				}
				else if(bombx==15&&bomby==0)
				{
					timer3.stop();
					t3=true;
					grounds[bomby][bombx].setIcon(fire);
					grounds[bomby+1][bombx].setIcon(fire);
					grounds[bomby][bombx-1].setIcon(fire);
					enemyx[2]=0;
					enemyy[2]=0;
				}
				else if(bombx==0&&bomby==15)
				{
					timer3.stop();
					t3=true;
					grounds[bomby][bombx].setIcon(fire);
					grounds[bomby-1][bombx].setIcon(fire);
					grounds[bomby][bombx+1].setIcon(fire);
					enemyx[2]=0;
					enemyy[2]=0;
				}
				else if(bombx==15&&bomby==15)
				{
					timer3.stop();
					t3=true;
					grounds[bomby][bombx].setIcon(fire);
					grounds[bomby-1][bombx].setIcon(fire);
					grounds[bomby][bombx-1].setIcon(fire);
					enemyx[2]=0;
					enemyy[2]=0;
				}
			}
			
			if(t1==true&&t2==true&&t3==true)
			{
				JDialog dialog = new JDialog();
				dialog.setUndecorated(true);
				JLabel label = new JLabel( new ImageIcon("win.jpg") );//�[�JĹ����
				dialog.add( label );
				dialog.pack();
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
			}
			
			if(bomby>0&&bomby<15&&bombx>0&&bombx<15)
			{
				grounds[bomby][bombx].setIcon(fire);
				grounds[bomby+1][bombx].setIcon(fire);
				grounds[bomby-1][bombx].setIcon(fire);
				grounds[bomby][bombx+1].setIcon(fire);
				grounds[bomby][bombx-1].setIcon(fire);
				for(int i=0;i<50;i++)
				{
					if((bomby==wally[i]&&bombx==wallx[i])||(bomby+1==wally[i]&&bombx==wallx[i])||(bomby-1==wally[i]&&bombx==wallx[i])||(bomby==wally[i]&&bombx+1==wallx[i])||(bomby==wally[i]&&bombx-1==wallx[i]))
					{
						grounds[wally[i]][wallx[i]].setIcon(backwl);
					}
				}
			}
			else if(bomby==0&&bombx>0&&bombx<15)
			{
				grounds[bomby][bombx].setIcon(fire);
				grounds[bomby+1][bombx].setIcon(fire);
				grounds[bomby][bombx+1].setIcon(fire);
				grounds[bomby][bombx-1].setIcon(fire);
				for(int i=0;i<50;i++)
				{
					if((bomby==wally[i]&&bombx==wallx[i])||(bomby+1==wally[i]&&bombx==wallx[i])||(bomby-1==wally[i]&&bombx==wallx[i])||(bomby==wally[i]&&bombx+1==wallx[i])||(bomby==wally[i]&&bombx-1==wallx[i]))
					{
						grounds[wally[i]][wallx[i]].setIcon(backwl);
					}
				}
			}
			else if(bomby==15&&bombx>0&&bombx<15)
			{
				grounds[bomby][bombx].setIcon(fire);
				grounds[bomby-1][bombx].setIcon(fire);
				grounds[bomby][bombx+1].setIcon(fire);
				grounds[bomby][bombx-1].setIcon(fire);
				for(int i=0;i<50;i++)
				{
					if((bomby==wally[i]&&bombx==wallx[i])||(bomby+1==wally[i]&&bombx==wallx[i])||(bomby-1==wally[i]&&bombx==wallx[i])||(bomby==wally[i]&&bombx+1==wallx[i])||(bomby==wally[i]&&bombx-1==wallx[i]))
					{
						grounds[wally[i]][wallx[i]].setIcon(backwl);
					}
				}
			}
			else if(bombx==0&&bomby>0&&bomby<15)
			{
				grounds[bomby][bombx].setIcon(fire);
				grounds[bomby+1][bombx].setIcon(fire);
				grounds[bomby-1][bombx].setIcon(fire);
				grounds[bomby][bombx+1].setIcon(fire);
				for(int i=0;i<50;i++)
				{
					if((bomby==wally[i]&&bombx==wallx[i])||(bomby+1==wally[i]&&bombx==wallx[i])||(bomby-1==wally[i]&&bombx==wallx[i])||(bomby==wally[i]&&bombx+1==wallx[i])||(bomby==wally[i]&&bombx-1==wallx[i]))
					{
						grounds[wally[i]][wallx[i]].setIcon(backwl);
					}
				}
			}
			else if(bombx==15&&bomby>0&&bomby<15)
			{
				grounds[bomby][bombx].setIcon(fire);
				grounds[bomby+1][bombx].setIcon(fire);
				grounds[bomby-1][bombx].setIcon(fire);
				grounds[bomby][bombx-1].setIcon(fire);
				for(int i=0;i<50;i++)
				{
					if((bomby==wally[i]&&bombx==wallx[i])||(bomby+1==wally[i]&&bombx==wallx[i])||(bomby-1==wally[i]&&bombx==wallx[i])||(bomby==wally[i]&&bombx+1==wallx[i])||(bomby==wally[i]&&bombx-1==wallx[i]))
					{
						grounds[wally[i]][wallx[i]].setIcon(backwl);
					}
				}
			}
			else if(bombx==0&&bomby==0)
			{
				grounds[bomby][bombx].setIcon(fire);
				grounds[bomby+1][bombx].setIcon(fire);
				grounds[bomby][bombx+1].setIcon(fire);
				for(int i=0;i<50;i++)
				{
					if((bomby==wally[i]&&bombx==wallx[i])||(bomby+1==wally[i]&&bombx==wallx[i])||(bomby-1==wally[i]&&bombx==wallx[i])||(bomby==wally[i]&&bombx+1==wallx[i])||(bomby==wally[i]&&bombx-1==wallx[i]))
					{
						grounds[wally[i]][wallx[i]].setIcon(backwl);
					}
				}
			}
			else if(bombx==15&&bomby==0)
			{
				grounds[bomby][bombx].setIcon(fire);
				grounds[bomby+1][bombx].setIcon(fire);
				grounds[bomby][bombx-1].setIcon(fire);
				for(int i=0;i<50;i++)
				{
					if((bomby==wally[i]&&bombx==wallx[i])||(bomby+1==wally[i]&&bombx==wallx[i])||(bomby-1==wally[i]&&bombx==wallx[i])||(bomby==wally[i]&&bombx+1==wallx[i])||(bomby==wally[i]&&bombx-1==wallx[i]))
					{
						grounds[wally[i]][wallx[i]].setIcon(backwl);
					}
				}
			}
			else if(bombx==0&&bomby==15)
			{
				grounds[bomby][bombx].setIcon(fire);
				grounds[bomby-1][bombx].setIcon(fire);
				grounds[bomby][bombx+1].setIcon(fire);
				for(int i=0;i<50;i++)
				{
					if((bomby==wally[i]&&bombx==wallx[i])||(bomby+1==wally[i]&&bombx==wallx[i])||(bomby-1==wally[i]&&bombx==wallx[i])||(bomby==wally[i]&&bombx+1==wallx[i])||(bomby==wally[i]&&bombx-1==wallx[i]))
					{
						grounds[wally[i]][wallx[i]].setIcon(backwl);
					}
				}
			}
			else if(bombx==15&&bomby==15)
			{
				grounds[bomby][bombx].setIcon(fire);
				grounds[bomby-1][bombx].setIcon(fire);
				grounds[bomby][bombx-1].setIcon(fire);
				for(int i=0;i<50;i++)
				{
					if((bomby==wally[i]&&bombx==wallx[i])||(bomby+1==wally[i]&&bombx==wallx[i])||(bomby-1==wally[i]&&bombx==wallx[i])||(bomby==wally[i]&&bombx+1==wallx[i])||(bomby==wally[i]&&bombx-1==wallx[i]))
					{
						grounds[wally[i]][wallx[i]].setIcon(backwl);
					}
				}
			}
			btimer.stop();
			rtimer.start();
		}
		else if(e.getSource()==rtimer)
		{
			ImageIcon backgd = new ImageIcon("ground.jpg");
			ImageIcon player = new ImageIcon("player.png");
			ImageIcon backwl = new ImageIcon("wall.jpg");
			ImageIcon enemy = new ImageIcon("enemy.png");
			ImageIcon bomb = new ImageIcon("bumb.png");
			ImageIcon fire = new ImageIcon("fire.jpg");
			if(bomby>0&&bomby<15&&bombx>0&&bombx<15)
			{
				grounds[bomby][bombx].setIcon(backgd);
				grounds[bomby+1][bombx].setIcon(backgd);
				grounds[bomby-1][bombx].setIcon(backgd);
				grounds[bomby][bombx+1].setIcon(backgd);
				grounds[bomby][bombx-1].setIcon(backgd);
				for(int i=0;i<50;i++)
				{
					if((bomby==wally[i]&&bombx==wallx[i])||(bomby+1==wally[i]&&bombx==wallx[i])||(bomby-1==wally[i]&&bombx==wallx[i])||(bomby==wally[i]&&bombx+1==wallx[i])||(bomby==wally[i]&&bombx-1==wallx[i]))
					{
						grounds[wally[i]][wallx[i]].setIcon(backwl);
					}
				}
			}
			else if(bomby==0&&bombx>0&&bombx<15)
			{
				grounds[bomby][bombx].setIcon(backgd);
				grounds[bomby+1][bombx].setIcon(backgd);
				grounds[bomby][bombx+1].setIcon(backgd);
				grounds[bomby][bombx-1].setIcon(backgd);
				for(int i=0;i<50;i++)
				{
					if((bomby==wally[i]&&bombx==wallx[i])||(bomby+1==wally[i]&&bombx==wallx[i])||(bomby-1==wally[i]&&bombx==wallx[i])||(bomby==wally[i]&&bombx+1==wallx[i])||(bomby==wally[i]&&bombx-1==wallx[i]))
					{
						grounds[wally[i]][wallx[i]].setIcon(backwl);
					}
				}
			}
			else if(bomby==15&&bombx>0&&bombx<15)
			{
				grounds[bomby][bombx].setIcon(backgd);
				grounds[bomby-1][bombx].setIcon(backgd);
				grounds[bomby][bombx+1].setIcon(backgd);
				grounds[bomby][bombx-1].setIcon(backgd);
				for(int i=0;i<50;i++)
				{
					if((bomby==wally[i]&&bombx==wallx[i])||(bomby+1==wally[i]&&bombx==wallx[i])||(bomby-1==wally[i]&&bombx==wallx[i])||(bomby==wally[i]&&bombx+1==wallx[i])||(bomby==wally[i]&&bombx-1==wallx[i]))
					{
						grounds[wally[i]][wallx[i]].setIcon(backwl);
					}
				}
			}
			else if(bombx==0&&bomby>0&&bomby<15)
			{
				grounds[bomby][bombx].setIcon(backgd);
				grounds[bomby+1][bombx].setIcon(backgd);
				grounds[bomby-1][bombx].setIcon(backgd);
				grounds[bomby][bombx+1].setIcon(backgd);
				for(int i=0;i<50;i++)
				{
					if((bomby==wally[i]&&bombx==wallx[i])||(bomby+1==wally[i]&&bombx==wallx[i])||(bomby-1==wally[i]&&bombx==wallx[i])||(bomby==wally[i]&&bombx+1==wallx[i])||(bomby==wally[i]&&bombx-1==wallx[i]))
					{
						grounds[wally[i]][wallx[i]].setIcon(backwl);
					}
				}
			}
			else if(bombx==15&&bomby>0&&bomby<15)
			{
				grounds[bomby][bombx].setIcon(backgd);
				grounds[bomby+1][bombx].setIcon(backgd);
				grounds[bomby-1][bombx].setIcon(backgd);
				grounds[bomby][bombx-1].setIcon(backgd);
				for(int i=0;i<50;i++)
				{
					if((bomby==wally[i]&&bombx==wallx[i])||(bomby+1==wally[i]&&bombx==wallx[i])||(bomby-1==wally[i]&&bombx==wallx[i])||(bomby==wally[i]&&bombx+1==wallx[i])||(bomby==wally[i]&&bombx-1==wallx[i]))
					{
						grounds[wally[i]][wallx[i]].setIcon(backwl);
					}
				}
			}
			else if(bombx==0&&bomby==0)
			{
				grounds[bomby][bombx].setIcon(backgd);
				grounds[bomby+1][bombx].setIcon(backgd);
				grounds[bomby][bombx+1].setIcon(backgd);
				for(int i=0;i<50;i++)
				{
					if((bomby==wally[i]&&bombx==wallx[i])||(bomby+1==wally[i]&&bombx==wallx[i])||(bomby-1==wally[i]&&bombx==wallx[i])||(bomby==wally[i]&&bombx+1==wallx[i])||(bomby==wally[i]&&bombx-1==wallx[i]))
					{
						grounds[wally[i]][wallx[i]].setIcon(backwl);
					}
				}
			}
			else if(bombx==15&&bomby==0)
			{
				grounds[bomby][bombx].setIcon(backgd);
				grounds[bomby+1][bombx].setIcon(backgd);
				grounds[bomby][bombx-1].setIcon(backgd);
				for(int i=0;i<50;i++)
				{
					if((bomby==wally[i]&&bombx==wallx[i])||(bomby+1==wally[i]&&bombx==wallx[i])||(bomby-1==wally[i]&&bombx==wallx[i])||(bomby==wally[i]&&bombx+1==wallx[i])||(bomby==wally[i]&&bombx-1==wallx[i]))
					{
						grounds[wally[i]][wallx[i]].setIcon(backwl);
					}
				}
			}
			else if(bombx==0&&bomby==15)
			{
				grounds[bomby][bombx].setIcon(backgd);
				grounds[bomby-1][bombx].setIcon(backgd);
				grounds[bomby][bombx+1].setIcon(backgd);
				for(int i=0;i<50;i++)
				{
					if((bomby==wally[i]&&bombx==wallx[i])||(bomby+1==wally[i]&&bombx==wallx[i])||(bomby-1==wally[i]&&bombx==wallx[i])||(bomby==wally[i]&&bombx+1==wallx[i])||(bomby==wally[i]&&bombx-1==wallx[i]))
					{
						grounds[wally[i]][wallx[i]].setIcon(backwl);
					}
				}
			}
			else if(bombx==15&&bomby==15)
			{
				grounds[bomby][bombx].setIcon(backgd);
				grounds[bomby-1][bombx].setIcon(backgd);
				grounds[bomby][bombx-1].setIcon(backgd);
				for(int i=0;i<50;i++)
				{
					if((bomby==wally[i]&&bombx==wallx[i])||(bomby+1==wally[i]&&bombx==wallx[i])||(bomby-1==wally[i]&&bombx==wallx[i])||(bomby==wally[i]&&bombx+1==wallx[i])||(bomby==wally[i]&&bombx-1==wallx[i]))
					{
						grounds[wally[i]][wallx[i]].setIcon(backwl);
					}
				}
			}
			rtimer.stop();
			bombx=0;
			bomby=0;
		}
	}
	@Override
	public void keyTyped(KeyEvent e) 
	{
	}
	@Override
	public void keyPressed(KeyEvent e)
	{
		keyboardInputCode = e.getKeyCode();//�i�H����L����
		ImageIcon backgd = new ImageIcon("ground.jpg");
		ImageIcon player = new ImageIcon("player.png");
		ImageIcon backwl = new ImageIcon("wall.jpg");
		ImageIcon enemy = new ImageIcon("enemy.png");
		ImageIcon bomb = new ImageIcon("bumb.png");
		ImageIcon fire = new ImageIcon("fire.jpg");
		
		if(keyboardInputCode == KeyEvent.VK_UP)//�D���V�W���ʳ]�w
		{
			if(havebomb==true)
			{
				for(int i=0;i<50;i++)
				{
					if(playery-1 == wally[i] && playerx==wallx[i])
						wall=true;
				}
				if(wall==true)
					grounds[playery][playerx].setIcon(player);
				else
				{
					grounds[playery][playerx].setIcon(bomb);
					if(playery>0)
						playery=playery-1;
					grounds[playery][playerx].setIcon(player);
				}
				wall=false;
				havebomb=false;
			}
			else
			{
				for(int i=0;i<50;i++)
				{
					if(playery-1 == wally[i] && playerx==wallx[i])
						wall=true;
				}
				if(wall==true)
					grounds[playery][playerx].setIcon(player);
				else
				{
					grounds[playery][playerx].setIcon(backgd);
					if(playery>0)
						playery=playery-1;
					grounds[playery][playerx].setIcon(player);
				}
				wall=false;
			}
		}
		
		if(keyboardInputCode == KeyEvent.VK_DOWN)//�D���V�U���ʳ]�w
		{
			if(havebomb == true)
			{
				for(int i=0;i<50;i++)
				{
					if(playery+1 == wally[i] && playerx==wallx[i])
						wall=true;
				}
				if(wall==true)
				{
					grounds[playery][playerx].setIcon(bomb);
					grounds[playery][playerx].setIcon(player);
				}
				else
				{
					grounds[playery][playerx].setIcon(bomb);
					if(playery<15)
						playery=playery+1;
					grounds[playery][playerx].setIcon(player);
				}
				wall=false;
				havebomb=false;
			}
			else
			{
				for(int i=0;i<50;i++)
				{
					if(playery+1 == wally[i] && playerx==wallx[i])
						wall=true;
				}
				if(wall==true)
					grounds[playery][playerx].setIcon(player);
				else
				{
					grounds[playery][playerx].setIcon(backgd);
					if(playery<15)
						playery=playery+1;
					grounds[playery][playerx].setIcon(player);
				}
				wall=false;
			}
		}
		
		if(keyboardInputCode == KeyEvent.VK_LEFT)//�D���V�����ʳ]�w
		{
			if(havebomb==true)
			{
				for(int i=0;i<50;i++)
				{
					if(playerx-1 == wallx[i] && playery==wally[i])
						wall=true;
				}
				if(wall==true)
					grounds[playery][playerx].setIcon(player);
				else
				{
					grounds[playery][playerx].setIcon(bomb);
					if(playerx>0)
						playerx=playerx-1;
					grounds[playery][playerx].setIcon(player);
				}			
				wall=false;
				havebomb=false;
			}
			else
			{
				for(int i=0;i<50;i++)
				{
					if(playerx-1 == wallx[i] && playery==wally[i])
						wall=true;
				}
				if(wall==true)
					grounds[playery][playerx].setIcon(player);
				else
				{
					grounds[playery][playerx].setIcon(backgd);
					if(playerx>0)
						playerx=playerx-1;
					grounds[playery][playerx].setIcon(player);
				}			
				wall=false;
			}
		}
		
		if(keyboardInputCode == KeyEvent.VK_RIGHT)//�D���V�k���ʳ]�w
		{
			if(havebomb==true)
			{
				for(int i=0;i<50;i++){
					if(playerx+1 == wallx[i] && playery==wally[i])
						wall=true;
				}
				if(wall==true)
					grounds[playery][playerx].setIcon(player);
				else
				{
					grounds[playery][playerx].setIcon(bomb);
					if(playerx<15)
						playerx=playerx+1;
					grounds[playery][playerx].setIcon(player);
				}				
				wall=false;
				havebomb=false;
			}
			else
			{
				for(int i=0;i<50;i++)
				{
					if(playerx+1 == wallx[i] && playery==wally[i])
						wall=true;
				}
				if(wall==true)
					grounds[playery][playerx].setIcon(player);
				else
				{
					grounds[playery][playerx].setIcon(backgd);
					if(playerx<15)
						playerx=playerx+1;
					grounds[playery][playerx].setIcon(player);
				}				
				wall=false;
			}
		}
		if(keyboardInputCode == KeyEvent.VK_SPACE)//�D���תť���񬵼u�]�w
		{
			bombx=playerx;
			bomby=playery;
			btimer.start();
			grounds[playery][playerx].setIcon(bomb);
			grounds[playery][playerx].setIcon(player);
			havebomb = true;
		}
		
		if(enemyx[0]==playerx&&enemyy[0]==playery)
		{
			JDialog dialog = new JDialog();
			dialog.setUndecorated(true);
			JLabel label = new JLabel( new ImageIcon("lose.jpg") );//�[�J�鱼����
			dialog.add( label );
			dialog.pack();
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
			timer1.stop();
			timer2.stop();
			timer3.stop();
		}
		
		if(enemyx[1]==playerx&&enemyy[1]==playery)
		{
			JDialog dialog = new JDialog();
			dialog.setUndecorated(true);
			JLabel label = new JLabel( new ImageIcon("lose.jpg") );//�[�J�鱼����
			dialog.add( label );
			dialog.pack();
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
			timer1.stop();
			timer2.stop();
			timer3.stop();
		}
		
		if(enemyx[2]==playerx&&enemyy[2]==playery)
		{
			JDialog dialog = new JDialog();
			dialog.setUndecorated(true);
			JLabel label = new JLabel( new ImageIcon("lose.jpg") );//�[�J�鱼����
			dialog.add( label );
			dialog.pack();
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
			timer1.stop();
			timer2.stop();
			timer3.stop();
		}
		
		this.repaint();
	}
	@Override
	public void keyReleased(KeyEvent e)
	{
	}

}
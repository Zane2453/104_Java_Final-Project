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

	Font font = new Font("OK", 1, 20);//設定字型大小
	
	private Timer timer1 = new Timer(1000,this);//敵人1動作的計時器
	private Timer timer2 = new Timer(1000,this);//敵人2動作的計時器
	private Timer timer3 = new Timer(1000,this);//敵人3動作的計時器
	private Timer btimer = new Timer(1000,this);//炸彈爆炸的計時器
	private Timer rtimer = new Timer(1000,this);//火焰熄滅的計時器
	
	private JLabel[][] grounds = new JLabel[16][16];//遊戲的格子
	
	private ImageIcon backgd;//遊戲地面的圖
	private ImageIcon player;//主角的圖
	private ImageIcon backwl;//遊戲牆壁的圖
	private ImageIcon enemy;//敵人的圖
	private ImageIcon bomb;//炸彈的圖
	private ImageIcon fire;//爆炸後火焰的圖
	
	private int playerx;//主角的row座標
	private int playery;//主角的column座標
	private int bombx; //炸彈的row座標
	private int bomby; //炸彈的column座標
	private int [] wallx = new int [50];//牆壁的row座標
	private int [] wally = new int [50];//牆壁的column座標
	private int [] enemyx = new int [3];//敵人的row座標
	private int [] enemyy = new int [3];//敵人的column座標
	private int keyboardInputCode;
	private int [] direction = new int [3];//敵人移動的方向
	
	private boolean wall=false;//主角移動方向是否有牆壁
	private boolean ewall=false;//敵人移動方向受否有牆壁
	private boolean havebomb=false;//是否有炸彈
	private boolean t1=false;//怪物1是否死掉
	private boolean t2=false;//怪物2是否死掉
	private boolean t3=false;//怪物3是否死掉
	
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
		
		enemyx[0] = ran.nextInt(15);//敵人1的位置設定
		enemyy[0] = ran.nextInt(15);
		if(enemyx[0]==0&&enemyy[0]==0)
		{
			enemyx[0] = ran.nextInt(15);
			enemyy[0] = ran.nextInt(15);
		}
		
		enemyx[1] = ran.nextInt(15);//敵人2的位置設定
		enemyy[1] = ran.nextInt(15);
		if(enemyx[1]==0&&enemyy[1]==0)
		{
			enemyx[1] = ran.nextInt(15);
			enemyy[1] = ran.nextInt(15);
		}
		
		enemyx[2] = ran.nextInt(15);//敵人3的位置設定
		enemyy[2] = ran.nextInt(15);
		if(enemyx[2]==0&&enemyy[2]==0)
		{
			enemyx[2] = ran.nextInt(15);
			enemyy[2] = ran.nextInt(15);
		}
		
		for(int i=0;i<50;i++){//設定牆壁初始位置
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
		
		for(int i=0;i<16;i++)//設定頁面初始狀態
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
			
			direction[0]=ran.nextInt(3)+1;//讓怪物1決定要往上下左右哪邊移動
			
			if(direction[0]==1)//怪物1向上移動設定
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
			else if(direction[0]==2)//怪物1向下移動設定
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
			else if(direction[0]==3)//怪物1向左移動設定
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
			else if(direction[0]==4)//怪物1向右移動設定
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
			
			direction[1]=ran.nextInt(3)+1;//讓怪物2決定要往上下左右哪邊移動
			
			if(direction[1]==1)//怪物2向上移動設定
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
			else if(direction[1]==2)//怪物2向下移動設定
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
			else if(direction[1]==3)//怪物2向左移動設定
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
			else if(direction[1]==4)//怪物2向右移動設定
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
			
			direction[2]=ran.nextInt(3)+1;//讓怪物3決定要往上下左右哪邊移動
			
			if(direction[2]==1)//怪物3向上移動設定
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
			else if(direction[2]==2)//怪物3向下移動設定
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
			else if(direction[2]==3)//怪物3向左移動設定
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
			else if(direction[2]==4)//怪物3向右移動設定
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
					JLabel label = new JLabel( new ImageIcon("lose.jpg") );//加入輸掉的圖
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
					JLabel label = new JLabel( new ImageIcon("lose.jpg") );//加入輸掉的圖
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
					JLabel label = new JLabel( new ImageIcon("lose.jpg") );//加入輸掉的圖
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
					JLabel label = new JLabel( new ImageIcon("lose.jpg") );//加入輸掉的圖
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
					JLabel label = new JLabel( new ImageIcon("lose.jpg") );//加入輸掉的圖
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
					JLabel label = new JLabel( new ImageIcon("lose.jpg") );//加入輸掉的圖
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
					JLabel label = new JLabel( new ImageIcon("lose.jpg") );//加入輸掉的圖
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
					JLabel label = new JLabel( new ImageIcon("lose.jpg") );//加入輸掉的圖
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
					JLabel label = new JLabel( new ImageIcon("lose.jpg") );//加入輸掉的圖
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
				JLabel label = new JLabel( new ImageIcon("win.jpg") );//加入贏的圖
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
		keyboardInputCode = e.getKeyCode();//可以用鍵盤控制
		ImageIcon backgd = new ImageIcon("ground.jpg");
		ImageIcon player = new ImageIcon("player.png");
		ImageIcon backwl = new ImageIcon("wall.jpg");
		ImageIcon enemy = new ImageIcon("enemy.png");
		ImageIcon bomb = new ImageIcon("bumb.png");
		ImageIcon fire = new ImageIcon("fire.jpg");
		
		if(keyboardInputCode == KeyEvent.VK_UP)//主角向上移動設定
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
		
		if(keyboardInputCode == KeyEvent.VK_DOWN)//主角向下移動設定
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
		
		if(keyboardInputCode == KeyEvent.VK_LEFT)//主角向左移動設定
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
		
		if(keyboardInputCode == KeyEvent.VK_RIGHT)//主角向右移動設定
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
		if(keyboardInputCode == KeyEvent.VK_SPACE)//主角案空白鍵放炸彈設定
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
			JLabel label = new JLabel( new ImageIcon("lose.jpg") );//加入輸掉的圖
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
			JLabel label = new JLabel( new ImageIcon("lose.jpg") );//加入輸掉的圖
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
			JLabel label = new JLabel( new ImageIcon("lose.jpg") );//加入輸掉的圖
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
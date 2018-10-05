package FP_104502529;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Start extends JFrame implements ActionListener
{
	Font font = new Font("OK", 1, 20);//設定字型大小
	
	private ImageIcon backgd;//遊戲地面的圖
	private ImageIcon player;//主角的圖
	private ImageIcon backwl;//遊戲牆壁的圖
	private ImageIcon enemy;//敵人的圖
	private ImageIcon bomb;//炸彈的圖
	private ImageIcon fire;//爆炸後火焰的圖
	private ImageIcon backst;//開始的背景

	JPanel back = new JPanel();//背景
	JButton start = new JButton("START");//開始鍵
	JButton soundon = new JButton("SOUND ON");//音樂開始鍵
	JButton soundoff = new JButton("SOUND OFF");//音樂結束鍵
	JButton rule = new JButton("RULE");//規則鍵
	JButton change = new JButton("CHANGE MUSIC");//音樂改變鍵
	
	private JLabel background;//開始的背景放置的位置
	
	File Hall = new File("Nightcore-Hall of Fame.WAV");
	File Stereo = new File("Nightcore-Stereo heart.WAV");
	music startmusic = new music(Hall);
	boolean musicboolean = true;
	boolean musicchange = false;
	
	public Start()
	{
		this.setTitle("Bomberman Jetters");
		this.setSize(591,397);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ImageIcon backgd = new ImageIcon("ground.jpg");
		ImageIcon player = new ImageIcon("player.png");
		ImageIcon backwl = new ImageIcon("wall.jpg");
		ImageIcon enemy = new ImageIcon("enemy.jpg");
		ImageIcon bomb = new ImageIcon("bumb.png");
		ImageIcon fire = new ImageIcon("fire.jpg");

		back.setLayout(new BorderLayout());
		add(back);
		
		start.setBounds(30,175,110,45);
		start.setFont(font);
		start.addActionListener(this);
		
		rule.setBounds(30,220,110,45);
		rule.setFont(font);
		rule.addActionListener(this);
		
		soundon.setBounds(30,265,160,45);
		soundon.setFont(font);
		soundon.addActionListener(this);
		
		soundoff.setBounds(30,310,160,45);
		soundoff.setFont(font);
		soundoff.addActionListener(this);
		
		change.setBounds(190,310,190,45);
		change.setFont(font);
		change.addActionListener(this);
		
		ImageIcon backst = new ImageIcon("punch.jpg");
		background = new JLabel(backst);
		back.add(background);
		
		back.add(start);
		back.add(rule);
		back.add(soundon);
		back.add(soundoff);
		back.add(change);
		back.add(background);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==start)//按開始鍵的結果
			{
			Scene Game = new Scene();
			Game.setVisible(true);
			this.dispose();
		}
		
		else if(e.getSource()==rule)//按規則鍵的結果
		{
			ImageIcon backgd = new ImageIcon("ground.jpg");
			ImageIcon player = new ImageIcon("player.png");
			ImageIcon backwl = new ImageIcon("wall.jpg");
			ImageIcon enemy = new ImageIcon("enemy.jpg");
			ImageIcon bomb = new ImageIcon("bumb.png");
			ImageIcon fire = new ImageIcon("fire.jpg");
			JDialog dialog = new JDialog();
			dialog.setUndecorated(true);
			JLabel label = new JLabel( new ImageIcon("rule.jpg") );//加入規則的圖
			dialog.add( label );
			dialog.pack();
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
			dialog.addMouseListener(new MouseListener() 
			{
				@Override
				public void mouseClicked(MouseEvent e)
				{
				}
				@Override
				public void mousePressed(MouseEvent e)
				{
					dialog.dispose();//滑鼠按一下消失
				}
				@Override
				public void mouseReleased(MouseEvent e) 
				{
				}
				@Override
				public void mouseEntered(MouseEvent e)
				{
				}
				@Override
				public void mouseExited(MouseEvent e) 
				{
				}
			}
			);
		}
		
		else if(e.getSource()==soundon)//按音樂開始鍵的結果
		{
			if(musicboolean==true)
			{
				musicboolean = false;
				startmusic.play();
			}
		}
		
		else if(e.getSource()==soundoff)//按音樂結束鍵的結果
		{
			if(musicboolean==false)
			{
				musicboolean = true;
				startmusic.stop();
			}
		}
		
		else if(e.getSource()==change)//按音樂改變鍵的結果
		{
			if(musicboolean==false)
			{
				musicboolean = true;
				startmusic.stop();
			}
			if(musicchange==false)
			{
				musicchange=true;
				startmusic = new music(Stereo);
			}
			else if(musicchange==true)
			{
				musicchange=false;
				startmusic = new music(Hall);
			}
		}
	}

}
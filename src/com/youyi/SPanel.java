package com.youyi;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SPanel extends JPanel implements KeyListener, ActionListener {

	ImageIcon title;
	ImageIcon body;
	ImageIcon up;
	ImageIcon down;
	ImageIcon left;
	ImageIcon right;
	ImageIcon food;

	private int len;
	public int score;
	int[] snakex = new int[750];
	int[] snakey = new int[750];

	String fx = "R"; // R,L,U,D
	boolean isStarted = false;
	boolean isFailed = false;
	Timer timer = new Timer(100, this);

	int foodx;
	int foody;
	Random rand = new Random();

	public SPanel() {
		init();
		this.setFocusable(true);
		this.addKeyListener(this);
		timer.start();
	}

	public void init() {
		loadImages();
		len = 3;
		snakex[0] = 100;
		snakey[0] = 100;
		snakex[1] = 75;
		snakey[1] = 100;
		snakex[2] = 50;
		snakey[2] = 100;

		foodx = 25 + 25 * rand.nextInt(34);
		foody = 75 + 25 * rand.nextInt(24);
		score = 0;
	}

	private void loadImages() {
		InputStream is;

		try {
			is = getClass().getClassLoader().getResourceAsStream("images/title.jpg");
			title = new ImageIcon(ImageIO.read(is));
			is = getClass().getClassLoader().getResourceAsStream("images/body.png");
			body = new ImageIcon(ImageIO.read(is));
			is = getClass().getClassLoader().getResourceAsStream("images/up.png");
			up = new ImageIcon(ImageIO.read(is));
			is = getClass().getClassLoader().getResourceAsStream("images/down.png");
			down = new ImageIcon(ImageIO.read(is));
			is = getClass().getClassLoader().getResourceAsStream("images/left.png");
			left = new ImageIcon(ImageIO.read(is));
			is = getClass().getClassLoader().getResourceAsStream("images/right.png");
			right = new ImageIcon(ImageIO.read(is));
			is = getClass().getClassLoader().getResourceAsStream("images/food.png");
			food = new ImageIcon(ImageIO.read(is));
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		this.setBackground(Color.white);
		title.paintIcon(this, g, 25, 11); // 插入顶部图片

		g.fillRect(25, 75, 850, 600);
		g.setColor(Color.white);
		g.drawString("Len : " + len, 750, 35);
		g.drawString("Score" + score, 750, 50);

		switch (fx) {
		case "R":
			right.paintIcon(this, g, snakex[0], snakey[0]);
			break;
		case "L":
			left.paintIcon(this, g, snakex[0], snakey[0]);
			break;
		case "U":
			up.paintIcon(this, g, snakex[0], snakey[0]);
			break;
		case "D":
			down.paintIcon(this, g, snakex[0], snakey[0]);
			break;
		}

		for (int i = 1; i < len; i++) {
			body.paintIcon(this, g, snakex[i], snakey[i]);
		}

		food.paintIcon(this, g, foodx, foody);
		if (!isStarted) {
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD, 40));
			g.drawString("Press Space to Start", 300, 300);
		}
		if (isFailed) {
			g.setColor(Color.red);
			g.setFont(new Font("arial", Font.BOLD, 40));
			g.drawString("Failed: Press Space to Restart", 300, 300);
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_SPACE) {
			if (isFailed) {
				isFailed = false;
				init();
			} else {
				isStarted = !isStarted;
			}
			repaint();
		} else if (keyCode == KeyEvent.VK_LEFT) {
			if (fx != "R")
				fx = "L";
		} else if (keyCode == KeyEvent.VK_RIGHT) {
			if (fx != "L")
				fx = "R";
		} else if (keyCode == KeyEvent.VK_UP) {
			if (fx != "D")
				fx = "U";
		} else if (keyCode == KeyEvent.VK_DOWN) {
			if (fx != "U")
				fx = "D";
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (isStarted && !isFailed) {
			for (int i = len - 1; i > 0; i--) {
				snakex[i] = snakex[i - 1];
				snakey[i] = snakey[i - 1];
			} // 控制身体部分移动

			switch (fx) {
			case "R":
				snakex[0] = snakex[0] + 25;
				if (snakex[0] > 850)
					snakex[0] = 25;
				break;
			case "L":
				snakex[0] = snakex[0] - 25;
				if (snakex[0] < 25)
					snakex[0] = 850;
				break;
			case "U":
				snakey[0] = snakey[0] - 25;
				if (snakey[0] < 75)
					snakey[0] = 650;
				break;
			case "D":
				snakey[0] = snakey[0] + 25;
				if (snakey[0] > 670)
					snakey[0] = 75;
				break;
			}
			if (snakex[0] == foodx && snakey[0] == foody) {
				len++;
				score += 10;
				foodx = 25 + 25 * rand.nextInt(34);
				foody = 75 + 25 * rand.nextInt(24);
			}

			for (int i = 1; i < len; i++) {
				if (snakex[i] == snakex[0] && snakey[i] == snakey[0]) {
					isFailed = true;
				}

			}
			repaint();
		}

		timer.start();
	}
}

package game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 500;
	public static final int HEIGHT = WIDTH;
	public static final int SCALE = 1;
	public static final String NAME = "PLANSA";
	public static final int DEFAULT_COLOR_CODE = 0x55ffffff;
	
	public boolean running = false;
	public int tickCount = 0;

	private int red = 0, green = 170, blue = 170;
	private int dred = 1, dgreen = 1, dblue = -1;
	
	private JFrame frame;
	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT,
			BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer())
			.getData();
	private int[] drawed = new int[pixels.length];

	private int deltaRadius;
	private double period;
	private double speed;
	private int colorDraw;
	private int colorBackground;
	private boolean rainbowDraw;
	private boolean rainbowBackground;
	
	public Game(int deltaRadius, double period, double speed, int colorDraw, int colorBackground, boolean rainbowDraw, boolean rainbowBackground) {
		
		this.deltaRadius = deltaRadius;
		this.period = period;
		this.speed = speed;
		this.colorDraw = colorDraw;
		this.colorBackground = colorBackground;
		this.rainbowDraw = rainbowDraw;
		this.rainbowBackground = rainbowBackground;
		
		setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		frame = new JFrame(NAME);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		frame.add(this, BorderLayout.CENTER);
		frame.pack();

		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
	}

	public synchronized void start() {
		frame.setVisible(true);
		running = true;
		new Thread(this).start();
	}

	public synchronized void stop() {
		running = false;
	}

	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / 60D;

		int ticks = 0;
		int frames = 0;

		long lasTimer = System.currentTimeMillis();
		double delta = 0;
		
		for (int i = 0; i < drawed.length; i++)
			drawed[i] = DEFAULT_COLOR_CODE;

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;

			while (delta >= 1) {
				ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}

			try {
				Thread.sleep(2);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (shouldRender) {
				frames++;
				render();
			}

			if (System.currentTimeMillis() - lasTimer >= 1000) {
				lasTimer += 1000;
				//System.out.println(ticks + " ticks , " + frames + " frames");
				frames = 0;
				ticks = 0;
			}

		}
	}

	public void incrementColor(Integer color, Integer dcolor) {
		color += dcolor;
		if (color == 255) dcolor = -1;
		if (color == 0) dcolor = 1;
	}
	
	public void draw(long tickCount) {
		
		
		double a = ((double)tickCount / 2 * (Math.PI / 180));
		// a e in [0, 2*PI]
		
		int r_max = (WIDTH / 2 - deltaRadius - 5);
		
		double r = r_max + Math.sin(a*period) * deltaRadius;
		// r e in [0, (WIDTH / 2 - 5)]
		
		int x = (int)(r * Math.cos(a*speed));
		int y = (int)(r * Math.sin(a*speed));
		
		int real_x = x + WIDTH / 2;
		int real_y = y + HEIGHT / 2;
		int i = real_x + real_y * WIDTH;
		
		if (rainbowDraw)
			drawed[i] = red * 256 * 256 + green * 256 + blue;
		else
			drawed[i] = colorDraw;
		
		for (int j = 0; j < pixels.length; j++)
			if (rainbowBackground)
				pixels[j] = red * 256 * 256 + green * 256 + blue;
			else
				pixels[j] = colorBackground;
		
		for (int j = 0; j < pixels.length; j++)
			if (drawed[j] != DEFAULT_COLOR_CODE)
				pixels[j] = drawed[j];
	}
	
	public void tick() {
		tickCount++;
		
		red += dred;
		if (red == 255) dred = -1;
		if (red == 0) dred = 1;
		
		green += dgreen;
		if (green == 255) dgreen = -1;
		if (green == 0) dgreen = 1;
		
		blue += dblue;
		if (blue == 255) dblue = -1;
		if (blue == 0) dblue = 1;
		
		draw(tickCount);
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

		g.setColor(Color.BLACK);
		g.drawRect(0, 0, getWidth(), getHeight());

		g.dispose();
		bs.show();
	}
}

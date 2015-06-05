package game;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class DataFrame extends JFrame {

	public static final int WIDTH = 500;
	public static final int HEIGHT = WIDTH;
	public static final String NAME = "Parametrii desenului";
	
	private JLabel lblDeltaRadius = new JLabel("Grosime (numar intreg, intre 0 si "+(Game.WIDTH/2 - 5)+"): ");
	private JTextField tfdDeltaRadius = new JTextField();
	private JLabel lblPeriod = new JLabel("Perioda (numar real): ");
	private JTextField tfdPeriod = new JTextField();
	private JLabel lblSpeed = new JLabel("Viteza (numar real): ");
	private JTextField tfdSpeed = new JTextField();
	private JLabel lblColorDraw = new JLabel("Culoare desen (numar hexa de 6 cifre): ");
	private JTextField tfdColorDraw = new JTextField();
	private JLabel lblRainbowDraw = new JLabel("Sau stil curcubeu: ");
	private JCheckBox chkRainbowDraw = new JCheckBox();
	private JLabel lblColorBackground = new JLabel("Culoare fundal (numar hexa de 6 cifre): ");
	private JTextField tfdColorBackground = new JTextField();
	private JLabel lblRainbowBackground = new JLabel("Sau stil curcubeu: ");
	private JCheckBox chkRainbowBackground = new JCheckBox();
	private JButton btnStart = new JButton("Start");
	
	
	private Game game;
	
	public DataFrame() {
		
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setMaximumSize(new Dimension(WIDTH, HEIGHT));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		setTitle(NAME);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);

		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		
		lblDeltaRadius.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPeriod.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSpeed.setHorizontalAlignment(SwingConstants.RIGHT);
		lblColorDraw.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRainbowDraw.setHorizontalAlignment(SwingConstants.RIGHT);
		lblColorBackground.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRainbowBackground.setHorizontalAlignment(SwingConstants.RIGHT);
		
		lblDeltaRadius.setBounds(50, 80, 250, 30);
		tfdDeltaRadius.setBounds(310, 80, 140, 30);
		lblPeriod.setBounds(50, 125, 250, 30);
		tfdPeriod.setBounds(310, 125, 140, 30);
		lblSpeed.setBounds(50, 170, 250, 30);
		tfdSpeed.setBounds(310, 170, 140, 30);
		lblColorDraw.setBounds(50, 215, 250, 30);
		tfdColorDraw.setBounds(310, 215, 140, 30);
		lblRainbowDraw.setBounds(50, 260, 250, 30);
		chkRainbowDraw.setBounds(310, 260, 140, 30);
		lblColorBackground.setBounds(50, 305, 250, 30);
		tfdColorBackground.setBounds(310, 305, 140, 30);
		lblRainbowBackground.setBounds(50, 350, 250, 30);
		chkRainbowBackground.setBounds(310, 350, 140, 30);
		btnStart.setBounds(100, 420, 300, 30);
		
		add(lblDeltaRadius);
		add(tfdDeltaRadius);
		add(lblPeriod);
		add(tfdPeriod);
		add(lblSpeed);
		add(tfdSpeed);
		add(lblColorDraw);
		add(tfdColorDraw);
		add(lblRainbowDraw);
		add(chkRainbowDraw);
		add(lblColorBackground);
		add(tfdColorBackground);
		add(lblRainbowBackground);
		add(chkRainbowBackground);
		add(btnStart);
		
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int deltaRadius = Integer.parseInt(tfdDeltaRadius.getText());
					double period = Double.parseDouble(tfdPeriod.getText());
					double speed = Double.parseDouble(tfdSpeed.getText());
					int colorDraw = Integer.parseInt(tfdColorDraw.getText(), 16);
					int colorBackground = Integer.parseInt(tfdColorBackground.getText(), 16);
					boolean rainbowDraw = chkRainbowDraw.isSelected();
					boolean rainbowBackground = chkRainbowBackground.isSelected();
					
					new Game(deltaRadius, period, speed, colorDraw, colorBackground, rainbowDraw, rainbowBackground).start();
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
	
	public static void main(String[] args) {
		
		new DataFrame();
	}
}

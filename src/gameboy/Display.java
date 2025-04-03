package gameboy;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Display extends JFrame {
	private static final int PIXEL_SIZE = 3;
	private static final int CANVAS_WIDTH = 160 * PIXEL_SIZE;
	private static final int CANVAS_HEIGHT = 144 * PIXEL_SIZE;
	GameBoy gameboy;
	Thread thread;

	public Display() {
		gameboy = new GameBoy(this);
		setTitle("Game Boy Emulator");
		setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");

		JMenuItem chooseRomItem = new JMenuItem("Choose Rom File");
		JMenuItem exitItem = new JMenuItem("Exit");

		chooseRomItem.addActionListener(e -> chooseRomFile());
		exitItem.addActionListener(e -> System.exit(0));
		fileMenu.add(chooseRomItem);
		fileMenu.add(exitItem);
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);

		setResizable(false);
		setFocusable(true);
		add(gameboy);
		addKeyListener(gameboy);
		pack();
		setLocationRelativeTo(null);
	}

	private void chooseRomFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Game Boy ROMs", "gb"));

		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			gameboy.gameTitle = selectedFile.getName();
			if (thread != null && thread.isAlive()) {
				gameboy.running = false;
				thread.stop();
				try {
					thread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			gameboy.chooseRomFile(selectedFile);
			gameboy.running = true;
			thread = new Thread(gameboy);
			thread.start();
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Display().setVisible(true);
		});
	}
}

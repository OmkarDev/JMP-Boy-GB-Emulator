package gameboy;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JPanel;

import bus.Bus;
import cartridge.Cartridge;
import cpu.CPU;
import dma.DMA;
import joypad.Joypad;
import ppu.PPU;
//import ppu.LCD;
import timer.Timer;

public class GameBoy extends JPanel implements Runnable, KeyListener {

	private static final long serialVersionUID = 1L;
	public static final int PIXEL_SIZE = 4;
	public static final int WIDTH = 160 * PIXEL_SIZE;
	public static final int HEIGHT = 144 * PIXEL_SIZE;

	CPU cpu;
	public Cartridge cartridge;
	Timer timer;
	public static String serialOut = "";

	public PPU ppu;
	public Joypad joypad;
	public Bus bus;
	public DMA dma;
	public static String gameTitle = "";
	private Display display;

	public GameBoy(Display display) {
		this.display = display;
		bus = new Bus();
		cpu = new CPU(bus);
		joypad = new Joypad(cpu);
		dma = new DMA(bus);
//		cartridge = new Cartridge("/cpu_instrs.gb", cpu);
//		cartridge = new Cartridge("/mem_timing.gb", cpu);
//		cartridge = new Cartridge("/mem_timing_2.gb", cpu);// TODO REQUIRES LCD & DOES NOT HAVE SERIAL OUTPUT
//		cartridge = new Cartridge("/instr_timing.gb", cpu);
//		cartridge = new Cartridge("/interrupt_time.gb", cpu);// TODO REQUIRED APU & DOES NOT HAVE ANY SERIAL OUTPUT!!!!!!!!!!!!!!
//		cartridge = new Cartridge("/halt_bug.gb", cpu);// TODO DOES NOT HAVE ANY SERIAL OUTPUT!!!!!!!!!!!!!!
//		cartridge = new Cartridge("/01-read_timing_2.gb", cpu);
//		cartridge = new Cartridge("/roms/Tetris.gb", cpu);
//		cartridge = new Cartridge("/roms/tellinglys.gb", cpu);
//		cartridge = new Cartridge("/roms/Dr. Mario (World).gb", cpu);
//		cartridge = new Cartridge("/roms/PPU_Tests/m2_win_en_toggle.gb", cpu);
//		cartridge = new Cartridge("/roms/acceptance/add_sp_e_timing.gb", cpu);
//		cartridge = new Cartridge("/roms/Pokemon Red.gb", cpu);
//		cartridge = new Cartridge("/roms/Legend of Zelda, The Link's Awakening.gb", cpu);
//		cartridge = new Cartridge("/roms/Prehistorik Man (USA, Europe).gb", cpu);
//		cartridge = new Cartridge("/roms/Super Mario Land 2 - 6 Golden Coins (USA, Europe).gb", cpu);
//		cartridge = new Cartridge("/roms/Super Mario Land (World).gb", cpu);
//		cartridge = new Cartridge("/roms/acceptance/interrupts/ie_push.gb", cpu);
//		cartridge = new Cartridge("/roms/acceptance/oam_dma/basic.gb", cpu);
//		cartridge = new Cartridge("/oh.gb", cpu);
//		cartridge = new Cartridge("/pocket.gb", cpu);
//		cartridge = new Cartridge("/20y.gb", cpu);
//		cartridge = new Cartridge("/gejmboj.gb", cpu);
//		cartridge = new Cartridge("/bad_apple.gb", cpu);
//		cartridge = new Cartridge("/REVIVAL/matrixtrailer.gb", cpu);
//		cartridge = new Cartridge("/roms/emulator_only/mbc1/ram_64kb.gb", cpu);
//		cartridge = new Cartridge("/roms/emulator_only/mbc5/rom_16Mb.gb", cpu);
//		cartridge = new Cartridge("/roms/acceptance/ppu/vblank_stat_intr-GS.gb", cpu);
//		cartridge = new Cartridge("/roms/acceptance/add_sp_e_timing.gb", cpu);
//		cartridge = new Cartridge("/individual/02-interrupts.gb", cpu);
//		cartridge = new Cartridge("/individual/03-op sp,hl.gb", cpu);
//		cartridge = new Cartridge("/individual/04-op r,imm.gb", cpu);
//		cartridge = new Cartridge("/individual/05-op rp.gb", cpu);
//		cartridge = new Cartridge("/individual/06-ld r,r.gb", cpu);
//		cartridge = new Cartridge("/individual/07-jr,jp,call,ret,rst.gb", cpu);
//		cartridge = new Cartridge("/individual/08-misc instrs.gb", cpu);
//		cartridge = new Cartridge("/individual/09-op r,r.gb", cpu);
//		cartridge = new Cartridge("/individual/10-bit ops.gb", cpu);
//		cartridge = new Cartridge("/individual/11-op a,(hl).gb", cpu);

//		chooseRomFile(filePath);
	}

	public void chooseRomFile(File file) {
		cartridge = new Cartridge(file, cpu);
		cpu.setCartridge(cartridge);
		timer = new Timer(cpu);
		ppu = new PPU(cpu);
		bus.connect(cartridge, timer, cpu.getInterrupt(), ppu, joypad, dma);
		cpu.reset();
	}

	public long before = 0;

	public static final int MAX_CYCLES = 69905;
	int cycles = 0;

	int fps = 0;
	long timeForFrame = 0;
	public volatile boolean running = false;

	public void run() {
		while (running) {
			if (System.currentTimeMillis() - before >= 1000) {
				display.setTitle(gameTitle + "		FPS: " + fps);
				before = System.currentTimeMillis();
				fps = 0;
			}

			if (cycles < MAX_CYCLES) {
				cpu.step();
				for (int i = 0; i < 4; i++) {
					timer.tick();
					ppu.tick();
				}
				dma.transfer();
				cycles += 4;
			} else {
				repaint();
				fps++;
				cycles = 0;
				long timeTaken = (System.nanoTime() - timeForFrame);
				while (timeTaken - 16666666 < 0) {
					timeTaken = (System.nanoTime() - timeForFrame);
				}
				timeForFrame = System.nanoTime();
			}
		}
	}

	public void paint(Graphics g) {
		if (ppu != null) {
			ppu.render((Graphics2D) g);
		}
	}

	@Override
	public Dimension preferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_Z) {
			joypad.press(false, 0);
			return;
		} else if (e.getKeyCode() == KeyEvent.VK_X) {
			joypad.press(false, 1);
			return;
		} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			joypad.press(false, 3);
			return;
		} else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
			joypad.press(false, 2);
			return;
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			joypad.press(true, 2);
			return;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			joypad.press(true, 3);
			return;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			joypad.press(true, 1);
			return;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			joypad.press(true, 0);
			return;
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
			return;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_Z) {
			joypad.release(false, 0);
			return;
		} else if (e.getKeyCode() == KeyEvent.VK_X) {
			joypad.release(false, 1);
			return;
		} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			joypad.release(false, 3);
			return;
		} else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
			joypad.release(false, 2);
			return;
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			joypad.release(true, 2);
			return;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			joypad.release(true, 3);
			return;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			joypad.release(true, 1);
			return;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			joypad.release(true, 0);
			return;
		}
	}

}

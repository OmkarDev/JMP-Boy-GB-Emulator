package ppu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import cpu.CPU;
import cpu.Interrupt;
import gameboy.GameBoy;
import utils.Utils;

public class PPU {

	public int LY;
	private int LX;
	public int STAT = 0x81;
	public int LCDC = 0x91;
	public int SCX, SCY;
	public int WX, WY;
	public int LYC;
	public int OBP0;
	public int OBP1;

	public static enum Mode {
		H_BLANK, V_BLANK, OAM_SCAN, DRAWING
	}

	public Mode mode = Mode.OAM_SCAN;
	public static int STAT_BIT_LYC_EQ_LY = 2;
	public static int STAT_BIT_MODE0 = 3;
	public static int STAT_BIT_MODE1 = 4;
	public static int STAT_BIT_MODE2 = 5;
	public static int STAT_BIT_LYC = 6;

	CPU cpu;
	public int[] vram = new int[0xFFFF + 1];

	public int BGP = 0xFC;

	public PPU(CPU cpu) {
		this.cpu = cpu;
	}

	private BufferedImage background = new BufferedImage(160, 144, BufferedImage.TYPE_INT_RGB);
	private BufferedImage sprite = new BufferedImage(160, 144, BufferedImage.TYPE_INT_ARGB);
	private int[][] backgroundBuffer = new int[144][160];
	private int[][] priorityBuffer = new int[144][160];

	public void render(Graphics2D g) {
		if (isLcdEnabled()) {
			g.drawImage(background, 0, 0, GameBoy.WIDTH, GameBoy.HEIGHT, null);
			g.drawImage(sprite, 0, 0, GameBoy.WIDTH, GameBoy.HEIGHT, null);
		}
	}

	int WHITE = 0xffe0f8d0;
	int LIGHT_GRAY = 0xff88c070;
	int DARK_GRAY = 0xff346856;
	int BLACK = 0xff081820;

	int[] colors = { WHITE, LIGHT_GRAY, DARK_GRAY, BLACK };

	public int getColorFromPalette(int index) {
		int BG_Palette_Data = BGP;
		return colors[(BG_Palette_Data >> (2 * index)) & 0b11];
	}

	public int getOBJColorFromPalette(int index, boolean obp0) {
		int BG_Palette_Data = OBP0;
		if (obp0 == false) {
			BG_Palette_Data = OBP1;
		}
		return colors[(BG_Palette_Data >> (2 * index)) & 0b11];
	}

	public boolean isBgWindowEnabled() {
		return (LCDC & 1) == 1;
	}

	public boolean isObjEnabled() {
		return ((LCDC >> 1) & 1) == 1;
	}

	public boolean isWindowEnabled() {
		return ((LCDC >> 5) & 1) == 1;
	}

	public boolean is8x8SpriteEnabled() {
		return (((LCDC >> 2)) & 1) == 0;
	}

	public void drawSprites() {
		if (isObjEnabled()) {
			BufferedImage temp_sprite = new BufferedImage(160, 144, BufferedImage.TYPE_INT_ARGB);
			priorityBuffer = new int[144][160];
			// TODO 10 sprites per line
			if (is8x8SpriteEnabled() == false) {
				for (int i = 0; i < 40; i++) {
					draw8x8Sprite(i, temp_sprite, 0, 1);
					draw8x8Sprite(i, temp_sprite, 8, -1);
				}
			} else {
				for (int i = 0; i < 40; i++) {
					draw8x8Sprite(i, temp_sprite, 0, 0);
				}
			}
			sprite = temp_sprite;
			clearScreen = true;
		} else {
			if (clearScreen) {
				sprite = new BufferedImage(160, 144, BufferedImage.TYPE_INT_ARGB);
				clearScreen = false;
			}
		}
	}

	boolean clearScreen = true;

	public int xPosOfObject(int index) {
		int spriteAddress = 0xFE00 + 4 * index;
		int xPos = cpu.read_memory(spriteAddress + 1) - 8;
		return xPos;
	}

	public void draw8x8Sprite(int index, BufferedImage sprite, int y, int andOrNone) {
		int spriteAddress = 0xFE00 + 4 * index;
		int yPos = cpu.read_memory(spriteAddress) - 16;
		int xPos = cpu.read_memory(spriteAddress + 1) - 8;
		int tileIndex = cpu.read_memory(spriteAddress + 2);

		int attributes = cpu.read_memory(spriteAddress + 3);
		boolean obp0 = (attributes & 4) == 0;
		boolean priority = (attributes & 0x80) == 0x80;
		boolean flipX = (attributes & 0x20) > 0;
		boolean flipY = (attributes & 0x40) > 0;
		int offsetX = 7;
		int sign = -1;
		if (flipX) {
			offsetX = 0;
			sign = 1;
		}
		if (andOrNone != 0 && flipY) {
			y = (y == 0) ? 8 : 0;
		}

		if (andOrNone == 1) {
			tileIndex &= 0xFE;
		} else if (andOrNone == -1) {
			tileIndex |= 0x01;
		}

		for (int py = 0; py < 8; py++) {
			for (int px = 0; px < 8; px++) {
				if (py + yPos < 0 || py + yPos >= 144) {
					continue;
				}
				if (px + xPos < 0 || px + xPos >= 160) {
					continue;
				}
				int offsetY = py;
				if (flipY) {
					offsetY = 7 - py;
				}
				if (offsetY + yPos + y < 0 || offsetY + yPos + y >= 144) {
					continue;
				}
				int bg = backgroundBuffer[offsetY + yPos + y][px + xPos];
				if (priority && (bg != 0)) {
					continue;
				}
				int[] bytes = getDataofNthObjTile(tileIndex, py);
				int fb = bytes[0];
				int sb = bytes[1];
				int b = (1 << (offsetX + sign * px));
				int col = ((fb & b) >> (offsetX + sign * px)) + (((sb & b) >> (offsetX + sign * px)) << 1);
				if (col == 0) {
					continue;
				}
				int priorityBufferIndex = priorityBuffer[offsetY + yPos + y][px + xPos];
				if (priorityBufferIndex != 0) {
					if (xPosOfObject(priorityBufferIndex - 1) < xPos) {
						continue;
					}
				}

				int color = getOBJColorFromPalette(col, obp0);
				priorityBuffer[offsetY + yPos + y][px + xPos] = index + 1;
				sprite.setRGB(px + xPos, offsetY + yPos + y, color);
			}
		}
	}

	public int getBgPixel(int x, int y) {
		x = (x + SCX) & 0xFF;
		y = (y + SCY) & 0xFF;
		int tileX = x / 8;
		int tileY = y / 8;
		int py = y % 8;
		int px = x % 8;
		int bgIndex = (getBackgroundTileIndex(tileX, tileY));
		int[] bytes = getDataOfNthTile(bgIndex, py);
		int fb = bytes[0];
		int sb = bytes[1];
		int b = (1 << (7 - px));
		int index = ((fb & b) >> (7 - px)) + (((sb & b) >> (7 - px)) << 1);
		return index;
	}

	public int getWinPixel(int x, int y) {
		int tileX = (x) / 8;
		int tileY = (y) / 8;
		int py = (y) % 8;
		int px = (x) % 8;

		int bgIndex = (getWindowTileIndex(tileX, tileY));
		int[] bytes = getDataOfNthTile(bgIndex, py);
		int fb = bytes[0];
		int sb = bytes[1];

		int b = (1 << (7 - px));
		int index = ((fb & b) >> (7 - px)) + (((sb & b) >> (7 - px)) << 1);
		return index;
	}

	public int[] getDataofNthObjTile(int n, int py) {
		int startAdd = 0x8000;
		int[] res = new int[2];
		int startIndex = startAdd + (n * 16) + (py * 2);
		for (int i = 0; i < res.length; i++) {
			res[i] = vram[startIndex + i];
		}
		return res;
	}

	public int[] getDataOfNthTile(int n, int py) {
		int startAdd = getBgWindowTileDataAreaStart();
		if (startAdd == 0x8800) { // 8800
			startAdd = 0x9000;
			n = (byte) n;
		}
		int[] res = new int[2];
		int startIndex = startAdd + (n * 16) + (py * 2);
		for (int i = 0; i < res.length; i++) {
			res[i] = vram[startIndex + i];
		}
		return res;
	}

	public int[] getBgPixelData(int bgIndex, int px, int py) {
		int[] res = new int[2];
		res[0] = vram[bgIndex];
		res[1] = vram[bgIndex + 1];
		return res;
	}

	public int getBackgroundTileIndex(int x, int y) {
		int startAdd = 0x9800;
		boolean bit = (LCDC & 0b1000) == 0b1000;
		if (bit == true) {
			startAdd = 0x9C00;
		} else {
			startAdd = 0x9800;
		}
		int ind = x + y * 32;
		return vram[startAdd + ind];
	}

	public int getWindowTileIndex(int x, int y) {
		int startAdd = 0x9800;
		boolean bit = ((LCDC >> 6) & 1) == 1;
		if (bit == true) {
			startAdd = 0x9C00;
		} else {
			startAdd = 0x9800;
		}
		int ind = x + y * 32;
		return vram[startAdd + ind];
	}

	public int getBgWindowTileDataAreaStart() {
		boolean bit = (LCDC & 0x10) == 0x10;
		if (bit == true) {
			return 0x8000;
		} else {
			return 0x8800;
		}
	}

	public boolean isLcdEnabled() {
		return (LCDC & 0x80) == 0x80;
	}

	public int getBitSTAT(int bit) {
		return ((STAT & (1 << bit)) & 0xFF) > 0 ? 1 : 0;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
		switch (mode) {
		case H_BLANK: {
			STAT = (STAT & 0xfc) | 0b00;
			if (getBitSTAT(STAT_BIT_MODE0) == 1) {
				cpu.interrupt.setIF(Interrupt.IRQ_BIT_LCD, true);
			}
			break;
		}
		case V_BLANK: {
			STAT = (STAT & 0xfc) | 0b01;
			cpu.interrupt.setIF(Interrupt.IRQ_BIT_VBLANK, true);
			break;
		}
		case OAM_SCAN: {
			STAT = (STAT & 0xfc) | 0b10;
			if (getBitSTAT(STAT_BIT_MODE2) == 1) {
				cpu.interrupt.setIF(Interrupt.IRQ_BIT_LCD, true);
			}
			break;
		}
		case DRAWING: {
			STAT = (STAT & 0xfc) | 0b11;
			break;
		}
		default: {
			System.out.println("LCD Mode not implemented yet");
			System.exit(0);
		}
		}
	}

	public void tick() {
		if (LY < 144) {
			if (LX == 0) {
				setMode(Mode.OAM_SCAN);
			} else if (LX == 80) {
				setMode(Mode.DRAWING);
			} else if (LX == 252) {
				setMode(Mode.H_BLANK);
			}
		}
		if (mode == Mode.DRAWING) {
			if (LX - 80 >= 0 && LX - 80 < 160) {
				int px = LX - 80;
				int py = LY;
				int index = getBgPixel(px, py);
				int wx = WX - 7;
				int wy = WY;
				if (isWindowEnabled() && px >= wx && py >= wy) {
					index = getWinPixel(px - wx, py - wy);
				}
				background.setRGB(px, py, getColorFromPalette(index));
				backgroundBuffer[py][px] = index;
			}
		}
		LX++;
		if (LX > 455) {
			LX = 0;
			LY++;
			if (LY > 153) {
				LY = 0;
			}
			if (LY == 144) {
				if (LX == 0) {
					drawSprites();
				}
				setMode(Mode.V_BLANK);
			}
			setStatBit(STAT_BIT_LYC_EQ_LY, LYC == LY);
			if (LYC == LY) {
				if (getBitSTAT(STAT_BIT_LYC) == 1) {
					cpu.interrupt.setIF(Interrupt.IRQ_BIT_LCD, true);
				}
			}
		}
	}

	private void setStatBit(int bit, boolean set) {
		if (set) {
			STAT = STAT | (1 << bit);
		} else {
			STAT = STAT & ((~(1 << bit)) & 0xFF);
		}
		STAT &= 0xFF;
	}

}

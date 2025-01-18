package bus;

import cartridge.Cartridge;
import cpu.CPU;
import cpu.Interrupt;
import dma.DMA;
import gameboy.GameBoy;
import joypad.Joypad;
import ppu.PPU;
import timer.Timer;

public class Bus {

	public int[] ram = new int[0xFFFF + 1];

	public Cartridge cartridge;
	public Timer timer;
	public static final int DIVIDER_REGISTER_ADDRESS = 0xFF04;
	public static final int TIMER_MODULO_REGISTER_ADDRESS = 0xFF06;
	public static final int TIMER_CONTROL_REGISTER_ADDRESS = 0xFF07;
	public static final int TIMER_COUNTER_REGISTER_ADDRESS = 0xFF05;

	public Interrupt interrupt;
	public static final int INTERRUPT_FLAG_REGISTER_ADDRESS = 0xFF0F;
	public static final int INTERRUPT_ENABLE_REGISTER_ADDRESS = 0xFFFF;

	public PPU ppu;
	public static final int BACKGROUND_PALETTE_ADDRESS = 0xFF47;
	public static final int OBP0_REGISTER_ADDRESS = 0xFF48;
	public static final int OBP1_REGISTER_ADDRESS = 0xFF49;
	public static final int LCD_STATUS_REGISTER_ADDRESS = 0xFF41;
	public static final int LY_REGISTER_ADDRESS = 0xFF44;
	public static final int SCROLL_X_REGISTER_ADDRESS = 0xFF43;
	public static final int SCROLL_Y_REGISTER_ADDRESS = 0xFF42;
	public static final int LCD_CONTROL_REGISTER_ADDRESS = 0xFF40;
	public static final int WINDOW_X_REGISTER_ADDRESS = 0xFF4B;
	public static final int WINDOW_Y_REGISTER_ADDRESS = 0xFF4A;
	public static final int LYC_REGISTER_ADDRESS = 0xFF45;

	public Joypad joypad;
	public DMA dma;

	public void connect(Cartridge cartridge, Timer timer, Interrupt interrupt, PPU ppu, Joypad joypad, DMA dma) {
		this.cartridge = cartridge;
		this.timer = timer;
		this.interrupt = interrupt;
		this.ppu = ppu;
		this.joypad = joypad;
		this.dma = dma;
	}

	public int read(int address) {
		if (address == 0xFF00) {
			if ((joypad.JOYP & 0x10) == 0) {
				return (joypad.JOYP & 0xF0) | (joypad.direction & 0x0F);
			}
			if ((joypad.JOYP & 0x20) == 0) {
				return (joypad.JOYP & 0xF0) | (joypad.button & 0x0F);
			}
			return 0x3F;
		}
		if (address >= 0x0000 && address <= 0x3FFF) {
			return cartridge.readRomBank0(address);
		} else if (address >= 0x4000 && address <= 0x7FFF) {
			return cartridge.getMbc().readSwitchableRomBank(address);
		} else if (address >= 0x8000 && address <= 0x9FFF) {
			return ppu.vram[address] & 0xFF;
		} else if (address >= 0xA000 && address <= 0xBFFF) {
			return cartridge.getMbc().readExtRam(address & 0xFFFF);
		} else if (address >= 0xC000 && address <= 0xDFFF) {
			return ram[address & 0xFFFF] & 0xFF;
		} else if (address >= 0xE000 && address <= 0xFDFF) {
			return ram[address - 0x2000] & 0xFF;
		} else if (address >= 0xFE00 && address <= 0xFE9F) {
			return ram[address] & 0xFF;
		} else if (address >= 0xFEA0 && address <= 0xFEFF) {
			return ram[address] & 0xFF;
		} else if (address >= 0xFF00 && address <= 0xFF7F) {
			if (address == DMA.DMA_REGISTER_ADDRESS) {
				return dma.highByte;
			} else if (address == BACKGROUND_PALETTE_ADDRESS) {
				return ppu.BGP & 0xFF;
			} else if (address == OBP0_REGISTER_ADDRESS) {
				return ppu.OBP0 & 0xFF;
			} else if (address == OBP1_REGISTER_ADDRESS) {
				return ppu.OBP1 & 0xFF;
			} else if (address == LCD_STATUS_REGISTER_ADDRESS) {
				return ppu.STAT & 0xFF;
			} else if (address == LY_REGISTER_ADDRESS) {
				return ppu.LY & 0xFF;
			} else if (address == SCROLL_X_REGISTER_ADDRESS) {
				return ppu.SCX & 0xFF;
			} else if (address == SCROLL_Y_REGISTER_ADDRESS) {
				return ppu.SCY & 0xFF;
			} else if (address == WINDOW_X_REGISTER_ADDRESS) {
				return ppu.WX & 0xFF;
			} else if (address == WINDOW_Y_REGISTER_ADDRESS) {
				return ppu.WY & 0xFF;
			} else if (address == LCD_CONTROL_REGISTER_ADDRESS) {
				return ppu.LCDC & 0xFF;
			} else if (address == LYC_REGISTER_ADDRESS) {
				return ppu.LYC & 0xFF;
			} else if (address == INTERRUPT_FLAG_REGISTER_ADDRESS) {
				return interrupt.IF & 0xFF;
			} else if (address == DIVIDER_REGISTER_ADDRESS) {
				return (timer.divCounter >> 8) & 0xFF;
			} else if (address == TIMER_MODULO_REGISTER_ADDRESS) {
				return timer.TMA & 0xFF;
			} else if (address == TIMER_CONTROL_REGISTER_ADDRESS) {
				return timer.TAC & 0xFF;
			} else if (address == TIMER_COUNTER_REGISTER_ADDRESS) {
				return timer.TIMA & 0xFF;
			} else {
				return ram[address] & 0xFF;
			}
		} else if (address >= 0xFF80 && address <= 0xFFFE) {
			return ram[address];
		} else if (address == INTERRUPT_ENABLE_REGISTER_ADDRESS) {
			return interrupt.IE & 0xFF;
		}
		return ram[address] & 0xFF;
	}

	public void write(int address, int val) {
		if (address >= 0x0000 && address <= 0x7FFF) {
			cartridge.selectBank(address, val);
			return;
		} else if (address >= 0x8000 && address <= 0x9FFF) {
			ppu.vram[address] = val & 0xFF;
			return;
		} else if (address >= 0xA000 && address <= 0xBFFF) {
			cartridge.getMbc().writeExtRam(address & 0xFFFF, val & 0xFF);
			return;
		} else if (address >= 0xC000 && address <= 0xDFFF) {
			ram[address] = val & 0xFF;
			return;
		} else if (address >= 0xE000 && address <= 0xFDFF) {
			ram[address - 0x2000] = val & 0xFF;
			return;
		} else if (address >= 0xFE00 && address <= 0xFE9F) {
			ram[address] = val & 0xFF;
			return;
		} else if (address >= 0xFEA0 && address <= 0xFEFF) {
			ram[address] = val & 0xFF;
			return;
		} else if (address >= 0xFF00 && address <= 0xFF7F) {
			if (address == 0xFF00) {
				joypad.JOYP = (val & 0xF0) | (joypad.JOYP & 0x0F);
				return;
			} else if (address == DMA.DMA_REGISTER_ADDRESS) {
				dma.startTransfer(val & 0xFF);
				return;
			}
			if (address == 0xFF01) {
				ram[address] = val & 0xFF;
				GameBoy.serialOut += (char) val;
//				System.out.print((char) val);
				return;
			}
			if (address == LY_REGISTER_ADDRESS) {
				ppu.LY = 0;
				return;
			} else if (address == LCD_STATUS_REGISTER_ADDRESS) {
				ppu.STAT = val & 0xFF;
				return;
			} else if (address == BACKGROUND_PALETTE_ADDRESS) {
				ppu.BGP = val & 0xFF;
				return;
			} else if (address == OBP0_REGISTER_ADDRESS) {
				ppu.OBP0 = val & 0xFF;
				return;
			} else if (address == OBP1_REGISTER_ADDRESS) {
				ppu.OBP1 = val & 0xFF;
				return;
			} else if (address == SCROLL_X_REGISTER_ADDRESS) {
				ppu.SCX = val & 0xFF;
				return;
			} else if (address == SCROLL_Y_REGISTER_ADDRESS) {
				ppu.SCY = val & 0xFF;
				return;
			} else if (address == WINDOW_X_REGISTER_ADDRESS) {
				ppu.WX = val & 0xFF;
				return;
			} else if (address == WINDOW_Y_REGISTER_ADDRESS) {
				ppu.WY = val & 0xFF;
				return;
			} else if (address == LCD_CONTROL_REGISTER_ADDRESS) {
				ppu.LCDC = val & 0xFF;
				return;
			} else if (address == LYC_REGISTER_ADDRESS) {
				ppu.LYC = val & 0xFF;
				return;
			} else if (address == INTERRUPT_FLAG_REGISTER_ADDRESS) {
				interrupt.IF = (0xE0 | val) & 0xFF;
				return;
			} else if (address == CPU.PREPARE_SPEED_SWITCH_REGISTER_ADDRESS) {
				ram[CPU.PREPARE_SPEED_SWITCH_REGISTER_ADDRESS] |= val;
				return;
			} else if (address == DIVIDER_REGISTER_ADDRESS) {
				timer.divCounter = 0;
				return;
			} else if (address == TIMER_CONTROL_REGISTER_ADDRESS) {
				timer.TAC = 0xf8 | val;
				return;
			} else if (address == TIMER_MODULO_REGISTER_ADDRESS) {
				timer.TMA = val & 0xFF;
				return;
			} else if (address == TIMER_COUNTER_REGISTER_ADDRESS) {
				timer.TIMA = val & 0xFF;
				timer.writtenTima = true;
				return;
			} else {
				ram[address] = val & 0xFF;
				return;
			}
		} else if (address >= 0xFF80 && address <= 0xFFFE) {
			ram[address] = val & 0xFF;
			return;
		} else if (address == INTERRUPT_ENABLE_REGISTER_ADDRESS) {
			interrupt.IE = val & 0xFF;
			return;
		}
		ram[address] = val & 0xFF;
	}

}

package joypad;

import cpu.CPU;
import cpu.Interrupt;

public class Joypad {

	public int JOYP = 0xff;

	public CPU cpu;

	public int button = 0xF;
	public int direction = 0xF;

	public Joypad(CPU cpu) {
		this.cpu = cpu;
	}

	public void press(boolean areDirectionKeys, int bit) {
		int prev;
		if (areDirectionKeys) {
			prev = direction;
			direction &= (~(1 << bit) & 0xF);
		} else {
			prev = button;
			button &= (~(1 << bit) & 0xF);
		}
		if (((prev >> bit) & 0b1) == 1) {
			cpu.interrupt.setIF(Interrupt.IRQ_BIT_JOYPAD, true);
		}
	}

	public void release(boolean areDirectionKeys, int bit) {
		if(areDirectionKeys) {
			direction |= (1 << bit);
		}else {
			button |= (1<<bit);
		}
	}

}

package timer;

import cpu.CPU;
import cpu.Interrupt;

public class Timer {

	private CPU cpu;
	public int TMA;
	public int TAC = 0xF8;
	public int TIMA = 0;

	public Timer(CPU cpu) {
		this.cpu = cpu;
	}

	public void tick() {
		updateDivider();
		updateTimer();
	}

	private int previousAndResult = 0;
	public boolean writtenTima = false;

	private int freqBit[] = { 9, 3, 5, 7 };

	int M_Cycle = 0;
	boolean overflowed = false;

	public void updateTimer() {
		if (overflowed) {
			M_Cycle++;
//			System.out.println("TIMER");
			if (M_Cycle == 3) {
				cpu.interrupt.setIF(Interrupt.IRQ_BIT_TIMER, true);
				TIMA = TMA;
				overflowed = false;
				M_Cycle = 0;
			}
		}
		int timerEnableBit = (TAC >> 2) & 1;
		int bitPos = freqBit[TAC & 0b11];
		bitPos <<= (cpu.doubleSpeed ? 2 : 1) - 1;
		int divCounterBit = ((divCounter & (1 << bitPos)) != 0) ? 1 : 0;
		int currentAndResult = divCounterBit & timerEnableBit;
		if (previousAndResult == 1 && currentAndResult == 0) {
			TIMA++;
			if (TIMA > 0xFF) {
				TIMA = 0;
				overflowed = true;
			}
		}
		previousAndResult = currentAndResult;
		writtenTima = false;
	}

	public int divCounter = 0xAC00;

	public void updateDivider() {
		divCounter += 1;
		divCounter &= 0xFFFF;
	}

}

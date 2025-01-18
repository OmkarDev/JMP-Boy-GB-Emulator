package dma;

import bus.Bus;

public class DMA {
	public static final int DMA_REGISTER_ADDRESS = 0xFF46;

	public int highByte;
	private int cycles = 160;
	private Bus bus;

	public DMA(Bus bus) {
		this.bus = bus;
	}

	public void startTransfer(int val) {
		highByte = val;
		cycles = 0;
	}

	public void transfer() {
		if (cycles >= 160) {
			return;
		}
		int address = (highByte << 8);
		bus.write(0xFE00 + cycles,bus.read(address + cycles));
		cycles++;
	}
}

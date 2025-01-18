package cpu;

public class Interrupt {

	public boolean interrupt_master_enable = false;

	public static final int IRQ_BIT_VBLANK = 0;
	public static final int IRQ_BIT_LCD = 1;
	public static final int IRQ_BIT_TIMER = 2;
	public static final int IRQ_BIT_SERIAL = 3;
	public static final int IRQ_BIT_JOYPAD = 4;
	public int IE = 0x00;
	public int IF = 0xE1;
	
	public boolean hasInterruptOccurred() {
		if (interrupt_master_enable) {
			if (isInterruptRequested()) {
				//interrupt master should not be disabled here!!!
				return true;
			}
		}
		return false;
	}

	public boolean isInterruptRequested() {
		if ((IE & IF & 0x1F) > 0) {
			return true;
		}
		return false;
	}

	public int ISR_OPCODE = Integer.MIN_VALUE;


	public void find_ISR_PC() {
		if (((IE & 0b0001) & (IF & 0b0001)) > 0) {
			ISR_OPCODE = 0xDE00;
			setIF(IRQ_BIT_VBLANK, false);
		} else if (((IE & 0b0010) & (IF & 0b0010)) > 0) {
			ISR_OPCODE = 0xDE01;
			setIF(IRQ_BIT_LCD, false);
		} else if (((IE & 0b100) & (IF & 0b100)) > 0) {
			ISR_OPCODE = 0xDE02;
			setIF(IRQ_BIT_TIMER, false);
		} else if (((IE & 0b1000) & (IF & 0b1000)) > 0) {
			ISR_OPCODE = 0xDE03;
			setIF(IRQ_BIT_SERIAL, false);
		} else if (((IE & 0b10000) & (IF & 0b10000)) > 0) {
			ISR_OPCODE = 0xDE04;
			setIF(IRQ_BIT_JOYPAD, false);
		} else {
			ISR_OPCODE = Integer.MIN_VALUE;
			System.out.println(
					"THIS SHOULD NOT HAVE HAPPENED IE:" + Integer.toHexString(IE) + " IF:" + Integer.toHexString(IF));
			System.exit(0);
		}
	}

	public void setIF(int bit, boolean set) {
		if (set) {
			IF = IF | (1 << bit);
		} else {
			IF = IF & (~(1 << bit)) & 0xFF;
		}
		IF &= 0xFF;
	}

	public int getIsrOpcode() {
		return ISR_OPCODE;
	}

}

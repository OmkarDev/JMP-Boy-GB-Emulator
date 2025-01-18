package cpu;


public class Instructions {

	private CPU cpu;
	private int lsb;
	private int msb;

	public boolean checkCondition = false;
	public boolean hasCondition = false;

	public Instructions(CPU cpu) {
		this.cpu = cpu;
	}

	public void checkCondition(String cc) {
		hasCondition = true;
		checkCondition = getFlag(cc);
	}

	public void JR_e8() {
		int e8 = (byte) lsb;
		cpu.PC += e8;
	}

	public void NOP() {
		fetch();
	}

	public void fetch() {
		cpu.opcode = cpu.read_memory(cpu.PC);
		if (cpu.opcode == 0xCB) {
			cpu.opcode = (cpu.opcode << 8) | cpu.read_memory(cpu.PC + 1);
		}
		increment_PC();
		if (cpu.interrupt.hasInterruptOccurred()) {
			cpu.canDoISR = true;
		}
	}

	public void HALT() {
		cpu.isHalted = true;
		if (cpu.interrupt.isInterruptRequested()) {
			if (cpu.interrupt.interrupt_master_enable) {
				cpu.isHalted = false;
				fetch();
				return;
			}else {
				cpu.isHalted = false;
				fetch();
				return;
			}
		}
	}

	public void DI() {
		cpu.interrupt.interrupt_master_enable = false;
	}

	public void EI() {
		cpu.interrupt.interrupt_master_enable = true;
	}


	private String saved_r8 = null;

	public void save_r8(String r8) {
		this.saved_r8 = r8;
		lsb = read_r8(r8);
	}

	public void JP_HL() {
		cpu.PC = read_r16("HL");
	}

	public void RLCA() {
		setFlag("C", (cpu.A & 0x80) == 0x80);
		cpu.A = cpu.A << 1;
		cpu.A |= ((cpu.A & 0x100) == 0x100) ? 1 : 0;
		cpu.A &= 0xFF;
		setFlag("Z", false);
		setFlag("N", false);
		setFlag("H", false);
	}

	public void no_op() {

	}

	public void RLC_r8(String r8) {
		int r = read_r8(r8);
		setFlag("C", (r & 0x80) == 0x80);
		r <<= 1;
		r |= ((r & 0x100) == 0x100) ? 1 : 0;
		r &= 0xFF;
		write_r8(r8, r);
		setFlag("Z", r == 0);
		setFlag("N", false);
		setFlag("H", false);
		increment_PC();
	}

	public void ADD_SP_e8() {
		int e8 = lsb;
		setFlag("H", ((cpu.SP & 0xF) + (e8 & 0xF)) > 0xF);
		setFlag("C", ((cpu.SP & 0xFF) + (e8 & 0xFF)) > 0xFF);
		cpu.SP += (byte) e8;
		cpu.SP &= 0xFFFF;
		setFlag("Z", false);
		setFlag("N", false);
	}

	public void RRC_r8(String r8) {
		int r = read_r8(r8);
		int bit0 = (r & 0x1);
		setFlag("C", bit0 == 0x1);
		r >>= 1;
		r |= (bit0 << 7);
		r &= 0xFF;
		write_r8(r8, r);
		setFlag("Z", r == 0);
		setFlag("N", false);
		setFlag("H", false);
		increment_PC();
	}

	public void RL_r8(String r8) {
		int r = read_r8(r8);
		int oldC = getFlag("C") ? 1 : 0;
		setFlag("C", (r & 0x80) == 0x80);
		r <<= 1;
		r |= oldC;
		r &= 0xFF;
		write_r8(r8, r);
		setFlag("Z", r == 0);
		setFlag("N", false);
		setFlag("H", false);
		increment_PC();
	}

	public void SLA_r8(String r8) {
		int r = read_r8(r8);
		setFlag("C", (r & 0x80) == 0x80);
		r <<= 1;
		r &= 0xFF;
		write_r8(r8, r);
		setFlag("Z", r == 0);
		setFlag("N", false);
		setFlag("H", false);
		increment_PC();
	}

	public void SRA_r8(String r8) {
		int r = read_r8(r8);
		int b7 = ((r & 0x80) == 0x80) ? 1 : 0;
		setFlag("C", (r & 1) == 1);
		r >>>= 1;
		r |= (b7 << 7);
		r &= 0xFF;
		write_r8(r8, r);
		setFlag("Z", r == 0);
		setFlag("N", false);
		setFlag("H", false);
		increment_PC();
	}

	public void SWAP(String r8) {
		int r = read_r8(r8);
		int lo = (r & 0xF);
		int hi = (r >>> 4) & 0xF;
		r = (lo << 4) | hi;
		r &= 0xFF;
		write_r8(r8, r);
		setFlag("Z", r == 0);
		setFlag("N", false);
		setFlag("H", false);
		setFlag("C", false);
		increment_PC();
	}

	public void BIT_b_r8(String nthBit, String register) {
		int b = Integer.parseInt(nthBit);
		int r = read_r8(register);
		setFlag("Z", ((1 << b) & r) == 0);
		setFlag("N", false);
		setFlag("H", true);
		increment_PC();
	}

	public void RES_b_r8(String nthBit, String register) {
		int b = Integer.parseInt(nthBit);
		int r = read_r8(register);
		r = r & (~(1 << b) & 0xFF);
		write_r8(register, r);
		increment_PC();
	}

	public void SET_b_r8(String nthBit, String register) {
		int b = Integer.parseInt(nthBit);
		int r = read_r8(register);
		r = r | ((1 << b));
		write_r8(register, r);
		increment_PC();
	}

	public void write_PC(String hex_val) {
		cpu.PC = Integer.parseInt(hex_val, 16);
	}

	public void LD_HL_SP_e8() {
		int e8 = (byte) lsb;
		write_r16("HL", cpu.SP + e8);
		setFlag("H", ((cpu.SP & 0xF) + (e8 & 0xF)) > 0xF);
		setFlag("C", ((cpu.SP & 0xFF) + (e8 & 0xFF)) > 0xFF);
		setFlag("Z", false);
		setFlag("N", false);
	}

	public void SRL_r8(String r8) {
		int n = read_r8(r8);
		int ans = n >>> 1;
		setFlag("N", false);
		setFlag("H", false);
		setFlag("Z", ans == 0);
		setFlag("C", (n & 0x1) == 1);
		write_r8(r8, ans & 0xFF);
		increment_PC();
	}

	public void ADC_r8(String r8) {
		ADC(read_r8(r8));
	}

	public void ADC_n8() {
		ADC(lsb);
	}

	public void ADC(int n) {
		int C = getFlag("C") ? 1 : 0;
		setFlag("H", ((cpu.A & 0xF) + (n & 0xF)) + C > 0xF);
		setFlag("C", (cpu.A + n + C) > 0xFF);
		cpu.A = (cpu.A + n + C) & 0xFF;
		setFlag("N", false);
		setFlag("Z", cpu.A == 0);
	}

	public void RRA() {
		int n = read_r8("A");
		int C = getFlag("C") ? 1 : 0;
		setFlag("N", false);
		setFlag("H", false);
		setFlag("C", (n & 0x1) == 1);

		int ans = n >>> 1;
		ans = (C << 7) | ans;
		setFlag("Z", false);
		write_r8("A", ans & 0xFF);
	}

	public void ADD_HL_r16(String reg) {
		int r16 = read_r16(reg);
		int hl = read_r16("HL");
		int val = r16 + hl;
		setFlag("C", val > 0xFFFF);
		setFlag("N", false);
		setFlag("H", ((hl & 0xFFF) + (r16 & 0xFFF)) > 0xFFF);
		write_r16("HL", val);
	}

	public void ADD_n8() {
		ADD(lsb);
	}

	public void ADD_r8(String r8) {
		ADD(read_r8(r8));
	}

	public void SBC_r8(String r8) {
		SBC(read_r8(r8));
	}

	public void SBC_n8() {
		SBC(lsb);
	}

	public void SBC(int n8) {
		int C = getFlag("C") ? 1 : 0;
		setFlag("N", true);
		setFlag("Z", ((cpu.A - (C + n8)) & 0xFF) == 0);
		setFlag("H", ((cpu.A & 0xF) - (n8 & 0xF) - (C & 0xF)) < 0);
		setFlag("C", n8 + C > cpu.A);
		cpu.A = (cpu.A - C - n8) & 0xFF;
	}

	public void SUB_n8() {
		SUB(lsb);
	}

	public void SUB_r8(String r8) {
		SUB(read_r8(r8));
	}

	public void SUB(int n) {
		setFlag("N", true);
		setFlag("H", (cpu.A & 0xF) < (n & 0xF));
		setFlag("C", (cpu.A < n));
		cpu.A = (cpu.A - n) & 0xFF;
		setFlag("Z", cpu.A == 0);
	}

	public void ADD(int n) {
		setFlag("N", false);
		setFlag("H", ((cpu.A & 0xF) + (n & 0xF) > 0xF));
		setFlag("C", (cpu.A + n) > 0xFF);
		cpu.A = (cpu.A + n) & 0xFF;
		setFlag("Z", cpu.A == 0);
	}

	public void CP_n8() {
		int n = lsb;
		CP(n);
	}

	public void CP(int n) {
		setFlag("Z", cpu.A == n);
		setFlag("N", true);
		setFlag("H", (cpu.A & 0xF) < (n & 0xF));
		setFlag("C", cpu.A < n);
	}

	public void CP_r8(String r8) {
		int n = read_r8(r8);
		CP(n);
	}

	public void read_lsb() {
		lsb = read_byte();
	}

	public void read_msb() {
		msb = read_byte();
	}

	public void read_HL() {
		lsb = read_r8("(HL)");
	}

	public void increment_r16(String r16) {
		write_r16(r16, (read_r16(r16) + 1) & 0xFFFF);
	}

	public void decrement_r16(String r16) {
		write_r16(r16, (read_r16(r16) - 1) & 0xFFFF);
	}

	public void increment_r8(String r8) {
		int val = (read_r8(r8) + 1) & 0xFF;
		setFlag("Z", val == 0);
		setFlag("N", false);
		setFlag("H", (read_r8(r8) & 0xF) + 1 > 0xF);
		write_r8(r8, val);
	}

	public void decrement_r8(String r8) {
		int val = (read_r8(r8) - 1) & 0xFF;
		setFlag("Z", val == 0);
		setFlag("N", true);
		setFlag("H", (read_r8(r8) & 0xF) < 1);
		write_r8(r8, val);
	}

	public void CPL() {
		cpu.A = ~cpu.A;
		cpu.A &= 0xFF;
		setFlag("N", true);
		setFlag("H", true);
	}

	public void SCF() {
		setFlag("N", false);
		setFlag("H", false);
		setFlag("C", true);
	}

	public void CCF() {
		setFlag("N", false);
		setFlag("H", false);
		setFlag("C", !getFlag("C"));
	}

	public void RRCA() {
		int bit0 = cpu.A & 0x1;
		setFlag("C", bit0 == 1);
		cpu.A >>= 1;
		cpu.A |= (bit0 << 7);
		cpu.A &= 0xFF;
		setFlag("Z", false);
		setFlag("N", false);
		setFlag("H", false);
	}

	public void RLA() {
		boolean oldC = getFlag("C");
		setFlag("C", (cpu.A & 0x80) == 0x80);
		cpu.A <<= 1;
		cpu.A |= oldC ? 1 : 0;
		cpu.A &= 0xFF;
		setFlag("Z", false);
		setFlag("N", false);
		setFlag("H", false);
	}

	public void write_r16(String r16, int val) {
		val &= 0xFFFF;
		write_r16(r16, read_MSB(val), read_LSB(val));
	}

	public void write_LSB(String r8) {
		lsb = read_r8(r8);
	}

	public void write_MSB(String r8) {
		msb = read_r8(r8);
	}

	public void write_r16_r16(String r1, String r2) {
		write_r16(r1, read_r16(r2));
	}

	public void write_abs_n16_lsb(String register) {
		int a16 = unsigned_16(lsb, msb);
		cpu.write_memory(a16, read_LSB(read_r16(register)));
	}

	public void increment_n16() {
		int n16 = unsigned_16(lsb, msb) + 1;
		n16 &= 0xFFFF;
		lsb = read_LSB(n16);
		msb = read_MSB(n16);
	}

	public void write_abs_n16_msb(String register) {
		int a16 = unsigned_16(lsb, msb);
		cpu.write_memory(a16, read_MSB(read_r16(register)));
	}

	public void write_r8_n8(String r8) {
		write_r8(r8, lsb);
	}

	public void write_r8_abs_n16(String r8) {
		write_r8(r8, cpu.read_memory(read_n16()));
	}

	public int read_n16() {
		return unsigned_16(lsb, msb);
	}

	public void write_high_r8(String r8) {
		cpu.write_memory((0xFF00 + lsb) & 0xFFFF, read_r8(r8));
	}

	public void read_high_r8(String r8) {
		write_r8(r8, cpu.read_memory((0xFF00 + lsb) & 0xFFFF));
	}

	public void write_a16_r8(String r8) {
		cpu.write_memory(unsigned_16(lsb, msb), read_r8(r8));
	}

	public void write_r16_n16(String r16) {
		write_r16(r16, msb, lsb);
	}

	public void write_r8_r8(String r1, String r2) {
		write_r8(r1, read_r8(r2));
	}

	public int read_r8(String r8) {
		if (r8.equals("saved")) {
			return lsb & 0xff;
		} else if (r8.equals("A")) {
			return cpu.A & 0xFF;
		} else if (r8.equals("B")) {
			return cpu.B & 0xFF;
		} else if (r8.equals("C")) {
			return cpu.C & 0xFF;
		} else if (r8.equals("D")) {
			return cpu.D & 0xFF;
		} else if (r8.equals("E")) {
			return cpu.E & 0xFF;
		} else if (r8.equals("F")) {
			return cpu.F & 0xFF;
		} else if (r8.equals("H")) {
			return cpu.H & 0xFF;
		} else if (r8.equals("L")) {
			return cpu.L & 0xFF;
		} else if (r8.equals("(HL)")) {
			return cpu.read_memory(read_r16("HL")) & 0xFF;
		} else if (r8.equals("(SP)")) {
			return cpu.read_memory(read_r16("SP")) & 0xFF;
		} else if (r8.equals("(BC)")) {
			return cpu.read_memory(read_r16("BC")) & 0xFF;
		} else if (r8.equals("(DE)")) {
			return cpu.read_memory(read_r16("DE")) & 0xFF;
		} else {
			System.out.println("Unknown Register: " + r8);
			System.exit(0);
			return -1;
		}
	}

	public void ORA_r8(String r8) {
		ORA(read_r8(r8));
	}

	public void ORA_n8() {
		ORA(lsb);
	}

	public void ORA(int val) {
		int result = read_r8("A") | val;
		write_r8("A", result);
		setFlag("Z", result == 0);
		setFlag("N", false);
		setFlag("H", false);
		setFlag("C", false);
	}

	public void DAA() {
		int u = 0;
		boolean FH = getFlag("H");
		boolean FN = getFlag("N");
		boolean FC = getFlag("C");
		int RA = cpu.A;
		if (FH || (!FN && (RA & 0xf) > 9)) {
			u = 6;
		}
		if (FC || (!FN && RA > 0x99)) {
			u |= 0x60;
			FC = true;
		}
		RA += FN ? -u : u;
		FH = false;
		setFlag("Z", (RA & 0xFF) == 0);
		setFlag("H", FH);
		setFlag("N", FN);
		setFlag("C", FC);
		write_r8("A", RA & 0xFF);
	}

	public void AND_n8() {
		AND(lsb);
	}

	public void AND_r8(String r8) {
		AND(read_r8(r8));
	}

	public void AND(int val) {
		int result = read_r8("A") & val;
		write_r8("A", result);
		setFlag("Z", result == 0);
		setFlag("N", false);
		setFlag("H", true);
		setFlag("C", false);
	}

	public void XOR_n8() {
		XOR(lsb);
	}

	public void XOR_r8(String r8) {
		XOR(read_r8(r8));
	}

	public void XOR(int val) {
		int result = (read_r8("A") ^ val) & 0xFF;
		write_r8("A", result);
		setFlag("Z", result == 0);
		setFlag("N", false);
		setFlag("H", false);
		setFlag("C", false);
	}

	public void RR_r8(String r8) {
		int n = read_r8(r8);
		int C = getFlag("C") ? 1 : 0;
		setFlag("N", false);
		setFlag("H", false);
		setFlag("C", (n & 0x1) == 1);

		int ans = n >>> 1;
		ans = (C << 7) | ans;
		setFlag("Z", (ans & 0xFF) == 0);
		write_r8(r8, ans & 0xFF);
		increment_PC();
	}

	public void write_r8(String r8, int val) {
		switch (r8) {
		case "saved": {
			write_r8(saved_r8, val);
			break;
		}
		case "A": {
			cpu.A = val & 0xFF;
			break;
		}
		case "B": {
			cpu.B = val & 0xFF;
			break;
		}
		case "C": {
			cpu.C = val & 0xFF;
			break;
		}
		case "D": {
			cpu.D = val & 0xFF;
			break;
		}
		case "E": {
			cpu.E = val & 0xFF;
			break;
		}
		case "F": {
			cpu.F = val & 0xFF;
			break;
		}
		case "H": {
			cpu.H = val & 0xFF;
			break;
		}
		case "L": {
			cpu.L = val & 0xFF;
			break;
		}
		case "(BC)": {
			cpu.write_memory(read_r16("BC"), val & 0xFF);
			break;
		}
		case "(HL)": {
			cpu.write_memory(read_r16("HL"), val & 0xFF);
			break;
		}
		case "(DE)": {
			cpu.write_memory(read_r16("DE"), val & 0xFF);
			break;
		}
		default: {
			System.out.println("setR8 Unknown Register" + r8);
			break;
		}
		}
	}

	public int read_r16(String r16) {
		if (r16.equals("SP")) {
			return cpu.SP;
		}
		if (r16.equals("PC")) {
			return cpu.PC;
		}
		int lsb = read_r8(r16.charAt(1) + "");
		int msb = read_r8(r16.charAt(0) + "");
		return unsigned_16(lsb, msb);
	}

	public void write_r16(String r16, int msb, int lsb) {
		if (r16.equals("SP")) {
			cpu.SP = unsigned_16(lsb, msb);
			return;
		}
		if (r16.equals("PC")) {
			cpu.PC = unsigned_16(lsb, msb);
			return;
		}
		write_r8(Character.toString(r16.charAt(0)), msb);
		write_r8(Character.toString(r16.charAt(1)), lsb);
	}

	public void write_PC_a16() {
		cpu.PC = unsigned_16(lsb, msb);
//		System.out.println(Integer.toHexString(cpu.PC));
	}

	public int read_byte() {
		int n8 = cpu.read_memory(cpu.PC) & 0xFF;
		increment_PC();
		return n8;
	}

	public void STOP() {
//		cpu.low_power_mode = true;
//		if (cpu.CGB_MODE) {
//			if ((cpu.read_memory(CPU.PREPARE_SPEED_SWITCH_REGISTER_ADDRESS) & 0x1) == 0x1) {
//				cpu.canSpeedSwitch = true;
//			}
//		}
	}

	public void increment_PC() {
		cpu.PC++;
		cpu.PC &= 0xFFFF;
	}

	public void write_Stack_r16_MSB(String r16) {
		cpu.write_memory(cpu.SP, read_MSB(read_r16(r16)));
	}

	public void write_Stack_r16_LSB(String r16) {
		int lsb = read_LSB(read_r16(r16));
		if (r16.equals("AF")) {
			lsb &= 0b11110000;
		}
		cpu.write_memory(cpu.SP, lsb);
	}

	public void read_LSB_r16(String r16) {
		lsb = cpu.read_memory(read_r16(r16));
	}

	public void read_MSB_r16(String r16) {
		msb = cpu.read_memory(read_r16(r16));
	}

	public int unsigned_16(int lsb, int msb) {
		return ((msb << 8) | lsb) & 0xFFFF;
	}

	public int read_LSB(int u16) {
		return u16 & 0xFF;
	}

	public int read_MSB(int u16) {
		return (u16 >> 8) & 0xFF;
	}

	public void setFlag(String flag, boolean set) {
		int F = cpu.F;
		if (flag.equals("Z")) {
			if (set) {
				F |= 0b10000000;
			} else {
				F &= 0b01111111;
			}
		} else if (flag.equals("N")) {
			if (set) {
				F |= 0b01000000;
			} else {
				F &= 0b10111111;
			}
		} else if (flag.equals("H")) {
			if (set) {
				F |= 0b00100000;
			} else {
				F &= 0b11011111;
			}
		} else if (flag.equals("C")) {
			if (set) {
				F |= 0b00010000;
			} else {
				F &= 0b11101111;
			}
		} else {
			System.out.println("SET FLAG NOT FOUND");
			System.exit(0);
		}

		cpu.F = F & 0xFF;
	}

	public boolean getFlag(String flag) {
		int F = cpu.F;
		if (flag.equals("NZ")) {
			return ((F >> 7) & 0x1) == 0;
		} else if (flag.equals("NC")) {
			return ((F >> 4) & 0x1) == 0;
		} else if (flag.equals("Z")) {
			return ((F >> 7) & 0x1) == 1;
		} else if (flag.equals("N")) {
			return ((F >> 6) & 0x1) == 1;
		} else if (flag.equals("H")) {
			return ((F >> 5) & 0x1) == 1;
		} else if (flag.equals("C")) {
			return ((F >> 4) & 0x1) == 1;
		} else {
			System.out.println("getFLAG NOT FOUND" + flag);
			System.exit(0);
			return false;
		}
	}

}

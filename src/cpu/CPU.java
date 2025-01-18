package cpu;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import bus.Bus;
import cartridge.Cartridge;

public class CPU {

	public int A, B, C, D, E, F, H, L;
	public int SP;
	public int PC;
	public int cycles = 0;

	private Cartridge cartridge;
	public int microCycle = 0;
	private Instructions instructions;

	public static final int PREPARE_SPEED_SWITCH_REGISTER_ADDRESS = 0xff4d;
	public static final boolean CGB_MODE = true;

	public HashMap<Integer, String[][]> opcodeToInstruction = new HashMap<>();
	public Interrupt interrupt;
	public boolean isHalted = false;

	public Bus bus;

	public CPU(Bus bus) {
		this.bus = bus;
		instructions = new Instructions(this);
		FastInstructionsAdder.addInstructions(opcodeToInstruction);
		interrupt = new Interrupt();
	}

	public int opcode;
	public boolean low_power_mode;
	public boolean canSpeedSwitch;
	public boolean doubleSpeed = false;
	public boolean canDoISR = false;

	public void step() {
		if (canDoISR) {
			interrupt.find_ISR_PC();
			instructions.decrement_r16("PC");
			int custom_opcode = interrupt.getIsrOpcode();
			interrupt.interrupt_master_enable = false;
			opcode = custom_opcode;
			canDoISR = false;
			interrupt.ISR_OPCODE = Integer.MIN_VALUE;
		}
//		if (opcodeToInstruction.containsKey(opcode) == false) {
//			System.out.println("Cycles: " + cycles);
//			System.out.println(String.format("%02X", opcode) + " NOT IMPLEMENTED YET");
//		}
//		prepareSpeedSwitch();
		try {
			executeInstruction();
		} catch (Exception e) {
			System.out.println(Integer.toHexString(opcode));
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void executeInstruction() throws Exception {
		String[][] args = opcodeToInstruction.get(opcode);
		String micro_micro_instructions[] = args[microCycle];
		for (int i = 0; i < micro_micro_instructions.length; i++) {
			String micro_micro_instruction = micro_micro_instructions[i];
			if (micro_micro_instruction.charAt(micro_micro_instruction.length() - 1) == ')') {
				StringBuilder func = new StringBuilder();
				int br = 0;
				for (int j = 0; j < micro_micro_instruction.length(); j++) {
					br++;
					if (micro_micro_instruction.charAt(j) == '(') {
						break;
					}
					func.append(micro_micro_instruction.charAt(j));
				}
				StringBuilder[] params = { new StringBuilder(), null };
				int ind = 0;
				for (int j = br; j < micro_micro_instruction.length()-1; j++) {
					char c = micro_micro_instruction.charAt(j);
					if(c == ',') {
						ind++;
						params[ind] = new StringBuilder();
						continue;
					}
					params[ind].append(c);
				}

				if (params[1] == null) {
					Method method = Instructions.class.getMethod(func.toString(), String.class);
					method.invoke(instructions, params[0].toString());
				} else {
					Method method = Instructions.class.getMethod(func.toString(), String.class, String.class);
					method.invoke(instructions, params[0].toString(), params[1].toString());
				}
				continue;
			}

			Method method = Instructions.class.getMethod(micro_micro_instruction);
			method.invoke(instructions);
		}
		microCycle++;
//		cycles += (doubleSpeed ? 2 : 4);
		cycles += 4;
		if (instructions.hasCondition && instructions.checkCondition == false) {
			microCycle = args.length - 1;
		}
		if (instructions.hasCondition) {
			instructions.hasCondition = false;
			instructions.checkCondition = false;
		}
		if (microCycle >= args.length) {
			microCycle = 0;
		}
	}

	public void setCartridge(Cartridge cartridge) {
		this.cartridge = cartridge;
	}

	public Instructions getInstructions() {
		return instructions;
	}

	public void reset() {
		A = 0x01;
//		A = 0x11;

		B = 0x00;
		C = 0x13;
		D = 0x00;
		E = 0xd8;
		F = 0xB0;
		H = 0x01;
		L = 0x4D;
		SP = 0xFFFE;
		PC = 0x100;
		bus.write(PREPARE_SPEED_SWITCH_REGISTER_ADDRESS, 0x7E);
		opcode = bus.read(PC);
		if (PC == 0x100) {
			instructions.increment_PC();
		}
	}

	public void prepareSpeedSwitch() {
		if (canSpeedSwitch && CGB_MODE) {
			canSpeedSwitch = false;
			int speed = bus.read(PREPARE_SPEED_SWITCH_REGISTER_ADDRESS);
			speed &= ((~1) & 0xFF);
			if ((speed >> 7) == 0) {
				speed |= (1 << 7);
				doubleSpeed = true;
			} else {
				speed &= 0b1111111;
				doubleSpeed = false;
			}
			bus.write(PREPARE_SPEED_SWITCH_REGISTER_ADDRESS, speed & 0xFF);
		}
	}

	public int read_memory(int address) {
		return bus.read(address & 0xFFFF) & 0xFF;
	}

	public void write_memory(int address, int val) {
		bus.write(address & 0xFFFF, val & 0xFF);
	}

	public Interrupt getInterrupt() {
		return interrupt;
	}

}

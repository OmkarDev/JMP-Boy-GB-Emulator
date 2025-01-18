//package cpu;
//
//import java.util.HashMap;
//
//public class InstructionsAdder {
//
//	public static void addInstructions(HashMap<Integer, String[]> opcodeToInstruction) {
//		// misc
//		opcodeToInstruction.put(0x00, new String[] { "NOP" });
//
//		// load or store
//		opcodeToInstruction.put(0x21, new String[] { "read_lsb", "read_msb", "fetch&write_r16_n16(HL)" });
//		opcodeToInstruction.put(0x11, new String[] { "read_lsb", "read_msb", "fetch&write_r16_n16(DE)" });
//		opcodeToInstruction.put(0x31, new String[] { "read_lsb", "read_msb", "fetch&write_r16_n16(SP)" });
//		opcodeToInstruction.put(0x01, new String[] { "read_lsb", "read_msb", "fetch&write_r16_n16(BC)" });
//		opcodeToInstruction.put(0x08, new String[] { "read_lsb", "read_msb", "write_abs_n16_lsb(SP)&increment_n16",
//				"write_abs_n16_msb(SP)", "fetch" });
//
//		opcodeToInstruction.put(0xF9, new String[] { "write_r16_r16(SP,HL)", "fetch" });
//
//		opcodeToInstruction.put(0x0E, new String[] { "read_lsb", "fetch&write_r8_n8(C)" });
//		opcodeToInstruction.put(0x1E, new String[] { "read_lsb", "fetch&write_r8_n8(E)" });
//		opcodeToInstruction.put(0x2E, new String[] { "read_lsb", "fetch&write_r8_n8(L)" });
//		opcodeToInstruction.put(0x3E, new String[] { "read_lsb", "fetch&write_r8_n8(A)" });
//
//		opcodeToInstruction.put(0x06, new String[] { "read_lsb", "fetch&write_r8_n8(B)" });
//		opcodeToInstruction.put(0x16, new String[] { "read_lsb", "fetch&write_r8_n8(D)" });
//		opcodeToInstruction.put(0x26, new String[] { "read_lsb", "fetch&write_r8_n8(H)" });
//
//		opcodeToInstruction.put(0xF8, new String[] { "read_lsb", "LD_HL_SP_e8", "fetch" });
//
//		opcodeToInstruction.put(0x22, new String[] { "write_r8_r8((HL),A)&increment_r16(HL)", "fetch" });
//		opcodeToInstruction.put(0x32, new String[] { "write_r8_r8((HL),A)&decrement_r16(HL)", "fetch" });
//
//		opcodeToInstruction.put(0xE0, new String[] { "read_lsb", "write_high_r8(A)", "fetch" });
//		opcodeToInstruction.put(0xF0, new String[] { "read_lsb", "read_high_r8(A)", "fetch" });
//		opcodeToInstruction.put(0xE2, new String[] { "write_LSB(C)&write_high_r8(A)", "fetch" });
//		opcodeToInstruction.put(0xF2, new String[] { "write_LSB(C)&read_high_r8(A)", "fetch" });
//
//		opcodeToInstruction.put(0xFA, new String[] { "read_lsb", "read_msb", "write_r8_abs_n16(A)", "fetch" });
//
//		opcodeToInstruction.put(0x2A, new String[] { "save_r8((HL))&increment_r16(HL)", "fetch&write_r8_n8(A)" });
//		opcodeToInstruction.put(0x3A, new String[] { "save_r8((HL))&decrement_r16(HL)", "fetch&write_r8_n8(A)" });
//
//		opcodeToInstruction.put(0x02, new String[] { "write_r8_r8((BC),A)", "fetch" });
//		opcodeToInstruction.put(0x12, new String[] { "write_r8_r8((DE),A)", "fetch" });
//
//		opcodeToInstruction.put(0x46, new String[] { "write_r8_r8(B,(HL))", "fetch" });
//		opcodeToInstruction.put(0x4E, new String[] { "write_r8_r8(C,(HL))", "fetch" });
//		opcodeToInstruction.put(0x5E, new String[] { "write_r8_r8(E,(HL))", "fetch" });
//		opcodeToInstruction.put(0x6E, new String[] { "write_r8_r8(L,(HL))", "fetch" });
//
//		opcodeToInstruction.put(0x0A, new String[] { "write_r8_r8(A,(BC))", "fetch" });
//		opcodeToInstruction.put(0x1A, new String[] { "write_r8_r8(A,(DE))", "fetch" });
//
//		opcodeToInstruction.put(0xEA, new String[] { "read_lsb", "read_msb", "write_a16_r8(A)", "fetch" });
//
//		opcodeToInstruction.put(0x78, new String[] { "fetch&write_r8_r8(A,B)" });
//		opcodeToInstruction.put(0x79, new String[] { "fetch&write_r8_r8(A,C)" });
//		opcodeToInstruction.put(0x7A, new String[] { "fetch&write_r8_r8(A,D)" });
//		opcodeToInstruction.put(0x7B, new String[] { "fetch&write_r8_r8(A,E)" });
//		opcodeToInstruction.put(0x7C, new String[] { "fetch&write_r8_r8(A,H)" });
//		opcodeToInstruction.put(0x7D, new String[] { "fetch&write_r8_r8(A,L)" });
//		opcodeToInstruction.put(0x7E, new String[] { "write_r8_r8(A,(HL))", "fetch" });
//		opcodeToInstruction.put(0x7F, new String[] { "fetch&write_r8_r8(A,A)" });
//
//		opcodeToInstruction.put(0x47, new String[] { "fetch&write_r8_r8(B,A)" });
//		opcodeToInstruction.put(0x40, new String[] { "fetch&write_r8_r8(B,B)" });
//		opcodeToInstruction.put(0x41, new String[] { "fetch&write_r8_r8(B,C)" });
//		opcodeToInstruction.put(0x42, new String[] { "fetch&write_r8_r8(B,D)" });
//		opcodeToInstruction.put(0x43, new String[] { "fetch&write_r8_r8(B,E)" });
//		opcodeToInstruction.put(0x44, new String[] { "fetch&write_r8_r8(B,H)" });
//		opcodeToInstruction.put(0x45, new String[] { "fetch&write_r8_r8(B,L)" });
//
//		opcodeToInstruction.put(0x50, new String[] { "fetch&write_r8_r8(D,B)" });
//		opcodeToInstruction.put(0x51, new String[] { "fetch&write_r8_r8(D,C)" });
//		opcodeToInstruction.put(0x52, new String[] { "fetch&write_r8_r8(D,D)" });
//		opcodeToInstruction.put(0x53, new String[] { "fetch&write_r8_r8(D,E)" });
//		opcodeToInstruction.put(0x54, new String[] { "fetch&write_r8_r8(D,H)" });
//		opcodeToInstruction.put(0x55, new String[] { "fetch&write_r8_r8(D,L)" });
//		opcodeToInstruction.put(0x56, new String[] { "write_r8_r8(D,(HL))", "fetch" });
//		opcodeToInstruction.put(0x57, new String[] { "fetch&write_r8_r8(D,A)" });
//
//		opcodeToInstruction.put(0x58, new String[] { "fetch&write_r8_r8(E,B)" });
//		opcodeToInstruction.put(0x59, new String[] { "fetch&write_r8_r8(E,C)" });
//		opcodeToInstruction.put(0x5A, new String[] { "fetch&write_r8_r8(E,D)" });
//		opcodeToInstruction.put(0x5B, new String[] { "fetch&write_r8_r8(E,E)" });
//		opcodeToInstruction.put(0x5C, new String[] { "fetch&write_r8_r8(E,H)" });
//		opcodeToInstruction.put(0x5D, new String[] { "fetch&write_r8_r8(E,L)" });
//		opcodeToInstruction.put(0x5E, new String[] { "write_r8_r8(E,(HL))", "fetch" });
//		opcodeToInstruction.put(0x5F, new String[] { "fetch&write_r8_r8(E,A)" });
//
//		opcodeToInstruction.put(0x68, new String[] { "fetch&write_r8_r8(L,B)" });
//		opcodeToInstruction.put(0x69, new String[] { "fetch&write_r8_r8(L,C)" });
//		opcodeToInstruction.put(0x6A, new String[] { "fetch&write_r8_r8(L,D)" });
//		opcodeToInstruction.put(0x6B, new String[] { "fetch&write_r8_r8(L,E)" });
//		opcodeToInstruction.put(0x6C, new String[] { "fetch&write_r8_r8(L,H)" });
//		opcodeToInstruction.put(0x6D, new String[] { "fetch&write_r8_r8(L,L)" });
//		opcodeToInstruction.put(0x6E, new String[] { "write_r8_r8(L,(HL))", "fetch" });
//		opcodeToInstruction.put(0x6F, new String[] { "fetch&write_r8_r8(L,A)" });
//
//		opcodeToInstruction.put(0x48, new String[] { "fetch&write_r8_r8(C,B)" });
//		opcodeToInstruction.put(0x49, new String[] { "fetch&write_r8_r8(C,C)" });
//		opcodeToInstruction.put(0x4A, new String[] { "fetch&write_r8_r8(C,D)" });
//		opcodeToInstruction.put(0x4B, new String[] { "fetch&write_r8_r8(C,E)" });
//		opcodeToInstruction.put(0x4C, new String[] { "fetch&write_r8_r8(C,H)" });
//		opcodeToInstruction.put(0x4D, new String[] { "fetch&write_r8_r8(C,L)" });
//		opcodeToInstruction.put(0x4E, new String[] { "write_r8_r8(C,(HL))", "fetch" });
//		opcodeToInstruction.put(0x4F, new String[] { "fetch&write_r8_r8(C,A)" });
//
//		opcodeToInstruction.put(0x60, new String[] { "fetch&write_r8_r8(H,B)" });
//		opcodeToInstruction.put(0x61, new String[] { "fetch&write_r8_r8(H,C)" });
//		opcodeToInstruction.put(0x62, new String[] { "fetch&write_r8_r8(H,D)" });
//		opcodeToInstruction.put(0x63, new String[] { "fetch&write_r8_r8(H,E)" });
//		opcodeToInstruction.put(0x64, new String[] { "fetch&write_r8_r8(H,H)" });
//		opcodeToInstruction.put(0x65, new String[] { "fetch&write_r8_r8(H,L)" });
//		opcodeToInstruction.put(0x66, new String[] { "write_r8_r8(H,(HL))", "fetch" });
//		opcodeToInstruction.put(0x67, new String[] { "fetch&write_r8_r8(H,A)" });
//
//		opcodeToInstruction.put(0x70, new String[] { "write_r8_r8((HL),B)", "fetch" });
//		opcodeToInstruction.put(0x71, new String[] { "write_r8_r8((HL),C)", "fetch" });
//		opcodeToInstruction.put(0x72, new String[] { "write_r8_r8((HL),D)", "fetch" });
//		opcodeToInstruction.put(0x73, new String[] { "write_r8_r8((HL),E)", "fetch" });
//		opcodeToInstruction.put(0x74, new String[] { "write_r8_r8((HL),H)", "fetch" });
//		opcodeToInstruction.put(0x75, new String[] { "write_r8_r8((HL),L)", "fetch" });
//		opcodeToInstruction.put(0x77, new String[] { "write_r8_r8((HL),A)", "fetch" });
//		opcodeToInstruction.put(0x36, new String[] { "read_lsb", "write_r8_n8((HL))", "fetch" });
//
//		// arithmetic
//		opcodeToInstruction.put(0x80, new String[] { "ADD_r8(B)&fetch" });
//		opcodeToInstruction.put(0x81, new String[] { "ADD_r8(C)&fetch" });
//		opcodeToInstruction.put(0x82, new String[] { "ADD_r8(D)&fetch" });
//		opcodeToInstruction.put(0x83, new String[] { "ADD_r8(E)&fetch" });
//		opcodeToInstruction.put(0x84, new String[] { "ADD_r8(H)&fetch" });
//		opcodeToInstruction.put(0x85, new String[] { "ADD_r8(L)&fetch" });
//		opcodeToInstruction.put(0x86, new String[] { "ADD_r8((HL))", "fetch" });
//		opcodeToInstruction.put(0x87, new String[] { "ADD_r8(A)&fetch" });
//		opcodeToInstruction.put(0xC6, new String[] { "read_lsb", "ADD_n8&fetch" });
//
//		opcodeToInstruction.put(0x09, new String[] { "ADD_HL_r16(BC)", "fetch" });
//		opcodeToInstruction.put(0x19, new String[] { "ADD_HL_r16(DE)", "fetch" });
//		opcodeToInstruction.put(0x29, new String[] { "ADD_HL_r16(HL)", "fetch" });
//		opcodeToInstruction.put(0x39, new String[] { "ADD_HL_r16(SP)", "fetch" });
//
//		// no_op to pass cycles
//		opcodeToInstruction.put(0xE8, new String[] { "read_lsb", "no_op", "ADD_SP_e8", "fetch" });
//
//		opcodeToInstruction.put(0x88, new String[] { "ADC_r8(B)&fetch" });
//		opcodeToInstruction.put(0x89, new String[] { "ADC_r8(C)&fetch" });
//		opcodeToInstruction.put(0x8A, new String[] { "ADC_r8(D)&fetch" });
//		opcodeToInstruction.put(0x8B, new String[] { "ADC_r8(E)&fetch" });
//		opcodeToInstruction.put(0x8C, new String[] { "ADC_r8(H)&fetch" });
//		opcodeToInstruction.put(0x8D, new String[] { "ADC_r8(L)&fetch" });
//		opcodeToInstruction.put(0x8E, new String[] { "ADC_r8((HL))", "fetch" });
//		opcodeToInstruction.put(0x8F, new String[] { "ADC_r8(A)&fetch" });
//		opcodeToInstruction.put(0xCE, new String[] { "read_lsb", "ADC_n8&fetch" });
//
//		opcodeToInstruction.put(0x90, new String[] { "SUB_r8(B)&fetch" });
//		opcodeToInstruction.put(0x91, new String[] { "SUB_r8(C)&fetch" });
//		opcodeToInstruction.put(0x92, new String[] { "SUB_r8(D)&fetch" });
//		opcodeToInstruction.put(0x93, new String[] { "SUB_r8(E)&fetch" });
//		opcodeToInstruction.put(0x94, new String[] { "SUB_r8(H)&fetch" });
//		opcodeToInstruction.put(0x95, new String[] { "SUB_r8(L)&fetch" });
//		opcodeToInstruction.put(0x96, new String[] { "SUB_r8((HL))", "fetch" });
//		opcodeToInstruction.put(0x97, new String[] { "SUB_r8(A)&fetch" });
//		opcodeToInstruction.put(0xD6, new String[] { "read_lsb", "SUB_n8&fetch" });
//
//		opcodeToInstruction.put(0x98, new String[] { "SBC_r8(B)&fetch" });
//		opcodeToInstruction.put(0x99, new String[] { "SBC_r8(C)&fetch" });
//		opcodeToInstruction.put(0x9A, new String[] { "SBC_r8(D)&fetch" });
//		opcodeToInstruction.put(0x9B, new String[] { "SBC_r8(E)&fetch" });
//		opcodeToInstruction.put(0x9C, new String[] { "SBC_r8(H)&fetch" });
//		opcodeToInstruction.put(0x9D, new String[] { "SBC_r8(L)&fetch" });
//		opcodeToInstruction.put(0x9E, new String[] { "SBC_r8((HL))", "fetch" });
//		opcodeToInstruction.put(0x9F, new String[] { "SBC_r8(A)&fetch" });
//		opcodeToInstruction.put(0xDE, new String[] { "read_lsb", "SBC_n8&fetch" });
//
//		opcodeToInstruction.put(0x0C, new String[] { "fetch&increment_r8(C)" });
//		opcodeToInstruction.put(0x1C, new String[] { "fetch&increment_r8(E)" });
//		opcodeToInstruction.put(0x2C, new String[] { "fetch&increment_r8(L)" });
//		opcodeToInstruction.put(0x3C, new String[] { "fetch&increment_r8(A)" });
//
//		opcodeToInstruction.put(0x04, new String[] { "fetch&increment_r8(B)" });
//		opcodeToInstruction.put(0x14, new String[] { "fetch&increment_r8(D)" });
//		opcodeToInstruction.put(0x24, new String[] { "fetch&increment_r8(H)" });
//		opcodeToInstruction.put(0x34, new String[] { "save_r8((HL))", "increment_r8(saved)", "fetch" });
//
//		opcodeToInstruction.put(0x05, new String[] { "fetch&decrement_r8(B)" });
//		opcodeToInstruction.put(0x15, new String[] { "fetch&decrement_r8(D)" });
//		opcodeToInstruction.put(0x25, new String[] { "fetch&decrement_r8(H)" });
//
//		opcodeToInstruction.put(0x35, new String[] { "save_r8((HL))", "decrement_r8(saved)", "fetch" });
//
//		opcodeToInstruction.put(0x0D, new String[] { "fetch&decrement_r8(C)" });
//		opcodeToInstruction.put(0x1D, new String[] { "fetch&decrement_r8(E)" });
//		opcodeToInstruction.put(0x2D, new String[] { "fetch&decrement_r8(L)" });
//		opcodeToInstruction.put(0x3D, new String[] { "fetch&decrement_r8(A)" });
//
//		opcodeToInstruction.put(0xFE, new String[] { "read_lsb", "CP_n8&fetch" });
//		opcodeToInstruction.put(0xB8, new String[] { "CP_r8(B)&fetch" });
//		opcodeToInstruction.put(0xB9, new String[] { "CP_r8(C)&fetch" });
//		opcodeToInstruction.put(0xBA, new String[] { "CP_r8(D)&fetch" });
//		opcodeToInstruction.put(0xBB, new String[] { "CP_r8(E)&fetch" });
//		opcodeToInstruction.put(0xBC, new String[] { "CP_r8(H)&fetch" });
//		opcodeToInstruction.put(0xBD, new String[] { "CP_r8(L)&fetch" });
//		opcodeToInstruction.put(0xBE, new String[] { "CP_r8((HL))", "fetch" });
//		opcodeToInstruction.put(0xBF, new String[] { "CP_r8(A)&fetch" });
//
//		opcodeToInstruction.put(0x03, new String[] { "increment_r16(BC)", "fetch" });
//		opcodeToInstruction.put(0x13, new String[] { "increment_r16(DE)", "fetch" });
//		opcodeToInstruction.put(0x23, new String[] { "increment_r16(HL)", "fetch" });
//		opcodeToInstruction.put(0x33, new String[] { "increment_r16(SP)", "fetch" });
//
//		opcodeToInstruction.put(0x0B, new String[] { "decrement_r16(BC)", "fetch" });
//		opcodeToInstruction.put(0x1B, new String[] { "decrement_r16(DE)", "fetch" });
//		opcodeToInstruction.put(0x2B, new String[] { "decrement_r16(HL)", "fetch" });
//		opcodeToInstruction.put(0x3B, new String[] { "decrement_r16(SP)", "fetch" });
//
//		// jump
//		opcodeToInstruction.put(0xC3, new String[] { "read_lsb", "read_msb", "write_PC_a16", "fetch" });
//		opcodeToInstruction.put(0x20, new String[] { "read_lsb&checkCondition(NZ)", "JR_e8", "fetch" });
//		opcodeToInstruction.put(0x30, new String[] { "read_lsb&checkCondition(NC)", "JR_e8", "fetch" });
//		opcodeToInstruction.put(0x28, new String[] { "read_lsb&checkCondition(Z)", "JR_e8", "fetch" });
//		opcodeToInstruction.put(0x38, new String[] { "read_lsb&checkCondition(C)", "JR_e8", "fetch" });
//		opcodeToInstruction.put(0x18, new String[] { "read_lsb", "JR_e8", "fetch" });
//
//		opcodeToInstruction.put(0xE9, new String[] { "JP_HL&fetch" });
//
//		opcodeToInstruction.put(0xC2,
//				new String[] { "read_lsb", "read_msb&checkCondition(NZ)", "write_PC_a16", "fetch" });
//		opcodeToInstruction.put(0xD2,
//				new String[] { "read_lsb", "read_msb&checkCondition(NC)", "write_PC_a16", "fetch" });
//		opcodeToInstruction.put(0xCA,
//				new String[] { "read_lsb", "read_msb&checkCondition(Z)", "write_PC_a16", "fetch" });
//		opcodeToInstruction.put(0xDA,
//				new String[] { "read_lsb", "read_msb&checkCondition(C)", "write_PC_a16", "fetch" });
//
//		// CALL
//		opcodeToInstruction.put(0xCD, new String[] { "read_lsb", "read_msb", "decrement_r16(SP)",
//				"write_Stack_r16_MSB(PC)&decrement_r16(SP)", "write_Stack_r16_LSB(PC)&write_PC_a16", "fetch" });
//		opcodeToInstruction.put(0xC4, new String[] { "read_lsb", "read_msb&checkCondition(NZ)", "decrement_r16(SP)",
//				"write_Stack_r16_MSB(PC)&decrement_r16(SP)", "write_Stack_r16_LSB(PC)&write_PC_a16", "fetch" });
//		opcodeToInstruction.put(0xD4, new String[] { "read_lsb", "read_msb&checkCondition(NC)", "decrement_r16(SP)",
//				"write_Stack_r16_MSB(PC)&decrement_r16(SP)", "write_Stack_r16_LSB(PC)&write_PC_a16", "fetch" });
//		opcodeToInstruction.put(0xCC, new String[] { "read_lsb", "read_msb&checkCondition(Z)", "decrement_r16(SP)",
//				"write_Stack_r16_MSB(PC)&decrement_r16(SP)", "write_Stack_r16_LSB(PC)&write_PC_a16", "fetch" });
//		opcodeToInstruction.put(0xDC, new String[] { "read_lsb", "read_msb&checkCondition(C)", "decrement_r16(SP)",
//				"write_Stack_r16_MSB(PC)&decrement_r16(SP)", "write_Stack_r16_LSB(PC)&write_PC_a16", "fetch" });
//
//		// RET
//		opcodeToInstruction.put(0xC7, new String[] { "decrement_r16(SP)", "write_Stack_r16_MSB(PC)&decrement_r16(SP)",
//				"write_Stack_r16_LSB(PC)&write_PC(00)", "fetch" });
//		opcodeToInstruction.put(0xD7, new String[] { "decrement_r16(SP)", "write_Stack_r16_MSB(PC)&decrement_r16(SP)",
//				"write_Stack_r16_LSB(PC)&write_PC(10)", "fetch" });
//		opcodeToInstruction.put(0xE7, new String[] { "decrement_r16(SP)", "write_Stack_r16_MSB(PC)&decrement_r16(SP)",
//				"write_Stack_r16_LSB(PC)&write_PC(20)", "fetch" });
//		opcodeToInstruction.put(0xF7, new String[] { "decrement_r16(SP)", "write_Stack_r16_MSB(PC)&decrement_r16(SP)",
//				"write_Stack_r16_LSB(PC)&write_PC(30)", "fetch" });
//
//		opcodeToInstruction.put(0xCF, new String[] { "decrement_r16(SP)", "write_Stack_r16_MSB(PC)&decrement_r16(SP)",
//				"write_Stack_r16_LSB(PC)&write_PC(08)", "fetch" });
//		opcodeToInstruction.put(0xDF, new String[] { "decrement_r16(SP)", "write_Stack_r16_MSB(PC)&decrement_r16(SP)",
//				"write_Stack_r16_LSB(PC)&write_PC(18)", "fetch" });
//		opcodeToInstruction.put(0xEF, new String[] { "decrement_r16(SP)", "write_Stack_r16_MSB(PC)&decrement_r16(SP)",
//				"write_Stack_r16_LSB(PC)&write_PC(28)", "fetch" });
//		opcodeToInstruction.put(0xFF, new String[] { "decrement_r16(SP)", "write_Stack_r16_MSB(PC)&decrement_r16(SP)",
//				"write_Stack_r16_LSB(PC)&write_PC(38)", "fetch" });
//
//		opcodeToInstruction.put(0xC9, new String[] { "read_LSB_r16(SP)&increment_r16(SP)",
//				"read_MSB_r16(SP)&increment_r16(SP)", "write_r16_n16(PC)", "fetch" });
//
//		opcodeToInstruction.put(0xC0, new String[] { "checkCondition(NZ)", "read_LSB_r16(SP)&increment_r16(SP)",
//				"read_MSB_r16(SP)&increment_r16(SP)", "write_r16_n16(PC)", "fetch" });
//		opcodeToInstruction.put(0xD0, new String[] { "checkCondition(NC)", "read_LSB_r16(SP)&increment_r16(SP)",
//				"read_MSB_r16(SP)&increment_r16(SP)", "write_r16_n16(PC)", "fetch" });
//		opcodeToInstruction.put(0xC8, new String[] { "checkCondition(Z)", "read_LSB_r16(SP)&increment_r16(SP)",
//				"read_MSB_r16(SP)&increment_r16(SP)", "write_r16_n16(PC)", "fetch" });
//		opcodeToInstruction.put(0xD8, new String[] { "checkCondition(C)", "read_LSB_r16(SP)&increment_r16(SP)",
//				"read_MSB_r16(SP)&increment_r16(SP)", "write_r16_n16(PC)", "fetch" });
//
//		// stack
//		opcodeToInstruction.put(0xC5, new String[] { "decrement_r16(SP)", "write_Stack_r16_MSB(BC)&decrement_r16(SP)",
//				"write_Stack_r16_LSB(BC)", "fetch" });
//		opcodeToInstruction.put(0xD5, new String[] { "decrement_r16(SP)", "write_Stack_r16_MSB(DE)&decrement_r16(SP)",
//				"write_Stack_r16_LSB(DE)", "fetch" });
//		opcodeToInstruction.put(0xE5, new String[] { "decrement_r16(SP)", "write_Stack_r16_MSB(HL)&decrement_r16(SP)",
//				"write_Stack_r16_LSB(HL)", "fetch" });
//
//		opcodeToInstruction.put(0xF5, new String[] { "decrement_r16(SP)", "write_Stack_r16_MSB(AF)&decrement_r16(SP)",
//				"write_Stack_r16_LSB(AF)", "fetch" });
//
//		opcodeToInstruction.put(0xC1, new String[] { "write_LSB((SP))&increment_r16(SP)",
//				"write_MSB((SP))&increment_r16(SP)", "fetch&write_r16_n16(BC)" });
//		opcodeToInstruction.put(0xD1, new String[] { "write_LSB((SP))&increment_r16(SP)",
//				"write_MSB((SP))&increment_r16(SP)", "fetch&write_r16_n16(DE)" });
//		opcodeToInstruction.put(0xE1, new String[] { "write_LSB((SP))&increment_r16(SP)",
//				"write_MSB((SP))&increment_r16(SP)", "fetch&write_r16_n16(HL)" });
//		opcodeToInstruction.put(0xF1, new String[] { "write_LSB((SP))&increment_r16(SP)",
//				"write_MSB((SP))&increment_r16(SP)", "fetch&write_r16_n16(AF)" });
//
//		// interrupts
//		opcodeToInstruction.put(0xF3, new String[] { "DI&fetch" });
//		opcodeToInstruction.put(0x76, new String[] { "HALT" });
//		opcodeToInstruction.put(0xFB, new String[] { "fetch&EI" });
//		opcodeToInstruction.put(0xD9, new String[] { "write_LSB((SP))&increment_r16(SP)",
//				"write_MSB((SP))&increment_r16(SP)", "write_r16_n16(PC)", "EI&fetch" });
//
//		opcodeToInstruction.put(0x10, new String[] { "STOP&fetch" });
//
//		// bit operations
//
//		opcodeToInstruction.put(0x2F, new String[] { "CPL&fetch" });
//		opcodeToInstruction.put(0x37, new String[] { "SCF&fetch" });
//		opcodeToInstruction.put(0x3F, new String[] { "CCF&fetch" });
//		opcodeToInstruction.put(0x17, new String[] { "RLA&fetch" });
//		opcodeToInstruction.put(0x07, new String[] { "RLCA&fetch" });
//		opcodeToInstruction.put(0x1F, new String[] { "RRA&fetch" });
//		opcodeToInstruction.put(0x0F, new String[] { "RRCA&fetch" });
//		opcodeToInstruction.put(0x27, new String[] { "DAA&fetch" });
//
//		opcodeToInstruction.put(0xB0, new String[] { "ORA_r8(B)&fetch" });
//		opcodeToInstruction.put(0xB1, new String[] { "ORA_r8(C)&fetch" });
//		opcodeToInstruction.put(0xB2, new String[] { "ORA_r8(D)&fetch" });
//		opcodeToInstruction.put(0xB3, new String[] { "ORA_r8(E)&fetch" });
//		opcodeToInstruction.put(0xB4, new String[] { "ORA_r8(H)&fetch" });
//		opcodeToInstruction.put(0xB5, new String[] { "ORA_r8(L)&fetch" });
//		opcodeToInstruction.put(0xB6, new String[] { "ORA_r8((HL))", "fetch" });
//		opcodeToInstruction.put(0xB7, new String[] { "ORA_r8(A)&fetch" });
//		opcodeToInstruction.put(0xF6, new String[] { "read_lsb", "ORA_n8&fetch" });
//
//		opcodeToInstruction.put(0xA0, new String[] { "AND_r8(B)&fetch" });
//		opcodeToInstruction.put(0xA1, new String[] { "AND_r8(C)&fetch" });
//		opcodeToInstruction.put(0xA2, new String[] { "AND_r8(D)&fetch" });
//		opcodeToInstruction.put(0xA3, new String[] { "AND_r8(E)&fetch" });
//		opcodeToInstruction.put(0xA4, new String[] { "AND_r8(H)&fetch" });
//		opcodeToInstruction.put(0xA5, new String[] { "AND_r8(L)&fetch" });
//		opcodeToInstruction.put(0xA6, new String[] { "AND_r8((HL))", "fetch" });
//		opcodeToInstruction.put(0xA7, new String[] { "AND_r8(A)&fetch" });
//		opcodeToInstruction.put(0xE6, new String[] { "read_lsb", "AND_n8&fetch" });
//
//		opcodeToInstruction.put(0xA8, new String[] { "XOR_r8(B)&fetch" });
//		opcodeToInstruction.put(0xA9, new String[] { "XOR_r8(C)&fetch" });
//		opcodeToInstruction.put(0xAA, new String[] { "XOR_r8(D)&fetch" });
//		opcodeToInstruction.put(0xAB, new String[] { "XOR_r8(E)&fetch" });
//		opcodeToInstruction.put(0xAC, new String[] { "XOR_r8(H)&fetch" });
//		opcodeToInstruction.put(0xAD, new String[] { "XOR_r8(L)&fetch" });
//		opcodeToInstruction.put(0xAE, new String[] { "XOR_r8((HL))", "fetch" });
//		opcodeToInstruction.put(0xAF, new String[] { "XOR_r8(A)&fetch" });
//		opcodeToInstruction.put(0xEE, new String[] { "read_lsb", "XOR_n8&fetch" });
//
//		// no_op does nothing, just to make cycle accurate
//		opcodeToInstruction.put(0xCB00, new String[] { "RLC_r8(B)", "fetch" });
//		opcodeToInstruction.put(0xCB01, new String[] { "RLC_r8(C)", "fetch" });
//		opcodeToInstruction.put(0xCB02, new String[] { "RLC_r8(D)", "fetch" });
//		opcodeToInstruction.put(0xCB03, new String[] { "RLC_r8(E)", "fetch" });
//		opcodeToInstruction.put(0xCB04, new String[] { "RLC_r8(H)", "fetch" });
//		opcodeToInstruction.put(0xCB05, new String[] { "RLC_r8(L)", "fetch" });
//		opcodeToInstruction.put(0xCB06, new String[] { "no_op", "save_r8((HL))", "RLC_r8(saved)", "fetch" });
//		opcodeToInstruction.put(0xCB07, new String[] { "RLC_r8(A)", "fetch" });
//
//		opcodeToInstruction.put(0xCB08, new String[] { "RRC_r8(B)", "fetch" });
//		opcodeToInstruction.put(0xCB09, new String[] { "RRC_r8(C)", "fetch" });
//		opcodeToInstruction.put(0xCB0A, new String[] { "RRC_r8(D)", "fetch" });
//		opcodeToInstruction.put(0xCB0B, new String[] { "RRC_r8(E)", "fetch" });
//		opcodeToInstruction.put(0xCB0C, new String[] { "RRC_r8(H)", "fetch" });
//		opcodeToInstruction.put(0xCB0D, new String[] { "RRC_r8(L)", "fetch" });
//		opcodeToInstruction.put(0xCB0E, new String[] { "no_op", "save_r8((HL))", "RRC_r8(saved)", "fetch" });
//		opcodeToInstruction.put(0xCB0F, new String[] { "RRC_r8(A)", "fetch" });
//
//		opcodeToInstruction.put(0xCB10, new String[] { "RL_r8(B)", "fetch" });
//		opcodeToInstruction.put(0xCB11, new String[] { "RL_r8(C)", "fetch" });
//		opcodeToInstruction.put(0xCB12, new String[] { "RL_r8(D)", "fetch" });
//		opcodeToInstruction.put(0xCB13, new String[] { "RL_r8(E)", "fetch" });
//		opcodeToInstruction.put(0xCB14, new String[] { "RL_r8(H)", "fetch" });
//		opcodeToInstruction.put(0xCB15, new String[] { "RL_r8(L)", "fetch" });
//		opcodeToInstruction.put(0xCB16, new String[] { "no_op", "save_r8((HL))", "RL_r8(saved)", "fetch" });
//		opcodeToInstruction.put(0xCB17, new String[] { "RL_r8(A)", "fetch" });
//
//		opcodeToInstruction.put(0xCB18, new String[] { "RR_r8(B)", "fetch" });
//		opcodeToInstruction.put(0xCB19, new String[] { "RR_r8(C)", "fetch" });
//		opcodeToInstruction.put(0xCB1A, new String[] { "RR_r8(D)", "fetch" });
//		opcodeToInstruction.put(0xCB1B, new String[] { "RR_r8(E)", "fetch" });
//		opcodeToInstruction.put(0xCB1C, new String[] { "RR_r8(H)", "fetch" });
//		opcodeToInstruction.put(0xCB1D, new String[] { "RR_r8(L)", "fetch" });
//		opcodeToInstruction.put(0xCB1E, new String[] { "no_op", "save_r8((HL))", "RR_r8(saved)", "fetch" });
//		opcodeToInstruction.put(0xCB1F, new String[] { "RR_r8(A)", "fetch" });
//
//		opcodeToInstruction.put(0xCB20, new String[] { "SLA_r8(B)", "fetch" });
//		opcodeToInstruction.put(0xCB21, new String[] { "SLA_r8(C)", "fetch" });
//		opcodeToInstruction.put(0xCB22, new String[] { "SLA_r8(D)", "fetch" });
//		opcodeToInstruction.put(0xCB23, new String[] { "SLA_r8(E)", "fetch" });
//		opcodeToInstruction.put(0xCB24, new String[] { "SLA_r8(H)", "fetch" });
//		opcodeToInstruction.put(0xCB25, new String[] { "SLA_r8(L)", "fetch" });
//		opcodeToInstruction.put(0xCB26, new String[] { "no_op", "save_r8((HL))", "SLA_r8(saved)", "fetch" });
//		opcodeToInstruction.put(0xCB27, new String[] { "SLA_r8(A)", "fetch" });
//
//		opcodeToInstruction.put(0xCB28, new String[] { "SRA_r8(B)", "fetch" });
//		opcodeToInstruction.put(0xCB29, new String[] { "SRA_r8(C)", "fetch" });
//		opcodeToInstruction.put(0xCB2A, new String[] { "SRA_r8(D)", "fetch" });
//		opcodeToInstruction.put(0xCB2B, new String[] { "SRA_r8(E)", "fetch" });
//		opcodeToInstruction.put(0xCB2C, new String[] { "SRA_r8(H)", "fetch" });
//		opcodeToInstruction.put(0xCB2D, new String[] { "SRA_r8(L)", "fetch" });
//		opcodeToInstruction.put(0xCB2E, new String[] { "no_op", "save_r8((HL))", "SRA_r8(saved)", "fetch" });
//		opcodeToInstruction.put(0xCB2F, new String[] { "SRA_r8(A)", "fetch" });
//
//		opcodeToInstruction.put(0xCB30, new String[] { "SWAP(B)", "fetch" });
//		opcodeToInstruction.put(0xCB31, new String[] { "SWAP(C)", "fetch" });
//		opcodeToInstruction.put(0xCB32, new String[] { "SWAP(D)", "fetch" });
//		opcodeToInstruction.put(0xCB33, new String[] { "SWAP(E)", "fetch" });
//		opcodeToInstruction.put(0xCB34, new String[] { "SWAP(H)", "fetch" });
//		opcodeToInstruction.put(0xCB35, new String[] { "SWAP(L)", "fetch" });
//		opcodeToInstruction.put(0xCB36, new String[] { "no_op", "save_r8((HL))", "SWAP(saved)", "fetch" });
//		opcodeToInstruction.put(0xCB37, new String[] { "SWAP(A)", "fetch" });
//
//		opcodeToInstruction.put(0xCB38, new String[] { "SRL_r8(B)", "fetch" });
//		opcodeToInstruction.put(0xCB39, new String[] { "SRL_r8(C)", "fetch" });
//		opcodeToInstruction.put(0xCB3A, new String[] { "SRL_r8(D)", "fetch" });
//		opcodeToInstruction.put(0xCB3B, new String[] { "SRL_r8(E)", "fetch" });
//		opcodeToInstruction.put(0xCB3C, new String[] { "SRL_r8(H)", "fetch" });
//		opcodeToInstruction.put(0xCB3D, new String[] { "SRL_r8(L)", "fetch" });
//		opcodeToInstruction.put(0xCB3E, new String[] { "no_op", "save_r8((HL))", "SRL_r8(saved)", "fetch" });
//		opcodeToInstruction.put(0xCB3F, new String[] { "SRL_r8(A)", "fetch" });
//
//		opcodeToInstruction.put(0xCB40, new String[] { "BIT_b_r8(0,B)", "fetch" });
//		opcodeToInstruction.put(0xCB41, new String[] { "BIT_b_r8(0,C)", "fetch" });
//		opcodeToInstruction.put(0xCB42, new String[] { "BIT_b_r8(0,D)", "fetch" });
//		opcodeToInstruction.put(0xCB43, new String[] { "BIT_b_r8(0,E)", "fetch" });
//		opcodeToInstruction.put(0xCB44, new String[] { "BIT_b_r8(0,H)", "fetch" });
//		opcodeToInstruction.put(0xCB45, new String[] { "BIT_b_r8(0,L)", "fetch" });
//		opcodeToInstruction.put(0xCB46, new String[] { "no_op", "BIT_b_r8(0,(HL))", "fetch" });
//		opcodeToInstruction.put(0xCB47, new String[] { "BIT_b_r8(0,A)", "fetch" });
//
//		opcodeToInstruction.put(0xCB48, new String[] { "BIT_b_r8(1,B)", "fetch" });
//		opcodeToInstruction.put(0xCB49, new String[] { "BIT_b_r8(1,C)", "fetch" });
//		opcodeToInstruction.put(0xCB4A, new String[] { "BIT_b_r8(1,D)", "fetch" });
//		opcodeToInstruction.put(0xCB4B, new String[] { "BIT_b_r8(1,E)", "fetch" });
//		opcodeToInstruction.put(0xCB4C, new String[] { "BIT_b_r8(1,H)", "fetch" });
//		opcodeToInstruction.put(0xCB4D, new String[] { "BIT_b_r8(1,L)", "fetch" });
//		opcodeToInstruction.put(0xCB4E, new String[] { "no_op", "BIT_b_r8(1,(HL))", "fetch" });
//		opcodeToInstruction.put(0xCB4F, new String[] { "BIT_b_r8(1,A)", "fetch" });
//
//		opcodeToInstruction.put(0xCB50, new String[] { "BIT_b_r8(2,B)", "fetch" });
//		opcodeToInstruction.put(0xCB51, new String[] { "BIT_b_r8(2,C)", "fetch" });
//		opcodeToInstruction.put(0xCB52, new String[] { "BIT_b_r8(2,D)", "fetch" });
//		opcodeToInstruction.put(0xCB53, new String[] { "BIT_b_r8(2,E)", "fetch" });
//		opcodeToInstruction.put(0xCB54, new String[] { "BIT_b_r8(2,H)", "fetch" });
//		opcodeToInstruction.put(0xCB55, new String[] { "BIT_b_r8(2,L)", "fetch" });
//		opcodeToInstruction.put(0xCB56, new String[] { "no_op", "BIT_b_r8(2,(HL))", "fetch" });
//		opcodeToInstruction.put(0xCB57, new String[] { "BIT_b_r8(2,A)", "fetch" });
//
//		opcodeToInstruction.put(0xCB58, new String[] { "BIT_b_r8(3,B)", "fetch" });
//		opcodeToInstruction.put(0xCB59, new String[] { "BIT_b_r8(3,C)", "fetch" });
//		opcodeToInstruction.put(0xCB5A, new String[] { "BIT_b_r8(3,D)", "fetch" });
//		opcodeToInstruction.put(0xCB5B, new String[] { "BIT_b_r8(3,E)", "fetch" });
//		opcodeToInstruction.put(0xCB5C, new String[] { "BIT_b_r8(3,H)", "fetch" });
//		opcodeToInstruction.put(0xCB5D, new String[] { "BIT_b_r8(3,L)", "fetch" });
//		opcodeToInstruction.put(0xCB5E, new String[] { "no_op", "BIT_b_r8(3,(HL))", "fetch" });
//		opcodeToInstruction.put(0xCB5F, new String[] { "BIT_b_r8(3,A)", "fetch" });
//
//		opcodeToInstruction.put(0xCB60, new String[] { "BIT_b_r8(4,B)", "fetch" });
//		opcodeToInstruction.put(0xCB61, new String[] { "BIT_b_r8(4,C)", "fetch" });
//		opcodeToInstruction.put(0xCB62, new String[] { "BIT_b_r8(4,D)", "fetch" });
//		opcodeToInstruction.put(0xCB63, new String[] { "BIT_b_r8(4,E)", "fetch" });
//		opcodeToInstruction.put(0xCB64, new String[] { "BIT_b_r8(4,H)", "fetch" });
//		opcodeToInstruction.put(0xCB65, new String[] { "BIT_b_r8(4,L)", "fetch" });
//		opcodeToInstruction.put(0xCB66, new String[] { "no_op", "BIT_b_r8(4,(HL))", "fetch" });
//		opcodeToInstruction.put(0xCB67, new String[] { "BIT_b_r8(4,A)", "fetch" });
//
//		opcodeToInstruction.put(0xCB68, new String[] { "BIT_b_r8(5,B)", "fetch" });
//		opcodeToInstruction.put(0xCB69, new String[] { "BIT_b_r8(5,C)", "fetch" });
//		opcodeToInstruction.put(0xCB6A, new String[] { "BIT_b_r8(5,D)", "fetch" });
//		opcodeToInstruction.put(0xCB6B, new String[] { "BIT_b_r8(5,E)", "fetch" });
//		opcodeToInstruction.put(0xCB6C, new String[] { "BIT_b_r8(5,H)", "fetch" });
//		opcodeToInstruction.put(0xCB6D, new String[] { "BIT_b_r8(5,L)", "fetch" });
//		opcodeToInstruction.put(0xCB6E, new String[] { "no_op", "BIT_b_r8(5,(HL))", "fetch" });
//		opcodeToInstruction.put(0xCB6F, new String[] { "BIT_b_r8(5,A)", "fetch" });
//
//		opcodeToInstruction.put(0xCB70, new String[] { "BIT_b_r8(6,B)", "fetch" });
//		opcodeToInstruction.put(0xCB71, new String[] { "BIT_b_r8(6,C)", "fetch" });
//		opcodeToInstruction.put(0xCB72, new String[] { "BIT_b_r8(6,D)", "fetch" });
//		opcodeToInstruction.put(0xCB73, new String[] { "BIT_b_r8(6,E)", "fetch" });
//		opcodeToInstruction.put(0xCB74, new String[] { "BIT_b_r8(6,H)", "fetch" });
//		opcodeToInstruction.put(0xCB75, new String[] { "BIT_b_r8(6,L)", "fetch" });
//		opcodeToInstruction.put(0xCB76, new String[] { "no_op", "BIT_b_r8(6,(HL))", "fetch" });
//		opcodeToInstruction.put(0xCB77, new String[] { "BIT_b_r8(6,A)", "fetch" });
//
//		opcodeToInstruction.put(0xCB78, new String[] { "BIT_b_r8(7,B)", "fetch" });
//		opcodeToInstruction.put(0xCB79, new String[] { "BIT_b_r8(7,C)", "fetch" });
//		opcodeToInstruction.put(0xCB7A, new String[] { "BIT_b_r8(7,D)", "fetch" });
//		opcodeToInstruction.put(0xCB7B, new String[] { "BIT_b_r8(7,E)", "fetch" });
//		opcodeToInstruction.put(0xCB7C, new String[] { "BIT_b_r8(7,H)", "fetch" });
//		opcodeToInstruction.put(0xCB7D, new String[] { "BIT_b_r8(7,L)", "fetch" });
//		opcodeToInstruction.put(0xCB7E, new String[] { "no_op", "BIT_b_r8(7,(HL))", "fetch" });
//		opcodeToInstruction.put(0xCB7F, new String[] { "BIT_b_r8(7,A)", "fetch" });
//
//		opcodeToInstruction.put(0xCB80, new String[] { "RES_b_r8(0,B)", "fetch" });
//		opcodeToInstruction.put(0xCB81, new String[] { "RES_b_r8(0,C)", "fetch" });
//		opcodeToInstruction.put(0xCB82, new String[] { "RES_b_r8(0,D)", "fetch" });
//		opcodeToInstruction.put(0xCB83, new String[] { "RES_b_r8(0,E)", "fetch" });
//		opcodeToInstruction.put(0xCB84, new String[] { "RES_b_r8(0,H)", "fetch" });
//		opcodeToInstruction.put(0xCB85, new String[] { "RES_b_r8(0,L)", "fetch" });
//		opcodeToInstruction.put(0xCB86, new String[] { "no_op", "save_r8((HL))", "RES_b_r8(0,saved)", "fetch" });
//		opcodeToInstruction.put(0xCB87, new String[] { "RES_b_r8(0,A)", "fetch" });
//
//		opcodeToInstruction.put(0xCB88, new String[] { "RES_b_r8(1,B)", "fetch" });
//		opcodeToInstruction.put(0xCB89, new String[] { "RES_b_r8(1,C)", "fetch" });
//		opcodeToInstruction.put(0xCB8A, new String[] { "RES_b_r8(1,D)", "fetch" });
//		opcodeToInstruction.put(0xCB8B, new String[] { "RES_b_r8(1,E)", "fetch" });
//		opcodeToInstruction.put(0xCB8C, new String[] { "RES_b_r8(1,H)", "fetch" });
//		opcodeToInstruction.put(0xCB8D, new String[] { "RES_b_r8(1,L)", "fetch" });
//		opcodeToInstruction.put(0xCB8E, new String[] { "no_op", "save_r8((HL))", "RES_b_r8(1,saved)", "fetch" });
//		opcodeToInstruction.put(0xCB8F, new String[] { "RES_b_r8(1,A)", "fetch" });
//
//		opcodeToInstruction.put(0xCB90, new String[] { "RES_b_r8(2,B)", "fetch" });
//		opcodeToInstruction.put(0xCB91, new String[] { "RES_b_r8(2,C)", "fetch" });
//		opcodeToInstruction.put(0xCB92, new String[] { "RES_b_r8(2,D)", "fetch" });
//		opcodeToInstruction.put(0xCB93, new String[] { "RES_b_r8(2,E)", "fetch" });
//		opcodeToInstruction.put(0xCB94, new String[] { "RES_b_r8(2,H)", "fetch" });
//		opcodeToInstruction.put(0xCB95, new String[] { "RES_b_r8(2,L)", "fetch" });
//		opcodeToInstruction.put(0xCB96, new String[] { "no_op", "save_r8((HL))", "RES_b_r8(2,saved)", "fetch" });
//		opcodeToInstruction.put(0xCB97, new String[] { "RES_b_r8(2,A)", "fetch" });
//
//		opcodeToInstruction.put(0xCB98, new String[] { "RES_b_r8(3,B)", "fetch" });
//		opcodeToInstruction.put(0xCB99, new String[] { "RES_b_r8(3,C)", "fetch" });
//		opcodeToInstruction.put(0xCB9A, new String[] { "RES_b_r8(3,D)", "fetch" });
//		opcodeToInstruction.put(0xCB9B, new String[] { "RES_b_r8(3,E)", "fetch" });
//		opcodeToInstruction.put(0xCB9C, new String[] { "RES_b_r8(3,H)", "fetch" });
//		opcodeToInstruction.put(0xCB9D, new String[] { "RES_b_r8(3,L)", "fetch" });
//		opcodeToInstruction.put(0xCB9E, new String[] { "no_op", "save_r8((HL))", "RES_b_r8(3,saved)", "fetch" });
//		opcodeToInstruction.put(0xCB9F, new String[] { "RES_b_r8(3,A)", "fetch" });
//
//		opcodeToInstruction.put(0xCBA0, new String[] { "RES_b_r8(4,B)", "fetch" });
//		opcodeToInstruction.put(0xCBA1, new String[] { "RES_b_r8(4,C)", "fetch" });
//		opcodeToInstruction.put(0xCBA2, new String[] { "RES_b_r8(4,D)", "fetch" });
//		opcodeToInstruction.put(0xCBA3, new String[] { "RES_b_r8(4,E)", "fetch" });
//		opcodeToInstruction.put(0xCBA4, new String[] { "RES_b_r8(4,H)", "fetch" });
//		opcodeToInstruction.put(0xCBA5, new String[] { "RES_b_r8(4,L)", "fetch" });
//		opcodeToInstruction.put(0xCBA6, new String[] { "no_op", "save_r8((HL))", "RES_b_r8(4,saved)", "fetch" });
//		opcodeToInstruction.put(0xCBA7, new String[] { "RES_b_r8(4,A)", "fetch" });
//
//		opcodeToInstruction.put(0xCBA8, new String[] { "RES_b_r8(5,B)", "fetch" });
//		opcodeToInstruction.put(0xCBA9, new String[] { "RES_b_r8(5,C)", "fetch" });
//		opcodeToInstruction.put(0xCBAA, new String[] { "RES_b_r8(5,D)", "fetch" });
//		opcodeToInstruction.put(0xCBAB, new String[] { "RES_b_r8(5,E)", "fetch" });
//		opcodeToInstruction.put(0xCBAC, new String[] { "RES_b_r8(5,H)", "fetch" });
//		opcodeToInstruction.put(0xCBAD, new String[] { "RES_b_r8(5,L)", "fetch" });
//		opcodeToInstruction.put(0xCBAE, new String[] { "no_op", "save_r8((HL))", "RES_b_r8(5,saved)", "fetch" });
//		opcodeToInstruction.put(0xCBAF, new String[] { "RES_b_r8(5,A)", "fetch" });
//
//		opcodeToInstruction.put(0xCBB0, new String[] { "RES_b_r8(6,B)", "fetch" });
//		opcodeToInstruction.put(0xCBB1, new String[] { "RES_b_r8(6,C)", "fetch" });
//		opcodeToInstruction.put(0xCBB2, new String[] { "RES_b_r8(6,D)", "fetch" });
//		opcodeToInstruction.put(0xCBB3, new String[] { "RES_b_r8(6,E)", "fetch" });
//		opcodeToInstruction.put(0xCBB4, new String[] { "RES_b_r8(6,H)", "fetch" });
//		opcodeToInstruction.put(0xCBB5, new String[] { "RES_b_r8(6,L)", "fetch" });
//		opcodeToInstruction.put(0xCBB6, new String[] { "no_op", "save_r8((HL))", "RES_b_r8(6,saved)", "fetch" });
//		opcodeToInstruction.put(0xCBB7, new String[] { "RES_b_r8(6,A)", "fetch" });
//
//		opcodeToInstruction.put(0xCBB8, new String[] { "RES_b_r8(7,B)", "fetch" });
//		opcodeToInstruction.put(0xCBB9, new String[] { "RES_b_r8(7,C)", "fetch" });
//		opcodeToInstruction.put(0xCBBA, new String[] { "RES_b_r8(7,D)", "fetch" });
//		opcodeToInstruction.put(0xCBBB, new String[] { "RES_b_r8(7,E)", "fetch" });
//		opcodeToInstruction.put(0xCBBC, new String[] { "RES_b_r8(7,H)", "fetch" });
//		opcodeToInstruction.put(0xCBBD, new String[] { "RES_b_r8(7,L)", "fetch" });
//		opcodeToInstruction.put(0xCBBE, new String[] { "no_op", "save_r8((HL))", "RES_b_r8(7,saved)", "fetch" });
//		opcodeToInstruction.put(0xCBBF, new String[] { "RES_b_r8(7,A)", "fetch" });
//
//		opcodeToInstruction.put(0xCBC0, new String[] { "SET_b_r8(0,B)", "fetch" });
//		opcodeToInstruction.put(0xCBC1, new String[] { "SET_b_r8(0,C)", "fetch" });
//		opcodeToInstruction.put(0xCBC2, new String[] { "SET_b_r8(0,D)", "fetch" });
//		opcodeToInstruction.put(0xCBC3, new String[] { "SET_b_r8(0,E)", "fetch" });
//		opcodeToInstruction.put(0xCBC4, new String[] { "SET_b_r8(0,H)", "fetch" });
//		opcodeToInstruction.put(0xCBC5, new String[] { "SET_b_r8(0,L)", "fetch" });
//		opcodeToInstruction.put(0xCBC6, new String[] { "no_op", "save_r8((HL))", "SET_b_r8(0,saved)", "fetch" });
//		opcodeToInstruction.put(0xCBC7, new String[] { "SET_b_r8(0,A)", "fetch" });
//
//		opcodeToInstruction.put(0xCBC8, new String[] { "SET_b_r8(1,B)", "fetch" });
//		opcodeToInstruction.put(0xCBC9, new String[] { "SET_b_r8(1,C)", "fetch" });
//		opcodeToInstruction.put(0xCBCA, new String[] { "SET_b_r8(1,D)", "fetch" });
//		opcodeToInstruction.put(0xCBCB, new String[] { "SET_b_r8(1,E)", "fetch" });
//		opcodeToInstruction.put(0xCBCC, new String[] { "SET_b_r8(1,H)", "fetch" });
//		opcodeToInstruction.put(0xCBCD, new String[] { "SET_b_r8(1,L)", "fetch" });
//		opcodeToInstruction.put(0xCBCE, new String[] { "no_op", "save_r8((HL))", "SET_b_r8(1,saved)", "fetch" });
//		opcodeToInstruction.put(0xCBCF, new String[] { "SET_b_r8(1,A)", "fetch" });
//
//		opcodeToInstruction.put(0xCBD0, new String[] { "SET_b_r8(2,B)", "fetch" });
//		opcodeToInstruction.put(0xCBD1, new String[] { "SET_b_r8(2,C)", "fetch" });
//		opcodeToInstruction.put(0xCBD2, new String[] { "SET_b_r8(2,D)", "fetch" });
//		opcodeToInstruction.put(0xCBD3, new String[] { "SET_b_r8(2,E)", "fetch" });
//		opcodeToInstruction.put(0xCBD4, new String[] { "SET_b_r8(2,H)", "fetch" });
//		opcodeToInstruction.put(0xCBD5, new String[] { "SET_b_r8(2,L)", "fetch" });
//		opcodeToInstruction.put(0xCBD6, new String[] { "no_op", "save_r8((HL))", "SET_b_r8(2,saved)", "fetch" });
//		opcodeToInstruction.put(0xCBD7, new String[] { "SET_b_r8(2,A)", "fetch" });
//
//		opcodeToInstruction.put(0xCBD8, new String[] { "SET_b_r8(3,B)", "fetch" });
//		opcodeToInstruction.put(0xCBD9, new String[] { "SET_b_r8(3,C)", "fetch" });
//		opcodeToInstruction.put(0xCBDA, new String[] { "SET_b_r8(3,D)", "fetch" });
//		opcodeToInstruction.put(0xCBDB, new String[] { "SET_b_r8(3,E)", "fetch" });
//		opcodeToInstruction.put(0xCBDC, new String[] { "SET_b_r8(3,H)", "fetch" });
//		opcodeToInstruction.put(0xCBDD, new String[] { "SET_b_r8(3,L)", "fetch" });
//		opcodeToInstruction.put(0xCBDE, new String[] { "no_op", "save_r8((HL))", "SET_b_r8(3,saved)", "fetch" });
//		opcodeToInstruction.put(0xCBDF, new String[] { "SET_b_r8(3,A)", "fetch" });
//
//		opcodeToInstruction.put(0xCBE0, new String[] { "SET_b_r8(4,B)", "fetch" });
//		opcodeToInstruction.put(0xCBE1, new String[] { "SET_b_r8(4,C)", "fetch" });
//		opcodeToInstruction.put(0xCBE2, new String[] { "SET_b_r8(4,D)", "fetch" });
//		opcodeToInstruction.put(0xCBE3, new String[] { "SET_b_r8(4,E)", "fetch" });
//		opcodeToInstruction.put(0xCBE4, new String[] { "SET_b_r8(4,H)", "fetch" });
//		opcodeToInstruction.put(0xCBE5, new String[] { "SET_b_r8(4,L)", "fetch" });
//		opcodeToInstruction.put(0xCBE6, new String[] { "no_op", "save_r8((HL))", "SET_b_r8(4,saved)", "fetch" });
//		opcodeToInstruction.put(0xCBE7, new String[] { "SET_b_r8(4,A)", "fetch" });
//
//		opcodeToInstruction.put(0xCBE8, new String[] { "SET_b_r8(5,B)", "fetch" });
//		opcodeToInstruction.put(0xCBE9, new String[] { "SET_b_r8(5,C)", "fetch" });
//		opcodeToInstruction.put(0xCBEA, new String[] { "SET_b_r8(5,D)", "fetch" });
//		opcodeToInstruction.put(0xCBEB, new String[] { "SET_b_r8(5,E)", "fetch" });
//		opcodeToInstruction.put(0xCBEC, new String[] { "SET_b_r8(5,H)", "fetch" });
//		opcodeToInstruction.put(0xCBED, new String[] { "SET_b_r8(5,L)", "fetch" });
//		opcodeToInstruction.put(0xCBEE, new String[] { "no_op", "save_r8((HL))", "SET_b_r8(5,saved)", "fetch" });
//		opcodeToInstruction.put(0xCBEF, new String[] { "SET_b_r8(5,A)", "fetch" });
//
//		opcodeToInstruction.put(0xCBF0, new String[] { "SET_b_r8(6,B)", "fetch" });
//		opcodeToInstruction.put(0xCBF1, new String[] { "SET_b_r8(6,C)", "fetch" });
//		opcodeToInstruction.put(0xCBF2, new String[] { "SET_b_r8(6,D)", "fetch" });
//		opcodeToInstruction.put(0xCBF3, new String[] { "SET_b_r8(6,E)", "fetch" });
//		opcodeToInstruction.put(0xCBF4, new String[] { "SET_b_r8(6,H)", "fetch" });
//		opcodeToInstruction.put(0xCBF5, new String[] { "SET_b_r8(6,L)", "fetch" });
//		opcodeToInstruction.put(0xCBF6, new String[] { "no_op", "save_r8((HL))", "SET_b_r8(6,saved)", "fetch" });
//		opcodeToInstruction.put(0xCBF7, new String[] { "SET_b_r8(6,A)", "fetch" });
//
//		opcodeToInstruction.put(0xCBF8, new String[] { "SET_b_r8(7,B)", "fetch" });
//		opcodeToInstruction.put(0xCBF9, new String[] { "SET_b_r8(7,C)", "fetch" });
//		opcodeToInstruction.put(0xCBFA, new String[] { "SET_b_r8(7,D)", "fetch" });
//		opcodeToInstruction.put(0xCBFB, new String[] { "SET_b_r8(7,E)", "fetch" });
//		opcodeToInstruction.put(0xCBFC, new String[] { "SET_b_r8(7,H)", "fetch" });
//		opcodeToInstruction.put(0xCBFD, new String[] { "SET_b_r8(7,L)", "fetch" });
//		opcodeToInstruction.put(0xCBFE, new String[] { "no_op", "save_r8((HL))", "SET_b_r8(7,saved)", "fetch" });
//		opcodeToInstruction.put(0xCBFF, new String[] { "SET_b_r8(7,A)", "fetch" });
//
//		// CUSTOM Interrupt Sub Routine OP-CODE INSTRUCTIONS
//		opcodeToInstruction.put(0xDE00, new String[] { "decrement_r16(SP)", "write_Stack_r16_MSB(PC)&decrement_r16(SP)",
//				"write_Stack_r16_LSB(PC)", "write_PC(40)", "fetch" });
//		opcodeToInstruction.put(0xDE01, new String[] { "decrement_r16(SP)", "write_Stack_r16_MSB(PC)&decrement_r16(SP)",
//				"write_Stack_r16_LSB(PC)", "write_PC(48)", "fetch" });
//		opcodeToInstruction.put(0xDE02, new String[] { "decrement_r16(SP)", "write_Stack_r16_MSB(PC)&decrement_r16(SP)",
//				"write_Stack_r16_LSB(PC)", "write_PC(50)", "fetch" });
//		opcodeToInstruction.put(0xDE03, new String[] { "decrement_r16(SP)", "write_Stack_r16_MSB(PC)&decrement_r16(SP)",
//				"write_Stack_r16_LSB(PC)", "write_PC(58)", "fetch" });
//		opcodeToInstruction.put(0xDE04, new String[] { "decrement_r16(SP)", "write_Stack_r16_MSB(PC)&decrement_r16(SP)",
//				"write_Stack_r16_LSB(PC)", "write_PC(60)", "fetch" });
//
//		System.out.println(opcodeToInstruction.size() + " OPCODES IMPLEMENTED");
//	}
//
//}

package cartridge;

public class MBC1 extends MBC {

	public MBC1(Cartridge cartridge, boolean canSave) {
		super(cartridge, canSave);
	}

	public void selectBank(int address, int val) {
		if (address >= 0x0000 && address <= 0x1FFF) {
			if ((val & 0xF) == 0x0A && extRam.length > 0) {
				enabledExternalRam = true;
			} else {
				enabledExternalRam = false;
			}
			return;
		} else if (address >= 0x2000 && address <= 0x3FFF) {
			int n = val == 0 ? 1 : val;
			romBankNumber = (romBankNumber & 0b0110_0000) | (n & 0b0001_1111);
			if(romBankNumber == 0) {
				romBankNumber = 1;
			}
		} else if (address >= 0x4000 && address <= 0x5FFF) {
			if (bankingMode.equals("RAM")) {
				ramBankNumber = val & 0b11;
			} else {
				romBankNumber = (romBankNumber & 0b0001_1111) | ((val & 3) << 5);
			}
		} else if (address >= 0x6000 && address <= 0x7FFF) {
			if ((val & 01) == 1) {
				bankingMode = "RAM";
			} else {
				bankingMode = "ROM";
			}
			return;
		}
		if(romBankNumber == 0 || romBankNumber == 0x40 || romBankNumber == 0x60) {
			romBankNumber = 1;
		}
//		System.out.println(romBankNumber);
	}

}

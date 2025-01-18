package cartridge;

public class MBC3 extends MBC {

	public MBC3(Cartridge cartridge, boolean canSave) {
		super(cartridge, canSave);
	}

	@Override
	public void selectBank(int address, int val) {
		///TIMER NOT IMPLEMENTED!!!!
		if (address >= 0x0000 && address <= 0x1FFF) {
			if ((val & 0xF) == 0x0A) {
				enabledExternalRam = true;
			} else {
				enabledExternalRam = false;
			}
			return;
		} else if (address >= 0x2000 && address <= 0x3FFF) {
			if (val == 0) {
				val = 1;
			}
			romBankNumber = val;
		} else if (address >= 0x4000 && address <= 0x5FFF) {
			ramBankNumber = val & 0x11;
		} else {
//			throw new RuntimeException("RAM BANKING NOT IMPLEMNTED YET");

		}
	}

}

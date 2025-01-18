package cartridge;

public class MBC5 extends MBC {

	public MBC5(Cartridge cartridge) {
		super(cartridge);
	}

	@Override
	public void selectBank(int address, int val) {
		if (address >= 0x0000 && address <= 0x1FFF) {
			if ((val & 0xF) == 0x0A) {
				enabledExternalRam = true;
			} else {
				enabledExternalRam = false;
			}
			return;
		} else if (address >= 0x2000 && address <= 0x2FFF) {
			romBankNumber = val;
		} else if (address >= 0x3000 && address <= 0x3FFF) {
			romBankNumber = (((val & 0x1) << 8) | (romBankNumber & 0xFF)) & 0xFFFF;
		} else {
//			throw new RuntimeException("RAM BANKING NOT IMPLEMNTED YET");

		}
	}

}

package cartridge;

public class MBC0 extends MBC {

	public MBC0(Cartridge cartridge) {
		super(cartridge);
	}

	@Override
	public void selectBank(int address, int val) {
		romBankNumber = 1;
	}

}

package cartridge;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import utils.Utils;

public abstract class MBC {
	protected boolean enabledExternalRam = false;
	protected int romBankNumber = 1;
	protected int ramBankNumber = 0;
	protected String bankingMode = "ROM";
	protected Cartridge cartridge;

	protected int[] extRam;

	private boolean canSave = false;

	public MBC(Cartridge cartridge, boolean canSave) {
		this.cartridge = cartridge;
		extRam = new int[cartridge.getRamSize()];
		this.canSave = canSave;
		if (canSave) {
			loadExtRam();
		}
	}

	public MBC(Cartridge cartridge) {
		this(cartridge, false);
	}

	public int readSwitchableRomBank(int address) {
		int romBankStartAddress = (romBankNumber * 0x4000) % cartridge.rom.length;
		return cartridge.rom[(romBankStartAddress + (address - 0x4000))];
	}

	public abstract void selectBank(int address, int val);

	public void writeExtRam(int address, int val) {
		if (!enabledExternalRam) {
			return;
		}
		ramBankNumber %= (extRam.length/8192);
		int ramBankStartAddress = (ramBankNumber * 8192)%extRam.length;
		extRam[(ramBankStartAddress + (address - 0xA000))] = val;
		if (canSave) {
			saveExtRam();
		}
	}

	private void saveExtRam() {
		byte[] bytes = new byte[extRam.length];

		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) extRam[i];
		}
		try (FileOutputStream stream = new FileOutputStream(cartridge.saveFilePath)) {
			stream.write(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadExtRam() {
		File file = new File(cartridge.saveFilePath);
		if (file.exists() == false) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			saveExtRam();
		}
		try (FileInputStream stream = new FileInputStream(file)) {
			byte[] bytes = Utils.readAllBytes(stream);
			for (int i = 0; i < bytes.length; i++) {
				extRam[i] = (Byte.toUnsignedInt(bytes[i]) & 0xFF);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int readExtRam(int address) {
		if (!enabledExternalRam) {
			return 0xFF;
		}
		ramBankNumber %= (extRam.length/8192);
		int ramBankStartAddress = (ramBankNumber * 8192)%extRam.length;
		return extRam[ramBankStartAddress + (address - 0xA000)];
	}

	public boolean isExternalRamEnabled() {
		return enabledExternalRam;
	}
}

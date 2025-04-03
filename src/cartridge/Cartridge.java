package cartridge;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import cpu.CPU;
import utils.Utils;

public class Cartridge {

	public int[] rom;
	private String title;
	public CPU cpu;
	private String cartridgeType;
	private int romSize;
	private int bankCount;
	private int ramSize;
	File file;
//	private String path;

	private MBC mbc;
	public String saveFilePath = null;

	public Cartridge(File file, CPU cpu) {
		init(file, cpu);
	}

	public void init(File file, CPU cpu) {
		this.cpu = cpu;
		this.file = file;
		try {
			loadROM(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		title = getTitle();
		System.out.println("Title: " + title);
//		cpu.CGB_MODE = getCgbFlag();
		cartridgeType = getCartridgeType();
		System.out.println("Cartridge Type: " + cartridgeType);
		romSize = getRomSizeAndNumberOfBanks()[0];
		System.out.println("Rom Size: " + romSize + " Bytes");
		romSize = getRomSizeAndNumberOfBanks()[0];
		bankCount = getRomSizeAndNumberOfBanks()[1];
		System.out.println("Number of ROM Banks: " + bankCount);
		ramSize = getRamSize();
		System.out.println("Ram Size: " + ramSize + " Bytes");
		System.out.println("Number of RAM Banks: " + (ramSize / 8192));
		System.out.println("--------------------------------\n");
	}

	public MBC getMbc() {
		return mbc;
	}

	public void selectBank(int address, int val) {
		mbc.selectBank(address, val);
	}

//	public void switchRom() {
//		int bankDataStartAddress = 0x4000 * this.romBankNumber;
//		for (int i = 0; i <= 0x3FFF; i++) {
//			cpu.bus.ram[(i + 0x4000) & 0xFFFF] = rom[(bankDataStartAddress + i)] & 0xFF;
//		}
//	}

	public int getRamSize() {
		int val = rom[0x149];
		int ramSize = 0;
		switch (val) {
		case 0x00: {
			ramSize = 0;
			break;
		}
		case 0x01: {

			break;
		}
		case 0x02: {
			ramSize = 8 * 1024;
			break;
		}
		case 0x03: {
			ramSize = 32 * 1024;
			break;
		}
		case 0x04: {
			ramSize = 128 * 1024;
			break;
		}
		case 0x05: {
			ramSize = 64 * 1024;
			break;
		}
		default: {
			System.out.println("WRONG RAM SIZE");
			System.exit(0);
			break;
		}
		}
		return ramSize;
	}

	public int[] getRomSizeAndNumberOfBanks() {
		int[] res = new int[2];
		int romSize = 0;
		int romBanks = 0;
		int value = rom[0x148];
		switch (value) {
		case 0x00:
			romSize = 32 * 1024;
			romBanks = 2;
			break;
		case 0x01:
			romSize = 64 * 1024;
			romBanks = 4;
			break;
		case 0x02:
			romSize = 128 * 1024;
			romBanks = 8;
			break;
		case 0x03:
			romSize = 256 * 1024;
			romBanks = 16;
			break;
		case 0x04:
			romSize = 512 * 1024;
			romBanks = 32;
			break;
		case 0x05:
			romSize = 1024 * 1024;
			romBanks = 64;
			break;
		case 0x06:
			romSize = 2 * 1024 * 1024;
			romBanks = 128;
			break;
		case 0x07:
			romSize = 4 * 1024 * 1024;
			romBanks = 256;
			break;
		case 0x08:
			romSize = 8 * 1024 * 1024;
			romBanks = 512;
			break;
		case 0x52:
			romSize = (int) (1.1 * 1024 * 1024);
			romBanks = 72;
			break;
		case 0x53:
			romSize = (int) (1.2 * 1024 * 1024);
			romBanks = 80;
			break;
		case 0x54:
			romSize = (int) (1.5 * 1024 * 1024);
			romBanks = 96;
			break;
		default:
			System.out.println("Invalid value");
			break;
		}
		res[0] = romSize;
		res[1] = romBanks;
		return res;
	}

	public String getCartridgeType() {
		String res = "";
		switch (rom[0x147]) {
		case 0x00: {
			res = "ROM_ONLY";
			mbc = new MBC0(this);
			break;
		}
		case 0x01: {
			res = "MBC1";
			mbc = new MBC1(this, false);
			break;
		}
		case 0x02: {
			res = "MBC1+RAM";
			mbc = new MBC1(this, false);
			break;
		}
		case 0x03: {
			res = "MBC1+RAM+BATTERY";
			mbc = new MBC1(this, true);
			break;
		}
		case 0x13: {
			res = "MBC3+RAM+BATTERY";
			mbc = new MBC3(this, true);
			break;
		}
		case 0x19: {
			res = "MBC5";
			mbc = new MBC5(this);
			break;
		}
		default: {
			System.out.println("Unknown Cartridge Type: " + Integer.toHexString(rom[0x147]));
			System.exit(0);
			break;
		}
		}
		if (mbc == null) {
			throw new RuntimeException("MBC " + res + " not implemented!");
		}
		return res;
	}

	public int readRomBank0(int address) {
		return rom[(address & 0xFFFF)];
	}

	public String getTitle() {
		String res = "";
		for (int i = 0x134; i <= 0x143; i++) {
			char c = (char) rom[i];
			if (c == 0) {
				break;
			}
			res += c;
		}
		return res;
	}

	public boolean getCgbFlag() {
		System.out.println(String.format("CGB Flag: 0x%02X", rom[0x0143]));
		return true;
	}

	public void loadROM(File file) throws IOException {
//		URL resource = getClass().getResource(path);
		saveFilePath = file.getAbsolutePath().replaceFirst(".gb", ".sav");
		InputStream inputStream = new FileInputStream(file);
		byte[] rom = Utils.readAllBytes(inputStream);
		this.rom = new int[rom.length];

		for (int i = 0; i < rom.length; i++) {
			this.rom[i] = (Byte.toUnsignedInt(rom[i]) & 0xFF);
		}

		inputStream.close();
	}

//	public void loadBootRom() {
//		InputStream inputStream = getClass().getResourceAsStream("/dmg_boot.bin");
//		byte[] rom;
//		try {
//			rom = inputStream.readAllBytes();
//			for (int i = 0x0000; i < rom.length; i++) {
//				cpu.bus.ram[i] = (Byte.toUnsignedInt(rom[i]) & 0xFF);
//			}
//			inputStream.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		cpu.PC = 0;
//	}

	public String getFilePath() {
		return file.getAbsolutePath();
	}

}

package utils;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Utils {

	public static String byteToHexString(int b) {
		String res = String.format("%02X", b);
		return res;
	}

	public static <T> void prints(T... data) {
		for (int i = 0; i < data.length - 1; i++) {
			System.out.print(data[i] + " ");
		}
		System.out.println(data[data.length - 1]);
	}

	public static <T> void print(T[] data) {
		for (int i = 0; i < data.length - 1; i++) {
			System.out.print(data[i] + " ");
		}
		System.out.println(data[data.length - 1]);
	}

	public static void print(int[] data) {
		for (int i = 0; i < data.length - 1; i++) {
			System.out.print(data[i] + " ");
		}
		System.out.println(data[data.length - 1]);
	}

	public static byte[] readAllBytes(InputStream inputStream) throws IOException {
		byte[] bytes = new byte[inputStream.available()];
		DataInputStream dataInputStream = new DataInputStream(inputStream);
		dataInputStream.readFully(bytes);
		return bytes;
	}

}

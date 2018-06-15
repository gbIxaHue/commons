package ru.dev.gbixahue.library.utils;

import android.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
public class StringCipher {

	private static final String ALGORITHM = "AES";
	private static final byte[] keyValue = new byte[] {'A', 'l', 'A', 'n', 'Z', 'h', '0', '1', '0', 'q', 'T', 'b', '_', 'K', 'y', 'z'};

	public static String encrypt(String valueToEnc) {
		try {
			Key key = generateKey();
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encValue = cipher.doFinal(valueToEnc.getBytes());
			return Base64.encodeToString(encValue, Base64.NO_WRAP);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valueToEnc;
	}

	public static String decrypt(String encryptedValue) {
		try {
			Key key = generateKey();
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] decValue = cipher.doFinal(Base64.decode(encryptedValue, Base64.NO_WRAP));
			return new String(decValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedValue;
	}

	private static Key generateKey() throws Exception {
		return new SecretKeySpec(keyValue, ALGORITHM);
	}
}

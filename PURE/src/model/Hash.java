package model;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hash {

    /** アルゴリズム */
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    /** ストレッチング回数 */
    private static final int ITERATION_COUNT = 10000;
    /** 生成される鍵の長さ */
    private static final int KEY_LENGTH = 256;

    public String getHash(String password, String salt){
    	byte[] bytes = toByteArray(salt);
    	if(bytes == null){
    		return null;
    	}
	    return getHash(password, bytes);
    }

    /**
     * 平文のパスワードからハッシュ値を生成し、返却する
     *
     * @param password 平文のパスワード
     * @param salt ソルト
     * @return 安全なパスワード
     */
    public String getHash(String password, byte[] salt) {
        char[] passCharAry = password.toCharArray();
        if(salt == null){
        	salt = createSalt();
        }
        if(salt == null){
        	return null;
        }

        PBEKeySpec keySpec = new PBEKeySpec(passCharAry, salt, ITERATION_COUNT, KEY_LENGTH);

        SecretKeyFactory skf;
        try {
            skf = SecretKeyFactory.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        SecretKey secretKey;
        try {
            secretKey = skf.generateSecret(keySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        }
        byte[] passByteAry = secretKey.getEncoded();

        // 生成されたバイト配列を16進数の文字列に変換
        return toHex(passByteAry);
    }

    /**
     * ソルトをハッシュ化して返却します
     * ※ハッシュアルゴリズムはSHA-256を使用
     *
     * @param salt ソルト
     * @return ハッシュ化されたバイト配列のソルト
     * @throws NoSuchAlgorithmException
     */
    public byte[] createSalt() {
    	SecureRandom sha = null;
		try {
			sha = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
    	byte[] salt = new byte[32];
    	sha.nextBytes(salt);
    	return salt;
    }

    public String toHex(byte[] salt){
        StringBuilder sb = new StringBuilder(64);
        for (byte b : salt) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

    public byte[] toByteArray(String hex){
    	if(hex.length() % 2 != 0){
    		return null;
    	}
    	if(!hex.matches("[0-9a-fA-F]*")){
    		return null;
    	}
		byte[] salt = new byte[hex.length() / 2];
	    for (int i = 0; i < salt.length; i++) {
	        salt[i] = (byte) Integer.parseInt(hex.substring(i * 2, (i + 1) * 2), 16);
	    }
	    return salt;
    }

}

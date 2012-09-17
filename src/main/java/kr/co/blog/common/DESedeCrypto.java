package kr.co.blog.common;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

/**
 * 암복호화 처리
 * @author kbtapjm
 *
 */
public class DESedeCrypto {
    
    public static void main(String args[]) {
        DESedeCrypto.encrypt("111111");
    }
    
    /**
     * 암호화시에 사용할 키 추출
     * @return 키 문자열
     */
    public static SecretKey key() {
        SecretKey desKey = null;
        // TripleDES 키 생성, 24바이트 고정 지정(Java에서 지원은 192bit, 24자 지원)
        byte[] keydata = "itwildesedecbddeveloper1".getBytes();
        
        try {
            DESedeKeySpec keySpec = new DESedeKeySpec(keydata);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            desKey = keyFactory.generateSecret(keySpec);
        } catch(Exception e) {
            System.out.println(e.toString());
        }

        return desKey;
        
    }
    
    /**
     * 암호화
     * @param text 암호화할 일반 문자열
     * @return 암호화된 문자열
     */
    public static byte[] encrypt(String text) {
        byte[] ciphertext = null;
        String output = null;
        
        try {
            // 암호화 객체 설정
            Cipher cipher = Cipher.getInstance("TripleDES/ECB/PKCS5Padding");
            
            // key(): 암호화 키, 21바이트 지정 
            cipher.init(Cipher.ENCRYPT_MODE, key());

            // 주어진 문자열을 byte 배열로 추출
            byte[] plaintext = text.getBytes("UTF8"); 

            // 암호화
            ciphertext = cipher.doFinal(plaintext);
            
        } catch(Exception e) {
            System.out.println(e.toString());
        }
        
        return ciphertext;
        
    }
    
    /**
     * 복호화, 원래의 문자열로 변환
     * @param array 암호화 문자열
     * @return 알아볼수 있는 문자열 리턴
     */
    public static String decrypt(byte[] array){
        String output = null;
        
        try {
            // 암호화 객체 설정
            Cipher cipher = Cipher.getInstance("TripleDES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key());
            
            // 복호화 문자 출력
            byte[] decryptedText = cipher.doFinal(array);

            output = new String(decryptedText,"UTF8");
         
        } catch(Exception e) {
            System.out.println(e);
        }
        
        return output; 
    }
}

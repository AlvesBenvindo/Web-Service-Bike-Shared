package ao.uan.fc.cc4.bikeshared.utils;

import java.security.SecureRandom;

public class GenerateKeyToken {

    /*
     * Método para gerar uma chave secreta
     * que pode se usar para gerar o token
     */
    public static String generateSecretKey(int keyLength) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[keyLength];
        random.nextBytes(bytes);

        StringBuilder key = new StringBuilder();
        for (byte b : bytes) {
            key.append(Character.forDigit((b & 0xff) / 16, 16));
            key.append(Character.forDigit(b & 0xff % 16, 16));
        }

        return key.toString();
    }

    // public static void main (String [] args) {
    //     System.out.println(generateSecretKey(256));
    // }

}

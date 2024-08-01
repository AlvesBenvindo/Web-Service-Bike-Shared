package ao.uan.fc.cc4.bikeshared.Station1.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPassword {

    public static String hashing(String password) {

        try {
            // Obter a instância do algoritmo SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Converter a mensagem em bytes
            byte[] bytes = password.getBytes(StandardCharsets.UTF_8);

            // Atualizar o digest com os bytes da mensagem
            digest.update(bytes);

            // Gerar o hash SHA-256
            byte[] hash = digest.digest();

            // A converter o hash em uma string hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02X", b));
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException exc) {
            return null;
        }

        //System.out.println("Hash SHA-256: " + hexString.toString());
        //System.out.println("Comprovando igualdade: " + hexString.toString().equals("3534D89DA896571B03CA0DAE4AEC66D908F03CCA99169FAB06660115ECA74EB2"));
    }
}

package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {

    private static Hasher instance;
//    private static byte[] seed;


    public static Hasher getInstance() {
        if (instance == null) {
            instance = new Hasher();
        }
        return instance;
    }

    public static String generarSHA256(String password) {
        String hash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            //          md.update(seed);
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder constructor = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                constructor.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            hash = constructor.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash;
    }

/*  -- Preparado para tener seed pero cada inicio de server genera nueva seed
    static {
        try {
            seed = getSeed();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static byte[] getSeed() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        //Crea random Seed para el hash
        byte[] seed = new byte[16];
        sr.nextBytes(seed);
        return seed;
    }
*/
}

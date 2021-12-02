package com.brandonjernigan.dynamic_geospatial_rfi.utilities;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Account {

    private byte[] salt;
    private int derivedKeyLength;
    private int iterations;
    private String algorithm;
    private KeySpec keySpec;
    private SecureRandom random;
    private SecretKeyFactory factory;

    public boolean authenticate(String passwordInput, byte[] storedPassword, byte[] salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException{

        byte[] modifiedInput = modifyPassword(passwordInput, salt);
        byte[] comparePassword = new byte[modifiedInput.length + salt.length];

        System.arraycopy(modifiedInput, 0, comparePassword, 0, modifiedInput.length);
        System.arraycopy(salt, 0, comparePassword, modifiedInput.length, salt.length);

        return Arrays.equals(comparePassword, storedPassword);
    }

    public byte[] modifyPassword(String password, byte[] salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException{

        derivedKeyLength = 160;
        iterations = 20000;
        algorithm = "PBKDF2WithHmacSHA1";

        factory = SecretKeyFactory.getInstance(algorithm);
        keySpec = new PBEKeySpec(password.toCharArray(), salt, iterations, derivedKeyLength);

        return factory.generateSecret(keySpec).getEncoded();
    }

    public byte[] generateSalt() throws NoSuchAlgorithmException{

        salt = new byte[8];

        random = SecureRandom.getInstance("SHA1PRNG");
        random.nextBytes(salt);

        return salt;
    }
}

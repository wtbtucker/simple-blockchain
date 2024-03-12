package io.collective.basic;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block {
    private final String previousHash;
    private final long timestamp;
    private final int nonce;
    private String hash;

    public Block(String previousHash, long timestamp, int nonce) throws NoSuchAlgorithmException {
        this.previousHash = previousHash;
        this.timestamp = timestamp;
        this.nonce = nonce;
        this.hash = null;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getNonce() {
        return nonce;
    }

    public String getHash() {
        if (hash == null) {
            try {
                hash = calculatedHash();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return hash;
    }

    public Boolean checkValidHash() {
        try {
            String correctHash = calculatedHash();
            return correctHash.equals(getHash());
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public String calculatedHash() throws NoSuchAlgorithmException {
        try {
            String hashData = previousHash + timestamp + nonce;
            return calculateHash(hashData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /// Supporting functions that you'll need.

    static String calculateHash(String string) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(string.getBytes());
        return String.format("%064x", new BigInteger(1, digest.digest()));
    }
}
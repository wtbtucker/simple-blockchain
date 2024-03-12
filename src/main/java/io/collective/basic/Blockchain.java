package io.collective.basic;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    List<Block> chain = new ArrayList<>();

    public boolean isEmpty() {
        return chain.size() == 0;
    }

    public void add(Block block) {
        chain.add(block);
    }

    public int size() {
        return chain.size();
    }

    public boolean isValid() throws NoSuchAlgorithmException {
        Block lastBlock = (!isEmpty()) ? chain.get(chain.size() - 1) : null;
        Block penultimateBlock = (size() > 1) ? chain.get(chain.size() - 2) : null;

        if (lastBlock != null && !isMined(lastBlock)) {
            return false;
        } else if (penultimateBlock != null && lastBlock.getPreviousHash() != penultimateBlock.getHash()){
            return false;
        } else if (lastBlock != null && !lastBlock.checkValidHash()){
            return false;
        } else {
            return true;
        }

    }

    /// Supporting functions that you'll need.

    public static Block mine(Block block) throws NoSuchAlgorithmException {
        Block mined = new Block(block.getPreviousHash(), block.getTimestamp(), block.getNonce());

        while (!isMined(mined)) {
            mined = new Block(mined.getPreviousHash(), mined.getTimestamp(), mined.getNonce() + 1);
        }
        return mined;
    }

    public static boolean isMined(Block minedBlock) {
        return minedBlock.getHash().startsWith("00");
    }
}
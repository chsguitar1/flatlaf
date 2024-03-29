/*
 * This file is part of WebLookAndFeel library.
 *
 * WebLookAndFeel library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WebLookAndFeel library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WebLookAndFeel library.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.ocsoft.flatlaf.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.ocsoft.flatlaf.utils.encryption.Base64;

/**
 * This class provides a set of utilities to encode and decode data.
 *
 * @author Mikle Garin
 */

public final class EncryptionUtils {
    /**
     * Default text encoding.
     */
    private static final String DEFAULT_ENCODING = "UTF-8";
    
    /**
     * Encode and decode key.
     */
    private static String key = "aZCVKk3mospdfm12pk4fcFD43d435ccCDgHKPQMQ23x7zkq03";
    
    /**
     * Returns text enrypted through xor and encoded using base64.
     *
     * @param text
     *            text to encrypt
     * @return encrypted text
     */
    public static String encrypt(String text) {
        return base64encode(xorText(text));
    }
    
    /**
     * Returns text decoded using base64 and decrypted through xor.
     *
     * @param text
     *            text to decrypt
     * @return decrypted text
     */
    public static String decrypt(String text) {
        return xorText(base64decode(text));
    }
    
    /**
     * Returns text encrypted using xor.
     *
     * @param text
     *            to encrypt
     * @return encrypted text
     */
    public static String xorText(String text) {
        if (text == null) {
            return null;
        }
        
        char[] keys = key.toCharArray();
        char[] mesg = text.toCharArray();
        int ml = mesg.length;
        int kl = keys.length;
        char[] newmsg = new char[ml];
        for (int i = 0; i < ml; i++) {
            newmsg[i] = (char) (mesg[i] ^ keys[i % kl]);
        }
        return new String(newmsg);
    }
    
    /**
     * Returns text encoded with base64.
     *
     * @param text
     *            text to encode
     * @return text encoded with base64
     */
    public static String base64encode(String text) {
        try {
            return Base64.encode(text.getBytes(DEFAULT_ENCODING));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
    
    /**
     * Returns text decoded with base64.
     *
     * @param text
     *            text to decoded
     * @return text decoded with base64
     */
    public static String base64decode(String text) {
        try {
            return new String(Base64.decode(text), DEFAULT_ENCODING);
        } catch (IOException e) {
            return null;
        }
    }
}
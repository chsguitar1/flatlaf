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

package org.ocsoft.flatlaf.utils.general;

import java.io.Serializable;

import org.ocsoft.flatlaf.utils.reflection.ReflectUtils;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This class represents single name-value pair.
 *
 * @author Mikle Garin
 */

@XStreamAlias("Pair")
public class Pair<K, V> implements Serializable, Cloneable {
    /**
     * Key of this {@code Pair}.
     */
    @XStreamAsAttribute
    public K key;
    
    /**
     * Value of this this {@code Pair}.
     */
    @XStreamAsAttribute
    public V value;
    
    /**
     * Constructs a new pair with {@code null} key and value.
     */
    public Pair() {
        this(null, null);
    }
    
    /**
     * Constructs a new pair with {@code null} value.
     *
     * @param key
     *            The key for this pair
     */
    public Pair(final K key) {
        this(key, null);
    }
    
    /**
     * Constructs a new pair
     *
     * @param key
     *            The key for this pair
     * @param value
     *            The value to use for this pair
     */
    public Pair(final K key, final V value) {
        super();
        this.key = key;
        this.value = value;
    }
    
    /**
     * Gets the key for this pair.
     *
     * @return key for this pair
     */
    public K getKey() {
        return key;
    }
    
    /**
     * Sets the key for this pair.
     *
     * @param key
     *            new key for this pair
     */
    public void setKey(final K key) {
        this.key = key;
    }
    
    /**
     * Gets the value for this pair.
     *
     * @return value for this pair
     */
    public V getValue() {
        return value;
    }
    
    /**
     * Sets the value for this pair.
     *
     * @param value
     *            new value for this pair
     */
    public void setValue(final V value) {
        this.value = value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return key + "=" + value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return key.hashCode() * 13 + (value == null ? 0 : value.hashCode());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Pair) {
            final Pair pair = (Pair) o;
            return !(key != null ? !key.equals(pair.key) : pair.key != null)
                    && !(value != null ? !value.equals(pair.value)
                            : pair.value != null);
        }
        return false;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<K, V> clone() {
        if (getKey() instanceof Cloneable && getValue() instanceof Cloneable) {
            return (Pair<K, V>) new Pair(
                    ReflectUtils.cloneSafely((Cloneable) getKey()),
                    ReflectUtils.cloneSafely((Cloneable) getValue()));
        } else {
            throw new RuntimeException(
                    "Both key and value should implement Cloneable!");
        }
    }
}
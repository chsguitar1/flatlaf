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

package org.ocsoft.flatlaf.utils.xml;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * User: mgarin Date: 17.02.12 Time: 12:25
 */

@XStreamAlias("ResourceMap")
public class ResourceMap implements Serializable {
    @XStreamImplicit
    private Map<String, ResourceFile> states = new HashMap<String, ResourceFile>();
    
    public ResourceMap() {
        super();
    }
    
    public void addState(final String state, final ResourceFile resource) {
        states.put(state, resource);
    }
    
    public void removeState(final String state) {
        states.remove(state);
    }
    
    public ResourceFile getState(final String state) {
        return states.get(state);
    }
    
    public Map<String, ResourceFile> getStates() {
        return states;
    }
    
    public void setStates(final Map<String, ResourceFile> states) {
        this.states = states;
    }
}
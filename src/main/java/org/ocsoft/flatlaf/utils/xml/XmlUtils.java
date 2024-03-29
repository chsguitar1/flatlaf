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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import org.ocsoft.flatlaf.utils.collection.ValuesTable;
import org.ocsoft.flatlaf.utils.file.FileUtils;
import org.ocsoft.flatlaf.utils.general.Pair;
import org.ocsoft.flatlaf.utils.reflection.ReflectUtils;
import org.ocsoft.flatlaf.utils.system.FlatLafLogger;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * This class provides a set of utilities to easily serialize and deserialize
 * objects into and from XML. There are is a lot of methods to read specific
 * library objects using various resource files.
 *
 * @author Mikle Garin
 */

public final class XmlUtils {
    /**
     * Whether should offer better aliases for standard Java classes like Point
     * and Rectangle or not.
     */
    public static boolean aliasJdkClasses = true;
    
    /**
     * Custom converters.
     */
    public static final ColorConverter colorConverter = new ColorConverter();
    public static final InsetsConverter insetsConverter = new InsetsConverter();
    
    /**
     * Custom password converter that encrypts serialized passwords.
     */
    public static final PasswordConverter passwordConverter = new PasswordConverter();
    
    /**
     * XStream instance.
     */
    private static XStream xStream = null;
    
    /**
     * Returns global XStream instance configured with all required aliases and
     * converters.
     *
     * @return XStream
     */
    public static XStream getXStream() {
        if (xStream == null) {
            initializeXStream();
        }
        return xStream;
    }
    
    /**
     * Initializes global XStream instance.
     */
    private static void initializeXStream() {
        try {
            // XStream instnce initialization
            xStream = new XStream(new DomDriver());
            // xStream.setMode ( XStream.ID_REFERENCES );
            
            // Standart Java-classes aliases
            if (aliasJdkClasses) {
                xStream.alias("Point", Point.class);
                xStream.useAttributeFor(Point.class, "x");
                xStream.useAttributeFor(Point.class, "y");
                xStream.alias("Dimension", Dimension.class);
                xStream.useAttributeFor(Dimension.class, "width");
                xStream.useAttributeFor(Dimension.class, "height");
                xStream.alias("Rectangle", Rectangle.class);
                xStream.useAttributeFor(Rectangle.class, "x");
                xStream.useAttributeFor(Rectangle.class, "y");
                xStream.useAttributeFor(Rectangle.class, "width");
                xStream.useAttributeFor(Rectangle.class, "height");
                xStream.alias("Font", Font.class);
                xStream.alias("Color", Color.class);
                xStream.registerConverter(colorConverter);
                xStream.alias("Insets", Insets.class);
                xStream.registerConverter(insetsConverter);
            }
            
            // XML resources aliases
            xStream.processAnnotations(ResourceLocation.class);
            xStream.processAnnotations(ResourceFile.class);
            xStream.processAnnotations(ResourceList.class);
            xStream.processAnnotations(ResourceMap.class);
            
            // Additional WebLaF data classes aliases
            xStream.processAnnotations(ValuesTable.class);
            xStream.processAnnotations(Pair.class);
        } catch (final Throwable e) {
            FlatLafLogger.error(XmlUtils.class, e);
        }
    }
    
    /**
     * Process the annotations of the given type and configure the XStream. A
     * call of this method will automatically turn the auto-detection mode for
     * annotations off.
     *
     * @param type
     *            the type with XStream annotations
     */
    public static void processAnnotations(final Class type) {
        getXStream().processAnnotations(type);
    }
    
    /**
     * Process the annotations of the given types and configure the XStream.
     *
     * @param types
     *            the types with XStream annotations
     */
    public static void processAnnotations(final Class[] types) {
        getXStream().processAnnotations(types);
    }
    
    /**
     * Alias a Class to a shorter name to be used in XML elements.
     *
     * @param name
     *            Short name
     * @param type
     *            Type to be aliased
     */
    public static void alias(final String name, final Class type) {
        getXStream().alias(name, type);
    }
    
    /**
     * Use an attribute for a field declared in a specific type.
     *
     * @param type
     *            the name of the field
     * @param field
     *            the Class containing such field
     */
    public static void useAttributeFor(final Class type, final String field) {
        getXStream().useAttributeFor(type, field);
    }
    
    /**
     * Adds an implicit array.
     *
     * @param type
     *            class owning the implicit array
     * @param field
     *            name of the array field
     */
    public static void addImplicitArray(final Class type, final String field) {
        getXStream().addImplicitArray(type, field);
    }
    
    /**
     * Adds an implicit array which is used for all items of the given element
     * name defined by itemName.
     *
     * @param type
     *            class owning the implicit array
     * @param field
     *            name of the array field in the ownerType
     * @param itemName
     *            alias name of the items
     */
    public static void addImplicitArray(final Class type, final String field,
            final String itemName) {
        getXStream().addImplicitArray(type, field, itemName);
    }
    
    /**
     * Adds a new converter into converters registry.
     *
     * @param converter
     *            the new converter
     */
    public static void registerConverter(final Converter converter) {
        getXStream().registerConverter(converter);
    }
    
    /**
     * Adds a new converter into converters registry.
     *
     * @param converter
     *            the new converter
     */
    public static void registerConverter(final SingleValueConverter converter) {
        getXStream().registerConverter(converter);
    }
    
    /**
     * Calls static "provideAliases" method on a class that implements
     * AliasProvider. This method is created mostly for internal library usage.
     *
     * @param aliasProvider
     *            AliasProvider ancestor class
     * @param <T>
     *            specific class type
     */
    public static <T extends AliasProvider> void alias(
            final Class<T> aliasProvider) {
        ReflectUtils.callStaticMethodSafely(aliasProvider,
                AliasProvider.methodName, getXStream());
    }
    
    /**
     * Serializes Object into XML and writes it into specified file.
     *
     * @param obj
     *            object to serialize
     * @param file
     *            output file
     */
    public static void toXML(final Object obj, final String file) {
        toXML(obj, new File(file));
    }
    
    /**
     * Serializes Object into XML and writes it into specified file.
     *
     * @param obj
     *            object to serialize
     * @param file
     *            output file
     */
    public static void toXML(final Object obj, final File file) {
        try {
            final FileOutputStream fos = new FileOutputStream(file);
            final OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            toXML(obj, osw);
            osw.close();
            fos.close();
        } catch (final IOException e) {
            FlatLafLogger.error(XmlUtils.class, e);
        }
    }
    
    /**
     * Returns Object serialized into XML.
     *
     * @param obj
     *            object to serialize
     * @return serialized into XML object representation
     */
    public static String toXML(final Object obj) {
        return getXStream().toXML(obj);
    }
    
    /**
     * Serializes Object into XML and writes it using a specified Writer.
     *
     * @param obj
     *            object to serialize
     * @param out
     *            output writer
     */
    public static void toXML(final Object obj, final Writer out) {
        getXStream().toXML(obj, out);
    }
    
    /**
     * Serializes Object into XML and writes it using a specified OutputStream.
     *
     * @param obj
     *            object to serialize
     * @param out
     *            output stream
     */
    public static void toXML(final Object obj, final OutputStream out) {
        getXStream().toXML(obj, out);
    }
    
    /**
     * Serializes Object into XML and writes it using a specified
     * HierarchicalStreamWriter
     *
     * @param obj
     *            object to serialize
     * @param writer
     *            hierarchical stream writer
     */
    public static void toXML(final Object obj,
            final HierarchicalStreamWriter writer) {
        getXStream().marshal(obj, writer);
    }
    
    /**
     * Returns Object deserialized from XML content.
     *
     * @param reader
     *            XML text source
     * @param <T>
     *            read object type
     * @return deserialized object
     */
    public static <T> T fromXML(final Reader reader) {
        return (T) getXStream().fromXML(reader);
    }
    
    /**
     * Returns Object deserialized from XML content.
     *
     * @param input
     *            XML text source
     * @param <T>
     *            read object type
     * @return deserialized object
     */
    public static <T> T fromXML(final InputStream input) {
        return (T) getXStream().fromXML(input);
    }
    
    /**
     * Returns Object deserialized from XML text
     *
     * @param url
     *            XML text source
     * @param <T>
     *            read object type
     * @return deserialized object
     */
    public static <T> T fromXML(final URL url) {
        return (T) getXStream().fromXML(url);
    }
    
    /**
     * Returns Object deserialized from XML content.
     *
     * @param file
     *            file with XML content
     * @param <T>
     *            read object type
     * @return deserialized object
     */
    public static <T> T fromXML(final File file) {
        return (T) getXStream().fromXML(file);
    }
    
    /**
     * Returns Object deserialized from XML content.
     *
     * @param xml
     *            XML content
     * @param <T>
     *            read object type
     * @return deserialized object
     */
    public static <T> T fromXML(final String xml) {
        return (T) getXStream().fromXML(xml);
    }
    
    /**
     * Returns Object deserialized from XML content.
     *
     * @param source
     *            XML text source
     * @param <T>
     *            read object type
     * @return deserialized object
     */
    public static <T> T fromXML(final Object source) {
        if (source instanceof URL) {
            return fromXML((URL) source);
        } else if (source instanceof String) {
            return fromXML(new File((String) source));
        } else if (source instanceof File) {
            return fromXML((File) source);
        } else if (source instanceof Reader) {
            return fromXML((Reader) source);
        } else if (source instanceof InputStream) {
            return fromXML((InputStream) source);
        } else {
            return null;
        }
    }
    
    /**
     * Returns Object deserialized from XML text
     *
     * @param resource
     *            XML text source description
     * @param <T>
     *            read object type
     * @return deserialized object
     */
    public static <T> T fromXML(final ResourceFile resource) {
        switch (resource.getLocation()) {
        case url: {
            try {
                return XmlUtils.fromXML(new URL(resource.getSource()));
            } catch (final MalformedURLException e) {
                FlatLafLogger.error(XmlUtils.class, e);
                return null;
            }
        }
        case filePath: {
            return XmlUtils.fromXML(new File(resource.getSource()));
        }
        case nearClass: {
            InputStream is = null;
            try {
                is = Class.forName(resource.getClassName())
                        .getResourceAsStream(resource.getSource());
                if (is == null) {
                    final String src = resource.getSource();
                    final String cn = resource.getClassName();
                    throw new RuntimeException("Unable to read XML file \""
                            + src + "\" near class \"" + cn + "\"");
                }
                return XmlUtils.fromXML(is);
            } catch (final ClassNotFoundException e) {
                FlatLafLogger.error(XmlUtils.class, e);
                return null;
            } catch (final Throwable e) {
                FlatLafLogger.error(XmlUtils.class, e);
                return null;
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (final Throwable e) {
                    FlatLafLogger.error(XmlUtils.class, e);
                }
            }
        }
        default: {
            return null;
        }
        }
    }
    
    /**
     * Returns text which is read from the source.
     *
     * @param source
     *            one of possible sources: URL, String, File, Reader,
     *            InputStream
     * @return text as String
     */
    public static String loadString(final Object source) {
        return loadString(loadResourceFile(source));
    }
    
    /**
     * Returns text which is read from specified ResourceFile.
     *
     * @param resource
     *            file description
     * @return text as String
     */
    public static String loadString(final ResourceFile resource) {
        if (resource.getLocation().equals(ResourceLocation.url)) {
            try {
                return FileUtils.readToString(new BufferedReader(
                        new InputStreamReader(new URL(resource.getSource())
                                .openStream())));
            } catch (final IOException e) {
                FlatLafLogger.error(XmlUtils.class, e);
                return null;
            }
        }
        if (resource.getLocation().equals(ResourceLocation.filePath)) {
            return FileUtils.readToString(new File(resource.getSource()));
        } else if (resource.getLocation().equals(ResourceLocation.nearClass)) {
            try {
                return FileUtils.readToString(
                        Class.forName(resource.getClassName()),
                        resource.getSource());
            } catch (final ClassNotFoundException e) {
                FlatLafLogger.error(XmlUtils.class, e);
                return null;
            }
        } else {
            return null;
        }
    }
    
    /**
     * Returns ImageIcon which is read from the source.
     *
     * @param source
     *            one of possible sources: URL, String, File, Reader,
     *            InputStream
     * @return ImageIcon
     */
    public static ImageIcon loadImageIcon(final Object source) {
        return loadImageIcon(loadResourceFile(source));
    }
    
    /**
     * Returns ImageIcon which is read from specified ResourceFile.
     *
     * @param resource
     *            file description
     * @return ImageIcon
     */
    public static ImageIcon loadImageIcon(final ResourceFile resource) {
        if (resource.getLocation().equals(ResourceLocation.url)) {
            try {
                return new ImageIcon(new URL(resource.getSource()));
            } catch (final MalformedURLException e) {
                FlatLafLogger.error(XmlUtils.class, e);
                return null;
            }
        }
        if (resource.getLocation().equals(ResourceLocation.filePath)) {
            try {
                return new ImageIcon(
                        new File(resource.getSource()).getCanonicalPath());
            } catch (final IOException e) {
                FlatLafLogger.error(XmlUtils.class, e);
                return null;
            }
        } else if (resource.getLocation().equals(ResourceLocation.nearClass)) {
            try {
                return new ImageIcon(Class.forName(resource.getClassName())
                        .getResource(resource.getSource()));
            } catch (final ClassNotFoundException e) {
                FlatLafLogger.error(XmlUtils.class, e);
                return null;
            }
        } else {
            return null;
        }
    }
    
    /**
     * Returns ImageIcon list which is read from the source.
     *
     * @param source
     *            one of possible sources: URL, String, File, Reader,
     *            InputStream
     * @return ImageIcon list
     */
    public static List<ImageIcon> loadImagesList(final Object source) {
        return loadImagesList(loadResourceList(source));
    }
    
    /**
     * Returns ImageIcon list which is read from specified ResourceList.
     *
     * @param resourceList
     *            ResourceFile list
     * @return ImageIcon list
     */
    public static List<ImageIcon> loadImagesList(final ResourceList resourceList) {
        final List<ImageIcon> icons = new ArrayList<ImageIcon>();
        for (final ResourceFile resource : resourceList.getResources()) {
            final ImageIcon imageIcon = loadImageIcon(resource);
            if (imageIcon != null) {
                icons.add(imageIcon);
            }
        }
        return icons;
    }
    
    /**
     * Returns ResourceMap which is read from the source.
     *
     * @param source
     *            one of possible sources: URL, String, File, Reader,
     *            InputStream
     * @return ResourceMap
     */
    public static ResourceMap loadResourceMap(final Object source) {
        return fromXML(source);
    }
    
    /**
     * Returns ResourceList which is read from the source.
     *
     * @param source
     *            one of possible sources: URL, String, File, Reader,
     *            InputStream
     * @return ResourceList
     */
    public static ResourceList loadResourceList(final Object source) {
        return fromXML(source);
    }
    
    /**
     * Returns ResourceFile which is read from the source.
     *
     * @param source
     *            one of possible sources: URL, String, File, Reader,
     *            InputStream
     * @return ResourceFile
     */
    public static ResourceFile loadResourceFile(final Object source) {
        return fromXML(source);
    }
}
package org.ocsoft.flatlaf.core.constants;

import java.util.Arrays;
import java.util.List;

import org.ocsoft.flatlaf.utils.filefilter.AbstractFileFilter;
import org.ocsoft.flatlaf.utils.filefilter.AllFilesFilter;
import org.ocsoft.flatlaf.utils.filefilter.DirectoriesFilter;
import org.ocsoft.flatlaf.utils.filefilter.FilesFilter;
import org.ocsoft.flatlaf.utils.filefilter.FilterGroupType;
import org.ocsoft.flatlaf.utils.filefilter.GroupedFileFilter;
import org.ocsoft.flatlaf.utils.filefilter.ImageFilesFilter;
import org.ocsoft.flatlaf.utils.filefilter.NonHiddenFilter;

public class FlatLafFileFilters {
    

    
    /**
     * File filters.
     */
    public static final AllFilesFilter ALL_FILES_FILTER = new AllFilesFilter ();
    public static final NonHiddenFilter NON_HIDDEN_ONLY_FILTER = new NonHiddenFilter ();
    public static final DirectoriesFilter DIRECTORIES_FILTER = new DirectoriesFilter ();
    public static final GroupedFileFilter NON_HIDDEN_DIRECTORIES_FILTER =
            new GroupedFileFilter ( FilterGroupType.AND, DIRECTORIES_FILTER, NON_HIDDEN_ONLY_FILTER);
    public static final FilesFilter FILES_FILTER = new FilesFilter ();
    public static final ImageFilesFilter IMAGES_FILTER = new ImageFilesFilter ();
    public static final GroupedFileFilter IMAGES_AND_FOLDERS_FILTER =
            new GroupedFileFilter ( FilterGroupType.OR, IMAGES_FILTER, DIRECTORIES_FILTER );

    /**
     * Default file filters.
     */
    public static final List<AbstractFileFilter> DEFAULT_FILTERS =
            Arrays.asList ( ALL_FILES_FILTER, IMAGES_AND_FOLDERS_FILTER, DIRECTORIES_FILTER );

    
}

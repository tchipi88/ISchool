package com.tsoft.ischool.service.util;

import java.io.File;

/**
 *
 * @author tchipnangngansopa
 */
public class FileUtils {

    public static File getUploadedfile() {
        File uploadedfile = new File("." + File.separator + "reports" + File.separator + "output");
        if (!uploadedfile.exists()) {
            uploadedfile.mkdirs();
        }
        return uploadedfile;
    }
}

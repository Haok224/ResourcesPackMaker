package org.haok.resourcespackmaker;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipTools {
    private static final byte[] buf = new byte[1024 * 1024];
    private static final Logger LOGGER = Logger.getLogger(ZipTools.class);
    public static void toZip(String zipFileName, List<File> srcFiles) {
        LOGGER.debug("zip file name:"+zipFileName);
        LOGGER.debug(srcFiles);
        ZipOutputStream zos = null;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(zipFileName);
            zos = new ZipOutputStream(fileOutputStream);
            for (File srcFile : srcFiles) {
                compress(srcFile, zos, srcFile.getName(), true);
            }
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void compress(File sourceFile, ZipOutputStream zos, String name,
                                boolean KeepDirStructure) throws Exception {

        if (sourceFile.isFile()) {
            zos.putNextEntry(new ZipEntry(name));
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                if (KeepDirStructure) {
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    zos.closeEntry();
                }
            } else {
                for (File file : listFiles) {
                    if (KeepDirStructure) {
                        compress(file, zos, name + "/" + file.getName(), true);
                    } else {
                        compress(file, zos, file.getName(), false);
                    }

                }
            }
        }
    }

}

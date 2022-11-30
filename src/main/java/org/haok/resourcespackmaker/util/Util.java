package org.haok.resourcespackmaker.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Util {
    private static final Logger LOGGER = LogManager.getLogger(Util.class);
    private static final byte[] buf = new byte[1024 * 1024];
    public static void copy(File source, File dest) throws IOException {
        LOGGER.debug("copy file from " + source.getAbsolutePath() + " to " + dest.getAbsolutePath());
        if (!dest.isFile()) {
            LOGGER.debug("the dest file does not exists. create new file:" + dest.createNewFile());
        }
        try (InputStream input = new FileInputStream(source); OutputStream output = new FileOutputStream(dest)) {
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < Objects.requireNonNull(children).length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));

                if (!success) {
                    return false;
                }
            }
        }
        LOGGER.debug("The directory is deleted.");
        return dir.delete();
    }
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

    private static void compress(File sourceFile, ZipOutputStream zos, String name,
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

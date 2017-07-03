package com.okwyx.client.framework.libs.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;

public class IOUtil {
    public static final int EOF = -1;
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    public static FileInputStream openInputStream(final File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (file.canRead() == false) {
                throw new IOException("File '" + file + "' cannot be read");
            }
        } else {
            throw new FileNotFoundException("File '" + file + "' does not exist");
        }
        return new FileInputStream(file);
    }

    public static long copy(final Reader input, final Writer output, final char[] buffer) throws IOException {
        long count = 0;
        int n;
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }
    
    public static void writeString(String value , File destFile){
        FileOutputStream fos = null;
        try {
            if(destFile.getParentFile() != null && !destFile.exists()){
                destFile.getParentFile().mkdirs();
            }
            fos = new FileOutputStream(destFile);
            try {
                fos.write(value.getBytes("utf-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static String toString(final String filePath, final String encoding) throws IOException {
        return toString(openInputStream(new File(filePath)), encoding);
    }
    
    public static String toString(final File file, final String encoding) throws IOException {
        return toString(openInputStream(file), encoding);
    }

    public static String toString(final InputStream input, final String encoding) throws IOException {
        final StringBuilderWriter sw = new StringBuilderWriter();
        final InputStreamReader in = new InputStreamReader(input, encoding);
        copy(in, sw, new char[DEFAULT_BUFFER_SIZE]);
        return sw.toString();
    }
    

}

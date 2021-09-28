package fr.omega2028.challenge.utils;

import com.google.common.io.Files;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Disc Util
 */
public class DiscUtil {

    private final Map<String, Lock> locks;

    /**
     * Disc Util
     */
    public DiscUtil() {
        this.locks = new HashMap<>();
    }

    /**
     * read Bytes
     *
     * @param file file
     * @return output
     */
    public byte[] readBytes(File file) {
        int length = (int) file.length();
        byte[] output = new byte[length];
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
            int offset = 0;
            while (offset < length) {
                offset += in.read(output, offset, (length - offset));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    /**
     * write Bytes
     *
     * @param file file
     * @param bytes bytes
     */
    public void writeBytes(File file, byte[] bytes) {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
            out.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * write
     *
     * @param file file
     * @param content content
     */
    public void write(File file, String content) {
        writeBytes(file, utf8(content));
    }

    /**
     * read
     *
     * @param file file
     * @return read
     */
    public String read(File file) {
        return utf8(readBytes(file));
    }

    /**
     * write Catch
     *
     * @param file file
     * @param content content
     */
    public void writeCatch(final File file, final String content) {
        String name = file.getName();
        final Lock lock;

        // Create lock for each file if there isn't already one.
        if (locks.containsKey(name)) {
            lock = locks.get(name);
        } else {
            ReadWriteLock rwl = new ReentrantReadWriteLock();
            lock = rwl.writeLock();
            locks.put(name, lock);
        }
        lock.lock();
        try {
            file.createNewFile();
            Files.write(content, file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * read Catch
     *
     * @param file file
     * @return read
     */
    public String readCatch(File file) {
        return read(file);
    }

    /**
     * utf8 string
     *
     * @param string string
     * @return byte
     */
    public byte[] utf8(String string) {
        return string.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * utf8 bytes
     *
     * @param bytes bytes
     * @return String
     */
    public String utf8(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

}

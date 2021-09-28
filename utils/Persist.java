package fr.omega2028.challenge.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

/**
 * Persist
 */
public class Persist {


    private final Gson gson;
    private final DiscUtil discUtil;
    private final JavaPlugin javaPlugin;

    /**
     * Persist
     */
    public Persist(JavaPlugin javaPlugin) {
        this.gson = buildGson().create();
        this.discUtil = new DiscUtil();
        this.javaPlugin = javaPlugin;
    }

    /**
     * get Name
     *
     * @param clazz clazz
     * @return string
     */
    public static String getName(Class<?> clazz) {
        return clazz.getSimpleName().toLowerCase();
    }

    /**
     * get name
     *
     * @param object object
     * @return string
     */
    public static String getName(Object object) {
        return getName(object.getClass());
    }

    /**
     * get Name
     *
     * @param type type
     * @return string
     */
    public static String getName(Type type) {
        return getName(type.getClass());
    }

    /**
     * build Gson
     *
     * @return GsonBuilder
     */
    private GsonBuilder buildGson() {
        return new GsonBuilder().setPrettyPrinting().disableHtmlEscaping()
                .enableComplexMapKeySerialization()
                .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.VOLATILE);
    }

    /**
     * get File
     *
     * @param name name
     * @return File
     */
    public File getFile(String name) {
        return new File(javaPlugin.getDataFolder(), name + ".json");
    }

    /**
     * get File
     *
     * @param clazz clazz
     * @return File
     */
    public File getFile(Class<?> clazz) {
        return getFile(getName(clazz));
    }

    /**
     * get File
     *
     * @param obj Object
     * @return File
     */
    public File getFile(Object obj) {
        return getFile(getName(obj));
    }

    /**
     * get File
     *
     * @param type Type
     * @return File
     */
    public File getFile(Type type) {
        return getFile(getName(type));
    }

    /**
     * save
     *
     * @param instance instance
     */
    public void save(Object instance) {
        save(instance, getFile(instance));
    }

    /**
     * save
     *
     * @param instance instance
     * @param file file
     */
    public void save(Object instance, File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        discUtil.writeCatch(file, gson.toJson(instance));
    }

    /**
     * load
     *
     * @param clazz clazz
     * @param <T> Class
     * @return T
     */
    public <T> T load(Class<T> clazz) {
        return load(clazz, getFile(clazz));
    }

    /**
     * load
     *
     * @param clazz clazz
     * @param file file
     * @param <T> Class
     * @return T
     */
    public <T> T load(Class<T> clazz, File file) {
        String content = discUtil.readCatch(file);
        Bukkit.getLogger().info("Ce qui est lu dans load de persist : " + content);
        if (content == null) {
            return null;
        }

        try {
            return gson.fromJson(content, clazz);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
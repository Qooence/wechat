package com.qooems.wechat.common.util.poi;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
public final class POICacheManager {
    private static LoadingCache<String, byte[]> loadingCache;
    private static IFileLoader fileLoder;
    private static ThreadLocal<IFileLoader> LOCAL_FILELOADER = new ThreadLocal();

    public POICacheManager() {

    }

    public static InputStream getFile(String id) {
        try {
            byte[] result = Arrays.copyOf(loadingCache.get(id), (loadingCache.get(id)).length);
            return new ByteArrayInputStream(result);
        } catch (ExecutionException var2) {
            log.error(var2.getMessage(), var2);
            return null;
        }
    }

    public static void setFileLoder(IFileLoader fileLoder) {
        POICacheManager.fileLoder = fileLoder;
    }

    public static void setFileLoderOnce(IFileLoader fileLoder) {
        if (fileLoder != null) {
            LOCAL_FILELOADER.set(fileLoder);
        }

    }

    public static void setLoadingCache(LoadingCache<String, byte[]> loadingCache) {
        POICacheManager.loadingCache = loadingCache;
    }

    static {
        loadingCache = CacheBuilder.newBuilder().expireAfterWrite(1L, TimeUnit.HOURS).maximumSize(50L).build(new CacheLoader<String, byte[]>() {
            public byte[] load(String url) throws Exception {
                return POICacheManager.LOCAL_FILELOADER.get() != null ? (POICacheManager.LOCAL_FILELOADER.get()).getFile(url) : POICacheManager.fileLoder.getFile(url);
            }
        });
        fileLoder = new FileLoaderImpl();
    }
}

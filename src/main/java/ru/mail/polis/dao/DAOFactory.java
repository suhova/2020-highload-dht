package ru.mail.polis.dao;

import org.jetbrains.annotations.NotNull;
import ru.mail.polis.dao.suhova.TurboDAO;

import java.io.File;

/**
 * Custom {@link DAO} factory.
 *
 * @author Vadim Tsesko
 */
public final class DAOFactory {
    static final long MAX_HEAP = 256 * 1024 * 1024;

    private DAOFactory() {
        // Not instantiatable
    }

    /**
     * Construct a {@link DAO} instance.
     *
     * @param data local disk folder to persist the data to
     * @return a storage instance
     */
    @NotNull
    public static DAO create(@NotNull final File data) {
        if (Runtime.getRuntime().maxMemory() > MAX_HEAP) {
            throw new IllegalStateException("The heap is too big. Consider setting Xmx.");
        }

        if (!data.exists()) {
            throw new IllegalArgumentException("Path doesn't exist: " + data);
        }

        if (!data.isDirectory()) {
            throw new IllegalArgumentException("Path is not a directory: " + data);
        }
        return new TurboDAO(data, MAX_HEAP / 32, Runtime.getRuntime().availableProcessors());
    }
}

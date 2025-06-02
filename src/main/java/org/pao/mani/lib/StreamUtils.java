package org.pao.mani.lib;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class StreamUtils {
    public static <T, R> Stream<R> filterMap(Stream<T> stream, Function<T, Optional<R>> mapper) {
        return stream.map(mapper).flatMap(Optional::stream);
    }
}

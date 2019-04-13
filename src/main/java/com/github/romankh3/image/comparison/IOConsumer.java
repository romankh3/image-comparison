package com.github.romankh3.image.comparison;

import java.io.IOException;

@FunctionalInterface
interface IOConsumer<T> {

    void accept(T t) throws IOException;
}

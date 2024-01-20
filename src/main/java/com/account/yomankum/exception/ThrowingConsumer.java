package com.account.yomankum.exception;

import java.util.function.Consumer;

// Exception 을 래핑하는 사용자 정의 런타임 예외 발생 인터페이스
// 람다식 안에서 예외가 발생했을 때 쓰세요!
@FunctionalInterface
public interface ThrowingConsumer<T, E extends Exception> {
    void accept(T t) throws E;

    static <T> Consumer<T> unchecked(ThrowingConsumer<T, Exception> consumer) {
        return t -> {
            try {
                consumer.accept(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}

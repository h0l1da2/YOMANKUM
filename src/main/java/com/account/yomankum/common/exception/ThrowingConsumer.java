package com.account.yomankum.common.exception;

import java.util.function.Consumer;

// Exception 을 래핑하는 사용자 정의 런타임 예외 발생 인터페이스
// 람다식 안에서 예외가 발생했을 때 쓰세요!
@FunctionalInterface
public interface ThrowingConsumer<T, E extends java.lang.Exception> {
    void accept(T t) throws E;

    static <T> Consumer<T> unchecked(ThrowingConsumer<T, java.lang.Exception> consumer) {
        return t -> {
            try {
                consumer.accept(t);
            } catch (java.lang.Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        };
    }
}

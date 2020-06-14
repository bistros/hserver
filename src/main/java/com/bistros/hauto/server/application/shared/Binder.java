package com.bistros.hauto.server.application.shared;

public interface Binder<Q extends Request, R> {
    Q bind(R r);
}

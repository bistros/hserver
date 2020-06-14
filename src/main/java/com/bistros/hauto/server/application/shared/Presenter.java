package com.bistros.hauto.server.application.shared;


public interface Presenter<R extends Response, V> {
    V present(R r);
}

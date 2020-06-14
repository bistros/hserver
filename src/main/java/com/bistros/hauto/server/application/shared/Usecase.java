package com.bistros.hauto.server.application.shared;

import java.util.function.Function;

@FunctionalInterface
public interface Usecase<Q extends Request, S extends Response> extends Function<Q, S> {


    S apply(Q q);
}

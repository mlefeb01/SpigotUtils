package com.github.mlefeb01.spigotutils.currency;

import com.github.mlefeb01.spigotutils.struct.AbstractRegistry;

public final class CurrencyRegistry extends AbstractRegistry<Currency> {
    private static final CurrencyRegistry instance = new CurrencyRegistry();

    private CurrencyRegistry() {}

    public static CurrencyRegistry getInstance() {
        return instance;
    }

}

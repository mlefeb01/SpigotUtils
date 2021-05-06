package com.github.mlefeb01.spigotutils.plugin.currency;

import com.github.mlefeb01.spigotutils.plugin.struct.AbstractRegistry;

public final class CurrencyRegistry extends AbstractRegistry<Currency> {
    private static final CurrencyRegistry instance = new CurrencyRegistry();

    private CurrencyRegistry() {}

    public static CurrencyRegistry getInstance() {
        return instance;
    }

}

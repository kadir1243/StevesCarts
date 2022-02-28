/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package vswe.stevescarts.util;

import java.util.function.LongConsumer;
import java.util.function.LongSupplier;

public abstract class LongProperty implements LongSupplier, LongConsumer {
    private long oldValue;

    public static LongProperty create() {
        return new LongProperty(){
            private long value;

            @Override
            public long getAsLong() {
                return this.value;
            }

            @Override
            public void accept(long value) {
                this.value = value;
            }
        };
    }

    public boolean hasChanged() {
        long l = this.getAsLong();
        boolean changed = l != this.oldValue;
        this.oldValue = l;
        return changed;
    }

    public void inc(long amount) {
        this.accept(this.getAsLong() + amount);
    }
}

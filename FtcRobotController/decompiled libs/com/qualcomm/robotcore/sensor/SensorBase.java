/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.sensor;

import com.qualcomm.robotcore.sensor.SensorListener;
import java.util.List;

public abstract class SensorBase<T> {
    protected List<SensorListener<T>> mListeners;

    public SensorBase(List<SensorListener<T>> listeners) {
        this.mListeners = listeners;
    }

    public abstract boolean initialize();

    public abstract boolean shutdown();

    public abstract boolean resume();

    public abstract boolean pause();

    public final void update(T data) {
        List<SensorListener<T>> list = this.mListeners;
        synchronized (list) {
            if (this.mListeners == null) {
                return;
            }
            for (SensorListener<T> sensorListener : this.mListeners) {
                sensorListener.onUpdate(data);
            }
        }
    }
}


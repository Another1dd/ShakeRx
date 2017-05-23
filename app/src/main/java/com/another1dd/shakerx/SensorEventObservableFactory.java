package com.another1dd.shakerx;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import io.reactivex.Observable;
import io.reactivex.android.MainThreadDisposable;
import io.reactivex.annotations.NonNull;

/**
 * Created by Vasliy on 23.05.2017.
 */

public class SensorEventObservableFactory {
    public static Observable<SensorEvent> createSensorEventObservable(@NonNull Sensor sensor,
                                                                      @NonNull SensorManager sensorManager) {
        return Observable.create(subscriber -> {
            MainThreadDisposable.verifyMainThread();

            SensorEventListener listener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    if (subscriber.isDisposed()) {
                        return;
                    }

                    subscriber.onNext(event);
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            };

            sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_GAME);

            subscriber.setDisposable(new MainThreadDisposable() {
                @Override
                protected void onDispose() {
                    sensorManager.unregisterListener(listener);
                }
            });
        });
    }
}

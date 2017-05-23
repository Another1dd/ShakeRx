package com.another1dd.shakerx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private Observable<?> mShakeObservable;
    private Disposable mShakeSubscription;
    private int t = 0;
    private TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mShakeObservable = ShakeDetector.create(this);
        test = (TextView) findViewById(R.id.test);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mShakeSubscription = mShakeObservable.subscribe((object) -> {
            t = t + 1;
            test.setText(String.valueOf(t));
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mShakeSubscription.dispose();
    }
}

package com.reactnativedemo;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.Map;
import java.util.HashMap;

public class ToastModule extends ReactContextBaseJavaModule implements LifecycleEventListener {
    private static ReactApplicationContext reactContext;

    private static final String DURATION_SHORT_KEY = "SHORT";
    private static final String DURATION_LONG_KEY = "LONG";

    ToastModule(ReactApplicationContext context) {
        super(context);
        reactContext = context;
        context.addLifecycleEventListener((LifecycleEventListener) this);
    }

    @NonNull
    @Override
    public String getName() {
        return "ToastExample";
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put(DURATION_SHORT_KEY, Toast.LENGTH_SHORT);
        constants.put(DURATION_LONG_KEY, Toast.LENGTH_LONG);
        return constants;
    }

    @ReactMethod
    public void show(String message, int duration) {
        Toast.makeText(getReactApplicationContext(), message, duration).show();

        WritableMap params = Arguments.createMap();
        params.putString("eventProperty", "This is a Event");
        sendEvent(reactContext, "EventReminder", params);
    }

    @ReactMethod
    public void useCallback(Callback callback) {
        callback.invoke("ToastModule This is a callback");
    }

    @ReactMethod
    public void usePromise(Promise promise) {
        promise.resolve("ToastModule This is a promise");
    }

    //send event to react native
    private void sendEvent(ReactContext reactContext,
                           String eventName,
                           @Nullable WritableMap params) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }

    @Override
    public void onHostResume() {
        Log.d("Action", "ToastModule onHostResume");
    }

    @Override
    public void onHostPause() {
        Log.d("Action", "ToastModule onHostPause");
    }

    @Override
    public void onHostDestroy() {
        Log.d("Action", "ToastModule onHostDestroy");
    }
}
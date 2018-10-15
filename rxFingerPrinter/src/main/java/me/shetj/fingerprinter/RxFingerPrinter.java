package me.shetj.fingerprinter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import io.reactivex.subjects.PublishSubject;

import static me.shetj.fingerprinter.CodeException.FINGERPRINTERS_FAILED_ERROR;
import static me.shetj.fingerprinter.CodeException.HARDWARE_MISSIING_ERROR;
import static me.shetj.fingerprinter.CodeException.KEYGUARDSECURE_MISSIING_ERROR;
import static me.shetj.fingerprinter.CodeException.NO_FINGERPRINTERS_ENROOLED_ERROR;
import static me.shetj.fingerprinter.CodeException.PERMISSION_DENIED_ERROE;
import static me.shetj.fingerprinter.CodeException.SYSTEM_API_ERROR;

/**
 * Created by Administrator on 2016/12/31.
 */

public class RxFingerPrinter  {
    private FingerprintManager manager;
    private KeyguardManager mKeyManager;
    private Activity context;
    private PublishSubject<Boolean> publishSubject;
    private CancellationSignal mCancellationSignal;
    private FingerprintManager.AuthenticationCallback authenticationCallback;
    private boolean mSelfCompleted;
    public RxFingerPrinter(@NonNull Activity activity) {
        this.context = activity;
        publishSubject = PublishSubject.create();
    }

    public PublishSubject<Boolean> begin() {
        if (Build.VERSION.SDK_INT < 23) {
            publishSubject.onError(new FPerException(SYSTEM_API_ERROR));
        } else {
            initManager();
            confirmFinger();
            startListening(null);
        }
        return publishSubject;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void startListening(FingerprintManager.CryptoObject cryptoObject) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT)
                != PackageManager.PERMISSION_GRANTED) {
            publishSubject.onError(new FPerException(PERMISSION_DENIED_ERROE));
        }
        mCancellationSignal = new CancellationSignal();
        if (manager != null && authenticationCallback != null) {
            mSelfCompleted = false;
            manager.authenticate(cryptoObject, mCancellationSignal, 0, authenticationCallback, null);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initManager() {
        manager = context.getSystemService(FingerprintManager.class);
        mKeyManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        authenticationCallback = new FingerprintManager.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                //多次指纹密码验证错误后，进入此方法；并且，不能短时间内调用指纹验证
                if (mCancellationSignal!=null){
                    publishSubject.onError(new FPerException(FINGERPRINTERS_FAILED_ERROR));
                    mCancellationSignal.cancel();
                    mSelfCompleted = true;
                }
            }

            @Override
            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

            }

            @Override
            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                publishSubject.onNext(true);
                mSelfCompleted = true;
                publishSubject.onComplete();
            }

            @Override
            public void onAuthenticationFailed() {
                publishSubject.onNext(false);
            }
        };
    }

    @SuppressLint("NewApi")
    @TargetApi(23)
    public void confirmFinger() {

        //android studio 上，没有这个会报错 权限判断
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT)
                != PackageManager.PERMISSION_GRANTED) {
            publishSubject.onError(new FPerException(PERMISSION_DENIED_ERROE));
        }
        //判断硬件是否支持指纹识别
        if (!manager.isHardwareDetected()) {
            publishSubject.onError(new FPerException(HARDWARE_MISSIING_ERROR));
        }
        //判断 是否开启锁屏密码

        if (!mKeyManager.isKeyguardSecure()) {
            publishSubject.onError(new FPerException(KEYGUARDSECURE_MISSIING_ERROR));
        }
        //判断是否有指纹录入
        if (!manager.hasEnrolledFingerprints()) {
            publishSubject.onError(new FPerException(NO_FINGERPRINTERS_ENROOLED_ERROR));
        }

    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void stopListening() {
        if (mCancellationSignal != null) {
            mCancellationSignal.cancel();
            mCancellationSignal = null;
        }
    }
}

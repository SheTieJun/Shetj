package me.shetj.fingerprinter;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import io.reactivex.subjects.PublishSubject;

import static me.shetj.fingerprinter.CodeException.SYSTEM_API_ERROR;


public class RxFingerPrinter  {
    public static final int FINGER_CLICK_CANCEL = 4;
    public static final int FINGER_CANCEL = 3;
    public static final int FINGER_FAIL = 0;
    public static final int FINGER_SUCCEED = 1;
    public static final int FINGER_FAILED_ERROR = 5;
    public static final int PERMISSION_DENIED_ERROE = 6;

    private BiometricPrompt mBiometricPrompt;
    private CancellationSignal mCancellationSignal;
    private BiometricPrompt.AuthenticationCallback mAuthenticationCallback;
    private Activity context;
    private PublishSubject<Integer> publishSubject;
    public RxFingerPrinter(@NonNull Activity activity) {
        this.context = activity;
        publishSubject = PublishSubject.create();
    }

    public PublishSubject<Integer> init() {
        if (Build.VERSION.SDK_INT < 23) {
            publishSubject.onError(new FPerException(SYSTEM_API_ERROR));
        }
        initBiometric();
        return publishSubject;
    }

    private void initBiometric() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_BIOMETRIC)
                != PackageManager.PERMISSION_GRANTED) {
            publishSubject.onNext(PERMISSION_DENIED_ERROE);
            return;
        }
        mBiometricPrompt = new BiometricPrompt.Builder(context)
                .setTitle("指纹验证")
                .setDescription("描述")
                .setNegativeButton("取消", context.getMainExecutor(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        publishSubject.onNext(FINGER_CLICK_CANCEL);
                    }
                })
                .build();

        mCancellationSignal = new CancellationSignal();
        mCancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() {
            @Override
            public void onCancel() {
                publishSubject.onNext(FINGER_CANCEL);
            }
        });

        mAuthenticationCallback = new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                publishSubject.onNext(FINGER_FAILED_ERROR);
            }

            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                publishSubject.onNext(FINGER_SUCCEED);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                publishSubject.onNext(FINGER_FAIL);
            }
        };

    }

    public void start(){
        mBiometricPrompt.authenticate(mCancellationSignal, context.getMainExecutor(), mAuthenticationCallback);
    }


}

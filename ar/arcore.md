# ARCore

## 权限问题

```text
<uses-permission android:name="android.permission.CAMERA" />
<uses-feature android:name="android.hardware.camera" />
```

## 检查是否可以使用AR

```text
void maybeEnableArButton() {
  ArCoreApk.Availability availability = ArCoreApk.getInstance().checkAvailability(this);
  if (availability.isTransient()) {
    // Re-query at 5Hz while compatibility is checked in the background.
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        maybeEnableArButton();
      }
    }, 200);
  }
  if (availability.isSupported()) {
    mArButton.setVisibility(View.VISIBLE);
    mArButton.setEnabled(true);
    // indicator on the button.
  } else { // Unsupported or unknown.
    mArButton.setVisibility(View.INVISIBLE);
    mArButton.setEnabled(false);
  }
}
```


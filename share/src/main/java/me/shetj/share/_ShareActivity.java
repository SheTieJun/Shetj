package me.shetj.share;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;

/**
 * Created by shaohui on 2016/11/19.
 */

public class _ShareActivity extends Activity {

    private int mType;

    private boolean isNew;

    private static final String TYPE = "share_activity_type";

    public static Intent newInstance(Context context, int type) {
        Intent intent = new Intent(context, _ShareActivity.class);
        if (context instanceof Application) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.putExtra(TYPE, type);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShareLogger.i(ShareLogger.INFO.ACTIVITY_CREATE);
        isNew = true;
        // init data
        mType = getIntent().getIntExtra(TYPE, 0);
        if (mType == ShareUtil.TYPE) {
            // 分享
            ShareUtil.action(this);
        } else {
            ShareUtil.handleResult(getIntent());
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ShareLogger.i(ShareLogger.INFO.ACTIVITY_RESUME);
        if (isNew) {
            isNew = false;
        } else {
            finish();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ShareLogger.i(ShareLogger.INFO.ACTIVITY_NEW_INTENT);
       if (mType == ShareUtil.TYPE) {
            ShareUtil.handleResult(intent);
        }
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ShareLogger.i(ShareLogger.INFO.ACTIVITY_RESULT);
        if (mType == ShareUtil.TYPE) {
            ShareUtil.handleResult(data);
        }
        finish();
    }


}

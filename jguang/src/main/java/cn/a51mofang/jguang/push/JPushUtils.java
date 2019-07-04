package cn.a51mofang.jguang.push;

import android.app.Application;
import android.content.Context;
import androidx.annotation.NonNull;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

import cn.a51mofang.jguang.R;
import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

import static cn.a51mofang.jguang.push.TagAliasOperatorHelper.ACTION_ADD;
import static cn.a51mofang.jguang.push.TagAliasOperatorHelper.ACTION_CLEAN;
import static cn.a51mofang.jguang.push.TagAliasOperatorHelper.ACTION_DELETE;
import static cn.a51mofang.jguang.push.TagAliasOperatorHelper.ACTION_SET;
import static cn.a51mofang.jguang.push.TagAliasOperatorHelper.TagAliasBean;
import static cn.a51mofang.jguang.push.TagAliasOperatorHelper.sequence;
/**
 * Created by admin on 2017/8/16.
 */

public class JPushUtils {


    public static void init(Application app,boolean debug){
        JPushInterface.setDebugMode(debug);
        JPushInterface.init(app);
        JPushInterface.setLatestNotificationNumber(app, 5);
        //设置接收到通知时，默认notifycation样
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(app);
        builder.statusBarDrawable = R.drawable.icon_jiguang;
        JPushInterface.setDefaultPushNotificationBuilder(builder);
    }

    public static void setAlias(@NonNull Context activity, @NonNull String alias){
        TagAliasBean tagAliasBean = new TagAliasBean();
        tagAliasBean.isAliasAction = true;
        tagAliasBean.action = ACTION_SET;
        tagAliasBean.alias = alias;
        sequence++;
        handleAction(activity,sequence,tagAliasBean);
    }

    public static void delAlias(@NonNull Context activity){
        TagAliasBean tagAliasBean = new TagAliasBean();
        tagAliasBean.isAliasAction = true;
        tagAliasBean.action = ACTION_DELETE;
        sequence++;
        handleAction(activity,sequence,tagAliasBean);
    }

    public static void setTag(@NonNull Context  activity,@NonNull String  tag){
        Set<String > tags = new HashSet<>();
        tags.add(tag);
        setTag(activity,tags);
    }

    public static void addTag(@NonNull Context  activity,@NonNull String  tag){
        Set<String > tags = new HashSet<>();
        tags.add(tag);
        addTag(activity,tags);
    }


    public static void delTag(@NonNull Context  activity,@NonNull String  tag){
        Set<String > tags = new HashSet<>();
        tags.add(tag);
        deleteTag(activity,tags);
    }



    public static void addTag(@NonNull Context  activity,@NonNull Set<String> tags){
        TagAliasBean tagAliasBean = new TagAliasBean();
        tagAliasBean.isAliasAction = false;
        tagAliasBean.action = ACTION_ADD;
        tagAliasBean.tags = tags;
        sequence++;
        handleAction(activity,sequence,tagAliasBean);
    }

    public static void setTag(@NonNull Context  activity,@NonNull Set<String >  tags){
        TagAliasBean tagAliasBean = new TagAliasBean();
        tagAliasBean.isAliasAction = false;
        tagAliasBean.action = ACTION_SET;
        tagAliasBean.tags = tags;
        sequence++;
        handleAction(activity,sequence,tagAliasBean);
    }

    public static void deleteTag(@NonNull Context  activity,@NonNull Set<String > tags){
        TagAliasBean tagAliasBean = new TagAliasBean();
        tagAliasBean.isAliasAction = false;
        tagAliasBean.action = ACTION_DELETE;
        tagAliasBean.tags = tags;
        sequence++;
        handleAction(activity,sequence,tagAliasBean);
    }

    public static  void clearAll(@NonNull Context  activity){
        Log.i("JIGUANG-TagAliasHelper","start clean");
        delAlias(activity);
        clearTag(activity);
    }

    private static void clearTag(@NonNull Context activity) {
        TagAliasBean tagAliasBean = new TagAliasBean();
        tagAliasBean.isAliasAction = false;
        tagAliasBean.action = ACTION_CLEAN;
        sequence++;
        handleAction(activity,sequence,tagAliasBean);
    }

    public static void handleAction(Context  activity,int sequence,TagAliasBean tagAliasBean){
        TagAliasOperatorHelper.getInstance().handleAction(activity.getApplicationContext(),sequence,tagAliasBean);

    }


}

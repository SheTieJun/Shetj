import android.app.Activity
import android.app.Application
import android.content.Context
import android.net.ParseException
import android.os.Build
import android.os.Bundle
import android.support.multidex.MultiDex
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.View
import android.widget.TextView

import com.google.gson.JsonParseException
import com.jess.arms.base.delegate.AppLifecycles
import com.jess.arms.di.module.GlobalConfigModule
import com.jess.arms.http.GlobalHttpHandler
import com.jess.arms.http.RequestInterceptor
import com.jess.arms.integration.ConfigModule
import com.jess.arms.utils.ArmsUtils
import com.shetj.arms.BuildConfig
import com.shetj.arms.R
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.io.File
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

import butterknife.ButterKnife
import cn.a51mofang.mvp.model.api.Api
import me.jessyan.progressmanager.ProgressManager
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.HttpException
import timber.log.Timber

/**
 * app 的全局配置信息在此配置,需要将此实现类声明到 AndroidManifest 中
 * Created by jess on 12/04/2017 17:25
 * Contact with jess.yan.effort@gmail.com
 */
class GlobalConfiguration : ConfigModule {

    override fun applyOptions(context: Context, builder: GlobalConfigModule.Builder) {
        if (!BuildConfig.LOG_DEBUG)
        //Release 时,让框架不再打印 Http 请求和响应的信息
            builder.printHttpLogLevel(RequestInterceptor.Level.NONE)

        builder.baseurl("http://me.shetj.cn")
                .cacheFile(File("cache"))
                //如果 BaseUrl 在 App 启动时不能确定,需要请求服务器接口动态获取,请使用以下代码
                //并且使用 Okhttp (AppComponent中提供) 请求服务器获取到正确的 BaseUrl 后赋值给 GlobalConfiguration.sDomain
                //切记整个过程必须在第一次调用 Retrofit 接口之前完成,如果已经调用过 Retrofit 接口,将不能动态切换 BaseUrl
                //                .baseurl(new BaseUrl() {
                //                    @Override
                //                    public HttpUrl url() {
                //                        return HttpUrl.parse(sDomain);
                //                    }
                //                })
                .globalHttpHandler(object : GlobalHttpHandler {
                    // 这里可以提供一个全局处理Http请求和响应结果的处理类,
                    // 这里可以比客户端提前一步拿到服务器返回的结果,可以做一些操作,比如token超时,重新获取
                    override fun onHttpResultResponse(httpResult: String, chain: Interceptor.Chain, response: Response): Response {
                        /* 这里可以先客户端一步拿到每一次http请求的结果,可以解析成json,做一些操作,如检测到token过期后
                           重新请求token,并重新执行请求 */
                        try {
                            if (!TextUtils.isEmpty(httpResult) && RequestInterceptor.isJson(response.body()!!.contentType()!!)) {
                                val array = JSONArray(httpResult)
                                val `object` = array.get(0) as JSONObject
                                val login = `object`.getString("login")
                                val avatar_url = `object`.getString("avatar_url")
                                Timber.w("Result ------> $login    ||   Avatar_url------> $avatar_url")
                            }

                        } catch (e: JSONException) {
                            e.printStackTrace()
                            return response
                        }

                        /* 这里如果发现token过期,可以先请求最新的token,然后在拿新的token放入request里去重新请求
                        注意在这个回调之前已经调用过proceed,所以这里必须自己去建立网络请求,如使用okhttp使用新的request去请求
                        create a new request and modify it accordingly using the new token
                        Request newRequest = chain.request().newBuilder().header("token", newToken)
                                             .build();

                        retry the request

                        response.body().close();
                        如果使用okhttp将新的请求,请求成功后,将返回的response  return出去即可
                        如果不需要返回新的结果,则直接把response参数返回出去 */

                        return response
                    }

                    // 这里可以在请求服务器之前可以拿到request,做一些操作比如给request统一添加token或者header以及参数加密等操作
                    override fun onHttpRequestBefore(chain: Interceptor.Chain, request: Request): Request {
                        /* 如果需要再请求服务器之前做一些操作,则重新返回一个做过操作的的requeat如增加header,不做操作则直接返回request参数
                           return chain.request().newBuilder().header("token", tokenId)
                                  .build(); */
                        return request
                    }
                })
                .responseErrorListener { context1, t ->
                    /* 用来提供处理所有错误的监听
                       rxjava必要要使用ErrorHandleSubscriber(默认实现Subscriber的onError方法),此监听才生效 */
                    Timber.tag("Catch-Error").w(t.message)
                    //这里不光是只能打印错误,还可以根据不同的错误作出不同的逻辑处理
                    var msg = "未知错误"
                    if (t is UnknownHostException) {
                        msg = "网络不可用"
                    } else if (t is SocketTimeoutException) {
                        msg = "请求网络超时"
                    } else if (t is HttpException) {
                        msg = convertStatusCode(t)
                    } else if (t is JsonParseException || t is ParseException || t is JSONException) {
                        msg = "数据解析错误"
                    }
                    ArmsUtils.snackbarText(msg)
                }
                .gsonConfiguration {//这里可以自己自定义配置Gson的参数
                    _, gsonBuilder ->
                    gsonBuilder
                            .serializeNulls()//支持序列化null的参数
                            .enableComplexMapKeySerialization()//支持将序列化key为object的map,默认只能序列化key为string的map
                }
                .retrofitConfiguration {//这里可以自己自定义配置Retrofit的参数,甚至你可以替换系统配置好的okhttp对象
                    _, retrofitBuilder ->
                    // retrofitBuilder.addConverterFactory(FastJsonConverterFactory.create());//比如使用fastjson替代gson
                }
                .okhttpConfiguration {//这里可以自己自定义配置Okhttp的参数
                    context1, okhttpBuilder ->
                    okhttpBuilder.writeTimeout(10, TimeUnit.SECONDS)
                    //使用一行代码监听 Retrofit／Okhttp 上传下载进度监听,以及 Glide 加载进度监听. 详细使用请方法查看 https://github.com/JessYanCoding/ProgressManager
                    //                    ProgressManager.getInstance().with(okhttpBuilder);
                    //让 Retrofit 同时支持多个 BaseUrl 以及动态改变 BaseUrl. 详细使用请方法查看 https://github.com/JessYanCoding/RetrofitUrlManager
                    //                    RetrofitUrlManager.getInstance().with(okhttpBuilder);

                }.rxCacheConfiguration {//这里可以自己自定义配置RxCache的参数
                    context1, rxCacheBuilder ->

                    rxCacheBuilder.useExpiredDataIfLoaderNotAvailable(true)
                }
    }

    override fun injectAppLifecycle(context: Context, lifecycles: MutableList<AppLifecycles>) {
        // AppLifecycles 的所有方法都会在基类Application对应的生命周期中被调用,所以在对应的方法中可以扩展一些自己需要的逻辑
        lifecycles.add(object : AppLifecycles {

            override fun attachBaseContext(base: Context) {
                MultiDex.install(base)  //这里比 onCreate 先执行,常用于 MultiDex 初始化,插件化框架的初始化
            }

            override fun onCreate(application: Application) {
                if (BuildConfig.LOG_DEBUG) {//Timber初始化
                    //Timber 是一个日志框架容器,外部使用统一的Api,内部可以动态的切换成任何日志框架(打印策略)进行日志打印
                    //并且支持添加多个日志框架(打印策略),做到外部调用一次 Api,内部却可以做到同时使用多个策略
                    //比如添加三个策略,一个打印日志,一个将日志保存本地,一个将日志上传服务器
                    Timber.plant(Timber.DebugTree())
                    //                     如果你想将框架切换为 Logger 来打印日志,请使用下面的代码,如想切换为其他日志框架请根据下面的方式扩展
                    //                    Logger.addLogAdapter(new AndroidLogAdapter());
                    //                    Timber.plant(new Timber.DebugTree() {
                    //                        @Override
                    //                        protected void log(int priority, String tag, String message, Throwable t) {
                    //                            Logger.log(priority, tag, message, t);
                    //                        }
                    //                    });
                    ButterKnife.setDebug(true)
                }
                //leakCanary内存泄露检查
                ArmsUtils.obtainAppComponentFromContext(application).extras().put(RefWatcher::class.java.name,
                        if (BuildConfig.USE_CANARY) LeakCanary.install(application) else RefWatcher.DISABLED)
            }

            override fun onTerminate(application: Application) {

            }
        })
    }

    override fun injectActivityLifecycle(context: Context, lifecycles: MutableList<Application.ActivityLifecycleCallbacks>) {
        lifecycles.add(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle) {
                Timber.w(activity.toString() + " - onActivityCreated")
            }

            override fun onActivityStarted(activity: Activity) {
                Timber.w(activity.toString() + " - onActivityStarted")
                if (!activity.intent.getBooleanExtra("isInitToolbar", false)) {
                    //由于加强框架的兼容性,故将 setContentView 放到 onActivityCreated 之后,onActivityStarted 之前执行
                    //而 findViewById 必须在 Activity setContentView() 后才有效,所以将以下代码从之前的 onActivityCreated 中移动到 onActivityStarted 中执行
                    activity.intent.putExtra("isInitToolbar", true)
                    //这里全局给Activity设置toolbar和title,你想象力有多丰富,这里就有多强大,以前放到BaseActivity的操作都可以放到这里
                    if (activity.findViewById<View>(R.id.toolbar) != null) {
                        if (activity is AppCompatActivity) {
                            activity.setSupportActionBar(activity.findViewById<View>(R.id.toolbar) as Toolbar)
                            activity.supportActionBar!!.setDisplayShowTitleEnabled(false)
                        } else {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                activity.setActionBar(activity.findViewById<View>(R.id.toolbar) as android.widget.Toolbar)
                                activity.actionBar!!.setDisplayShowTitleEnabled(false)
                            }
                        }
                    }
                    //                    if (activity.findViewById(R.id.toolbar_title) != null) {
                    //                        ((TextView) activity.findViewById(R.id.toolbar_title)).setText(activity.getTitle());
                    //                    }
                    //                    if (activity.findViewById(R.id.toolbar_back) != null) {
                    //                        activity.findViewById(R.id.toolbar_back).setOnClickListener(v -> {
                    //                            activity.onBackPressed();
                    //                        });
                    //                    }
                }
            }

            override fun onActivityResumed(activity: Activity) {
                Timber.w(activity.toString() + " - onActivityResumed")
            }

            override fun onActivityPaused(activity: Activity) {
                Timber.w(activity.toString() + " - onActivityPaused")
            }

            override fun onActivityStopped(activity: Activity) {
                Timber.w(activity.toString() + " - onActivityStopped")
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                Timber.w(activity.toString() + " - onActivitySaveInstanceState")
            }

            override fun onActivityDestroyed(activity: Activity) {
                Timber.w(activity.toString() + " - onActivityDestroyed")
            }
        })
    }

    override fun injectFragmentLifecycle(context: Context, lifecycles: MutableList<FragmentManager.FragmentLifecycleCallbacks>) {
        lifecycles.add(object : FragmentManager.FragmentLifecycleCallbacks() {

            override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle) {
                // 在配置变化的时候将这个 Fragment 保存下来,在 Activity 由于配置变化重建是重复利用已经创建的Fragment。
                // https://developer.android.com/reference/android/app/Fragment.html?hl=zh-cn#setRetainInstance(boolean)
                // 如果在 XML 中使用 <Fragment/> 标签,的方式创建 Fragment 请务必在标签中加上 android:id 或者 android:tag 属性,否则 setRetainInstance(true) 无效
                // 在 Activity 中绑定少量的 Fragment 建议这样做,如果需要绑定较多的 Fragment 不建议设置此参数,如 ViewPager 需要展示较多 Fragment
                f.retainInstance = true
            }

            override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
                //这里应该是检测 Fragment 而不是 FragmentLifecycleCallbacks 的泄露。
                (ArmsUtils
                        .obtainAppComponentFromContext(f.activity)
                        .extras()
                        .get(RefWatcher::class.java.name) as RefWatcher)
                        .watch(f)
            }
        })
    }


    private fun convertStatusCode(httpException: HttpException): String {
        return  when {
            httpException.code() == 500 -> "服务器发生错误"
            httpException.code() == 404 -> "请求地址不存在"
            httpException.code() == 403 -> "请求被服务器拒绝"
            httpException.code() == 307 -> "请求被重定向到其他页面"
            else -> httpException.message()
        }
    }

}

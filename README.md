# DebugTools
DebugTools是一个设计开发者支撑工具库

- 查看崩溃日志
- 接口抓包工具
- 打开/关闭FPS
- 环境切换
- 项目地址：[https://github.com/Peakmain/DebugTools](https://github.com/Peakmain/DebugTools)
- 打开DebugToolDialogFragment
```kotlin
findViewById<TextView>(R.id.tv_name).setOnClickListener {
        //方法一
        var clazz: Class<*>? = null
        try {
            clazz = Class.forName("com.peakmain.debug.DebugToolDialogFragment")
            val target: DebugToolDialogFragment =
                clazz.getConstructor().newInstance() as DebugToolDialogFragment
            target.show(activity.getSupportFragmentManager(), "debug_tool")
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
        //方法二：使用DebugToolsManager,只支持FragmentActivity
	DebugToolsManager.instance.show(this)
}
```
#### How To
- Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
- Step 2. Add the dependency
```gradle
	dependencies {
	       implementation "com.github.Peakmain:DebugTools:+"
	}
```
#### 日志捕获分享框架
![日志捕获分享框架](https://user-images.githubusercontent.com/26482737/173175618-37bd970d-4c6d-42de-a304-eb69aee3719d.gif)
- App中初始化
```kotlin
 CrashUtils.init(this)
```
- 如果对native异常进行捕获，还需要拷贝[libbreakpad.aar](https://github.com/Peakmain/DebugTools/tree/master/debug/libs)到libs目录下
#### 网络抓包工具
![网络抓包工具](https://github.com/Peakmain/DebugTools/assets/26482737/82bf1c6b-a3ce-47bd-b0be-69bee77f755c)

- 网络请求添加拦截器
```kotlin
OkHttpClient.Builder builder = new OkHttpClient.Builder();
builder.addInterceptor(new com.peakmain.debug.log.HttpLoggingInterceptor());
```
##### 一、DebugTools功能
1. 支持查看最新接口前100条数据
2. 支持正序和倒序排序
3. 可查看每个接口的Header，请求参数与返回结果
4. 支持分享给开发

##### 二、所有Activity显示悬浮按钮点击显示网络抓包工具
- 可以使用我的另一个第三方库:https://github.com/Peakmain/BasicUI 的SuspensionView
- demo代码如下，在自己的基本Activity中调用下方代码即可
```kotlin
fun addSuspensionView(activity: AppCompatActivity) {
    val suspensionView = SuspensionView(
        activity, com.atour.atourlife.R.drawable.ui_ic_suspension_setting,
        56f, 60f, 20f, null, 0
    )
    activity.addContentView(
        suspensionView,
        FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
    )
    suspensionView.setSuspensionViewClick {
        //方法一
        var clazz: Class<*>? = null
        try {
            clazz = Class.forName("com.peakmain.debug.DebugToolDialogFragment")
            val target: DebugToolDialogFragment =
                clazz.getConstructor().newInstance() as DebugToolDialogFragment
            target.show(activity.getSupportFragmentManager(), "debug_tool")
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
        //方法二：使用DebugToolsManager,只支持FragmentActivity
	DebugToolsManager.instance.show(this)
    }
}
```
##### 三、 Jenkins智能控制开关
1. Android在项目的build.gradle(一般都是app/build.gradle),利用project.property获取属性，比如我这里属性名是IS_LOG_CONSONLE_ENABLE
```gradle
def releaseLogConsoleEnable = project.property('IS_LOG_CONSONLE_ENABLE')
```

2. buildTypes中通过buildConfigField方法，将属性添加BuildConfig
```gradle
buildTypes {
    release {
        buildConfigField "boolean", "releaseLogConsoleEnable", releaseLogConsoleEnable
    }
    debug {
        buildConfigField "boolean", "releaseLogConsoleEnable", releaseLogConsoleEnable

    }
}
```
3. 显示开关按钮的地方，添加代码开关
```kotlin
if(BuildConfig.releaseLogConsoleEnable) {
    addSuspensionView(this);
}
```
至此Android相关代码配置完成，接下来是Jenkins

4. Jenkins添加选项设置属性为IS_LOG_CONSONLE_ENABLE
<img width="1000" alt="image" src="https://github.com/Peakmain/DebugTools/assets/26482737/7980a8fd-f354-4dbd-8bf9-bb00908ce916">

5. Jenkins gradle配置代码-PIS_LOG_CONSONLE_ENABLE=$IS_LOG_CONSONLE_ENABLE
```gradle
./gradlew -Dgradle.user.home=$GRADLE_HOME clean assemble$buildType -b ${WORKSPACE}/app/build.gradle -PIS_LOG_CONSONLE_ENABLE=$IS_LOG_CONSONLE_ENABLE
```


#### fps监控
![fps](https://user-images.githubusercontent.com/26482737/173175633-403a0f86-f914-40ac-a74d-359c0808361f.gif)
- App的AndroidManifest.xml需要添加悬浮窗权限
```kotlin
 <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
```
- 开启悬浮窗权限即可

#### 环境切换
![一键网络切换](https://github.com/Peakmain/DebugTools/assets/26482737/27b1d507-ca09-4550-8880-88d26e728841)
##### 一、initEnvironmentExchangeBeanList:初始化http环境列表
```kotlin
fun initEnvironmentExchangeBeanList(
    environmentExchangeBeans: MutableList<EnvironmentExchangeBean>,
    selectEnvironmentCallback: ((EnvironmentExchangeBean) -> Unit)? = null,
)
```
- 第一个参数environmentExchangeBeans表示http环境列表
- 第二个参数表示选中某一个http环境的回调

##### 二、initH5EnvironmentExchangeBeanList:初始化http环境列表
```kotlin
fun initH5EnvironmentExchangeBeanList(
    environmentExchangeBeans: MutableList<EnvironmentExchangeBean>,
    selectH5EnvironmentCallback: ((EnvironmentExchangeBean) -> Unit)? = null,
)
```
- 第一个参数environmentExchangeBeans表示H5环境列表
- 第二个参数表示选中某一个H5环境的回调

##### demo如下
```kotlin
    var mEnvironmentExchangeBeans: MutableList<EnvironmentExchangeBean> = ArrayList()//初始化原生环境列表
    var mH5EnvironmentExchangeBeans: MutableList<EnvironmentExchangeBean> = ArrayList()//初始化H5环境列表
    findViewById<TextView>(R.id.tv_name).setOnClickListener {
            DebugToolsManager.instance
                .initEnvironmentExchangeBeanList(mEnvironmentExchangeBeans) {
                    ToastUtils.showLong("当前选中的环境是:${it.title},url是:${it.url}")
                }.initH5EnvironmentExchangeBeanList(mH5EnvironmentExchangeBeans){
                    LogUtils.e("当前选中的H5环境是:${it.title},url是:${it.url}")
                    ToastUtils.showLong("当前选中的H5环境是:${it.title},url是:${it.url}")
                }
                .show(this)
        }
```
- EnvironmentExchangeBean有三个参数：title(标题)、url(http或者H5链接)、isSelected(是否被选中)
```kotlin 
data class EnvironmentExchangeBean(
    val title: String,
    val url: String,
    var isSelected: Boolean = false
)
```
- 当http环境列表有多个环境的isSelected被设置为true,则只有第一个默认是被设置为true，其他则会被设置为false。H5环境列表也是同理。

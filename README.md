# DebugTools
DebugTools是一个设计开发者支撑工具库

- 查看崩溃日志
- 接口抓包工具
- 打开/关闭FPS
- 项目地址：[https://github.com/Peakmain/DebugTools](https://github.com/Peakmain/DebugTools)
- 打开DebugToolDialogFragment
```
findViewById<TextView>(R.id.tv_name).setOnClickListener {
    val clazz = Class.forName("com.peakmain.debug.DebugToolDialogFragment")
    val target = clazz.getConstructor().newInstance() as DebugToolDialogFragment
    target.show(supportFragmentManager, "debug_tool")
}
```
#### How To
- Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
- Step 2. Add the dependency
```
	dependencies {
	       implementation 'com.github.Peakmain:DebugTools:0.1.1'
	}
```
#### 日志捕获分享框架
![日志捕获分享框架.gif](https://upload-images.jianshu.io/upload_images/9387746-fa413b11266abb42.gif?imageMogr2/auto-orient/strip)
- App中初始化
```
 CrashUtils.init(this)
```
- 如果对native异常进行捕获，还需要拷贝[libbreakpad.aar](https://github.com/Peakmain/DebugTools/tree/master/debug/libs)到libs目录下
#### 网络抓包工具
![网络抓包工具.gif](https://upload-images.jianshu.io/upload_images/9387746-567b51f3eded1986.gif?imageMogr2/auto-orient/strip)
- 网络请求添加拦截器
```
OkHttpClient.Builder builder = new OkHttpClient.Builder();
builder.addInterceptor(new com.peakmain.debug.log.HttpLoggingInterceptor());
```
- 目前只支持自身App的网络请求拦截
- 支持接口数量最大100，当超过100，会将最旧的一条删除，添加新的接口

#### fps监控
![fps.gif](https://upload-images.jianshu.io/upload_images/9387746-a79d70830d353075.gif?imageMogr2/auto-orient/strip)
- App的AndroidManifest.xml需要添加悬浮窗权限
```
 <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
```
- 开启悬浮窗权限即可

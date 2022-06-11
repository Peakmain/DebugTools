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
	       implementation ("com.github.Peakmain:DebugTools:0.1.1"){
                         exclude group:"com.github.yalantis",module:"ucrop"
               }
	}
```
#### 日志捕获分享框架
![日志捕获分享框架](https://user-images.githubusercontent.com/26482737/173175618-37bd970d-4c6d-42de-a304-eb69aee3719d.gif)
- App中初始化
```
 CrashUtils.init(this)
```
- 如果对native异常进行捕获，还需要拷贝[libbreakpad.aar](https://github.com/Peakmain/DebugTools/tree/master/debug/libs)到libs目录下
#### 网络抓包工具
![网络抓包工具库](https://user-images.githubusercontent.com/26482737/173175628-e62f3c68-6b72-4f25-ab98-4f08b39c3259.gif)
- 网络请求添加拦截器
```
OkHttpClient.Builder builder = new OkHttpClient.Builder();
builder.addInterceptor(new com.peakmain.debug.log.HttpLoggingInterceptor());
```
- 目前只支持自身App的网络请求拦截
- 支持接口数量最大100，当超过100，会将最旧的一条删除，添加新的接口

#### fps监控
![fps](https://user-images.githubusercontent.com/26482737/173175633-403a0f86-f914-40ac-a74d-359c0808361f.gif)
- App的AndroidManifest.xml需要添加悬浮窗权限
```
 <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
```
- 开启悬浮窗权限即可

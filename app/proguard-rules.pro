# Add project specific ProGuard rules here.
#-------------------------------------------定制化区域----------------------------------------------
#---------------------------------1.实体类---------------------------------

-keep class com.seabig.moduledemo.widget.** { *; }

#-------------------------------------------------------------------------

#---------------------------------2.第三方包-------------------------------
#OkGo
-dontwarn com.lzy.okgo.**
-keep class com.lzy.okgo.**{*;}

#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}

#okio
-dontwarn okio.**
-keep class okio.**{*;}

#EventBus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

#glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#umeng
#-dontoptimize
#-dontwarn com.google.android.maps.**
#-dontwarn android.webkit.WebView
#-dontwarn com.umeng.**
#-dontwarn com.tencent.weibo.sdk.**
#-dontwarn com.facebook.**
#-keep public class javax.**
#-keep public class android.webkit.**

#JPush
-dontoptimize
-dontpreverify

-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-keep class * extends cn.jpush.android.helpers.JPushMessageReceiver { *; }

-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }

#alipay
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-keep class com.alipay.sdk.app.H5PayCallback {
    <fields>;
    <methods>;
}
-keep class com.alipay.android.phone.mrpc.core.** { *; }
-keep class com.alipay.apmobilesecuritysdk.** { *; }
-keep class com.alipay.mobile.framework.service.annotation.** { *; }
-keep class com.alipay.mobilesecuritysdk.face.** { *; }
-keep class com.alipay.tscenter.biz.rpc.** { *; }
-keep class org.json.alipay.** { *; }
-keep class com.alipay.tscenter.** { *; }
-keep class com.ta.utdid2.** { *;}
-keep class com.ut.device.** { *;}
#------------------------------------------------------------
#Gson
-keepattributes Signature

# For using GSON @Expose annotation
#-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# 如果使用了Gson之类的工具要使被它解析的JavaBean类即实体类不被混淆。
-keep class com.matrix.app.entity.json.** { *; }
-keep class com.matrix.appsdk.network.model.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }
-keepclassmembers class * implements java.io.Serializable {
static final long serialVersionUID;
private static final java.io.ObjectStreamField[] serialPersistentFields;
private void writeObject(java.io.ObjectOutputStream);
 private void readObject(java.io.ObjectInputStream);
 java.lang.Object writeReplace();
java.lang.Object readResolve();
}
-keep public class * implements java.io.Serializable {*;}

#FastJson
#-keepattributes Signature
#-dontwarn com.alibaba.fastjson.**
#-keep class com.alibaba.fastjson.**{*; }
#------------------------------------------------------------

#微信
-keep class com.tencent.mm.opensdk.** {
   *;
}
-keep class com.tencent.mm.sdk.** {
   *;
}

-dontwarn org.xmlpull.v1.XmlPullParser

-dontwarn android.net.**
-keep class android.net.SSLCertificateSocketFactory{*;}

#阿里云
#-keepclasseswithmembernames class ** {
#    native <methods>;
#}
#-keepattributes Signature
#-keep class sun.misc.Unsafe { *; }
#-keep class com.taobao.** {*;}
#-keep class com.alibaba.** {*;}
#-keep class com.alipay.** {*;}
#-dontwarn com.taobao.**
#-dontwarn com.alibaba.**
#-dontwarn com.alipay.**
#-keep class com.ut.** {*;}
#-dontwarn com.ut.**
#-keep class com.ta.** {*;}
#-dontwarn com.ta.**
#-keep class anet.**{*;}
#-keep class org.android.spdy.**{*;}
#-keep class org.android.agoo.**{*;}
#-dontwarn anet.**
#-dontwarn org.android.spdy.**
#-dontwarn org.android.agoo.**
#
#-keep class org.xmlpull.v1.** { *;}
#-dontwarn org.xmlpull.v1.**o

# 小米通道
#-keep class com.xiaomi.** {*;}
#-dontwarn com.xiaomi.**
# 华为通道
#-keep class com.huawei.** {*;}
#-dontwarn com.huawei.**
#-------------------------------------------------------------------------
# 高德地图
#3D 地图 V5.0.0之前：
#-dontwarn com.amap.api.**
#-keep   class com.amap.api.maps.**{*;}
#-keep   class com.autonavi.amap.mapcore.*{*;}
#-keep   class com.amap.api.trace.**{*;}

#3D 地图 V5.0.0之后：
#-keep   class com.amap.api.maps.**{*;}
#-keep   class com.autonavi.**{*;}
#-keep   class com.amap.api.trace.**{*;}

#定位
#-keep class com.amap.api.location.**{*;}
#-keep class com.amap.api.fence.**{*;}
#-keep class com.autonavi.aps.amapapi.model.**{*;}

#搜索
#-keep class com.amap.api.services.**{*;}

#2D地图
#-keep class com.amap.api.maps2d.**{*;}
#-keep class com.amap.api.mapcore2d.**{*;}

#导航
#-keep class com.amap.api.navi.**{*;}
#-keep class com.autonavi.**{*;}

#讯飞语音
#-dontwarn com.iflytek.**
#-keepattributes Signature
#-keep class com.iflytek.**{*;}

#bugly
#-dontwarn com.tencent.bugly.**
#-keep public class com.tencent.bugly.**{*;}
#-------------------------------------------------------------------------

#---------------------------------3.与js互相调用的类------------------------



#-------------------------------------------------------------------------

#---------------------------------4.反射相关的类和方法-----------------------



#----------------------------------------------------------------------------
#---------------------------------------------------------------------------------------------------

#-------------------------------------------基本不用动区域--------------------------------------------
#---------------------------------基本指令区----------------------------------
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-ignorewarnings
-printmapping proguardMapping.txt
-optimizations !code/simplification/cast,!field/*,!class/merging/*
-keepattributes *Annotation*,InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
#----------------------------------------------------------------------------

#---------------------------------默认保留区---------------------------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class **.R$* {
 *;
}
-keepclassmembers class * {
    void *(**On*Event);
}
#----------------------------------------------------------------------------

#---------------------------------webview------------------------------------
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}
#----------------------------------------------------------------------------
#---------------------------------------------------------------------------------------------------


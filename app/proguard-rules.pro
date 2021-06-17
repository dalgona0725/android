# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\android-sdk-windows/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-keep class com.theenm.common.http.schemas.* { *; }
-keep class com.theenm.common.node.schemas.* { *; }

-dontwarn okio.**
-dontwarn com.m2go.**
-dontwarn com.igaworks.**
-ignorewarnings

##---------------Begin: proguard configuration for Igaworks Common  ----------
-keep class com.igaworks.* { *; }
-dontwarn com.igaworks.**
##---------------End: proguard configuration for Igaworks Common   ----------

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
#-keep class sun.misc.Unsafe { *; }
-keep class com.igaworks.gson.stream.* { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.igaworks.adbrix.model.* { *; }

##---------------End: proguard configuration for Gson  ----------

# ---
-keep class io.realm.annotations.RealmModule
-keep @io.realm.annotations.RealmModule class *
-keep class io.realm.internal.Keep
-keep @io.realm.internal.Keep class *
-dontwarn javax.**
-dontwarn io.realm.**
# ---
# --- facebook
-keepclassmembers class * implements java.io.Serializable {
     private static final java.io.ObjectStreamField[] serialPersistentFields;
     private void writeObject(java.io.ObjectOutputStream);
     private void readObject(java.io.ObjectInputStream);
     java.lang.Object writeReplace();
     java.lang.Object readResolve();
 }

-keepnames class com.facebook.FacebookActivity
-keepnames class com.facebook.CustomTabActivity

-dontoptimize
-dontwarn org.**
-dontwarn retrofit2.**

-keep class org.* { *;}
-keep interface org.* { *;}

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-keep class retrofit2.* { *; }

# ignore netty lib warning
-dontwarn io.netty.**

# netty 4.0
-keep class io.netty.* {
    *;
}
-keep interface io.netty.* {
    *;
}

# Slf4j for android
-keep class org.slf4j.* {
    *;
}
-keep interface org.slf4j.* {
    *;
}

# Jzlib
-keep class com.jcraft.jzlib.* {
    *;
}
-keep interface com.jcraft.jzlib.* {
    *;
}

-keepnames class com.fasterxml.jackson.* { *; }
-dontwarn com.fasterxml.jackson.databind.**
-keep class org.codehaus.* { *; }

-dontwarn okhttp3.**
-dontwarn okio.**
-dontnote okhttp3.**

# ---

-dontwarn com.android.installreferrer
-dontwarn com.appsflyer.**
-keep public class com.google.firebase.iid.FirebaseInstanceId {
  public *;
}

-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient {*;}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient$* {*;}
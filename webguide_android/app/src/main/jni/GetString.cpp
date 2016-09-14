#include "top_glimpse_webguide_android_activity_MainActivity.h"


JNIEXPORT jstring JNICALL Java_top_glimpse_webguide_1android_activity_MainActivity_getString(JNIEnv *env, jobject) {

    return env->NewStringUTF("sprite");

}

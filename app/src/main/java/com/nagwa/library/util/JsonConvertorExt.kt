package com.nagwa.library.util

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import timber.log.Timber

fun Map<String, Any>.mMapToJsonElement(): JsonElement = this.hashToJe()
private fun Map<String, Any>.hashToJe(): JsonElement {
  val type2 = object : TypeToken<HashMap<String, Any>>() {}.type
  val mToJsonParams: String = Gson().toJson(this, type2)
  val ret = JsonParser().parse(mToJsonParams)
  return ret
}

inline fun <reified T : Any> T.mAnyToJsonElement(): JsonElement {
  val type2 = object : TypeToken<T>() {}.type
  val mToJsonParams: String = Gson().toJson(this, type2)
  return JsonParser().parse(mToJsonParams)
}

fun String.mStringToJsonElement(): JsonElement {
  return JsonParser().parse(this)
}

@Throws(JsonSyntaxException::class)
inline fun <reified T : Any> JsonElement.mMapToArrayList(): ArrayList<T> =
  try {
    mapJson(this)
  } catch (e: JsonSyntaxException) {
    e.printStackTrace()
    ArrayList()
  }

@Throws(JsonSyntaxException::class)
inline fun <reified T : Any> JsonArray.mMapToArrayListFix(): ArrayList<T> =
  try {
    val ret = ArrayList<T>()
    this.forEach { js ->
      val obj: T? = try {
        js.mMapToObject<T>()
      } catch (e: Exception) {
        e.printStackTrace()
        null
      }
      obj?.let { ob ->
        ret.add(ob)
      }
    }
    ret
  } catch (e: JsonSyntaxException) {
    e.printStackTrace()
    ArrayList()
  }

inline fun <reified T : Any> JsonElement.mMapToObject(): T? =
  try {
    mapJson(this)
  } catch (e: JsonSyntaxException) {
    Timber.e(e)
    null
  }

inline fun <reified T : Any> JsonObject.mMapToObject(): T? =
  try {
    mapJson(this)
  } catch (e: JsonSyntaxException) {
    Timber.e(e)
    null
  }

@Throws(JsonSyntaxException::class)
inline fun <reified T : Any> mapJson(je: JsonElement): T {
  val type = object : TypeToken<T>() {}.type
  return Gson().fromJson(je, type)
}

@Throws(JsonSyntaxException::class)
inline fun <reified T : Any> mapJson(je: JsonObject): T {
  val type = object : TypeToken<T>() {}.type // type
  return Gson().fromJson(je, type)
}

fun ArrayList<*>?.validateArrayList() = this != null
fun String?.isValid() = !(this.isNullOrEmpty())
fun Any?.validateType() = this != null
fun isNotNull(A: Any?): Boolean {
  return A != null
}

val emptyJe: JsonElement = HashMap<String, String>().mMapToJsonElement()

package ren.imyan.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import kotlin.reflect.KProperty

/**
 * @author EndureBlaze/炎忍 https://github.com.EndureBlaze
 * @data 2021-02-16 12:25
 * @website https://imyan.ren
 */
class SP<T>(context: Context, private val key: String, private val defValue: Any) {

    companion object {
        fun string(context: Context, key: String, defValue: String): SP<String> =
            SP(context, key, defValue)

        fun int(context: Context, key: String, defValue: Int): SP<Int> = SP(context, key, defValue)
        fun long(context: Context, key: String, defValue: Long): SP<Long> =
            SP(context, key, defValue)

        fun float(context: Context, key: String, defValue: Float): SP<Float> =
            SP(context, key, defValue)

        fun boolean(context: Context, key: String, defValue: Boolean): SP<Boolean> =
            SP(context, key, defValue)
    }

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(
            "share_data",
            Context.MODE_PRIVATE
        )
    }

    @Suppress("IMPLICIT_CAST_TO_ANY", "UNCHECKED_CAST")
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T =
        when (defValue) {
            is String -> sharedPreferences.getString(key, defValue)!!
            is Int -> sharedPreferences.getInt(key, defValue)
            is Long -> sharedPreferences.getLong(key, defValue)
            is Float -> sharedPreferences.getFloat(key, defValue)
            is Boolean -> sharedPreferences.getBoolean(key, defValue)
            else -> sharedPreferences.getString(key, defValue.toString())!!
        } as T

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) =
        sharedPreferences.edit().apply {
            when (value) {
                is String -> putString(key, value)!!
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
                is Boolean -> putBoolean(key, value)
                else -> putString(key, value.toString())!!
            }
        }.apply()
}
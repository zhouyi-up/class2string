package com.liuujun.class2dml

import com.intellij.DynamicBundle
import org.jetbrains.annotations.Nls
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.PropertyKey
import java.util.function.Supplier

@NonNls
private const val BUNDLE = "messages.Class2dmlBundle"

internal object Class2dmlBundle {
    private val INSTANCE = DynamicBundle(Class2dmlBundle::class.java, BUNDLE)

    @JvmStatic
    fun message(
        @PropertyKey(resourceBundle = BUNDLE) key: String,
        vararg params: Any
    ): @Nls String {
            return INSTANCE.getMessage(key, *params)
    }

    fun lazyMessage (
        @PropertyKey(resourceBundle = BUNDLE) key: String,
        vararg params: Any
    ): Supplier<@Nls String> {
        return INSTANCE.getLazyMessage(key, *params)
    }
}
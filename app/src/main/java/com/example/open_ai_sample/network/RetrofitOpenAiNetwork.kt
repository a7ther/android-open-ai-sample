package com.example.open_ai_sample.network

import com.example.open_ai_sample.network.model.chatcompletions.ChatCompletions
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import javax.inject.Inject
import javax.inject.Singleton

private interface RetrofitOpenAiApi {

    //api reference : https://platform.openai.com/docs/api-reference/chat/create
    @Headers("Authorization: Bearer $OPENAI_API_KEY")
    @POST("v1/chat/completions")
    suspend fun chatCompletions(@Body request: ChatCompletions.Request): ChatCompletions.Response.Success
}

private const val OPEN_AI_API_BASE_URL = "https://api.openai.com/"

//XXX: サンプルのため、ここにおいてますが、API_KEYは外部から取得するようにしてください
private const val OPENAI_API_KEY = "{YOUR_API_KEY}"

@Singleton
class RetrofitOpenAiNetwork @Inject constructor(
    moshi: Moshi
) : OpenAiDataSource {

    private val client = OkHttpClient.Builder().apply {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        addInterceptor(interceptor)
    }.build()

    private val networkApi = Retrofit.Builder()
        .baseUrl(OPEN_AI_API_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(client)
        .build()
        .create(RetrofitOpenAiApi::class.java)

    override suspend fun chatCompletions(request: ChatCompletions.Request): ChatCompletions.Response {
        return try {
            networkApi.chatCompletions(request)
        } catch (e: Exception) {
            ChatCompletions.Response.Failure(e)
        }
    }

}
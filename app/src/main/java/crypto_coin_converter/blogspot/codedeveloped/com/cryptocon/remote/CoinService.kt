package crypto_coin_converter.blogspot.codedeveloped.com.cryptocon.remote

import crypto_coin_converter.blogspot.codedeveloped.com.cryptocon.model.Coin
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinService {
    @GET("data/price")
    fun calculateValue(@Query("fsym") from: String , @Query("tsyms") to: String): Call<Coin>
}
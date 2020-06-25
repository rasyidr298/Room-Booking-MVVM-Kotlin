package com.rrdev.roombookingmvvm.data.network
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.rrdev.roombookingmvvm.data.network.responses.*
import com.rrdev.roombookingmvvm.util.BASE
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit
interface MyApi {
    @FormUrlEncoded
    @POST ("/Backend%20Room%20Booking/TableUsers/LoginUser.php")
     suspend fun userLogin(
        @Field("nim") nim: String,
        @Field("password") password: String
    ) : Response<AuthResponse>

    @FormUrlEncoded
    @POST ("/Backend%20Room%20Booking/TableUsers/RegisterUser.php")
    suspend fun userSignUp(
        @Field("nim") nim: String,
        @Field("namaUser") namaUser: String,
        @Field("nohp") nohp: String,
        @Field("password") password: String,
        @Field("token") token: String
    ) : Response<AuthResponse>

    @FormUrlEncoded
    @POST ("/Backend%20Room%20Booking/TableBookings/Booking.php")
    suspend fun bookigRoom(
        @Field("idBooking") idBooking: String,
        @Field("nimBooking") nimBooking: String,
        @Field("namaPembooking") namaPembooking: String,
        @Field("namaRuangBooking") namaRuangBooking: String,
        @Field("tanggal") tanggal: String,
        @Field("jamMulai") jamMulai: String,
        @Field("jamSelesai") jamSelesai: String,
        @Field("keterangan") keterangan: String
    ) : Response<BookingRoomResponse>

    @GET("/Backend%20Room%20Booking/TableBookings/DeleteBooking.php")
    suspend fun cancelBooking(
        @Query("idBooking") idBooking: String?
    ): Response<CancelBookingResponse>

    @GET("/Backend%20Room%20Booking/TableRooms/GetAllRooms.php")
    suspend fun getRoom(
    ):Response<RoomResponse>

    @GET("/Backend%20Room%20Booking/TableBookings/GetBookingUser.php")
    fun getMyBooking(
        @Query("nimBooking") nimBooking: String?
    ):Deferred<MyBookingResponse>


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    companion object{
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ) : MyApi {

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl(BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(getOkHttpClient(networkConnectionInterceptor))
                .build()
                .create(MyApi::class.java)
        }

        private fun getOkHttpClient(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): OkHttpClient{
            return OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()
        }

    }
}
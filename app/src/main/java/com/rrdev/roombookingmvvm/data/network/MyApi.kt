package com.rrdev.roombookingmvvm.data.network
import com.rrdev.roombookingmvvm.data.db.entities.User
import com.rrdev.roombookingmvvm.data.network.responses.AuthResponse
import com.rrdev.roombookingmvvm.data.network.responses.BookingResponse
import com.rrdev.roombookingmvvm.data.network.responses.RoomResponse
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MyApi {

    @FormUrlEncoded
    @POST ("Backend%20Room%20Booking/TableUsers/LoginUser.php")
     suspend fun userLogin(
        @Field("nim") nim: String,
        @Field("password") password: String
    ) : Response<AuthResponse>

    @FormUrlEncoded
    @POST ("Backend%20Room%20Booking/TableUsers/RegisterUser.php")
    suspend fun userSignUp(
        @Field("nim") nim: String,
        @Field("namaUser") namaUser: String,
        @Field("nohp") nohp: String,
        @Field("password") password: String,
        @Field("token") token: String
    ) : Response<AuthResponse>

    @GET("Backend%20Room%20Booking/TableRooms/GetAllRooms.php")
    suspend fun getRoom(
    ):Response<RoomResponse>

    @GET("Backend%20Room%20Booking/TableBookings/GetBookingUser.php")
    suspend fun getBooking(
        @Query("nimBooking") nimBooking: String
    ):Response<BookingResponse>


    companion object{
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ) : MyApi {

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl("https://c001a264.ngrok.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }
}
package com.rrdev.roombookingmvvm.data.network
import com.rrdev.roombookingmvvm.data.network.responses.AuthResponse
import com.rrdev.roombookingmvvm.data.network.responses.BookingResponse
import com.rrdev.roombookingmvvm.data.network.responses.BookingRoomResponse
import com.rrdev.roombookingmvvm.data.network.responses.RoomResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

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

    @GET("/Backend%20Room%20Booking/TableRooms/GetAllRooms.php")
    suspend fun getRoom(
    ):Response<RoomResponse>

    @GET("/Backend%20Room%20Booking/TableBookings/GetBookingUser.php")
    suspend fun getBooking(
        @Query("nimBooking") nimBooking: String?
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
                .baseUrl("https://458f59302c21.ngrok.io")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }
}
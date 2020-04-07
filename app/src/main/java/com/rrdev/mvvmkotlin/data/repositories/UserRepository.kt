package com.rrdev.mvvmkotlin.data.repositories
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rrdev.mvvmkotlin.data.network.MyApi
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class UserRepository {

    fun userLogin(email: String, password: String ): LiveData<String> {
        val loginResponse = MutableLiveData<String>()

        //Dependency Inject
        MyApi().userLogin(email,password)
            .enqueue(object : retrofit2.Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    loginResponse.value = t.message
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful){
                        loginResponse.value = response.body()?.string()
                    }else{
                        loginResponse.value = response.errorBody()?.string()
                    }
                }

            } )
        return loginResponse
    }
}
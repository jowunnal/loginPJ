package com.example.example_kakaologinapi.viewModel
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.example_kakaologinapi.repository.LoginRepository
import com.example.example_kakaologinapi.database.User
import com.example.example_kakaologinapi.databinding.RegisterBinding
import com.example.example_kakaologinapi.item.RegisterInfo
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.*
import kotlin.math.log

class LoginViewModel(app: Application): ManageMemberViewModel(app) {
    fun loginUser(){
        viewModelScope.launch(Dispatchers.Main){checkUser(user,"환영합니다.","비밀번호가 틀렸습니다.","존재하지 않는 계정 입니다.")}
    }

    fun loginKakao(){
        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { _, error ->
                if (error != null) {
                    if (error is KakaoSdkError && error.isInvalidTokenError()) {
                        loginWithKakaoAccount()
                    }
                    else {
                        Log.d("test","카카오 로그인 에러")
                    }
                }
                else {
                    //토큰 유효성 체크 성공(필요 시 토큰 갱신됨)
                }
            }
        }
        else {
            loginWithKakaoAccount()
        }
    }
    fun loginWithKakaoAccount() {
        UserApiClient.instance.loginWithKakaoAccount(app.applicationContext) { token, error ->
            if (error != null) {
                Log.e("Tag", "Login 실패")
            } else if (token != null) {
                Log.e("Tag", "로그인 성공")
            }
        }
    }
}
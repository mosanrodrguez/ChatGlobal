package com.chatglobal.data.network
import retrofit2.http.*
interface ApiService {
    @POST(ApiConstants.ENDPOINT_LOGIN) suspend fun login(@Body req: LoginRequest): AuthResponse
    @POST(ApiConstants.ENDPOINT_REGISTER) suspend fun register(@Body req: RegisterRequest): AuthResponse
    @GET(ApiConstants.ENDPOINT_MESSAGES) suspend fun getMessages(@Query(ApiConstants.PARAM_LIMIT) limit: Int = ApiConstants.DEFAULT_LIMIT, @Query(ApiConstants.PARAM_CURSOR) cursor: String? = null): List<MessageResponse>
}

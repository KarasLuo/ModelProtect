package com.karasluo.libcommon.retrofit;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Hongliang Luo on 2019/8/21.
 **/
public interface DefaultPostInterface<T,H> {

    @POST("{sub_url}")
    @Headers("{headers}")
    Observable<T>request(@Path("sub_url")String subUrl,
                              @Path("headers")String headers,
                              @Body H requestBody);
    @POST("{sub_url}")
    Observable<T>request(@Path("sub_url")String subUrl,
                              @Body H requestBody);
}

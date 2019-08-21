package com.karasluo.libcommon.retrofit;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by Hongliang Luo on 2019/8/21.
 **/
public interface DefaultGetInterface<T> {


    @GET("{sub_url}")
    @Headers("{headers}")
    Observable<T> request(@Path("sub_url")String subUrl,
                          @Path("headers")String headers);
    @GET("{sub_url}")
    Observable<T>request(@Path("sub_url")String subUrl);
}

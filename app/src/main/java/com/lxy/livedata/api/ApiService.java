package com.lxy.livedata.api;

import com.lxy.livedata.SkilBean;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author a
 * @date 2018/1/5
 */

public interface ApiService {

    @GET("{type}/{count}/{page}")
    Call<SkilBean> loadSkilData(@Path("type") String type, @Path("count") int count, @Path("page") int page);

    @GET("{type}/{count}/{page}")
    Observable<SkilBean> loadData(@Path("type") String type, @Path("count") int count, @Path("page") int page);
}

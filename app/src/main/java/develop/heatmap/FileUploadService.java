package develop.heatmap;

import develop.heatmap.model.Emotion;
import develop.heatmap.model.Emotions;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface FileUploadService {

    @POST("/emotion/v1.0/recognizeinvideo?outputStyle=aggregate")
    Call<ResponseBody> videoTask (@Header("Ocp-Apim-Subscription-Key") String key, @Body RequestBody videoEmotion);
    @Multipart
    @POST("/uploadImage")
    Call<ResponseBody> uploadImage(
            @Part("name") RequestBody name,
            @Part("url") RequestBody url,
            @Part("data") RequestBody data,
            @Part MultipartBody.Part file
    );
    @Multipart
    @POST("/uploadVideo")
    Call<ResponseBody> uploadVideo(
            @Part("name") RequestBody name,
            @Part("url") RequestBody url,
            @Part("emotions") RequestBody emotions,
            @Part("userStat") RequestBody userStat,
            @Part MultipartBody.Part file
    );
    @GET
    Call<Emotion> getEmotion (@Url String url, @Header("Ocp-Apim-Subscription-Key") String key);
}

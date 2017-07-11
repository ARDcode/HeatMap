package develop.heatmap;

import java.util.ArrayList;
import java.util.List;

import develop.heatmap.model.Prototype;
import develop.heatmap.model.PrototypeUser;
import develop.heatmap.model.Prototype_coordinates;
import develop.heatmap.model.VideoEmotion;


public interface IDatabaseHandler {

    void addPrototype(Prototype prototype);


    void addPrototypeUser(PrototypeUser prototypeUser);

    void addVideoEmotion(VideoEmotion videoEmotion);

    String getPrototypeUrl(int id);

    void addPrototype(Prototype_coordinates prototype_coordinates);


    int getUrlCount(String url, String user_id);

    Prototype_coordinates getPrototype(int id);


    List<Prototype_coordinates> getAllPrototypes();

    List<Prototype_coordinates> getAllPrototypes(String id);

    List<Prototype> getPrototypes();

    List<PrototypeUser> getPrototypeUsers(int prototype_id);


    int getId(String url, String heatuserid);


    String getName(String id);

    int updateCoordinates(String url, String coordinates, String userid);

    int updateEmotions(String name, String json);

    ArrayList<String> getEmotionUrl(String videoName);

    void deleteAll();
}
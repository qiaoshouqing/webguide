package top.glimpse.webguide.data;

import top.glimpse.webguide.entity.Website;

import java.util.List;

/**
 * Created by joyce on 16-8-14.
 */
public interface WebsiteRepository {

    List<Website> getAll();
    Website getOne(int wid);

    void postOne(Website website);
    void deleteOne(int wid);
    void updateOne(Website website);


}

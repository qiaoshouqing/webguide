package top.glimpse.webguide.data;

/**
 * Created by joyce on 16-8-17.
 */
public interface OtherRepository {


    String getValue(String key);
    void setValue(String key, String value);

    void setKV(String key, String value);

}

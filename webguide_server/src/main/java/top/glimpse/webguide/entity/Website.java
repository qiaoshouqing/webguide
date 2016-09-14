package top.glimpse.webguide.entity;

/**
 * Created by joyce on 16-8-14.
 */
public class Website {

    private int wid;
    private String wname;
    private String logo;
    private String url;
    private int weight;

    public Website() {
        super();
    }

    public Website(int wid, String wname, String logo, String url, int weight) {
        this.wid = wid;
        this.wname = wname;
        this.logo = logo;
        this.url = url;
        this.weight = weight;
    }

    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

    public String getWname() {
        return wname;
    }

    public void setWname(String wname) {
        this.wname = wname;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }


    @Override
    public String toString() {
        return "[wid:\"" + this.weight + "\",wname:\"" + this.wname + "\"," +
                "logo:\""+ this.logo +"\",url:\""+ this.url +"\"," +
                "weight:\""+ this.weight +"\"]";
    }
}

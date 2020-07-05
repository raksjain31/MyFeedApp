package developer.rakshitjain.myfeedpage;

public class FeedList implements Comparable<FeedList> {

    private String id;
    private String thumbnail_image;
    private String event_name;
    private String event_date;
    private String views;
    private String likes;
    private String shares;


//    public FeedList(String Id,String ImageResource,String name,String date,String mviews,String mlikes,String share){
//        id = Id;
//        thumbnail_image= ImageResource;
//        event_name= name;
//        event_date= date;
//        views= mviews;
//        likes= mlikes;
//        shares= share;
//
//    }


    @Override
    public int compareTo(FeedList feedList) {

        return this.likes.compareTo(feedList.likes);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThumbnail_image() {
        return thumbnail_image;
    }

    public String getEvent_name() {
        return event_name;
    }

    public String getEvent_date() {
        return event_date;
    }

    public String getViews() {
        return views;
    }

    public String getLikes() {
        return likes;
    }

    public String getShares() {
        return shares;
    }

    public void setThumbnail_image(String thumbnail_image) {
        this.thumbnail_image = thumbnail_image;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public void setShares(String shares) {
        this.shares = shares;
    }
}
package myproject.kehamilanku.Kelas;

import java.io.Serializable;

public class VideoSenam implements Serializable {
    public String idVideo;
    public String judulVideo;
    public String youtubeID;


    public VideoSenam(String idVideo, String judulVideo, String youtubeID) {
        this.idVideo = idVideo;
        this.judulVideo = judulVideo;
        this.youtubeID = youtubeID;
    }

    public VideoSenam(){}

    public String getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(String idVideo) {
        this.idVideo = idVideo;
    }

    public String getJudulVideo() {
        return judulVideo;
    }

    public void setJudulVideo(String judulVideo) {
        this.judulVideo = judulVideo;
    }

    public String getYoutubeID() {
        return youtubeID;
    }

    public void setYoutubeID(String youtubeID) {
        this.youtubeID = youtubeID;
    }
}

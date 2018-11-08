package sample.lyon.tv.myapplication.tvUI;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;


import org.json.JSONObject;


public class Poster implements Parcelable {
    String TAG=Poster.class.getName();
    public String name = "華爾街之狼";
    public String englishName = "The Wolf Of Wall street";
    public String chineseName = "華爾街之狼";
    public String imageUrl = "https://2.bp.blogspot.com/-xvW1AOfVyiE/VArb7ol7dtI/AAAAAAAAAvk/ZSgCxvdLSOg/s1600/%E8%8F%AF%E7%88%BE%E8%A1%97%E4%B9%8B%E7%8B%BC_%E6%B5%B7%E5%A0%B1.jpg";

    public Poster() {
    }



    public static final Creator<Poster> CREATOR = new Creator<Poster>() {
        public Poster createFromParcel(Parcel source) {
            Poster poster = new Poster();

            poster.name = source.readString();
            poster.chineseName = source.readString();
            poster.englishName = source.readString();
            poster.imageUrl = source.readString();


            return poster;
        }

        public Poster[] newArray(int size) {
            return new Poster[size];
        }

    };




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(name);
        dest.writeString(chineseName);
        dest.writeString(englishName);
        dest.writeString(imageUrl);
    }

    public String toString(){
        return "{\"name\"=\""+name+
                "\", \"chineseName\"=\""+chineseName+
                "\", \"englishName\"=\""+englishName+
                "\", \"imageUrl\"=\""+imageUrl+
                "\"}"
                ;
    }
}

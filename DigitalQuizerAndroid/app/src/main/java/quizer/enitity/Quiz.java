package quizer.enitity;

import android.os.Parcel;
import android.os.Parcelable;

public class Quiz {

    private int id;
    private String title;
    private String description;

    public Quiz() {

    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){

        return id;
    }

    public void setTitle(String title){

        this.title = title;
    }

    public String getTitle(){

        return title;
    }

    public void setDescription(String description){

        this.description = description;
    }

    public String getDescription(){

        return description;
    }

}

package etu.toptip.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;


public class Place implements Parcelable {

    private String name;
    private int type;
    private String image;
    private String ville;
    private String codeP;
    private String adresse;
    private String id;

    public Place(String name, int type, String image, String ville, String codeP, String adresse, String id) {
        this.name = name;
        this.image = image;
        this.type = type;
        this.ville = ville;
        this.adresse = adresse;
        this.codeP = codeP;
        this.id = id;
    }

    protected Place(Parcel in) {   
        name = in.readString();
        type = in.readInt();
        if (in.readByte() == 0) {
            image = null;
        } else {
            image = in.readString();
        }
        ville = in.readString();
        codeP = in.readString();
        adresse = in.readString();
        id = in.readString();
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public String getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(type);
        if (image == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeString(image);
        }
        parcel.writeString(codeP);
        parcel.writeString(adresse);
        parcel.writeString(ville);
    }

    public String getVille() {
        return ville;
    }

    public String getCodeP() {
        return codeP;
    }

    public String getAdresse() {
        return adresse;
    }
}

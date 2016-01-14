package apidez.com.databinding.model.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.UUID;

/**
 * Created by nongdenchet on 10/21/15.
 */
public class Place extends BaseObservable {

    @SerializedName("icon")
    private String icon;

    @SerializedName("place_id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("types")
    private List<String> types;

    private Place(String icon, String id, String name, List<String> types) {
        this.icon = icon;
        this.id = id;
        this.name = name;
        this.types = types;
    }

    @Bindable
    public String getIcon() {
        return icon;
    }

    public String getId() {
        return id;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public List<String> getTypes() {
        return types;
    }

    public static class Builder {
        private String id = UUID.randomUUID().toString();
        private String icon;
        private String name;
        private List<String> types;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder icon(String icon) {
            this.icon = icon;
            return this;
        }

        public Builder types(List<String> types) {
            this.types = types;
            return this;
        }

        public Place build() {
            return new Place(icon, id, name, types);
        }
    }
}

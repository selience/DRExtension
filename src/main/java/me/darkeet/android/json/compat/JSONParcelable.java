package me.darkeet.android.json.compat;

public interface JSONParcelable {

    public void writeToParcel(JSONParcel out);

    public interface Creator<T extends JSONParcelable> {

        public T createFromParcel(JSONParcel in);

        public T[] newArray(int size);
    }
}

package essths.li3.fileflow;

import android.net.Uri;

public class File {

    private String name;
    private String category;
    private boolean important;
    private Uri uri;   // vrai fichier du téléphone

    public File(String name, String category, boolean important, Uri uri) {
        this.name = name;
        this.category = category;
        this.important = important;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public boolean isImportant() {
        return important;
    }

    public Uri getUri() {
        return uri;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return name + "  (" + category + ")" + (important ? " ⭐" : "");
    }
}

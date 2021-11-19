package pedro.gouveia.notesapp;

public class Note {

    private int id;
    private String title;
    private String description;

    public Note() {  }

    public Note(int aId, String aTitle, String aDescription) {
        this.id = aId;
        this.title = aTitle;
        this.description = aDescription;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId(){
        return this.id;
    }

    @Override
    public String toString() {
        return this.title;
    }

}

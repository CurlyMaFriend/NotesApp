package pedro.gouveia.notesapp;

public class Note {

    private String title;
    private String description;

    public Note() {  }

    public Note(String aTitle, String aDescription) {
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

    @Override
    public String toString() {
        return "Note{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}

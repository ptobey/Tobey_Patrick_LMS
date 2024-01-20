public class Book {

    private String id;
    private String title;
    private String author;


    public Book( String title, String author) {
        String newId;

        this.title = title;
        this.author = author;

        do {
            newId = String.valueOf(Main.incrementIdCounter());
            System.out.println();
        } while(Main.getExistingIds().contains(newId));

        this.id = newId;
        Main.addExistingId(newId);
    }

    public Book( String id, String title, String author) {
        this.title = title;
        this.author = author;
        this.id = id;
        Main.addExistingId(id);
    }

    public String getId() {
        return this.id;
    }
    public String getTitle() {
        return this.title;
    }
    public String getAuthor() {
        return this.author;
    }
}

public class Book {

    private int id;
    private String title;
    private String author;


    public Book( String title, String author) {
        int newId;

        this.title = title;
        this.author = author;

        do {
            newId = Main.incrementIdCounter();
            System.out.println();
        } while(Main.getExistingIds().contains(newId));

        this.id = newId;
        Main.addExistingId(newId);
    }

    public int getId() {
        return this.id;
    }
    public String getTitle() {
        return this.title;
    }
    public String getAuthor() {
        return this.author;
    }
}

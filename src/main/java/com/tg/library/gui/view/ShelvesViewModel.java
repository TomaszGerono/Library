package com.tg.library.gui.view;

public class ShelvesViewModel {

//    private final ShelfService shelfService;
//    private final BookService bookService;
//
//    private final ObservableList<Shelf> topics = FXCollections.observableArrayList();
//    private final ObservableList<Book> shelfBooks = FXCollections.observableArrayList();
//
//    private final ObjectProperty<Shelf> selectedShelf = new SimpleObjectProperty<>();
//    private final ObjectProperty<Book> selectedBook = new SimpleObjectProperty<>();
//
//    @Autowired
//    public ShelvesViewModel(ShelfService shelfService, BookService bookService) {
//        this.shelfService = shelfService;
//        this.bookService = bookService;
//    }

//    public void load() {
//        topics.setAll(shelfService.findAll());
//        if (selectedShelf.get() != null) loadBooksForSelectedShelf();
//        else shelfBooks.clear();
//    }
//
//    public ObservableList<Shelf> topics() { return topics; }
//    public ObservableList<Book> shelfBooks() { return shelfBooks; }
//
//    public ObjectProperty<Shelf> selectedShelfProperty() { return selectedShelf; }
//    public ObjectProperty<Book> selectedBookProperty() { return selectedBook; }
//
//    public void setSelectedShelf(Shelf s) {
//        selectedShelf.set(s);
//        loadBooksForSelectedShelf();
//    }
//
//    public void setSelectedBook(Book b) { selectedBook.set(b); }
//
//    public Shelf addShelf(String name) {
//        Shelf s = shelfService.create(name);
//        load();
//        return s;
//    }
//
//    public void renameShelf(String id, String newName) {
//        shelfService.rename(id, newName);
//        load();
//    }
//
//    public void deleteShelf(String id) {
//        shelfService.deleteById(id);
//        selectedShelf.set(null);
//        load();
//    }
//
//    public void addBookToShelf(String shelfId, String bookId) {
//        shelfService.addBookToShelf(shelfId, bookId);
//        loadBooksForSelectedShelf();
//    }
//
//    public void removeBookFromShelf(String shelfId, String bookId) {
//        shelfService.removeBookFromShelf(shelfId, bookId);
//        loadBooksForSelectedShelf();
//    }
//
//    public Set<Book> allBooks() {
//        return new HashSet<>(bookService.findAll());
//    }
//
//    private void loadBooksForSelectedShelf() {
//        Shelf s = selectedShelf.get();
//        if (s == null) { shelfBooks.clear(); return; }
//
//        var ids = shelfService.findBookIdsInShelf(s.getId());
//        var all = bookService.findAll();
//        shelfBooks.setAll(all.stream().filter(b -> ids.contains(b.getId())).toList());
//    }
}


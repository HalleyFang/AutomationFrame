package core.utils;

public enum Authors {
    Halley("halley.fang");

    private String name;

    Authors(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return name;
    }
}

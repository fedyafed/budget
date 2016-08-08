package io.github.fedyafed;

/**
 * Created by fedyafed on 09.08.16.
 */
public class Category {
    private long id;
    private String name;
    private long parentId;

    public Category(long id, String name, long parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }
}

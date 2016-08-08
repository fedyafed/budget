package io.github.fedyafed;

/**
 * Created by fedyafed on 09.08.16.
 */
public class Purchase {
    private long id;
    private String name;
    private long categoryId;
    private double count;
    private double totalPrice;

    public Purchase(long id, String name, long categoryId, double count, double totalPrice) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.count = count;
        this.totalPrice = totalPrice;
    }
}

import java.util.HashMap;
import java.util.Map;

public enum Category {
    Food(1),
    Electricity(2),
    Restaurant(3),
    Vacation(4);
    private int id;

    Category(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    private static final Map<Integer, Category> map = new HashMap<>();

    static {
        for (Category category : Category.values()) {
            map.put(category.getId(), category);
        }
    }

    public static Category valueOf(int id) {
        return map.get(id);
    }
}

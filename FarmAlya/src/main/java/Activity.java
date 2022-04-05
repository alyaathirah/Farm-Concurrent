public class Activity {
    String id;
    String date;
    String action;
    String type;
    String unit;
    double quantity;
    int field;
    int row;

    String farmId;
    String userId;

    public Activity(String id, String date, String action, String type, String unit, double quantity, int field, int row, String farmId, String userId) {
        this.id = id;
        this.date = date;
        this.action = action;
        this.type = type;
        this.unit = unit;
        this.quantity = quantity;
        this.field = field;
        this.row = row;
        this.farmId = farmId;
        this.userId = userId;
    }

    public Activity(String action, String type, String farmId, String userId) {
        this.action = action;
        this.type = type;
        this.farmId = farmId;
        this.userId = userId;
    }
}

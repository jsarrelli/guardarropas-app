package model.enums;

public enum Capa {

    //Tiro un enum como ejemplo
    INTERIOR(1),
    INTERMEDIO(2),
    EXTERIOR(3);

    private int value;

    private Capa(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


}

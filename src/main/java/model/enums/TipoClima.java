package model.enums;

import app.Properties;

import java.util.ArrayList;
import java.util.Arrays;

public enum TipoClima {

    CALUROSO(30, 80, 0, 25),

    CALIDO(20, 30, 5, 30),

    TEMPLADO(15, 20, 15, 40),

    FRIO(5, 15, 25, 50),

    MUY_FRIO(-10, 5, 65, 100);

    Properties properties = new Properties();
    private int minTemperatura;
    private int maxTemperatura;
    private int minAbrigo;
    private int maxAbrigo;

    TipoClima(int minTemperatura, int maxTemperatura, int minAbrigo, int maxAbrigo) {
        this.minTemperatura = minTemperatura;
        this.maxTemperatura = maxTemperatura;
        this.minAbrigo = minAbrigo;
        this.maxAbrigo = maxAbrigo;
    }

    public static int getDatos(String identificador) {
        String strTemp = Properties.getProperty(identificador);
        return Integer.parseInt(strTemp);
    }

    public static TipoClima getTipoClima(double temp) {
        ArrayList<TipoClima> climas = new ArrayList<TipoClima>(Arrays.asList(TipoClima.values()));

        return climas.stream()
                .filter(tipoClima -> tipoClima.getMaxTemperatura() >= temp && temp >= tipoClima.getMinTemperatura())
                .findFirst().get();

    }

    public int getMinTemperatura() {
        return minTemperatura;
    }

    public void setMinTemperatura(int minTemperatura) {
        this.minTemperatura = minTemperatura;
    }

    public int getMaxTemperatura() {
        return maxTemperatura;
    }

    public void setMaxTemperatura(int maxTemperatura) {
        this.maxTemperatura = maxTemperatura;
    }

    public int getMinAbrigo() {
        return minAbrigo;
    }

    public void setMinAbrigo(int minAbrigo) {
        this.minAbrigo = minAbrigo;
    }

    public int getMaxAbrigo() {
        return maxAbrigo;
    }

    public void setMaxAbrigo(int maxAbrigo) {
        this.maxAbrigo = maxAbrigo;
    }

}

package de.afp.db_kino.model;

public class Raum {
    private int raum_id;
    private String name;
    private boolean is_3d;
    private int kapazitaet;

    public Raum() {
    }

    public Raum(int raum_id, String name, boolean is_3d, int kapazitaet) {
        this.raum_id = raum_id;
        this.name = name;
        this.is_3d = is_3d;
        this.kapazitaet = kapazitaet;
    }

    public Raum(String name, boolean is_3d, int kapazitaet) {
        this.name = name;
        this.is_3d = is_3d;
        this.kapazitaet = kapazitaet;
    }

    public int getRaum_id() {
        return this.raum_id;
    }

    public void setRaum_id(int raum_id) {
        this.raum_id = raum_id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIs_3d() {
        return this.is_3d;
    }

    public void setIs_3d(boolean is_3d) {
        this.is_3d = is_3d;
    }

    public int getKapazitaet() {
        return this.kapazitaet;
    }

    public void setKapazitaet(int kapazitaet) {
        this.kapazitaet = kapazitaet;
    }

    @Override
    public String toString() {
        return "RaumID: " + this.getRaum_id() + " Name: " + this.getName() +
                " 3D: " + this.getIs_3d() + " Kapazität: " + this.getKapazitaet();
    }

}

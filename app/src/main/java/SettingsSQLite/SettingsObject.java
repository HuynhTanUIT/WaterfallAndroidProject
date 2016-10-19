package SettingsSQLite;

/**
 * Created by TAN-PC on 10/12/2016.
 */

public class SettingsObject {
    public Integer id;
    public Integer valves;
    public Integer rows;
    public Integer images;
    public Integer threhold;
    public String ip;
    public Integer port;
    public Integer languages;
    public Integer themes;

    public SettingsObject(Integer id, Integer valves, Integer rows, Integer images, Integer threhold, String ip, Integer port, Integer languages, Integer themes) {
        this.id = id;
        this.valves = valves;
        this.rows = rows;
        this.images = images;
        this.threhold = threhold;
        this.ip = ip;
        this.port = port;
        this.languages = languages;
        this.themes = themes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getValves() {
        return valves;
    }

    public void setValves(Integer valves) {
        this.valves = valves;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getImages() {
        return images;
    }

    public void setImages(Integer images) {
        this.images = images;
    }

    public Integer getThrehold() {
        return threhold;
    }

    public void setThrehold(Integer threhold) {
        this.threhold = threhold;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getLanguages() {
        return languages;
    }

    public void setLanguages(Integer languages) {
        this.languages = languages;
    }

    public Integer getThemes() {
        return themes;
    }

    public void setThemes(Integer themes) {
        this.themes = themes;
    }
}

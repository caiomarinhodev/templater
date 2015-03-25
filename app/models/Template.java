package models;

import javax.persistence.*;

/**
 * Created by caio on 24/03/15.
 */
@Entity
@Table(name = "TEMPLATE")
public class Template {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTemplate;
    @Column
    private String titulo;
    @Column
    private String url_preview;
    @Column
    private String url_download;
    @Column
    private String url_picture;
    @Column
    private String tag;
    @Column
    private String categorie;
    @Column
    private String text;
    @Column
    private int type;
    @Column
    private int downloads;


    public Template(){

    }

    public Template(String titulo, String url_preview, String url_download, String url_picture, String tag, String categorie, String text, int type) {
        this.titulo = titulo;
        this.url_preview = url_preview;
        this.url_download = url_download;
        this.url_picture = url_picture;
        this.tag = tag;
        this.categorie = categorie;
        this.text = text;
        this.type = type;
        this.downloads=0;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public Long getIdTemplate() {
        return idTemplate;
    }

    public void setIdTemplate(Long idTemplate) {
        this.idTemplate = idTemplate;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUrl_preview() {
        return url_preview;
    }

    public void setUrl_preview(String url_preview) {
        this.url_preview = url_preview;
    }

    public String getUrl_download() {
        return url_download;
    }

    public void setUrl_download(String url_download) {
        this.url_download = url_download;
    }

    public String getUrl_picture() {
        return url_picture;
    }

    public void setUrl_picture(String url_picture) {
        this.url_picture = url_picture;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

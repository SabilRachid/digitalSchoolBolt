package com.digital.school.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Link {
    private String title;
    private String url;
    private String icon;
    private String description;
    private boolean external;

    // Getters and setters
    public String getTitle()  { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public boolean isExternal() { return external; }
    public void setExternal(boolean external) { this.external = external; }

}

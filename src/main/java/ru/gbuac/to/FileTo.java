package ru.gbuac.to;

import org.hibernate.validator.constraints.SafeHtml;

public class FileTo {
    @SafeHtml
    String fileUrl;

    public FileTo(@SafeHtml String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileTo fileTo = (FileTo) o;

        return fileUrl != null ? fileUrl.equals(fileTo.fileUrl) : fileTo.fileUrl == null;
    }

    @Override
    public int hashCode() {
        return fileUrl != null ? fileUrl.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "FileTo{" +
                "fileUrl='" + fileUrl + '\'' +
                '}';
    }
}

package ru.gbuac.to;

public class CatalogElemTo extends BaseTo {
    private Integer idParentCatalogElem;

    private String valueInt;

    private String valueStr;

    private Integer catalogId;

    public CatalogElemTo(Integer id, Integer idParentCatalogElem, String valueInt, String valueStr, Integer catalogId) {
        super(id);
        this.idParentCatalogElem = idParentCatalogElem;
        this.valueInt = valueInt;
        this.valueStr = valueStr;
        this.catalogId = catalogId;
    }

    public Integer getIdParentCatalogElem() {
        return idParentCatalogElem;
    }

    public void setIdParentCatalogElem(Integer idParentCatalogElem) {
        this.idParentCatalogElem = idParentCatalogElem;
    }

    public String getValueInt() {
        return valueInt;
    }

    public void setValueInt(String valueInt) {
        this.valueInt = valueInt;
    }

    public String getValueStr() {
        return valueStr;
    }

    public void setValueStr(String valueStr) {
        this.valueStr = valueStr;
    }

    public Integer getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Integer catalogId) {
        this.catalogId = catalogId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CatalogElemTo that = (CatalogElemTo) o;

        if (idParentCatalogElem != null ? !idParentCatalogElem.equals(that.idParentCatalogElem) : that.idParentCatalogElem != null)
            return false;
        if (valueInt != null ? !valueInt.equals(that.valueInt) : that.valueInt != null) return false;
        if (valueStr != null ? !valueStr.equals(that.valueStr) : that.valueStr != null) return false;
        return catalogId != null ? catalogId.equals(that.catalogId) : that.catalogId == null;
    }

    @Override
    public int hashCode() {
        int result = idParentCatalogElem != null ? idParentCatalogElem.hashCode() : 0;
        result = 31 * result + (valueInt != null ? valueInt.hashCode() : 0);
        result = 31 * result + (valueStr != null ? valueStr.hashCode() : 0);
        result = 31 * result + (catalogId != null ? catalogId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CatalogElemTo{" +
                "id=" + id +
                ", id_parent_catalogelem=" + idParentCatalogElem +
                ", value_int='" + valueInt + '\'' +
                ", value_str='" + valueStr + '\'' +
                ", catalog_id=" + catalogId +
                '}';
    }
}

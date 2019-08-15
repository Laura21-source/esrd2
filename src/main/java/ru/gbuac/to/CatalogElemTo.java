package ru.gbuac.to;

import javax.persistence.criteria.CriteriaBuilder;

public class CatalogElemTo extends BaseTo {
    Integer id_parent_catalogelem;

    String value_int;

    String value_str;

    Integer catalog_id;

    public CatalogElemTo(Integer id, Integer id_parent_catalogelem, String value_int, String value_str, Integer catalog_id) {
        super(id);
        this.id_parent_catalogelem = id_parent_catalogelem;
        this.value_int = value_int;
        this.value_str = value_str;
        this.catalog_id = catalog_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CatalogElemTo that = (CatalogElemTo) o;

        if (id_parent_catalogelem != null ? !id_parent_catalogelem.equals(that.id_parent_catalogelem) : that.id_parent_catalogelem != null)
            return false;
        if (value_int != null ? !value_int.equals(that.value_int) : that.value_int != null) return false;
        if (value_str != null ? !value_str.equals(that.value_str) : that.value_str != null) return false;
        return catalog_id != null ? catalog_id.equals(that.catalog_id) : that.catalog_id == null;
    }

    @Override
    public int hashCode() {
        int result = id_parent_catalogelem != null ? id_parent_catalogelem.hashCode() : 0;
        result = 31 * result + (value_int != null ? value_int.hashCode() : 0);
        result = 31 * result + (value_str != null ? value_str.hashCode() : 0);
        result = 31 * result + (catalog_id != null ? catalog_id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CatalogElemTo{" +
                "id=" + id +
                ", id_parent_catalogelem=" + id_parent_catalogelem +
                ", value_int='" + value_int + '\'' +
                ", value_str='" + value_str + '\'' +
                ", catalog_id=" + catalog_id +
                '}';
    }
}

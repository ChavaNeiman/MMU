package main.java.com.company.dm;

import java.io.Serializable;

public class DataModel<T> implements Serializable {

    public Long id;
    public  T content;
    private static final long serialVersionUID = 1L;

    /**
     * Ctor
     * @param id to be put in the current data model
     * @param content to be put in the current data model
     */
    public DataModel(Long id,T content) {
        this.id = id;
        this.content = content;
    }

    /**
     *
     * @return a string representing an entity in the data model.
     */
    @Override
    public String toString() {
        return "(" + id +")";
    }

    /**
     *
     * @param o object being compared to this
     * @return true if their content is equal.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DataModel)) {
            return false;
        }

        DataModel<T> dm = (DataModel<T>) o;
        return this.id.equals(dm.getDataModelId());
    }

    /**
     * computes and returns the hashcode according to the id*/
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     *
     * @return id of the current data model.
     */
    public Long getDataModelId() {
        return id;
    }

    /**
     * receives an id and sets it value in the current id
     * @param id
     */
    public void setDataModelId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return the content in current data model
     */
    public T getContent() {
        return content;
    }

    /**
     * sets received content to the current data model
     * @param content to be placed in the current data model
     */
    public void setContent(T content) {
        this.content = content;
    }
}


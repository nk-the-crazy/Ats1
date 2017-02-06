package model.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "system_type" )
public class SystemAttribute
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id")
    private long id;
    
    @Column( name = "type_id")
    private short typeId;
    
    @Column( name = "category_id")
    private short categoryId;
    
    @Column( name = "locale_id")
    private short localeId;
    
    @Column( name = "parent_category_id")
    private short parentCategoryId;
    
    
    @Column( name = "name", length = 60, nullable = false )
    private String name;
    
    @Column( name = "details")
    private String details;

    public long getId()
    {
        return id;
    }

    
    public int getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId( int categoryId )
    {
        this.categoryId = (short)categoryId;
    }

    public int getParentCategoryId()
    {
        return parentCategoryId;
    }

    public void setParentCategoryId( short parentCategoryId )
    {
        this.parentCategoryId = parentCategoryId;
    }

    public int getLocaleId()
    {
        return localeId;
    }

    public void setLocaleId( int localeId )
    {
        this.localeId = (short)localeId;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getDetails()
    {
        return details;
    }

    public void setDetails( String details )
    {
        this.details = details;
    }

    public int getTypeId()
    {
        return typeId;
    }

    public void setTypeId( int typeId )
    {
        this.typeId = (short)typeId;
    }


}

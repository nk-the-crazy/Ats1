package service.impl.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.api.system.SystemTypeDAO;
import model.system.SystemAttribute;
import service.api.system.SystemManager;


@Service("localeManagerService")
@Transactional
public class SystemManagerImpl implements SystemManager
{
    //---------------------------------
    private static final Logger logger = LoggerFactory.getLogger(SystemManagerImpl.class);
    //---------------------------------
            
    @Autowired
    SystemTypeDAO systemTypeDAO;
    
    /**************************************************
     * 
     */
    @Override
    public SystemAttribute createSystemType(int typeId, int categoryId, int parentCategoryId, String name , int localeId)
    {
        SystemAttribute systemType = null;
        
        try 
        {
            systemType = new SystemAttribute();
            systemType.setTypeId( typeId );
            systemType.setCategoryId( categoryId );
            systemType.setName( name );
        }
        catch(Exception e) 
        {
            logger.error( e.toString() , e );
        }
        
        return systemType;
    
    }
    
    
    /**************************************************
     * 
     */
    @Override
    public SystemAttribute saveSystemType(SystemAttribute systemType) 
    {
        try 
        {
            return systemTypeDAO.save( systemType );
        }
        catch(Exception e) 
        {
            logger.error( " **** Error in Save SystemType:" +  e.toString() , e );
            return null;
        }
    }
   
}

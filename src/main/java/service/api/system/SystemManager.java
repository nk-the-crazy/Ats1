package service.api.system;

import model.system.SystemAttribute;

public interface SystemManager
{

    SystemAttribute saveSystemType( SystemAttribute systemType );

    SystemAttribute createSystemType( int typeId, int categoryId, int parentCategoryId, String name, int localeId );

}

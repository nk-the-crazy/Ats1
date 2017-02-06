package service.api.organization;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import model.organization.Organization;
import model.person.Person;

public interface OrganizationManager
{

    String generateCode();

    Organization saveOrganization( Organization organization );

    Organization createOrganization( String code, String name, int type );

    Organization getOrganizationDetails( long organizationId );

    Page<Organization> getOrganizationsByName( String organizationName, Pageable pageable );

    List<Organization> getOrganizationsByName( String organizationName );
    
    List<Organization> getOrganizations();

    Page<Organization> getOrganizations( Pageable pageable );

    Page<Person> getOrganizationPersonList( long organizationId, Pageable pageable );

    
}
